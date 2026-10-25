/**
 * Adapta Rectangle a la interfaz Figure, usandolo siempre como cuadrado.
 * Es la forma de los simbolos timidos.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class SquareFigure implements Figure {

    private Rectangle shape;

    /** 
     * Construye la figura con un rectangulo del paquete shapes. 
    */
    public SquareFigure() {
        shape = new Rectangle();
    }

    @Override
    public void moveTo(int x, int y) {
        shape.moveTo(x, y);
    }

    @Override
    public void changeSize(int size) {
        shape.changeSize(size, size);
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