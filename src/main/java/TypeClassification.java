import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.fnlp.nlp.cn.CNFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyu on 16-9-6.
 */
public class TypeClassification {


    public static void main(String[] args) {
        TypeClassification typeClassification=new TypeClassification();
        String basePath=TypeClassification.class.getClassLoader().getResource("").getPath();
        List<String> stringList2=typeClassification.getSentence2(basePath+"/fromWindows/stratificationSampling");
        List<String> stringList=typeClassification.getSentence(basePath+"/fromWindows/classicalProbability");
        Map<String,Integer> stringSet=new HashMap<String, Integer>();
        for (int i=0;i<stringList.size()&&i<stringList2.size();i++) {
            stringSet.put(stringList.get(i),1);
            stringSet.put(stringList2.get(i),2);
        }

        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(basePath+"/Matrix.txt"));
            BufferedWriter writerMatrix = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
            BufferedOutputStream fos2= new BufferedOutputStream(new FileOutputStream(basePath+"/home/liuxiaoyu/文档/Category.txt"));
            BufferedWriter writerCategory=new BufferedWriter(new OutputStreamWriter(fos2,"utf-8"));
            Set<String> set=stringSet.keySet();

            Iterator<String> iterator=set.iterator();
            CNFactory factory = CNFactory.getInstance("models");
            System.out.println("-----------");
            int i=1;
            while (iterator.hasNext()) {
                String s=iterator.next();
                writerCategory.write(String.valueOf(stringSet.get(s)));
                writerCategory.newLine();
                StringBuilder stringBuilder=new StringBuilder();
                String[] words=factory.seg(s.replaceAll("\\s*", ""));
                for (String oneWord:words) {
                    Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
                    Matcher matcher=pattern.matcher(oneWord);
                    if (matcher.find()&&oneWord.length()>=2) {
                        pattern=Pattern.compile(".*[0-9].*");
                        matcher=pattern.matcher(oneWord);
                        if (!matcher.find()) {
                            System.out.println(oneWord);
                            stringBuilder.append(oneWord + " ");
                        }
                    }
                }
                writerMatrix.write(stringBuilder.toString().trim());
                writerMatrix.newLine();
            }
            writerMatrix.flush();
            writerCategory.flush();
            writerMatrix.close();
            writerCategory.close();


        }
        catch (Exception e) {
            e.printStackTrace();
        }





    }

    public String classify(String s) {
        if (s.contains("分层抽样"))
            return "分成抽样";
        else if (s.contains("抽取")) {
            if(s.contains("随机")||s.contains("任取"))
                return "古典概型";
            else {
                return "分成抽样";
            }

        }
        else
            return "古典概型";
    }


    /**
     * 从xml文件中抽取内容
     * @param file
     * @return
     */
    public String readXML(File file) {
         SAXReader saxReader=new SAXReader();
        saxReader.setEncoding("gbk");
        String content=null;
        try {
            Document document=saxReader.read(file);
            List<Node> list=document.selectNodes("comment()");
            Node node=list.get(0);
            content=node.getText();
            //System.out.println(content);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println(file.getName());
        }
        return content;
    }

    public String readDocx(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            fis.close();
            return doc1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getSentence2(String filePath) {
        List<String> stringList=new ArrayList<String>();
        File file=new File(filePath);
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            for (File file1:files) {
                if (file1.getName().contains("README"))
                    continue;
//                if (file1.getName().endsWith(".xml"))
//                    stringList.add(readXML(file1));
                else if(file1.getName().endsWith(".docx")) {
                    stringList.add(readDocx(file1));
                }
            }
        }
        return stringList;
    }

    public List<String> getSentence(String filePath) {
        List<String> stringList=new ArrayList<String>();
        File file=new File(filePath);
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            for (File file1:files) {
                if (file1.getName().contains("README"))
                    continue;
                if (file1.getName().endsWith(".xml"))
                    stringList.add(readXML(file1));
                else if(file1.getName().endsWith(".docx")) {
                    stringList.add(readDocx(file1));
                }
            }
        }
        return stringList;
    }
}
