package proprietary;

public class TIN {
    private final String asString;

    public TIN(String tin) {
        this.asString = tin;
    }

    @Override
    public int hashCode() {
        return asString != null ? asString.hashCode() : 0;
    }
}
