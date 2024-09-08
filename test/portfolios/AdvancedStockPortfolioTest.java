package portfolios;

import mvc.DateRange;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests for advanced stock portfolios.
 */
public class AdvancedStockPortfolioTest extends AbstractPortfolioTest<AdvancedStockPortfolio> {
  private LocalDate date1;

  protected AdvancedStockPortfolio createPortfolio() {
    return new AdvancedStockPortfolio();
  }

  @Before
  public void setUp() {
    super.setUp();
    date1 = LocalDate.of(2024, 5, 1);
  }

  @Test
  public void testAdjustStockOnSingleDay() {

    //check before
    assertEquals(portfolio.getValue(date1.minusDays(1)), 0.0, 0.001);

    //adjust stocks on a day
    portfolio.adjustStockOnDay("AAPL", 10, date1);

    //check after
    assertEquals(portfolio.getCompositionOnDay(date1), "AAPL: 10.0 shares, ");
    assertEquals(portfolio.getValue(date1), 1693.0, 0.001);
  }

  @Test
  public void testAdjustStockOnMultipleDays() {
    // Adjust stocks on day 1
    portfolio.adjustStockOnDay("AAPL", 10, date1);

    // Check after day 1 adjustment
    assertEquals("AAPL: 10.0 shares, ", portfolio.getCompositionOnDay(date1));
    assertEquals(1693.0, portfolio.getValue(date1), 0.001);

    // Adjust stocks on day 2
    LocalDate date2 = date1.plusDays(1);
    portfolio.adjustStockOnDay("AAPL", 5, date2);

    // Check after day 2 adjustment
    assertEquals("AAPL: 15.0 shares, ", portfolio.getCompositionOnDay(date2));
    assertEquals(2595.45, portfolio.getValue(date2), 0.001);
  }

  @Test
  public void testAdjustStockNegativeShares() {
    try {
      portfolio.adjustStockOnDay("AAPL", -10, date1);
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("** YOU CANNOT HAVE NEGATIVE SHARES OF A STOCK **", e.getMessage());
    }
  }

  @Test
  public void testAdjustStockZeroShares() {
    try {
      portfolio.adjustStockOnDay("AAPL", 0, date1);
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("** A TRANSACTION MUST ADD OR SUBTRACT SHARES **", e.getMessage());
    }
  }

  @Test
  public void testAdjustStockNonExistingStock() {
    try {
      portfolio.adjustStockOnDay("aksjfkajdsf", 10, date1);
      fail("Expected RuntimeException was not thrown");
    } catch (RuntimeException e) {
      assertEquals("** STOCK NOT FOUND, CHECK TICKER FORMAT **", e.getMessage());
    }
  }

  @Test
  public void testAdjustStockFutureDate() {
    try {
      LocalDate futureDate = date1.plusYears(20);
      portfolio.adjustStockOnDay("AAPL", 10, futureDate);
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testAdjustStockFarPastDate() {
    LocalDate pastDate = date1.minusYears(100);
    try {
      portfolio.adjustStockOnDay("AAPL", 10, pastDate);
      fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "** NO DATA AVAILABLE FOR 1924-05-01 TRY A MORE RECENT DATE **");
    }
  }

  @Test
  public void testGetValueWithInitialStocks() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date1 = LocalDate.of(2024, 5, 29);

    // Adjust stocks on date1
    portfolio.adjustStockOnDay("AAPL", 10, date1);
    portfolio.adjustStockOnDay("MSFT", 20, date1);

    assertEquals(10486.3, portfolio.getValue(date1), 0.001);
  }

  @Test
  public void testGetValueAfterAddingStocks() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date2 = LocalDate.of(2024, 5, 29); // Earlier date

    // Adjust stocks on date2
    portfolio.adjustStockOnDay("AAPL", 5, date2);
    portfolio.adjustStockOnDay("GOOGL", 10, date2);

    assertEquals(2710.45, portfolio.getValue(date2), 0.001);
  }

  @Test
  public void testGetValueAfterRemovingStocks() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date3 = LocalDate.of(2024, 5, 10);

    // Adjust stocks on date3
    portfolio.adjustStockOnDay("AAPL", 5, date3);
    portfolio.adjustStockOnDay("GOOGL", 10, date3);

    assertEquals(2601.75, portfolio.getValue(date3), 0.001);
  }

  @Test
  public void testGetValueNoStocksInPortfolio() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date1 = LocalDate.now().minusDays(3); // Recent date

    try {
      // Verify portfolio value when no stocks are present
      assertEquals(0.0, portfolio.getValue(date1), 0.001);
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NO STOCKS IN PORTFOLIO ON 2024-06-10 **");
    }
  }

  @Test
  public void testGetValueDateInFuture() {
    LocalDate futureDate = LocalDate.now().plusYears(1);

    // Try to get value for a future date
    try {
      portfolio.getValue(futureDate);
      fail("Expected IllegalArgumentException");
    } catch (RuntimeException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testPlotPortfolioWeek() {
    portfolio.adjustStockOnDay("AAPL", 10,
            LocalDate.of(2024, 6, 1));
    portfolio.adjustStockOnDay("GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange week = DateRange.CUSTOM_WEEK;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), week);

    // Plot for different date ranges
    String plotWeek = portfolio.plot(week);

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
    portfolio.adjustStockOnDay("AAPL", 10,
            LocalDate.of(2024, 6, 1));
    portfolio.adjustStockOnDay("GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange month = DateRange.CUSTOM_MONTH;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), month);

    // Plot for different date ranges
    String plotMonth = portfolio.plot(month);
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
    portfolio.adjustStockOnDay("AAPL", 10,
            LocalDate.of(2024, 6, 1));
    portfolio.adjustStockOnDay("GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange year = DateRange.CUSTOM_YEAR;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), year);

    // Plot for different date ranges
    String plotYear = portfolio.plot(year);
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
    portfolio.adjustStockOnDay("AAPL", 10,
            LocalDate.of(2024, 6, 1));
    portfolio.adjustStockOnDay("GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange fiveYears = DateRange.CUSTOM_FIVE_YEARS;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), fiveYears);

    // Plot for different date ranges
    String plotFiveYears = portfolio.plot(fiveYears);
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
    portfolio.adjustStockOnDay("AAPL", 10,
            LocalDate.of(2024, 6, 1));
    portfolio.adjustStockOnDay("GOOG", 5,
            LocalDate.of(2024, 6, 1));

    DateRange tenYears = DateRange.CUSTOM_TEN_YEARS;
    DateRange.setCustomRange(LocalDate.of(2024, 6, 13), tenYears);

    // Plot for different date ranges
    String plotTenYears = portfolio.plot(tenYears);
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
  public void testRebalanceStockToExpectedValue() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.of(2024, 5, 29);
    portfolio.adjustStockOnDay("AAPL", 100, date);

    double expectedValue = 2000.0; // Assuming $20 per share
    try {
      portfolio.rebalanceStock(expectedValue, date, "AAPL");
    } catch (IllegalArgumentException e) {
      //nothing needed
    }

    assertEquals(expectedValue, portfolio.getValue(date), 0.001);
  }

  @Test
  public void testRebalanceStockWithMultipleDates() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date1 = LocalDate.now().minusDays(2);
    LocalDate date2 = LocalDate.now().minusDays(1);
    LocalDate date3 = LocalDate.now();

    portfolio.adjustStockOnDay("AAPL", 100, date1);
    portfolio.adjustStockOnDay("AAPL", 50, date2);
    portfolio.adjustStockOnDay("AAPL", 25, date3);

    double expectedValue = 2500.0; // Adjust according to expected value
    try {
      portfolio.rebalanceStock(expectedValue, date3, "AAPL");
    } catch (IllegalArgumentException e) {
      //nothing needed
    }

    assertEquals(expectedValue, portfolio.getValue(date3), 0.001);
  }

  @Test
  public void testRebalanceStockInvalidStockName() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.now().minusDays(1);
    portfolio.adjustStockOnDay("AAPL", 100, date);

    try {
      portfolio.rebalanceStock(2000.0, date, "GOOG");
    } catch (IllegalArgumentException e) {
      assertEquals("** STOCK NOT FOUND, CHECK TICKER FORMAT **", e.getMessage());
    }
  }

  @Test
  public void testRebalanceStockFutureDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate futureDate = LocalDate.now().plusDays(7);

    portfolio.adjustStockOnDay("AAPL", 100, LocalDate.now());

    try {
      portfolio.rebalanceStock(2000.0, futureDate, "AAPL");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testDistributionOnValidDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.of(2024, 5, 29);
    portfolio.adjustStockOnDay("AAPL", 100, date);
    portfolio.adjustStockOnDay("GOOG", 50, date);

    String expectedOutput = "AAPL: $19029.0 — 68%\n" +
            "GOOG: $8870.0 — 32%\n";
    String actualOutput = portfolio.distribution(date);

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testDistributionNoStocksOnDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.now().minusDays(1);

    String expectedOutput = "\n** NO STOCKS IN Untitled Portfolio ON " + date + " **";
    String actualOutput = portfolio.distribution(date);

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testDistributionFutureDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate futureDate = LocalDate.now().plusDays(7);

    portfolio.adjustStockOnDay("AAPL", 100, LocalDate.now());

    try {
      portfolio.distribution(futureDate);
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetCompositionOnValidDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.now().minusDays(1);
    portfolio.adjustStockOnDay("AAPL", 100, date);
    portfolio.adjustStockOnDay("GOOG", 50, date);

    String expectedOutput = "AAPL: 100.0 shares, GOOG: 50.0 shares, ";
    String actualOutput = portfolio.getCompositionOnDay(date);

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testGetCompositionNoStocksOnDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate date = LocalDate.now().minusDays(1);

    try {
      portfolio.getCompositionOnDay(date);
    } catch (IllegalArgumentException e) {
      assertEquals("** NO STOCKS IN PORTFOLIO ON " + date + " **", e.getMessage());
    }
  }

  @Test
  public void testGetCompositionFutureDate() {
    AdvancedStockPortfolio portfolio = new AdvancedStockPortfolio();
    LocalDate futureDate = LocalDate.now().plusDays(7);

    portfolio.adjustStockOnDay("AAPL", 100, LocalDate.now());

    try {
      portfolio.getCompositionOnDay(futureDate);
    } catch (IllegalArgumentException e) {
      assertEquals("** EASY THERE, THIS PROGRAM CANNOT TELL THE FUTURE **", e.getMessage());
    }
  }

}





