package com.tasks.pptReaderProject.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface pptUploadService {

	public List<Map<String, List<String>>> processPpt(MultipartFile file) throws IOException;
}
