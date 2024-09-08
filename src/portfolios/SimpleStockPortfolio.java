package portfolios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import stocks.SimpleStock;
import stocks.Stock;


/**
 * The {@code portfolios.SimpleStockPortfolio} represent a regular, simplified
 * stock portfolio with limited operations.
 */
public class SimpleStockPortfolio implements StockPortfolio {
  private String name;
  private List<Stock> stocks;

  /**
   * Constructs a {@code portfolios.SimpleStockPortfolio} object.
   */
  public SimpleStockPortfolio() {
    this.name = "Untitled Portfolio";
    this.stocks = new ArrayList<Stock>();
  }

  @Override
  public void adjustStock(String ticker, double shares) throws IllegalArgumentException {
    if (ticker == null) {
      throw new IllegalArgumentException("** STOCK NOT FOUND, CHECK TICKER FORMAT **");
    }
    //this was made final because the Advanced Class that extends it
    //replaces the method entirely with the adjustStockOnDay method
    String formattedTicker = ticker.toUpperCase();
    if (Math.floor(shares) != shares) {
      throw new IllegalArgumentException(
              "** FRACTIONAL SHARES NOT ALLOWED FOR SIMPLE PORTFOLIOS **");
    }

    if (!containsStock(formattedTicker, stocks)) {
      Stock newStock = new SimpleStock(ticker);
      newStock.adjustShares(shares);
      stocks.add(newStock);
    } else {
      Stock existingStock = find(formattedTicker);
      existingStock.adjustShares(shares);
      if (existingStock.getShares() == 0) {
        stocks.remove(existingStock);
      }
    }
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<Stock> getStocks() {
    return new ArrayList<>(stocks);
  }

  @Override
  public double getValue(LocalDate date) {
    double value = 0.0;
    for (Stock s : stocks) {
      value += (s.getPriceOnDay(date) * s.getShares());
    }
    return value;
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
  private Stock find(String ticker) throws IllegalArgumentException {
    Stock output = null;
    for (Stock s : stocks) {
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
  private boolean containsStock(String ticker, List<Stock> stocks) {
    boolean found = false;
    for (Stock s : stocks) {
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
  private String stockListToString(List<Stock> stocks) {
    StringBuilder output = new StringBuilder();
    for (Stock s : stocks) {
      String format = s.getTicker() + ": " + s.getShares() + ", ";
      output.append(format);
    }
    return output.toString();
  }
}
