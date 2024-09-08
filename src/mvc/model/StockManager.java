package mvc.model;

import java.time.LocalDate;
import java.util.List;


/**
 * The {@code mvc.model.StockManager} interface represents operations on stocks and is the interface
 * that houses the methods for the model of this application.
 * Changes since P1: The portfolio manager class was redundant and methods from that class
 * were added to the StockManager interface (i.e. addPortfolio, removePortfolio, findValue,
 * etc...). The old PortfolioManager class was removed and its methods were
 * added to the SimpleStockManager implementation as needed. Now all the functionality of the
 * program is encapsulated in the model without the need of an external class for methods on
 * portfolios
 */
public interface StockManager {

  /**
   * The getGainLoss method returns the gain or loss on a specified stock over a
   * specified date range.
   *
   * @param ticker the stock ticker
   * @param start  the start date
   * @param end    the end date
   * @return the gain or loss
   */
  double getGainLoss(String ticker, LocalDate start, LocalDate end);

  /**
   * The getMovingAvg method returns the x-day moving average of a specified
   * stock on a specified date for a specified number of days.
   *
   * @param ticker the stock ticker
   * @param date   the date
   * @param days   teh number of days (x-value)
   * @return the moving average
   */
  double getMovingAvg(String ticker, LocalDate date, int days);

  /**
   * The getCrossovers method returns the days that are x-day crossovers for a specified stock over
   * a specified date range and x-value.
   *
   * @param ticker the stock ticker
   * @param start  the start date
   * @param end    the end date
   * @param days   the number of days (x-value)
   * @return a list of crossover days
   */
  List<LocalDate> getCrossovers(String ticker, LocalDate start, LocalDate end, int days);


  /**
   * The addPortfolio method allows a user to add a portfolio with a given name
   * to the PortfolioManager. Adding a portfolio with a name that already exists is not allowed.
   *
   * @param name the name of the portfolio
   * @throws IllegalArgumentException if the name already exists
   */
  void addPortfolio(String name) throws IllegalArgumentException;

  /**
   * The removePortfolio method removes a portfolio of the given name from the
   * portfolio manger.
   *
   * @param name the name of the portfolio to remove
   * @throws IllegalArgumentException if the given portfolio is not found
   */
  void removePortfolio(String name) throws IllegalArgumentException;

  /**
   * The adjustPortfolio method supports the addition and subtraction of stock from a single
   * portfolio. If a stock is already found in a portfolio, that stock will be updated.
   * Otherwise, a new stock with a given number of shares will be added to the portfolio.
   * If the number of shares of a certain stock reaches 0, that stock will be removed from the
   * portfolio. No stock may have negative shares.
   *
   * @param name   the name of the portfolio
   * @param ticker the stock ticker
   * @param shares the number of shares to be added
   */
  void adjustPortfolio(String name, String ticker, double shares);

  /**
   * The getPortfolioValue method returns the total value of a portfolio of the given
   * name on the given date.
   *
   * @param name the name of the portfolio
   * @param date the given date
   * @return the double value
   */
  double getPortfolioValue(String name, LocalDate date);

  /**
   * The listStockInPortfolio method returns a list of the stocks in a given portfolio
   * in the form of a string.
   *
   * @param name the name of the portfolio
   * @return the string list
   */
  String listStocksInPortfolio(String name);

  /**
   * The listPortfolios method return a list of all the portfolios in the
   * portfolio manager in the form of a string.
   *
   * @return the string list
   */
  String listPortfolios();

}
