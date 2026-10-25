/**
 * Simbolo timido: alterna visible/invisible cada vez que queda seleccionado.
 * Sigue contando para el jackpot aunque este escondido.
 *
 * @author Carlos Jimenez y Alejandro Ospina
 * @version 4.0
 */
public class ShySymbol extends Symbol {

    private boolean shown;

    public ShySymbol(String color, int x, int y) {
        super(color, x, y);
        shown = true;
    }

    @Override
    public String type() {
        return "shy";
    }

    @Override
    public void onSelected() {
        shown = !shown;
    }

    @Override
    public boolean isShown() {
        return shown;
    }
}
