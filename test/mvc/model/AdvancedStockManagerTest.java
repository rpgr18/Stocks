package mvc.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mvc.DateRange;
import stocks.StockData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests for advanced version of model.
 */
public class AdvancedStockManagerTest extends AbstractStockManagerTest<AdvancedStockManager> {
  private LocalDate date1;
  private LocalDate date2;
  private LocalDate future;
  private LocalDate farPast;
  private static final String PORTFOLIOS_DIRECTORY = "./portfolios/"; // Adjust as needed
  private static final String PORTFOLIO_FILE_EXTENSION = ".csv";
  private List<String> createdPortfolioFiles;

  @Override
  public AdvancedStockManager createMockStockManager() {
    return new AdvancedStockManager() {
      @Override
      protected List<StockData> getData(String ticker, LocalDate start, int x) {
        earliestDataCheck(start, x, mockData);
        return mockData;
      }
    };
  }

  @Override
  public AdvancedStockManager createRealStockManager() {
    return new AdvancedStockManager();
  }

  @Before
  public void setUp() {
    super.setUp();
    createdPortfolioFiles = new ArrayList<>();
    date1 = LocalDate.of(2020, 5, 29);
    date2 = LocalDate.of(2024, 6, 1);
    future = LocalDate.of(2050, 6, 1);
    farPast = LocalDate.of(1800, 6, 1);
  }

  @After
  public void tearDown() {
    // Clean up only the portfolio files created during testing
    for (String fileName : createdPortfolioFiles) {
      File file = new File(PORTFOLIOS_DIRECTORY + fileName);
      if (file.exists()) {
        file.delete();
      }
    }
  }


  @Test
  public void testAdjustPortfolioOnDay_InvalidPortfolioName() {
    realManager.addPortfolio("Tech");
    try {
      realManager.adjustPortfolioOnDay("asdfgasdf", "AAPL", 10, date1);
      fail("Expected error for invalid portfolio name.");
    } catch (RuntimeException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
      assertEquals(realManager.listPortfolios(), "Tech, ");
    }
  }

  @Test
  public void testAdjustPortfolioOnDay_InvalidFutureDate() {
    realManager.addPortfolio("Tech");
    try {
      realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, future);
      fail("Expected error for invalid future date.");
    } catch (RuntimeException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **",
              e.getMessage());
      assertEquals(realManager.listPortfolios(), "Tech, ");
    }
  }

  @Test
  public void testAdjustPortfolioOnDay_InvalidPastDate() {
    realManager.addPortfolio("Tech");
    try {
      realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, farPast);
      fail("Expected error for invalid future date.");
    } catch (RuntimeException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1800-06-01 TRY A MORE RECENT DATE **",
              e.getMessage());
      assertEquals(realManager.listPortfolios(), "Tech, ");
    }
  }

  @Test
  public void testAdjustPortfolioOnDay_0Shares() {
    realManager.addPortfolio("Tech");
    try {
      realManager.adjustPortfolioOnDay("Tech", "AAPL", 0, farPast);
      fail("Expected error for bad transaction");
    } catch (RuntimeException e) {
      assertEquals("** A TRANSACTION MUST ADD OR SUBTRACT SHARES **",
              e.getMessage());
    }
  }

  @Test
  public void testAdjustPortfolioOnDay_InvalidTicker() {
    realManager.addPortfolio("Tech");
    try {
      realManager.adjustPortfolioOnDay("Tech", "asdaf", 10, date1);
      fail("Expected error for invalid ticker date.");
    } catch (RuntimeException e) {
      assertEquals("** STOCK NOT FOUND, CHECK TICKER FORMAT **",
              e.getMessage());
    }
  }

  @Test
  public void testAdjustPortfolioOnDay_AddOnce() {
    realManager.addPortfolio("Tech");
    //check that portfolio is unchanged before add date
    assertEquals(realManager.getPortfolioValue("Tech",
            date1.minusDays(1)), 0.0, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1.minusDays(1)),
            System.lineSeparator() + "** NO STOCKS IN Tech ON 2020-05-28 **");

    //adjust shares on a day
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    //check after
    assertEquals(realManager.getPortfolioValue("Tech", date1), 3179.4, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1),
            "AAPL: $3179.4 — 100%" + System.lineSeparator());
    assertEquals(realManager.getComposition("Tech", date1), "AAPL: 10.0 shares, ");

  }

  @Test
  public void testAdjustPortfolioOnDay_AddAndRemoveLater() {
    realManager.addPortfolio("Tech");
    //check that portfolio is unchanged before add date
    assertEquals(realManager.getPortfolioValue("Tech",
            date1.minusDays(1)), 0.0, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1.minusDays(1)),
            System.lineSeparator() + "** NO STOCKS IN Tech ON 2020-05-28 **");

    //adjust shares on a day
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    //check after first transaction
    assertEquals(realManager.getPortfolioValue("Tech", date1), 3179.4, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1),
            "AAPL: $3179.4 — 100%" + System.lineSeparator());
    assertEquals(realManager.getComposition("Tech", date1), "AAPL: 10.0 shares, ");

    //remove the shares
    realManager.adjustPortfolioOnDay("Tech", "AAPL", -10, date2);

    //check after that shares are gone
    assertEquals(realManager.getPortfolioValue("Tech", date2), 0.0, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date2),
            System.lineSeparator() + "** NO STOCKS IN Tech ON 2024-06-01 **");
  }

  @Test
  public void testAdjustPortfolioOnDay_AddAndRemoveMultipleTimesMultipleStocks() {
    realManager.addPortfolio("Tech");
    //check that portfolio is unchanged before add date
    assertEquals(realManager.getPortfolioValue("Tech",
            date1.minusDays(1)), 0.0, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1.minusDays(1)),
            System.lineSeparator() + "** NO STOCKS IN Tech ON 2020-05-28 **");

    //adjust shares on a day
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 2.5, date1);
    realManager.adjustPortfolioOnDay("Tech", "AAPL", -7.5, date1);

    //check1
    assertEquals(realManager.getPortfolioValue("Tech", date1), 4367.15, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date1),
            "AAPL: $794.8499999999999 — 18%\n" +
                    "GOOG: $3572.3 — 82%" + System.lineSeparator());
    assertEquals(realManager.getComposition("Tech", date1),
            "AAPL: 2.5 shares, GOOG: 2.5 shares, ");

    //adjust again
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 2.5, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOG", -2.5, date1);
    realManager.adjustPortfolioOnDay("Tech", "AMZN", 2.5, date1);

    //check after that shares are gone
    assertEquals(realManager.getPortfolioValue("Tech", date2), 1402.35, 0.001);
    assertEquals(realManager.portfolioDistribution("Tech", date2),
            "AAPL: $961.25 — 69%\n" +
                    "AMZN: $441.1 — 31%" + System.lineSeparator());
  }

  @Test
  public void testPortfolioDistribution_SingleStock() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    assertEquals(3179.4, realManager.getPortfolioValue("Tech",
            date1), 0.001);
    assertEquals("AAPL: $3179.4 — 100%" + System.lineSeparator(),
            realManager.portfolioDistribution("Tech", date1));
    assertEquals("AAPL: 10.0 shares, ", realManager.getComposition("Tech", date1));
  }

  @Test
  public void testPortfolioDistribution_MultipleStocks() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOGL", 5, date1);


    assertEquals(10347.0, realManager.getPortfolioValue("Tech", date1),
            0.001);
    assertEquals("AAPL: $3179.4 — 31%\nGOOGL: $7167.6 — 69%" +
            System.lineSeparator(), realManager.portfolioDistribution("Tech", date1));
    assertEquals("AAPL: 10.0 shares, GOOGL: 5.0 shares, ",
            realManager.getComposition("Tech", date1));
  }

  @Test
  public void testPortfolioDistribution_AddAndRemove() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOGL", 5, date1);
    realManager.adjustPortfolioOnDay("Tech", "AAPL", -5, date2);

    assertEquals(1823.75, realManager.getPortfolioValue("Tech", date2), 0.001);
    assertEquals("AAPL: $961.25 — 53%\nGOOGL: $862.5 — 47%" + System.lineSeparator(),
            realManager.portfolioDistribution("Tech", date2));
    assertEquals("AAPL: 5.0 shares, GOOGL: 5.0 shares, ",
            realManager.getComposition("Tech", date2));
  }

  @Test
  public void testPortfolioDistribution_NoStocks() {
    realManager.addPortfolio("Tech");
    assertEquals(0.0, realManager.getPortfolioValue("Tech", date1), 0.001);
    assertEquals(System.lineSeparator() + "** NO STOCKS IN Tech ON 2020-05-29 **",
            realManager.portfolioDistribution("Tech", date1));
  }

  @Test
  public void testPortfolioDistribution_PrecedesEarliestPurchase() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    assertEquals(0.0, realManager.getPortfolioValue("Tech", date1.minusDays(1)), 0.001);
    assertEquals(System.lineSeparator() + "** NO STOCKS IN Tech ON 2020-05-28 **",
            realManager.portfolioDistribution("Tech", date1.minusDays(1)));
  }

  @Test
  public void testPortfolioDistribution_AfterRemoval() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "AAPL", -10, date2);

    assertEquals(0.0, realManager.getPortfolioValue("Tech", date2), 0.001);
    assertEquals(System.lineSeparator() + "** NO STOCKS IN Tech ON 2024-06-01 **",
            realManager.portfolioDistribution("Tech", date2));
  }

  @Test
  public void testPortfolioDistribution_WrongPortfolioName() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    try {
      realManager.portfolioDistribution("TechX", date1);
      fail("Expected IllegalArgumentException for wrong portfolio name");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioDistribution_FutureDate() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);

    LocalDate futureDate = LocalDate.of(2025, 6, 5);
    try {
      realManager.portfolioDistribution("Tech", futureDate);
      fail("Expected IllegalArgumentException for future date");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testSavePortfolio_NewPortfolio() {
    realManager.addPortfolio("Tech");
    String portfolioName = "Tech";
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", 10, date1);

    // Save portfolio and verify the file existence in the portfolios directory
    realManager.savePortfolio(portfolioName);

    assertTrue(checkPortfolioFileExists(portfolioName));

    // Delete portfolio file from the portfolios directory after verification
    deletePortfolioFile(portfolioName);
  }

  @Test
  public void testSavePortfolio_ExistingPortfolio() {
    realManager.addPortfolio("Tech");
    String portfolioName = "Tech";
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", 10, date1);

    // Save portfolio and verify the file existence in the portfolios directory
    realManager.savePortfolio(portfolioName);

    assertTrue(checkPortfolioFileExists(portfolioName));

    // Modify the portfolio
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", -5, date1);

    // Save modified portfolio and verify the file existence again
    realManager.savePortfolio(portfolioName);

    assertTrue(checkPortfolioFileExists(portfolioName));

    // Delete portfolio file from the portfolios directory after verification
    deletePortfolioFile(portfolioName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSavePortfolio_InvalidPortfolioName_Null() {
    realManager.addPortfolio("Tech");
    realManager.savePortfolio(null); // Should throw IllegalArgumentException
  }

  @Test
  public void testSavePortfolio_InvalidPortfolioName_Empty() {
    realManager.addPortfolio("Tech");
    try {
      realManager.savePortfolio("");
      fail("Expected IllegalArgumentException for empty portfolio name");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
    }
  }

  private boolean checkPortfolioFileExists(String portfolioName) {
    String fileName = PORTFOLIOS_DIRECTORY + portfolioName + PORTFOLIO_FILE_EXTENSION;
    Path filePath = Paths.get(fileName);
    return Files.exists(filePath);
  }

  private void deletePortfolioFile(String portfolioName) {
    String fileName = PORTFOLIOS_DIRECTORY + portfolioName + PORTFOLIO_FILE_EXTENSION;
    Path filePath = Paths.get(fileName);
    try {
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      System.err.println("Failed to delete portfolio file: " + e.getMessage());
    }
  }

  @Test
  public void testLoadPortfolio_SavedPortfolio() {
    String portfolioName = "Tech";

    // Create portfolio
    realManager.addPortfolio(portfolioName);
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay(portfolioName, "GOOG", 5, date2);
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", -5, date2);

    // Save portfolio
    realManager.savePortfolio(portfolioName);
    createdPortfolioFiles.add(portfolioName + PORTFOLIO_FILE_EXTENSION);

    //Remove Portfolio
    realManager.removePortfolio(portfolioName);

    // Load portfolio
    realManager.loadPortfolio(portfolioName);

    // Check list of portfolios contains loaded portfolio
    assertEquals(realManager.listPortfolios(), "Tech, ");

    // Check portfolio operations after loading
    assertEquals(3179.4, realManager.getPortfolioValue(portfolioName, date1), 0.001);
    assertEquals(1831.05, realManager.getPortfolioValue(portfolioName, date2), 0.001);

    assertEquals("AAPL: $3179.4 — 100%" + System.lineSeparator(),
            realManager.portfolioDistribution(portfolioName, date1));
    assertEquals("AAPL: $961.25 — 52%\n" +
                    "GOOG: $869.8000000000001 — 48%" + System.lineSeparator(),
            realManager.portfolioDistribution(portfolioName, date2));

    assertEquals("AAPL: 10.0 shares, ", realManager.getComposition(portfolioName, date1));
    assertEquals("AAPL: 5.0 shares, GOOG: 5.0 shares, ",
            realManager.getComposition(portfolioName, date2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio_NonExistingPortfolio() {
    String portfolioName = "NonExistingPortfolio";

    assertEquals(realManager.listPortfolios(), "");

    realManager.loadPortfolio(portfolioName); // Should throw IllegalArgumentException
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio_NullPortfolioName() {
    realManager.loadPortfolio(null); // Should throw IllegalArgumentException
  }

  @Test
  public void testLoadPortfolio_EmptyPortfolioName() {
    try {
      realManager.loadPortfolio("");
      fail("Expected IllegalArgumentException for empty portfolio name");
    } catch (IllegalArgumentException e) {
      assertEquals("** PORTFOLIO FILE NOT FOUND **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance() {
    String portfolioName = "Tech";

    // Create portfolio
    realManager.addPortfolio(portfolioName);
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay(portfolioName, "GOOG", 5, date2);

    try {
      // Check portfolio distribution before rebalance
      assertEquals("AAPL: 10.0 shares, GOOG: 5.0 shares, ",
              realManager.getComposition(portfolioName, date2));
      assertEquals("AAPL: $1922.5 — 69%\n" +
                      "GOOG: $869.8000000000001 — 31%" + System.lineSeparator(),
              realManager.portfolioDistribution(portfolioName, date2));
      double originalValue = realManager.getPortfolioValue(portfolioName, date2);

      // Define new distributions for rebalancing
      double[] distributions = {60, 40}; // Example: 60% AAPL, 40% GOOG

      // Perform portfolio rebalance
      realManager.portfolioRebalance(portfolioName, date2, distributions);

      // Check portfolio distribution after rebalance
      assertEquals("AAPL: 8.714590377113135 shares, GOOG: 6.420556449758565 shares, ",
              realManager.getComposition(portfolioName, date2));
      assertEquals("AAPL: $1675.38 — 60%\n" +
                      "GOOG: $1116.92 — 40%" + System.lineSeparator(),
              realManager.portfolioDistribution(portfolioName, date2));

      // Check portfolio value after rebalance (assuming no change in stock prices for simplicity)
      double expectedValue = originalValue; // Assuming no change in stock prices
      assertEquals(expectedValue, realManager.getPortfolioValue(portfolioName, date2), 0.001);

    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_NonExistingPortfolio() {
    String nonExistingPortfolio = "NonExistingPortfolio";
    try {
      double[] distributions = {50, 50}; // Example distributions
      realManager.portfolioRebalance(nonExistingPortfolio, date1, distributions);
      // Should throw IllegalArgumentException
      fail("Expected IllegalArgumentException for non-existing portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_NullPortfolioName() {
    try {
      double[] distributions = {50, 50}; // Example distributions
      realManager.portfolioRebalance(null, date1, distributions);
      // Should throw IllegalArgumentException
      fail("Expected IllegalArgumentException for null portfolio name");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_EmptyPortfolioName() {
    try {
      double[] distributions = {50, 50}; // Example distributions
      realManager.portfolioRebalance("", date1, distributions);
      // Should throw IllegalArgumentException
      fail("Expected IllegalArgumentException for empty portfolio name");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO SUCH PORTFOLIO FOUND **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_FractionalDistributions() {
    String portfolioName = "Tech";

    // Create portfolio
    realManager.addPortfolio(portfolioName);
    realManager.adjustPortfolioOnDay(portfolioName, "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay(portfolioName, "GOOG", 5, date2);

    try {
      // Define fractional distributions for rebalancing
      double[] distributions = {0.5, 0.7}; //

      // Perform portfolio rebalance
      realManager.portfolioRebalance(portfolioName, date2, distributions);
      fail("Expected IllegalArgumentException for fractional distributions");
    } catch (IllegalArgumentException e) {
      assertEquals("** DISTRIBUTIONS MUST BE WHOLE NUMBERS **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_OneStock() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, LocalDate.now());

    double[] distributions = {2};
    realManager.portfolioRebalance("Tech", LocalDate.now(), distributions);

    assertEquals("AAPL: 10.0 shares, ", realManager.getComposition("Tech",
            LocalDate.now()));
  }

  @Test
  public void testPortfolioRebalance_OneStockTooManyDistributions() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, LocalDate.now());

    double[] distributions = {2, 1};
    try {
      realManager.portfolioRebalance("Tech", LocalDate.now(), distributions);
      fail("Expected IllegalArgumentException for two many distributions.");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** TOO MANY DISTRIBUTIONS FOR ONE STOCK **");
      assertEquals("AAPL: 10.0 shares, ", realManager.getComposition("Tech",
              LocalDate.now()));
    }
  }

  @Test
  public void testPortfolioRebalance_FutureDate() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, LocalDate.now());

    double[] distributions = {50, 50};
    LocalDate futureDate = LocalDate.now().plusDays(10);

    try {
      realManager.portfolioRebalance("Tech", futureDate, distributions);
      fail("Expected IllegalArgumentException for future date");
    } catch (IllegalArgumentException e) {
      assertEquals("** EASY THERE, THIS PROGRAM CANNOT TELL THE FUTURE **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_PastDate() {
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, LocalDate.now());

    double[] distributions = {50, 50};

    try {
      realManager.portfolioRebalance("Tech", farPast, distributions);
      fail("Expected IllegalArgumentException for past date");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO STOCKS IN PORTFOLIO ON 1800-06-01 **", e.getMessage());
    }
  }

  @Test
  public void testPortfolioRebalance_MultipleStocks() {
    realManager.addPortfolio("Tech");

    // Add stocks to the portfolio with different buy/sell actions
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5, date1);
    realManager.adjustPortfolioOnDay("Tech", "MSFT", 15, date1);
    realManager.adjustPortfolioOnDay("Tech", "AMZN", 8, date2);

    // Perform some sells
    realManager.adjustPortfolioOnDay("Tech", "AAPL", -5, date2);
    realManager.adjustPortfolioOnDay("Tech", "MSFT", -7, date1);

    // Verify composition before rebalance
    assertEquals("AAPL: 5.0 shares, GOOG: 5.0 shares, MSFT: 8.0 shares, AMZN: 8.0 shares, ",
            realManager.getComposition("Tech", date2));

    // Calculate distributions for rebalance (arbitrary example)
    double[] distributions = {20, 15, 25, 10};

    // Rebalance the portfolio
    realManager.portfolioRebalance("Tech", date2.plusDays(1), distributions);

    // Verify composition after rebalance
    assertEquals("AAPL: 9.754575515511796 shares, GOOG: 8.085122195578622 shares, " +
                    "MSFT: 5.646776741192948 shares, AMZN: 5.31431972018007 shares, ",
            realManager.getComposition("Tech", date2.plusDays(1)));
    assertEquals("AAPL: $961.25 — 15%\n" +
                    "GOOG: $869.8000000000001 — 13%\n" +
                    "MSFT: $3321.04 — 51%\n" +
                    "AMZN: $1411.52 — 22%" + System.lineSeparator(),
            realManager.portfolioDistribution("Tech", date2));

  }

  @Test
  public void testPortfolioRebalance_TwiceSameWeights() {
    realManager.addPortfolio("Tech");

    // Add stocks to the portfolio with different buy/sell actions
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10, date1);
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5, date1);
    realManager.adjustPortfolioOnDay("Tech", "MSFT", 15, date1);

    // Calculate distributions for rebalance (arbitrary example)
    double[] distributions = {30, 20, 50};

    // Rebalance the portfolio first time
    realManager.portfolioRebalance("Tech", date1.plusDays(1), distributions);

    // Verify composition after first rebalance
    assertEquals("AAPL: 12.335110398188338 shares, GOOG: 1.829738543795314 shares, " +
                    "MSFT: 35.66916780354707 shares, ",
            realManager.getComposition("Tech", LocalDate.now()));

    // Rebalance the portfolio second time with the same weights
    realManager.portfolioRebalance("Tech", date1.plusDays(1), distributions);

    // Verify composition remains unchanged after second rebalance
    assertEquals("AAPL: 12.335110398188338 shares, GOOG: 1.829738543795314 shares, " +
                    "MSFT: 35.66916780354707 shares, ",
            realManager.getComposition("Tech", LocalDate.now()));
  }

  @Test
  public void testPlotPortfolioWeek() {
    // Add a portfolio and adjust stocks
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange week = DateRange.CUSTOM_WEEK;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), week);

    // Plot for different date ranges
    String plotWeek = realManager.plotPortfolio("Tech", week);

    assertEquals(plotWeek,
            "Performance of portfolio from 2024-06-06 to 2024-06-13\n" +
                    "\n" +
                    "Jun 6, 2024      : ****************************\n" +
                    "Jun 7, 2024      : ****************************\n" +
                    "Jun 8, 2024      : ****************************\n" +
                    "Jun 9, 2024      : ****************************\n" +
                    "Jun 10, 2024     : ****************************\n" +
                    "Jun 11, 2024     : *****************************\n" +
                    "Jun 12, 2024     : ******************************\n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $100.0");
  }

  @Test
  public void testPlotPortfolioMonth() {
    // Add a portfolio and adjust stocks
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange month = DateRange.CUSTOM_MONTH;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), month);

    // Plot for different date ranges
    String plotMonth = realManager.plotPortfolio("Tech", month);
    assertEquals(plotMonth,
            "Performance of portfolio from 2024-05-13 to 2024-06-13\n" +
                    "\n" +
                    "May 13, 2024     : \n" +
                    "May 15, 2024     : \n" +
                    "May 17, 2024     : \n" +
                    "May 19, 2024     : \n" +
                    "May 21, 2024     : \n" +
                    "May 23, 2024     : \n" +
                    "May 25, 2024     : \n" +
                    "May 27, 2024     : \n" +
                    "May 29, 2024     : \n" +
                    "May 31, 2024     : \n" +
                    "Jun 2, 2024      : ***************************\n" +
                    "Jun 4, 2024      : ****************************\n" +
                    "Jun 6, 2024      : ****************************\n" +
                    "Jun 8, 2024      : ****************************\n" +
                    "Jun 10, 2024     : ****************************\n" +
                    "Jun 12, 2024     : ******************************\n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $100.0");
  }

  @Test
  public void testPlotPortfolioYear() {
    // Add a portfolio and adjust stocks
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange year = DateRange.CUSTOM_YEAR;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), year);

    // Plot for different date ranges
    String plotYear = realManager.plotPortfolio("Tech", year);
    assertEquals(plotYear,
            "Performance of portfolio from 2023-06-13 to 2024-06-13\n" +
                    "\n" +
                    "Jun 13, 2023     : \n" +
                    "Jul 13, 2023     : \n" +
                    "Aug 12, 2023     : \n" +
                    "Sep 11, 2023     : \n" +
                    "Oct 11, 2023     : \n" +
                    "Nov 10, 2023     : \n" +
                    "Dec 10, 2023     : \n" +
                    "Jan 9, 2024      : \n" +
                    "Feb 8, 2024      : \n" +
                    "Mar 9, 2024      : \n" +
                    "Apr 8, 2024      : \n" +
                    "May 8, 2024      : \n" +
                    "Jun 7, 2024      : ****************************\n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $100.0");
  }

  @Test
  public void testPlotPortfolioFiveYear() {
    // Add a portfolio and adjust stocks
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange fiveYears = DateRange.CUSTOM_FIVE_YEARS;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), fiveYears);

    // Plot for different date ranges
    String plotFiveYears = realManager.plotPortfolio("Tech", fiveYears);
    assertEquals(plotFiveYears,
            "Performance of portfolio from 2019-06-13 to 2024-06-13\n" +
                    "\n" +
                    "Jun 13, 2019     : \n" +
                    "Nov 12, 2019     : \n" +
                    "Apr 12, 2020     : \n" +
                    "Sep 11, 2020     : \n" +
                    "Feb 10, 2021     : \n" +
                    "Jul 12, 2021     : \n" +
                    "Dec 11, 2021     : \n" +
                    "May 12, 2022     : \n" +
                    "Oct 11, 2022     : \n" +
                    "Mar 12, 2023     : \n" +
                    "Aug 11, 2023     : \n" +
                    "Jan 10, 2024     : \n" +
                    "Jun 10, 2024     : ****************************\n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $100.0");
  }

  @Test
  public void testPlotPortfolioTenYears() {
    // Add a portfolio and adjust stocks
    realManager.addPortfolio("Tech");
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange tenYears = DateRange.CUSTOM_TEN_YEARS;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), tenYears);

    // Plot for different date ranges
    String plotTenYears = realManager.plotPortfolio("Tech", tenYears);
    assertEquals(plotTenYears,
            "Performance of portfolio from 2014-06-13 to 2024-06-13\n" +
                    "\n" +
                    "Jun 13, 2014     : \n" +
                    "Apr 9, 2015      : \n" +
                    "Feb 3, 2016      : \n" +
                    "Nov 29, 2016     : \n" +
                    "Sep 25, 2017     : \n" +
                    "Jul 22, 2018     : \n" +
                    "May 18, 2019     : \n" +
                    "Mar 13, 2020     : \n" +
                    "Jan 7, 2021      : \n" +
                    "Nov 3, 2021      : \n" +
                    "Aug 30, 2022     : \n" +
                    "Jun 26, 2023     : \n" +
                    "Apr 21, 2024     : \n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $100.0");
  }

  @Test
  public void testGetComposition() {
    // Create a portfolio and adjust stocks on different days
    realManager.addPortfolio("Tech");

    // Adding stocks to the portfolio
    realManager.adjustPortfolioOnDay("Tech", "AAPL", 10,
            LocalDate.of(2024, 6, 1));
    realManager.adjustPortfolioOnDay("Tech", "GOOG", 5,
            LocalDate.of(2024, 6, 3));
    realManager.adjustPortfolioOnDay("Tech", "MSFT", 15,
            LocalDate.of(2024, 6, 5));

    // Testing different scenarios
    // Valid compositions
    String expectedComposition1 = "AAPL: 10.0 shares, ";
    assertEquals(expectedComposition1, realManager.getComposition("Tech",
            LocalDate.of(2024, 6, 1)));

    String expectedComposition2 = "AAPL: 10.0 shares, GOOG: 5.0 shares, ";
    assertEquals(expectedComposition2, realManager.getComposition("Tech",
            LocalDate.of(2024, 6, 3)));

    String expectedComposition3 = "AAPL: 10.0 shares, GOOG: 5.0 shares, MSFT: 15.0 shares, ";
    assertEquals(expectedComposition3, realManager.getComposition("Tech",
            LocalDate.of(2024, 6, 5)));
  }

  @Test
  public void testGetCompositionEmptyPortfolio() {
    realManager.addPortfolio("Tech");
    try {
      // Testing for empty portfolio
      assertEquals("", realManager.getComposition("",
              LocalDate.of(2024, 6, 1)));
      fail("Expected Runtime error.");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NO SUCH PORTFOLIO FOUND **");
    }
  }

  @Test
  public void testGetCompositionNoStocks() {
    realManager.addPortfolio("Tech");

    // Testing for invalid past date
    try {
      realManager.getComposition("Tech", LocalDate.of(2023, 6, 1));
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      // Expected exception
      assertEquals("** NO STOCKS IN PORTFOLIO ON 2023-06-01 **", e.getMessage());
    }
  }

  @Test
  public void testGetCompositionInvalidFutureDate() {
    realManager.addPortfolio("Tech");
    // Testing for invalid future date
    try {
      realManager.getComposition("Tech", LocalDate.of(2024, 7, 1));
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      // Expected exception
      assertEquals("** EASY THERE, THIS PROGRAM CANNOT TELL THE FUTURE **", e.getMessage());
    }
  }


}






















