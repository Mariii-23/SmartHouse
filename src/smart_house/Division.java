package smart_house;

public class Division {
    private final String name;

    public Division(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
