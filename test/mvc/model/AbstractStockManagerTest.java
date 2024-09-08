package mvc.model;

import org.junit.Before;
import org.junit.Test;

import stocks.LocalCSVDataSource;
import stocks.StockData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The AbstractStockManagerTest class represent tests common to Advanced and
 * Simple StockManagers.
 */
public abstract class AbstractStockManagerTest<T extends SimpleStockManager> {

  protected T mockManager;
  protected T realManager;
  List<StockData> mockData;

  public abstract T createMockStockManager();

  public abstract T createRealStockManager();


  @Before
  public void setUp() {
    String mockCSVData =
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
    mockData = new LocalCSVDataSource().parseCSV(mockCSVData);

    realManager = createRealStockManager();
    mockManager = createMockStockManager();
  }

  @Test
  public void testGetGainLoss_NormalCase() {
    double result = mockManager.getGainLoss("MOCK", LocalDate.of(
            2024, 5, 30), LocalDate.of(2024, 6, 5));
    assertEquals(4.58, result, 0.001);
  }

  @Test
  public void testGetGainLoss_SameStartEndDate() {
    double result = mockManager.getGainLoss("MOCK", LocalDate.of(
            2024, 6, 5), LocalDate.of(2024, 6, 5));
    assertEquals(0.0, result, 0.001);
  }

  @Test
  public void testGetGainLoss_StartDateAfterEndDate() {
    try {
      mockManager.getGainLoss("MOCK", LocalDate.of(2024, 6, 5),
              LocalDate.of(2024, 5, 30));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("** START DATE MUST PRECEDE END DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_StartDateInFuture() {
    try {
      mockManager.getGainLoss("MOCK", LocalDate.now().plusDays(1),
              LocalDate.now().plusDays(2));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **",
              e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_EndDateInFuture() {
    try {
      mockManager.getGainLoss("MOCK", LocalDate.of(2024,
              5, 30), LocalDate.now().plusDays(1));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_StartDateBeforeEarliestData() {
    try {
      mockManager.getGainLoss("MOCK", LocalDate.of(2024,
              5, 1), LocalDate.of(2024, 5, 30));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("** HISTORICAL DATA NOT AVAILABLE FOR 2024-05-01" +
              ", TRY A MORE RECENT TIME FRAME **\n", e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_RolloverToPreviousDay_EndDate() {
    double result = mockManager.getGainLoss("MOCK", LocalDate.of(
            2024, 5, 30), LocalDate.of(2024, 6, 1));
    assertEquals(0.96, result, 0.001);
  }

  @Test
  public void testGetGainLoss_RolloverToPreviousDay_StartDate() {
    double result = mockManager.getGainLoss("MOCK", LocalDate.of(
            2024, 6, 1), LocalDate.of(2024, 6, 5));
    assertEquals(3.62, result, 0.001);
  }

  @Test
  public void testGetGainLoss_SpanWeekend() {
    double result = mockManager.getGainLoss("MOCK", LocalDate.of(
            2024, 5, 30), LocalDate.of(2024, 6, 2));
    assertEquals(0.96, result, 0.001);
  }


  @Test
  public void testGetGainLoss_RealData_Success1() {
    // Example dates and ticker
    String ticker = "AAPL";
    LocalDate start = LocalDate.of(2024, 5, 22);
    LocalDate end = LocalDate.of(2024, 6, 5);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(4.969, result, 0.001);
  }

  @Test
  public void testGetGainLoss_RealData_Success2() {
    // Example dates and ticker
    String ticker = "GOOG";
    LocalDate start = LocalDate.of(2024, 5, 22);
    LocalDate end = LocalDate.of(2024, 6, 5);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(-0.93, result, 0.001);
  }

  @Test
  public void testGetGainLoss_RealData_Success3() {
    // Example dates and ticker
    String ticker = "MSFT";
    LocalDate start = LocalDate.of(2024, 5, 22);
    LocalDate end = LocalDate.of(2024, 6, 5);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(-6.509, result, 0.001);
  }

  @Test
  public void testGetGainLoss_RealData_InvalidDates() {
    // Example of testing future dates, which should throw an exception
    String ticker = "AAPL";
    LocalDate start = LocalDate.of(2030, 6, 10);
    LocalDate end = LocalDate.of(2031, 6, 15);

    try {
      realManager.getGainLoss(ticker, start, end);
      fail("Expected IllegalArgumentException for future dates.");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_RealData_StartAfterEnd() {
    // Example of start date after end date
    String ticker = "AAPL";
    LocalDate start = LocalDate.of(2024, 6, 5);
    LocalDate end = LocalDate.of(2024, 5, 22);

    try {
      realManager.getGainLoss(ticker, start, end);
      fail("Expected IllegalArgumentException for start date after end date.");
    } catch (IllegalArgumentException e) {
      assertEquals("** START DATE MUST PRECEDE END DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetGainLoss_Holiday() {
    // Example dates and ticker
    String ticker = "GOOG";
    LocalDate start = LocalDate.of(2023, 6, 4);
    LocalDate end = LocalDate.of(2024, 5, 29);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(52.17, result, 0.001);
  }

  @Test
  public void testGetGainLoss_HolidayBoth() {
    // Example dates and ticker
    String ticker = "GOOG";
    LocalDate start = LocalDate.of(2023, 6, 4);
    LocalDate end = LocalDate.of(2024, 6, 4);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(49.899, result, 0.001);
  }

  @Test
  public void testGetGainLossLowCaseTicker() {
    // Example dates and ticker
    String ticker = "goog";
    LocalDate start = LocalDate.of(2023, 6, 4);
    LocalDate end = LocalDate.of(2024, 6, 4);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(49.899, result, 0.001);
  }

  @Test
  public void testGetGainLossLowCaseTickerWierd() {
    // Example dates and ticker
    String ticker = "gOoG";
    LocalDate start = LocalDate.of(2023, 6, 4);
    LocalDate end = LocalDate.of(2024, 6, 4);

    double result = realManager.getGainLoss(ticker, start, end);
    assertEquals(49.899, result, 0.001);
  }

  @Test
  public void testInvalidTicker() {
    String ticker = "123123";
    // Valid dates
    LocalDate start = LocalDate.of(2023, 6, 4);
    LocalDate end = LocalDate.of(2024, 6, 4);
    try {
      double result = realManager.getGainLoss(ticker, start, end);
      fail("Expected RuntimeException for invalid ticker");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** STOCK NOT FOUND, CHECK TICKER FORMAT **");
    }
  }

  @Test
  public void testInvalidTickerAndDates() {
    String ticker = "123123";
    // Valid dates
    LocalDate start = LocalDate.of(2025, 6, 4);
    LocalDate end = LocalDate.of(2025, 6, 4);
    try {
      double result = realManager.getGainLoss(ticker, start, end);
      fail("Expected RuntimeException for invalid date");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
  }

  @Test
  public void testGetMovingAvg_NormalCase() {
    String ticker = "MOCK";
    LocalDate date = LocalDate.of(2024, 5, 30);
    int days = 3;

    double result = mockManager.getMovingAvg(ticker, date, days);
    assertEquals(190.523, result, 0.001);
  }

  @Test
  public void testGetMovingAvg_EndDateRollover() {
    String ticker = "MOCK";
    LocalDate date = LocalDate.of(2024, 6, 1);
    int days = 3;

    double result = mockManager.getMovingAvg(ticker, date, days);
    assertEquals(191.276, result, 0.001);
  }

  @Test
  public void testGetMovingAvg_SpanWeekend() {
    String ticker = "MOCK";
    LocalDate date = LocalDate.of(2024, 5, 31); // Friday
    int days = 3;

    double result = mockManager.getMovingAvg(ticker, date, days);
    assertEquals(191.276, result, 0.001);
  }

  @Test
  public void testGetMovingAvg_FutureDate() {
    String ticker = "MOCK";
    LocalDate futureDate = LocalDate.now().plusDays(1);
    int days = 3;

    try {
      mockManager.getMovingAvg(ticker, futureDate, days);
      fail("Expected RuntimeException for future date.");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
  }

  @Test
  public void testGetMovingAvg_PastDate() {
    String ticker = "MOCK";
    LocalDate pastDate = LocalDate.of(1900, 9, 9);
    int days = 3;

    try {
      mockManager.getMovingAvg(ticker, pastDate, days);
      fail("Expected RuntimeException for past date to far back.");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** HISTORICAL DATA NOT AVAILABLE FOR " +
              "1900-09-09, TRY A MORE RECENT TIME FRAME **\n");
    }
  }

  @Test
  public void testGetMovingAvg_XValuePastDate() {
    String ticker = "MOCK";
    LocalDate pastDate = LocalDate.of(2024, 5, 31);
    int days = 1000000;

    try {
      mockManager.getMovingAvg(ticker, pastDate, days);
      fail("Expected RuntimeException for x value to far back.");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** AN X-VALUE OF 1000000 "
              + "GOES BEFORE THE EARLIEST ACCESSIBLE DATA FOR THIS STOCK **");
    }
  }

  @Test
  public void testMovingAvg_RealData_Success1() {
    // Example dates and ticker
    String ticker = "AAPL";
    LocalDate date = LocalDate.of(2024, 5, 22);
    int x = 5;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(190.8, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealData_Success2() {
    // Example dates and ticker
    String ticker = "GOOG";
    LocalDate date = LocalDate.of(2024, 6, 5);
    int x = 30;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(172.350, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealData_Success3() {
    // Example dates and ticker
    String ticker = "MSFT";
    LocalDate date = LocalDate.of(2024, 6, 5);
    int x = 200;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(380.463, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealData_InvalidDate() {
    // Example of testing future dates, which should throw an exception
    String ticker = "AAPL";
    LocalDate date = LocalDate.of(2030, 6, 10);
    int x = 30;

    try {
      realManager.getMovingAvg(ticker, date, x);
      fail("Expected IllegalArgumentException for future date.");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testMovingAvg_RealData_Holiday() {
    // Example dates and ticker
    String ticker = "GOOG";
    LocalDate date = LocalDate.of(2024, 1, 1);
    int x = 30;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(137.185, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealData_LowCaseTicker() {
    // Example dates and ticker
    String ticker = "goog";
    LocalDate date = LocalDate.of(2024, 6, 5);
    int x = 30;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(172.350, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealDataLowCaseTickerWierd() {
    // Example dates and ticker
    String ticker = "gOoG";
    LocalDate date = LocalDate.of(2024, 6, 5);
    int x = 30;

    double result = realManager.getMovingAvg(ticker, date, x);
    assertEquals(172.350, result, 0.001);
  }

  @Test
  public void testMovingAvg_RealData_InvalidTicker() {
    String ticker = "123123";
    // Valid date
    LocalDate date = LocalDate.of(2024, 6, 5);
    int x = 30;

    try {
      realManager.getMovingAvg(ticker, date, x);
      fail("Expected RuntimeException for invalid ticker");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** STOCK NOT FOUND, CHECK TICKER FORMAT **");
    }
  }

  @Test
  public void testMovingAvg_RealData_InvalidTickerAndDates() {
    String ticker = "123123";
    LocalDate date = LocalDate.of(2030, 6, 4);
    int x = 30;

    try {
      realManager.getMovingAvg(ticker, date, x);
      fail("Expected RuntimeException for invalid date");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
  }

  @Test
  public void testGetCrossovers_MockData() {
    String ticker = "MOCK";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 3;

    List<LocalDate> result = mockManager.getCrossovers(ticker, start, end, days);
    assertEquals(6, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 6, 3), result.get(2));
    assertEquals(LocalDate.of(2024, 5, 31), result.get(3));
    assertEquals(LocalDate.of(2024, 5, 30), result.get(4));
    assertEquals(LocalDate.of(2024, 5, 29), result.get(5));
  }

  @Test
  public void testGet5Crossovers_RealData1() {
    String ticker = "AAPL";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 5;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(6, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 6, 3), result.get(2));
    assertEquals(LocalDate.of(2024, 5, 31), result.get(3));
    assertEquals(LocalDate.of(2024, 5, 30), result.get(4));
    assertEquals(LocalDate.of(2024, 5, 29), result.get(5));
  }

  @Test
  public void testGet5Crossovers_RealData2() {
    String ticker = "GOOG";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 5;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(3, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 5, 29), result.get(2));
  }

  @Test
  public void testGet5Crossovers_RealData3() {
    String ticker = "MSFT";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 5;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(1, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
  }

  @Test
  public void testGet2Crossovers_RealData1() {
    String ticker = "AAPL";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 2;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(6, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 6, 3), result.get(2));
    assertEquals(LocalDate.of(2024, 5, 31), result.get(3));
    assertEquals(LocalDate.of(2024, 5, 30), result.get(4));
    assertEquals(LocalDate.of(2024, 5, 29), result.get(5));
  }

  @Test
  public void testGet2Crossovers_RealData2() {
    String ticker = "GOOG";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 2;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(4, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 6, 3), result.get(2));
    assertEquals(LocalDate.of(2024, 5, 31), result.get(3));
  }

  @Test
  public void testGet2Crossovers_RealData3() {
    String ticker = "MSFT";
    LocalDate start = LocalDate.of(2024, 5, 29);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 2;

    List<LocalDate> result = realManager.getCrossovers(ticker, start, end, days);
    assertEquals(3, result.size());
    assertEquals(LocalDate.of(2024, 6, 5), result.get(0));
    assertEquals(LocalDate.of(2024, 6, 4), result.get(1));
    assertEquals(LocalDate.of(2024, 5, 31), result.get(2));
  }

  @Test
  public void testGetCrossovers_EndDateBeforeStartDate() {
    String ticker = "MOCK";
    LocalDate start = LocalDate.of(2024, 6, 5);
    LocalDate end = LocalDate.of(2024, 5, 22);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, start, end, days);
      fail("Expected exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("** START DATE MUST PRECEDE END DATE **", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_StartDateInFuture() {
    String ticker = "MOCK";
    LocalDate start = LocalDate.now().plusDays(1);
    LocalDate end = LocalDate.now().plusDays(2);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, start, end, days);
      fail("Expected exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_EndDateInFuture() {
    String ticker = "MOCK";
    LocalDate start = LocalDate.of(2024, 5, 30);
    LocalDate end = LocalDate.now().plusDays(1);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, start, end, days);
      fail("Expected exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_StartDateBeforeEarliestData() {
    String ticker = "MOCK";
    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 5, 30);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, start, end, days);
      fail("Expected exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("** HISTORICAL DATA NOT AVAILABLE FOR 2024-05-01" +
              ", TRY A MORE RECENT TIME FRAME **\n", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_FutureDate() {
    String ticker = "MOCK";
    LocalDate futureDate = LocalDate.now().plusDays(1);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, futureDate, futureDate.plusDays(1), days);
      fail("Expected exception.");
    } catch (RuntimeException e) {
      assertEquals("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_PastDate() {
    String ticker = "MOCK";
    LocalDate pastDate = LocalDate.of(1900, 9, 9);
    int days = 3;

    try {
      mockManager.getCrossovers(ticker, pastDate, pastDate.plusDays(1), days);
      fail("Expected exception.");
    } catch (RuntimeException e) {
      assertEquals("** HISTORICAL DATA NOT AVAILABLE FOR 1900-09-09, " +
              "TRY A MORE RECENT TIME FRAME **\n", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_XValuePastDate() {
    String ticker = "MOCK";
    LocalDate pastDate = LocalDate.of(2024, 5, 31);
    int days = 1000000;

    try {
      mockManager.getCrossovers(ticker, pastDate, pastDate.plusDays(1), days);
      fail("Expected exception.");
    } catch (RuntimeException e) {
      assertEquals("** AN X-VALUE OF 1000000 "
              + "GOES BEFORE THE EARLIEST ACCESSIBLE DATA FOR THIS STOCK **", e.getMessage());
    }
  }

  @Test
  public void testGetCrossovers_RealData_InvalidTicker() {
    String ticker = "INVALID";
    LocalDate start = LocalDate.of(2024, 5, 22);
    LocalDate end = LocalDate.of(2024, 6, 5);
    int days = 5;

    try {
      realManager.getCrossovers(ticker, start, end, days);
      fail("Expected exception.");
    } catch (RuntimeException e) {
      assertEquals("** STOCK NOT FOUND, CHECK TICKER FORMAT **", e.getMessage());
    }
  }

  @Test
  public void testNewrealManager() {
    assertEquals(realManager.listPortfolios(), "");
  }


  @Test
  public void testAddPortfolio() {
    realManager.addPortfolio("MyPortfolio");
    assertEquals(realManager.listPortfolios(),"MyPortfolio, ");
  }

  @Test
  public void testAddMultiplePortfolios() {
    realManager.addPortfolio("MyPortfolio1");
    realManager.addPortfolio("MyPortfolio2");
    realManager.addPortfolio("MyPortfolio3");
    assertEquals(realManager.listPortfolios(), "MyPortfolio1, MyPortfolio2, MyPortfolio3, ");
  }

  @Test
  public void testAddDuplicatePortfolio() {
    realManager.addPortfolio("MyPortfolio");
    try {
      realManager.addPortfolio("MyPortfolio");
      fail("Expected IllegalArgumentException for adding duplicate portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("** A PORTFOLIO NAMED: MyPortfolio ALREADY EXISTS **", e.getMessage());
    }
  }

  @Test
  public void testRemovePortfolio() {
    realManager.addPortfolio("MyPortfolio");
    realManager.removePortfolio("MyPortfolio");
    assertTrue(realManager.listPortfolios().isEmpty());
  }

  @Test
  public void testRemoveNonExistentPortfolio() {
    realManager.addPortfolio("MyPortfolio");
    try {
      realManager.removePortfolio("askdjfnakjg");
      fail("Expected IllegalArgumentException for removing nonexistent portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "** NO SUCH PORTFOLIO FOUND **");
      assertEquals(realManager.listPortfolios(),"MyPortfolio, ");
    }
  }

  @Test
  public void testGetValue_AfterClearingPortfolio() {
    realManager.addPortfolio("MyPortfolio");
    realManager.adjustPortfolio("MyPortfolio","AAPL", 10);
    realManager.adjustPortfolio("MyPortfolio","GOOG", 20);
    realManager.adjustPortfolio("MyPortfolio","MSFT", 30);
    realManager.adjustPortfolio("MyPortfolio","AAPL", -10);
    realManager.adjustPortfolio("MyPortfolio","GOOG", -20);
    realManager.adjustPortfolio("MyPortfolio","MSFT", -30);

    LocalDate date1 = LocalDate.of(2024, 05, 29);
    LocalDate date2 = LocalDate.of(2020, 06, 01);
    LocalDate date3 = LocalDate.of(2022, 12, 12);

    assertEquals(0.0, realManager.getPortfolioValue("MyPortfolio", date1), 0.001);
    assertEquals(0.0, realManager.getPortfolioValue("MyPortfolio", date2), 0.001);
    assertEquals(0.0, realManager.getPortfolioValue("MyPortfolio", date3), 0.001);
  }

}



