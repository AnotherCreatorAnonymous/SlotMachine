import java.util.ArrayList;
import java.util.List;
/**
 * Representa una rueda con una secuencia de simbolos identificados por color.
 * La rueda tambien debe conservar cual simbolo se encuentra visible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 1.0
 */
public class Wheel {

    //  MC1 
    private List<Symbol> symbols;
    private int current;

    /**
     * Construye una rueda sin simbolos y con una posicion visible inicial.
     */
    public Wheel() {
        symbols = new ArrayList<>();
        current = 0;
    }

    /**
     * Agrega un simbolo de un color a la rueda.
     *
     * @param color color CSS del simbolo que se agregara.
     */
    public void addSymbol(String color) {
        /* BORRAR DESPUES: validar que color sea valido y no este repetido;
         * agregarlo a symbols conservando el orden de la rueda. */
    }

    /**
     * Elimina de la rueda el simbolo identificado por su color.
     *
     * @param symbol color CSS del simbolo que se eliminara.
     */
    public void delSymbol(String symbol) {
        /* BORRAR DESPUES: localizar symbol, eliminarlo de symbols y ajustar
         * la posicion visible si el simbolo eliminado era el actual. */
    }
}
