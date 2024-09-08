package mvc.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import mvc.model.StockManager;
import mvc.view.TextStockView;

/**
 * The AbstractMenus class is an abstract class that houses common methods to all menus like
 * prompting for the date, etc...
 */
abstract class AbstractMenus implements Menus {
  //stores the data of the program
  protected StockManager model;
  //keeps Track of responding output
  protected TextStockView view;
  //takes in user input
  protected Scanner sc;

  /**
   * Controller will pass its parameters to the menu to help cut down on long switch
   * statements in the controllerGo method.
   *
   * @param model the model of the program
   * @param view the view
   * @param sc the input scanner
   */
  protected AbstractMenus(StockManager model, TextStockView view, Scanner sc) {
    if ((model == null) || (sc == null) || (view == null)) {
      throw new IllegalArgumentException("Model, readable or appendable is null!");
    }
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public LocalDate promptDate(String time) {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    this.view.writeMessage("ENTER " + time + "DATE YEAR (E.G. 999 or 2023):"
            + System.lineSeparator());
    String year = String.format("%4s", sc.next()).replace(' ', '0');
    this.view.writeMessage("ENTER " + time + "DATE MONTH (E.G. 1 or 12):"
            + System.lineSeparator());
    String month = String.format("%2s", sc.next()).replace(' ', '0');
    this.view.writeMessage("ENTER " + time + "DATE DAY (E.G. 1 or 30):"
            + System.lineSeparator());
    String day = String.format("%2s", sc.next()).replace(' ', '0');
    String date = year + "-" + month + "-" + day;
    try {
      return LocalDate.parse(date, dateFormat);
    } catch (RuntimeException e) {
      throw new RuntimeException("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **");
    }
  }

  @Override
  public abstract void apply(String input);

}
