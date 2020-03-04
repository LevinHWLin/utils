package com.common;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class RenderXmlUtils {
    public static void main(String[] args){
        writeXml("E:\\LevinGitHub\\xmlTest.xml");

        renderXml("E:\\LevinGitHub\\xmlTest.xml");
    }

    public static void renderXml(String filePath){
        try{
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(filePath));

            Element element = document.getRootElement();
            getNodes(element);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getNodes(Element element) {
        String name = element.getName();
        System.out.println("节点:" + name);
        //获取节点属性
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            System.out.println("属性：" + attribute.getName() + "，值：" + attribute.getValue());
        }
        String value = element.getTextTrim();
        if (!value.equals("")) {
            System.out.println("值：" + value);
        }
        //判断是否有下一个节点
        Iterator<Element> elementIterator = element.elementIterator();
        while (elementIterator.hasNext()) {
            Element next = elementIterator.next();
            getNodes(next);
        }
    }


    public static void writeXml(String filePath){
        try{
            Document document = DocumentHelper.createDocument();
            Element rootElement = document.addElement("file");

            Element fileNameElement = rootElement.addElement("file-name");
            fileNameElement.addText("20200304.txt");

            fileNameElement.addAttribute("status","只读");
            fileNameElement.addCDATA("cdata");
            fileNameElement.addComment("comment");
            fileNameElement.addEntity("entity","真的吗");
            fileNameElement.addNamespace("namespace","命名空间");
            fileNameElement.addProcessingInstruction("target","标签");

            Element fileType = fileNameElement.addElement("file-type");
            fileType.addText("txt");

            // 创建输出流
            Writer out = new PrintWriter(filePath, "utf-8");
            // 格式化
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);//去掉原来的空白(\t和换行和空格)！

            XMLWriter writer = new XMLWriter(out, format);
            // 把document对象写到out流中。
            writer.write(document);

            out.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
