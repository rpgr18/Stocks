package stocks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The {@code LocalCSVDataSource} class represents a CSV stock data
 * source from a local file. This allows the program to download API data
 * to use locally in the future or for the user to add their own CSV file.
 * IMPORTANT: If using your own file, you must put the csv file in the stockdata
 * directory.
 */
public class LocalCSVDataSource extends CSVSources {

  @Override
  public List<StockData> getHistoryData(String ticker) {
    List<StockData> data;
    try {
      String csvData = new String(Files.readAllBytes(Paths.get(ticker + ".csv")));
      data = parseCSV(csvData);
    } catch (IOException e) {
      throw new RuntimeException("** UNABLE TO READ LOCAL CSV FILE FOR:" + ticker + " **");
    }
    return data;
  }

}
