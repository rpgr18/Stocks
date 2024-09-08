package portfolios;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Abstract test class for portfolios.
 */
public abstract class AbstractPortfolioTest<T extends SimpleStockPortfolio> {

  protected T portfolio;

  abstract T createPortfolio();

  @Before
  public void setUp() {
    portfolio = createPortfolio();
  }

  @Test
  public void testNewPortfolioName() {
    assertEquals("Untitled Portfolio", portfolio.getName());
  }

  @Test
  public void testNewPortfolioEmptyStocks() {
    assertTrue(portfolio.listStocks().isEmpty());
  }

  @Test
  public void testNewPortfolioValue() {
    assertEquals(0.0, portfolio.getValue(LocalDate.now()), 0.001);
  }

  @Test
  public void testAdjustStock_NonexistentStock() {
    try {
      portfolio.adjustStock("sdfafhsfghsfgad", 10);
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertEquals("** STOCK NOT FOUND, CHECK TICKER FORMAT **", e.getMessage());
    }
  }

  @Test
  public void testListStocks_EmptyPortfolio() {
    assertEquals("", portfolio.listStocks());
  }

  @Test
  public void testSetName() {
    portfolio.setName("My Portfolio");
    assertEquals("My Portfolio", portfolio.getName());
  }

  @Test
  public void testGetName() {
    assertEquals("Untitled Portfolio", portfolio.getName());
  }

  @Test
  public void testGetValue_AfterClearingPortfolio() {
    portfolio.adjustStock("AAPL", 10);
    portfolio.adjustStock("GOOG", 20);
    portfolio.adjustStock("MSFT", 30);
    portfolio.adjustStock("AAPL", -10);
    portfolio.adjustStock("GOOG", -20);
    portfolio.adjustStock("MSFT", -30);

    LocalDate date1 = LocalDate.of(2024, 05, 29);
    LocalDate date2 = LocalDate.of(2020, 06, 01);
    LocalDate date3 = LocalDate.of(2022, 12, 12);

    //Always 0
    assertEquals(0.0, portfolio.getValue(date1), 0.001);
    assertEquals(0.0, portfolio.getValue(date2), 0.001);
    assertEquals(0.0, portfolio.getValue(date3), 0.001);
  }


}
