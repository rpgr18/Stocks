package stocks;

import java.util.List;

/**
 * The {@code stocks.StockDataSource} interface represents different types of data sources
 * for reporting stock information and methods to retrieve the data.
 */
public interface StockDataSource {

  /** The getHistoryData method queries the current stocks.StockDataSource for
   * historical stock data within a specified date range.
   *
   * @param ticker the ticker for the desired stock
   * @return a list of stocks.StockData corresponding to each date in the given range
   */
  List<StockData> getHistoryData(String ticker);

}
