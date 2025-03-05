package com.tasks.pptReaderProject.util;

//import java.awt.geom.Rectangle2D;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.xslf.usermodel.XSLFAutoShape;
//import org.apache.poi.xslf.usermodel.XSLFConnectorShape;
//import org.apache.poi.xslf.usermodel.XSLFFreeformShape;
//import org.apache.poi.xslf.usermodel.XSLFGroupShape;
//import org.apache.poi.xslf.usermodel.XSLFShape;
//import org.apache.poi.xslf.usermodel.XSLFTextBox;
//
//public class svgPathExtractor {
//
//	public static List<String> extractSvgPathNumbers(XSLFShape shape) {
//		List<String> svgPaths = new ArrayList<>();
//
//		if (shape instanceof XSLFAutoShape autoShape) {
//			svgPaths.add(generateSvgPathFromShape(autoShape));
//		} else if (shape instanceof XSLFTextBox textBox) {
//			svgPaths.add(generateSvgPathFromTextBox(textBox));
//		}
//
//		return svgPaths;
//	}
//
//	private static String generateSvgPathFromShape(XSLFAutoShape shape) {
//		var bounds = shape.getAnchor();
//		return String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z", bounds.getX(), bounds.getY(),
//				bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(),
//				bounds.getY() + bounds.getHeight(), bounds.getX(), bounds.getY() + bounds.getHeight());
//	}
//
//	private static String generateSvgPathFromTextBox(XSLFTextBox textBox) {
//		var bounds = textBox.getAnchor();
//		return String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z", bounds.getX(), bounds.getY(),
//				bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(),
//				bounds.getY() + bounds.getHeight(), bounds.getX(), bounds.getY() + bounds.getHeight());
//	}
//
//	public static Map<String, Object> extractSvgPaths(XSLFShape shape) {
//		Map<String, Object> shapePaths = new HashMap<>();
//		List<String> outerPaths = new ArrayList<>();
//		List<String> innerPaths = new ArrayList<>();
//
//		if (shape instanceof XSLFAutoShape autoShape) {
//			outerPaths.add(generateSvgPath(autoShape));
//		} else if (shape instanceof XSLFFreeformShape freeformShape) {
//			outerPaths.add(generateSvgPath(freeformShape));
//		} else if (shape instanceof XSLFTextBox textBox) {
//			outerPaths.add(generateSvgPath(textBox));
//		} else if (shape instanceof XSLFConnectorShape connectorShape) {
//			outerPaths.add(generateConnectorPath(connectorShape));
//		} 
//		if (shape instanceof XSLFGroupShape groupShape) {
//			for (XSLFShape innerShape : groupShape.getShapes()) {
//				Object extractedPaths = extractSvgPaths(innerShape).get("outer_paths");
//				if (extractedPaths instanceof List<?>) {
//					for (Object path : (List<?>) extractedPaths) {
//						if (path instanceof String) {
//							innerPaths.add((String) path);
//						}
//					}
//				}
//			}
//		}
//
//		if (!outerPaths.isEmpty()) {
//			shapePaths.put("outer_paths", outerPaths);
//		}
//		if (!innerPaths.isEmpty()) {
//			shapePaths.put("inner_paths", innerPaths);
//		}
//
//		return shapePaths;
//	}
//
//	private static String generateSvgPath(XSLFAutoShape shape) {
//		return convertToSvgPath(shape.getAnchor());
//	}
//
//	private static String generateSvgPath(XSLFFreeformShape shape) {
//		return convertToSvgPath(shape.getAnchor());
//	}
//
//	private static String generateSvgPath(XSLFTextBox shape) {
//		return convertToSvgPath(shape.getAnchor());
//	}
//
//	private static String generateConnectorPath(XSLFConnectorShape shape) {
//		return convertToSvgPath(shape.getAnchor());
//	}
//
//	private static String convertToSvgPath(Rectangle2D bounds) {
//		return String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z", bounds.getX(), bounds.getY(),
//				bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(),
//				bounds.getY() + bounds.getHeight(), bounds.getX(), bounds.getY() + bounds.getHeight());
//	}
//
//	public static List<String> extractSvgPathNumbers(XSLFShape shape) {
//        List<String> svgPaths = new ArrayList<>();
//
//        if (shape instanceof XSLFAutoShape autoShape) {
//            svgPaths.add(generateSvgPath(autoShape));
//        } else if (shape instanceof XSLFFreeformShape freeformShape) {
//            svgPaths.add(generateSvgPath(freeformShape));
//        } else if (shape instanceof XSLFTextBox textBox) {
//            svgPaths.add(generateSvgPath(textBox));
//        } else if (shape instanceof XSLFConnectorShape connectorShape) {
//            svgPaths.add(generateConnectorPath(connectorShape));
//        }
//
//        return svgPaths;
//    }
//
//    private static String generateSvgPath(XSLFAutoShape shape) {
//        return convertToSvgPath(shape.getAnchor());
//    }
//
//    private static String generateSvgPath(XSLFFreeformShape shape) {
//        return convertToSvgPath(shape.getAnchor());
//    }
//
//    private static String generateSvgPath(XSLFTextBox shape) {
//        return convertToSvgPath(shape.getAnchor());
//    }
//
//    private static String generateConnectorPath(XSLFConnectorShape shape) {
//        return convertToSvgPath(shape.getAnchor());
//    }
//
//    private static String convertToSvgPath(Rectangle2D bounds) {
//        return String.format("M %.2f %.2f L %.2f %.2f L %.2f %.2f L %.2f %.2f Z",
//                bounds.getX(), bounds.getY(),
//                bounds.getX() + bounds.getWidth(), bounds.getY(),
//                bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(),
//                bounds.getX(), bounds.getY() + bounds.getHeight());
//    }
//}
