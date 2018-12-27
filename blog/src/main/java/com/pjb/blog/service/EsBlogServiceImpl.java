package com.pjb.blog.service;

import com.pjb.blog.domain.User;
import com.pjb.blog.domain.es.EsBlog;
import com.pjb.blog.repository.es.EsBlogRepository;
import com.pjb.blog.vo.TagVO;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * EsBlog 服务.
 */
@Service("EsBlogService")
public class EsBlogServiceImpl implements EsBlogService {
	private final EsBlogRepository esBlogRepository;
	private final ElasticsearchTemplate elasticsearchTemplate;
	private final UserService userService;
	
	private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
	private static final String EMPTY_KEYWORD = "";

	@Autowired
	public EsBlogServiceImpl(EsBlogRepository esBlogRepository, ElasticsearchTemplate elasticsearchTemplate, UserService userService) {
		this.esBlogRepository = esBlogRepository;
		this.elasticsearchTemplate = elasticsearchTemplate;
		this.userService = userService;
	}
	@Override
	public void removeEsBlog(String id) {
		esBlogRepository.deleteById(id);
	}

	@Override
	public void updateEsBlog(EsBlog esBlog) {
        esBlogRepository.save(esBlog);
    }

	@Override
	public EsBlog getEsBlogByBlogId(Long blogId) {
		return esBlogRepository.findByBlogId(blogId);
	}

	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable){
		Sort sort = new Sort(Direction.DESC,"createTime");
		return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword, pageSort(pageable,sort));
	}

	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable){
		Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime");
		return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageSort(pageable,sort));
	}

	private Pageable pageSort(Pageable pageable,Sort sort){
		if (pageable.getSort() == null) {
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}else {
			return null;
		}
	}

	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		return esBlogRepository.findAll(pageable);
	}
 
 
	/**
	 * 最新前5
	 */
	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		Page<EsBlog> page = this.listNewestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}
	
	/**
	 * 最热前5
	 */
	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<TagVO> listTop30Tags() {
 
		List<TagVO> list = new ArrayList<>();
		// given
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				//将搜索条件设置到构建中
				.withQuery(matchAllQuery())
				//设置搜索类型
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				//指定索引库和文档类型
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("tags").field("tags").order(BucketOrder.count(false)).size(30))
				.build();
		// when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);
		
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags");

		for (Bucket actiontypeBucket : modelTerms.getBuckets()) {
			list.add(new TagVO(actiontypeBucket.getKey().toString(),
					actiontypeBucket.getDocCount()));
		}
		return list;
	}
	/**
	 * 最热前12用户
	 */
	@Override
	public List<User> listTop12Users() {
 
		List<String> usernamelist = new ArrayList<>();
		// given
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("users").field("username").order(BucketOrder.count(false)).size(12))
				.build();
		// when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("users");

		for (Bucket actiontypeBucket : modelTerms.getBuckets()) {
			String username = actiontypeBucket.getKey().toString();
			usernamelist.add(username);
		}
		return userService.listUsersByUsernames(usernamelist);
	}
}
