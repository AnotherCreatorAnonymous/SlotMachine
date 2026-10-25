import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad compartidas del curso para el ciclo 4.
 * Cada caso de prueba identifica a sus autores en el nombre del metodo.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class SlotMachineCC4Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
    }

    @After
    public void tearDown() {
        machine.exit();
        machine = null;
    }

    /**
     * Una cadena de ruedas zurdas debe propagar el simbolo de la primera rueda
     * hacia la derecha a medida que cada una gira.
     */
    @Test
    public void accordingJcOmShouldPropagateSymbolAcrossChainOfLeftyWheels() {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("lefty", 3);

        for (int w = 1; w <= 3; w++) {
            machine.addSymbol(w, "red");
            machine.addSymbol(w, "blue");
            machine.addSymbol(w, "green");
        }

        machine.spin(new String[]{"green", "red", "blue"});
        machine.spin(2, 1);
        machine.spin(3, 1);

        assertTrue(machine.ok());
        assertTrue(machine.isJackpot());
        assertEquals("green", machine.configuration()[2]);
    }

    /**
     * Una rueda rebelde debe rechazar bloqueo, intercambio y eliminacion,
     * manteniendo intacto el estado de la maquina tras cada intento fallido.
     */
    @Test
    public void accordingJcOmShouldRejectAllForbiddenOperationsOnRebelWheel() {
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        for (int w = 1; w <= 2; w++) {
            machine.addSymbol(w, "red");
            machine.addSymbol(w, "blue");
        }

        machine.spin(new String[]{"red", "blue"});

        machine.lock(1);
        assertFalse(machine.ok());

        machine.swap(1, 2);
        assertFalse(machine.ok());

        machine.delWheel(1);
        assertFalse(machine.ok());

        assertEquals(2, machine.configuration().length);
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * Los simbolos timidos ocultos deben seguir aportando su color a la configuracion
     * y al conteo de jackpot sin alterarlo.
     */
    @Test
    public void accordingJcOmShouldMaintainJackpotWhenShySymbolHides() {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol("shy", 1, "gold");
        machine.addSymbol("normal", 1, "blue");
        machine.addSymbol("shy", 2, "gold");
        machine.addSymbol("normal", 2, "blue");

        machine.spin(new String[]{"gold", "gold"});
        assertTrue(machine.isJackpot());

        // Al cambiar y volver a colocar el simbolo timido, alterna a oculto
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(1, "gold");

        assertTrue(machine.isJackpot());
        assertEquals("gold", machine.configuration()[0]);
        assertEquals("gold", machine.configuration()[1]);
    }

    /**
     * La maquina debe rechazar tipos desconocidos de ruedas y simbolos sin
     * modificar el estado existente de la maquina.
     */
    @Test
    public void accordingJcOmShouldRejectUnknownTypesWithoutModifyingMachine() {
        machine.addWheel("voladora", 1);
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);

        machine.addWheel("normal", 1);
        assertTrue(machine.ok());

        machine.addSymbol("extraño", 1, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }
}
