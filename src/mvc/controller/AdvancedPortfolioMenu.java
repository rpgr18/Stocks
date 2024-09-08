package mvc.controller;

import java.time.LocalDate;
import java.util.Scanner;

import mvc.DateRange;
import mvc.model.BetterStockManager;
import mvc.view.TextStockView;

/**
 * The AdvancedPortfolioMenu class extends the functionality of the SimplePortfolioMenu
 * class to add support for the inputs that are need for the updated portfolio
 * functionality.
 */
class AdvancedPortfolioMenu extends SimplePortfolioMenu {
  //Store the data of the program with the advanced model
  protected BetterStockManager model;

  /**
   * Controller will pass its parameters to the menu to help cut down on long switch
   * statements in the controllerGo method.
   *
   * @param model the model of the program
   * @param view  the view
   * @param sc    the input scanner
   */
  protected AdvancedPortfolioMenu(BetterStockManager model, TextStockView view, Scanner sc) {
    super(model, view, sc);
    this.model = model;
  }

  @Override
  public void apply(String input) {
    try {
      switch (input) {
        case "3":
        case "buy":
        case "sell":
          updateAdjustPortfolio();
          break;
        case "5":
        case "composition":
          updateListStocksInPortfolio();
          break;
        case "7":
        case "distribution":
          updateDistributionPortfolio();
          break;
        case "8":
        case "save":
          updateSavePortfolio();
          break;
        case "9":
        case "load":
          updateLoadPortfolio();
          break;
        case "a":
        case "rebalance":
          updateRebalancePortfolio();
          break;
        case "b":
        case "plot":
          updatePlotPortfolio();
          break;
        default:
          super.apply(input);
      }
    } catch (RuntimeException e) {
      this.view.writeMessage(e.getMessage() + System.lineSeparator());
    }

  }


  private void updateAdjustPortfolio() {
    double shares;
    String name;
    String ticker;
    LocalDate date;
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
      this.view.writeMessage("INVALID INPUT: Please enter a valid number of shares!"
              + System.lineSeparator());
      return;
    }

    date = promptDate("");

    this.model.adjustPortfolioOnDay(name, formattedTicker, shares, date);
    this.view.writeMessage(shares + " SHARES OF " + formattedTicker + " ADDED TO " + name + " ON "
            + date + System.lineSeparator());
  }

  private void updateListStocksInPortfolio() {
    String name;
    LocalDate date;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();

    date = promptDate("");

    String output = this.model.getComposition(name, date);
    this.view.writeMessage("STOCKS IN " + name + " ON " + date + ": "
            + output);
  }

  private void updateDistributionPortfolio() {
    String name;
    LocalDate date;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();

    date = promptDate("");

    String distribution = this.model.portfolioDistribution(name, date);
    this.view.writeMessage(distribution);
  }

  private void updateSavePortfolio() {
    String name;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();

    this.model.savePortfolio(name);
    this.view.writeMessage(name + " SAVED TO 'portfolios' DIRECTORY" + System.lineSeparator());
  }

  private void updateLoadPortfolio() {
    String name;

    this.view.writeMessage("ENTER THE NAME OF THE PORTFOLIO TO LOAD " +
            "(Must be in portfolio directory): " + System.lineSeparator());
    name = sc.next();

    this.model.loadPortfolio(name);
    this.view.writeMessage(name + " LOADED, CHECK ACTIVE PORTFOLIOS" + System.lineSeparator());
  }

  private void updateRebalancePortfolio() {
    String name;
    LocalDate date;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + this.model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());

    name = sc.next();

    date = promptDate("REBALANCE ");

    String[] stocks = this.model.getComposition(name, date).split(", ");

    this.view.writeMessage("ENTER A WHOLE NUMBER FOR EACH STOCK BELOW " + System.lineSeparator()
            + "TO DETERMINE THEIR WEIGHT (i.e. GOOG: $100, PFE: $50 —> " +
            "1, 1 —> GOOG: $100, PFE: $100):"
            + System.lineSeparator());

    double[] distributions = new double[stocks.length];
    for (int i = 0; i < stocks.length; i++) {
      if (stocks[i].substring(stocks[i].indexOf(": "), stocks[i].indexOf(": ") + 1).equals("0")) {
        distributions[i] = 0;
      } else {
        this.view.writeMessage("Distribution for " + stocks[i].substring(0, stocks[i].indexOf(":"))
                + ":" + System.lineSeparator());
        if (sc.hasNextInt()) {
          distributions[i] = sc.nextInt();
          if (distributions[i] < 0) {
            throw new IllegalArgumentException("INVALID INPUT: Distributions can't be negative");
          }
        } else {
          sc.next();
          throw new IllegalArgumentException("INVALID INPUT: Please enter a valid integer!"
                  + System.lineSeparator());
        }
      }
    }
    this.model.portfolioRebalance(name, date, distributions);

  }

  private void updatePlotPortfolio() {
    LocalDate custom = LocalDate.of(2024, 6, 13);
    String name;
    String range;
    DateRange appliedRange;

    this.view.writeMessage("ACTIVE PORTFOLIOS (" + model.listPortfolios() + ")"
            + System.lineSeparator());
    this.view.writeMessage("ENTER A PORTFOLIO NAME:" + System.lineSeparator());
    name = sc.next();
    this.view.writeMessage("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
            "'5y' = FIVE YEARS, '10y' = TEN YEARS):" + System.lineSeparator());
    range = sc.next();

    switch (range) {
      case "w":
        appliedRange = DateRange.WEEK;
        break;
      case "m":
        appliedRange = DateRange.MONTH;
        break;
      case "y":
        appliedRange = DateRange.YEAR;
        break;
      case "5y":
        appliedRange = DateRange.FIVE_YEARS;
        break;
      case "10y":
        appliedRange = DateRange.TEN_YEARS;
        break;
      // for custom date range functionality, not yet full implemented, but added
      // for ease of testing and for future upgrades.
      case "cw":
        appliedRange = DateRange.CUSTOM_WEEK;
        DateRange.setCustomRange(custom, appliedRange);
        break;
      case "cm":
        appliedRange = DateRange.CUSTOM_MONTH;
        DateRange.setCustomRange(custom, appliedRange);
        break;
      case "cy":
        appliedRange = DateRange.CUSTOM_YEAR;
        DateRange.setCustomRange(custom, appliedRange);
        break;
      case "c5y":
        appliedRange = DateRange.CUSTOM_FIVE_YEARS;
        DateRange.setCustomRange(custom, appliedRange);
        break;
      case "c10y":
        appliedRange = DateRange.CUSTOM_TEN_YEARS;
        DateRange.setCustomRange(custom, appliedRange);
        break;
      default:
        throw new IllegalArgumentException("** ENTER A VALID DATE RANGE (i.e. w, m, y, 5y) **");
    }
    this.view.writeMessage(this.model.plotPortfolio(name, appliedRange) + System.lineSeparator());
  }

}
