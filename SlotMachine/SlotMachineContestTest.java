import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Pruebas de unidad del ciclo 3 del simulador SlotMachine.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 3.0
 */
public class SlotMachineContestTest {

    /**
     * Deberia construir una maquina con n ruedas y n simbolos por rueda.
     */
    @Test
    public void shouldCreateAMachineWithTheSameNumberOfWheelsAndSymbols() {
        SlotMachine machine = new SlotMachine(5);
        assertTrue(machine.ok());
        assertEquals(5, machine.configuration().length);
        assertEquals(25, machine.symbols().length);
        machine.exit();
    }

    /**
     * Deberia contar solo los simbolos que la maquina esta mostrando.
     */
    @Test
    public void shouldCountOnlyTheVisibleDistinctSymbols() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        for (int wheel = 1; wheel <= 2; wheel++) {
            machine.addSymbol(wheel, "red");
            machine.addSymbol(wheel, "blue");
        }
        assertEquals(1, machine.distinctSymbols());
        machine.spin(new String[]{"red", "blue"});
        assertEquals(2, machine.distinctSymbols());
        machine.exit();
    }

    /**
     * Deberia llegar al jackpot en la maquina mas pequena del enunciado.
     */
    @Test
    public void shouldReachTheJackpotWithThreeWheels() {
        SlotMachine machine = new SlotMachine(3);
        SlotMachineContest.solve(machine, 3);
        assertTrue(machine.isJackpot());
        machine.exit();
    }

    /**
     * Deberia llegar al jackpot en una maquina mediana.
     */
    @Test
    public void shouldReachTheJackpotWithTenWheels() {
        SlotMachine machine = new SlotMachine(10);
        SlotMachineContest.solve(machine, 10);
        assertTrue(machine.isJackpot());
        machine.exit();
    }

    /**
     * Deberia retornar acciones bien formadas: pares {rueda, pasos}
     * y ruedas dentro del rango de la maquina.
     */
    @Test
    public void shouldReturnWellFormedActions() {
        int[][] actions = SlotMachineContest.solve(6);
        assertTrue(actions.length > 0);
        for (int[] action : actions) {
            assertEquals(2, action.length);
            assertTrue(action[0] >= 1 && action[0] <= 6);
        }
    }

    /**
     * No deberia dañar una maquina que ya venia en jackpot.
     */
    @Test
    public void shouldKeepTheJackpotIfTheMachineAlreadyWon() {
        SlotMachine machine = new SlotMachine(4);
        String color = machine.configuration()[0];
        machine.spin(new String[]{color, color, color, color});
        SlotMachineContest.solve(machine, 4);
        assertTrue(machine.isJackpot());
        machine.exit();
    }

    /**
     * No deberia superar el limite de acciones del enunciado en el caso mas grande.
     */
    @Test
    public void shouldNotExceedTheActionLimit() {
        int[][] actions = SlotMachineContest.solve(50);
        assertTrue(actions.length <= 10000);
    }
}