/**
 * Simbolo efimero: se encoge en cada giro de su rueda hasta quedar en un punto.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class EphemeralSymbol extends Symbol {

    private static final int LOSS = 20;
    private static final int DOT = 10;
    private static final int DOT_SIZE = 4;

    private int life;

    public EphemeralSymbol(String color, int x, int y) {
        super(color, x, y);
        life = 100;
    }

    @Override
    public String type() {
        return "ephemeral";
    }

    @Override
    public void onSpin() {
        life = Math.max(DOT, life - LOSS);
    }

    @Override
    protected int scaled(int size) {
        return Math.max(DOT_SIZE, size * life / 100);
    }

    /** Indica si el simbolo ya quedo reducido a un punto. */
    public boolean isDot() {
        return life == DOT;
    }
}
