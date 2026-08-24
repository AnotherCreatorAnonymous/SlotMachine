import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    //metodos mini-ciclo2

    /**
     * Agrega un simbolo de un color a la rueda.
     *
     * @param color color CSS del simbolo que se agregara.
     */
    public void addSymbol(String color) {
        symbols.add(new Symbol(color, 0, 0));
    }

    /**
     * Elimina de la rueda el simbolo identificado por su color.
     *
     * @param symbol color CSS del simbolo que se eliminara.
     */
    public boolean delSymbol(String symbol) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(symbol)) {
                symbols.remove(i);
                if (current >= symbols.size()) {
                    current = Math.max(0, symbols.size() - 1);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Cambia el simbolo visible de la rueda al simbolo identificado por su color.
     *
     * @param color color CSS del simbolo que se mostrara.
     * @return true si el simbolo existe y se cambio, false en caso contrario.
     */
    public boolean setCurrentByColor(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(color)) {
                current = i;
                return true;
            }
        }
        return false;
    }


    /**
     * Indica si la rueda tiene simbolos.
     *
     * @return true si la rueda no tiene simbolos, false en caso contrario.
     */
    public boolean isEmpty() {
        return symbols.isEmpty();
    }

    /**
     * Retorna el color del simbolo visible de la rueda.
     *
     * @return color CSS del simbolo visible, o null si la rueda no tiene simbolos.
     */
    public String currentColor() {
        return symbols.isEmpty() ? null : symbols.get(current).getColor();
    }


    /**
     * Hace que todos los simbolos de la rueda sean invisibles.
     */
    public void hide() {
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
    }

    
    //  MC3 

    /**
     * Hace girar la rueda y cambia el simbolo visible a uno aleatorio.
     */
    public void spin() {
        if (symbols.isEmpty()) {
            return;
        }
        current = new Random().nextInt(symbols.size());
    }

    //  MC4

    /**
     * Retorna los colores de todos los simbolos de la rueda.
     *
     * @return arreglo con los colores de los simbolos.
     */
    public String[] allColors() {
        String[] result = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            result[i] = symbols.get(i).getColor();
        }
        return result;
    }

    //  MC5

    /**
     * Muestra el simbolo visible de la rueda en una posicion y tamaño especificos.
     *
     * @param x    posicion horizontal.
     * @param y    posicion vertical.
     * @param size diametro del simbolo.
     */
    public void showAt(int x, int y, int size) {
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
        if (!symbols.isEmpty()) {
            Symbol s = symbols.get(current);
            s.changeSize(size);
            s.moveTo(x, y);
            s.makeVisible();
        }
    }
}

