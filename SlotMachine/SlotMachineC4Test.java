import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad para el ciclo 4 de SlotMachine.
 * Cubre los tipos de ruedas y simbolos, sus comportamientos y las fabricas.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class SlotMachineC4Test {

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

    private void populateWheel(int wheel) {
        machine.addSymbol(wheel, "red");
        machine.addSymbol(wheel, "blue");
        machine.addSymbol(wheel, "green");
    }

    /**
     * Deberia crear ruedas de los 4 tipos disponibles y mantener la configuracion.
     */
    @Test
    public void shouldCreateWheelsOfAllFourTypes() {
        machine.addWheel("normal", 1);
        assertTrue(machine.ok());
        machine.addWheel("lefty", 2);
        assertTrue(machine.ok());
        machine.addWheel("rebel", 3);
        assertTrue(machine.ok());
        machine.addWheel("mirror", 4);
        assertTrue(machine.ok());
        assertEquals(4, machine.configuration().length);
    }

    /**
     * No deberia crear una rueda de un tipo desconocido y la maquina debe quedar intacta.
     */
    @Test
    public void shouldRejectUnknownWheelType() {
        machine.addWheel("fantasma", 1);
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    /**
     * Una rueda lefty deberia copiar el color visible de su vecina izquierda al girar.
     */
    @Test
    public void shouldCopyLeftNeighbourWhenSpinningLeftyWheel() {
        machine.addWheel("normal", 1);
        populateWheel(1);
        machine.addWheel("lefty", 2);
        populateWheel(2);

        machine.placeSymbol(1, "green");
        machine.placeSymbol(2, "red");
        machine.spin(2, 1);

        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[1]);
        assertTrue(machine.isJackpot());
    }

    /**
     * Una rueda lefty sin vecina izquierda (posicion 1) deberia girar como una normal.
     */
    @Test
    public void shouldSpinNormallyWhenLeftyWheelHasNoLeftNeighbour() {
        machine.addWheel("lefty", 1);
        populateWheel(1);

        machine.placeSymbol(1, "red");
        machine.spin(1, 1);

        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Una rueda rebelde no deberia dejarse bloquear y debe poder girar normalmente.
     */
    @Test
    public void shouldNotLockARebelWheel() {
        machine.addWheel("rebel", 1);
        populateWheel(1);

        machine.lock(1);
        assertFalse(machine.ok());

        machine.placeSymbol(1, "red");
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Una rueda rebelde no deberia dejarse intercambiar de posicion.
     */
    @Test
    public void shouldNotSwapARebelWheel() {
        machine.addWheel("normal", 1);
        populateWheel(1);
        machine.addWheel("rebel", 2);
        populateWheel(2);

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);

        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * Una rueda rebelde no deberia dejarse eliminar.
     */
    @Test
    public void shouldNotDeleteARebelWheel() {
        machine.addWheel("normal", 1);
        populateWheel(1);
        machine.addWheel("rebel", 2);
        populateWheel(2);

        machine.delWheel(2);
        assertFalse(machine.ok());
        assertEquals(2, machine.configuration().length);

        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    /**
     * Una rueda mirror deberia invertir el sentido de rotacion pedido.
     */
    @Test
    public void shouldRotateInReverseWithMirrorWheel() {
        machine.addWheel("mirror", 1);
        populateWheel(1);

        machine.placeSymbol(1, "red");
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);

        machine.spin(1, -1);
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Deberia crear simbolos de los 3 tipos disponibles.
     */
    @Test
    public void shouldCreateSymbolsOfAllThreeTypes() {
        machine.addWheel(1);
        machine.addSymbol("normal", 1, "red");
        assertTrue(machine.ok());
        machine.addSymbol("ephemeral", 1, "blue");
        assertTrue(machine.ok());
        machine.addSymbol("shy", 1, "green");
        assertTrue(machine.ok());
        assertEquals(3, machine.symbols().length);
    }

    /**
     * No deberia crear un simbolo de un tipo no registrado.
     */
    @Test
    public void shouldRejectUnknownSymbolType() {
        machine.addWheel(1);
        machine.addSymbol("desconocido", 1, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Un simbolo efimero deberia encogerse en cada giro hasta el minimo establecido sin bajar de alli.
     */
    @Test
    public void shouldShrinkEphemeralSymbolOnEachSpinDownToMinimum() {
        EphemeralSymbol ephemeral = new EphemeralSymbol("red", 0, 0);
        assertFalse(ephemeral.isDot());

        for (int i = 0; i < 5; i++) {
            ephemeral.onSpin();
        }
        assertTrue(ephemeral.isDot());

        ephemeral.onSpin();
        assertTrue(ephemeral.isDot());
        assertEquals(4, ephemeral.scaled(30));
    }

    /**
     * Un simbolo timido deberia alternar su estado isShown() cada vez que se selecciona.
     */
    @Test
    public void shouldAlternateVisibilityOfShySymbolOnSelected() {
        ShySymbol shy = new ShySymbol("blue", 0, 0);
        assertTrue(shy.isShown());
        shy.onSelected();
        assertFalse(shy.isShown());
        shy.onSelected();
        assertTrue(shy.isShown());
    }

    /**
     * Un simbolo timido escondido sigue contando para la configuracion y para el jackpot.
     */
    @Test
    public void shouldCountHiddenShySymbolTowardsJackpot() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol("normal", 1, "blue");
        machine.addSymbol("shy", 2, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.spin(new String[]{"red", "red"});
        assertTrue(machine.ok());
        assertTrue(machine.isJackpot());

        // Alternar el simbolo timido de la rueda 1 para que quede escondido
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(1, "red");

        assertEquals("red", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
        assertTrue(machine.isJackpot());
    }

    /**
     * Deberia permitir mezclar diferentes tipos de ruedas y simbolos en una misma maquina.
     */
    @Test
    public void shouldMixWheelAndSymbolTypesInSameMachine() {
        machine.addWheel("rebel", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("mirror", 3);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 1, "blue");
        machine.addSymbol("shy", 2, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addSymbol("ephemeral", 3, "red");
        machine.addSymbol("shy", 3, "blue");

        machine.placeSymbol(1, "blue");
        machine.spin(2, 1);
        assertEquals("blue", machine.configuration()[1]);
        assertEquals(6, machine.symbols().length);
    }

    /**
     * WheelFactory y SymbolFactory deben reconocer tipos registrados y rechazar inventados.
     */
    @Test
    public void shouldRecognizeRegisteredTypesInFactories() {
        assertTrue(WheelFactory.isKnown("normal"));
        assertTrue(WheelFactory.isKnown("lefty"));
        assertTrue(WheelFactory.isKnown("rebel"));
        assertTrue(WheelFactory.isKnown("mirror"));
        assertFalse(WheelFactory.isKnown("inexistente"));

        assertTrue(SymbolFactory.isKnown("normal"));
        assertTrue(SymbolFactory.isKnown("ephemeral"));
        assertTrue(SymbolFactory.isKnown("shy"));
        assertFalse(SymbolFactory.isKnown("inexistente"));
    }
}
