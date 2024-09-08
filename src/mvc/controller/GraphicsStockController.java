package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import mvc.model.BetterStockManager;
import mvc.view.GraphicsStockView;

/**
 * The GraphicsStockController class is an implementation on the StockController
 * interface that works for graphical user interfaces.
 */
public class GraphicsStockController implements StockController, ActionListener {
  //store and process the data
  private BetterStockManager model;
  //keep track and respond to output requests from the controller
  private GraphicsStockView view;

  /**
   * Constructs a GraphicsStockController object.
   * @param model this model
   * @param view this view
   */
  public GraphicsStockController(BetterStockManager model, GraphicsStockView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void controllerGo() throws IllegalStateException {
    view.setListeners(this);
    view.makeVisible();
    view.setSavedPortfolios(model.getPortfolioNamesInDirectory().toArray(new String[0]));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    try {
      switch (command) {
        case "Create Portfolio":
          updateCreatePortfolio();
          break;
        case "Buy/Sell":
          updateAdjustSharesOnDay();
          break;
        case "Query Value":
          updateGetValueOnDay();
          break;
        case "Save Portfolio":
          updateSavePortfolio();
          break;
        case "Load Portfolio":
          updateLoadPortfolio();
          break;
        default:
          break;
      }
    } catch (Exception ex) {
      view.displayError(ex.getMessage());
    }

    view.setActivePortfolios(model.getPortfolioNames().toArray(new String[0]));
    view.setSavedPortfolios(model.getPortfolioNamesInDirectory().toArray(new String[0]));
    view.refresh();
  }

  /**
   * Tell the model to create a portfolio.
   */
  private void updateCreatePortfolio() {
    String name = view.getCreatePortfolioNameCommand();

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("** MISSING ARGUMENTS **");
    }

    model.addPortfolio(name);
    view.displayMessage("Portfolio " + name + " created successfully.");
  }

  /**
   * Tell the model to buy/sell shares on a day.
   */
  private void updateAdjustSharesOnDay() {
    LocalDate date = view.getBuySellStockDateCommand();
    String name = view.getBuySellPortfolioCommand();
    String ticker = view.getStockTickerCommand();
    Double shares = view.getStockSharesCommand();

    if (name == null || name.isEmpty() || ticker == null || date == null || shares == null) {
      throw new IllegalArgumentException("** MISSING ARGUMENTS **");
    }

    model.adjustPortfolioOnDay(name, ticker, shares, date);
    if (shares >= 0) {
      view.displayMessage(shares + " of " + ticker + " bought in portfolio " + name
              + " on " + date);
    } else {
      view.displayMessage(shares + " of " + ticker + " sold in portfolio " + name + " on " + date);
    }
  }

  /**
   * Tell the model to query the value of a portfolio.
   */
  private void updateGetValueOnDay() {
    LocalDate date = view.getValueStockDateCommand();
    String name = view.getValuePortfolioCommand();

    if (name == null || name.isEmpty() || date == null) {
      throw new IllegalArgumentException("** MISSING ARGUMENTS **");
    }

    double value = model.getPortfolioValue(name, date);
    view.displayMessage("Value of portfolio " + name + " on " + date + ": " + value);
  }

  /**
   * Tell the model to save a portfolio.
   */
  private void updateSavePortfolio() {
    String name = view.getSavePortfolioCommand();

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("** MISSING ARGUMENTS **");
    }

    model.savePortfolio(name);
    view.displayMessage("Portfolio " + name + " saved successfully.");
  }

  /**
   * Tell the model to load a portfolio.
   */
  private void updateLoadPortfolio() {
    String name = view.getLoadPortfolioCommand();

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("** MISSING ARGUMENTS **");
    }

    model.loadPortfolio(name);
    view.displayMessage("Portfolio " + name + " loaded successfully.");
    view.displayMessage(model.listPortfolios());
  }

}
