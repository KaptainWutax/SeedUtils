package kaptainwutax.seedutils.mc;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Dimension {

    OVERWORLD("overworld", 0), NETHER("nether", 1), END("end", -1);

    private static final Map<String, Dimension> STRING_TO_DIMENSION = Arrays.stream(values())
            .collect(Collectors.toMap(Dimension::toString, o -> o));

    public final String dim_name;
    public final int id;

    Dimension(String dim_name, int id) {
        this.dim_name = dim_name;
        this.id = id;
    }

    public static Dimension fromString(String dim_name) {
        return STRING_TO_DIMENSION.get(dim_name);
    }

    @Override
    public String toString() {
        return this.dim_name;
    }

}
