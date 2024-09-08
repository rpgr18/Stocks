package mvc.controller;

import java.time.LocalDate;
import java.util.Scanner;

import mvc.model.StockManager;
import mvc.view.TextStockView;

/**
 * The SimplePortfolioMenu handles the inputs for the existing simple functions.
 */
class SimplePortfolioMenu extends AbstractMenus {

  /**
   * Controller will pass its parameters to the menu to help cut down on long switch
   * statements in the controllerGo method.
   *
   * @param model the model of the program
   * @param view  the view
   * @param sc    the input scanner
   */
  protected SimplePortfolioMenu(StockManager model, TextStockView view, Scanner sc) {
    super(model, view, sc);
  }

  @Override
  public void apply(String input) {
    try {
      switch (input) {
        case "1":
        case "new-portfolio":
          updateAddPortfolio();
          break;
        case "2":
        case "remove-portfolio":
          updateRemovePortfolio();
          break;
        case "3":
        case "adjust-portfolio":
          updateAdjustPortfolio();
          break;
        case "4":
        case "portfolio-value":
          updatePortfolioValue();
          break;
        case "5":
        case "portfolio-composition":
          updateListStocksInPortfolio();
          break;
        case "6":
        case "list-portfolio":
          this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
                  + System.lineSeparator());
          break;
        default:
          this.view.writeMessage("** UNCLEAR INSTRUCTION: " + input + " **"
                  + System.lineSeparator());
      }
    } catch (RuntimeException e) {
      this.view.writeMessage(e.getMessage() + System.lineSeparator());
    }

  }

  private void updateAddPortfolio() {
    this.view.writeMessage("ENTER PORTFOLIO NAME:" + System.lineSeparator());
    String name = sc.next();
    this.model.addPortfolio(name);
    this.view.writeMessage(name + " ADDED TO PORTFOLIO MANAGER." + System.lineSeparator());

  }

  private void updateRemovePortfolio() {
    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER PORTFOLIO NAME:" + System.lineSeparator());
    String name = sc.next();
    this.model.removePortfolio(name);
    this.view.writeMessage(name + " REMOVED FROM PORTFOLIO MANAGER."
            + System.lineSeparator());

  }

  private void updateAdjustPortfolio() {
    double shares;
    String name;
    String ticker;
    String formattedTicker;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();
    this.view.writeMessage("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "
            + System.lineSeparator());
    ticker = sc.next();
    formattedTicker = ticker.toUpperCase();
    this.view.writeMessage("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"
            + System.lineSeparator());

    if (sc.hasNextDouble()) {
      shares = sc.nextDouble();
    } else {
      sc.next();
      this.view.writeMessage("** INVALID INPUT: PLEASE ENTER A VALID NUMBER OF SHARES **"
              + System.lineSeparator());
      return;
    }

    this.model.adjustPortfolio(name, formattedTicker, shares);
    this.view.writeMessage(shares + " SHARES OF " + formattedTicker + " ADDED TO " + name
            + System.lineSeparator());
  }

  private void updatePortfolioValue() {
    String name;
    LocalDate date;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();

    date = promptDate("");

    double output = model.getPortfolioValue(name, date);
    this.view.writeMessage("VALUE OF: " + name + " ON " + date + ": $");
    this.view.writeMessage(String.format("%.2f", output) + System.lineSeparator());
  }

  private void updateListStocksInPortfolio() {
    String name;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();
    this.view.writeMessage("STOCKS IN " + name + ": " + this.model.listStocksInPortfolio(name));
  }

}
