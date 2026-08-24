/**
 * Representa un simbolo de la maquina tragamonedas.
 * Cada simbolo se identifica por un color y tiene una figura visual.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 1.0
 */
public class Symbol {
    private String color;
    private Circle shape;

    /**
     * Construye un simbolo en una posicion inicial.
     *
     * @param color color CSS del simbolo.
     * @param x posicion horizontal.
     * @param y posicion vertical.
     */
    public Symbol(String color, int x, int y) {
        this.color = color;
        shape = new Circle();
        shape.changeColor(color);
        shape.moveTo(x, y);
    }

    /**
     * Retorna el color del simbolo.
     *
     * @return color CSS del simbolo.
     */
    public String getColor() {
        return color;
    }

    /**
     * Cambia el color del simbolo.
     *
     * @param color nuevo color CSS.
     */
    public void changeColor(String color) {
        this.color = color;
        shape.changeColor(color);
    }

    /**
     * Mueve el simbolo a una posicion absoluta.
     *
     * @param x nueva posicion horizontal.
     * @param y nueva posicion vertical.
     */
    public void moveTo(int x, int y) {
        shape.moveTo(x, y);
    }

    /**
     * Hace visible el simbolo.
     */
    public void makeVisible() {
        shape.makeVisible();
    }

    /**
     * Hace invisible el simbolo.
     */
    public void makeInvisible() {
        shape.makeInvisible();
    }

    /**
     * Indica si este simbolo tiene el color indicado.
     *
     * @param color color que se desea comparar.
     * @return true si los colores son iguales.
     */
    public boolean hasColor(String color) {
        return this.color.equals(color);
    }
}
