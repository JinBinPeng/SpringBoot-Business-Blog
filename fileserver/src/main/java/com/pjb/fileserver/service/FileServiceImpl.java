package com.pjb.fileserver.service;

import java.util.List;

import com.pjb.fileserver.domain.File;
import com.pjb.fileserver.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * File 服务.
 */
@Service
public class FileServiceImpl implements FileService {
	
	private final FileRepository fileRepository;

	@Autowired
	public FileServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public File saveFile(File file) {
		return fileRepository.save(file);
	}

	@Override
	public void removeFile(String id) {
		fileRepository.deleteById(id);
	}

	@Override
	public File getFileById(String id) {
		return fileRepository.findById(id).orElse(null);
	}

	@Override
	public List<File> listFiles() {
		return fileRepository.findAll();
	}

}
