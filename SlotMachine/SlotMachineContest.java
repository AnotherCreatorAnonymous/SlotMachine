import java.util.ArrayList;
import java.util.List;

/**
 * Resuelve el problema de la maraton sobre una SlotMachine de n ruedas y n simbolos.
 * La unica informacion que se puede leer de la maquina es distinctSymbols(),
 * es decir cuantos simbolos distintos se ven en este momento.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 3.0
 */
public class SlotMachineContest{

    /**
     * Resuelve una maquina de n ruedas y n simbolos sin mostrarla.
     * Cada accion es un par {rueda, pasos}.
     *
     * @param n cantidad de ruedas y de simbolos.
     * @return secuencia de acciones que llevan la maquina al jackpot.
     */
    public static int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();
        return solve(machine, n);
    }

    /**
     * Resuelve una maquina ya construida. Sirve para las pruebas de unidad,
     * que necesitan revisar el estado final de la maquina.
     *
     * @param machine maquina de n ruedas y n simbolos.
     * @param n cantidad de ruedas y de simbolos.
     * @return secuencia de acciones que llevan la maquina al jackpot.
     */
    public static int[][] solve(SlotMachine machine, int n) {
        return Jugar(machine, n);
    }

    /**
     * Resuelve la maquina mostrandola, para ver como se llega al jackpot.
     *
     * @param n cantidad de ruedas y de simbolos.
     */
    public static void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        solve(machine, n);
    }

}
