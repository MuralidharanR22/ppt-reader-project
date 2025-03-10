package com.tasks.pptReaderProject.service.impl;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFConnectorShape;
import org.apache.poi.xslf.usermodel.XSLFFreeformShape;
import org.apache.poi.xslf.usermodel.XSLFGroupShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tasks.pptReaderProject.service.pptUploadService;

@Service
public class pptUploadServiceImpl implements pptUploadService {

	@Override
	public List<Map<String, List<String>>> processPpt(MultipartFile file) throws IOException {
		List<Map<String, List<String>>> slidesPaths = new ArrayList<>();
		XMLSlideShow ppt = new XMLSlideShow(file.getInputStream());

		for (XSLFSlide slide : ppt.getSlides()) {
			Map<String, List<String>> slidePaths = new HashMap<>();
			List<String> outerPaths = new ArrayList<>();
			List<String> innerPaths = new ArrayList<>();
			for (XSLFShape shape : slide.getShapes()) {
				Map<String, List<String>> extractedPaths = extractSvgPaths(shape);
				if (extractedPaths.containsKey("outer_paths")) {
					outerPaths.addAll(extractedPaths.get("outer_paths"));
				}
				if (extractedPaths.containsKey("inner_paths")) {
					innerPaths.addAll(extractedPaths.get("inner_paths"));
				}
			}
			if (!outerPaths.isEmpty()) {
				slidePaths.put("outer_paths", outerPaths);
			}
			if (!innerPaths.isEmpty()) {
				slidePaths.put("inner_paths", innerPaths);
			}
			if (!slidePaths.isEmpty()) {
				slidesPaths.add(slidePaths);
			}
		}
		return slidesPaths;
	}

	private Map<String, List<String>> extractSvgPaths(XSLFShape shape) {
		Map<String, List<String>> shapePaths = new HashMap<>();
		List<String> outerPaths = new ArrayList<>();
		List<String> innerPaths = new ArrayList<>();

		if (shape instanceof XSLFAutoShape autoShape) {
			outerPaths.add(generateSvgPath(autoShape));
			innerPaths.addAll(extractInnerPaths(autoShape));
		} else if (shape instanceof XSLFFreeformShape freeformShape) {
			outerPaths.add(generateSvgPath(freeformShape));
			innerPaths.addAll(extractInnerPaths(freeformShape));
		} else if (shape instanceof XSLFTextBox textBox) {
			outerPaths.add(generateSvgPath(textBox));
		} else if (shape instanceof XSLFConnectorShape connectorShape) {
			outerPaths.add(generateSvgPath(connectorShape));
		} else if (shape instanceof XSLFGroupShape groupShape) {
			outerPaths.add(generateGroupShapePath(groupShape));

			for (XSLFShape innerShape : groupShape.getShapes()) {
				Map<String, List<String>> innerPathsMap = extractSvgPaths(innerShape);
				innerPaths.addAll(innerPathsMap.getOrDefault("outer_paths", new ArrayList<>()));
				innerPaths.addAll(innerPathsMap.getOrDefault("inner_paths", new ArrayList<>()));
			}
		}

		if (!outerPaths.isEmpty()) {
			shapePaths.put("outer_paths", outerPaths);
		}
		if (!innerPaths.isEmpty()) {
			shapePaths.put("inner_paths", innerPaths);
		}

		return shapePaths;
	}

	private String generateSvgPath(XSLFAutoShape shape) {
		Rectangle2D bounds = shape.getAnchor();
		return convertToSvgPath(bounds);
	}

	private String generateSvgPath(XSLFFreeformShape shape) {
		Rectangle2D bounds = shape.getAnchor();
		return convertToSvgPath(bounds);
	}

	private String generateSvgPath(XSLFTextBox shape) {
		Rectangle2D bounds = shape.getAnchor();
		return convertToSvgPath(bounds);
	}

	private String generateSvgPath(XSLFConnectorShape shape) {
		Rectangle2D bounds = shape.getAnchor();
		return convertToSvgPath(bounds);
	}

	private String generateGroupShapePath(XSLFGroupShape shape) {
		Rectangle2D bounds = shape.getAnchor();
		return convertToSvgPath(bounds);
	}

	private List<String> extractInnerPaths(XSLFShape shape) {
		List<String> innerPaths = new ArrayList<>();

		if (shape instanceof XSLFAutoShape || shape instanceof XSLFFreeformShape) {
			Rectangle2D bounds = shape.getAnchor();

			// Dynamically calculate offset (e.g., 10% of width/height)
			double offsetX = bounds.getWidth() * 0.1;
			double offsetY = bounds.getHeight() * 0.1;

			// Generate inner path based on offset
			String innerPath = String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z",
					bounds.getX() + offsetX, bounds.getY() + offsetY, bounds.getX() + bounds.getWidth() - offsetX,
					bounds.getY() + offsetY, bounds.getX() + bounds.getWidth() - offsetX,
					bounds.getY() + bounds.getHeight() - offsetY, bounds.getX() + offsetX,
					bounds.getY() + bounds.getHeight() - offsetY);

			innerPaths.add(innerPath);
		}
		return innerPaths;
	}

	private String convertToSvgPath(Rectangle2D bounds) {
		return String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z", bounds.getX(), bounds.getY(),
				bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(),
				bounds.getY() + bounds.getHeight(), bounds.getX(), bounds.getY() + bounds.getHeight());
	}

}
