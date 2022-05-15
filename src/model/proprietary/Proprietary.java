package model.proprietary;

public class Proprietary {
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
