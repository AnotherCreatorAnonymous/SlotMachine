/**
 * Adapta Circle a la interfaz Figure. Es la forma de los simbolos normales.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class CircleFigure implements Figure {

    private Circle shape;

    /** 
     * Construye la figura con un circulo del paquete shapes. 
     */
    public CircleFigure() {
        shape = new Circle();
    }

    @Override
    public void moveTo(int x, int y) {
        shape.moveTo(x, y);
    }

    @Override
    public void changeSize(int size) {
        shape.changeSize(size);
    }

    @Override
    public void changeColor(String color) {
        shape.changeColor(color);
    }

    @Override
    public void makeVisible() {
        shape.makeVisible();
    }

    @Override
    public void makeInvisible() {
        shape.makeInvisible();
    }
}