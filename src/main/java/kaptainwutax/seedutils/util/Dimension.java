package kaptainwutax.seedutils.util;

public enum Dimension {

    OVERWORLD("overworld", 0), NETHER("nether", 1), THE_END("the_end", -1);

    public final String name;
    public final int id;

    Dimension(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
