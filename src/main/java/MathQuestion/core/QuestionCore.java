package MathQuestion.core;

/**
 * Created by liuxiaoyu on 16-11-6.
 */

import MathQuestion.tntity.ResultEntity;
import MathQuestion.fencheng.fenchengReg;
import MathQuestion.tntity.EQ;
import MathQuestion.tntity.SO;

import java.util.List;

/**
 * 概率题目分成抽样语义标签提取
 */
public class QuestionCore {

    /**
     * 获取分成抽样题目XML标签
     * @param q1
     * @return
     */
    public static String getXML(String q1,String resources) {
        String q=q1.split("\n")[0];
        Q1Analysis q1Analysis=new Q1Analysis(resources);
        ResultEntity stratificationSampling=new fenchengReg(resources).getResult(q1);
        String result="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        result+="<Stratification>\n";
        result+="  <TS>\n";
        result+="    <name>"+stratificationSampling.getTSName()+"</name>\n";
        result+="    <quantity>"+stratificationSampling.getQuantity()+"</quantity>\n";
        result+="    <unit>"+stratificationSampling.getUnit()+"</unit>\n";
        result+="  </TS>\n";

        List<SO> sot=stratificationSampling.getSots();
        if (sot!=null) {
            for (SO so : sot) {
                result+="  <SOT>\n";
                result+="    <name>"+so.getName()+"</name>\n";
                result+="    <quantity>"+so.getQuantity()+"</quantity>\n";
                result+="    <unit>"+stratificationSampling.getUnit()+"</unit>\n";
                result+="  </SOT>\n";
            }
        }
        SO ss=stratificationSampling.getS();
        result+="  <S>\n";
        result+="    <name>"+ss.getName()+"</name>\n";
        result+="    <quantity>"+ss.getQuantity()+"</quantity>\n";
        result+="    <unit>"+stratificationSampling.getUnit()+"</unit>\n";
        result+="  </S>\n";

        List<SO> sos=stratificationSampling.getSoss();
        if (sos!=null) {
            for (SO so : sos) {
                result+="  <SOS>\n";
                result+="    <name>"+so.getName()+"</name>\n";
                result+="    <quantity>"+so.getQuantity()+"</quantity>\n";
                result+="    <unit>"+stratificationSampling.getUnit()+"</unit>\n";
                result+="  </SOS>\n";
            }
        }

        EQ eq=stratificationSampling.getEq();
        if (eq==null) {
            result+="  <EQ>\n";
            result+="  </EQ>\n";
        }
        else {
            result+="  <EQ>\n";
            result+="    <entityName>"+eq.getEntityName()+"</entityName>\n";
            result+="    <relation>"+eq.getRelation()+"</relation>\n";
            result+="    <value>"+eq.getValue()+"</value>\n";
            result+="  </EQ>\n";
        }


            result+="  <QUE>\n";
            result+="    <description>"+stratificationSampling.getDescription()+"</description>\n";
            result+="    <belong>"+stratificationSampling.getBelong()+"</belong>\n";
            result+="    <name>"+stratificationSampling.getName()+"</name>\n";
            result+="    <unit>"+stratificationSampling.getUnit()+"</unit>\n";
            result+="  </QUE>\n";


        result+="</Stratification>";
        return result;

    }

    public static void main(String[] args) {
        System.out.println(getXML("某社区现有480个住户，其中中等收入家庭200户、低收入家庭160户，其他为高收入家庭。在建设幸福广东的某次分层抽样调查中，高收入家庭被抽取了6户，则该社区本次被抽取的总户数为","src/main/resources"));
    }
}
