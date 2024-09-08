package mvc.model;


import org.junit.Test;

import stocks.StockData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The SimpleStockManagerTest class represent test for the model and
 * the model's methods.
 */
public class SimpleStockManagerTest extends AbstractStockManagerTest<SimpleStockManager> {


  public SimpleStockManager createRealStockManager() {
    return new SimpleStockManager();
  }

  @Override
  public SimpleStockManager createMockStockManager() {
    return new SimpleStockManager() {
      @Override
      protected List<StockData> getData(String ticker, LocalDate start, int x) {
        earliestDataCheck(start, x, mockData);
        return mockData;
      }
    };
  }

  @Test
  public void testAdjustStock_RemoveStock() {
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio", "AAPL", -10);
    assertTrue(realManager.find("MyPortfolio").getStocks().isEmpty());
  }

  @Test
  public void testGetPortfolioValue_AfterAddingStocks() {
    // Add stocks to the portfolio
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio", "GOOG", 20);
    realManager.adjustPortfolio("MyPortfolio", "MSFT", 30);

    // Real Values
    double priceOfAAPL1 = 190.29;
    double priceOfGOOG1 = 177.40;
    double priceOfMSFT1 = 429.17;
    double priceOfAAPL2 = 321.85;
    double priceOfGOOG2 = 1431.82;
    double priceOfMSFT2 = 182.83;
    double priceOfAAPL3 = 177.57;
    double priceOfGOOG3 = 2893.59;
    double priceOfMSFT3 = 336.32;

    // Corresponding Dates
    LocalDate date1 = LocalDate.of(2024, 05, 29);
    LocalDate date2 = LocalDate.of(2020, 06, 01);
    LocalDate date3 = LocalDate.of(2022, 01, 01); // Rollback Day

    // Expected Total Values
    double expectedValue1 = 10 * priceOfAAPL1 + 20 * priceOfGOOG1 + 30 * priceOfMSFT1;
    double expectedValue2 = 10 * priceOfAAPL2 + 20 * priceOfGOOG2 + 30 * priceOfMSFT2;
    double expectedValue3 = 10 * priceOfAAPL3 + 20 * priceOfGOOG3 + 30 * priceOfMSFT3;

    assertEquals(expectedValue1, realManager.getPortfolioValue("MyPortfolio", date1), 0.001);
    assertEquals(expectedValue2, realManager.getPortfolioValue("MyPortfolio", date2), 0.001);
    assertEquals(expectedValue3, realManager.getPortfolioValue("MyPortfolio", date3), 0.001);
  }

  @Test
  public void testAdjustStock_AddMultipleStocks() {
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio", "GOOG", 20);
    realManager.adjustPortfolio("MyPortfolio", "MSFT", 30);
    assertEquals("AAPL", realManager.find("MyPortfolio").getStocks().get(0).getTicker());
    assertEquals(10, realManager.find("MyPortfolio").getStocks().get(0).getShares(), 0.001);
    assertEquals("GOOG", realManager.find("MyPortfolio").getStocks().get(1).getTicker());
    assertEquals(20, realManager.find("MyPortfolio").getStocks().get(1).getShares(), 0.001);
    assertEquals("MSFT", realManager.find("MyPortfolio").getStocks().get(2).getTicker());
    assertEquals(30,realManager.find("MyPortfolio").getStocks().get(2).getShares(), 0.001);
    assertEquals("AAPL: 10.0, GOOG: 20.0, MSFT: 30.0, ",
            realManager.listStocksInPortfolio("MyPortfolio"));
  }

  @Test
  public void testAdjustStock_FractionalShares() {
    realManager.addPortfolio("MyPortfolio");
    try {
      realManager.adjustPortfolio("MyPortfolio", "AAPL", 10.5);
      fail("Expected IllegalArgumentException for fractional shares");
    } catch (IllegalArgumentException e) {
      assertEquals("** FRACTIONAL SHARES NOT ALLOWED FOR SIMPLE PORTFOLIOS **", e.getMessage());
    }
  }

  @Test
  public void testGetValue_AfterAddingAndRemovingStocks() {
    realManager.addPortfolio("MyPortfolio");
    // Add stocks to the portfolio
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio", "GOOG", 20);
    realManager.adjustPortfolio("MyPortfolio", "MSFT", 30);
    realManager.adjustPortfolio("MyPortfolio", "GOOG", -10);

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

    assertEquals(expectedValue1, realManager.getPortfolioValue("MyPortfolio", date1), 0.001);
    assertEquals(expectedValue2, realManager.getPortfolioValue("MyPortfolio", date2), 0.001);
    assertEquals(expectedValue3, realManager.getPortfolioValue("MyPortfolio", date3), 0.001);
  }

  @Test
  public void testAdjustStock_AddNewStock() {
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);

    assertEquals("AAPL", realManager.find("MyPortfolio").getStocks().get(0).getTicker());
    assertEquals(10, realManager.find("MyPortfolio").getStocks().get(0).getShares(), 0.001);
    assertEquals("AAPL: 10.0, ", realManager.listStocksInPortfolio("MyPortfolio"));
  }

  @Test
  public void testAdjustStock_AddToExistingStock() {
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio", "AAPL", 5);
    assertEquals("AAPL", realManager.find("MyPortfolio").getStocks().get(0).getTicker());
    assertEquals(15, realManager.find("MyPortfolio").getStocks().get(0).getShares(), 0.001);
    assertEquals("AAPL: 15.0, ", realManager.listStocksInPortfolio("MyPortfolio"));
  }


}



