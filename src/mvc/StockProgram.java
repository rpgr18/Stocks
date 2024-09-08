package mvc;

import java.io.InputStreamReader;

import mvc.controller.AdvancedStockController;
import mvc.controller.GraphicsStockController;
import mvc.controller.StockController;
import mvc.model.AdvancedStockManager;
import mvc.model.BetterStockManager;
import mvc.view.AdvancedTextStockView;
import mvc.view.GraphicsStockView;
import mvc.view.SimpleGraphicsStockView;
import mvc.view.TextStockView;

/**
 * Method to run the Stock program.
 */
public class StockProgram {

  /**
   * Main method.
   * @param args arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // No arguments provided, launch graphical interface
      BetterStockManager model = new AdvancedStockManager();
      GraphicsStockView view = new SimpleGraphicsStockView();
      StockController controller = new GraphicsStockController(model, view);
      controller.controllerGo();
    } else if (args.length == 1 && args[0].equals("-text")) {
      // "-text" argument provided, launch text-based interface
      BetterStockManager model = new AdvancedStockManager();
      Readable rd = new InputStreamReader(System.in);
      Appendable ap = System.out;
      TextStockView view = new AdvancedTextStockView(ap);
      StockController controller = new AdvancedStockController(model, view, rd);
      controller.controllerGo();
    } else {
      // Invalid arguments provided
      System.err.println("Invalid arguments. Usage:");
      System.err.println("java -jar Program.jar       // to open the graphical interface");
      System.err.println("java -jar Program.jar -text // to open the text-based interface");
      System.exit(1);
    }
  }
}



