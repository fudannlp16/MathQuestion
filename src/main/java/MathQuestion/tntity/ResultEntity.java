package MathQuestion.tntity;

import java.util.List;

/**
 * Created by liuxiaoyu on 17-2-20.
 */
public class ResultEntity {

    private String TSName;
    private String quantity;
    private String unit;

    private List<SO> sots;
    private SO s;
    private List<SO> soss;

    private EQ eq;

    public SO getS() {
        return s;
    }

    public void setS(SO s) {
        this.s = s;
    }

    public EQ getEq() {
        return eq;
    }

    public void setEq(EQ eq) {
        this.eq = eq;
    }

    private String description;
    private String belong;
    private String name;

    public String getTSName() {
        return TSName;
    }

    public void setTSName(String TSName) {
        this.TSName = TSName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SO> getSots() {
        return sots;
    }

    public void setSots(List<SO> sots) {
        this.sots = sots;
    }

    public List<SO> getSoss() {
        return soss;
    }

    public void setSoss(List<SO> soss) {
        this.soss = soss;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String ts="name "+TSName+"\n"
                     +"quantity "+quantity+"\n"
                     +"unit"+unit+"\n"
                     +"\n";
        String sot="SOT\n";
        if(sots==null) {
            sot=sot+null+"\n";
        }
        else {
            for (SO sot1 : sots) {
                sot = sot + sot1.getName() + "\n"
                        + sot1.getQuantity() + "\n";
            }
        }

        String ss="\nS\n";
        if (s==null) {
            ss=ss+null+"\n";
        }
        else {
            ss=ss+s.getName()+"\n"
                    +s.getQuantity()+"\n";
        }

        String sos="\nSOS\n";
        if (soss==null) {
            sos=sos+null+"\n";
        }
        else {
            for (SO sos1 : soss) {
                sos = sos + sos1.getName() + "\n"
                        + sos1.getQuantity() + "\n";
            }
        }

        String eqs="\nEQ\n";
        if (eq==null) {
            eqs=eqs+null+"\n";
        }
        else {
            eqs=eq.getEntityName()+"\n"
                    +eq.getRelation()+"\n"
                    +eq.getValue()+"\n";
        }

        String que="\ndescription "+description+"\n"
                  +"belong "+belong+"\n"
                  +"name "+name+"\n";
        return ts+sot+ss+sos+eqs+que;
    }
}

