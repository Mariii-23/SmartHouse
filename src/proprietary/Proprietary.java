package proprietary;

public class Proprietary {
    private final String name;
    private final TIN tin;

    public Proprietary(String name, String tin) {
        this.name = name;
        this.tin = new TIN(tin);
    }
}
