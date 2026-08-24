import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Simula una maquina tragamonedas compuesta por una o mas ruedas.
 * Cada rueda contiene simbolos identificados por colores CSS.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 1.0
 */
public class SlotMachine
{
 // === MC1 ===
    private static final int START_X = 50;
    private static final int Y = 100;
    private static final int SPACING = 80;
    private static final int NORMAL_SIZE = 30;
    private static final int JACKPOT_SIZE = 50;

    private ArrayList<Wheel> wheels;
    private boolean isOK;
    private boolean visible;

    /**
     * Construye una maquina tragamonedas sin ruedas y en estado correcto.
     */
    public SlotMachine(){

        isOK = true;
        visible = false;
        wheels = new ArrayList<Wheel>();
    }

    /**
     * Agrega una nueva rueda a la maquina en la posicion deseada
     * 
     * @param pos indica la posicion de la rueda
     */
    public void addWheel(int pos){
        int p = clamp(pos, wheels.size() + 1);
        wheels.add(p - 1, new Wheel());
        succeed();
    }
    
    /**
     *  Eliminar una rueda de la maquina de una posicion deseada
     *  
     *  @param pos indica la posicion de la rueda a eliminar
     */
    public void delWheel(int pos){
        if (wheels.isEmpty()) {
            fail("No hay ruedas para eliminar.");
            return;
        }
        int p = clamp(pos, wheels.size());
        wheels.remove(p - 1);
        succeed();
    }
    
    /**
     *  Añade un simbolo nuevo a la rueda en la posicion y del color indicado
     *  
     *  @param pos indica la posicion del simbolo en la rueda
     *  @param color indica el color del nuevo simbolo
     */
    public void addSymbol(int pos, String color){
        if (wheels.isEmpty()) {
            fail("Debe existir al menos una rueda antes de agregar simbolos.");
            return;
        }
        int p = clamp(pos, wheels.size());
        wheels.get(p - 1).addSymbol(color);
        succeed();
    }
    
    /**
     *  Elimina un simbolo de la rueda
     *  
        *  @param symbol color del simbolo a eliminar
     */
    public void delSymbol(String symbol){
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
     *  Pone un simbolo en la rueda y en la rueda indicada
     *  
     *  @param wheel rueda donde poner el simbolo
     *  @param symbol simbolo a poner en la rueda
     */
    public void placeSymbol(int wheel, String symbol){
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        int p = clamp(wheel, wheels.size());
        if (wheels.get(p - 1).setCurrentByColor(symbol)) {
            succeed();
        } else {
            fail("El simbolo indicado no existe en la rueda.");
        }
    }
    
    // MC3

    /**
     *  Gira la rueda especificada
     *  
     *  @param wheel indica la rueda a girar
     */
    public void spin(int wheel){
         if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        int p = clamp(wheel, wheels.size());
        Wheel w = wheels.get(p - 1);
        if (w.isEmpty()) {
            fail("La rueda indicada no tiene simbolos.");
            return;
        }
        w.spin();
        succeed();
    }
    
    /**
     *  Gira todas las ruedas
     */
    public void spin(){
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        boolean any = false;
        for (Wheel w : wheels) {
            if (!w.isEmpty()) {
                w.spin();
                any = true;
            }
        }
        if (any) {
            succeed();
        } else {
            fail("Ninguna rueda tiene simbolos para girar.");
        }
    }

    // MC4
    
    /**
     *  Retorna los colores de los simbolos en el orden en el que estan iniciando por el 1
     */
    public String[] symbols(){
        if (wheels.isEmpty()) {
            return new String[0];
        }
        return wheels.get(0).allColors();
    }

    /**
     *  Indica la cantidad de simbolos distintos que hay en la maquina
     */
    public int distinctSymbols(){
        /* BORRAR DESPUES: reunir los colores existentes en todas las ruedas
         * sin repetirlos y retornar el numero de colores distintos. */
        return 1;
    }
    
    /**
     *  Retorna los colores de los simbolos visibles en todas las ruedas de la maquina ordenados de izquierda a derecha
     */
    public String[] configuration(){
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).currentColor();
        }
        return config;
    }
    
    /**
     *  Indica cuando hay un jackpot 
     */
    public boolean isJackpot(){
        /* BORRAR DESPUES: comparar la configuracion visible y retornar true
         * cuando todas las ruedas tengan el mismo simbolo; cambiar la
         * apariencia de la maquina al entrar o salir del estado ganador. */
        return false;
    }
    
    /**
     *  Hace visible la maquina, si ya es visible no hace nada
     */
    public void makeVisible(){
        if (visible) return;
        visible = true;
        redraw();
        succeed();
    }
    
    /**
     *  Hace invisible la maquina, si ya es invisible no hace nada
     */
    public void makeInvisible(){
        if (!visible) return;
        for (Wheel w : wheels) {
            w.hide();
        }
        visible = false;
        succeed();
    }
    
    // Mini-ciclo 7

    /**
     *  Cierra el simulador
     */
    public void exit(){
        for (Wheel w : wheels) {
            w.hide();
        }
        visible = false;
        isOK = true;
    }

    
    /**
     *  Indica si la ultima operacion se realizo correctamente
     */
    public boolean ok(){
        return isOK;
    }

    //helpers privados para los metodos publicos funcionen correctamente y para no repetir codigo y Mini-ciclo 6
    private int clamp(int pos, int max){
        if (pos < 1) return 1;
        if (pos > max) return max;
        return pos;
    }

    private void succeed(){
        isOK = true;
        //redraw();
    }

    private void fail(String message){
        isOK = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     *  Redibuja la maquina en su estado actual
     */
    private void redraw(){
        if (!visible) return;
        int size = isJackpot() ? JACKPOT_SIZE : NORMAL_SIZE;
        int x = START_X;
        for (Wheel w : wheels) {
            w.showAt(x, Y, size);
            x += SPACING;
        }
    }
}

