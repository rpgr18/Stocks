package mvc.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import portfolios.SimpleStockPortfolio;
import portfolios.StockPortfolio;
import stocks.AlphaVantageDataSource;
import stocks.LocalCSVDataSource;
import stocks.StockData;
import stocks.StockDataSource;

/**
 * The {@code mvc.model.SimpleStockManager} class is the model in the MVC architecture
 * of this program. This class handles all calculations and computations and that are then
 * pushed to the view by the controller.
 */
public class SimpleStockManager implements StockManager {
  private StockDataSource source;
  private List<StockPortfolio> portfolios;

  public SimpleStockManager() {
    source = new AlphaVantageDataSource();
    portfolios = new ArrayList<>();
  }

  @Override
  public double getGainLoss(String ticker, LocalDate start, LocalDate end) {

    timeCheck(start, end);

    List<StockData> data = getData(ticker, start, 0);

    double startPrice = getClosingPrice(data, start);
    double endPrice = getClosingPrice(data, end);

    return endPrice - startPrice;

  }

  @Override
  public double getMovingAvg(String ticker, LocalDate date, int days) {
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
    List<StockData> data = getData(ticker, date, days);
    double sum = 0.0;
    int acc = 0;

    for (StockData stockData : data) {
      if (!stockData.getDate().isAfter(date) && acc < days) {
        sum += stockData.getClose();
        acc++;
      }
    }
    if (acc == 0) {
      throw new IllegalArgumentException("** ENTER A POSITIVE X-VALUE **");
    }

    return sum / acc;
  }

  @Override
  public List<LocalDate> getCrossovers(String ticker, LocalDate start, LocalDate end, int days) {

    timeCheck(start, end);

    List<StockData> data = getData(ticker, start, days);
    List<LocalDate> crossovers = new ArrayList<>();


    data = trim(data, start, end);

    for (StockData d : data) {
      LocalDate currentDate = d.getDate();
      double movingAverage = getMovingAvg(ticker, currentDate, days);

      if (d.getClose() > movingAverage) {
        crossovers.add(currentDate);
      }
    }
    return crossovers;
  }

  @Override
  public void addPortfolio(String name) throws IllegalArgumentException {
    if (containsPortfolio(name)) {
      throw new IllegalArgumentException("** A PORTFOLIO NAMED: " + name + " ALREADY EXISTS **");
    }
    StockPortfolio newPortfolio = new SimpleStockPortfolio();
    newPortfolio.setName(name);
    portfolios.add(newPortfolio);
  }

  @Override
  public void removePortfolio(String name) throws IllegalArgumentException {
    portfolios.remove(find(name));
  }

  @Override
  public void adjustPortfolio(String name, String ticker, double shares) {
    StockPortfolio portfolio = find(name);
    portfolio.adjustStock(ticker, shares);
  }

  @Override
  public double getPortfolioValue(String name, LocalDate date) {
    StockPortfolio portfolio = find(name);
    return portfolio.getValue(date);
  }

  @Override
  public String listStocksInPortfolio(String name) {
    StockPortfolio portfolio = find(name);
    return portfolio.listStocks();
  }

  @Override
  public String listPortfolios() {
    return portfolioListToString();
  }

  /**
   * The portfolioListToString method converts a list of StockPortfolio objects to
   * a corresponding string list.
   *
   * @return the string list
   */
  protected String portfolioListToString() {
    StringBuilder output = new StringBuilder();
    for (StockPortfolio p : portfolios) {
      String format = p.getName() + ", ";
      output.append(format);
    }
    return output.toString();
  }

  /**
   * The containsPortfolio private method return a boolean value based on whether
   * a given list of portfolios contains a portfolio of a given name.
   *
   * @param name       the given name
   * @return the boolean value
   */
  protected boolean containsPortfolio(String name) {
    boolean found = false;
    for (StockPortfolio p : portfolios) {
      if (p.getName().equals(name)) {
        found = true;
        break;
      }
    }
    return found;
  }

  /**
   * The find method searches this list of portfolios and return the StockPortfolio
   * object of the given name. If no matching portfolio is found, an exception is thrown.
   *
   * @param name the name of portfolio to find
   * @return the matching portfolio object
   * @throws IllegalArgumentException if the given portfolio is not found
   */
  protected StockPortfolio find(String name)
          throws IllegalArgumentException {
    StockPortfolio output = null;
    for (StockPortfolio p : portfolios) {
      if (p.getName().equals(name)) {
        output = p;
        break;
      }
    }
    if (output == null) {
      throw new IllegalArgumentException("** NO SUCH PORTFOLIO FOUND **");
    }
    return output;
  }


  /**
   * The getData method get the history data from a local CSV file by default.
   * If a CSV corresponding to the given ticker is not found, the method will attempt to
   * download a new file from the API. Alternatively if the CSV file does exist but is
   * not up-to-date (i.e. its most recent entry is not today), it is replaced by a new CSV file
   * from the API. This method also checks that the earliest date accessed by the user is
   * not out of range of the accessible data and that the x value for methods like getCrossovers
   * does not reach beyond the earliest data available.
   *
   * @param ticker stock ticker
   * @param start earliest date accessed
   * @param x x-day value
   * @return the list of StockData
   */
  protected List<StockData> getData(String ticker, LocalDate start, int x) {
    StockDataSource localSource = new LocalCSVDataSource();
    String formattedTicker = ticker.toUpperCase();

    try {
      List<StockData> data = localSource.getHistoryData(formattedTicker);
      if (data.get(0).getDate().isBefore(LocalDate.now())) {
        earliestDataCheck(start, x, data);
        return source.getHistoryData(formattedTicker);
      }
      earliestDataCheck(start, x, data);
      return data;
    } catch (RuntimeException e) {
      List<StockData> data = source.getHistoryData(formattedTicker);
      earliestDataCheck(start, x, data);
      return data;
    }

  }

  /**
   * The getClosingPrice method retrieves the closing price of the given date,
   * if the stock market is closed on the given date, it will return the closing price
   * of the next previous stock market day.
   *
   * @param history the list of stocks.StockData for a given ticker
   * @param date    the desired date
   * @return the closing price on that date
   */
  private double getClosingPrice(List<StockData> history, LocalDate date) {
    while (true) {
      for (StockData d : history) {
        if (d.getDate().equals(date)) {
          return d.getClose();
        }
      }
      date = date.minusDays(1);
    }
  }

  /**
   * The trim method trims a list of stocks.StockData to only include those entries
   * corresponding to the date within the range (inclusive).
   *
   * @param data  the list of stocks.StockData
   * @param start the start date
   * @param end   the end date
   * @return the new, trimmed list of stocks.StockData
   */
  private List<StockData> trim(List<StockData> data, LocalDate start, LocalDate end) {
    List<StockData> output = new ArrayList<>();
    for (StockData d : data) {
      if (!d.getDate().isBefore(start) && !d.getDate().isAfter(end)) {
        output.add(d);
      }
    }
    return output;
  }

  /**
   * The timeCheck method ensure that the start date is before or equal to the end date,
   * and that the given date is not in the future.
   * @param start start date
   * @param end end date
   * @throws IllegalArgumentException if start date is after end date or start date is in
   *         the future
   */
  protected void timeCheck(LocalDate start, LocalDate end) throws IllegalArgumentException {
    if (!start.isBefore(end) && !start.isEqual(end)) {
      throw new IllegalArgumentException("** START DATE MUST PRECEDE END DATE **");
    }
    if (start.isAfter(LocalDate.now()) || end.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
  }

  /**
   * The earliestDataCheck method ensure that the given date does not try to access
   * data that doesn't exist either because the date itself is before the earliest accessible
   * data, or because the x value goes back before the earliest accessible data.
   * @param start start date
   * @param x x-value
   * @param data list of StockData
   * @throws IllegalArgumentException if the start date or x-value attempts to access a day
   *         before the earliest data available.
   */
  protected void earliestDataCheck(LocalDate start, int x, List<StockData> data)
          throws IllegalArgumentException {
    if (start.isBefore(data.get(data.size() - 1).getDate())) {
      throw new IllegalArgumentException("** HISTORICAL DATA NOT AVAILABLE FOR "
              + start + ", TRY A MORE RECENT TIME FRAME **" + System.lineSeparator());
    }
    if (start.minusDays(x).isBefore(data.get(data.size() - 1).getDate())) {
      throw new IllegalArgumentException("** AN X-VALUE OF "
              + x + " GOES BEFORE THE EARLIEST ACCESSIBLE DATA FOR THIS STOCK **");
    }
  }

}


