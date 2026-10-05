/**
 * Rueda espejo: tipo propuesto (requisito 19). Invierte el sentido del giro.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class MirrorWheel extends Wheel {

    public MirrorWheel() {
        super();
    }

    @Override
    public String type() {
        return "mirror";
    }

    @Override
    protected String markColor() {
        return "gold";
    }

    @Override
    public void rotate(int steps) {
        super.rotate(-steps);
    }
}
