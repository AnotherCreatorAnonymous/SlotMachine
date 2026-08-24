import java.util.List;
/**
 * Simula una maquina tragamonedas compuesta por una o mas ruedas.
 * Cada rueda contiene simbolos identificados por colores CSS.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 1.0
 */
public class SlotMachine
{
    // instance variables - replace the example below with your own
    private ArrayList<Wheel> wheels;
    private boolean isOK;

    /**
     * Construye una maquina tragamonedas sin ruedas y en estado correcto.
     */
    public SlotMachine()
    {
        // initialise instance variables
        isOK = true;
        wheels = new ArrayList<Wheel>();
    }

    /**
     * Agrega una nueva rueda a la maquina en la posicion deseada
     * 
     * @param pos indica la posicion de la rueda
     */
    public void addWheel(int pos){
        /* BORRAR DESPUES: validar y normalizar pos; crear una rueda nueva,
         * insertarla en wheels en la posicion indicada y actualizar isOK. */
    }
    
    /**
     *  Eliminar una rueda de la maquina de una posicion deseada
     *  
     *  @param pos indica la posicion de la rueda a eliminar
     */
    public void delWheel(int pos){
        /* BORRAR DESPUES: validar que exista una rueda en pos; eliminarla,
         * actualizar la representacion grafica y registrar el resultado en isOK. */
    }
    
    /**
     *  Añade un simbolo nuevo a la rueda en la posicion y del color indicado
     *  
     *  @param pos indica la posicion del simbolo en la rueda
     *  @param color indica el color del nuevo simbolo
     */
    public void addSymbol(int pos, String color){
        /* BORRAR DESPUES: validar color y pos; agregar el simbolo a la rueda
         * correspondiente en esa posicion y registrar el resultado en isOK. */
    }
    
    /**
     *  Elimina un simbolo de la rueda
     *  
        *  @param symbol color del simbolo a eliminar
     */
    public void delSymbol(String symbol){
        /* BORRAR DESPUES: buscar el color en las ruedas, eliminar sus
         * apariciones segun el diseño y registrar en isOK si la operacion fue posible. */
    }
    
    /**
     *  Pone un simbolo en la rueda y en la rueda indicada
     *  
     *  @param wheel rueda donde poner el simbolo
     *  @param symbol simbolo a poner en la rueda
     */
    public void placeSymbol(int wheel, String symbol){
        /* BORRAR DESPUES: validar la rueda y el simbolo; ubicar el simbolo
         * visible en la rueda indicada y redibujarla si la maquina es visible. */
    }
    
    /**
     *  Gira la rueda especificada
     *  
     *  @param wheel indica la rueda a girar
     */
    public void spin(int wheel){
        /* BORRAR DESPUES: validar la rueda; desplazar aleatoriamente su
         * posicion visible, animar el giro con shapes y actualizar isOK. */
    }
    
    /**
     *  Gira todas las ruedas
     */
    public void spin(){
        /* BORRAR DESPUES: girar todas las ruedas, conservar una operacion
         * consistente para la maquina completa y actualizar su apariencia. */
    }
    
    /**
     *  Retorna los colores de los simbolos en el orden en el que estan iniciando por el 1
     */
    public String symbols(){
        /* BORRAR DESPUES: consultar la primera rueda y retornar sus colores
         * en el orden de la rueda, comenzando por la posicion 1. */
        return "hola";
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
    public String configuration(){
        /* BORRAR DESPUES: consultar el simbolo visible de cada rueda y
         * retornar sus colores ordenados de izquierda a derecha. */
        return "Hola";
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
        /* BORRAR DESPUES: marcar la maquina como visible y hacer visibles
         * todas sus ruedas y simbolos mediante los componentes de shapes. */
    }
    
    /**
     *  Hace invisible la maquina, si ya es invisible no hace nada
     */
    public void makeInvisible(){
        /* BORRAR DESPUES: ocultar todas las figuras de la maquina y evitar
         * mostrar JOptionPane mientras la maquina este invisible. */
    }
    
    /**
     *  Cierra el simulador
     */
    public void exit(){
        /* BORRAR DESPUES: ocultar y liberar la representacion grafica de la
         * maquina, dejando registrado en isOK si el cierre fue exitoso. */
    }
    
    /**
     *  Indica si la ultima operacion se realizo correctamente
     */
    public boolean ok(){
        /* BORRAR DESPUES: retornar el valor de isOK, que debe representar
         * exclusivamente el resultado de la ultima operacion solicitada. */
        return true;
    }
}

