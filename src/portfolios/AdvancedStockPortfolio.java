package portfolios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import mvc.DateRange;
import stocks.AdvancedStock;
import stocks.BetterStock;
import stocks.SimpleStock;
import stocks.Stock;

/**
 * The AdvancedStockPortfolio class extends the functionality of the original
 * SimpleStockPortfolio implementation to include the new functions (i.e. plot,
 * re-balance, etc...).
 */
public class AdvancedStockPortfolio extends SimpleStockPortfolio implements BetterStockPortfolio {
  private List<BetterStock> stocks;

  /**
   * Constructs a {@code portfolios.AdvancedStockPortfolio} object using
   * a list of BetterStocks instead of Stocks.
   */
  public AdvancedStockPortfolio() {
    setName("Untitled Portfolio");
    this.stocks = new ArrayList<BetterStock>();
  }

  @Override
  public void adjustStock(String ticker, double shares) {
    if (!containsStock(ticker, stocks)) {
      BetterStock newStock = new AdvancedStock(ticker);
      stocks.add(newStock);
    }
  }

  @Override
  public void adjustStockOnDay(String ticker, double shares, LocalDate date) {
    adjustStock(ticker, shares);
    BetterStock stock = find(ticker);

    stock.adjustSharesOnDay(shares, date);

  }

  @Override
  public double getValue(LocalDate date) {
    double value = 0.0;
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }

    for (BetterStock s : stocks) {
      value += (s.getValueOnDate(date));
    }
    return value;
  }

  @Override
  public List<Stock> getStocks() {
    return advancedToSimpleStockAdapter();
  }

  @Override
  public String getCompositionOnDay(LocalDate date) {
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** EASY THERE, THIS PROGRAM CANNOT TELL THE FUTURE **");
    }
    StringBuilder output = new StringBuilder();
    double acc = 0.0;
    for (BetterStock s : stocks) {
      double shares = s.getSharesOnDay(date);
      if (shares == 0) {
        output.append("");
      } else {
        String appendable = s.getTicker() + ": " + shares + " shares, ";
        output.append(appendable);
        acc += shares;
      }
    }

    if (acc == 0) {
      throw new IllegalArgumentException("** NO STOCKS IN PORTFOLIO ON " + date + " **");
    }
    return output.toString();
  }

  @Override
  public String distribution(LocalDate date) {
    StringBuilder output = new StringBuilder();
    StringBuilder stocksNotYetPurchased = new StringBuilder();
    String percentage;
    String appendable;

    for (BetterStock s : stocks) {

      if (s.getValueOnDate(date) == 0) {
        appendable = "";
        String label = "";
        stocksNotYetPurchased.append(label);
      } else {
        percentage = Long.toString(Math.round((s.getValueOnDate(date) / getValue(date)) * 100));
        appendable = s.getTicker() + ": " + "$" + s.getValueOnDate(date) + " — " + percentage + "%"
                + System.lineSeparator();

      }

      output.append(appendable);
    }

    if (output.length() == 0) {
      return System.lineSeparator() + "** NO STOCKS IN " + getName() + " ON " + date + " **";
    } else {
      return output.toString() + stocksNotYetPurchased;
    }
  }

  @Override
  public void savePortfolioToFile() {
    File directory = new File("portfolios");

    if (!directory.exists()) {
      directory.mkdir();
    }


    String filePath = "portfolios" + File.separator + getName() + ".csv";

    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
      // Write the stock data
      writer.println("Ticker,Transaction,Shares,TotalShares,Date");
      for (BetterStock s : stocks) {
        writer.print(s.getTransactions());
      }
    } catch (IOException e) {
      throw new RuntimeException("** UNABLE TO SAVE PORTFOLIO TO FILE, PLEASE TRY AGAIN **");
    }


  }

  @Override
  public void rebalanceStock(double expectedValue, LocalDate date, String stockName) {
    find(stockName).rebalanceStock(expectedValue, date);
  }

  @Override
  public String plot(DateRange range) {
    StringBuilder outputBuilder = new StringBuilder();
    TreeMap<LocalDate, Double> valuesInRange = getValuesInRange(range);
    if (valuesInRange.isEmpty()) {
      return "No data available for the given date range.";
    }

    double maxValue = valuesInRange.values().stream().max(Double::compareTo).orElse(0.0);
    int maxAsterisks = 30; // can be changed to adjust the horizontal scale
    double unitsPerAsterisk = maxValue / maxAsterisks;

    // Round units to nearest 10
    double roundedUnits = Math.round(unitsPerAsterisk / 10) * 10;
    roundedUnits = roundedUnits == 0 ? 5 : roundedUnits;  // Ensure we don't end up with zero

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
    int maxDateLength = valuesInRange.keySet().stream()
            .map(date -> date.format(formatter))
            .mapToInt(String::length)
            .max()
            .orElse(0);

    int padding = 5; // Additional spaces for padding after the date
    outputBuilder.append("Performance of portfolio from ")
            .append(range.getStartDate())
            .append(" to ")
            .append(range.getEndDate())
            .append("\n\n");

    for (Map.Entry<LocalDate, Double> entry : valuesInRange.entrySet()) {
      String dateStr = entry.getKey().format(formatter);
      double value = entry.getValue();
      int asterisksCount = (int) (value / roundedUnits);
      String asterisks = "*".repeat(asterisksCount);
      outputBuilder.append(String.format("%-" + (maxDateLength + padding)
              + "s: %s%n", dateStr, asterisks));
    }

    outputBuilder.append("\n")
            .append("Scale: * = $")
            .append(roundedUnits);

    return outputBuilder.toString();
  }

  @Override
  public String listStocks() {
    return stockListToString(stocks);
  }

  /**
   * The find method searches this list of stocks and returns the Stock object
   * that matches the given ticker.
   *
   * @param ticker the stock ticker
   * @return the matching stock object.
   * @throws IllegalArgumentException if the stock of the given ticker is not found
   */
  private BetterStock find(String ticker) throws IllegalArgumentException {
    BetterStock output = null;
    for (BetterStock s : stocks) {
      if (s.getTicker().equals(ticker)) {
        output = s;
        break;
      }
    }
    if (output == null) {
      throw new IllegalArgumentException("** STOCK NOT FOUND, CHECK TICKER FORMAT **");
    }
    return output;
  }

  /**
   * The containsStock method searches a list of Stocks and return a boolean value
   * indicating whether a stock of the given ticker was found in that list.
   *
   * @param ticker the stock ticker
   * @param stocks the list of stocks to search
   * @return the boolean value
   */
  private boolean containsStock(String ticker, List<BetterStock> stocks) {
    boolean found = false;
    for (BetterStock s : stocks) {
      if (s.getTicker().equals(ticker)) {
        found = true;
        break;
      }
    }
    return found;
  }

  /**
   * The stockListToString method converts a list of Stock objects to a string list.
   *
   * @param stocks the list of stocks
   * @return the converted string list
   */
  private String stockListToString(List<BetterStock> stocks) {
    StringBuilder output = new StringBuilder();
    for (BetterStock s : stocks) {
      String format = s.getTicker() + ": " + s.getShares() + ", ";
      output.append(format);
    }
    return output.toString();
  }

  /**
   * Converts a list of AdvancedStocks to a list of SimpleStocks that have the corresponding number
   * of shares and the correct ticker.
   *
   * @return the list of Stock objects
   */
  private List<Stock> advancedToSimpleStockAdapter() {
    List<Stock> output = new ArrayList<>();
    for (BetterStock s : stocks) {
      Stock convertedStock = new SimpleStock(s.getTicker());
      convertedStock.adjustShares(s.getShares());
      output.add(convertedStock);
    }
    return output;
  }

  /**
   * The getValuesInRange method returns a daily list of the values of a certain stock
   * in the given range.
   *
   * @param range the date range enum
   * @return the map of values
   */
  private TreeMap<LocalDate, Double> getValuesInRange(DateRange range) {
    TreeMap<LocalDate, Double> output = new TreeMap<>();
    LocalDate start = range.getStartDate();
    LocalDate end = range.getEndDate();
    int step;

    if (stocks.isEmpty()) {
      throw new IllegalArgumentException("** NO STOCKS HAVE BEEN ADDED, " +
              "UNABLE TO PLOT PERFORMANCE **");
    }

    switch (range) {
      case MONTH:
      case CUSTOM_MONTH:
        step = 2;
        break;
      case YEAR:
      case CUSTOM_YEAR:
        step = 30;
        break;
      case FIVE_YEARS:
      case CUSTOM_FIVE_YEARS:
        step = 152;
        break;
      case TEN_YEARS:
      case CUSTOM_TEN_YEARS:
        step = 300;
        break;
      default:
        step = 1;
        break;
    }

    // Iterate through the range with the calculated step size
    LocalDate currentDate = start;
    while (!currentDate.isAfter(end)) {
      double valueOnDay = getValue(currentDate);
      output.put(currentDate, valueOnDay);
      currentDate = currentDate.plusDays(step); // Move to the next date by stepSize
    }

    // Add the exact start and end dates to the output map
    output.put(start, getValue(start));
    output.put(end, getValue(end));

    return output;
  }

}

