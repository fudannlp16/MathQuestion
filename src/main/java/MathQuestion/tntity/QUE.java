package MathQuestion.tntity;

/**
 * Created by liuxiaoyu on 16-11-30.
 */
public class QUE {
    private String description=null;
    private String belong=null;
    private String name=null;

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
        return getDescription()+" "+getBelong()+" "+getName();
    }
}
