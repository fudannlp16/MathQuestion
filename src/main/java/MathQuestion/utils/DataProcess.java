package MathQuestion.utils;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liuxiaoyu on 16-10-10.
 */

/**
 * 数据处理类
 */
public class DataProcess {
    /**
     * 判断文件编码
     *
     * @param file
     * @return
     */
    public static String judgeCoding(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[3];
            inputStream.read(bytes);
            System.out.println(bytes[0]);
            System.out.println(bytes[1]);
            if (inputStream != null)
                inputStream.close();
            if (bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65)
                return "utf8";
            else
                return "gbk";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从xml文件中抽取题目内容
     *
     * @param file
     * @return
     */
    public static String readXML(File file) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("gbk");
        String content = null;
        try {
            Document document = saxReader.read(file);
            List<Node> list = document.selectNodes("comment()");
            content = list.get(0).getText().trim();
            content = content.replaceAll("\n", "");
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            return content;
        }
    }

    /**
     * 从docx文件中读取题目内容
     *
     * @param file
     * @return
     */
    public static String readDocx(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String docContent = extractor.getText().trim();
            //删除第一行内容，如1-1标签
            int pos = docContent.indexOf('\n');
            docContent = docContent.substring(pos).trim();
            if (fis != null)
                fis.close();
            return docContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件夹中的全部文件名
     *
     * @param dirPath
     * @return
     */
    public static List<File> getFileNames(String dirPath) {
        List<File> list = new ArrayList<File>();
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (!file1.getPath().endsWith("README.docx"))
                    list.add(file1);
            }
        }
        return list;
    }

    /**
     * 获取试验对象集合
     *
     * @param files
     * @return
     */
    public static Set<String> getExperimentObject(List<File> files) {
        Set<String> set = new HashSet<String>();
        org.jsoup.nodes.Document document = null;
        try {
            for (File file : files) {
                document = Jsoup.parse(file, "gbk");
                Elements elements = document.getElementsByTag("ObjectName");
                String ObjectName = elements.get(0).text();
                set.add(ObjectName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static String getXmlObject(File file, String tagName) {
        try {
            org.jsoup.nodes.Document document = Jsoup.parse(file, "gbk");
            Elements elements = document.getElementsByTag(tagName);
            return elements.get(0).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getXmlObject2(File file, String tag1, String tag2) {
        try {
            org.jsoup.nodes.Document document = Jsoup.parse(file, "gbk");
            Elements elements = document.select("Stratification" + " " + tag1 + " " + tag2);
            return elements.get(0).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getXmlS(File file, String tag) {
        try {
            String result = "";
            org.jsoup.nodes.Document document = Jsoup.parse(file, "gbk");
            Elements elements = document.getElementsByTag(tag);
            for (Element element : elements) {
                String name = element.getElementsByTag("name").text();
                String quantity = element.getElementsByTag("quantity").text();
                String unit = element.getElementsByTag("unit").text();
                result = result + name + " " + quantity + " " + unit + "\n";
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getXmlEQ(File file, String tag) {
        try {
            String result = "";
            org.jsoup.nodes.Document document = Jsoup.parse(file, "gbk");
            Elements elements = document.getElementsByTag(tag);
            for (Element element : elements) {
                String entityName = element.getElementsByTag("entityName").text();
                String relation = element.getElementsByTag("relation").text();
                String value = element.getElementsByTag("value").text();
                result = result + entityName + " " + relation + " " + value + "\n";
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getXmlQUE(File file, String tag) {
        try {
            String result = "";
            org.jsoup.nodes.Document document = Jsoup.parse(file, "gbk");
            Elements elements = document.getElementsByTag(tag);
            for (Element element : elements) {
                String description = element.getElementsByTag("description").text();
                String belong = element.getElementsByTag("belong").text();
                String name = element.getElementsByTag("name").text();
                result = result + description + " " + belong + " " + name + "\n";
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
