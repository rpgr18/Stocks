package mvc.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mvc.DateRange;
import portfolios.AdvancedStockPortfolio;
import portfolios.BetterStockPortfolio;
import stocks.BetterStock;

/**
 * The AdvancedStockManager is the updated model that extends the SimpleStockManager with
 * new functionality for portfolios. The AdvancedStockManager for the most part references
 * the old, unchanged SimpleStockManager class for unchanged functionality, although in some
 * cases where the new class need to use a Better... interface, the old methods are overridden
 * to work with Better... objects
 */
public class AdvancedStockManager extends SimpleStockManager implements BetterStockManager {
  private List<BetterStockPortfolio> portfolios;

  /**
   * Constructs a new Advanced model.
   */
  public AdvancedStockManager() {
    super();
    portfolios = new ArrayList<>();
  }

  @Override
  public void adjustPortfolioOnDay(String name, String ticker, double shares, LocalDate date) {
    BetterStockPortfolio portfolio = find(name);
    portfolio.adjustStockOnDay(ticker, shares, date);
  }

  @Override
  public String portfolioDistribution(String name, LocalDate date) {
    StringBuilder distribution = new StringBuilder();
    BetterStockPortfolio portfolio = find(name);

    return portfolio.distribution(date);
  }

  @Override
  public void addPortfolio(String name) throws IllegalArgumentException {
    if (containsPortfolio(name)) {
      throw new IllegalArgumentException("** A PORTFOLIO NAMED: " + name + " ALREADY EXISTS **");
    }

    BetterStockPortfolio newPortfolio = new AdvancedStockPortfolio();
    newPortfolio.setName(name);
    portfolios.add(newPortfolio);
  }

  @Override
  public void removePortfolio(String name) throws IllegalArgumentException {
    portfolios.remove(find(name));
  }

  @Override
  public String listStocksInPortfolio(String name) {
    BetterStockPortfolio portfolio = find(name);
    return portfolio.listStocks();
  }

  @Override
  public String getComposition(String name, LocalDate date) {
    BetterStockPortfolio portfolio = find(name);
    return portfolio.getCompositionOnDay(date);
  }

  @Override
  public void portfolioRebalance(String portfolioName, LocalDate date, double[] distributions) {
    for (double ratio : distributions) {
      if (Math.abs(ratio - Math.floor(ratio)) > 1e-9) {
        throw new IllegalArgumentException("** DISTRIBUTIONS MUST BE WHOLE NUMBERS **");
      }
    }

    BetterStockPortfolio portfolio = find(portfolioName);
    double totalDistribution = 0;
    for (double ratio : distributions) {
      totalDistribution += ratio;
    }
    String input = this.getComposition(portfolioName, date);
    String[] stocks = input.split(", ");
    if (stocks.length == 1 && distributions.length > 1) {
      throw new IllegalArgumentException("** TOO MANY DISTRIBUTIONS FOR ONE STOCK **");
    }
    final double originalValue = find(portfolioName).getValue(date);
    for (int i = 0; i < stocks.length; i++) {
      String realStockName = stocks[i].substring(0,stocks[i].indexOf(":"));
      find(portfolioName).rebalanceStock( originalValue
                      * (distributions[i] / totalDistribution),
              date , realStockName);
    }

  }

  @Override
  public String plotPortfolio(String name, DateRange range) {
    BetterStockPortfolio portfolio = find(name);
    return portfolio.plot(range);
  }

  @Override
  public void savePortfolio(String name) {
    BetterStockPortfolio portfolio = find(name);
    portfolio.savePortfolioToFile();
  }

  @Override
  public void loadPortfolio(String name) {
    String directoryPath = "portfolios";
    String fileName = name + ".csv";
    File directory = new File(directoryPath);
    File fileToLoad = new File(directory, fileName);

    if (containsPortfolio(name)) {
      throw new IllegalArgumentException("** ATTEMPTING TO LOAD A PORTFOLIO THAT ALREADY EXISTS **"
      + System.lineSeparator() + "** IF YOU WISH TO LOAD " + name + " FIRST REMOVE THE "
              + "CURRENT PORTFOLIO WITH THE SAME NAME **");
    }

    if (!fileToLoad.exists()) {
      throw new IllegalArgumentException("** PORTFOLIO FILE NOT FOUND **");
    }

    // Check if the file name matches exactly
    boolean exactMatch = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
            .anyMatch(file -> file.getName().equals(fileName));
    if (!exactMatch) {
      throw new IllegalArgumentException("** PORTFOLIO FILE NOT FOUND WITH EXACT "
              + "CASE-SENSITIVE NAME **");
    }


    try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
      // Skip the header line
      reader.readLine();

      // Read the stock data
      String line;
      BetterStockPortfolio output = new AdvancedStockPortfolio();
      Map<String, BetterStock> stockMap = new HashMap<>();

      while ((line = reader.readLine()) != null) {
        System.out.println("Reading line: " + line);
        String[] fields = line.split(",");

        if (fields.length != 5) {
          throw new IllegalArgumentException("** PORTFOLIO FILE IS NOT PROPERLY FORMATTED **");
        }

        String ticker = fields[0].trim();
        double shares = Double.parseDouble(fields[2].trim());
        LocalDate date = LocalDate.parse(fields[4].trim());

        output.adjustStockOnDay(ticker, shares, date);
      }

      output.setName(name);
      portfolios.add(output);
    } catch (IOException | RuntimeException e) {
      throw new IllegalArgumentException("** ERROR READING THE PORTFOLIO FILE **", e);
    }
  }


  /**
   * The portfolioListToString method converts a list of StockPortfolio objects to
   * a corresponding string list.
   *
   * @return the string list
   */
  @Override
  protected String portfolioListToString() {
    StringBuilder output = new StringBuilder();
    for (BetterStockPortfolio p : portfolios) {
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
  @Override
  protected boolean containsPortfolio(String name) {
    boolean found = false;
    for (BetterStockPortfolio p : portfolios) {
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
  @Override
  protected BetterStockPortfolio find(String name)
          throws IllegalArgumentException {
    BetterStockPortfolio output = null;
    for (BetterStockPortfolio p : portfolios) {
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

  @Override
  public ArrayList<String> getPortfolioNamesInDirectory() {
    String directoryPath = "portfolios";
    File directory = new File(directoryPath);
    File[] files = directory.listFiles();

    ArrayList<String> portfolioNames = new ArrayList<>();

    if (files != null) {
      for (File file : files) {
        if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
          String portfolioName = file.getName().replace(".csv", "").trim();
          portfolioNames.add(portfolioName);
        }
      }
    }

    return portfolioNames;
  }

  @Override
  public ArrayList<String> getPortfolioNames() {
    ArrayList<String> output = new ArrayList<>();
    for (BetterStockPortfolio p : portfolios) {
      output.add(p.getName());
    }
    return output;
  }

}