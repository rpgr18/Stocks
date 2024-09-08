package stocks;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The LocalCSVDataSourceTest class represents tests for methods on
 * LocalCSVDataSource objects. This class extends the CSVSourcesTest class
 * and has tests unique tests.
 */
public class LocalCSVDataSourceTest extends CSVSourcesTest {

  @Override
  protected CSVSources getCSVDataSource() {
    return new LocalCSVDataSource();
  }

  /**
   * The testGetHistoryData method tests that a LocalCSVDataSource correctly
   * assembles a list of StockData when calling the API.
   * WARNING: This test only works if there is a valid local CSV file. If there isn't, the
   * test will automatically download one and print a message. If this happens just run the
   * test again
   */
  @Test
  public void testGetHistoryData() {
    StockDataSource source;
    try {
      source = new LocalCSVDataSource();
      source.getHistoryData("AAPL");
    } catch (RuntimeException e) {
      System.out.print("Local CSV file for APPL not found, downloading one from API. "
              + "Run the test again to use the downloaded file and properly check that"
              + "LocalCSVDataSource works for a valid local file");
      source = new AlphaVantageDataSource();

    }

    List<StockData> stockDataList = source.getHistoryData("AAPL");

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
    List<StockData> constantStockDataList = source.getHistoryData("AAPL");
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
      StockDataSource dataSource = new LocalCSVDataSource();
      dataSource.getHistoryData("1234");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** UNABLE TO READ LOCAL CSV FILE FOR:1234 **");
    }
  }

}
