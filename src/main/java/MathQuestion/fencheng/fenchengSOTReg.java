package MathQuestion.fencheng;


import MathQuestion.tntity.SO;
import MathQuestion.utils.GeneralTools;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyu on 17-2-20.
 * 该类通过正则表达式抽取分成抽样问题SOT语义标签
 */

public class fenchengSOTReg {

    private static Pattern pattern;
    private static Matcher matcher;

    //模板列表4_1
    //name1,name2,name3,name4,quantity1,quantity2,quantity3,quantity4
    private String[] regList_4_1={
        "[\\u4e00-\\u9fa5]+?(.)、(.)、(.)、(.)[四4][\\u4e00-\\u9fa5]+分别有(\\d+)?、(\\d+)?、(\\d+)?、(\\d+)?",
            ".*(?:其中|且)([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)[、和及]([\\u4e00-\\u9fa5]+)分别有(\\d+)\\D、(\\d+)\\D、(\\d+)\\D、(\\d+)\\D",
    };
    //模板列表4_2
    //name1,quantity1,name2,quantity2,name3,quantity3,name4,quantity4
    private String[] regList_4_2={
            ".*其中([\\u4e00-\\u9fa5]+?)有?(\\d+).，([\\u4e00-\\u9fa5]+?)有?(\\d+).，([\\u4e00-\\u9fa5]+?)有?(\\d+).，([\\u4e00-\\u9fa5]+?)有?(\\d+|若干).[．，。]"
    };

    //模板列表3_1
    //name1,quantity1,name2,quantity2,name3,quantity3
    private String[] regList_3_1={
        ".*其中([\\u4e00-\\u9fa5]+?)有?(\\d+).，([\\u4e00-\\u9fa5]+?)有?(\\d+).，([\\u4e00-\\u9fa5]+?)有?(\\d+|若干).[．，。]",
        ".*(?:有|某学校)([\\u4e00-\\u9fa5]+)(\\d+).[、，]([\\u4e00-\\u9fa5]+)(\\d+).[、，]([\\u4e00-\\u9fa5]+)(\\d+|若干).[．。，、]",
            ".*分[三3][\\u4e00-\\u9fa5]+，([\\u4e00-\\u9fa5a-zA-Z]+)共有[\\u4e00-\\u9fa5]+(\\d+).，([\\u4e00-\\u9fa5a-zA-Z]+)共有[\\u4e00-\\u9fa5]+(\\d+).，([\\u4e00-\\u9fa5a-zA-Z]+)共有[\\u4e00-\\u9fa5]+(\\d+)",
            "[\\u4e00-\\u9fa5]+现有[\\u4e00-\\u9fa5\\d]+，其中([\\u4e00-\\u9fa5]+)(\\d+).[、，]([\\u4e00-\\u9fa5]+)(\\d+).[、，]其他为([\\u4e00-\\u9fa5]+)[。，](.*)"
    };
    //模板列表3_2
    //name1,name2,name3
    private String[] regList_3_2={
        ".*生产([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])[三3][^，。]+，[^，。]+数量之比",
            ".*分为([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])[三3][^，。]+，[^，。]+之比",
            ".*中学([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)学生人数之比"
    };
    //模板列表3_3
    //name1,name2,name3,quantity1,quantity2.quantity3
    private String[] regList_3_3={
        ".*有([\\u4e00-\\u9fa5]+)[、，]([\\u4e00-\\u9fa5]+)[、，]([\\u4e00-\\u9fa5]+)依次为(\\d+).[、，](\\d+).[、，](\\d+).[。，]",
        "某学院的([a-zA-Z])，([a-zA-Z])，([a-zA-Z]).*。已知该学院的A专业有(\\d+)名学生，" +
                "[a-zA-Z]专业有(\\d+)名学生，(.*)",
            "有([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])[、，]([\\u4e00-\\u9fa5]+|[a-zA-Z])三[\\u4e00-\\u9fa5]+，分别为(\\w+).，(\\w+).，(\\w+).，",
            ".*把[\\u4e00-\\u9fa5\\d]+分成([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)[三3].*分别为(\\d+)、(\\d+)、(\\d+)。",
            ".*已知[\\u4e00-\\u9fa5]+的([\\u4e00-\\u9fa5]+)、([\\u4e00-\\u9fa5]+)[、和及]([\\u4e00-\\u9fa5]+)分别有(\\d+).、(\\d+).、(\\d+)."

        };

    //模板列表2_1
    //name1,quantity1,name2,quantity2
    private String[] regList_2_1={
        "[\\u4e00-\\u9fa5]+有([\\u4e00-\\u9fa5]+)(\\d+).，([\\u4e00-\\u9fa5]+)(\\d+).，",
            ".*有(男生)(\\d+).[、，](女生)(\\d+)."
    };

    //获取SOT语义标签结果
    public List<SO> getSOTResult(String str) {
//        str=str.replace("万","0000");
        int regno;

        //模板列表4_1
        //name1,name2,name3,name4,quantity1,quantity2,quantity3,quantity4

        regno=0;
        for (String s:regList_4_1) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功4_1 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<4;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i));
                    sot.setQuantity(matcher.group(5+i));
                    sotList.add(sot);
                }
                return sotList;
            }
        }

        //模板列表4_2
        //name1,quantity1,name2,quantity2,name3,quantity3,name4,quantity4
        regno=0;
        for (String s:regList_4_2) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功3_1 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<4;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i*2));
                    if (i==3&&!GeneralTools.str_is_Num(matcher.group(2+i*2))) //str_is_Num 判断字符串是否为数字.如"4564"如数字
                        sot.setQuantity(null);
                    else
                        sot.setQuantity(matcher.group(2+i*2));
                    sotList.add(sot);
                }
                return sotList;
            }
        }


        //模板列表3_1
        //name1,quantity1,name2,quantity2,name3,quantity3
        regno=0;
        for (String s:regList_3_1) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功3_1 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<3;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i*2));
                    if (i==2&&!GeneralTools.str_is_Num(matcher.group(2+i*2))) //str_is_Num 判断字符串是否为数字.如"4564"如数字
                        sot.setQuantity(null);
                    else
                        sot.setQuantity(matcher.group(2+i*2));
                    sotList.add(sot);
                }
                return sotList;
            }
        }

        //模板列表3_2
        //name1,name2,name3
        regno=0;
        for (String s:regList_3_2) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功3_2 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<3;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i));
                    sot.setQuantity(null);
                    sotList.add(sot);
                }
                return sotList;
            }
        }

        //模板列表3_3
        //name1,name2,name3,quantity1,quantity2.quantity3
        regno=0;
        for (String s:regList_3_3) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功3_3 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<3;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i));
                    sot.setQuantity(matcher.group(4+i));
                    sotList.add(sot);
                }

                if (sotList.get(2).getQuantity().length()>5)
                    sotList.get(2).setQuantity(null);
                return sotList;
            }
        }

        //模板列表2_1
        //name1,quantity1,name2,quantity2
        regno=0;
        for (String s:regList_2_1) {
            regno++;
            pattern=pattern.compile(s);
            matcher=pattern.matcher(str);
            if (matcher.find()) {
                //模板处理
                System.out.println("匹配成功3_1 "+" "+regno);
                List<SO> sotList=new ArrayList<SO>();
                for (int i=0;i<2;i++) {
                    SO sot=new SO();
                    sot.setName(matcher.group(1+i*2));
                    sot.setQuantity(matcher.group(2+i*2));
                    sotList.add(sot);
                }
                return sotList;
            }
        }

        return null;
    }


        public static void main(String[] args) {
            String s="某社区对居民进行上海世博会知晓情况的分层抽样调查，已知该社区的青年人、中年人和老年人分别有800人、1600人、1400人，若在老年人中的抽样人数是70，则在中年人中的抽样人数应该是（    ）";
            s=s.replaceAll("\\n","");
            List<SO> sotlist=new fenchengSOTReg().getSOTResult(s);
            if (sotlist!=null)
                System.out.println(sotlist);
        }

}
