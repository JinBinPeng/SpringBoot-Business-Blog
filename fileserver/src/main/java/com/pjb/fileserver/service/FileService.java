package com.pjb.fileserver.service;


import com.pjb.fileserver.domain.File;

import java.util.List;

/**
 * File 服务接口.
 */
public interface FileService {
	/**
	 * 保存文件
	 */
	File saveFile(File file);
	
	/**
	 * 删除文件
	 */
	void removeFile(String id);
	
	/**
	 * 根据id获取文件
	 */
	File getFileById(String id);
	
	/**
	 * 获取文件列表
	 */
	List<File> listFiles();
}
