package model.proprietary;

import java.io.Serializable;

public class Proprietary implements Serializable {
    private final String name;
    private final String tin;

    public Proprietary(String name, String tin) {
        this.name = name;
        this.tin = tin;
    }

    public Proprietary(Proprietary that) {
        this.name = that.name;
        this.tin = that.tin;
    }

    public String getTin() {
        return tin;
    }

    @Override
    public Proprietary clone() {
        return new Proprietary(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[")
          .append(tin)
          .append("]  ")
          .append(name);
        return sb.toString();
    }
}
