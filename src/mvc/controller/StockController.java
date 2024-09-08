package mvc.controller;

/**
 * The StockController interface represents the handling of inputs
 * to the stock program via the controller the StockController interface has
 * two implementations that coexist, the Simple and Advanced controllers, the advanced
 * controller class adds support for the new inputs while delegating any existing inputs
 * to the simple controller.
 */
public interface StockController {

  /**
   * Starts the controller program and handles user input.
   * @throws IllegalStateException throws for illegal inputs.
   */
  void controllerGo() throws IllegalStateException;
}
