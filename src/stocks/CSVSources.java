package stocks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code stocks.CSVSources} class represents all
 * Comma Separated Values input sources including web and local files.
 */
public abstract class CSVSources implements StockDataSource {
  private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * The getHistoryData method queries the CSV data sources and parses each entry
   * into a StockData object with corresponding values.
   *
   * @param ticker the ticker for the desired stock
   * @return the list of StockData
   */
  public abstract List<StockData> getHistoryData(String ticker);

  /**
   * The parseCSV method parses a CSV file into a list of  StockData objects.
   *
   * @param csvData the CSV file (i.e. timestamp, high, low, open, close, volume)
   * @return the list of StockData
   */
  public List<StockData> parseCSV(String csvData) throws RuntimeException {
    List<StockData> data = new ArrayList<>();
    String[] rows = csvData.split("\n");
    for (int i = 1; i < rows.length; i++) {
      try {
        StockData stockData = getStockData(rows[i]);
        data.add(stockData);
      } catch (DateTimeParseException e) {
        throw new RuntimeException("** STOCK NOT FOUND, CHECK TICKER FORMAT **");
      }
    }
    return data;
  }

  /**
   * The static method getStockData parses an individual CSV entry into a StockData
   * object.
   *
   * @param rows the number of lines in the CSV file
   * @return the StockData object
   */
  private static StockData getStockData(String rows) {
    StockData stockData;

    String[] columns = rows.split(",");
    LocalDate date = LocalDate.parse(columns[0].trim(), dateFormat);
    double open = Double.parseDouble(columns[1].trim());
    double high = Double.parseDouble(columns[2].trim());
    double low = Double.parseDouble(columns[3].trim());
    double close = Double.parseDouble(columns[4].trim());
    int volume = Integer.parseInt(columns[5].trim());
    stockData = new DailyStockData(date, open, close, high, low, volume);
    return stockData;
  }

}
