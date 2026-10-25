import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fabrica de ruedas con registro de tipos disponibles.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class WheelFactory {

    public static final String DEFAULT = "normal";

    private static final Map<String, WheelMaker> TYPES = new LinkedHashMap<>();

    static {
        register("normal", Wheel::new);
        register("lefty", LeftyWheel::new);
        register("rebel", RebelWheel::new);
        register("mirror", MirrorWheel::new);
    }

    public static Wheel create(String type) {
        WheelMaker maker = TYPES.get(type);
        return maker == null ? null : maker.make();
    }

    public static void register(String type, WheelMaker maker) {
        TYPES.put(type, maker);
    }

    public static boolean isKnown(String type) {
        return TYPES.containsKey(type);
    }
}
