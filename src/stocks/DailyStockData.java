package stocks;

import java.time.LocalDate;

/**
 * The {@code stocks.DailyStockData} class represents day-to-day stock data in
 * the form of high, low, open, close, and volume values.
 */
public class DailyStockData implements StockData {
  private final LocalDate date;
  private final double open;
  private final double close;
  private final double high;
  private final double low;
  private final int volume;

  /**
   * Constructs a {@code stocks.DailyStockData} object allowing for direct
   * field assignment.
   *
   * @param date the timestamp of the data
   * @param open the opening value
   * @param close the closing value
   * @param high the high value
   * @param low the low value
   * @param volume the trading volume
   */
  public DailyStockData(LocalDate date, double open, double close, double high,
                        double low, int volume) {
    this.date = date;
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.volume = volume;
  }

  @Override
  public LocalDate getDate() {
    return this.date;
  }

  @Override
  public double getOpen() {
    return this.open;
  }

  @Override
  public double getClose() {
    return this.close;
  }

  @Override
  public double getHigh() {
    return this.high;
  }

  @Override
  public double getLow() {
    return this.low;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }
}
