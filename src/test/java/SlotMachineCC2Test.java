import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad compartidas del curso para el ciclo 2.
 * Cada caso de prueba identifica a sus autores en el nombre del metodo.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @version 3.0
 */
public class SlotMachineCC2Test {

    private SlotMachine machine;

    /**
     * Prepara una maquina de tres ruedas con los simbolos red, blue y green.
     */
    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        for (int wheel = 1; wheel <= 3; wheel++) {
            machine.addSymbol(wheel, "red");
            machine.addSymbol(wheel, "blue");
            machine.addSymbol(wheel, "green");
        }
    }

    /**
     * Libera la maquina despues de cada prueba.
     */
    @After
    public void tearDown() {
        machine.exit();
        machine = null;
    }

    /**
     * Deberia conservar los simbolos de cada rueda despues de intercambiarlas
     * dos veces, es decir el intercambio debe ser reversible.
     */
    @Test
    public void accordingJcOmShouldRestoreTheConfigurationAfterSwappingTwice() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.swap(1, 3);
        machine.swap(1, 3);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.configuration());
    }

    /**
     * No deberia mover una rueda fija aunque se roten las demas ruedas,
     * mientras las demas si deben poder cambiar.
     */
    @Test
    public void accordingJcOmShouldNotMoveALockedWheelWhileSpinningTheMachine() {
        machine.spin(new String[]{"green", "red", "red"});
        machine.lock(2);
        machine.spin(1, 1);
        machine.spin(3, 2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "red", "green"}, machine.configuration());
    }

    /**
     * Deberia alcanzar el jackpot cuando se solicita una configuracion
     * con el mismo simbolo en todas las ruedas.
     */
    @Test
    public void accordingJcOmShouldReachTheJackpotWithAGivenConfiguration() {
        machine.spin(new String[]{"green", "green", "green"});
        assertTrue(machine.ok());
        assertTrue(machine.isJackpot());
    }

    /**
     * No deberia dejar la maquina en una configuracion incompleta.
     */
    @Test
    public void accordingJcOmShouldNotAcceptAnIncompleteConfiguration() {
        machine.spin(new String[]{"red", "blue"});
        assertFalse(machine.ok());
        assertEquals(3, machine.configuration().length);
    }
}