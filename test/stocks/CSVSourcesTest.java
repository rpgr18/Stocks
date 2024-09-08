package stocks;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The CSVSourcesTest class represents tests for methods on
 * CSVSources objects. This is an abstract test class.
 */
public abstract class CSVSourcesTest {
  private CSVSources dataSource;

  protected abstract CSVSources getCSVDataSource();

  @Before
  public void setUp() {
    dataSource = getCSVDataSource();
  }

  @Test
  public void testParseCSV() {
    String csvData =
            "timestamp,open,high,low,close,volume\n"
                    + "2024-06-05,195.4000,196.9000,194.8700,195.8700,54156785\n"
                    + "2024-06-04,194.6350,195.3200,193.0342,194.3500,47471445\n"
                    + "2024-06-03,192.9000,194.9900,192.5200,194.0300,50080539\n"
                    + "2024-05-31,191.4400,192.5700,189.9100,192.2500,75158277\n"
                    + "2024-05-30,190.7600,192.1800,190.6300,191.2900,49947941\n"
                    + "2024-05-29,189.6100,192.2470,189.5100,190.2900,53068016\n"
                    + "2024-05-28,191.5100,193.0000,189.1000,189.9900,52280051\n"
                    + "2024-05-24,188.8200,190.5800,188.0404,189.9800,36326975\n"
                    + "2024-05-23,190.9800,191.0000,186.6250,186.8800,51005924\n"
                    + "2024-05-22,192.2650,192.8231,190.2700,190.9000,34648547\n";

    List<StockData> stockDataList = dataSource.parseCSV(csvData);

    assertEquals(10, stockDataList.size());

    // Line 1
    StockData entry1 = stockDataList.get(0);
    assertEquals(LocalDate.of(2024, 06, 05), entry1.getDate());
    assertEquals(195.4000, entry1.getOpen(), 0.001);
    assertEquals(196.9000, entry1.getHigh(), 0.001);
    assertEquals(194.8700, entry1.getLow(), 0.001);
    assertEquals(195.8700, entry1.getClose(), 0.001);
    assertEquals(54156785, entry1.getVolume());

    // Line 2
    StockData entry2 = stockDataList.get(1);
    assertEquals(LocalDate.of(2024, 06, 04), entry2.getDate());
    assertEquals(194.6350, entry2.getOpen(), 0.001);
    assertEquals(195.3200, entry2.getHigh(), 0.001);
    assertEquals(193.0342, entry2.getLow(), 0.001);
    assertEquals(194.3500, entry2.getClose(), 0.001);
    assertEquals(47471445, entry2.getVolume());

    // Line 3
    StockData entry3 = stockDataList.get(2);
    assertEquals(LocalDate.of(2024, 06, 03), entry3.getDate());
    assertEquals(192.9000, entry3.getOpen(), 0.001);
    assertEquals(194.9900, entry3.getHigh(), 0.001);
    assertEquals(192.5200, entry3.getLow(), 0.001);
    assertEquals(194.0300, entry3.getClose(), 0.001);
    assertEquals(50080539, entry3.getVolume());

    // Line 4
    StockData entry4 = stockDataList.get(3);
    assertEquals(LocalDate.of(2024, 05, 31), entry4.getDate());
    assertEquals(191.4400, entry4.getOpen(), 0.001);
    assertEquals(192.5700, entry4.getHigh(), 0.001);
    assertEquals(189.9100, entry4.getLow(), 0.001);
    assertEquals(192.2500, entry4.getClose(), 0.001);
    assertEquals(75158277, entry4.getVolume());

    // Line 5
    StockData entry5 = stockDataList.get(4);
    assertEquals(LocalDate.of(2024, 05, 30), entry5.getDate());
    assertEquals(190.7600, entry5.getOpen(), 0.001);
    assertEquals(192.1800, entry5.getHigh(), 0.001);
    assertEquals(190.6300, entry5.getLow(), 0.001);
    assertEquals(191.2900, entry5.getClose(), 0.001);
    assertEquals(49947941, entry5.getVolume());

    // Line 6
    StockData entry6 = stockDataList.get(5);
    assertEquals(LocalDate.of(2024, 05, 29), entry6.getDate());
    assertEquals(189.6100, entry6.getOpen(), 0.001);
    assertEquals(192.2470, entry6.getHigh(), 0.001);
    assertEquals(189.5100, entry6.getLow(), 0.001);
    assertEquals(190.2900, entry6.getClose(), 0.001);
    assertEquals(53068016, entry6.getVolume());

    // Line 7
    StockData entry7 = stockDataList.get(6);
    assertEquals(LocalDate.of(2024, 05, 28), entry7.getDate());
    assertEquals(191.5100, entry7.getOpen(), 0.001);
    assertEquals(193.0000, entry7.getHigh(), 0.001);
    assertEquals(189.1000, entry7.getLow(), 0.001);
    assertEquals(189.9900, entry7.getClose(), 0.001);
    assertEquals(52280051, entry7.getVolume());

    // Line 8
    StockData entry8 = stockDataList.get(7);
    assertEquals(LocalDate.of(2024, 05, 24), entry8.getDate());
    assertEquals(188.8200, entry8.getOpen(), 0.001);
    assertEquals(190.5800, entry8.getHigh(), 0.001);
    assertEquals(188.0404, entry8.getLow(), 0.001);
    assertEquals(189.9800, entry8.getClose(), 0.001);
    assertEquals(36326975, entry8.getVolume());

    // Line 9
    StockData entry9 = stockDataList.get(8);
    assertEquals(LocalDate.of(2024, 05, 23), entry9.getDate());
    assertEquals(190.9800, entry9.getOpen(), 0.001);
    assertEquals(191.0000, entry9.getHigh(), 0.001);
    assertEquals(186.6250, entry9.getLow(), 0.001);
    assertEquals(186.8800, entry9.getClose(), 0.001);
    assertEquals(51005924, entry9.getVolume());

    // Line 10
    StockData entry10 = stockDataList.get(9);
    assertEquals(LocalDate.of(2024, 05, 22), entry10.getDate());
    assertEquals(192.2650, entry10.getOpen(), 0.001);
    assertEquals(192.8231, entry10.getHigh(), 0.001);
    assertEquals(190.2700, entry10.getLow(), 0.001);
    assertEquals(190.9000, entry10.getClose(), 0.001);
    assertEquals(34648547, entry10.getVolume());
  }
}
