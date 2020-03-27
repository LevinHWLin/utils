package com.common;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class PptToPdfDemo {

    public static void main(String[] args) {
        try{
            Document pdfDocument = new Document(new RectangleReadOnly(535,950).rotate());
            File file = new File("E:\\pdf\\"+System.currentTimeMillis()+".pdf");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(pdfDocument, fileOutputStream);
            pdfDocument.open();
            pdfDocument.open();

            File pptFile = new File("E:\\pdf\\山东苹果作物知识.pptx");
            FileInputStream fin = new FileInputStream(pptFile);
            //BufferedInputStream reader = new BufferedInputStream(fin);

            int width = (int)pdfDocument.getPageSize().getWidth();
            int height = (int)pdfDocument.getPageSize().getHeight();

            int x = (int)pdfDocument.left();
            int y = (int)pdfDocument.bottom();

            XMLSlideShow xmlSlideShow = new XMLSlideShow(fin);
            Dimension pgsize = xmlSlideShow.getPageSize();
            String uid = CommonUtil.getUuid();
            int index = 1;
            int image_rate = 1;
            for (XSLFSlide xslfSlide : xmlSlideShow.getSlides()) {
                int imageWidth = (int) Math.floor(image_rate * pgsize.width);
                int imageHeight = (int) Math.floor(image_rate * pgsize.height)+100;

                BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, imageWidth, imageHeight));
                graphics.scale(image_rate, image_rate);
                xslfSlide.draw(graphics);

                File path = new File("E:\\pdf\\images");
                if (!path.exists()) {
                    path.mkdir();
                }

                String imagePath = path + "/" + uid+"_"+index + ".png";
                ++index;
                FileOutputStream out = new FileOutputStream(imagePath);
                javax.imageio.ImageIO.write(img, "png", out);

                pdfDocument.newPage();
                Image image = Image.getInstance(imagePath);
                image.scalePercent(100f);
                image.scaleToFit(width, height);
                //System.out.println(pgsize.height);
                image.setAbsolutePosition(0, 0);
                pdfDocument.add(image);
            }

            pdfDocument.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
