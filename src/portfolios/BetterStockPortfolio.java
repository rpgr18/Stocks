package portfolios;

import java.time.LocalDate;

import mvc.DateRange;

/**
 * The BetterStockPortfolio interface extends the functionality of the
 * StockPortfolio interface allowing for the buying and selling of stock on a
 * certain day.
 */
public interface BetterStockPortfolio extends StockPortfolio {

  /**
   * The adjustStockOnDay method adds or removes the specified number of shares
   * of a given stock from the portfolio on a given date.
   *
   * @param ticker the stock ticker
   * @param shares the number of shares
   * @param date the date to complete the transaction on
   */
  void adjustStockOnDay(String ticker, double shares, LocalDate date);

  /**
   * The getCompositionOnDay method returns a list of stock and the number of shares existent in
   * a portfolio on a given day.
   *
   * @param date the given date
   * @return the string list
   */
  String getCompositionOnDay(LocalDate date);

  /**
   * The distribution method returns the distribution of stock in this portfolio as a string.
   *
   * @param date the date on which to find the distribution
   * @return the string distribution
   */
  String distribution(LocalDate date);


  /**
   * The savePortfolioToFile method saves this portfolio
   * to a CSV file that can be loaded into the program at a later date.
   * The CSV file is named 'portfolio-name'.csv and is automatically
   * saved to a new "portfolios" directory.
   */
  void savePortfolioToFile();

  /**
   * The plot method returns a visual representation of a portfolio's performance over a time range
   * of WEEK, MONTH, YEAR, FIVE_YEARS, or TEN_YEARS.
   *
   * @param range the DateRange enum object range
   */
  String plot(DateRange range);

  /**
   * The rebalance Stock method rebalances a single stock.
   * @param expectedValue the expectedValue of the balanced stock
   * @param date the date on which to rebalance.
   * @param name the ticker of the stock to rebalance
   */
  void rebalanceStock(double expectedValue, LocalDate date, String name);

}
