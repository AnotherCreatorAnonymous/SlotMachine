import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad del ciclo 1 del simulador SlotMachine.
 * Cubre la creacion de ruedas y simbolos, los giros, las consultas,
 * el manejo de errores y el cierre. Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 5.0
 */
public class SlotMachineC1Test {

    private SlotMachine machine;

    /**
     * Prepara una maquina vacia.
     */
    @Before
    public void setUp() {
        machine = new SlotMachine();
    }

    /**
     * Libera la maquina despues de cada prueba.
     */
    @After
    public void tearDown() {
        machine.exit();
        machine = null;
    }

    private void addFullWheel(int pos) {
        machine.addWheel(pos);
        machine.addSymbol(pos, "red");
        machine.addSymbol(pos, "blue");
        machine.addSymbol(pos, "green");
    }

    // === MC1: SlotMachine, addWheel, delWheel ===

    /**
     * Deberia crear una maquina sin ruedas y en estado correcto.
     */
    @Test
    public void shouldCreateAnEmptyMachine() {
        assertTrue(machine.ok());
        assertEquals(0, machine.configuration().length);
        assertFalse(machine.isJackpot());
    }

    /**
     * Deberia agregar ruedas al final cuando la posicion supera la cantidad de ruedas.
     */
    @Test
    public void shouldAppendAWheelWhenThePositionIsTooBig() {
        addFullWheel(1);
        machine.addWheel(99);
        assertTrue(machine.ok());
        machine.addSymbol(2, "blue");
        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());
    }

    /**
     * Deberia agregar la rueda al principio cuando la posicion es menor que 1.
     */
    @Test
    public void shouldInsertAWheelAtTheStartWhenThePositionIsTooSmall() {
        addFullWheel(1);
        machine.addWheel(-3);
        assertTrue(machine.ok());
        machine.addSymbol(1, "green");
        assertArrayEquals(new String[]{"green", "red"}, machine.configuration());
    }

    /**
     * Deberia eliminar una rueda y con ella sus simbolos.
     */
    @Test
    public void shouldDeleteAWheel() {
        addFullWheel(1);
        addFullWheel(2);
        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
        assertEquals(3, machine.symbols().length);
    }

    /**
     * No deberia eliminar una rueda de una maquina vacia.
     */
    @Test
    public void shouldNotDeleteAWheelFromAnEmptyMachine() {
        machine.delWheel(1);
        assertFalse(machine.ok());
    }

    // === MC2: addSymbol, delSymbol, placeSymbol ===

    /**
     * Deberia mostrar el primer simbolo que se agrega a una rueda.
     */
    @Test
    public void shouldShowTheFirstSymbolAddedToAWheel() {
        machine.addWheel(1);
        assertNull(machine.configuration()[0]);
        machine.addSymbol(1, "blue");
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Deberia eliminar un simbolo de todas las ruedas que lo tienen.
     */
    @Test
    public void shouldDeleteASymbolFromEveryWheel() {
        addFullWheel(1);
        addFullWheel(2);
        machine.delSymbol("blue");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "green", "red", "green"}, machine.symbols());
    }

    /**
     * No deberia eliminar un simbolo que no esta en ninguna rueda.
     */
    @Test
    public void shouldNotDeleteAMissingSymbol() {
        addFullWheel(1);
        machine.delSymbol("gold");
        assertFalse(machine.ok());
        assertEquals(3, machine.symbols().length);
    }

    /**
     * Deberia dejar visible el simbolo indicado en la rueda indicada.
     */
    @Test
    public void shouldPlaceASymbolInAWheel() {
        addFullWheel(1);
        machine.placeSymbol(1, "green");
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * No deberia poner un simbolo que la rueda no tiene.
     */
    @Test
    public void shouldNotPlaceAMissingSymbol() {
        addFullWheel(1);
        machine.placeSymbol(1, "gold");
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Deberia ajustar a los limites las posiciones de rueda fuera de rango.
     */
    @Test
    public void shouldClampWheelPositionsOutOfRange() {
        addFullWheel(1);
        addFullWheel(2);
        machine.placeSymbol(0, "blue");
        machine.placeSymbol(99, "green");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue", "green"}, machine.configuration());
    }

    // === MC3: spin(wheel), spin() ===

    /**
     * Deberia girar una rueda y dejar visible uno de sus simbolos.
     */
    @Test
    public void shouldSpinOneWheel() {
        addFullWheel(1);
        machine.spin(1);
        assertTrue(machine.ok());
        String shown = machine.configuration()[0];
        assertTrue("red".equals(shown) || "blue".equals(shown) || "green".equals(shown));
    }

    /**
     * Deberia girar todas las ruedas cuando todas estan disponibles.
     */
    @Test
    public void shouldSpinAllWheels() {
        addFullWheel(1);
        addFullWheel(2);
        machine.spin();
        assertTrue(machine.ok());
        assertEquals(2, machine.configuration().length);
    }

    /**
     * No deberia girar una maquina sin ruedas.
     */
    @Test
    public void shouldNotSpinAMachineWithoutWheels() {
        machine.spin();
        assertFalse(machine.ok());
        machine.spin(1);
        assertFalse(machine.ok());
    }

    // === MC4: symbols, distinctSymbols, configuration, isJackpot ===

    /**
     * Deberia listar los simbolos rueda por rueda en el orden en que se agregaron.
     */
    @Test
    public void shouldListTheSymbolsInOrder() {
        addFullWheel(1);
        machine.addWheel(2);
        machine.addSymbol(2, "gold");
        assertArrayEquals(new String[]{"red", "blue", "green", "gold"}, machine.symbols());
    }

    /**
     * No deberia listar simbolos de una maquina vacia.
     */
    @Test
    public void shouldNotListSymbolsOfAnEmptyMachine() {
        assertEquals(0, machine.symbols().length);
        assertFalse(machine.ok());
        assertEquals(0, machine.distinctSymbols());
    }

    /**
     * No deberia haber jackpot si alguna rueda no tiene simbolos.
     */
    @Test
    public void shouldNotBeJackpotWithAnEmptyWheel() {
        machine.addWheel(1);
        assertFalse(machine.isJackpot());
    }

    /**
     * Deberia haber jackpot cuando todas las ruedas muestran el mismo simbolo.
     */
    @Test
    public void shouldBeJackpotWhenAllWheelsShowTheSameSymbol() {
        addFullWheel(1);
        addFullWheel(2);
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(2, "blue");
        assertTrue(machine.isJackpot());
        assertEquals(1, machine.distinctSymbols());
    }

    // === MC5 a MC7: visibilidad, ok y exit ===

    /**
     * Hacer invisible una maquina que ya es invisible no deberia cambiar nada.
     */
    @Test
    public void shouldIgnoreMakeInvisibleWhenAlreadyInvisible() {
        addFullWheel(1);
        machine.makeInvisible();
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Una operacion correcta deberia dejar ok en verdadero despues de un error.
     */
    @Test
    public void shouldRecoverOkAfterAValidOperation() {
        machine.delWheel(1);
        assertFalse(machine.ok());
        machine.addWheel(1);
        assertTrue(machine.ok());
    }

    /**
     * Al salir la maquina deberia quedar sin ruedas y en estado correcto.
     */
    @Test
    public void shouldClearTheMachineOnExit() {
        addFullWheel(1);
        machine.delSymbol("gold");
        machine.exit();
        assertTrue(machine.ok());
        assertEquals(0, machine.configuration().length);
    }
}
