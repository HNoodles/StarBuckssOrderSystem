package fudan.se.lab4.dto;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private static final long serialVersionUID = 7600387145905184435L;
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
