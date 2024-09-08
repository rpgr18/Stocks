package mvc.view;


import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * The GraphicsStockView interface defines operations and methods for graphical user
 * interfaces (views) for the stock program.
 */
public interface GraphicsStockView  {

  /**
   * The makeVisible makes the current view visible.
   */
  void makeVisible();

  /**
   * The displayMessage method displays the given string to the output box in the view.
   * @param message the message to display
   */
  void displayMessage(String message);

  /**
   * The displayError method displays an error popup with the given error message.
   * @param error the error message
   */
  void displayError(String error);

  /**
   * The getCreatePortfolioNameCommand method returns the name of the desired portfolio
   * as entered by the user in the create new portfolio window.
   * @return the portfolio name
   */
  String getCreatePortfolioNameCommand();

  /**
   * The getStockTickerCommand method return the stock ticker for the desired stock as
   * entered by the user for the buy/sell functionality.
   * @return the stock ticker
   */
  String getStockTickerCommand();

  /**
   * The getStockSharesCommand method returns the number of shares to buy/sell as entered
   * by the user for the buy/sell functionality.
   * @return the number of shares as a double
   */
  double getStockSharesCommand();

  /**
   * The getBuySellStockDateCommand returns the date as entered by the user for the
   * buy/sell functionality.
   * @return the date
   */
  LocalDate getBuySellStockDateCommand();

  /**
   * The getValueStockDateCommand returns the date as entered by the user for the
   * value of a portfolio functionality.
   * @return the date
   */
  LocalDate getValueStockDateCommand();

  /**
   * The getBuySellPortfolioCommand method returns the desired portfolio the user
   * chose to operate on for the buy/sell functionality.
   * @return the portfolio name
   */
  String getBuySellPortfolioCommand();

  /**
   * The getValuePortfolioCommand method returns the desired portfolio the user
   * chose to operate on for the value of a portfolio functionality.
   * @return the portfolio name
   */
  String getValuePortfolioCommand();

  /**
   * The getSavePortfolioCommand method returns the desired portfolio the user
   * chose to operate on for the save a portfolio functionality.
   * @return the portfolio name
   */
  String getSavePortfolioCommand();

  /**
   * The getLoadPortfolioCommand method returns the desired portfolio the user
   * chose to operate on for the load a portfolio functionality.
   * @return the portfolio name
   */
  String getLoadPortfolioCommand();

  /**
   * The refresh method refreshes the view and updates the visuals.
   */
  void refresh();

  /**
   * The setActivePortfolios method tells the view which portfolios are active in the model's
   * portfolio manager.
   * @param portfolioNames the names of active portfolios
   */
  void setActivePortfolios(String[] portfolioNames);

  /**
   * The setSavedPortfolios method tells the view which portfolios are in the local "portfolios"
   * directory and are availible to be loaded.
   * @param portfolioNames the names of the portfolios in the local directory
   */
  void setSavedPortfolios(String[] portfolioNames);

  /**
   * The setListeners method passes this controller to the view and sets the controller
   * as the action-listener for all the buttons in the view.
   * @param actionEvent the listener (controller)
   */
  void setListeners(ActionListener actionEvent);

}
