package MathQuestion;

import MathQuestion.core.Q1Analysis;
import MathQuestion.fencheng.fenchengEQReg;
import MathQuestion.fencheng.fenchengReg;
import MathQuestion.fencheng.fenchengSOTReg;
import MathQuestion.tntity.EQ;
import MathQuestion.tntity.QUE;
import MathQuestion.tntity.ResultEntity;
import MathQuestion.tntity.SO;

import MathQuestion.utils.DataProcess;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by liuxiaoyu on 17-2-23.
 * 测试类
 */
public class fenchengTest {


    //测试数据目录
    private String basedir="src/main/resources/testdata";
    //测试数据文件名
    private List<String> fileNames;
    //数据文件路径
    private String resources="src/main/resources";



    //获取测试数据
    private List<String> getTestData() {
        Set<String> set=new HashSet<String>();
        List<File> fileList= DataProcess.getFileNames(basedir);
        for (File file:fileList) {
            String name=file.getAbsolutePath();

            Pattern pattern=Pattern.compile("(.*testdata/.*)\\.,*");
            Matcher matcher=pattern.matcher(name);
            matcher.find();
            set.add(matcher.group(1));;
        }
        fileNames=new ArrayList<String>(set);

        Collections.sort(fileNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                String s1=o1;
                String s2=o2;
                int i1=s1.indexOf('-');
                int j1=s1.lastIndexOf("testdata/")+"testdata/".length();
                int s11=Integer.parseInt(s1.substring(j1,i1));
                int s12=Integer.parseInt(s1.substring(i1+1,s1.length()));
                int i2=s2.indexOf('-');
                int j2=s2.lastIndexOf("testdata/")+"testdata/".length();
                int s21=Integer.parseInt(s2.substring(j2,i2));
                int s22=Integer.parseInt(s2.substring(i2+1,s2.length()));
                if (s11!=s21){
                    if (s11<s21)
                        return -1;
                    if (s11==s21)
                        return 0;
                    else
                        return 1;
                }
                else {

                    if (s12<s22)
                        return -1;
                    if (s12==s22)
                        return 0;
                    else
                        return 1;
                }
            };
        });
        return fileNames;
    }

    //测试TS_Name
    public void TS_NameTest() {
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {
            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            questions= questions.split("\\n")[0];
            System.out.println(tag+" "+questions);
            String ResultTrue= DataProcess.getXmlObject2(new File(fileName + ".xml"), "TS", "name");


            String ts_name=reg.getTSName(questions);
            System.out.println("True "+ResultTrue);
            System.out.println(ts_name);
            System.out.println("--------------------------------------");


        }
    }

    //测试TS_Quantity
    public void TS_QuantityTest() {
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {
            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            questions= questions.split("\\n")[0];
            System.out.println(tag+" "+questions);
            String ResultTrue= DataProcess.getXmlObject2(new File(fileName + ".xml"), "TS", "quantity");

            List<SO> sotList=reg.getSOTResult(questions);
            System.out.println(sotList);

            String ts_quantity=reg.getTSQuantity(questions,sotList);
            System.out.println("True "+ResultTrue);
            System.out.println(ts_quantity);
            System.out.println("--------------------------------------");


        }
    }

    //测试unit
    public void UnitTest() {
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {
            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            questions= questions.split("\\n")[0];
            System.out.println(tag+" "+questions);
            String ResultTrue= DataProcess.getXmlObject2(new File(fileName + ".xml"), "TS", "unit");


            String unit=reg.getUnit(questions);
            System.out.println("True "+ResultTrue);
            System.out.println(unit);
            System.out.println("--------------------------------------");


        }
    }

    @Test
    //测试SOT
    public void SOTTest()  {
        fenchengSOTReg sotReg=new fenchengSOTReg();
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {

            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            System.out.println(tag+" "+questions);

            String sotResultTrue = DataProcess.getXmlS(new File(fileName + ".xml"), "SOT");
            System.out.println("--------------------------------------");
            System.out.println(sotResultTrue);

            List<SO> sotList=sotReg.getSOTResult(questions);
            System.out.println(sotList);
            System.out.println("--------------------------------------");
        }
    }


    //测试S
    public void STest() {

        fenchengSOTReg sotReg = new fenchengSOTReg();
        Q1Analysis q1Analysis = new Q1Analysis("src/main/resources");
        fileNames = getTestData();
        for (String fileName : fileNames) {

            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n", "");
            int i = fileName.lastIndexOf("Sampling/") + "Sampling/".length();
            String tag = fileName.substring(i, fileName.length());
            System.out.println(tag + " " + questions);

            String sResult = DataProcess.getXmlS(new File(fileName + ".xml"), "S");

            List<SO> sotList=sotReg.getSOTResult(questions);

            SO sResult2 = new fenchengReg("src/main/resources").getSResult(questions, sotList);
            System.out.print(sResult);
            System.out.println(sResult2);
            System.out.println("--------------------------------------");
        }
    }

    //测试SOS
    public void SOSTest()  {
        fenchengSOTReg sotReg=new fenchengSOTReg();
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {

            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            System.out.println(tag+" "+questions);

            String sosResultTrue = DataProcess.getXmlS(new File(fileName + ".xml"), "SOS");
            System.out.println(sosResultTrue);

            List<SO> sotList=sotReg.getSOTResult(questions);

            List<SO> sosList=reg.getSOSResult(questions,sotList);
            System.out.println(sosList);
            System.out.println("--------------------------------------");
        }
    }

    //测试EQ
    public  void EQTest() {
        fenchengSOTReg sotReg=new fenchengSOTReg();
        fenchengEQReg eqReg=new fenchengEQReg();
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {

            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            System.out.println(tag+" "+questions);

            String eqResultTrue = DataProcess.getXmlEQ(new File(fileName + ".xml"), "EQ");

            List<SO> sotList=sotReg.getSOTResult(questions);

            EQ eq=eqReg.getEQ(questions,sotList);
            System.out.println("True "+eqResultTrue);
            System.out.println(eq);
            System.out.println("--------------------------------------");
        }
    }


    //测试QUE
    public void QUETest() {
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        for (String fileName:fileNames) {
            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
//            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            questions= questions.split("\\n")[0];
            System.out.println(tag+" "+questions);

            String queResultTrue=DataProcess.getXmlQUE(new File(fileName+".xml"),"QUE");

            List<SO> sotList=reg.getSOTResult(questions);
            String tsname=reg.getTSName(questions);
            QUE que=reg.getQUE(questions,sotList,tsname);
//            System.out.println(DataProcess.getXmlObject2(new File(fileName+".xml"),"TS", "name"));
//            System.out.println(DataProcess.getXmlS(new File(fileName + ".xml"), "SOT").replaceAll("\\n",""));

            System.out.println("True "+queResultTrue);
            System.out.println(que);
            System.out.println("--------------------------------------");


        }
    }


    public void Test() {
        fenchengReg reg=new fenchengReg("src/main/resources");
        Q1Analysis q1Analysis=new Q1Analysis("src/main/resources");
        fileNames=getTestData();
        int count=0;
        for (String fileName:fileNames) {
            count++;
            String questions = DataProcess.readDocx(new File(fileName + ".docx"));
//            questions = questions.replace("\\n","");
            int i=fileName.lastIndexOf("Sampling/")+"Sampling/".length();
            String tag=fileName.substring(i,fileName.length());
            questions= questions.split("\\n")[0];
            System.out.println(count+"  "+tag+" "+questions);
            ResultEntity resultEntity=reg.getResult(questions);
            System.out.println(resultEntity);
            System.out.println("--------------------------------------");


        }
    }

}
