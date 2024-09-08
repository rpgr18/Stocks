package stocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * The {@code stocks.AlphaVantageDataSource} class represents the data source for
 * the AlphaVantage API.
 */
public class AlphaVantageDataSource extends CSVSources {
  private static final String apiKey = "";

  @Override
  public List<StockData> getHistoryData(String ticker) {
    List<StockData> data;
    URL url;

    try {
      url = new URL("https://www.alphavantage.co/query?"
              + "function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol="
              + ticker
              + "&apikey="
              + apiKey
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException(" ** MALFORMED URL, TRY AGAIN OR RESTART THE PROGRAM ** ");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      in.close();

      // Save the CSV file in the stockdata directory
      try (FileOutputStream fos = new FileOutputStream(ticker + ".csv")) {
        fos.write(output.toString().getBytes());
      }

      data = parseCSV(output.toString());
    } catch (IOException e) {
      throw new RuntimeException(" ** UNABLE TO CONNECT TO THE INTERNET, CHECK YOUR CONNECTION **");
    }
    return data;
  }

}


