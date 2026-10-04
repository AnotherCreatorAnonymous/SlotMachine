/**
 * Representa un simbolo de la maquina tragamonedas.
 * Cada simbolo se identifica por un color y tiene una figura visual.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public abstract class Symbol {
    private String color;
    private Circle shape;

    /**
     * Construye un simbolo en una posicion inicial.
     *
     * @param color color CSS del simbolo.
     * @param x posicion horizontal.
     * @param y posicion vertical.
     */
    protected Symbol(String color, int x, int y) {
        this.color = color;
        shape = new Circle();
        shape.changeColor(color);
        shape.moveTo(x, y);
    }

    // MC20, lo que cada tipo de simbolo puede cambiar

    /**
     * Retorna el nombre del tipo de simbolo.
     */
    public abstract String type();

    /**
     * Se invoca cada vez que gira la rueda que contiene este simbolo.
     * Por defecto no hace nada.
     */
    public void onSpin() {
    }

    /**
     * Se invoca cada vez que este simbolo queda seleccionado en su rueda.
     * Por defecto no hace nada.
     */
    public void onSelected() {
    }

    /**
     * Indica si el simbolo debe dibujarse cuando esta seleccionado.
     * Por defecto siempre se dibuja.
     */
    public boolean isShown() {
        return true;
    }

    /**
     * Ajusta el tamaño que pide la maquina al tamaño propio del simbolo.
     * Por defecto se respeta el tamaño pedido.
     *
     * @param size tamaño que pide la maquina.
     * @return tamaño con el que realmente se dibuja.
     */
    protected int scaled(int size) {
        return size;
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

    /**
     * Cambia el tamaño del simbolo.
     *
     * @param diameter nuevo diametro del simbolo.
     */
    public void changeSize(int diameter) {
        shape.changeSize(scaled(diameter));
    }
}
