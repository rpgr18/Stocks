package stocks;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * The DailyStockDataTest class represents tests for DailyStockData objects
 * and associated methods.
 */
public class DailyStockDataTest {

  @Test
  public void testConstructor() {
    // Test if the constructor correctly initializes all fields
    LocalDate date = LocalDate.of(2024, 6, 6);
    double open = 100.0;
    double close = 105.0;
    double high = 110.0;
    double low = 95.0;
    int volume = 100000;

    DailyStockData stockData = new DailyStockData(date, open, close, high, low, volume);

    // Check if all fields are correctly initialized
    assertEquals(date, stockData.getDate());
    assertEquals(open, stockData.getOpen(), 0.001);
    assertEquals(close, stockData.getClose(), 0.001);
    assertEquals(high, stockData.getHigh(), 0.001);
    assertEquals(low, stockData.getLow(), 0.001);
    assertEquals(volume, stockData.getVolume());
  }

  @Test
  public void testGetDate() {
    // Test if getDate() returns the correct date
    LocalDate date = LocalDate.of(2024, 6, 6);
    DailyStockData stockData = new DailyStockData(date, 100.0,
            105.0, 110.0, 95.0, 100000);
    assertEquals(date, stockData.getDate());
  }

  @Test
  public void testGetOpen() {
    // Test if getOpen() returns the correct opening value
    double open = 100.0;
    DailyStockData stockData = new DailyStockData(LocalDate.now(), open,
            105.0, 110.0, 95.0, 100000);
    assertEquals(open, stockData.getOpen(), 0.001);
  }

  @Test
  public void testGetClose() {
    // Test if getClose() returns the correct closing value
    double close = 105.0;
    DailyStockData stockData = new DailyStockData(LocalDate.now(), 100.0,
            close, 110.0, 95.0, 100000);
    assertEquals(close, stockData.getClose(), 0.001);
  }

  @Test
  public void testGetHigh() {
    // Test if getHigh() returns the correct high value
    double high = 110.0;
    DailyStockData stockData = new DailyStockData(LocalDate.now(), 100.0,
            105.0, high, 95.0, 100000);
    assertEquals(high, stockData.getHigh(), 0.001);
  }

  @Test
  public void testGetLow() {
    // Test if getLow() returns the correct low value
    double low = 95.0;
    DailyStockData stockData = new DailyStockData(LocalDate.now(), 100.0,
            105.0, 110.0, low, 100000);
    assertEquals(low, stockData.getLow(), 0.001);
  }

  @Test
  public void testGetVolume() {
    // Test if getVolume() returns the correct trading volume
    int volume = 100000;
    DailyStockData stockData = new DailyStockData(LocalDate.now(), 100.0,
            105.0, 110.0, 95.0, volume);
    assertEquals(volume, stockData.getVolume());
  }

}
