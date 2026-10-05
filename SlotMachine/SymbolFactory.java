import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fabrica de simbolos con registro de tipos disponibles.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class SymbolFactory {

    public static final String DEFAULT = "normal";

    private static final Map<String, SymbolMaker> TYPES = new LinkedHashMap<>();

    static {
        register("normal", (color) -> new Symbol(color, 0, 0));
        register("ephemeral", (color) -> new EphemeralSymbol(color, 0, 0));
        register("shy", (color) -> new ShySymbol(color, 0, 0));
    }

    public static Symbol create(String type, String color) {
        SymbolMaker maker = TYPES.get(type);
        return maker == null ? null : maker.make(color);
    }

    public static void register(String type, SymbolMaker maker) {
        TYPES.put(type, maker);
    }

    public static boolean isKnown(String type) {
        return TYPES.containsKey(type);
    }
}
