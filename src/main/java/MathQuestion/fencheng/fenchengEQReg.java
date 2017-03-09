package MathQuestion.fencheng;

import MathQuestion.tntity.EQ;
import MathQuestion.tntity.SO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyu on 17-3-1.
 * 该类通过正则表达式抽取分成抽样问题SOT语义标签
 */
public class fenchengEQReg {

    //name1:name2:name3:name4 value1:value2:value3:value4
    private String[] eqReg_4={
            "之比依次为(\\d+)：(\\d+)：(\\d+)：(\\d+)",
            "之比为(\\d+)：(\\d+)：(\\d+)：(\\d+)"
    };
    //name1:name2:name3 value1:value2:value3
    private String[] eqReg_3={
            "之比依次为(\\d+)：(\\d+)：(\\d+)",
            "之比为(\\d+)：(\\d+)：(\\d+)"
    };

    //name1是name2的n倍
    private String[] eqReg_2_1={
            "([\\u4e00-\\u9fa5]+)是([\\u4e00-\\u9fa5]+)的(\\d+)倍"
    };
    //name1与name2相等
    private String[] eqReg_2_2={
            "([\\u4e00-\\u9fa5]+)[与和]([\\u4e00-\\u9fa5]+)相等"
    };

    //EQ
    public EQ getEQ(String str, List<SO> sotList) {
        if (sotList==null)
            return null;
        //name1:name2:name3:name4 value1:value2:value3:value4
        for (String reg:eqReg_4) {
            Pattern pattern=Pattern.compile(reg);
            Matcher matcher=pattern.matcher(str);
            if (matcher.find()) {
                EQ eq=new EQ();
                String entityName="";
                String value="";
                for (int i=0;i<3;i++) {
                    entityName+=sotList.get(i).getName()+",";
                    value+=matcher.group(i+1)+":";
                }
                entityName+=sotList.get(3).getName();
                value+=matcher.group(4);

                eq.setEntityName(entityName);
                eq.setRelation("比");
                eq.setValue(value);

                return eq;
            }
        }
        //name1:name2:name3 value1:value2:value3
        for (String reg:eqReg_3) {
            Pattern pattern=Pattern.compile(reg);
            Matcher matcher=pattern.matcher(str);
            if (matcher.find()) {
                EQ eq=new EQ();
                String entityName="";
                String value="";
                for (int i=0;i<2;i++) {
                    entityName+=sotList.get(i).getName()+",";
                    value+=matcher.group(i+1)+":";
                }
                entityName+=sotList.get(2).getName();
                value+=matcher.group(3);

                eq.setEntityName(entityName);
                eq.setRelation("比");
                eq.setValue(value);

                return eq;
            }
        }

        //name1是name2的n倍
        for (String reg:eqReg_2_1) {
            Pattern pattern=Pattern.compile(reg);
            Matcher matcher=pattern.matcher(str);
            if (matcher.find()) {
                EQ eq=new EQ();
                String entityName=matcher.group(1)+","+matcher.group(2);
                eq.setEntityName(entityName);
                eq.setRelation("倍");
                String value=matcher.group(3);
                eq.setValue(value);
                return eq;
            }

        }
        //name1与name2相等
        for (String reg:eqReg_2_2) {
            Pattern pattern=Pattern.compile(reg);
            Matcher matcher=pattern.matcher(str);
            if (matcher.find()) {
                EQ eq=new EQ();
                String entityName=matcher.group(1)+","+matcher.group(2);
                eq.setEntityName(entityName);
                eq.setRelation("倍");
                eq.setValue("1");
                return eq;
            }

        }


        return null;
    }
}
