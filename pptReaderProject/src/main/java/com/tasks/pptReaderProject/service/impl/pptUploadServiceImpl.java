package com.tasks.pptReaderProject.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFGroupShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.tasks.pptReaderProject.service.pptUploadService;
import com.tasks.pptReaderProject.util.svgPathExtractor;

@Service
public class pptUploadServiceImpl implements pptUploadService {

	@Override
	public Map<String, Object> parsePpt(MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		List<Map<String, Object>> slidesData = new ArrayList<>();

		try (InputStream is = file.getInputStream(); XMLSlideShow ppt = new XMLSlideShow(is)) {
			for (XSLFSlide slide : ppt.getSlides()) {
				List<Map<String, Object>> shapesList = new ArrayList<>();

				for (XSLFShape shape : slide.getShapes()) {
					Map<String, Object> shapeData = extractShapeData(shape);
					if (!shapeData.isEmpty()) {
						shapesList.add(shapeData);
					}
				}

				if (!shapesList.isEmpty()) {
					slidesData.add(Map.of("slide_number", slide.getSlideNumber(), "shapes", shapesList));
				}
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing PPT file");
		}

		if (slidesData.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shapes not found");
		}

		response.put("slides", slidesData);
		return response;
	}

	private Map<String, Object> extractShapeData(XSLFShape shape) {
		Map<String, Object> shapeData = new HashMap<>();
		shapeData.put("shape_name", shape.getShapeName());

		if (shape instanceof XSLFGroupShape groupShape) {
			List<Map<String, Object>> innerShapes = new ArrayList<>();
			for (XSLFShape innerShape : groupShape.getShapes()) {
				innerShapes.add(extractShapeData(innerShape));
			}
			shapeData.put("inner_shapes", innerShapes);
		}

		List<String> svgPaths = svgPathExtractor.extractSvgPathNumbers(shape);
		if (!svgPaths.isEmpty()) {
			shapeData.put("svg_paths", svgPaths);
		}

		return shapeData;
	}
}
