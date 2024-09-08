package mvc.controller;

import java.time.LocalDate;

/**
 * The Menus interface is used to help replace a long switch statement in the controller
 * this is designed to be a helper system to the controller that handles user inputs
 * for specific menus, i.e. stock menu, portfolio menu. The controller will then call
 * the methods in menu with its parameters to handle the users input.
 */

interface Menus {

  /**
   * The promptDate method is a general method that prompt the user for
   * a date and records the input for use by the controller.
   * @param time either "START", "END", etc... to label whether it is asking
   *             for the start date, end date, etc...
   * @return the local date as recorded by the inputs
   */
  LocalDate promptDate(String time);

  /**
   * The apply method takes in the input and applies a helper function to send the
   * recorded input to the model/view.
   * @param input the users input.
   */
  void apply(String input);
}
