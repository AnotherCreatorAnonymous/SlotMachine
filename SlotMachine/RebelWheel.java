/**
 * Rueda rebelde: no se deja bloquear, ni intercambiar, ni eliminar.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class RebelWheel extends Wheel {

    public RebelWheel() {
        super();
    }

    @Override
    public String type() {
        return "rebel";
    }

    @Override
    protected String markColor() {
        return "crimson";
    }

    @Override
    public boolean canLock() {
        return false;
    }

    @Override
    public boolean canSwap() {
        return false;
    }

    @Override
    public boolean canDelete() {
        return false;
    }
}
