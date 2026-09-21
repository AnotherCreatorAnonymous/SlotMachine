import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Pruebas de unidad compartidas del curso para el ciclo 3.
 * Cada caso de prueba identifica a sus autores en el nombre del metodo.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 3.0 
 */
public class SlotMachineCContestCTest {

    /**
     * Deberia ganar para cualquier tamaño de maquina entre tres y diez ruedas.
     */
    @Test
    public void accordingJcOmShouldWinForEveryMachineSizeFromThreeToTen() {
        for (int n = 3; n <= 10; n++) {
            SlotMachine machine = new SlotMachine(n);
            SlotMachineContest.solve(machine, n);
            assertTrue(machine.isJackpot());
            machine.exit();
        }
    }

    /**
     * Deberia ganar aunque la maquina se vuelva a crear muchas veces,
     * es decir sin depender de la configuracion inicial aleatoria.
     */
    @Test
    public void accordingJcOmShouldWinInEveryRandomInitialConfiguration() {
        for (int attempt = 0; attempt < 30; attempt++) {
            SlotMachine machine = new SlotMachine(6);
            SlotMachineContest.solve(machine, 6);
            assertTrue(machine.isJackpot());
            machine.exit();
        }
    }
}