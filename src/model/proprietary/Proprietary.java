package model.proprietary;

import java.io.Serializable;

public class Proprietary implements Serializable {
    private final String name;
    private final String tin;

    public Proprietary(String name, String tin) {
        this.name = name;
        this.tin = tin;
    }

    public String getTin() {
        return tin;
    }
}
