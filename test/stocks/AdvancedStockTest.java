package stocks;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The StockTest class represent tests for Stock objects and
 * associated methods.
 */
public class AdvancedStockTest {

  private AdvancedStock mockStock;
  private AdvancedStock realStockAAPL;
  private AdvancedStock realStockGOOGL;

  @Before
  public void setUp() {
    // Mock data for testing
    List<StockData> mockData = new ArrayList<>();
    mockData.add(new DailyStockData(LocalDate.of(2023, 6, 2), 0, 105.0, 0, 0, 0));
    mockData.add(new DailyStockData(LocalDate.of(2023, 6, 5), 0, 110.0, 0, 0, 0));
    // 6/3 and 6/4 are weekends (non-market days)
    mockData.add(new DailyStockData(LocalDate.of(2023, 6, 6), 0, 115.0, 0, 0, 0));
    mockData.add(new DailyStockData(LocalDate.of(2023, 6, 1), 0, 100.0, 0, 0, 0));

    // Replace the actual data fetching method with mock data
    mockStock = new AdvancedStock("MOCK") {
      @Override
      protected List<StockData> getData(String ticker) {
        return mockData;
      }
    };

    // Real stocks for testing API/CSV data
    realStockAAPL = new AdvancedStock("AAPL");
    realStockGOOGL = new AdvancedStock("GOOGL");
  }

  @Test
  public void testStockConstructionWithApiAndMockData() {
    // Construct a Stock object with API/CSV data
    Stock stockWithApiData = new AdvancedStock("AAPL");
    // Construct a Stock object with mock data
    Stock stockWithMockData = new AdvancedStock("GOOGL");

    // Ensure that both stocks are properly constructed
    assertEquals("AAPL", stockWithApiData.getTicker());
    assertEquals("GOOGL", stockWithMockData.getTicker());
    assertEquals(0.0, stockWithApiData.getShares(), 0.001);
    assertEquals(0.0, stockWithMockData.getShares(), 0.001);
  }

  @Test
  public void testStockConstructionWithImproperFormat() {
    Stock lowCaseTick1 = new AdvancedStock("goog");
    Stock lowCaseTick2 = new AdvancedStock("aApL");

    assertEquals(lowCaseTick1.getTicker(), "GOOG");
    assertEquals(lowCaseTick2.getTicker(), "AAPL");
  }

  @Test
  public void testGetPriceOnMarketDay() {
    double price = mockStock.getPriceOnDay(LocalDate.of(2023, 6, 2));
    assertEquals("Price on 2023-06-02 should be 105.0", 105.0, price, 0.001);
  }

  @Test
  public void testGetPriceOnNonMarketDay() {
    double price = mockStock.getPriceOnDay(LocalDate.of(2023, 6, 3)); // Saturday
    assertEquals("Price on 2023-06-03 (Saturday) "
            + "should be the previous market day's price 105.0", 105.0, price, 0.001);
  }

  @Test
  public void testGetPriceInFuture() {
    LocalDate futureDate = LocalDate.of(2060, 1, 1);
    try {
      mockStock.getPriceOnDay(futureDate);
      fail("Expected IllegalArgumentException for future date");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetPriceTooFarInPast() {
    LocalDate pastDate = LocalDate.of(2020, 1, 1);
    try {
      mockStock.getPriceOnDay(pastDate);
      fail("Expected IllegalArgumentException for date too far in the past");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 2020-01-01 TRY A MORE RECENT DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetPriceOnVariousValidDays() {
    double price1 = mockStock.getPriceOnDay(LocalDate.of(2023, 6, 1));
    double price2 = mockStock.getPriceOnDay(LocalDate.of(2023, 6, 6));

    assertEquals("Price on 2023-06-01 should be 100.0", 100.0, price1, 0.001);
    assertEquals("Price on 2023-06-06 should be 115.0", 115.0, price2, 0.001);
  }

  @Test
  public void testGetPriceOnNonMarketDayWithMultipleFallbacks() {
    double price = mockStock.getPriceOnDay(LocalDate.of(2023, 6, 4)); // Sunday
    assertEquals("Price on 2023-06-04 (Sunday) should fallback to "
            + "2023-06-02 price 105.0", 105.0, price, 0.001);
  }

  // Tests using real API/CSV data for AAPL
  @Test
  public void testGetPriceOnMarketDayRealDataAAPL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price = realStockAAPL.getPriceOnDay(LocalDate.of(2023, 1, 3));
      assertEquals("Price should be 125.07", 125.07, price, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testGetPriceOnNonMarketDayRealDataAAPL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price = realStockAAPL.getPriceOnDay(
              LocalDate.of(2023, 1, 1)); // New Year's Day (non-market day)
      assertEquals("Price should fall back to 129.93.", 129.93, price, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testGetPriceTooFarInPastRealDataAAPL() {
    // WARNING: This test requires internet connection or a valid CSV file
    LocalDate pastDate = LocalDate.of(1900, 1, 1);
    try {
      realStockAAPL.getPriceOnDay(pastDate);
      fail("Expected IllegalArgumentException for date too far in the past");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1900-01-01 TRY A MORE RECENT DATE **",
              e.getMessage());
    }
  }

  @Test
  public void testGetPriceInFutureRealDataAAPL() {
    // WARNING: This test requires internet connection or a valid CSV file
    LocalDate futureDate = LocalDate.of(2030, 1, 1);
    try {
      realStockAAPL.getPriceOnDay(futureDate);
      fail("Expected IllegalArgumentException for future date");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **",
              e.getMessage());
    }
  }

  @Test
  public void testGetPriceOnVariousValidDaysRealDataAAPL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price1 = realStockAAPL.getPriceOnDay(LocalDate.of(2023, 1, 4));
      double price2 = realStockAAPL.getPriceOnDay(LocalDate.of(2023, 1, 5));

      assertEquals("Price on 2023-01-04 should be 126.36", 126.36, price1, 0.001);
      assertEquals("Price on 2023-01-05 should be 125.02", 125.02, price2, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  // Tests using real API/CSV data for GOOGL
  @Test
  public void testGetPriceOnMarketDayRealDataGOOGL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price = realStockGOOGL.getPriceOnDay(LocalDate.of(2023, 6, 6));
      assertEquals("Price should be 127.91", 127.31, price, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testGetPriceOnNonMarketDayRealDataGOOGL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price = realStockGOOGL.getPriceOnDay(
              LocalDate.of(2024, 1, 1)); // New Year's Day (non-market day)
      assertEquals("Price should fall back to 140.93", 139.69, price, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testGetPriceTooFarInPastRealDataGOOGL() {
    // WARNING: This test requires internet connection or a valid CSV file
    LocalDate pastDate = LocalDate.of(1900, 1, 1);
    try {
      realStockGOOGL.getPriceOnDay(pastDate);
      fail("Expected IllegalArgumentException for date too far in the past");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1900-01-01 TRY A MORE RECENT DATE **",
              e.getMessage());
    }
  }

  @Test
  public void testGetPriceInFutureRealDataGOOGL() {
    // WARNING: This test requires internet connection or a valid CSV file
    LocalDate futureDate = LocalDate.of(2030, 1, 1);
    try {
      realStockGOOGL.getPriceOnDay(futureDate);
      fail("Expected IllegalArgumentException for future date");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **",
              e.getMessage());
    }
  }

  @Test
  public void testGetPriceOnVariousValidDaysRealDataGOOGL() {
    // WARNING: This test requires internet connection or a valid CSV file
    try {
      double price1 = realStockGOOGL.getPriceOnDay(LocalDate.of(2023, 1, 4));
      double price2 = realStockGOOGL.getPriceOnDay(LocalDate.of(2024, 1, 20));

      assertEquals("Price on 2023-01-04 should be 88.08", 88.08, price1, 0.001);
      assertEquals("Price on 2023-01-05 should be ", 146.38, price2, 0.001);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testGetTicker() {
    assertEquals("MOCK", mockStock.getTicker());
    assertEquals("GOOGL", realStockGOOGL.getTicker());
  }

  @Test
  public void testGetShares() {
    // Initial shares are 0 for all stocks
    assertEquals(0.0, mockStock.getShares(), 0.001);
    assertEquals(0.0, realStockAAPL.getShares(), 0.001);
    assertEquals(0.0, realStockGOOGL.getShares(), 0.001);

    // Adjust shares and test again
    mockStock.adjustShares(10.0);
    realStockAAPL.adjustShares(20.0);
    realStockGOOGL.adjustShares(30.0);

    assertEquals(10.0, mockStock.getShares(), 0.001);
    assertEquals(20.0, realStockAAPL.getShares(), 0.001);
    assertEquals(30.0, realStockGOOGL.getShares(), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustSharesNegativeTotalShares() {
    // Adjusting shares to a negative value causing total shares to be negative
    mockStock.adjustShares(-15.0);
  }

  @Test
  public void testAdjustSharesDoubleValue() {
    // Trying to adjust shares with a double value, which is supported by stocks
    // just not AdvancedStockPortfolios
    mockStock.adjustShares(5.5);
    assertEquals(mockStock.getShares(), 5.5, 0.001);
  }

  @Test
  public void testAdjustSharesAddingSharesMultipleTimes() {
    // Adding shares multiple times
    realStockAAPL.adjustShares(10.0);
    realStockAAPL.adjustShares(100.0);
    realStockAAPL.adjustShares(2.0);
    realStockAAPL.adjustShares(5.0);
    assertEquals(117.0, realStockAAPL.getShares(), 0.001);
  }

  @Test
  public void testAdjustSharesRemoveAndAddShares() {
    // Removing shares and then adding shares
    realStockGOOGL.adjustShares(100);
    realStockGOOGL.adjustShares(-10.0);
    realStockGOOGL.adjustShares(5.0);
    assertEquals(95.0, realStockGOOGL.getShares(), 0.001);
  }

  @Test
  public void testAdjustSharesNegativeValue() {
    // Adjusting shares with a negative value, but there are enough shares to remove
    realStockGOOGL.adjustShares(20.0);
    realStockGOOGL.adjustShares(-15.0);
    assertEquals(5.0, realStockGOOGL.getShares(), 0.001);
  }

  @Test
  public void testAdjustSharesOnDay_AddShares() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    assertEquals(10.0, mockStock.getShares(), 0.001);
    assertEquals(10.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2)), 0.001);
  }

  @Test
  public void testAdjustSharesOnDay_RemoveShares() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(-5.0, LocalDate.of(2023, 6, 2));
    assertEquals(5.0, mockStock.getShares(), 0.001);
    assertEquals(5.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2)), 0.001);
  }

  @Test
  public void testAdjustSharesOnDay_ZeroShares() {
    try {
      mockStock.adjustSharesOnDay(0.0, LocalDate.of(2023, 6, 2));
      fail("Expected IllegalArgumentException for zero shares");
    } catch (IllegalArgumentException e) {
      assertEquals("** A TRANSACTION MUST ADD OR SUBTRACT SHARES **", e.getMessage());
    }
  }

  @Test
  public void testAdjustSharesOnDay_AddSharesOnDifferentDays() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    assertEquals(15.0, mockStock.getShares(), 0.001);
    assertEquals(10.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2)), 0.001);
    assertEquals(15.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 5)), 0.001);
  }

  @Test
  public void testAdjustSharesOnDay_AddAndRemoveSharesOnDifferentDays() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 6));
    assertEquals(12.0, mockStock.getShares(), 0.001);
    assertEquals(10.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2)), 0.001);
    assertEquals(15.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 5)), 0.001);
    assertEquals(12.0, mockStock.getSharesOnDay(LocalDate.of(2023, 6, 6)), 0.001);
  }

  @Test
  public void testAdjustSharesOnDay_TransactionsRecorded() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 6));

    String expectedTransactions = "MOCK,Buy,10.0,10.0,2023-06-02\n" +
            "MOCK,Buy,5.0,15.0,2023-06-05\n" +
            "MOCK,Sell,-3.0,12.0,2023-06-06\n";
    assertEquals(expectedTransactions, mockStock.getTransactions());
  }

  @Test
  public void testAdjustSharesOnDay_DateInFarPast() {
    try {
      mockStock.adjustSharesOnDay(10.0, LocalDate.of(1800, 1, 1));
      fail("Expected IllegalArgumentException for a date in the far past");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1800-01-01 TRY A MORE RECENT DATE **", e.getMessage());
    }
  }

  @Test
  public void testAdjustSharesOnDay_DateInFuture() {
    try {
      mockStock.adjustSharesOnDay(10.0, LocalDate.of(2100, 1, 1));
      fail("Expected IllegalArgumentException for a date in the future");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testAdjustSharesOnDay_NegativeShares() {
    try {
      mockStock.adjustSharesOnDay(-15.0, LocalDate.of(2023, 6, 2));
      fail("Expected IllegalArgumentException for negative shares with no prior shares to sell");
    } catch (IllegalArgumentException e) {
      assertEquals("** YOU CANNOT HAVE NEGATIVE SHARES OF A STOCK **", e.getMessage());
    }
  }

  @Test
  public void testGetValueOnDate_BeforeFirstTransaction() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    try {
      mockStock.getValueOnDate(LocalDate.of(1900, 6, 1));
      fail("Expected IllegalArgumentException for a date before the first transaction");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1900-06-01 TRY A MORE RECENT DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetValueOnDate_DateInFuture() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    try {
      mockStock.getValueOnDate(LocalDate.of(2100, 1, 1));
      fail("Expected IllegalArgumentException for a date in the future");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetValueOnDate_SingleTransaction() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    double expectedValue = 10.0 * 105.0;
    assertEquals(expectedValue, mockStock.getValueOnDate(LocalDate.of(2023, 6, 2)), 0.001);
  }

  @Test
  public void testGetValueOnDate_MultipleTransactions() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    double expectedValue = (10.0 + 5.0) * 110.0;
    assertEquals(expectedValue, mockStock.getValueOnDate(LocalDate.of(2023, 6, 5)), 0.001);
  }

  @Test
  public void testGetValueOnDate_MultipleTransactionsWithSell() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 6));
    double expectedValue = (10.0 + 5.0 - 3.0) * 115.0;
    assertEquals(expectedValue, mockStock.getValueOnDate(LocalDate.of(2023, 6, 6)), 0.001);
  }

  @Test
  public void testGetValueOnDate_FarPastDate() {
    try {
      mockStock.getValueOnDate(LocalDate.of(1800, 1, 1));
      fail("Expected IllegalArgumentException for a date in the far past");
    } catch (IllegalArgumentException e) {
      assertEquals("** NO DATA AVAILABLE FOR 1800-01-01 TRY A MORE RECENT DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetSharesOnDay_NoTransactions() {
    double shares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    assertEquals(0.0, shares, 0.001);
  }

  @Test
  public void testGetSharesOnDay_BeforeFirstTransaction() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    double shares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 1));
    assertEquals(0.0, shares, 0.001);
  }

  @Test
  public void testGetSharesOnDay_SingleTransaction() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    double shares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    assertEquals(10.0, shares, 0.001);
  }

  @Test
  public void testGetSharesOnDay_MultipleTransactions() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    double shares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 5));
    assertEquals(15.0, shares, 0.001);
  }

  @Test
  public void testGetSharesOnDay_MultipleTransactionsWithSell() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 6));
    double shares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 6));
    assertEquals(12.0, shares, 0.001);
  }

  @Test
  public void testGetSharesOnDay_FarPastDate() {
    double shares = mockStock.getSharesOnDay(LocalDate.of(1800, 1, 1));
    assertEquals(0.0, shares, 0.001);
  }

  @Test
  public void testGetTransactions_NoTransactions() {
    String transactions = mockStock.getTransactions();
    assertEquals("", transactions);
  }

  @Test
  public void testGetTransactions_SingleBuyTransaction() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    String transactions = mockStock.getTransactions();
    String expected = "MOCK,Buy,10.0,10.0,2023-06-02" + System.lineSeparator();
    assertEquals(expected, transactions);
  }

  @Test
  public void testGetTransactions_MultipleTransactions() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 6));

    String transactions = mockStock.getTransactions();
    String expected =
            "MOCK,Buy,10.0,10.0,2023-06-02" + System.lineSeparator() +
                    "MOCK,Buy,5.0,15.0,2023-06-05" + System.lineSeparator() +
                    "MOCK,Sell,-3.0,12.0,2023-06-06" + System.lineSeparator();
    assertEquals(expected, transactions);
  }

  @Test
  public void testGetTransactions_BuyAndSellTransactions() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    mockStock.adjustSharesOnDay(-3.0, LocalDate.of(2023, 6, 5));
    mockStock.adjustSharesOnDay(5.0, LocalDate.of(2023, 6, 6));

    String transactions = mockStock.getTransactions();
    String expected =
            "MOCK,Buy,10.0,10.0,2023-06-02" + System.lineSeparator() +
                    "MOCK,Sell,-3.0,7.0,2023-06-05" + System.lineSeparator() +
                    "MOCK,Buy,5.0,12.0,2023-06-06" + System.lineSeparator();
    assertEquals(expected, transactions);
  }

  @Test
  public void testRebalanceStock_AddShares() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    double initialShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double initialValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    double expectedValue = initialValue + 1050.0; // Expected value after adding shares
    mockStock.rebalanceStock(expectedValue, LocalDate.of(2023, 6, 2));

    double newShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double newValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    assertTrue(newValue > initialValue);
    assertEquals(expectedValue, newValue, 0.01);
    assertTrue(newShares > initialShares);
  }

  @Test
  public void testRebalanceStock_RemoveShares() {
    mockStock.adjustSharesOnDay(20.0, LocalDate.of(2023, 6, 2));
    double initialShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double initialValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    double expectedValue = initialValue - 525.0; // Expected value after removing shares
    mockStock.rebalanceStock(expectedValue, LocalDate.of(2023, 6, 2));

    double newShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double newValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    assertTrue(newValue < initialValue);
    assertEquals(expectedValue, newValue, 0.01);
    assertTrue(newShares < initialShares);
  }

  @Test
  public void testRebalanceStock_NoChange() {
    mockStock.adjustSharesOnDay(10.0, LocalDate.of(2023, 6, 2));
    double initialShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double initialValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    double expectedValue = initialValue; // No change in value
    mockStock.rebalanceStock(expectedValue, LocalDate.of(2023, 6, 2));

    double newShares = mockStock.getSharesOnDay(LocalDate.of(2023, 6, 2));
    double newValue = mockStock.getValueOnDate(LocalDate.of(2023, 6, 2));

    assertEquals(initialValue, newValue, 0.01);
    assertEquals(initialShares, newShares, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceStock_NoDataForDate() {
    // Attempt to rebalance stock on a date for which there is no data
    mockStock.rebalanceStock(1000.0, LocalDate.of(2023, 5, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceStock_FutureDate() {
    // Attempt to rebalance stock on a future date
    mockStock.rebalanceStock(1000.0, LocalDate.of(2025, 1, 1));
  }
}


