import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Pruebas de unidad del ciclo 3 del simulador SlotMachine.
 * Cubre la paleta de colores, la maquina de n ruedas y casos limite del
 * solucionador. Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 5.0
 */
public class SlotMachineC3Test {

    // === MC13: palette y paletteSize ===

    /**
     * Deberia entregar n colores distintos y conocidos.
     */
    @Test
    public void shouldGiveNDistinctKnownColors() {
        String[] colors = ColorHelper.palette(10);
        Set<String> distinct = new HashSet<>();
        for (String color : colors) {
            assertTrue(ColorHelper.isKnown(color));
            distinct.add(color);
        }
        assertEquals(10, distinct.size());
    }

    /**
     * Deberia generar y registrar colores nuevos cuando la paleta base no alcanza.
     */
    @Test
    public void shouldGenerateColorsBeyondTheBasePalette() {
        int n = ColorHelper.paletteSize() + 5;
        String[] colors = ColorHelper.palette(n);
        Set<String> distinct = new HashSet<>();
        for (String color : colors) {
            assertTrue(ColorHelper.isKnown(color));
            distinct.add(color);
        }
        assertEquals(n, distinct.size());
    }

    /**
     * No deberia entregar colores cuando se piden cero o menos.
     */
    @Test
    public void shouldGiveNoColorsForNonPositiveSizes() {
        assertEquals(0, ColorHelper.palette(0).length);
        assertEquals(0, ColorHelper.palette(-4).length);
    }

    /**
     * Un color desconocido deberia resolverse como negro, y uno registrado
     * deberia poder usarse en la maquina.
     */
    @Test
    public void shouldRegisterNewColorsAtRuntime() {
        assertEquals(Color.black, ColorHelper.resolve("noExiste"));
        assertFalse(ColorHelper.isKnown("ecigarnet"));

        Color garnet = new Color(128, 0, 32);
        ColorHelper.register("ecigarnet", garnet);
        assertEquals(garnet, ColorHelper.resolve("ecigarnet"));

        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addSymbol(1, "ecigarnet");
        assertTrue(machine.ok());
        assertEquals("ecigarnet", machine.configuration()[0]);
        machine.exit();
    }

    // === MC14 y MC15: SlotMachine(n) y distinctSymbols ===

    /**
     * Deberia construir una maquina de n ruedas aunque n supere la paleta base.
     */
    @Test
    public void shouldCreateAMachineLargerThanTheBasePalette() {
        int n = ColorHelper.paletteSize() + 2;
        SlotMachine machine = new SlotMachine(n);
        assertTrue(machine.ok());
        assertEquals(n, machine.configuration().length);
        assertEquals(n * n, machine.symbols().length);
        assertTrue(machine.distinctSymbols() >= 1 && machine.distinctSymbols() <= n);
        machine.exit();
    }

    /**
     * Cada rueda de la maquina de n ruedas deberia tener los mismos n simbolos.
     */
    @Test
    public void shouldGiveEveryWheelTheSameSymbols() {
        SlotMachine machine = new SlotMachine(4);
        String[] all = machine.symbols();
        for (int i = 0; i < all.length; i++) {
            assertEquals(all[i % 4], all[i]);
        }
        machine.exit();
    }

    // === MC16 a MC18: solucionador ===

    /**
     * Una maquina de una rueda ya esta en jackpot y no necesita acciones.
     */
    @Test
    public void shouldNeedNoActionsWithOneWheel() {
        SlotMachine machine = new SlotMachine(1);
        int[][] actions = SlotMachineContest.solve(machine, 1);
        assertEquals(0, actions.length);
        assertTrue(machine.isJackpot());
        machine.exit();
    }

    /**
     * Deberia llegar al jackpot en la maquina mas pequeña con mas de una rueda.
     */
    @Test
    public void shouldReachTheJackpotWithTwoWheels() {
        for (int run = 0; run < 20; run++) {
            SlotMachine machine = new SlotMachine(2);
            SlotMachineContest.solve(machine, 2);
            assertTrue(machine.isJackpot());
            machine.exit();
        }
    }

    /**
     * Deberia llegar al jackpot de forma repetida, ya que la maquina empieza al azar.
     */
    @Test
    public void shouldReachTheJackpotRepeatedly() {
        for (int n = 3; n <= 12; n++) {
            for (int run = 0; run < 5; run++) {
                SlotMachine machine = new SlotMachine(n);
                SlotMachineContest.solve(machine, n);
                assertTrue("fallo con n = " + n, machine.isJackpot());
                machine.exit();
            }
        }
    }
}
