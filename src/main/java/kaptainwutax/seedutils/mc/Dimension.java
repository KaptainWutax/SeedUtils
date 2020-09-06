package kaptainwutax.seedutils.mc;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Dimension {

    OVERWORLD("overworld", 0), NETHER("nether", 1), END("end", -1);

    private static Map<String, Dimension> STRING_TO_DIMENSION = Arrays.stream(values())
            .collect(Collectors.toMap(Dimension::toString, o -> o));

    private final String name;
    public final int id;

    Dimension(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Dimension fromString(String dim_name) {
        return STRING_TO_DIMENSION.get(dim_name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }
}
