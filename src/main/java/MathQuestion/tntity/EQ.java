package MathQuestion.tntity;

/**
 * Created by liuxiaoyu on 16-11-30.
 */
public class EQ {
    private String entityName=null;
    private String relation=null;
    private String value=null;

    public String getEntityName() {
        entityName=entityName.replaceAll("人数","");
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getEntityName()+" "+getRelation()+" "+getValue();
    }
}
