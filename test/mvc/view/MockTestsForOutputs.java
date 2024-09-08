package mvc.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;


import mvc.controller.AdvancedStockController;
import mvc.controller.SimpleStockController;
import mvc.model.AdvancedStockManager;
import mvc.model.BetterStockManager;
import mvc.model.SimpleStockManager;
import mvc.model.StockManager;

import static org.junit.Assert.assertEquals;

/**
 * Class to test outputs of program.
 */
public class MockTestsForOutputs {
  private String start;
  private String menu;
  private String stock;
  private String portfolio;
  private String aPortfolio;
  private String quit;
  private TextStockView view;

  /**
   * Helps set up the values needed universally in this test class.
   */
  @Before
  public void setup() {

    start = "\nWelcome to the stock program!\n";
    menu = "\n" +
            "||—————————— MAIN MENU ——————————||\n" +
            "- '1' : STOCK OPERATIONS\n" +
            "- '2' : PORTFOLIO OPERATIONS\n" +
            "- 'q' : QUIT";
    stock = "\n||————————————————— STOCKS MENU —————————————————||\n" +
            "- '1' : GAIN/LOSS CALCULATOR\n" +
            "- '2' : X-DAY MOVING AVG CALCULATOR\n" +
            "- '3' : X-DAY CROSSOVER CALCULATOR\n" +
            "- 'm' : MAIN MENU";
    portfolio = "\n||———————————————— PORTFOLIO MENU ————————————————||\n" +
            "- '1' : ADD PORTFOLIO\n" +
            "- '2' : REMOVE PORTFOLIO\n" +
            "- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO\n" +
            "- '4' : FIND A PORTFOLIO VALUE\n" +
            "- '5' : LIST STOCKS IN A PORTFOLIO\n" +
            "- '6' : LIST ALL PORTFOLIOS\n" +
            "- 'm' : MAIN MENU";
    aPortfolio = "\n||—————————————————— PORTFOLIO MENU ——————————————————||\n" +
            "- '1' : ADD PORTFOLIO           - '7' : DISTRIBUTION\n" +
            "- '2' : REMOVE PORTFOLIO        - '8' : SAVE\n" +
            "- '3' : BUY/SELL                - '9' : LOAD\n" +
            "- '4' : VALUE                   - 'a' : RE-BALANCE\n" +
            "- '5' : COMPOSITION             - 'b' : PLOT\n" +
            "- '6' : LIST ALL PORTFOLIOS     - 'm' : MAIN MENU";
    quit = "Thank you for using this stock program!";
  }

  /**
   * Test the all the functions in the Menu and Errors.
   *
   * @throws IOException Throws when there is a problem with Input/Output.
   */
  @Test
  public void testDefaultMenu() throws IOException {
    assertEquals("Thank you for using this stock program!", quit);
    //Enters the stock menu and leaves.
    //Then enters the portfolio menu and leaves.
    //Quit Program
    testRun(new SimpleStockManager(),
            inputs(""),
            prints(start + menu),
            inputs("1\n"),
            prints(stock),
            inputs("m\n"),
            prints(menu),
            inputs("2\n"),
            prints(portfolio),
            inputs("m\n"),
            prints(menu),
            inputs("q\n"),
            prints(quit));
    //Enters and unclear Instruction.
    //Can still enter menus.
    //Quit program
    testRun(new SimpleStockManager(),
            inputs(""),
            prints(start + menu),
            inputs("5\n"),
            prints("** UNCLEAR INSTRUCTION: 5 **"),
            inputs("1\n"),
            prints(stock),
            inputs("m\n"),
            prints(menu),
            inputs("2\n"),
            prints(portfolio),
            inputs("m\n"),
            prints(menu),
            inputs("q\n"),
            prints(quit));
  }

  /**
   * Test the all the functions in the Stock Menu.
   *
   * @throws IOException Throws when there is a problem with Input/Output.
   */
  @Test
  public void testStockMenu() throws IOException {
    assertEquals("Thank you for using this stock program!", quit);
    testRun(new SimpleStockManager(),
            //Start Menu
            inputs(""),
            prints(start + menu),
            //Enter Stock Menu
            inputs("1\n"),
            prints(stock),
            //Test for Gain-Loss
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("GAIN/LOSS OF GOOG FOR RANGE 2023-10-11 — 2024-01-20: 6.27\n" + stock),
            //Test for Moving Average
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER X VALUE:"),
            inputs("5\n"),
            prints("5 DAY MOVING AVERAGE OF GOOG ON 2023-10-11: 139.02\n" + stock),
            //Test for CrossOver
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("ENTER X VALUE:"),
            inputs("30\n"),
            prints("30 DAY CROSSOVERS FOR GOOG DURING THE PERIOD  2023-10-11 — 2024-01-20: " +
                    "\n2024-01-19\n" +
                    "2024-01-18\n" +
                    "2024-01-17\n" +
                    "2024-01-16\n" +
                    "2024-01-12\n" +
                    "2024-01-11\n" +
                    "2024-01-10\n" +
                    "2024-01-09\n" +
                    "2024-01-08\n" +
                    "2024-01-05\n" +
                    "2024-01-04\n" +
                    "2024-01-03\n" +
                    "2024-01-02\n" +
                    "2023-12-29\n" +
                    "2023-12-28\n" +
                    "2023-12-27\n" +
                    "2023-12-26\n" +
                    "2023-12-22\n" +
                    "2023-12-21\n" +
                    "2023-12-20\n" +
                    "2023-12-19\n" +
                    "2023-12-18\n" +
                    "2023-12-11\n" +
                    "2023-12-08\n" +
                    "2023-12-07\n" +
                    "2023-11-30\n" +
                    "2023-11-29\n" +
                    "2023-11-28\n" +
                    "2023-11-27\n" +
                    "2023-11-24\n" +
                    "2023-11-22\n" +
                    "2023-11-21\n" +
                    "2023-11-20\n" +
                    "2023-11-17\n" +
                    "2023-11-16\n" +
                    "2023-11-15\n" +
                    "2023-11-14\n" +
                    "2023-10-24\n" +
                    "2023-10-23\n" +
                    "2023-10-20\n" +
                    "2023-10-19\n" +
                    "2023-10-18\n" +
                    "2023-10-17\n" +
                    "2023-10-16\n" +
                    "2023-10-13\n" +
                    "2023-10-12\n" +
                    "2023-10-11\n" + stock),
            //Return to Menu
            inputs("m\n"),
            prints(menu),
            //Quit Program
            inputs("q\n"),
            prints(quit));
  }

  /**
   * Test all Possible misinputs in Stock menu.
   *
   * @throws IOException throws errors for problems in Input/Output
   */
  @Test
  public void testErrorsStockMenu() throws IOException {
    testRun(new SimpleStockManager(),
            inputs(""),
            prints(start + menu),
            //Enter Stock Menu.
            inputs("1\n"),
            prints(stock),
            //Test for Gain-Loss for Invalid ticker Input.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("123\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("** STOCK NOT FOUND, CHECK TICKER FORMAT **\n" + stock),
            //Test for Gain-Loss for Invalid Start Date Format.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("304\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("100\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),
            //Test for Gain-Loss for Invalid Start Date.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("20\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),
            //Test for Gain-Loss for Invalid End Date Format.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("12\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("2023\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),
            //Test for Gain-Loss for Invalid End Date.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("33\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),
            //Test for Gain-Loss for Invalid End Date Format.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2010\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** START DATE MUST PRECEDE END DATE **\n" + stock),
            //Test for Gain-Loss for Invalid Date, before the stock was created.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("1800\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2030\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("7\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **\n" + stock),
            //Test for Gain-Loss for Invalid Date, date is in the future.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2000\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2025\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("7\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **\n" + stock),

            //Test for Gain-Loss for Invalid Date, date is in the past.
            inputs("1\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("1000\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("7\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** HISTORICAL DATA NOT AVAILABLE FOR " +
                    "1000-12-11, TRY A MORE RECENT TIME FRAME **\n\n" + stock),

            //Test for Moving Average, Invalid Ticker.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("321\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER X VALUE:"),
            inputs("5\n"),
            prints("** STOCK NOT FOUND, CHECK TICKER FORMAT **\n" + stock),

            //Test for Moving Average, Invalid Date Format.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("12\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("2023\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for Moving Average, Invalid Date.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for Moving Average, Invalid Date Future Date.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2030\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER X VALUE:"),
            inputs("5\n"),
            prints("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **\n" + stock),

            //Test for Moving Average, Invalid Date Past Date.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("1600\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER X VALUE:"),
            inputs("5\n"),
            prints("** HISTORICAL DATA NOT AVAILABLE FOR 1600-10-11, TRY A MORE RECENT " +
                    "TIME FRAME **"
                    + "\n\n" + stock),

            //Test for Moving Average, Invalid Range.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("ENTER X VALUE:"),
            inputs("-30\n"),
            prints("** ENTER A POSITIVE X-VALUE **\n" + stock),

            //Test for Moving Average, Invalid Range.
            inputs("2\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER X VALUE:"),
            inputs("a\n"),
            prints("** INVALID INPUT: PLEASE ENTER A VALID INTEGER **\n" + stock),

            //Test for CrossOver Invalid Stock Ticker.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("123\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("ENTER X VALUE:"),
            inputs("30\n"),
            prints("** STOCK NOT FOUND, CHECK TICKER FORMAT **\n" + stock),

            //Test for CrossOver Invalid Start Date Format.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("11\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("2023\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for CrossOver Invalid Start Date.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for CrossOver Invalid Start Date.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("02\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for CrossOver Invalid End Date Format.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("01\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("2023\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for CrossOver Invalid End Date.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("01\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + stock),

            //Test for CrossOver Invalid Start and End date switched.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("11\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("01\n"),
            prints("ENTER X VALUE:"),
            inputs("30\n"),
            prints("** START DATE MUST PRECEDE END DATE **\n" + stock),

            //Test for CrossOver Invalid range.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("ENTER X VALUE:"),
            inputs("-30\n"),
            prints("** ENTER A POSITIVE X-VALUE **\n" + stock),

            //Test for CrossOver Invalid Range.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("01\n"),
            prints("ENTER X VALUE:"),
            inputs("a\n"),
            prints("** INVALID INPUT: PLEASE ENTER A VALID INTEGER **\n" + stock),

            //Test for CrossOver Invalid Start Date Before Stock.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("1000\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("01\n"),
            prints("ENTER X VALUE:"),
            inputs("30\n"),
            prints("** HISTORICAL DATA NOT AVAILABLE FOR 1000-10-11, TRY A MORE RECENT " +
                    "TIME FRAME **"
                    + "\n\n" + stock),

            //Test for CrossOver Invalid End Date inFuture.
            inputs("3\n"),
            prints("ENTER STOCK TICKER (i.e. 'GOOG'):"),
            inputs("GOOG\n"),
            prints("ENTER STARTDATE YEAR (E.G. 999 or 2023):"),
            inputs("2020\n"),
            prints("ENTER STARTDATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER STARTDATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("ENTER ENDDATE YEAR (E.G. 999 or 2023):"),
            inputs("2025\n"),
            prints("ENTER ENDDATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER ENDDATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("ENTER X VALUE:"),
            inputs("30\n"),
            prints("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD **\n" + stock),
            inputs("m\n"),
            prints(menu),
            inputs("q\n"),
            prints(quit)
    );
    assertEquals("Thank you for using this stock program!", quit);
  }


  /**
   * Test class to check the misinputs in Portfolio.
   *
   * @throws IOException throws an error if problem occurs in Input/Output
   */
  @Test
  public void testPortfolioMenuErrors() throws IOException {
    testRun(new SimpleStockManager(),
            //Enter Start Menu.
            inputs(""),
            prints(start + menu),
            //Enter Portfolio Menu.
            inputs("2\n"),
            prints(portfolio),
            //Test Add Portfolio.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + portfolio),

            //Test Adding Portfolio Name of one already existing.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("** A PORTFOLIO NAMED: Bobby ALREADY EXISTS **\n" + portfolio),

            //Remove Portfolio Nonexistent Portfolio Name.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + portfolio),

            //Test Add Stocks Nonexistent Portfolio Name.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + portfolio),

            //Test Add Stocks Invalid Ticker.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("123\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("** STOCK NOT FOUND, CHECK TICKER FORMAT **\n" + portfolio),

            //Test Add Stocks Invalid Value.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("a\n"),
            prints("** INVALID INPUT: PLEASE ENTER A VALID NUMBER OF SHARES **\n" + portfolio),

            //Test Add Stocks
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("50\n"),
            prints("50.0 SHARES OF GOOG ADDED TO Bobby\n" + portfolio),

            //Test Remove Stocks more than own
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("-100\n"),
            prints("** YOU CANNOT HAVE NEGATIVE SHARES OF A STOCK **\n" + portfolio),
            //Test list of Stocks in Portfolio of NonExistent.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + portfolio),

            //Test Portfolio Value Nonexistent portfolio.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + portfolio),

            //Test Portfolio Value Invalid Date Format.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("100\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + portfolio),

            //Test Portfolio Value Invalid Date.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("21\n"),
            prints("** INVALID INPUT: PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + portfolio),

            //Test Portfolio Value Invalid Date in the Future.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2025\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("** NICE TRY, THIS PROGRAM " +
                    "CANNOT TELL THE FUTURE XD **\n" + portfolio),

            //Test Portfolio Value Invalid Date Before Stock existed.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("1000\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("12\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("** NO DATA AVAILABLE FOR 1000-12-20 " +
                    "TRY A MORE RECENT DATE **\n" + portfolio),

            //ReEnter Menu.
            inputs("m\n"),
            prints(menu),
            //Quit Program
            inputs("q\n"),
            prints(quit));
    assertEquals("Thank you for using this stock program!", quit);
  }

  /**
   * Test the all the functions in the Portfolio Menu.
   *
   * @throws IOException Throws when there is a problem with Input/Output.
   */
  @Test
  public void testPortfolioMenu() throws IOException {
    testRun(new SimpleStockManager(),
            //Enter Start Menu.
            inputs(""),
            prints(start + menu),
            //Enter Portfolio Menu.
            inputs("2\n"),
            prints(portfolio),
            //Test Add Portfolio.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + portfolio),
            //Remove Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby REMOVED FROM PORTFOLIO MANAGER.\n" + portfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + portfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe ADDED TO PORTFOLIO MANAGER.\n" + portfolio),
            //Test List of Portfolios.
            inputs("6\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, )\n" + portfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("Dave ADDED TO PORTFOLIO MANAGER.\n" + portfolio),
            //Remove Middle Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, Dave, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe REMOVED FROM PORTFOLIO MANAGER.\n" + portfolio),
            //Test Add Stocks to Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("20.0 SHARES OF GOOG ADDED TO Dave\n" + portfolio),
            //Add Multiply Stocks to One Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("50\n"),
            prints("50.0 SHARES OF AAPL ADDED TO Dave\n" + portfolio),
            //Add Stocks to a Different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("30\n"),
            prints("30.0 SHARES OF AAPL ADDED TO Bobby\n" + portfolio),
            //Add multiply Stock to a different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("40\n"),
            prints("40.0 SHARES OF GOOG ADDED TO Bobby\n" + portfolio),
            //Have a Portfolio with more stocks than the other.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("NVDA\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("60\n"),
            prints("60.0 SHARES OF NVDA ADDED TO Bobby\n" + portfolio),
            //Test list of Stocks in Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("STOCKS IN Bobby: AAPL: 30.0, GOOG: 40.0, NVDA: 60.0, " + portfolio),
            //Test list of Stocks in Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("STOCKS IN Dave: GOOG: 20.0, AAPL: 50.0, " + portfolio),
            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Bobby ON 2023-10-11: $39145.60\n" + portfolio),
            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2016\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2016-10-11: $21476.40\n" + portfolio),

            //Remove Stock Reduce Stock Amount.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("NVDA\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("-30\n"),
            prints("-30.0 SHARES OF NVDA ADDED TO Bobby\n" + portfolio),
            //Remove Stock Reduce Stock Amount.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("-10\n"),
            prints("-10.0 SHARES OF GOOG ADDED TO Bobby\n" + portfolio),
            //Remove Stock Entirely.
            //Remove Stock Reduce Stock Amount.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("-20\n"),
            prints("-20.0 SHARES OF GOOG ADDED TO Dave\n" + portfolio),

            //Rerun test to check if Stocks were removed.
            //Test list of Stocks in Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("STOCKS IN Bobby: AAPL: 30.0, GOOG: 30.0, NVDA: 30.0, " + portfolio),
            //Test list of Stocks in Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("STOCKS IN Dave: AAPL: 50.0, " + portfolio),
            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Bobby ON 2023-10-11: $23686.80\n" + portfolio),
            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2016\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2016-10-11: $5815.00\n" + portfolio),
            //Return to Menu.
            inputs("m\n"),
            prints(menu),
            //Quit Program.
            inputs("q\n"),
            prints(quit)
    );
    assertEquals("Thank you for using this stock program!", quit);
  }

  @Test
  public void testAdvancedPortfolioMenu() throws IOException {
    testRun(new AdvancedStockManager(),
            inputs(""),
            prints(start + menu),
            //Enter Portfolio Menu.
            inputs("2\n"),
            prints(aPortfolio),
            //Test Add Portfolio.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Remove Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby REMOVED FROM PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test List of Portfolios.
            inputs("6\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, )\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("Dave ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Remove Middle Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, Dave, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe REMOVED FROM PORTFOLIO MANAGER.\n" + aPortfolio),

            //Test Add Stocks to Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("20.0 SHARES OF GOOG ADDED TO Dave ON 2023-10-10\n" + aPortfolio),
            //Add Multiply Stocks to One Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("50\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("50.0 SHARES OF AAPL ADDED TO Dave ON 2023-10-11\n" + aPortfolio),
            //Add Stocks to a Different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("30\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("9\n"),
            prints("30.0 SHARES OF AAPL ADDED TO Bobby ON 2023-10-09\n" + aPortfolio),

            //Add multiply Stock to a different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("40\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("12\n"),
            prints("40.0 SHARES OF GOOG ADDED TO Bobby ON 2023-10-12\n" + aPortfolio),

            //Have a Portfolio with more stocks than the other.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("NVDA\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("60\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("14\n"),
            prints("60.0 SHARES OF NVDA ADDED TO Bobby ON 2023-10-14\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Bobby ON 2023-10-11: $5394.00\n" + aPortfolio),

            //Test Portfolio Value with all stocks.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("14\n"),
            prints("VALUE OF: Bobby ON 2023-10-14: $38185.30\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2016\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2016-10-11: $0.00\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("2\n"),
            prints("VALUE OF: Bobby ON 2024-01-02: $40052.40\n" + aPortfolio),

            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("11\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2023-11-11: $12001.20\n" + aPortfolio),

            //Test Composition.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("STOCKS IN Bobby ON 2023-10-10: AAPL: 30.0 shares, " + aPortfolio),

            //Test Composition.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("STOCKS IN Bobby ON 2023-10-20: AAPL: 30.0 shares, " +
                    "GOOG: 40.0 shares, NVDA: 60.0 shares, " + aPortfolio),

            //Test Distribution with all stocks.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("AAPL: $5186.4 — 15%\n" +
                    "GOOG: $5469.6 — 15%\n" +
                    "NVDA: $24832.2 — 70%\n" + aPortfolio),

            //Test Distribution with all stocks.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("AAPL: $5351.7 — 100%\n" + aPortfolio),

            //Test Save Multiple Portfolios.
            inputs("8\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby SAVED TO 'portfolios' DIRECTORY\n" + aPortfolio),

            //Test Save Multiple Portfolios.
            inputs("8\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("Dave SAVED TO 'portfolios' DIRECTORY\n" + aPortfolio),

            //Test Plot Week.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("cw\n"),
            prints("Performance of portfolio from 2024-06-06 to 2024-06-13\n" +
                    "\nJun 6, 2024      : ******************************\n" +
                    "Jun 7, 2024      : *****************************\n" +
                    "Jun 8, 2024      : *****************************\n" +
                    "Jun 9, 2024      : *****************************\n" +
                    "Jun 10, 2024     : *******\n" +
                    "Jun 11, 2024     : *******\n" +
                    "Jun 12, 2024     : *******\n" +
                    "Jun 13, 2024     : *******\n" +
                    "\n" +
                    "Scale: * = $2850.0\n" + aPortfolio),

            //Test Plot Month
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("cm\n"),
            prints("Performance of portfolio from 2024-05-13 to 2024-06-13\n" +
                    "\nMay 13, 2024     : ***************************\n" +
                    "May 15, 2024     : ***************************\n" +
                    "May 17, 2024     : ***************************\n" +
                    "May 19, 2024     : ***************************\n" +
                    "May 21, 2024     : ****************************\n" +
                    "May 23, 2024     : ***************************\n" +
                    "May 25, 2024     : ***************************\n" +
                    "May 27, 2024     : ***************************\n" +
                    "May 29, 2024     : ***************************\n" +
                    "May 31, 2024     : ***************************\n" +
                    "Jun 2, 2024      : ***************************\n" +
                    "Jun 4, 2024      : ****************************\n" +
                    "Jun 6, 2024      : ****************************\n" +
                    "Jun 8, 2024      : ****************************\n" +
                    "Jun 10, 2024     : ****************************\n" +
                    "Jun 12, 2024     : ******************************\n" +
                    "Jun 13, 2024     : ******************************\n" +
                    "\n" +
                    "Scale: * = $470.0\n" + aPortfolio),


            //Test Plot Year.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("cy\n"),
            prints("Performance of portfolio from 2023-06-13 to 2024-06-13\n" +
                    "\nJun 13, 2023     : \n" +
                    "Jul 13, 2023     : \n" +
                    "Aug 12, 2023     : \n" +
                    "Sep 11, 2023     : \n" +
                    "Oct 11, 2023     : *\n" +
                    "Nov 10, 2023     : **************\n" +
                    "Dec 10, 2023     : *************\n" +
                    "Jan 9, 2024      : ***************\n" +
                    "Feb 8, 2024      : ******************\n" +
                    "Mar 9, 2024      : **********************\n" +
                    "Apr 8, 2024      : **********************\n" +
                    "May 8, 2024      : ***********************\n" +
                    "Jun 7, 2024      : *****************************\n" +
                    "Jun 13, 2024     : *******\n" +
                    "\n" +
                    "Scale: * = $2850.0\n" + aPortfolio),

            //Test Plot 5 Years.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("c5y\n"),
            prints("Performance of portfolio from 2019-06-13 to 2024-06-13\n" +
                    "\nJun 13, 2019     : \n" +
                    "Nov 12, 2019     : \n" +
                    "Apr 12, 2020     : \n" +
                    "Sep 11, 2020     : \n" +
                    "Feb 10, 2021     : \n" +
                    "Jul 12, 2021     : \n" +
                    "Dec 11, 2021     : \n" +
                    "May 12, 2022     : \n" +
                    "Oct 11, 2022     : \n" +
                    "Mar 12, 2023     : \n" +
                    "Aug 11, 2023     : \n" +
                    "Jan 10, 2024     : ******************************\n" +
                    "Jun 10, 2024     : *************\n" +
                    "Jun 13, 2024     : **************\n" +
                    "\n" +
                    "Scale: * = $1460.0\n" + aPortfolio),
            //Test Rebalance.
            inputs("a\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER REBALANCE DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER REBALANCE DATE MONTH (E.G. 1 or 12):"),
            inputs("5\n"),
            prints("ENTER REBALANCE DATE DAY (E.G. 1 or 30):"),
            inputs("4\n"),
            prints("ENTER A WHOLE NUMBER FOR EACH STOCK BELOW " + System.lineSeparator()
                    + "TO DETERMINE THEIR WEIGHT (i.e. GOOG: $100, PFE: $50 —> " +
                    "1, 1 —> GOOG: $100, PFE: $100):"),
            prints("Distribution for AAPL:"),
            inputs("50\n"),
            prints("Distribution for GOOG:"),
            inputs("50\n"),
            prints("Distribution for NVDA:"),
            inputs("50\n"),
            prints(aPortfolio),

            //Test Distribution After a Rebalance.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("5\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("4\n"),
            prints("AAPL: $21844.8 — 33%\n" +
                    "GOOG: $21844.8 — 33%\n" +
                    "NVDA: $21844.8 — 33%\n" + aPortfolio),
            //Return to Menu.
            inputs("m\n"),
            prints(menu),
            //Quit Program.
            inputs("q\n"),
            prints(quit)

    );

    //Test loading in a previously saved Portfolio.
    testRun(new AdvancedStockManager(),
            inputs(""),
            prints(start + menu),
            //Enter Portfolio Menu.
            inputs("2\n"),
            prints(aPortfolio),
            //Test load Portfolio.
            inputs("9\n"),
            prints("ENTER THE NAME OF THE PORTFOLIO TO LOAD " +
                    "(Must be in portfolio directory): "),
            inputs("Bobby\n"),
            prints("Bobby LOADED, CHECK ACTIVE PORTFOLIOS\n" + aPortfolio),
            //Test load Portfolio.
            inputs("9\n"),
            prints("ENTER THE NAME OF THE PORTFOLIO TO LOAD " +
                    "(Must be in portfolio directory): "),
            inputs("Dave\n"),
            prints("Dave LOADED, CHECK ACTIVE PORTFOLIOS\n" + aPortfolio),

            //Test Composition to ensure they match the saved Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("STOCKS IN Bobby ON 2023-10-20: AAPL: 30.0 shares, " +
                    "GOOG: 40.0 shares, NVDA: 60.0 shares, " + aPortfolio),

            //Test Composition.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("STOCKS IN Dave ON 2023-10-20: " +
                    "GOOG: 20.0 shares, AAPL: 50.0 shares, " + aPortfolio),
            //Return to Menu.
            inputs("m\n"),
            prints(menu),
            //Quit Program.
            inputs("q\n"),
            prints(quit));
    assertEquals("Thank you for using this stock program!", quit);
  }

  @Test
  public void testAdvancedPortfolioMenuErrors() throws IOException {
    testRun(new AdvancedStockManager(),
            inputs(""),
            prints(start + menu),
            //Enter Portfolio Menu.
            inputs("2\n"),
            prints(aPortfolio),
            //Test Add Portfolio.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test Add existing Portfolio Name.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("** A PORTFOLIO NAMED: Bobby ALREADY EXISTS **\n" + aPortfolio),
            //Remove Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby REMOVED FROM PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Test List of Portfolios.
            inputs("6\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, )\n" + aPortfolio),
            //Test Add Multiply Portfolios.
            inputs("1\n"),
            prints("ENTER PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("Dave ADDED TO PORTFOLIO MANAGER.\n" + aPortfolio),
            //Remove Middle Portfolio.
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Ashe, Dave, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("Ashe REMOVED FROM PORTFOLIO MANAGER.\n" + aPortfolio),

            //Test Removing nonexistent Portfolio
            inputs("2\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Add Stocks to Portfolio that doesn't exist.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Add Stocks to Portfolio with date before stock creation.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("999\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("** NO DATA AVAILABLE FOR " +
                    "0999-10-10 TRY A MORE RECENT DATE **\n" + aPortfolio),

            //Test Add Stocks to Portfolio with invalid ticker.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("12394\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("999\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("** STOCK NOT FOUND, CHECK TICKER FORMAT **\n" + aPortfolio),

            //Test Add Stocks to Portfolio with impossible date.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("12394\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("20\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("0\n"),
            prints("** INVALID INPUT: " +
                    "PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + aPortfolio),

            //Add Multiply Stocks to One Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("50\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("50.0 SHARES OF AAPL ADDED TO Dave ON 2023-10-11\n" + aPortfolio),

            //Add Stocks to a Different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("AAPL\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("30\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("9\n"),
            prints("30.0 SHARES OF AAPL ADDED TO Bobby ON 2023-10-09\n" + aPortfolio),

            //Add multiply Stock to a different Portfolio.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("GOOG\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("40\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("12\n"),
            prints("40.0 SHARES OF GOOG ADDED TO Bobby ON 2023-10-12\n" + aPortfolio),

            //Have a Portfolio with more stocks than the other.
            inputs("3\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG') "),
            inputs("NVDA\n"),
            prints("ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)"),
            inputs("60\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("14\n"),
            prints("60.0 SHARES OF NVDA ADDED TO Bobby ON 2023-10-14\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Bobby ON 2023-10-11: $5394.00\n" + aPortfolio),

            //Test Portfolio Value with nonexistent Portfolio.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Portfolio Value Impossible date.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("00\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("30\n"),
            prints("** INVALID INPUT: " +
                    "PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + aPortfolio),

            //Test Portfolio Value with all stocks.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("14\n"),
            prints("VALUE OF: Bobby ON 2023-10-14: $38185.30\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2016\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2016-10-11: $0.00\n" + aPortfolio),

            //Test Portfolio Value before stocks were bought.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("2\n"),
            prints("VALUE OF: Bobby ON 2024-01-02: $40052.40\n" + aPortfolio),

            //Test Portfolio Value.
            inputs("4\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("11\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("11\n"),
            prints("VALUE OF: Dave ON 2023-11-11: $9320.00\n" + aPortfolio),

            //Test Composition nonexistent Portfolio.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Composition.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("121212\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("1010\n"),
            prints("** INVALID INPUT: " +
                    "PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + aPortfolio),

            //Test Composition.
            inputs("5\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("STOCKS IN Bobby ON 2023-10-20: AAPL: 30.0 shares, " +
                    "GOOG: 40.0 shares, NVDA: 60.0 shares, " + aPortfolio),

            //Test Distribution with all stocks.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("AAPL: $5186.4 — 15%\n" +
                    "GOOG: $5469.6 — 15%\n" +
                    "NVDA: $24832.2 — 70%\n" + aPortfolio),

            //Test Distribution with nonexistent Portfolio.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Ashey\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("20\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Distribution with invalid Date.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("13\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("201\n"),
            prints("** INVALID INPUT: " +
                    "PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + aPortfolio),

            //Test Distribution with all stocks.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("10\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("10\n"),
            prints("AAPL: $5351.7 — 100%\n" + aPortfolio),

            //Test Save Multiple Portfolios.
            inputs("8\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("Bobby SAVED TO 'portfolios' DIRECTORY\n" + aPortfolio),

            //Test Save nonexistent Portfolios.
            inputs("8\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Ashe\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Load a Portfolio Same name as existing.
            inputs("9\n"),
            prints("ENTER THE NAME OF THE PORTFOLIO TO LOAD " +
                    "(Must be in portfolio directory): "),
            inputs("Bobby\n"),
            prints("** ATTEMPTING TO LOAD A PORTFOLIO THAT ALREADY EXISTS **\n" +
                    "** IF YOU WISH TO LOAD Bobby " +
                    "FIRST REMOVE THE CURRENT PORTFOLIO WITH THE SAME NAME **\n" + aPortfolio),

            //Test Save Multiple Portfolios.
            inputs("8\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Dave\n"),
            prints("Dave SAVED TO 'portfolios' DIRECTORY\n" + aPortfolio),

            //Test Plot Week.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("cw\n"),
            prints("Performance of portfolio from 2024-06-06 to 2024-06-13\n" +
                    "\nJun 6, 2024      : ******************************\n" +
                    "Jun 7, 2024      : *****************************\n" +
                    "Jun 8, 2024      : *****************************\n" +
                    "Jun 9, 2024      : *****************************\n" +
                    "Jun 10, 2024     : *******\n" +
                    "Jun 11, 2024     : *******\n" +
                    "Jun 12, 2024     : *******\n" +
                    "Jun 13, 2024     : *******\n" +
                    "\n" +
                    "Scale: * = $2850.0\n" + aPortfolio),

            //Test Plot with nonexistent Portfolio.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("ashe\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("w\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Plot with invalid input.
            inputs("b\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE RANGE ('w' = WEEK, 'm' = MONTH, 'y' = YEAR, " +
                    "'5y' = FIVE YEARS, '10y' = TEN YEARS):"),
            inputs("10\n"),
            prints("** ENTER A VALID DATE RANGE (i.e. w, m, y, 5y) **\n" + aPortfolio),

            //Test Rebalance nonexistent Portfolio.
            inputs("a\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("ashe\n"),
            prints("ENTER REBALANCE DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER REBALANCE DATE MONTH (E.G. 1 or 12):"),
            inputs("5\n"),
            prints("ENTER REBALANCE DATE DAY (E.G. 1 or 30):"),
            inputs("4\n"),
            prints("** NO SUCH PORTFOLIO FOUND **\n" + aPortfolio),

            //Test Rebalance invalid Date.
            inputs("a\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("ashe\n"),
            prints("ENTER REBALANCE DATE YEAR (E.G. 999 or 2023):"),
            inputs("2023\n"),
            prints("ENTER REBALANCE DATE MONTH (E.G. 1 or 12):"),
            inputs("30\n"),
            prints("ENTER REBALANCE DATE DAY (E.G. 1 or 30):"),
            inputs("91\n"),
            prints("** INVALID INPUT: " +
                    "PLEASE ENTER DATE USING YYYY-MM-DD FORMAT **\n" + aPortfolio),

            //Test Rebalance invalid Future Date.
            inputs("a\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER REBALANCE DATE YEAR (E.G. 999 or 2023):"),
            inputs("2123\n"),
            prints("ENTER REBALANCE DATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER REBALANCE DATE DAY (E.G. 1 or 30):"),
            inputs("2\n"),
            prints("** EASY THERE, THIS PROGRAM CANNOT TELL THE FUTURE **\n" + aPortfolio),

            //Test Rebalance invalid Past Date.
            inputs("a\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER REBALANCE DATE YEAR (E.G. 999 or 2023):"),
            inputs("123\n"),
            prints("ENTER REBALANCE DATE MONTH (E.G. 1 or 12):"),
            inputs("1\n"),
            prints("ENTER REBALANCE DATE DAY (E.G. 1 or 30):"),
            inputs("2\n"),
            prints("** NO STOCKS IN PORTFOLIO ON 0123-01-02 **\n" + aPortfolio),


            //Test Distribution After a failed Rebalance.
            inputs("7\n"),
            prints("ACTIVE PORTFOLIOS (Bobby, Dave, )\n" + "ENTER A PORTFOLIO NAME:"),
            inputs("Bobby\n"),
            prints("ENTER DATE YEAR (E.G. 999 or 2023):"),
            inputs("2024\n"),
            prints("ENTER DATE MONTH (E.G. 1 or 12):"),
            inputs("5\n"),
            prints("ENTER DATE DAY (E.G. 1 or 30):"),
            inputs("4\n"),
            prints("AAPL: $5501.4 — 8%\n" +
                    "GOOG: $6759.6 — 10%\n" +
                    "NVDA: $53273.4 — 81%\n" + aPortfolio),
            //Return to Menu.
            inputs("m\n"),
            prints(menu),
            //Quit Program.
            inputs("q\n"),
            prints(quit)
    );
    assertEquals("Thank you for using this stock program!", quit);
  }

  private void testRun(BetterStockManager model,
                       Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    Readable rd = new StringReader(fakeUserInput.toString());

    Appendable ap = new StringBuilder();
    AdvancedTextStockView sView = new AdvancedTextStockView(ap);

    AdvancedStockController controller = new AdvancedStockController(model, sView, rd);
    controller.controllerGo();

    assertEquals(expectedOutput.toString(), sView.viewToString() + "\n");

  }

  private void testRun(StockManager model,
                       Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    Readable rd = new StringReader(fakeUserInput.toString());

    Appendable ap = new StringBuilder();
    SimpleTextStockView sView = new SimpleTextStockView(ap);

    SimpleStockController controller = new SimpleStockController(model, sView, rd);
    controller.controllerGo();

    assertEquals(expectedOutput.toString(), sView.viewToString() + "\n");

  }

  private static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  private static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in);
    };
  }


}
