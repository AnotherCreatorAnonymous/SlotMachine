
/**
 * Write a description of class SlotMachine here.
 * 
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 1.0
 */
public class SlotMachine
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * Agrega una nueva rueda a la maquina en la posicion deseada
     * 
     * @param pos indica la posicion de la rueda
     */
    public void addWheel(int pos){
        
    }
    
    /**
     *  Eliminar una rueda de la maquina de una posicion deseada
     *  
     *  @param pos indica la posicion de la rueda a eliminar
     */
    public void delWheel(int pos){
        
    }
    
    /**
     *  Añade un simbolo nuevo a la rueda en la posicion y del color indicado
     *  
     *  @param pos indica la posicion del simbolo en la rueda
     *  @param color indica el color del nuevo simbolo
     */
    public void addSymbol(int pos, String color){
        
    }
    
    /**
     *  Elimina un simbolo de la rueda
     *  
     *  @symbol color del symbolo a eliminar
     */
    public void delSymbol(String symbol){
        
    }
    
    /**
     *  Pone un simbolo en la rueda y en la rueda indicada
     *  
     *  @param wheel rueda donde poner el simbolo
     *  @param symbol simbolo a poner en la rueda
     */
    public void placeSymbol(int wheel, String symbol){
        
    }
    
    /**
     *  Gira la rueda especificada
     *  
     *  @param wheel indica la rueda a girar
     */
    public void spin(int wheel){
        
    }
    
    /**
     *  Gira todas las ruedas
     */
    public void spin(){
        
    }
    
    /**
     *  Retorna los colores de los simbolos en el orden en el que estan iniciando por el 1
     */
    public String symbols(){
        return "hola";
    }

    /**
     *  Indica la cantidad de simbolos distintos que hay en la maquina
     */
    public int distinctSymbols(){
        return 1;
    }
    
    /**
     *  Retorna los colores de los simbolos visibles en todas las ruedas de la maquina ordenados de izquierda a derecha
     */
    public String configuration(){
        return "Hola";
    }
    
    /**
     *  Indica cuando hay un jackpot 
     */
    public boolean isJackpot(){
        return false;
    }
    
    /**
     *  Hace visible la maquina, si ya es visible no hace nada
     */
    public void isVisible(){
        
    }
    
    /**
     *  Hace invisible la maquina, si ya es invisible no hace nada
     */
    public void isInvisible(){
        
    }
    
    /**
     *  Cierra el simulador
     */
    public void exit(){
        
    }
    
    /**
     *  Indica si la ultima operacion se realizo correctamente
     */
    public boolean ok(){
        return true;
    }
}

