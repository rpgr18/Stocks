package mvc.view;

/**
 * The {@code TextStockView} interface represents the handling of displaying
 * results, menus, any other messages to the user/system.
 * Changes since P1: This interface was added and methods in the view class were pulled to
 * the interface to allow for a general TextStockView interface with separate implementations.
 */
public interface TextStockView {

  /**
   * The writeMessage method takes a string input and outputs the corresponding message to
   * the user.
   * @param message the desired message
   * @throws IllegalStateException if state/input is invalid
   */
  void writeMessage(String message) throws IllegalStateException;

  /**
   * The stockInstructionsMenu method displays the instructions for the stock menu.
   */
  void stockInstructionsMenu();

  /**
   * The portfolioInstructionsMenu method displays the instructions for the portfolio menu.
   */
  void portfolioInstructionMenu();

  /**
   * The printMenu method displays the main menu.
   */
  void printMenu();

  /**
   * The welcomeMessage method displays the welcome message.
   */
  void welcomeMessage();

  /**
   * The farewellMessage method displays the farewell message.
   */
  void farewellMessage();

}
