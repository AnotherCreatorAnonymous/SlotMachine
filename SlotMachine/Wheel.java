import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Representa una rueda con una secuencia de simbolos identificados por color.
 * La rueda tambien debe conservar cual simbolo se encuentra visible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */

public class Wheel {

    //  MC1 
    private List<Symbol> symbols;
    private int current;
    // MC10
    private boolean locked;
    private static final Random RANDOM = new Random();
    private Wheel left;
    // MC24
    private static final int MARK_HEIGHT = 6;
    private static final int MARK_GAP = 8;
    private Rectangle mark;

    /**
     * Construye una rueda sin simbolos y con una posicion visible inicial.
     */
    protected Wheel() {
        symbols = new ArrayList<>();
        current = 0;
        locked = false;
        left = null;
        mark = new Rectangle();
        mark.changeColor(markColor());
    }


    // MC21, lo que cada tipo de rueda puede cambiar

    /**
     * Retorna el nombre del tipo de rueda.
     */
    public String type() {
        return "normal";
    }

    /**
     * Color de la marca con la que esta rueda se reconoce en pantalla.
     */
    protected String markColor() {
        return "slateGray";
    }

    /**
     * Indica si esta rueda acepta que la bloqueen.
     */
    public boolean canLock() {
        return true;
    }

    /**
     * Indica si esta rueda acepta que la intercambien de posicion.
     */
    public boolean canSwap() {
        return true;
    }

    /**
     * Indica si esta rueda acepta que la eliminen de la maquina.
     */
    public boolean canDelete() {
        return true;
    }

    /**
     * Registra cual rueda queda a la izquierda de esta. La maquina la
     * actualiza cada vez que cambia el orden de las ruedas.
     *
     * @param left rueda vecina de la izquierda, o null si esta es la primera.
     */
    public void setLeft(Wheel left) {
        this.left = left;
    }

    /**
     * Retorna la rueda vecina de la izquierda, o null si no hay.
     */
    protected Wheel leftWheel() {
        return left;
    }    

    //metodos mini-ciclo2

    /**
     * Agrega un simbolo de un color a la rueda, en la posicion indicada.
     * Si la posicion es menor a 1 se usa 1; si es mayor al numero de
     * simbolos + 1, se usa el maximo (se agrega al final).
     *
     * @param pos   posicion donde se insertara el simbolo (1-based).
     * @param type  tipo de simbolo.
     * @param color color CSS del simbolo que se agregara.
     * 
     */
    public void addSymbol(int pos, String type, String color) {
        int p = pos < 1 ? 1 : Math.min(pos, symbols.size() + 1);
        symbols.add(p - 1, SymbolFactory.create(type, color));
    }

    /**
     * Retorna la cantidad de simbolos que tiene la rueda.
     */
    public int size() {
        return symbols.size();
    }

    /**
     * Elimina de la rueda el simbolo identificado por su color.
     *
     * @param symbol color CSS del simbolo que se eliminara.
     * @return true si el simbolo existia y se elimino.
     * 
     */
    public boolean delSymbol(String symbol) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(symbol)) {
                symbols.get(i).makeInvisible();
                symbols.remove(i);
                if (i < current) current--;
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
     * 
     */
    public boolean setCurrentByColor(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).hasColor(color)) {
                current = i;
                selected();
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
        mark.makeInvisible();
    }

    /**
     * Muestra todos los simbolos de la rueda.
     */
    public void show() {
        for (Symbol s : symbols) {
            s.makeVisible();
        }
    }

    
    //  MC3 

    /**
     * Hace girar la rueda y cambia el simbolo visible a uno aleatorio.
     */
    public void spin() {
        current = RANDOM.nextInt(symbols.size());
        selected();
        wearOut();
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
            if (s.isShown()) {
                s.makeVisible();
            }
        }
        mark.changeSize(MARK_HEIGHT, size);
        mark.moveTo(x, y + size + MARK_GAP);
        mark.makeVisible();
    }


    /**
     * Indica si la rueda contiene un simbolo del color indicado.
     *
     * @param color color CSS que se busca.
     * @return true si algun simbolo de la rueda tiene ese color.
     */
    public boolean contains(String color) {
        for (Symbol s : symbols) {
            if (s.hasColor(color)) {
                return true;
            }
        }
        return false;
    }


    // MC10

    /**
     * Bloquea la rueda para que no cambie de simbolo ni de posicion.
     */
    public void lock() {
        locked = true;
    }

    /**
     * Desbloquea la rueda para que vuelva a poder moverse.
     */
    public void unlock() {
        locked = false;
    }

    /**
     * Indica si la rueda esta Bloqueada.
     *
     * @return true si la rueda esta bloqueada.
     */
    public boolean isLocked() {
        return locked;
    }

    // MC11

    /**
     * Rota la rueda un numero de pasos. Los pasos negativos rotan en sentido contrario
     * y la rueda es circular, asi que nunca se sale del rango de simbolos.
     *
     * @param steps cantidad de pasos a rotar.
     */
    public void rotate(int steps) {
        if (symbols.isEmpty()) {
            return;
        }
        int n = symbols.size();
        current = ((current + steps) % n + n) % n;
        selected();
        wearOut();
    }


    // MC20: avisos a los simbolos

    /**
     * Le avisa al simbolo visible que quedo seleccionado.
     */
    protected void selected() {
        if (!symbols.isEmpty()) {
            symbols.get(current).onSelected();
        }
    }

    /**
     * Le avisa a todos los simbolos de la rueda que hubo un giro.
     */
    protected void wearOut() {
        for (Symbol s : symbols) {
            s.onSpin();
        }
    }

    /**
     * Retorna el tipo del simbolo visible, o null si la rueda esta vacia.
     */
    public String currentType() {
        return symbols.isEmpty() ? null : symbols.get(current).type();
    }

    /**
     * Retorna el simbolo visible, o null si la rueda esta vacia.
     */
    public Symbol currentSymbol() {
        return symbols.isEmpty() ? null : symbols.get(current);
    }

}

