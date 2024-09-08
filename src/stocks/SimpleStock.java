package stocks;


import java.time.LocalDate;
import java.util.List;

/**
 * The {@code stocks.Stock} class represents basic information about a stock in a portfolio,
 * including symbol, number of shares, and historical data.
 */
public class SimpleStock implements Stock {
  private final String ticker;
  protected final List<StockData> historicalData;
  private double shares;

  /**
   * Constructs a {@code stocks.Stock} object.
   * Historical data is automatically populated by a local file if
   * one is found. If not the data will be retrieved from the API.
   *
   * @param ticker the stock ticker
   */
  public SimpleStock(String ticker) {
    this.ticker = ticker.toUpperCase();
    this.historicalData = getData(ticker);
    this.shares = 0.0;
  }

  /**
   * The getPriceOnDay method returns the closing price on a Stock on a given
   * day. If the given day is not a market day, the closing price of the next previous
   * market day is returned.
   *
   * @param date the date to examine
   * @return the price on the given date
   * @throws IllegalArgumentException if the date given is either before the earliest
   *                                  data entry or sometime in the future
   */
  public double getPriceOnDay(LocalDate date) throws IllegalArgumentException {
    if (historicalData.get(historicalData.size() - 1).getDate().isAfter(date)) {
      throw new IllegalArgumentException("** NO DATA AVAILABLE FOR " + date
              + " TRY A MORE RECENT DATE **");
    }
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
    while (true) {
      for (StockData d : historicalData) {
        if (d.getDate().equals(date)) {
          return d.getClose();
        }
      }
      date = date.minusDays(1);
    }
  }

  /**
   * The getTicker method return the ticker of this stock.
   *
   * @return the ticker
   */
  public String getTicker() {
    return this.ticker;
  }

  /**
   * The getShare method return the number of shares of this stock.
   *
   * @return the number of shares
   */
  public double getShares() {
    return this.shares;
  }

  /**
   * The adjustShares method add the given number of shares to this stock's
   * shares (number cna be negative). An overall negative number of shares is
   * not allowed.
   *
   * @param shares number of shares to add
   * @throws IllegalArgumentException if total shares is less than 0
   */
  public void adjustShares(double shares) throws IllegalArgumentException {
    if (shares + this.shares < 0) {
      throw new IllegalArgumentException("** YOU CANNOT HAVE NEGATIVE SHARES OF A STOCK **");
    }
    this.shares += shares;
  }

  /**
   * The getData method get the history data from a local CSV file by default.
   * If a CSV corresponding to the given ticker is not found, the method will attempt to
   * download a new file from the API. Alternatively if the CSV file does exist but is
   * not up-to-date (i.e. its most recent entry is not today), it is replaced by a new CSV file
   * from the API.
   *
   * @param ticker the stock ticker to find
   * @return a list of StockData
   */
  protected List<StockData> getData(String ticker) {
    StockDataSource localSource = new LocalCSVDataSource();
    StockDataSource webSource = new AlphaVantageDataSource();

    try {
      List<StockData> data = localSource.getHistoryData(ticker);

      if (data.get(0).getDate().isBefore(LocalDate.now())) {
        return webSource.getHistoryData(ticker);
      }
      return data;
    } catch (RuntimeException e) {
      return webSource.getHistoryData(ticker);
    }
  }

}
