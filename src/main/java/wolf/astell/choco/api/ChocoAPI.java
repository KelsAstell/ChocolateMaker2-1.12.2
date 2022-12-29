package wolf.astell.choco.api;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChocoAPI {
    public static final Map<String, Brew> brewMap = new LinkedHashMap<>();
    public static Brew registerBrew(Brew brew) {
        brewMap.put(brew.getKey(), brew);
        return brew;
    }
}
