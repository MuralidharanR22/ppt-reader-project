package com.tasks.pptReaderProject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tasks.pptReaderProject.service.pptUploadService;

@RestController
public class pptUploadController {

	@Autowired
	private pptUploadService pptService;

	@PostMapping("/api/ppt/upload")
	public ResponseEntity<Map<String, Object>> uploadPpt(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok(pptService.parsePpt(file));
	}
}
