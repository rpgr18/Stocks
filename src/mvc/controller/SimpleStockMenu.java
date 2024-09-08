package mvc.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import mvc.model.StockManager;
import mvc.view.TextStockView;

/**
 * The SimpleStockMenu support the inputs for basic stock functions like
 * gain/loss, moving avg, crossovers.
 */
class SimpleStockMenu extends AbstractMenus {

  /**
   * Controller will pass its parameters to the menu to help cut down on long switch
   * statements in the controllerGo method.
   *
   * @param model the model of the program
   * @param view the view
   * @param sc the input scanner
   */
  protected SimpleStockMenu(StockManager model, TextStockView view, Scanner sc) {
    super(model, view, sc);
  }

  @Override
  public void apply(String input) {
    try {
      switch (input) {
        case "1":
        case "gain-loss":
          updateGainLoss();
          break;
        case "2":
        case "moving-avg":
          updateMovingAvg();
          break;
        case "3":
        case "crossovers":
          updateCrossovers();
          break;
        default:
          this.view.writeMessage("Unclear Instruction: " + input
                  + System.lineSeparator());
      }
    } catch (RuntimeException e) {
      this.view.writeMessage(e.getMessage() + System.lineSeparator());
    }
  }

  private int promptRange() {
    this.view.writeMessage("ENTER X VALUE:" + System.lineSeparator());
    if (sc.hasNextInt()) {
      return sc.nextInt();
    } else {
      sc.next();
      throw new IllegalArgumentException("** INVALID INPUT: PLEASE ENTER A VALID INTEGER **");
    }
  }

  private void updateGainLoss() throws RuntimeException {
    LocalDate start;
    LocalDate end;
    String ticker;
    String formattedTicker;

    this.view.writeMessage("ENTER STOCK TICKER (i.e. 'GOOG'):" + System.lineSeparator());
    ticker = sc.next();
    formattedTicker = ticker.toUpperCase();
    start = promptDate("START");

    end = promptDate("END");

    String output = String.format("%.2f", model.getGainLoss(formattedTicker, start, end));
    this.view.writeMessage("GAIN/LOSS OF " + formattedTicker + " FOR RANGE " + start
            + " — " + end + ": ");
    this.view.writeMessage(output + System.lineSeparator());
  }

  private void updateMovingAvg() {
    LocalDate start;
    int numDays;
    String ticker;
    String formattedTicker;

    this.view.writeMessage("ENTER STOCK TICKER (i.e. 'GOOG'):" + System.lineSeparator());
    ticker = sc.next();
    formattedTicker = ticker.toUpperCase();

    start = promptDate("");

    numDays = promptRange();

    String output = String.format("%.2f", model.getMovingAvg(formattedTicker, start, numDays));
    this.view.writeMessage(numDays + " DAY MOVING AVERAGE OF " + formattedTicker
            + " ON " + start + ": ");
    this.view.writeMessage(output + System.lineSeparator());
  }

  //String ticker, LocalDate start, LocalDate end, int days
  private void updateCrossovers() {
    LocalDate start;
    LocalDate end;
    int numDays;
    String ticker;
    String formattedTicker;

    this.view.writeMessage("ENTER STOCK TICKER (i.e. 'GOOG'):" + System.lineSeparator());
    ticker = sc.next();
    formattedTicker = ticker.toUpperCase();

    start = promptDate("START");

    end = promptDate("END");

    numDays = promptRange();

    List<LocalDate> dates = model.getCrossovers(formattedTicker, start, end, numDays);

    this.view.writeMessage(numDays + " DAY CROSSOVERS FOR " + formattedTicker
            + " DURING THE PERIOD  " + start + " — " + end + ": " + System.lineSeparator());
    for (LocalDate date : dates) {
      this.view.writeMessage(date.toString() + System.lineSeparator());
    }
  }

}
