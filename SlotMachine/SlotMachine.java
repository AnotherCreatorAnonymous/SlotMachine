import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Simula una maquina tragamonedas compuesta por una o mas ruedas.
 * Cada rueda contiene simbolos identificados por colores CSS.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 2.0
 */
public class SlotMachine {
    // MC1
    private static final int START_X = 50;
    private static final int Y = 100;
    private static final int SPACING = 80;
    private static final int NORMAL_SIZE = 30;
    private static final int JACKPOT_SIZE = 50;
    // MC11
    private static final int STEP_DELAY = 200;

    private static final int FRAME_MARGIN = 30;
    private static final String FRAME_COLOR = "darkGray";

    private Rectangle housing;

    private List<Wheel> wheels;
    private boolean isOK;
    private boolean visible;

    /**
     * Construye una maquina tragamonedas sin ruedas y en estado correcto.
     */
    public SlotMachine() {

        housing = new Rectangle();
        housing.changeColor(FRAME_COLOR);
        isOK = true;
        visible = false;
        wheels = new ArrayList<Wheel>();
    }


    /**
     * Construye una maquina de n ruedas, cada una con los mismos n simbolos,
     * y la deja en una configuracion aleatoria.
     *
     * @param n cantidad de ruedas y de simbolos por rueda
     */
    public SlotMachine(int n) {
        this();
        String[] palette = ColorHelper.palette(n);
        for (int i = 1; i <= n; i++) {
            addWheel(i);
            for (String color : palette) {
                addSymbol(i, color);
            }
        }
        spin();
    }

    /**
     * Construye una maquina de n ruedas, cada una con los mismos n simbolos,
     * y la deja en una configuracion aleatoria.
     *
     * @param n cantidad de ruedas y de simbolos por rueda
     */
    public SlotMachine(int n) {
        this();
        String[] palette = ColorHelper.palette(n);
        for (int i = 1; i <= n; i++) {
            addWheel(i);
            for (String color : palette) {
                addSymbol(i, color);
            }
        }
        spin();
    }

    /**
     * Agrega una nueva rueda a la maquina en la posicion deseada
     * 
     * @param pos indica la posicion de la rueda
     */
    public void addWheel(int pos) {
        int p = clamp(pos, wheels.size() + 1);
        wheels.add(p - 1, new Wheel());
        succeed();
    }

    /**
     * Eliminar una rueda de la maquina de una posicion deseada
     * 
     * @param pos indica la posicion de la rueda a eliminar
     */
    public void delWheel(int pos) {
        Wheel w = getFreeWheel(pos);
        if (w == null) 
            return;

        w.hide();
        wheels.remove(w);
        succeed();
    }

    /**
     * Añade un simbolo nuevo al final de la rueda en la posicion y del color indicado
     * 
     * @param pos   indica la rueda a la que se agregara el simbolo
     * @param color indica el color del nuevo simbolo (ej. "azure", "beige", "black",
     *              "blue", "brown", "chartreuse", "coral", "crimson", "cyan",
     *              "darkGray", "deepPink", "deepSkyBlue", "dodgerBlue", "firebrick",
     *              "forestGreen", "gold", "goldenRod", "gray", "green", "indigo",
     *              "khaki", "lavender", "lightCoral", "lightGray", "lime", "magenta",
     *              "maroon", "mediumSeaGreen", "midnightBlue", "navy", "olive",
     *              "orange", "orchid", "peru", "pink", "plum", "purple", "rosyBrown", 
     *              "salmon", "sandybrown", "seagreen", "sienna",
     *              "slateGray", "springGreen", "steelBlue", "teal", "tomato",
     *              "turquoise", "violet", "white", "yellow", "red")
     */
    public void addSymbol(int pos, String color) {
        Wheel w = getValidWheel(pos);
        if (w == null)
            return;

        if (!ColorHelper.isKnown(color)) {
            fail("El color indicado no existe.");
            return;
        }
        if (w.contains(color)) {
            fail("La rueda ya tiene un simbolo de ese color.");
            return;
        }

        w.addSymbol(w.size() + 1, color);
        succeed();
    }

    /**
     * Elimina un simbolo de la rueda
     * 
     * @param symbol color del simbolo a eliminar
     */
    public void delSymbol(String symbol) {
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).isLocked() && wheels.get(i).contains(symbol)) {
                fail("La rueda " + (i + 1) + " esta bloqueada y contiene ese simbolo.");
                return;
            }
        }
        boolean removed = false;
        for (Wheel w : wheels) {
            if (w.delSymbol(symbol)) {
                removed = true;
            }
        }
        if (removed) {
            succeed();
        } else {
            fail("El simbolo indicado no existe en ninguna rueda.");
        }
    }

    /**
     * Pone un simbolo en la rueda y en la rueda indicada
     * 
     * @param wheel  rueda donde poner el simbolo
     * @param symbol simbolo a poner en la rueda
     */
    public void placeSymbol(int wheel, String symbol) {
        Wheel w = getFreeWheel(wheel);
        if (w == null) {
            return;
        }

        if (w.setCurrentByColor(symbol)) {
            succeed();
        }
        else {
            fail("El simbolo indicado no existe en la rueda.");
        }
    }

    // MC3

    /**
     * Gira la rueda especificada
     * 
     * @param wheel indica la rueda a girar
     */
    public void spin(int wheel) {
        Wheel w = getSpinnableWheel(wheel);
        if (w == null)
            return;

        w.spin();
        succeed();
    }

    /**
     *  Gira todas las ruedas Siempre que todas esten disponibles. 
     *  Si alguna no esta disponible no hace nada.
     */
    public void spin() {
        if (!requireWheels())
            return;
        for (Wheel w : wheels) {
            if (w.isEmpty()) {
                fail("Una rueda esta vacia y no se puede girar.");
                return;
            }
            if (w.isLocked()) {
                fail("Una rueda esta bloqueada y no se puede girar.");
                return;
            }
        }
        for (Wheel w : wheels) {
            w.spin();
        }
        succeed();
    }

    // MC4

    /**
     * Retorna los colores de los simbolos de la maquina, rueda por rueda
     * de izquierda a derecha y en el orden en el que fueron agregados
     */
    public String[] symbols() {
        if (!requireWheels())
            return new String[0];
        return collectColors().toArray(new String[0]);
    }

    /**
     * Indica la cantidad de simbolos distintos que hay en la maquina.
     */
    public int distinctSymbols() {
        return new LinkedHashSet<>(collectColors()).size();
    }

    private List<String> collectColors() {
        List<String> all = new ArrayList<String>();
        for (Wheel w : wheels) {
            for (String color : w.allColors()) {
                all.add(color);
            }
        }
        return all;
    }

    /**
     * Retorna los colores de los simbolos visibles en todas las ruedas de la
     * maquina ordenados de izquierda a derecha
     */
    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).currentColor();
        }
        return config;
    }

    /**
     * Indica cuando hay un jackpot.
     */
    public boolean isJackpot() {
        String[] config = configuration();
        if (config.length == 0) {
            return false;
        }
        String first = config[0];
        if (first == null) {
            return false;
        }
        for (String color : config) {
            if (!first.equals(color)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hace visible la maquina, si ya es visible no hace nada
     */
    public void makeVisible() {
        if (!visible) {
            visible = true;
            succeed();
        }
    }

    /**
     * Hace invisible la maquina, si ya es invisible no hace nada
     */
    public void makeInvisible() {
        if (!visible)
            return;
        for (Wheel w : wheels) {
            w.hide();
        }
        housing.makeInvisible();
        visible = false;
        succeed();
    }

    // Mini-ciclo 7

    /**
     * Cierra el simulador
     */
    public void exit() {
        makeInvisible();
        wheels.clear();
        isOK = true;
    }

    /**
     * Indica si la ultima operacion se realizo correctamente
     */
    public boolean ok() {
        return isOK;
    }

    // helpers privados para los metodos publicos funcionen correctamente, para no
    // repetir codigo y para Mini-ciclo 6

    private int clamp(int pos, int max) {
        if (pos < 1)
            return 1;
        if (pos > max)
            return max;
        return pos;
    }

    private int validIndex(int pos) {
        return clamp(pos, wheels.size()) - 1;
    }

    private void succeed() {
        isOK = true;
        redraw();
    }

    private void fail(String message) {
        isOK = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Redibuja la maquina en su estado actual
     */
    private void redraw() {
        if (!visible)
            return;
        int size = isJackpot() ? JACKPOT_SIZE : NORMAL_SIZE;

        drawHousing(size);
        drawWheels(size);
    }

    private void drawHousing(int size){
        int slots = Math.max(wheels.size(), 1);
        int frameX = START_X - FRAME_MARGIN;
        int frameY = Y - FRAME_MARGIN;
        int frameWidth = (slots - 1) * SPACING + size + 2 * FRAME_MARGIN;
        int frameHeight = size + 2 * FRAME_MARGIN;

        Canvas.getCanvas().resize(frameX + frameWidth + FRAME_MARGIN,
                frameY + frameHeight + FRAME_MARGIN);

        housing.changeSize(frameHeight, frameWidth);
        housing.moveTo(frameX, frameY);
        if (wheels.isEmpty()) {
            housing.makeInvisible();
        } else {
            housing.makeVisible();
        }
    }

    private void drawWheels(int size){
        int x = START_X;
        for (Wheel w : wheels) {
            w.showAt(x, Y, size);
            x += SPACING;
        }
    }

    // MC8, terminar ciclo uno y hacer refactorizaciones minimas para mejorar
    // funcionamiento

    // MC9

    /**
     * Intercambia de posicion dos ruedas de la maquina
     *
     * @param wheel1 posicion de la primera rueda
     * @param wheel2 posicion de la segunda rueda
     */
    public void swap(int wheel1, int wheel2) {
        if (wheels.size() < 2) {
            fail("Se necesitan al menos dos ruedas para intercambiar.");
            return;
        }
        int i1 = validIndex(wheel1);
        int i2 = validIndex(wheel2);
        if (i1 == i2) {
            fail("Debe indicar dos ruedas diferentes.");
            return;
        }
        Wheel first = wheels.get(i1);
        Wheel second = wheels.get(i2);
        if (first.isLocked() || second.isLocked()) {
            fail("No se puede intercambiar una rueda fija.");
            return;
        }
        wheels.set(i1, second);
        wheels.set(i2, first);
        succeed();
    }

    // MC10

    /**
     * Bloquea una rueda para que no se mueva ni se intercambie
     *
     * @param wheel posicion de la rueda a bloquear
     * 
     */
    public void lock(int wheel) {
        Wheel w = getValidWheel(wheel);
        if (w == null)
            return;
        if (w.isLocked()) {
            fail("La rueda indicada ya esta bloqueada.");
            return;
        }

        w.lock();
        succeed();
    }

    /**
     * Desbloquea una rueda que estaba bloqueada
     *
     * @param wheel posicion de la rueda a desbloquear
     * 
     */
    public void unlock(int wheel) {
        Wheel w = getValidWheel(wheel);
        if (w == null)
            return;
        if (!w.isLocked()) {
            fail("La rueda indicada no esta bloqueada.");
            return;
        }

        w.unlock();
        succeed();
    }

    // MC11

    /**
     * Rota una rueda un numero de pasos. Si la maquina esta visible el
     * movimiento se muestra paso a paso. Los pasos negativos rotan al reves.
     *
     * @param wheel posicion de la rueda a rotar
     * @param steps cantidad de pasos a rotar
     */
    public void spin(int wheel, int steps) {
        Wheel w = getSpinnableWheel(wheel);
        if (w == null)
            return;
        
        int direction = steps < 0 ? -1 : 1;
        int total = Math.abs(steps);
        for (int i = 0; i < total; i++) {
            w.rotate(direction);
            if (visible) {
                redraw();
                Canvas.getCanvas().wait(STEP_DELAY);
            }
        }
        succeed();
    }

    // MC12

    /**
     * Deja la maquina en la configuracion indicada, un simbolo por rueda
     * de izquierda a derecha. Si algo no se puede cumplir no cambia nada.
     *
     * @param setSymbols colores que debe mostrar cada rueda
     */
    public void spin(String[] setSymbols) {
        if (!requireWheels())
            return;
        if (setSymbols == null || setSymbols.length != wheels.size()) {
            fail("La configuracion debe indicar un simbolo por cada rueda.");
            return;
        }

        for (int i = 0; i < wheels.size(); i++) {
            Wheel w = wheels.get(i);
            if (!w.contains(setSymbols[i])) {
                fail("La rueda " + (i + 1) + " no tiene el simbolo indicado.");
                return;
            }
            if (w.isLocked() && !setSymbols[i].equals(w.currentColor())) {
                fail("La rueda " + (i + 1) + " esta fija y no puede cambiar de simbolo.");
                return;
            }
        }
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setCurrentByColor(setSymbols[i]);
        }
        succeed();
    }

    // helper de validacion

    /**
     *  Validacion si la maquina tiene ruedas y retorna la rueda ajustada a los limites.
     *  Registra el fallo y retorna null si esta vacia.
     */
    private Wheel getValidWheel(int pos) {
        if (!requireWheels())
            return null;
        return wheels.get(validIndex(pos));
    }

    /**
     *  Retorna una rueda libre (no bloqueada) o null si no hay ruedas o la rueda indicada esta bloqueada.
     */
    private Wheel getFreeWheel(int pos) {
        Wheel w = getValidWheel(pos);
        if (w == null)
            return null;
        if (w.isLocked()) {
            fail("La rueda indicada esta bloqueada.");
            return null;
        }
        return w;
    }

    /**
     *  Retorna una rueda que se puede girar (no bloqueada y no vacia) 
     *  o null si no hay ruedas o la rueda indicada no se puede girar.
     */
    private Wheel getSpinnableWheel(int pos) {
        Wheel w = getFreeWheel(pos);
        if (w == null)
            return null;
        if (w.isEmpty()) {
            fail("La rueda indicada no tiene simbolos.");
            return null;
        }
        return w;
    }

    private boolean requireWheels() {
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return false;
        }
        return true;
    }
}