package mvc.view;

import java.io.IOException;

/**
 * The {@code mvc.view.TextStockView} class represent the view,
 * in the MVC architecture of this program.
 * The view handles all displaying of results and instructions to end user of the program.
 */
public class SimpleTextStockView implements TextStockView {
  private final Appendable appendable;

  /**
   * Constructor for the viewer.
   * @param appendable object to track actions
   */
  public SimpleTextStockView(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void stockInstructionsMenu() {
    writeMessage("\n||————————————————— STOCKS MENU —————————————————||" + System.lineSeparator());
    writeMessage("- '1' : GAIN/LOSS CALCULATOR" + System.lineSeparator());
    writeMessage("- '2' : X-DAY MOVING AVG CALCULATOR" + System.lineSeparator());
    writeMessage("- '3' : X-DAY CROSSOVER CALCULATOR" + System.lineSeparator());
    writeMessage("- 'm' : MAIN MENU" + System.lineSeparator());
  }

  @Override
  public void portfolioInstructionMenu() {
    writeMessage("\n||———————————————— PORTFOLIO MENU ————————————————||" + System.lineSeparator());
    writeMessage("- '1' : ADD PORTFOLIO" + System.lineSeparator());
    writeMessage("- '2' : REMOVE PORTFOLIO" + System.lineSeparator());
    writeMessage("- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO" + System.lineSeparator());
    writeMessage("- '4' : FIND A PORTFOLIO VALUE" + System.lineSeparator());
    writeMessage("- '5' : LIST STOCKS IN A PORTFOLIO" + System.lineSeparator());
    writeMessage("- '6' : LIST ALL PORTFOLIOS" + System.lineSeparator());
    writeMessage("- 'm' : MAIN MENU" + System.lineSeparator());
  }

  @Override
  public void printMenu() {
    writeMessage("\n||—————————— MAIN MENU ——————————||" + System.lineSeparator());
    writeMessage("- '1' : STOCK OPERATIONS" + System.lineSeparator());
    writeMessage("- '2' : PORTFOLIO OPERATIONS" + System.lineSeparator());
    writeMessage("- 'q' : QUIT" + System.lineSeparator());
  }

  @Override
  public void welcomeMessage() {
    writeMessage(System.lineSeparator() + "Welcome to the stock program!" + System.lineSeparator());
  }

  @Override
  public void farewellMessage() {
    writeMessage("Thank you for using this stock program!");
  }

  protected String viewToString() {
    return this.appendable.toString();
  }

}

