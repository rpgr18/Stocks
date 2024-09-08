# Stocks
OOP Investment tracking and portfolio management application in Java.

Features for Program:
The Default Menu when you first start up the program will provide you with these options below:
- '1' : STOCK OPERATIONS.
- '2' : PORTFOLIO OPERATIONS.
- 'q' : QUIT.
Enter '1' For Stock Operations which will allow you to see all the Features related to Stock.
Enter '2' For Portfolio Operations,
 which will allow you to see all the Features related to Portfolio.
Enter 'q' To Quit the Program.

(Stock Operations)
After entering '1' you'll be brought to the Stock Menu
||————————————————— STOCKS MENU —————————————————||
- '1' : GAIN/LOSS CALCULATOR
- '2' : X-DAY MOVING AVG CALCULATOR
- '3' : X-DAY CROSSOVER CALCULATOR
- 'm' : MAIN MENU

Enter '1' To use the Gain/Loss Calculator, which will calculate the Gain/Loss of your Stock over a given range of Time.

Enter '2' To use the Moving Average Calculator, which will calculate the average of the last X given days.

Enter '3' To use the X-Day Crossover Calculator, which will calculate which days in the given range
end with a closing price for a day that greater than the x-day moving average for that day.

Users will be prompted in order of Stock Ticker, then Dates and finally Duration

Enter 'm' To return to the starting menu

After every use of a feature the User will stay in their current Menu

(Portfolio Operations)
After entering '2' you'll be brought to the Portfolio Menu
||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

Enter '1' To add a Portfolio that you can add and remove stocks from,
Portfolio can't share the same name as another Portfolio.

Enter '2' To remove a Portfolio, you can't remove Portfolios that don't exist.

Enter '3' To add or remove stocks from a portfolio,
Users will be given a list of their existing portfolios to choose from,
You can't add or remove stocks from a nonexistent portfolio,
You can't add a nonexistent stock to a portfolio,
You can't remove more stocks than the Portfolio holds,
When a stock in a Portfolio reaches 0 the stock will be removed from the Portfolio,

Enter '4' To find the value of your Portfolio on a given date, won't work if given a nonexistent portfolio.
Users will be given a list of their existing portfolios to choose from,

Enter '5' To list the stocks in your portfolio,
Users will be given a list of their existing portfolios to choose from,
Will not show the stocks of a nonexistent portfolio,

Enter '6' To list all the portfolios.

Enter 'm' To return to the starting menu.


Features will not account for Stock Splits when calculating final values.
Features will not work if given incomplete inputs or inputs that don't follow the given format,
User will be returned to the last used Menu and told why their inputs were deemed invalid.
This also includes invalid dates like 2023-30-33, dates in the future and dates that were before the stocks creation.
There are hierarchies to errors so if your input violates multiple parameters they will not all be shown.

NEW ADDED Features in AdvancedStockController:

The Default Menu when you first start up the program will provide you with these options below:
- '1' : STOCK OPERATIONS.
- '2' : PORTFOLIO OPERATIONS.
- 'q' : QUIT.
Enter '1' For Stock Operations which will allow you to see all the Features related to Stock.
Enter '2' For Portfolio Operations, which will allow you to see all the Features related to Portfolio.
Enter 'q' To Quit the Program.

(Stock Operations)
After entering '1' you'll be brought to the Stock Menu
||————————————————— STOCKS MENU —————————————————||
- '1' : GAIN/LOSS CALCULATOR
- '2' : X-DAY MOVING AVG CALCULATOR
- '3' : X-DAY CROSSOVER CALCULATOR
- 'm' : MAIN MENU

Enter '1' To use the Gain/Loss Calculator, which will calculate the Gain/Loss of your Stock over a given range of Time.

Enter '2' To use the Moving Average Calculator, which will calculate the average of the last X given days.

Enter '3' To use the X-Day Crossover Calculator, which will calculate which days in the given range
end with a closing price for a day that greater than the x-day moving average for that day.

Users will be prompted in order of Stock Ticker, then Dates and finally Duration

Enter 'm' To return to the starting menu

(Advanced Portfolio Operations)
After entering '2' you'll be brought to the Portfolio Menu
||—————————————————— PORTFOLIO MENU ——————————————————||
- '1' : ADD PORTFOLIO           - '7' : DISTRIBUTION
- '2' : REMOVE PORTFOLIO        - '8' : SAVE
- '3' : BUY/SELL                - '9' : LOAD
- '4' : VALUE                   - 'a' : RE-BALANCE
- '5' : COMPOSITION             - 'b' : PLOT
- '6' : LIST ALL PORTFOLIOS     - 'm' : MAIN MENU

Enter '1' To add a Portfolio that you can add and remove stocks from,
Portfolio can't share the same name as another Portfolio.

Enter '2' To remove a Portfolio, you can't remove Portfolios that don't exist.

Enter '3' To add or remove stocks from a portfolio,
Users will be given a list of their existing portfolios to choose from,
User will be prompt to enter the Quantity of Stock they would like to add/remove,
(Positive Doubles represents Add, Negative Doubles represents Remove, Can enter Doubles now),
Users will be prompt to enter the Year, Month and Day of the Stock they would like to adjust,
You can't add or remove stocks from a nonexistent portfolio,
You can't add a nonexistent stock to a portfolio,
You can't give a nonexistent stock ticker,
You can't give an invalid date,
You can't remove more stocks than the Portfolio holds,
When a stock in a Portfolio reaches 0 the stock will be removed from the Portfolio,

Enter '4' To find the value of your Portfolio on a given date, won't work if given a nonexistent portfolio.
Users will be given a list of their existing portfolios to choose from,
User will be prompt for which Date of the Portfolio they would like to see the value of,
You can't give a date in the future,
You can't give an invalid date,
Stocks that didn't exist before the Date given to the Portfolio will not be accounted for.


Enter '5' To list the stocks in your portfolio,
Users will be given a list of their existing portfolios to choose from,
Users will be prompt for which Date would they like to see the existing Stocks in the Portfolio,
Will not show the stocks of a nonexistent portfolio,
You can't give a date in the future,
You can't give an invalid date,
Stocks that didn't exist before the Date given will not show,
and the user will be given a message of missing stocks not shown.

Enter '6' To list all the portfolios.

Enter '7' To see the distribution of the Portfolio's value in different Stocks
Users will be given a list of their existing portfolios to choose from,
Users will be prompt for which Date would they like to see the distribution of Stocks in the Portfolio,
You can't give a date in the future,
You can't give an invalid date,
Stocks that didn't exist before the Date will not be accounted for in the distribution.

Enter '8' To save a chosen Portfolio
Users will be given a list of their existing portfolios to choose from,
You can't give the name of a nonexistent portfolio,

Enter '9' To Load a chosen Portfolio
Users will be prompt to give the name of a save portfolio in their directory,
You can't give the name of a nonexistent portfolio,
You can't give the name of an existing portfolio,
To override an existing portfolio with the same name as a saved on in your directory,
You must first delete the existing portfolio.

Enter 'a' To Rebalance the Portfolio to a given date
Users will be given a list of their existing portfolios to choose from,
Users will be prompt for which Date would they like to see the Rebalance the Stocks in the Portfolio,
User will be prompt to give a distribution value to each stock individually,
You can't give the name of a nonexistent portfolio,
You can't give a date in the future,
You can't give an invalid date,
Rebalancing an empty Portfolio or providing a date where the Portfolio is empty won't do anything,
Stocks that didn't exist before the Date will not be accounted for in the distribution.
Distribution values don't have to add up to 100, as the program will determine the ratio by,
dividing each Distribution value by the total Distribution value given to all stocks.

Enter 'b' To plot the progress of you're Portfolio over a course of range of time
Users will be given a list of their existing portfolios to choose from,
Users will be prompt to select a Date Range,
(w = WEEK, m = MONTH, y = YEAR, 5y = FIVE YEARS, 10y = TEN YEARS)
You can't give the name of a nonexistent portfolio,
You can't give an unprovided date range.

Enter 'm' To return to the starting menu.

After every use of a feature the User will stay in their current Menu

GUI Features in GraphicsStockController:
When first launched the user will be shown a Stock Portfolio Manager with 4 tabs that contain all features

First, Create New Portfolio

Second, Buy or Sell Stocks

Third, Value of Portfolio

Fourth, Save or Load Portfolio

Users will initially be presented with the "Create New Portfolio" Tab, and can switch in between tabs by pressing on them.

"Create new Portfolio" Features:

Users can enter their desired Portfolio Name in the text box under "Portfolio Name:" and then press the button "Create Portfolio" to create the Portfolio.
A message  will appear in the box below the menu saying "Portfolio **GIVEN NAME** saved successfully."
If the given Portfolio Name already exists a popup will appear with the message "A PORTFOLIO NAMED: **GIVEN NAME** ALREADY EXISTS".

"Buy or Sell Stocks" Features:

Users can select your desired Portfolio through the dropdown menu under "Select Portfolio:",
Users can enter their desired stock ticker in the text box below "Stock Ticker",
Users can enter their desired number of Shares in the text box below "Shares",
Users can select their desired date of the stock in the selection boxes below "Select Date:",
Users can than confirm their actions by pressing the button "Buy/Sell",
Nothing will happen if the User enters all field without selecting a Portfolio,
Users will receive a popup if they enter an invalid ticker, warning them about their error
Users will receive a popup if they don't enter a number into Shares, warning them about their error,
Users will receive a popup if their selected date is before the creation of the stock they want to buy, warning them about their error
When selecting the date, users aren't allowed to select a date in the future,
If the date ranges are changed due to a change in the date it will be reset to the smallest value.

"Value of Portfolio" Features:
Users can select your desired Portfolio through the dropdown menu under "Select Portfolio:",
Users can select their desired date of the stock in the selection boxes below "Select Date:",
Users can than confirm their actions by pressing the button "Query Value",
Nothing will happen if the User enters all field without selecting a Portfolio,
When selecting the date, users aren't allowed to select a date in the future,
If the date ranges are changed due to a change in the date it will be reset to the smallest value.
A date chosen before the existence of a stock or before it was bought will not be included in the final value.

"Save or Load Portfolio" Features:

Users can select their desired Portfolio to save through the dropdown menu below "Save Portfolio:",
Users can select their desired Portfolio to load through the dropdown menu below "Load Portfolio:",
Only Portfolios saved in your directory can be loaded
To override an existing portfolio with the same name as a saved on in your directory,
You must first delete the existing portfolio.
Saving a Portfolio that shares the same name of a portfolio that has already been saved will override the old saved portfolio.

At the Bottom of the Stock Portfolio it will display a notification of the user's actions,

A warning textbox will appear if an error has occurred.

Features will not account for Stock Splits when calculating final values.
Features will not work if given incomplete inputs or inputs that don't follow the given format,
User will be returned to the last used Menu and told why their inputs were deemed invalid.
This also includes invalid dates like 2023-30-33, dates in the future and dates that were before the stocks creation.
There are hierarchies to errors so if your input violates multiple parameters they will not all be shown.
