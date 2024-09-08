package mvc.model;

import java.time.LocalDate;
import java.util.ArrayList;

import mvc.DateRange;

/**
 * The BetterStockManager interface extends the original model interface and adds additional
 * functionality to adjust a portfolio on a specific day.
 */
public interface BetterStockManager extends StockManager {

  /**
   * The adjustPortfolioOnDay method supports the addition and subtraction of shares of a stock
   * from a single portfolio on a specific day. If a stock is already found in a portfolio,
   * that stock will be updated.
   * Otherwise, a new stock with a given number of shares will be added to the portfolio.
   * If the number of shares of a certain stock reaches 0, that stock will be removed from the
   * portfolio. No stock may have negative shares.
   *
   * @param name   the name of the portfolio
   * @param ticker the stock ticker
   * @param shares the number of shares to be added
   * @param date the date to complete the transaction
   */
  void adjustPortfolioOnDay(String name, String ticker, double shares, LocalDate date);

  /**
   * The getComposition method return the composition of a portfolio on a given day.
   * @param name the portfolio to search
   * @param date the given date
   * @return a string list of composition
   */
  String getComposition(String name, LocalDate date);

  /**
   * The portfolioDistribution method returns, as a string a description of the distribution of
   * value in a portfolio including the stocks, their corresponding values, and percentages on
   * a given date.
   *
   * @param name the name of the portfolio to analyze
   * @param date the date to lookup
   * @return the distribution string
   */
  String portfolioDistribution(String name, LocalDate date);

  /**
   * The portfolio rebalance method rebalances the given portfolio with the desired distributions.
   * Rebalance will never result in negative shares even if shares are subtracted in the future,
   * for example if one buys 5 shares of GOOG on 2024-5-1 and 5 shares of PFE on 2024-5-2, then
   * sells 4.9 shares of GOOG on 2024-6-1, if the user tries to rebalance on 2024-5-3 with a
   * 50 - 50 distribution, the user will get an error message saying that negative shares are not
   * allowed because rebalancing does not invalidate future transactions. Also, if there is only
   * one stock in a portfolio on the rebalance date, the program will still prompt the user for
   * weights but regardless of the weight the stock will not be changed and there is no need for
   * rebalancing.
   *
   * @param name portoflio to rebalance
   * @param date the date on which to rebalance
   * @param distributions the desired weights
   */
  void portfolioRebalance(String name, LocalDate date, double[] distributions);

  /**
   * The plot method returns a visual representation of a portfolio's performance over a time range
   * of WEEK, MONTH, YEAR, FIVE_YEARS, or TEN_YEARS.
   *
   * @param range the DateRange enum object range
   */
  String plotPortfolio(String name, DateRange range);

  /**
   * The savePortfolio method searches this list of portfolios and saves the corresponding
   * portfolio with the given name to a directory call portfolios. The saves portfolio is a
   * CSV data file name 'portfolio-name'.csv.
   *
   * @param name the name of the portfolio to save
   */
  void savePortfolio(String name);

  /**
   * The loadPortfolio method loads a portfolio file that is a CSV file, is formatted correctly,
   * and is in the 'portfolios' directory. One will be automatically created the first
   * time you save a portfolio but if you are starting by loading one in, you must create your
   * own and it must be named "portfolios". The loaded portfolio is added to the models list of
   * portfolios and supports all operations.
   * @param name name of portfolio to search
   */
  void loadPortfolio(String name);

  ArrayList<String> getPortfolioNamesInDirectory();

  ArrayList<String> getPortfolioNames();
}
