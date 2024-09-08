package mvc.controller;

import java.util.Scanner;

import mvc.model.BetterStockManager;
import mvc.view.TextStockView;

/**
 * The AdvancedStockController class is the new controller that supports upgraded functionality
 * over the SimpleStockController. This new controller now supports operations like plotting,
 * re-balancing, save/load, etc... The AdvancedStockController extends the SimpleStockController
 * and adds support for the new features while calling the super class to handle unchanged inputs.
 */
public class AdvancedStockController extends SimpleStockController {
  //store and process the data
  private BetterStockManager model;
  //keep track and respond to output requests from the controller
  private TextStockView view;
  //scan for user input
  private Scanner sc;

  /**
   * Constructs an AdvancedStockController object. This constructor replaces the StockManager
   * interface from the super constructor with the BetterStockManager interface that extends the
   * model's functionality.
   *
   * @param model the model of the program
   * @param view the view of the program
   * @param readable handle user input
   */
  public AdvancedStockController(BetterStockManager model, TextStockView view, Readable readable) {
    super(model, view, readable);
    this.model = model;
    this.view = view;
    this.sc = new Scanner(readable);
  }

  /**
   * Starts the controller program and scans for user input. Any input for old/existing
   * functionality is passed to the old SimpleStockController method. All added functionality
   * is now handled using the AdvancedPortfolioMenu.
   * @throws IllegalStateException throws for illegal inputs.
   */
  @Override
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
            new AdvancedPortfolioMenu(model, view, sc).apply(func);
          }
          break;
        case "q":
        case "quit":
          quit = true;
          break;
        default:
          this.view.writeMessage("Unclear Instruction: " + userInstruction
                  + System.lineSeparator());
          userInstruction = sc.next(); //take an instruction name

      }

    }

    //after the user has quit, print farewell message
    this.view.farewellMessage();

  }

}
