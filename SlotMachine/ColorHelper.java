import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para resolver nombres de colores (estándar CSS) a objetos Color.
 * Cumple el requisito de extensibilidad: agregar un color nuevo solo
 * requiere agregar una entrada al mapa, sin modificar el método que lo usa.
 */
public class ColorHelper {

    /** Mapa estático que asocia nombres de color CSS con objetos Color. */
    private static final Map<String, Color> CSS_COLORS = new HashMap<>();

    static {
        CSS_COLORS.put("red", Color.red);
        CSS_COLORS.put("black", Color.black);
        CSS_COLORS.put("blue", Color.blue);
        CSS_COLORS.put("yellow", Color.yellow);
        CSS_COLORS.put("green", Color.green);
        CSS_COLORS.put("magenta", Color.magenta);
        CSS_COLORS.put("white", Color.white);
        CSS_COLORS.put("cyan", Color.cyan);
        CSS_COLORS.put("orange", Color.orange);
        CSS_COLORS.put("gray", Color.gray);
        CSS_COLORS.put("darkGray", Color.darkGray);
        CSS_COLORS.put("lightGray", Color.lightGray);
        CSS_COLORS.put("pink", Color.pink);
        CSS_COLORS.put("sandybrown", new Color(244, 164, 96));
        CSS_COLORS.put("brown", new Color(139, 69, 19));
        CSS_COLORS.put("purple", new Color(128, 0, 128));
        CSS_COLORS.put("navy", new Color(0, 0, 128));
        CSS_COLORS.put("olive", new Color(128, 128, 0));
        CSS_COLORS.put("teal", new Color(0, 128, 128));
        CSS_COLORS.put("maroon", new Color(128, 0, 0));
        CSS_COLORS.put("gold", new Color(255, 215, 0));
        CSS_COLORS.put("salmon", new Color(250, 128, 114));
        CSS_COLORS.put("violet", new Color(238, 130, 238));
        CSS_COLORS.put("indigo", new Color(75, 0, 130));
        CSS_COLORS.put("turquoise", new Color(64, 224, 208));
        CSS_COLORS.put("lime", new Color(0, 255, 0));
        CSS_COLORS.put("orchid", new Color(218, 112, 214));
        CSS_COLORS.put("crimson", new Color(220, 20, 60));
        CSS_COLORS.put("coral", new Color(255, 127, 80));
        CSS_COLORS.put("khaki", new Color(240, 230, 140));
        CSS_COLORS.put("lavender", new Color(230, 230, 250));
        CSS_COLORS.put("beige", new Color(245, 245, 220));
        CSS_COLORS.put("azure", new Color(240, 255, 255));
        CSS_COLORS.put("chartreuse", new Color(127, 255, 0));
        CSS_COLORS.put("peru", new Color(205, 133, 63));
        CSS_COLORS.put("slateGray", new Color(112, 128, 144));
        CSS_COLORS.put("seagreen", new Color(46, 139, 87));
        CSS_COLORS.put("tomato", new Color(255, 99, 71));
        CSS_COLORS.put("deepPink", new Color(255, 20, 147));
        CSS_COLORS.put("forestGreen", new Color(34, 139, 34));
        CSS_COLORS.put("midnightBlue", new Color(25, 25, 112));
        CSS_COLORS.put("rosyBrown", new Color(188, 143, 143));
        CSS_COLORS.put("firebrick", new Color(178, 34, 34));
        CSS_COLORS.put("dodgerBlue", new Color(30, 144, 255));
        CSS_COLORS.put("sienna", new Color(160, 82, 45));
        CSS_COLORS.put("plum", new Color(221, 160, 221));
        CSS_COLORS.put("lightCoral", new Color(240, 128, 128));
        CSS_COLORS.put("mediumSeaGreen", new Color(60, 179, 113));
        CSS_COLORS.put("steelBlue", new Color(70, 130, 180));
        CSS_COLORS.put("deepSkyBlue", new Color(0, 191, 255));
        CSS_COLORS.put("springGreen", new Color(0, 255, 127));
        CSS_COLORS.put("goldenRod", new Color(218, 165, 32));
    }

    /**
     * Retorna el objeto Color asociado a un nombre de color CSS.
     * Si el nombre no existe en el mapa, retorna Color.black por defecto.
     *
     * @param colorString nombre del color en formato CSS.
     * @return el objeto Color correspondiente, o Color.black si no se encuentra.
     */
    public static Color resolve(String colorString) {
        return CSS_COLORS.getOrDefault(colorString, Color.black);
    }

    /**
     * Permite registrar o sobrescribir un color en tiempo de ejecución,
     * sin necesidad de modificar esta clase (extensibilidad real).
     *
     * @param name  nombre del color.
     * @param color objeto Color asociado.
     */
    public static void register(String name, Color color) {
        CSS_COLORS.put(name, color);
    }
}