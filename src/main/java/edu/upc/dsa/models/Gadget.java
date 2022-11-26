package edu.upc.dsa.models;

public class Gadget {
    String id;
    int cost;
    String description;
    String unity_Shape;

    public Gadget() {}

    public Gadget(String id, int cost, String description, String unity_Shape) {
        this.setId(id);
        this.setCost(cost);
        this.setDescription(description);
        this.setUnity_Shape(unity_Shape);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnity_Shape() {
        return unity_Shape;
    }

    public void setUnity_Shape(String unity_Shape) {
        this.unity_Shape = unity_Shape;
    }
    @Override
    public String toString() {
        return "User [id="+id+", cost=" + cost + ", Description=" + description +",unity_Shape= "+ unity_Shape +"]";
    }
}
