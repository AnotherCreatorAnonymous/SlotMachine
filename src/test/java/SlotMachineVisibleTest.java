import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.awt.GraphicsEnvironment;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas del simulador en modo visible. Abren la ventana del simulador
 * unos segundos. Solo usan operaciones validas, para que no aparezcan
 * ventanas de error que detengan las pruebas. En un equipo sin pantalla
 * se omiten.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 5.0
 */
public class SlotMachineVisibleTest {

    /**
     * Omite las pruebas si no hay pantalla donde dibujar.
     */
    @Before
    public void requireScreen() {
        assumeFalse(GraphicsEnvironment.isHeadless());
    }

    /**
     * Deberia dibujar una maquina con todos los tipos de ruedas y simbolos
     * mientras se juega con ella.
     */
    @Test
    public void shouldPlayWithAllTypesWhileVisible() {
        SlotMachine machine = new SlotMachine();
        machine.makeVisible();
        assertTrue(machine.ok());

        String[] types = {"normal", "lefty", "mirror", "rebel"};
        for (int w = 1; w <= types.length; w++) {
            machine.addWheel(types[w - 1], w);
            machine.addSymbol("normal", w, "red");
            machine.addSymbol("shy", w, "blue");
            machine.addSymbol("ephemeral", w, "green");
        }
        assertTrue(machine.ok());

        machine.placeSymbol(1, "blue");
        assertEquals("blue", machine.configuration()[1]);
        machine.spin(3, 2);
        machine.spin(1);
        machine.spin();
        machine.lock(1);
        machine.unlock(1);
        machine.swap(1, 3);
        assertTrue(machine.ok());

        machine.spin(new String[]{"red", "red", "red", "red"});
        assertTrue(machine.isJackpot());

        machine.delSymbol("green");
        machine.delWheel(2);
        assertTrue(machine.ok());
        assertEquals(3, machine.configuration().length);

        machine.makeInvisible();
        assertTrue(machine.ok());
        machine.exit();
    }

    /**
     * Deberia resolver la maquina de la maraton mostrando cada paso.
     */
    @Test
    public void shouldSolveTheContestWhileVisible() {
        SlotMachine machine = new SlotMachine(3);
        machine.makeVisible();
        SlotMachineContest.solve(machine, 3);
        assertTrue(machine.isJackpot());
        machine.exit();

        SlotMachineContest.simulate(2);
    }
}
