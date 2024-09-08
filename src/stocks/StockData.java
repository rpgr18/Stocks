package stocks;

import java.time.LocalDate;


/**
 * The {@code stocks.StockData} interface represents different forms of stocks.Stock data
 * and operations for that data. An example would be daily stock data (high, low close, etc...)
 */
public interface StockData {

  /**
   * To report the corresponding date to a given stocks.StockData object's data.
   * @return the date
   */
  LocalDate getDate();

  /**
   * To report the value of a stock at market open on a given day.
   * @return the opening dollar value
   */
  double getOpen();

  /**
   * To report the value of a stock at market close on a given day.
   * @return the closing dollar value
   */
  double getClose();

  /**
   * To report the overall high value of a stock for a certain day.
   * @return the high dollar value
   */
  double getHigh();

  /**
   * To report the overall low value of a stock for a certain day.
   * @return the low dollar value
   */
  double getLow();

  /**
   * To report the overall trading volume of a stock for a certain day.
   * @return the trading volume
   */
  int getVolume();

}
