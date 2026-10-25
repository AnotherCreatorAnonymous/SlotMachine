/**
 * Figura que puede dibujar un simbolo. Existe para que cada tipo de simbolo
 * elija su propia forma sin que Symbol tenga que conocer las clases del
 * paquete shapes, que no comparten una interfaz comun.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public interface Figure {

    /** 
     * Mueve la figura a una posicion absoluta. 
    */
    void moveTo(int x, int y);

    /** 
     * Ajusta la figura a un cuadro de lado size. 
    */
    void changeSize(int size);

    /** 
     * Cambia el color de la figura. 
    */
    void changeColor(String color);

    /** 
     * Hace visible la figura. 
    */
    void makeVisible();

    /** 
     * Hace invisible la figura.  
    */
    void makeInvisible();
}