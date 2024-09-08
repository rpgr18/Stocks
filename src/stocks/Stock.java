package stocks;

import java.time.LocalDate;

/**
 * The stock interface represents stock objects that are able to track their own number of shares,
 * historical data and identity (ticker). This interface supports operations like adjusting the
 * stock's number of shares and getting the price of a stock on a certain day.
 */
public interface Stock {

  /**
   * The getPriceOnDay method returns the closing price on a Stock on a given
   * day. If the given day is not a market day, the closing price of the next previous
   * market day is returned.
   *
   * @param date the date to examine
   * @return the price on the given date
   * @throws IllegalArgumentException if the date given is either before the earliest
   *         data entry or sometime in the future
   */
  double getPriceOnDay(LocalDate date) throws IllegalArgumentException;

  /**
   * The getTicker method return the ticker of this stock.
   *
   * @return the ticker
   */
  String getTicker();

  /**
   * The getShare method return the number of shares of this stock.
   *
   * @return the number of shares
   */
  double getShares();

  /**
   * The adjustShares method add the given number of shares to this stock's
   * shares (number can be negative). An overall negative number of shares is
   * not allowed.
   *
   * @param shares number of shares to add
   * @throws IllegalArgumentException if total shares is less than 0
   */
  void adjustShares(double shares) throws IllegalArgumentException;
}
