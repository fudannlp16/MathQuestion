package MathQuestion.core;

import MathQuestion.WordSeg.WordSeg;
import MathQuestion.tntity.EQ;
import MathQuestion.tntity.QUE;
import MathQuestion.tntity.SO;
import MathQuestion.tntity.Word;
import MathQuestion.utils.GeneralTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyu on 16-11-21.
 */
public class Q1Analysis {

    private final String[] TSNAME={"产品","观众","居民","玩具","球","零件","职工","组委会","运动员","本科生",
    "总体","乳类商品","高校","树苗","某单位","住户"};
    private final String[] UNIT1={"件"};
    private final String[] UNIT={"名","件","个","家","所","棵","户","种","千克","辆","人"};

    private final String[] END={"（ ","为","是"};

    private final String[] NOTSNAME={"人","样本"};
    private final List<String> NOTSNAMELIST=Arrays.asList(NOTSNAME);

    private String resources;

    public Q1Analysis(String resources) {
        this.resources=resources;
    }


    /**
     * 获取TS/NAME
     * @param str
     * @return
     */
    public String getTSNAME(String str) {
        str=str.split("\n")[0];
        //method1
        for (String tsname:TSNAME) {
            if (str.contains(tsname))
                return tsname;
        }
        //method2
        if (str.contains("老师")&&str.contains("学生")) return "师生";
        if (str.contains("年级"))
            if (str.contains("高中生")||str.contains("本科生")) return "学生";
            else if (str.contains("高中")) if (str.contains("学生")) return "高中学生" ;else return "高中";
            else if (str.contains("中学")) return "中学";
            else return "学生";
        if (str.contains("男")&&str.contains("女"))
            return "学生";
        //method3
        List<Word> wordList= WordSeg.wordSegment(str,resources);
        List<String> nstrs=WordSeg.wordsSortByFreWithTag(wordList,"名词");
        for (String nst:nstrs) {
            if (NOTSNAMELIST.contains(nst))
                continue;
            else
                return nst;
        }
        return null;
    }

    /**
     * 获取TS/unit
     * @param str
     * @return
     */
    public String getUnit(String str)  {
        String str1=str.split("\n")[0];

        for (String unit:UNIT1) {
            if (str1.contains(unit)) {
                if (GeneralTools.getCount(str,unit)>=1)
                    return unit;
            }
        }

        for (String unit:UNIT) {
            if (str1.contains(unit)) {
                if (GeneralTools.getCount(str,unit)>3)
                    return unit;
            }
        }
        for (String unit:UNIT) {
            if (str1.contains(unit)) {
                if (GeneralTools.getCount(str,unit)>1)
                    return unit;
            }
        }

        return null;
    }

    /**
     * 获取SOT
     * @param str
     * @return
     */
    public List<SO> getSOT(String str) {
        List<SO> sot=null;
        sot=getSOT1(str);
        if (sot==null)
            sot=getSOT2(str);
        return sot;
    }

    /**
     * 获取SOT方法1,利用正则表达式
     * @param str
     * @return
     */
    public List<SO> getSOT1(String str) {
        //利用正则表达式分割字符串
        Pattern pattern=Pattern.compile("(.*)[,，.。．].*抽样.*");
        Matcher matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);
        pattern=Pattern.compile("(.*)[,.，。．]为.*");
        matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);
        pattern=Pattern.compile("(.*)[,，.。．].*抽取.*");
        matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);

        //正则表达式匹配
        return Q1Reg.getSOT1Reg(str);

    }


    /**
     * 获取SOT方法2，利用关键字匹配
     * @param str
     * @return
     */
    public List<SO> getSOT2(String str) {
        String seq=str;
        //利用正则表达式分割字符串
        Pattern pattern=Pattern.compile("(.*)[,，.。．].*抽样.*");
        Matcher matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);
        pattern=Pattern.compile("(.*)[,.，。．]为.*");
        matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);
        pattern=Pattern.compile("(.*)[,，.。．].*抽取.*");
        matcher=pattern.matcher(str);
        if (matcher.find())
            str=matcher.group(1);

        //读取关键字
        Scanner scanner=null;
        try {
            scanner=new Scanner(new File(resources+"/auxiliary/SOTKey.txt"),"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> list=new ArrayList<String>();
        while (scanner.hasNext()) {
            list.add(scanner.nextLine());
        }

//        List<String> list=Arrays.asList(SOTKey.split("\\n"));

        List<SO> SOList =new ArrayList<SO>();
        //利用关键字匹配
        for (String s:list) {
            String name1=s.split(":")[0];
            String name2=s.split(":")[1];
            String[] names=name2.split(",");
            boolean flag=true;
            for (String s1:names) {
                if (!seq.contains(s1))
                {
                    flag=false;
                    break;
                }
            }
            if (flag) {

                return Q1Reg.getSOT2Reg(str,seq,names);
            }
        }
        return null;

    }



}
