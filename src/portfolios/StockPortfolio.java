package portfolios;

import java.time.LocalDate;
import java.util.List;

import stocks.Stock;

/**
 * The {@code portfolios.StockPortfolio} interface represent operations on a portfolio of stocks,
 * allowing for the addition and removal of stocks as well as the calculation of total value
 * on a given day.
 */
public interface StockPortfolio {

  /**
   * The adjustStock method adds or removes the specified number of shares
   * of a given stock to the portfolio.
   *
   * @param ticker the stock ticker
   * @param shares the number of shares
   */
  void adjustStock(String ticker, double shares);

  /**
   * The setName method allows a user to name their portfolio.
   *
   * @param name the desired name
   */
  void setName(String name);

  /**
   * The getName method returns the name of a portfolio.
   *
   * @return the name
   */
  String getName();

  /**
   * The getValue method return the value of the entire portfolio on
   * a given date based on the closing price of the stocks.
   *
   * @param date the date to determine value
   * @return the total value of the portfolio
   */
  double getValue(LocalDate date);

  /**
   * The listStocks method lists all the stocks in the current portfolio and the number
   * of share of each.
   * @return a list of stocks as a string
   */
  String listStocks();

  /**
   * The getStocks method returns  the list of Stocks currently in the portfolio.
   *
   * @return a list of Stock objects
   */
  List<Stock> getStocks();
}
