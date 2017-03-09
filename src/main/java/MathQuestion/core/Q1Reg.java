package MathQuestion.core;

import MathQuestion.tntity.SO;
import MathQuestion.utils.GeneralTools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyu on 16-11-24.
 */
public class Q1Reg {
    private static Pattern pattern;
    private static Matcher matcher;

    public static List<SO> getSOT1Reg(String str) {
        List<SO> list=new ArrayList<SO>();
        //通过正则表达式匹配
        //正则表达式1
        pattern=Pattern.compile(".*其中(\\D*)有*(\\d*)\\D+，(\\D*)有*(\\d*)\\D+，(\\D*)有*(\\d*)\\D+，(\\D*)有*(\\d*)\\D+.*");
        matcher=pattern.matcher(str);
        if (matcher.find()) {
            for (int i=0;i<4;i++) {
                SO SO =new SO();
                SO.setName(matcher.group(1+2*i));
                SO.setQuantity(matcher.group(2+2*i));
                list.add(SO);
            }
            return list;
        }
        //正则表达式2
        pattern=Pattern.compile(".*其中(\\D*)有*(\\d*)\\D+，(\\D*)有*(\\d*)\\D+，(\\D*)有*(\\d*)\\D+.*");
        matcher=pattern.matcher(str);
        if (matcher.find()) {
            for (int i=0;i<3;i++) {
                SO SO =new SO();
                SO.setName(matcher.group(1+2*i));
                SO.setQuantity(matcher.group(2+2*i));
                list.add(SO);
            }
            return list;
        }
        //正则表达式3

        return null;
    }

    public static List<SO> getSOT2Reg(String str, String seq, String[] names) {
        List<SO> list=new ArrayList<SO>();
        //通过正则表达式匹配
        //正则表达式1
        int l=names.length;
        String reg=".*\\D";
        for (int i=0;i<l-1;i++){
            reg=reg+"([\\da-z]+)\\D?[、，,]";
        }
        reg=reg+"([\\da-z]+)\\D*.*";
        pattern=Pattern.compile(reg);
        matcher=pattern.matcher(str);
        if (matcher.find()) {
            for (int i=0;i<l;i++) {
                SO SO =new SO();
                SO.setName(names[i]);
                SO.setQuantity(matcher.group(i+1));
                list.add(SO);
            }
            return list;
        }

        //
        String[] ss=seq.split("[,，。,．、;；]");
        for (String name:names) {
            boolean flag=true;
            for (String s : ss) {
                if (s.contains(name)&&!(s.contains("抽")||s.contains("共")||s.contains("倍")||s.contains("样本"))) {
                    String posts=s.substring(s.lastIndexOf(name));
                    if (GeneralTools.countNum(posts)==1) {
                        SO SO =new SO();
                        SO.setName(name);
                        SO.setQuantity(GeneralTools.getNum(posts));
                        list.add(SO);
                        flag=false;
                        break;
                    }
                }
            }
            if (flag){
                SO SO =new SO();
                SO.setName(name);
                list.add(SO);
            }
        }
        return list;

    }



}
