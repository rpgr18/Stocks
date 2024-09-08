package mvc.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.stream.IntStream;

/**
 * The SimpleGraphicsStockView class in an implementation on the GraphicsStock view interface
 * and handles to view output with a GUI.
 */
public class SimpleGraphicsStockView extends JFrame implements GraphicsStockView {
  final static String CREATIONPANEL = "Create new Portfolio";
  final static String BUYPANEL = "Buy or Sell Stocks";
  final static String VALUEPANEL = "Value of Portfolio";
  final static String SAVELOADPANEL = "Save or Load Portfolio";

  private JButton createButton;
  private JButton buySellButton;
  private JButton queryButton;
  private JButton saveButton;
  private JButton loadButton;

  private JTextField portfolioNameField;
  private JTextField stockTickerField;
  private JTextField stockSharesField;

  private JComboBox<String> buySellPortfolioComboBox;
  private JComboBox<String> valuePortfolioComboBox;
  private JComboBox<String> savePortfolioComboBox;
  private JComboBox<String> loadPortfolioComboBox;

  private JComboBox<Integer> buySellYearComboBox;
  private JComboBox<Integer> buySellMonthComboBox;
  private JComboBox<Integer> buySellDayComboBox;

  private JComboBox<Integer> valueYearComboBox;
  private JComboBox<Integer> valueMonthComboBox;
  private JComboBox<Integer> valueDayComboBox;

  private JTextArea messageArea;

  /**
   * Constructs a SimpleGraphicsStockView object.
   */
  public SimpleGraphicsStockView() {
    super("Stock Portfolio Manager");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());

    addComponentToPane(getContentPane());
    addMessageArea();
  }

  /**
   * Add a component to the panel.
   * @param pane the pane to add
   */
  private void addComponentToPane(Container pane) {
    JTabbedPane tabbedPane = new JTabbedPane();

    JPanel creationPanel = createCreationPanel();
    JPanel buySellPanel = createBuySellPanel();
    JPanel valuePanel = createValuePanel();
    JPanel saveLoadPanel = createSaveLoadPanel();

    tabbedPane.addTab(CREATIONPANEL, creationPanel);
    tabbedPane.addTab(BUYPANEL, buySellPanel);
    tabbedPane.addTab(VALUEPANEL, valuePanel);
    tabbedPane.addTab(SAVELOADPANEL, saveLoadPanel);

    pane.add(tabbedPane, BorderLayout.CENTER);
  }

  /**
   * Create the new portfolio panel.
   * @return the JPanel object
   */
  private JPanel createCreationPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("Create New Portfolio"));

    JLabel nameLabel = new JLabel("Portfolio Name:");
    portfolioNameField = new JTextField(20);

    createButton = new JButton("Create Portfolio");

    panel.add(nameLabel);
    panel.add(portfolioNameField);
    panel.add(createButton);

    return panel;
  }

  /**
   * Create the buy/sell panel.
   * @return the JPanel object
   */
  private JPanel createBuySellPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("Buy/Sell Stocks"));

    JLabel portfolioLabel = new JLabel("Select Portfolio:");
    buySellPortfolioComboBox = new JComboBox<>();

    JLabel tickerLabel = new JLabel("Stock Ticker:");
    stockTickerField = new JTextField(10);

    JLabel sharesLabel = new JLabel("Shares:");
    stockSharesField = new JTextField(10);

    JLabel dateLabel = new JLabel("Select Date:");

    buySellYearComboBox = new JComboBox<>(IntStream.range(2000, LocalDate.now().getYear() + 1)
            .boxed().toArray(Integer[]::new));
    buySellMonthComboBox = new JComboBox<>(IntStream.range(1, 13).boxed().toArray(Integer[]::new));
    buySellDayComboBox = new JComboBox<>(IntStream.range(1, 32).boxed().toArray(Integer[]::new));

    // Add listener to update days in day combo box based on selected year and month
    ActionListener dateListener = e -> updateDaysAndMonthsInComboBox(buySellYearComboBox,
            buySellMonthComboBox, buySellDayComboBox);
    buySellYearComboBox.addActionListener(dateListener);
    buySellMonthComboBox.addActionListener(dateListener);

    buySellButton = new JButton("Buy/Sell");

    panel.add(portfolioLabel);
    panel.add(buySellPortfolioComboBox);
    panel.add(tickerLabel);
    panel.add(stockTickerField);
    panel.add(sharesLabel);
    panel.add(stockSharesField);
    panel.add(dateLabel);
    panel.add(createDatePanel(buySellYearComboBox, buySellMonthComboBox, buySellDayComboBox));
    panel.add(buySellButton);

    return panel;
  }

  /**
   * Create the value panel.
   * @return the JPanel object
   */
  private JPanel createValuePanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("Query Portfolio Value"));

    JLabel portfolioLabel = new JLabel("Select Portfolio:");
    valuePortfolioComboBox = new JComboBox<>();

    JLabel dateLabel = new JLabel("Select Date:");

    valueYearComboBox = new JComboBox<>(IntStream.range(2000, LocalDate.now().getYear() + 1).boxed()
            .toArray(Integer[]::new));
    valueMonthComboBox = new JComboBox<>(IntStream.range(1, 13).boxed().toArray(Integer[]::new));
    valueDayComboBox = new JComboBox<>(IntStream.range(1, 32).boxed().toArray(Integer[]::new));

    // Add listener to update days in day combo box based on selected year and month
    ActionListener dateListener = e -> updateDaysAndMonthsInComboBox(valueYearComboBox,
            valueMonthComboBox, valueDayComboBox);
    valueYearComboBox.addActionListener(dateListener);
    valueMonthComboBox.addActionListener(dateListener);

    queryButton = new JButton("Query Value");

    panel.add(portfolioLabel);
    panel.add(valuePortfolioComboBox);
    panel.add(dateLabel);
    panel.add(createDatePanel(valueYearComboBox, valueMonthComboBox, valueDayComboBox));
    panel.add(queryButton);

    return panel;
  }

  /**
   * Create the save/load panel.
   * @return the JPanel object
   */
  private JPanel createSaveLoadPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("Save/Load Portfolio"));

    JLabel saveLabel = new JLabel("Save Portfolio:");
    savePortfolioComboBox = new JComboBox<>();

    saveButton = new JButton("Save Portfolio");

    JLabel loadLabel = new JLabel("Load Portfolio:");
    loadPortfolioComboBox = new JComboBox<>();

    loadButton = new JButton("Load Portfolio");

    panel.add(saveLabel);
    panel.add(savePortfolioComboBox);
    panel.add(saveButton);
    panel.add(loadLabel);
    panel.add(loadPortfolioComboBox);
    panel.add(loadButton);

    return panel;
  }

  /**
   * Creates the date panel for various pages.
   * @return the JPanel object
   */
  private JPanel createDatePanel(JComboBox<Integer> yearBox, JComboBox<Integer> monthBox,
                                 JComboBox<Integer> dayBox) {
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    datePanel.add(yearBox);
    datePanel.add(new JLabel("/"));
    datePanel.add(monthBox);
    datePanel.add(new JLabel("/"));
    datePanel.add(dayBox);
    return datePanel;
  }

  /**
   * Updates the date dropdown to only include valid dates (considering leap years,
   * no future dates etc...).
   * @param yearComboBox the year drop down
   * @param monthComboBox the month dropdown
   * @param dayComboBox the day dropdown
   */
  private void updateDaysAndMonthsInComboBox(JComboBox<Integer> yearComboBox,
                                             JComboBox<Integer> monthComboBox,
                                             JComboBox<Integer> dayComboBox) {

    int year = (Integer) yearComboBox.getSelectedItem();

    int maxMonthsInYear = 12;
    if (year == LocalDate.now().getYear()) {
      maxMonthsInYear = LocalDate.now().getMonthValue();
    }
    if (maxMonthsInYear != monthComboBox.getItemCount() ) {
      Integer[] monthsArray = IntStream.range(1, maxMonthsInYear + 1).boxed()
              .toArray(Integer[]::new);
      DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>(monthsArray);
      monthComboBox.setModel(monthModel);
    }

    int month = (Integer) monthComboBox.getSelectedItem();

    // Determine the number of days in the selected month and year
    int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();

    if (month == LocalDate.now().getMonthValue()) {
      //Can't include Today, since stock market hasn't closed yet.
      daysInMonth = LocalDate.now().getDayOfMonth() - 1;
    }

    // Update dayComboBox model based on daysInMonth
    Integer[] daysArray = IntStream.range(1, daysInMonth + 1).boxed().toArray(Integer[]::new);
    DefaultComboBoxModel<Integer> dayModel = new DefaultComboBoxModel<>(daysArray);
    dayComboBox.setModel(dayModel);
  }

  /**
   * Adds the message/output area to the bottom of the view.
   */
  private void addMessageArea() {
    messageArea = new JTextArea(4, 60);
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(messageArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    add(scrollPane, BorderLayout.SOUTH);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void displayMessage(String message) {
    messageArea.append(message + "\n");
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public String getCreatePortfolioNameCommand() {
    String command = portfolioNameField.getText();
    portfolioNameField.setText("");
    return command;
  }

  @Override
  public String getStockTickerCommand() {
    String command = stockTickerField.getText();
    stockTickerField.setText("");
    return command;
  }

  @Override
  public double getStockSharesCommand() {
    double command;
    try {
      command = Double.parseDouble(stockSharesField.getText());
    } catch (Exception e) {
      stockSharesField.setText("");
      throw new IllegalArgumentException("** ENTER A VALID NUMBER OF SHARES **");
    }
    stockSharesField.setText("");
    return command;
  }

  @Override
  public LocalDate getBuySellStockDateCommand() {
    int year = (Integer) buySellYearComboBox.getSelectedItem();
    int month = (Integer) buySellMonthComboBox.getSelectedItem();
    int day = (Integer) buySellDayComboBox.getSelectedItem();
    return LocalDate.of(year, month, day);
  }

  @Override
  public LocalDate getValueStockDateCommand() {
    int year = (Integer) valueYearComboBox.getSelectedItem();
    int month = (Integer) valueMonthComboBox.getSelectedItem();
    int day = (Integer) valueDayComboBox.getSelectedItem();
    return LocalDate.of(year, month, day);
  }

  @Override
  public String getBuySellPortfolioCommand() {
    return (String) buySellPortfolioComboBox.getSelectedItem();
  }

  @Override
  public String getValuePortfolioCommand() {
    return (String) valuePortfolioComboBox.getSelectedItem();
  }

  @Override
  public String getSavePortfolioCommand() {
    return (String) savePortfolioComboBox.getSelectedItem();
  }

  @Override
  public String getLoadPortfolioCommand() {
    return (String) loadPortfolioComboBox.getSelectedItem();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setActivePortfolios(String[] portfolioNames) {
    buySellPortfolioComboBox.setModel(new DefaultComboBoxModel<>(portfolioNames));
    valuePortfolioComboBox.setModel(new DefaultComboBoxModel<>(portfolioNames));
    savePortfolioComboBox.setModel(new DefaultComboBoxModel<>(portfolioNames));
  }

  @Override
  public void setSavedPortfolios(String[] portfolioNames) {
    loadPortfolioComboBox.setModel(new DefaultComboBoxModel<>(portfolioNames));
  }

  @Override
  public void setListeners(ActionListener actionEvent) {
    createButton.addActionListener(actionEvent);
    buySellButton.addActionListener(actionEvent);
    queryButton.addActionListener(actionEvent);
    saveButton.addActionListener(actionEvent);
    loadButton.addActionListener(actionEvent);
  }

}
