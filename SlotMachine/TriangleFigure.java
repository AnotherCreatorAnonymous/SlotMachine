/**
 * Adapta Triangle a la interfaz Figure. Es la forma de los simbolos efimeros.
 * Triangle dibuja su vertice superior en la posicion indicada, asi que se
 * desplaza media base para que ocupe el mismo cuadro que un circulo.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class TriangleFigure implements Figure {

    private Triangle shape;
    private int size;

    /** 
     * Construye la figura con un triangulo del paquete shapes. 
     */
    public TriangleFigure() {
        shape = new Triangle();
        size = 0;
    }

    @Override
    public void moveTo(int x, int y) {
        shape.moveTo(x + size / 2, y);
    }

    @Override
    public void changeSize(int size) {
        this.size = size;
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