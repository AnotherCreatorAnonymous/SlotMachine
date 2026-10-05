/**
 * Rueda zurda: si tiene vecina a la izquierda, al girar copia su estado.
 * Si no tiene vecina o el color de la vecina no existe en esta rueda,
 * gira como una normal.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class LeftyWheel extends Wheel {

    public LeftyWheel() {
        super();
    }

    @Override
    public String type() {
        return "lefty";
    }

    @Override
    protected String markColor() {
        return "dodgerBlue";
    }

    @Override
    public void rotate(int steps) {
        if (copyLeft()) {
            wearOut();
        } else {
            super.rotate(steps);
        }
    }

    @Override
    public void spin() {
        if (copyLeft()) {
            wearOut();
        } else {
            super.spin();
        }
    }

    private boolean copyLeft() {
        Wheel neighbour = leftWheel();
        if (neighbour == null) {
            return false;
        }
        String color = neighbour.currentColor();
        return color != null && setCurrentByColor(color);
    }
}
