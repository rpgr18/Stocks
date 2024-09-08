package mvc.controller;

import java.util.Scanner;

import mvc.model.StockManager;
import mvc.view.TextStockView;

/**
 * Controller class that processes user input this SimpleStockController is outdated
 * because it doesn't support the new functions, but it is still used to handle any unchanged
 * functionality and the inputs that are used for that.
 */
public class SimpleStockController implements StockController {
  //Store the data of the program
  private StockManager model;
  // Keeps Track of responding output
  private TextStockView view;
  //Takes in user input
  private Scanner sc;

  /**Controller constructor.
   *
   * @param model the model
   * @param view the view to take in user inputs
   * @param readable readable
   */
  public SimpleStockController(StockManager model, TextStockView view, Readable readable) {
    if ((model == null) || (readable == null) || (view == null)) {
      throw new IllegalArgumentException("** MODEL, READABLE OR APPENDABLE IS NULL **");
    }
    this.model = model;
    this.view = view;
    this.sc = new Scanner(readable);
  }

  /**
   * Starts the controller program.
   * @throws IllegalStateException throws for illegal inputs.
   */
  public void controllerGo() throws IllegalStateException {
    boolean quit = false;

    //print the welcome message
    this.view.welcomeMessage();
    this.view.printMenu();
    String userInstruction = sc.next(); //take an instruction name

    String func;

    while (!quit) { //continue until the user quits

      switch (userInstruction) {
        case "1": //assign a value to a cell

          this.view.stockInstructionsMenu();
          func = sc.next();
          if (func.equals("m") || func.equals("menu")) {
            this.view.printMenu();
            userInstruction = sc.next();
          } else {
            new SimpleStockMenu(model, view, sc).apply(func);
          }
          break;
        case "2":

          this.view.portfolioInstructionMenu();
          func = sc.next();
          if (func.equals("m") || func.equals("menu")) {
            this.view.printMenu();
            userInstruction = sc.next();
          } else {
            new SimplePortfolioMenu(model, view, sc).apply(func);
          }
          break;
        case "q":
        case "quit":
          quit = true;
          break;
        default:
          this.view.writeMessage("** UNCLEAR INSTRUCTION: " + userInstruction + " **"
                  + System.lineSeparator());
          userInstruction = sc.next(); //take an instruction name

      }

    }

    //after the user has quit, print farewell message
    this.view.farewellMessage();

  }



}
