package portfolios;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The SimpleStockPortfolioTest class represents tests for methods on
 * SimpleStockPortfolio objects.
 */
public class SimpleStockPortfolioTest extends AbstractPortfolioTest<SimpleStockPortfolio> {


  protected SimpleStockPortfolio createPortfolio() {
    return new SimpleStockPortfolio();
  }

  @Test
  public void testAdjustStock_LargeNumberOfOperations() {
    // Add stocks
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    // Subtract stocks
    portfolio.adjustStock("AAPL", -5);
    portfolio.adjustStock("GOOG", -10);
    portfolio.adjustStock("MSFT", -15);

    // Add again
    portfolio.adjustStock("AAPL", 15);
    portfolio.adjustStock("GOOG", 25);
    portfolio.adjustStock("MSFT", 35);

    // Remove all
    portfolio.adjustStock("AAPL", -20);
    portfolio.adjustStock("GOOG", -35);
    portfolio.adjustStock("MSFT", -50);

    // Add some more
    portfolio.adjustStock("AAPL", 30);
    portfolio.adjustStock("GOOG", 45);
    portfolio.adjustStock("MSFT", 60);

    assertEquals(3, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(30, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("GOOG", portfolio.getStocks().get(1).getTicker());
    assertEquals(45, portfolio.getStocks().get(1).getShares(), 0.001);
    assertEquals("MSFT", portfolio.getStocks().get(2).getTicker());
    assertEquals(60, portfolio.getStocks().get(2).getShares(), 0.001);
    assertEquals("AAPL: 30.0, GOOG: 45.0, MSFT: 60.0, ", portfolio.listStocks());
  }

  @Test
  public void testAdjustStock_AddRemoveMultipleStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("GOOG", -10);
    assertEquals(3, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(10, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("GOOG", portfolio.getStocks().get(1).getTicker());
    assertEquals(10, portfolio.getStocks().get(1).getShares(), 0.001);
    assertEquals("MSFT", portfolio.getStocks().get(2).getTicker());
    assertEquals(30, portfolio.getStocks().get(2).getShares(), 0.001);
    assertEquals("AAPL: 10.0, GOOG: 10.0, MSFT: 30.0, ", portfolio.listStocks());
  }

  @Test
  public void testAdjustStock_AddToExistingStock() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("AAPL", 5);
    assertEquals(1, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(15, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("AAPL: 15.0, ", portfolio.listStocks());
  }

  @Test
  public void testAdjustStock_AddRemoveAllStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("AAPL", -10);
    portfolio.adjustStock("GOOG", -20);
    portfolio.adjustStock("MSFT", -30);

    assertTrue(portfolio.getStocks().isEmpty());
  }

  @Test
  public void testGetValue_IllegalFutureDate() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    LocalDate future = LocalDate.of(2030, 01, 01);
    try {
      portfolio.getValue(future);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
  }

  @Test
  public void testListStocks_AfterClearingPortfolio() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("AAPL", -10);
    portfolio.adjustStock("GOOG", -20);
    portfolio.adjustStock("MSFT", -30);

    assertEquals("", portfolio.listStocks());
  }

  @Test
  public void testGetValue_AfterAddingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    //Real Values
    double priceOfAAPL1 = 190.29;
    double priceOfGOOG1 = 177.40;
    double priceOfMSFT1 = 429.17;
    double priceOfAAPL2 = 321.85;
    double priceOfGOOG2 = 1431.82;
    double priceOfMSFT2 = 182.83;
    double priceOfAAPL3 = 177.57;
    double priceOfGOOG3 = 2893.59;
    double priceOfMSFT3 = 336.32;

    //Corresponding Dates
    LocalDate date1 = LocalDate.of(2024, 05, 29);
    LocalDate date2 = LocalDate.of(2020, 06, 01);
    LocalDate date3 = LocalDate.of(2022, 01, 01); // Rollback Day

    //Expected Total Values
    double expectedValue1 = 10 * priceOfAAPL1 + 20 * priceOfGOOG1 + 30 * priceOfMSFT1;
    double expectedValue2 = 10 * priceOfAAPL2 + 20 * priceOfGOOG2 + 30 * priceOfMSFT2;
    double expectedValue3 = 10 * priceOfAAPL3 + 20 * priceOfGOOG3 + 30 * priceOfMSFT3;

    assertEquals(expectedValue1, portfolio.getValue(LocalDate.of(2024, 05, 29)), 0.001);
    assertEquals(expectedValue3, portfolio.getValue(LocalDate.of(2022,
            01, 01)), 0.001); //RollBack Test
    assertEquals(expectedValue2, portfolio.getValue(LocalDate.of(2020, 06, 01)), 0.001);
  }

  @Test
  public void testAdjustStock_AddNewStock() {
    portfolio.adjustStock("AAPL", 10);
    assertEquals(1, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(10, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("AAPL: 10.0, ", portfolio.listStocks());
  }

  @Test
  public void testGetValue_AfterAddingAndRemovingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("GOOG", -10);

    //Real Values
    double priceOfAAPL1 = 190.29;
    double priceOfGOOG1 = 177.40;
    double priceOfMSFT1 = 429.17;
    double priceOfAAPL2 = 321.85;
    double priceOfGOOG2 = 1431.82;
    double priceOfMSFT2 = 182.83;
    double priceOfAAPL3 = 177.57;
    double priceOfGOOG3 = 2893.59;
    double priceOfMSFT3 = 336.32;

    //Corresponding Dates
    LocalDate date1 = LocalDate.of(2024, 05, 29);
    LocalDate date2 = LocalDate.of(2020, 06, 01);
    LocalDate date3 = LocalDate.of(2022, 01, 01); // Rollback Day

    //Expected Total Values
    double expectedValue1 = 10 * priceOfAAPL1 + 10 * priceOfGOOG1 + 30 * priceOfMSFT1;
    double expectedValue2 = 10 * priceOfAAPL2 + 10 * priceOfGOOG2 + 30 * priceOfMSFT2;
    double expectedValue3 = 10 * priceOfAAPL3 + 10 * priceOfGOOG3 + 30 * priceOfMSFT3;

    assertEquals(expectedValue1, portfolio.getValue(date1), 0.001);
    assertEquals(expectedValue2, portfolio.getValue(date2), 0.001);
    assertEquals(expectedValue3, portfolio.getValue(date3), 0.001);
  }

  @Test
  public void testGetStocks_AfterAddingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    assertEquals(3, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(10, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("GOOG", portfolio.getStocks().get(1).getTicker());
    assertEquals(20, portfolio.getStocks().get(1).getShares(), 0.001);
    assertEquals("MSFT", portfolio.getStocks().get(2).getTicker());
    assertEquals(30, portfolio.getStocks().get(2).getShares(), 0.001);
  }

  @Test
  public void testAdjustStock_NegativeShares() {
    try {
      // Attempting to adjust a stock with negative shares
      portfolio.adjustStock("AAPL", -50);
      fail("Expected IllegalArgumentException for negative shares");
    } catch (IllegalArgumentException e) {
      assertEquals("** YOU CANNOT HAVE NEGATIVE SHARES OF A STOCK **", e.getMessage());
    }
  }

  @Test
  public void testAdjustStock_FractionalShares() {
    try {
      portfolio.adjustStock("AAPL", 10.5);
      fail("Expected IllegalArgumentException for fractional shares");
    } catch (IllegalArgumentException e) {
      assertEquals("** FRACTIONAL SHARES NOT ALLOWED FOR SIMPLE PORTFOLIOS **", e.getMessage());
    }
  }

  @Test
  public void testListStocks_AfterAddingAndRemovingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("GOOG", -10);

    assertEquals("AAPL: 10.0, GOOG: 10.0, MSFT: 30.0, ", portfolio.listStocks());
  }

  @Test
  public void testAdjustStock_AddMultipleStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    assertEquals(3, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(10, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("GOOG", portfolio.getStocks().get(1).getTicker());
    assertEquals(20, portfolio.getStocks().get(1).getShares(), 0.001);
    assertEquals("MSFT", portfolio.getStocks().get(2).getTicker());
    assertEquals(30, portfolio.getStocks().get(2).getShares(), 0.001);
    assertEquals("AAPL: 10.0, GOOG: 20.0, MSFT: 30.0, ", portfolio.listStocks());
  }

  @Test
  public void testGetValue_IllegalPastDate() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    LocalDate past = LocalDate.of(1900, 01, 01);
    try {
      portfolio.getValue(past);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NO DATA AVAILABLE FOR 1900-01-01 TRY A MORE RECENT DATE **");
    }
  }

  @Test
  public void testListStocks_AfterAddingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);

    assertEquals("AAPL: 10.0, GOOG: 20.0, MSFT: 30.0, ", portfolio.listStocks());
  }

  @Test
  public void testGetValue_EmptyPortfolio() {
    assertEquals(0.0, portfolio.getValue(null), 0.001);
  }

  @Test
  public void testAdjustStock_RemoveStock() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("AAPL", -10);
    assertTrue(portfolio.getStocks().isEmpty());
  }

  @Test
  public void testGetStocks_AfterClearingPortfolio() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("AAPL", -10);
    portfolio.adjustStock("GOOG", -20);
    portfolio.adjustStock("MSFT", -30);

    assertTrue(portfolio.getStocks().isEmpty());
  }

  @Test
  public void testGetStocks_AfterAddingAndRemovingStocks() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("GOOG", -20);

    assertEquals(2, portfolio.getStocks().size());
    assertEquals("AAPL", portfolio.getStocks().get(0).getTicker());
    assertEquals(10, portfolio.getStocks().get(0).getShares(), 0.001);
    assertEquals("MSFT", portfolio.getStocks().get(1).getTicker());
    assertEquals(30, portfolio.getStocks().get(1).getShares(), 0.001);
  }



}