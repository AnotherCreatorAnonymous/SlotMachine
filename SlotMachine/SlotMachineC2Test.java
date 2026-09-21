import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad del ciclo 2 del simulador SlotMachine.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 3.0
 */
public class SlotMachineC2Test {

    private SlotMachine machine;

    /**
     * Prepara una maquina de dos ruedas, cada una con los simbolos
     * red, blue y green en ese orden. Al inicio ambas muestran red.
     */
    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        for (int wheel = 1; wheel <= 2; wheel++) {
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

    // === MC9: swap ===

    /**
     * Deberia intercambiar dos ruedas y con ello sus simbolos visibles.
     */
    @Test
    public void shouldSwapTwoWheels() {
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "red"}, machine.configuration());
    }

    /**
     * No deberia intercambiar una rueda consigo misma.
     */
    @Test
    public void shouldNotSwapAWheelWithItself() {
        machine.swap(2, 2);
        assertFalse(machine.ok());
    }

    /**
     * No deberia intercambiar cuando la maquina tiene menos de dos ruedas.
     */
    @Test
    public void shouldNotSwapWithLessThanTwoWheels() {
        SlotMachine small = new SlotMachine();
        small.addWheel(1);
        small.swap(1, 2);
        assertFalse(small.ok());
    }

    // === MC10: lock y unlock ===

    /**
     * Deberia fijar y despues soltar una rueda.
     */
    @Test
    public void shouldLockAndUnlockAWheel() {
        machine.lock(1);
        assertTrue(machine.ok());
        machine.unlock(1);
        assertTrue(machine.ok());
    }

    /**
     * No deberia fijar dos veces la misma rueda ni soltar una rueda suelta.
     */
    @Test
    public void shouldNotLockTwiceNorUnlockAFreeWheel() {
        machine.lock(1);
        machine.lock(1);
        assertFalse(machine.ok());
        machine.unlock(2);
        assertFalse(machine.ok());
    }

    /**
     * Una rueda fija no deberia cambiar de simbolo al girar toda la maquina.
     */
    @Test
    public void shouldKeepTheSymbolOfALockedWheelWhenSpinningAll() {
        machine.placeSymbol(1, "blue");
        machine.lock(1);
        for (int i = 0; i < 20; i++) {
            machine.spin();
        }
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * No deberia rotar una rueda fija.
     */
    @Test
    public void shouldNotRotateALockedWheel() {
        machine.lock(1);
        machine.spin(1, 2);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * No deberia intercambiar una rueda fija.
     */
    @Test
    public void shouldNotSwapALockedWheel() {
        machine.lock(1);
        machine.swap(1, 2);
        assertFalse(machine.ok());
    }

    /**
     * No deberia eliminar una rueda fija.
     */
    @Test
    public void shouldNotDeleteALockedWheel() {
        machine.lock(2);
        machine.delWheel(2);
        assertFalse(machine.ok());
        assertEquals(2, machine.configuration().length);
    }

    // === MC11: spin con pasos ===

    /**
     * Deberia rotar una rueda la cantidad exacta de pasos indicada.
     */
    @Test
    public void shouldRotateAWheelTheGivenNumberOfSteps() {
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        machine.spin(1, 1);
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * Deberia dar la vuelta completa cuando los pasos superan el numero de simbolos.
     */
    @Test
    public void shouldRotateInACircularWay() {
        machine.spin(1, 7);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Deberia rotar en sentido contrario con pasos negativos.
     */
    @Test
    public void shouldRotateBackwardsWithNegativeSteps() {
        machine.spin(1, -1);
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * No deberia rotar una rueda sin simbolos.
     */
    @Test
    public void shouldNotRotateAnEmptyWheel() {
        SlotMachine empty = new SlotMachine();
        empty.addWheel(1);
        empty.spin(1, 3);
        assertFalse(empty.ok());
    }

    // === MC12: configuracion dada ===

    /**
     * Deberia dejar la maquina en la configuracion solicitada.
     */
    @Test
    public void shouldLeaveTheMachineInAGivenConfiguration() {
        machine.spin(new String[]{"green", "blue"});
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "blue"}, machine.configuration());
    }

    /**
     * No deberia aceptar una configuracion con un numero de simbolos distinto
     * al numero de ruedas.
     */
    @Test
    public void shouldNotAcceptAConfigurationWithWrongSize() {
        machine.spin(new String[]{"green"});
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "red"}, machine.configuration());
    }

    /**
     * No deberia aceptar una configuracion con un simbolo que no existe,
     * y tampoco deberia cambiar parcialmente la maquina.
     */
    @Test
    public void shouldNotAcceptAConfigurationWithAnUnknownSymbol() {
        machine.spin(new String[]{"green", "purple"});
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "red"}, machine.configuration());
    }

    /**
     * No deberia cambiar el simbolo de una rueda fija con una configuracion dada.
     */
    @Test
    public void shouldNotChangeALockedWheelWithAGivenConfiguration() {
        machine.lock(1);
        machine.spin(new String[]{"green", "blue"});
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    // === MC8: consultas completadas ===

    /**
     * Deberia contar los simbolos visibles de toda la maquina.
     */
    @Test
    public void shouldCountTheDistinctSymbolsOfTheMachine() {
        assertEquals(1, machine.distinctSymbols());
        machine.spin(new String[]{"red", "blue"});
        assertEquals(2, machine.distinctSymbols());
    }
    /**
     * Deberia listar todos los simbolos de la maquina rueda por rueda.
     */
    @Test
    public void shouldListAllTheSymbolsOfTheMachine() {
        assertEquals(6, machine.symbols().length);
    }

    /**
     * Deberia reconocer el jackpot solo cuando todas las ruedas coinciden.
     */
    @Test
    public void shouldRecognizeTheJackpotOnlyWhenAllWheelsMatch() {
        machine.spin(new String[]{"blue", "blue"});
        assertTrue(machine.isJackpot());
        machine.spin(new String[]{"blue", "green"});
        assertFalse(machine.isJackpot());
    }

    /**
     * No deberia agregar un simbolo con un color que no existe.
     */
    @Test
    public void shouldNotAddASymbolWithAnUnknownColor() {
        machine.addSymbol(1, "purpel");
        assertFalse(machine.ok());
        assertEquals(6, machine.symbols().length);
    }

    /**
     * No deberia agregar un simbolo con color nulo.
     */
    @Test
    public void shouldNotAddASymbolWithANullColor() {
        machine.addSymbol(1, null);
        assertFalse(machine.ok());
        assertEquals(6, machine.symbols().length);
    }

    /**
     * No deberia repetir un color en la misma rueda.
     */
    @Test
    public void shouldNotAddARepeatedColorToTheSameWheel() {
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
        assertEquals(6, machine.symbols().length);
    }

    /**
     * Deberia permitir el mismo color en ruedas distintas.
     */
    @Test
    public void shouldAddTheSameColorToDifferentWheels() {
        machine.addWheel(3);
        machine.addSymbol(3, "red");
        assertTrue(machine.ok());
        assertEquals(7, machine.symbols().length);
    }

    /**
     * No deberia agregar un simbolo si la maquina no tiene ruedas.
     */
    @Test
    public void shouldNotAddASymbolWithoutWheels() {
        SlotMachine empty = new SlotMachine();
        empty.addSymbol(1, "red");
        assertFalse(empty.ok());
    }

    /**
     * No deberia borrar un simbolo que esta en una rueda bloqueada.
     */
    @Test
    public void shouldNotDeleteASymbolFromALockedWheel() {
        machine.lock(1);
        machine.delSymbol("red");
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "red"}, machine.configuration());
        assertEquals(6, machine.symbols().length);
    }

    /**
     * Deberia mantener el simbolo visible al borrar otro que estaba antes.
     */
    @Test
    public void shouldKeepTheVisibleSymbolWhenDeletingAnEarlierOne() {
        machine.addSymbol(1, "yellow");
        machine.placeSymbol(1, "blue");
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * No deberia girar una rueda sin simbolos.
     */
    @Test
    public void shouldNotSpinAWheelWithoutSymbols() {
        machine.addWheel(3);
        machine.spin(3);
        assertFalse(machine.ok());
    }

    /**
     * No deberia girar ninguna rueda si alguna esta bloqueada.
     */
    @Test
    public void shouldNotSpinAnyWheelIfOneIsLocked() {
        machine.lock(1);
        machine.spin();
        assertFalse(machine.ok());
    }
}