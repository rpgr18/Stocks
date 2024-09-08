package stocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * the AdvancedStock class represents a stock with additional functionality. An advanced stock also
 * tracks its transactions, keeping a record of not only the total shares, but also when the dates
 * that shares were bought and sold.
 */
public class AdvancedStock extends SimpleStock implements BetterStock {
  private List<Transaction> transactions;
  private TreeMap<LocalDate, Double> individualShares;

  /**
   * Constructs a {@code stocks.AdvancedStock} object.
   * Historical data is automatically populated by a local file if
   * one is found. If not the data will be retrieved from the API.
   * This constructor also assigns a default empty list to the transactions
   * field.
   *
   * @param ticker the stock ticker
   */
  public AdvancedStock(String ticker) {
    super(ticker);
    this.transactions = new ArrayList<>();
    this.individualShares = new TreeMap<>();
  }

  /**
   * The static inner class Transaction represents a simple stock transaction,
   * keeping track of the number of shares (+ if bought, - if sold), the date
   * of the transaction, and the total value of the transaction (the pricePaid
   * field tracks the entry price that the user paid to buy the stock, it is
   * not currently used for any functionality but could be used later to track
   * cost basis vs total portfolio value).
   */
  private static class Transaction {
    private final double shares;
    private final LocalDate date;
    private final double pricePaid;

    private Transaction(double shares, LocalDate date, double pricePaid) {
      this.shares = shares;
      this.date = date;
      this.pricePaid = pricePaid;
    }
  }

  @Override
  public void adjustSharesOnDay(double shares, LocalDate date) {
    if (shares == 0) {
      throw new IllegalArgumentException("** A TRANSACTION MUST ADD OR SUBTRACT SHARES **");
    }

    adjustShares(shares);
    if (individualShares.containsKey(date)) {
      individualShares.put(date, individualShares.get(date) + shares);
    } else {
      individualShares.put(date, shares);
    }

    double pricePaid = shares * this.getPriceOnDay(date);
    Transaction newTransaction = new Transaction(shares, date, pricePaid);
    transactions.add(newTransaction);
  }

  @Override
  public double getValueOnDate(LocalDate date) {
    if (historicalData.get(historicalData.size() - 1).getDate().isAfter(date)) {
      throw new IllegalArgumentException("** NO DATA AVAILABLE FOR " + date
              + " TRY A MORE RECENT DATE **");
    }
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **");
    }
    double value = 0.0;

    for (Transaction t : transactions) {
      if (t.date.isEqual(date) || t.date.isBefore(date)) {
        value += t.shares * getPriceOnDay(date);
      }
    }
    return value;
  }

  @Override
  public double getSharesOnDay(LocalDate date) {
    double shares = 0.0;
    for (Transaction t : transactions) {
      if (t.date.isEqual(date) || t.date.isBefore(date)) {
        shares += t.shares;
      }
    }
    return shares;
  }

  @Override
  public String getTransactions() {
    StringBuilder output = new StringBuilder();
    String transactionType;
    double sharesAcc = 0.0;

    for (Transaction t : transactions) {
      if (t.shares < 0) {
        transactionType = "Sell";
      } else {
        transactionType = "Buy";
      }
      sharesAcc += t.shares;
      String appendable = getTicker() + "," + transactionType + "," + t.shares + ","
              + sharesAcc + "," + t.date + System.lineSeparator();
      output.append(appendable);
    }
    return output.toString();
  }

  @Override
  public void rebalanceStock(double expectedValue, LocalDate date) {
    System.out.println("Expected Value for Distribution: " + expectedValue);
    System.out.println("Ticker: " + getTicker());
    System.out.println("Shares: " + getSharesOnDay(date));
    System.out.println("Date Rebalancing TO: " + date);
    System.out.println("This Stocks Value: " + getValueOnDate(date));
    double rebalanceValue = expectedValue - getValueOnDate(date);
    System.out.println("Rebalance Value " + getTicker() + ": " + rebalanceValue);
    if (rebalanceValue > 0) {
      double shares = rebalanceValue / getPriceOnDay(date);
      this.adjustSharesOnDay(shares, date);
      System.out.println("Added shares " + getTicker() + ": " + shares);
      return;
    }
    double tempValue = rebalanceValue;
    for (Map.Entry<LocalDate, Double> shares : individualShares.entrySet()) {
      //Temp Value will be Negative until enough stocks are sold
      if (tempValue != 0) {
        //Need to sell stocks in order to adjust current Stock to match distribution
        //Find the difference in price between when the stock was bought and,
        //when the stock is going to be rebalanced
        double sharePrice = shares.getValue() * getPriceOnDay(date);
        if (tempValue + sharePrice > 0) {
          //Since the total price of shares on this day exceeded the value that must be sold,
          //Only take a fraction of the shares away.
          this.adjustIndividualShares((tempValue / sharePrice) * shares.getValue(), date
                  , shares.getKey());
          return;
        } else {
          tempValue += sharePrice;
          //Otherwise just sell all the shares on that day.
          this.adjustIndividualShares(-1 * shares.getValue(), date, shares.getKey());
        }
      } else {
        return;
      }
    }
  }

  /**
   * Helper for re-balance stock that ensures re-balance only processes a transaction on the
   * re-balance date, not the date the stocks to re-balance were purchased.
   *
   * @param shares          the number of shares
   * @param transactionDate the transaction date
   * @param removalDate     the re-balance date
   */
  private void adjustIndividualShares(double shares,
                                      LocalDate transactionDate, LocalDate removalDate) {
    if (shares == 0) {
      throw new IllegalArgumentException("** A TRANSACTION MUST ADD OR SUBTRACT SHARES **");
    }

    adjustShares(shares);
    if (individualShares.containsKey(removalDate)) {
      individualShares.put(removalDate, individualShares.get(removalDate) + shares);
    } else {
      individualShares.put(removalDate, shares);
    }

    double pricePaid = shares * this.getPriceOnDay(transactionDate);
    Transaction newTransaction = new Transaction(shares, transactionDate, pricePaid);
    transactions.add(newTransaction);
  }

}
