import java.util.ArrayList;
import java.util.List;

/**
 * Resuelve el problema de la maraton sobre una SlotMachine de n ruedas y n simbolos.
 *
 * La estrategia se llama radar diferencial. La rueda 1 se usa como antena: al
 * recorrerla por todas sus posiciones, el numero de simbolos distintos sube
 * exactamente en las posiciones que ninguna otra rueda esta ocupando, asi que
 * un barrido completo de la rueda 1 devuelve el mapa de ocupacion de las demas.
 * Despues, para ubicar una rueda concreta se le da un paso y se repite el
 * barrido: la unica casilla del mapa que cambia es la que esa rueda toco, y eso
 * la identifica sin ambiguedad. Conocida su posicion se lleva de un solo giro a
 * la casilla de la rueda 1.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 3.0
 */
public class SlotMachineContest {

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
        return play(machine, n);
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

    // Algoritmo de solución 

    /**
     * Toma el mapa inicial con la antena y luego ubica y recoge una por una
     * las ruedas 2..n sobre la casilla de la rueda 1.
     */
    private static int[][] play(SlotMachine machine, int n) {
        List<int[]> log = new ArrayList<int[]>();
        if (n < 2) {
            return toArray(log);
        }
        boolean[] map = radar(machine, n, log);
        for (int wheel = 2; wheel <= n; wheel++) {
            map = collect(machine, n, log, wheel, map);
        }
        return toArray(log);
    }

    /**
     * Barrido de la antena. Recorre la rueda 1 por sus n posiciones, anota
     * cuantos simbolos distintos se ven en cada una y la devuelve a su lugar.
     * El conteo es minimo en las casillas ocupadas por las ruedas 2..n y una
     * unidad mayor en las casillas libres.
     *
     * @return mapa de ocupacion de las ruedas 2..n, medido desde la rueda 1.
     */
    private static boolean[] radar(SlotMachine machine, int n, List<int[]> log) {
        int[] seen = new int[n];
        seen[0] = machine.distinctSymbols();
        for (int step = 1; step < n; step++) {
            rotate(machine, log, 1, 1);
            seen[step] = machine.distinctSymbols();
        }
        rotate(machine, log, 1, 1);

        int floor = seen[0];
        for (int step = 1; step < n; step++) {
            if (seen[step] < floor) {
                floor = seen[step];
            }
        }
        boolean[] map = new boolean[n];
        for (int step = 0; step < n; step++) {
            map[step] = seen[step] == floor;
        }
        return map;
    }

    /**
     * Ubica una rueda y la lleva a la casilla de la rueda 1.
     * Le da pasos de a uno y repite el barrido hasta que el mapa cambie:
     * la casilla que aparece es donde quedo la rueda y la que desaparece es
     * de donde salio, y cualquiera de las dos la identifica.
     *
     * @return el mapa de ocupacion ya actualizado.
     */
    private static boolean[] collect(SlotMachine machine, int n, List<int[]> log,
                                     int wheel, boolean[] map) {
        boolean[] before = map;
        int here = -1;
        boolean lonely = false;

        while (here < 0) {
            rotate(machine, log, wheel, 1);
            boolean[] after = radar(machine, n, log);
            int arrived = -1;
            int left = -1;
            for (int step = 0; step < n; step++) {
                if (!before[step] && after[step]) {
                    arrived = step;
                }
                if (before[step] && !after[step]) {
                    left = step;
                }
            }
            if (arrived >= 0) {
                here = arrived;
                lonely = true;
            } else if (left >= 0) {
                here = (left + 1) % n;
                lonely = false;
            }
            before = after;
        }

        rotate(machine, log, wheel, shortest(n - here, n));

        boolean[] next = new boolean[n];
        for (int step = 0; step < n; step++) {
            next[step] = before[step];
        }
        if (lonely) {
            next[here] = false;
        }
        next[0] = true;
        return next;
    }

    // helpers privados para la solución del algoritma

    /**
     * Traduce un giro a la vuelta equivalente mas corta, para que la maquina
     * gire por el lado que menos pasos necesita.
     */
    private static int shortest(int steps, int n) {
        int value = ((steps % n) + n) % n;
        return value > n / 2 ? value - n : value;
    }

    /**
     * Gira una rueda y registra la accion. Los giros de cero pasos no cuentan.
     */
    private static void rotate(SlotMachine machine, List<int[]> log, int wheel, int steps) {
        if (steps == 0) {
            return;
        }
        machine.spin(wheel, steps);
        log.add(new int[]{wheel, steps});
    }

    /**
     * Convierte la bitacora de acciones en el arreglo que exige el enunciado.
     */
    private static int[][] toArray(List<int[]> log) {
        int[][] actions = new int[log.size()][];
        for (int i = 0; i < log.size(); i++) {
            actions[i] = log.get(i);
        }
        return actions;
    }
}