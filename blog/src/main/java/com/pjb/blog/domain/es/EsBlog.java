package com.pjb.blog.domain.es;

import com.pjb.blog.domain.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Blog.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "blog", type = "blog")
@XmlRootElement // MediaType 转为 XML
public class EsBlog implements Serializable {
	@Id  // 主键
	private String id;  
	@Field(type = FieldType.Keyword)
	private Long blogId; // Blog 的 id
	private String title;
	private String summary;
	private String content;
 
	@Field(type = FieldType.Text,fielddata = true)  // 不做全文检索字段
	private String username;
	@Field(type = FieldType.Text)  // 不做全文检索字段
	private String avatar;
	@Field(type = FieldType.Text) // 不做全文检索字段
	private Timestamp createTime;
	@Field(type = FieldType.Text) // 不做全文检索字段
	private Integer readSize = 0; // 访问量、阅读量
	@Field(type = FieldType.Text) // 不做全文检索字段
	private Integer commentSize = 0;  // 评论量
	@Field(type = FieldType.Text)  // 不做全文检索字段
	private Integer voteSize = 0;  // 点赞量
	@Field(type = FieldType.Text,fielddata = true)
	private String tags;  // 标签

	
	public EsBlog(Blog blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.voteSize = blog.getVoteSize();
		this.tags = blog.getTags();
	}
 
	public void update(Blog blog){
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.voteSize = blog.getVoteSize();
		this.tags = blog.getTags();
	}

	@Override
    public String toString() {
        return String.format(
                "User[id=%d, title='%s', content='%s']",
                blogId, title, content);
    }
}
