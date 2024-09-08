package mvc.view;

/**
 * The AdvancedTextStockView extends the SimpleTextStockView and overrides the portfolioMenu
 * method to show the user the new key-binds for advanced portfolio operations.
 */
public class AdvancedTextStockView extends SimpleTextStockView {

  /**
   * Constructor for the viewer.
   * @param appendable object to track actions
   */
  public AdvancedTextStockView(Appendable appendable) {
    super(appendable);
  }

  @Override
  public void portfolioInstructionMenu() {
    writeMessage("\n||—————————————————— PORTFOLIO MENU ——————————————————||"
            + System.lineSeparator());
    writeMessage("- '1' : ADD PORTFOLIO           - '7' : DISTRIBUTION" + System.lineSeparator());
    writeMessage("- '2' : REMOVE PORTFOLIO        - '8' : SAVE" + System.lineSeparator());
    writeMessage("- '3' : BUY/SELL                - '9' : LOAD" + System.lineSeparator());
    writeMessage("- '4' : VALUE                   - 'a' : RE-BALANCE" + System.lineSeparator());
    writeMessage("- '5' : COMPOSITION             - 'b' : PLOT" + System.lineSeparator());
    writeMessage("- '6' : LIST ALL PORTFOLIOS     - 'm' : MAIN MENU" + System.lineSeparator());
  }


}
