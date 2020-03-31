//package com.common;
//
//import org.apache.poi.common.usermodel.fonts.FontInfo;
//import org.apache.poi.extractor.POITextExtractor;
//import org.apache.poi.hslf.record.Document;
//import org.apache.poi.hslf.usermodel.*;
//import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
//import org.apache.poi.sl.usermodel.*;
//import org.apache.poi.xslf.usermodel.*;
//import org.apache.xmlbeans.XmlException;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.List;
//
//public class PptToImgDemo {
//
//    public static void main(String[] args) {
//        // 读入PPT文件
//        String filePath = "D:\\TTXN\\DevOpsDays Sharing_20181111.pptx";
//
//        String imgFilePath = "D:\\TTXN\\image\\ppt_"+System.currentTimeMillis();
//
//        File imgFile = new File(imgFilePath);
//        if(!imgFile.exists()){
//            imgFile.mkdirs();
//        }
//        //pptxToImage(filePath,imgFilePath);
//
//        File pptFile = new File("D:\\TTXN\\SQAPPT.ppt");
//        pptToImage(pptFile,imgFilePath);
//
//    }
//
//    public static void pptToImage(File pptFile,String imgFilePath){
//        try {
//            HSLFSlideShow slideShow = new HSLFSlideShow(new FileInputStream(pptFile));
//            Dimension pageSize = slideShow.getPageSize();
//            slideShow.setPageSize(new Dimension(820,640));
//            List<HSLFSlide> slides = slideShow.getSlides();
//            int slidesSize = slides.size();
//            for(int i = 0;i<slidesSize ;i++){
//                HSLFSlide slide = slides.get(i);
//                // 解决乱码问题
////                List<HSLFShape> shapes = slide.getShapes();
////                for(HSLFShape shape : shapes) {
////                    if (shape instanceof HSLFTextShape) {
////                        HSLFTextShape sh = (HSLFTextShape) shape;
////                        List<HSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
////                        for (HSLFTextParagraph hslfTextParagraph : textParagraphs) {
////                            List<HSLFTextRun> textRuns = hslfTextParagraph.getTextRuns();
////                            for (HSLFTextRun hslfTextRun : textRuns) {
////                                hslfTextRun.setFontFamily("宋体");
////                            }
////                        }
////                    }
////                }
//
////                BufferedImage image = new BufferedImage(pageSize.width+100, pageSize.height+100, BufferedImage.TYPE_INT_RGB);
////                Graphics2D graphics2d = image.createGraphics();
////                graphics2d.setColor(Color.white);
////                graphics2d.fillRect(0, 0, image.getWidth(), image.getHeight());
////                graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
////                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
////                slide.draw(graphics2d);
////                FileOutputStream out = new FileOutputStream(imgFilePath + "/ppt_" +(i+1) + ".jpg");
////                ImageIO.write(image, "png", out);
//
//
//                BufferedImage img = new BufferedImage(pageSize.width, pageSize.height,
//                        BufferedImage.TYPE_INT_RGB);
//                Graphics2D graphics = img.createGraphics();
//                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//                //graphics.setPaint(Color.white);
//                graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width+100, pageSize.height+100));
//                slide.draw(graphics);
//                FileOutputStream out = new FileOutputStream(imgFilePath + "/ppt_" +(i+1) + ".jpg");
//                ImageIO.write(img, "png", out);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void pptxToImage(String filePath,String imgFilePath){
//        try {
//            XMLSlideShow xmlSlideShow = new XMLSlideShow(new FileInputStream(new File(filePath)));
//
//            Dimension pageSize = xmlSlideShow.getPageSize();
//            List<XSLFSlide> slides = xmlSlideShow.getSlides();
//            int slidesSize = slides.size();
//            for(int i = 0;i<slidesSize ;i++){
//                XSLFSlide slide = slides.get(i);
//                // 解决乱码问题
//                List<XSLFShape> shapes = slide.getShapes();
//                for(XSLFShape shape : shapes) {
//                    if (shape instanceof XSLFTextShape) {
//                        XSLFTextShape sh = (XSLFTextShape) shape;
//                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
//                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
//                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
//                            for (XSLFTextRun xslfTextRun : textRuns) {
//                                xslfTextRun.setFontFamily("宋体");
//                            }
//                        }
//                    }
//                }
//
//                BufferedImage img = new BufferedImage(pageSize.width, pageSize.height,
//                        BufferedImage.TYPE_INT_RGB);
//                Graphics2D graphics = img.createGraphics();
//                //graphics.setPaint(Color.white);
//                graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
//                slide.draw(graphics);
//                FileOutputStream out = new FileOutputStream(imgFilePath + "/pptx_" +(i+1) + ".jpg");
//                ImageIO.write(img, "png", out);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
