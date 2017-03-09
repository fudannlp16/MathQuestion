package MathQuestion.tntity;

/**
 * Created by liuxiaoyu on 16-11-23.
 */
public class SO {
    private String name;
    private String quantity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        name=name.replaceAll("具有","");
        if (name.endsWith("的"))
            name=name.substring(0,name.length()-1);
        this.name=name;

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name+" "+quantity;
    }
}
