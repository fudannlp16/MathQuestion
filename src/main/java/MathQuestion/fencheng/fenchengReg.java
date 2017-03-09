package MathQuestion.fencheng;

import MathQuestion.core.Q1Analysis;
import MathQuestion.tntity.EQ;
import MathQuestion.tntity.QUE;
import MathQuestion.tntity.ResultEntity;
import MathQuestion.tntity.SO;
import MathQuestion.utils.GeneralTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaoyu on 17-2-28.
 * 获取分成抽样标签的核心类
 */
public class fenchengReg {

    private String resources;

    public fenchengReg(String resources) {
        q1Analysis=new Q1Analysis(resources);
    }

    private Q1Analysis q1Analysis;
    private fenchengSOTReg sotReg=new fenchengSOTReg();
    private fenchengEQReg eqReg=new fenchengEQReg();

    //TS_Quantity
    String[] tsquantityReg={"共有[\\u4e00-\\u9fa5]+(\\d+)","共有(\\d+)"};

    //QUE
    String[] que_No_Start={"此","则","那么","则此","则在","则从","则应","你认为","应在","应","那么从","其中","那么应当从","从"};
    String[] que_No_End={"（","_","？","_____","[     ]","["};
    String[] que_all={"从上述各层中依次抽取的人数分别是","应该如何抽取"};

    public ResultEntity getResult(String str) {

        String str_n=new String(str);
        str=str.replaceAll("\\n","");

        ResultEntity resultEntity=new ResultEntity();

        //SOT
        List<SO> sotList=getSOTResult(str);
        resultEntity.setSots(sotList);
        //TS_name
        String ts_name=getTSName(str);
        resultEntity.setTSName(ts_name);
        //TS_quantity
        String ts_quantity=getTSQuantity(str,sotList);
        resultEntity.setQuantity(ts_quantity);
        //S
        SO s=getSResult(str,sotList);
        resultEntity.setS(s);
        //SOS
        List<SO> sosList=getSOSResult(str,sotList);
        resultEntity.setSoss(sosList);
        //EQ
        EQ eq=getEQ(str,sotList);
        resultEntity.setEq(eq);
        //QUE
        QUE que=getQUE(str_n,sotList,ts_name);
        resultEntity.setDescription(que.getDescription());
        resultEntity.setBelong(que.getBelong());
        resultEntity.setName(que.getName());
        //unit
        String unit=getUnit(str);
        resultEntity.setUnit(unit);


        return resultEntity;
    };

    //TS_name
    public String getTSName(String str) {
        return q1Analysis.getTSNAME(str);
    }

    //TS_quantity
    public String getTSQuantity(String str,List<SO> sotList) {
        String[] results=str.split("[,，.。．;；]");
        str=results[0];

        boolean flag=false;
        for (int i=1;i<4;i++) {
            if (results.length<=1)
                break;
            if (results[i].length()==1) {
                str += results[i];
                flag=true;
            }
            else {
                if (flag)
                    str+=results[i];
                break;
            }
        }
        if (str.contains("样本"))
            return null;
        if (sotList!=null&&!str.contains("共")) {
            for (SO sot : sotList) {
                if (str.contains(sot.getName()))
                    return null;
            }
        }
        if (GeneralTools.countNum(str)==1)
            return GeneralTools.getNum(str);
        return null;
    }

    //unit
    public String getUnit(String str) {
        return q1Analysis.getUnit(str);
    }

    //SOT
    public List<SO> getSOTResult(String str) {
        List<SO> sotList=null;
        sotList=sotReg.getSOTResult(str);
        if (sotList!=null)
            return sotList;
        else {
            System.out.println("通过关键字匹配");
            return q1Analysis.getSOT(str);
        }
    }

    //S
    public SO getSResult(String str,List<SO> sotList) {
        SO sResult=new SO();
        sResult.setName("样本");
        String[] results=str.split("[,，.。．;；]");
        List<String> nameList=new ArrayList<String>();
        if (sotList!=null) {
            for (SO sot : sotList) {
                nameList.add(sot.getName());
            }
        }

        for (String s:results) {
            if (s.contains("抽")||s.contains("容量")) {
                boolean flag=true;
                for (String name:nameList) {
                    if (s.contains(name)) {
                        flag=false;
                        break;
                    }
                }
                if (!flag)
                    continue;
                String quantity=GeneralTools.getNum2(s);
                if (quantity!=null) {
                    sResult.setQuantity(quantity);
                    break;
                }

            }
        }
        return sResult;
    };

    //SOS
    public List<SO> getSOSResult(String str,List<SO> sotList) {
        if (sotList==null)
            return null;
        List<SO> sosList=new ArrayList<SO>();
        String[] results=str.split("[,，.。．;；]");
        for (SO sot:sotList) {
            SO sos=new SO();
            String name=sot.getName();
            sos.setName(name);

            for (String s:results) {

                if (s.contains("抽")||s.contains("样本")){
                    if (s.contains(name)) {
                        sos.setQuantity(GeneralTools.getNum(s));
                        continue;
                    }
                }
            }
            sosList.add(sos);

        }
        return sosList;
    }

    //EQ
    public EQ getEQ(String str,List<SO> sotList){
        return eqReg.getEQ(str,sotList);
    }

    //QUE
    public QUE getQUE(String str_n,List<SO> sotList,String tsname) {
        QUE que=new QUE();
        String str=str_n.split("\\n")[0];
        String[] results=str.split("[,，.。．;；]");
        String description=results[results.length-1];
        for (String s:que_No_Start) {
            if (description.startsWith(s)){
                description=description.substring(s.length());
                break;
            }
        }
        for (String s:que_No_End) {
            if (description.contains(s)) {
                description=description.substring(0,description.lastIndexOf(s));
                break;
            }
        }
        que.setDescription(description);

        for (String all_s:que_all) {

            if (description.startsWith(all_s) && sotList != null) {
                String name2 = "";
                for (int i = 0; i < sotList.size() - 1; i++)
                    name2 = name2 + sotList.get(i).getName() + ",";
                name2 = name2 + sotList.get(sotList.size() - 1).getName();
                que.setName(name2);
                que.setBelong("样本");
                return que;
            }
        }


        int count=0;
        String name="";
        if (sotList!=null) {
            boolean flag=false;
            for (SO so : sotList) {
                if ((so.getName().length()==1&&description.contains(so.getName().substring(0,1)))||
                        ((so.getName().length()!=1&&description.contains(so.getName().substring(0,2))))) {
                    flag=true;
                    count++;
                    name += so.getName() + ",";
                }
            }
            if (flag) {
                name = name.substring(0, name.length() - 1);

                if (count == sotList.size()) {
                    que.setBelong(tsname);
                } else que.setBelong("样本");
                que.setName(name);
                return que;
            }
        }

        if (description.contains(tsname)) {
            que.setName(tsname);
            que.setBelong(null);
            return que;
        }


        que.setName("样本");
        que.setBelong(null);
        return que;

    }

    public static void main(String[] args) {
        String s="某学校高一学生210人，高二学生270人、高三学生300人，校学生会用分层抽样的方法从这三个年级的学生中随机抽取n名学生进行问卷调查。如果已知从高一学生中抽取的人数为7，那么从高三中抽取的人数应为";
        s=s.replaceAll("\\n","");
        List<SO> sotList=new fenchengSOTReg().getSOTResult(s);
        System.out.println("结果");
        System.out.println(sotList);
        String tsname=new fenchengReg("src/main/resources").getTSName(s);
        System.out.println(new fenchengReg("src/main/resources").getQUE(s,sotList,tsname));
    }
}
