package stocks;

import java.time.LocalDate;


/**
 * The BetterStock interface extends the functionality of the Stock interface to allow
 * for the adjusting of stocks on a certain day.
 */
public interface BetterStock extends Stock {

  /**
   * The adjustShares method add the given number of shares to this stock's
   * shares (number can be negative). An overall negative number of shares is
   * not allowed.
   *
   * @param shares number of shares to add
   * @param date the date on which to buy or sell shares
   */
  void adjustSharesOnDay(double shares, LocalDate date);

  /**
   * The getSharesOnDay method returns the number of shares of a certain stock in a portfolio
   * on the given date.
   *
   * @param date the date to search;
   */
  double getSharesOnDay(LocalDate date);

  /**
   * The getValueOnDate method searches through a BetterStocks transactions and return the current
   * value of the stock on given date. If shares were purchased after the given date, they are not
   * considered. If the given date is before the earliest purchase of shares, the value is 0.
   *
   * @param date the date to find the value
   * @return the double value
   */
  double getValueOnDate(LocalDate date);

  /**
   * The getTransaction method return a string output of the transaction history for this stock.
   * @return the string output
   */
  String getTransactions();


  /**
   * The re-balanceStock method re-balances a single stock to the expected value on the given date.
   * @param expectedValue the expected value for the given stock
   * @param date the date on which t0 re-balance
   */
  void rebalanceStock(double expectedValue, LocalDate date);
}
