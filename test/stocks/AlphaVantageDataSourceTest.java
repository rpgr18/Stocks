package stocks;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The AlphaVantageDataSourceTest class represents tests for methods on
 * AlphaVantageDataSource objects. This class extends the CSVSourcesTest class
 * and has tests unique tests.
 */
public class AlphaVantageDataSourceTest extends CSVSourcesTest {

  @Override
  protected CSVSources getCSVDataSource() {
    return new AlphaVantageDataSource();
  }

  /**
   * The testGetHistoryData method tests that an AlphaVantageDataSource correctly
   * assemble a list of StockData when calling the API.
   * WARNING: This test only works when connected to the internet and with a valid API key.
   */
  @Test
  public void testGetValidHistoryData() {
    StockDataSource dataSource = new AlphaVantageDataSource();
    List<StockData> stockDataList = dataSource.getHistoryData("AAPL");

    // Test that data is not null and not empty
    assertEquals(false, stockDataList.isEmpty());
    // Print size
    System.out.print(stockDataList.size());

    // Test last data point
    StockData lastData = stockDataList.get(stockDataList.size() - 1);
    assertEquals(LocalDate.of(1999, 11, 1), lastData.getDate());
    assertEquals(80, lastData.getOpen(), 0.001);
    assertEquals(80.69, lastData.getHigh(), 0.001);
    assertEquals(77.37, lastData.getLow(), 0.001);
    assertEquals(77.62, lastData.getClose(), 0.001);
    assertEquals(2487300, lastData.getVolume());

    // Test a random data point, reversing the list so that the index of items from the front
    // of the list is constant even as the list changes (new data point added every day).
    // by reversing the list and getting an index from the back of the list this test
    // will work even when the list invitable increases in length by 1 each day.
    List<StockData> constantStockDataList = dataSource.getHistoryData("AAPL");
    Collections.reverse(constantStockDataList);
    StockData midData = constantStockDataList.get(567);
    assertEquals(LocalDate.of(2002, 2, 6), midData.getDate());
    assertEquals(25.61, midData.getOpen(), 0.001);
    assertEquals(25.98, midData.getHigh(), 0.001);
    assertEquals(24.15, midData.getLow(), 0.001);
    assertEquals(24.67, midData.getClose(), 0.001);
    assertEquals(10671000, midData.getVolume());
  }

  /**
   * The testGetInvalidHistoryData ensure that if an invalid ticker is entered
   * the proper exception is thrown.
   */
  @Test
  public void testGetInvalidHistoryData() {
    try {
      AlphaVantageDataSource dataSource = new AlphaVantageDataSource();
      dataSource.getHistoryData("asdadfag");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** STOCK NOT FOUND, CHECK TICKER FORMAT **");
    }
  }

}
