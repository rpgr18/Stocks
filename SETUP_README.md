# Setup

Stock Portfolio Management System Setup

Prerequisites:
Java Development Kit (JDK) 8 or higher
Command-line interface (CLI) or terminal
Either a local CSV File formatted with (YYYY-MM-DD, open, high, low, close, volume)
Or an Internet connection so the API is able to download the files for you.
Be mindful! Many of the tests in the test package require an internet connection or valid
CSV file to to properly test this functionality, there are mock tests as well but neither the tests 
That use the API nor the program itself will run properly if there is no valid CSV file in the same
Directory as the executable or the is no internet connection!

TLDR: Connect to internet for easiest experience, or put CSV files named 'TICKER.csv' in the same directory 
as the jar file. 

Note1: Once the API has been queried once for a certain stock, that data is valid and the user no longer needs
to have internet connection to access that data (program will automatically recognize the CSV file) until the next
day. This is because that data is now outdated so the API will automatically overwrite the file and add the missing
recent entries.

Note2: This program supports all stock that are available through the AlphaVantageAPI or have their data downloaded to 
A properly format CSV file that is named: 'TICKER.csv'. For example if there is some foreign stock: 'ABCD' you wish to use but 
It is not found with the API you can import a CSV file called 'ABCD.csv' that is formatted using: (YYYY-MM-DD, open, high, low, close, volume).

Note3: The range of data that this program supports is limited only by the range of data in the CSV file. The API tends to only download 
Stock data back to 1999 or whenever the IPO for that stock was, but if you have a file that has data that goes back further, it will work fine.
If you do happen to query data that is beyond the scope of the CSV file though (either too far in the past or sometime in the future **nice try**),
The program will give you the appropriate error message and prompt you to enter a new date. 

NOTE FOR TESTING: DO NOT RUN ALL TEST CLASSES TOGETHER, some of the tests are using the API and the amount of calls to the api overloads it and result 
In a stock not found error. Instead, run the test classes individually so the api doesn't get overloaded. Also, if many of the tests will fail when not 
Connected to the internet because they use the API. This is on purpose because, as it is central to the function of the program, it makes sense
To test not only with mock data but also using the api while connected to the internet.

Files Required:
Stonks.jar (main executable JAR file)

Running the Program:

1. Open a terminal or command-line interface. 
2. Navigate to the directory containing the JAR file and any required files.
3. Run the program using the following command: Java -jar Stonks.jar

Creating Portfolios and Querying Values:

Creating a Portfolio with 3 Different Stocks

1. Run the program as per previous instructions.
2. The first menu you will see gives you option to
Enter the portfolio menu and to enter the stocks menu.
The stocks menu is for calculating gain-loss, moving average,
etc. so don't worry about that for now.

||—————————— MAIN MENU ——————————||
- '1' : STOCK OPERATIONS
- '2' : PORTFOLIO OPERATIONS
- 'q' : QUIT

3. Enter '2' on your keyboard and press the enterkey 
to use the portfolio menu.
4. Once you have entered the portfolio menu you will see
the following menu:

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

5. To add a new portfolio enter '1'.

ENTER PORTFOLIO NAME:

6. You will be prompted to enter the name of your portfolio, type 
Your desired name and press enter.

Test ADDED TO PORTFOLIO MANAGER.

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

7. You will receive confirmation that you portfolio has been added.
Now to add stocks press '3'.

ACTIVE PORTFOLIOS (Test, )
ENTER A PORTFOLIO NAME:

8. You will be prompted to enter the name of the portfolio you wish to add/remove a stock from.
And there is a list of your active portfolios to help you. Type out the name of the portfolio you
Wish to adjust and press enter (CASE SENSITIVE).

ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG')

9. Now you can enter the ticker of the stock you wish to add. Type it in and press enter.

ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)

10. Now you can type in the number of shares you would like to add.

2.0 SHARES OF GOOG ADDED TO Test

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

11. You will receive confirmation that your shares have been added the portfolio.
Repeat this process 2 more times to have a total of three stocks in your portfolio.

12. Once you have done this you can check to make sure they have been properly added 
By returning entering '5' on your keyboard.

ACTIVE PORTFOLIOS (Test, )
ENTER A PORTFOLIO NAME:

13. You will be prompted to enter the name of the portfolio you wish to inspect.
Enter the name of your portfolio and press the enterkey.

STOCKS IN Test: GOOG: 2.0, NVDA: 15.0, PFE: 2.0, 
||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

14. You can now see that the shares have been successfully added.
15. To make a new portfolio with 2 shares, repeat the steps above.

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

16. Now, to check the value of your portfolio on a specific day you will enter '4'
On your keyboard.

ACTIVE PORTFOLIOS (Test, ad, )
ENTER A PORTFOLIO NAME:

17. Enter the name of the portfolio you want to find the value of.

ENTER A DATE TO FIND VALUE (I.E YYYY-MM-DD):

18. Now enter a date in YYYY-MM-DD format to find the value on that date.

VALUE OF: Test ON 2024-06-06: 18564.0

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

19. The program will print the value and bring you back to the portfolio menu!
20. Repeat the past few steps to find the value of your other portfolio.
21. From the portfolio menu you can also press 'm' to return to the main menu
If you also wish to access stock calculations.

(Running the Advanced Version of the Program)
Files Required:
AdvancedStonks.jar (main executable JAR file)

Running the Program:

1. Open a terminal or command-line interface.
2. Navigate to the directory containing the JAR file and any required files.
3. Run the program using the following command: Java -jar AdvancedStonks.jar

Creating a Portfolio with 3 Different Stocks

1. Run the program as per previous instructions.
2. The first menu you will see gives you option to
Enter the portfolio menu and to enter the stocks menu.
The stocks menu is for calculating gain-loss, moving average,
etc. so don't worry about that for now.

||—————————— MAIN MENU ——————————||
- '1' : STOCK OPERATIONS
- '2' : PORTFOLIO OPERATIONS
- 'q' : QUIT

3. Enter '2' on your keyboard and press the enterkey
to use the portfolio menu.
4. Once you have entered the portfolio menu you will see
the following menu:

||—————————————————— PORTFOLIO MENU ——————————————————||
- '1' : ADD PORTFOLIO           - '7' : DISTRIBUTION
- '2' : REMOVE PORTFOLIO        - '8' : SAVE
- '3' : BUY/SELL                - '9' : LOAD
- '4' : VALUE                   - 'a' : RE-BALANCE
- '5' : COMPOSITION             - 'b' : PLOT
- '6' : LIST ALL PORTFOLIOS     - 'm' : MAIN MENU

5. To add a new portfolio enter '1'.

ENTER PORTFOLIO NAME:

6. You will be prompted to enter the name of your portfolio, type
Your desired name and press enter.

**NAME** ADDED TO PORTFOLIO MANAGER.

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

7. You will receive confirmation that you portfolio has been added.
Now to add stocks press '3'.

ACTIVE PORTFOLIOS (Test, )
ENTER A PORTFOLIO NAME:

8. You will be prompted to enter the name of the portfolio you wish to add/remove a stock from.
And there is a list of your active portfolios to help you. Type out the name of the portfolio you
Wish to adjust and press enter (CASE SENSITIVE).

ENTER A STOCK TICKER TO ADD OR REMOVE: (i.e. 'GOOG')

9. Now you can enter the ticker of the stock you wish to add. Type it in and press enter.

ENTER NUMBER OF SHARES TO ADD OR REMOVE (i.e. 25, -2)

10. Now you can type in the number of shares you would like to add.
(Shares don't have to be whole numbers,
Positive number of Shares represents Buy,
Negative number of Shares represents Sell)

ENTER DATE YEAR (E.G. 999 or 2023):

ENTER DATE MONTH (E.G. 1 or 12):

ENTER DATE DAY (E.G. 1 or 30):

11. Now you can enter the date of your chosen stock you to wish to buy.
The program will ask in order of Year, Month, Day.
Enter a positive whole number after each prompt in compliance with valid dates and press the enterykey.
(Dates can't be before the creation of the Stock or in the Future.)

10.0 SHARES OF GOOG ADDED TO Bob 2023-12-10

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

11. You will receive confirmation that your shares have been added the portfolio.
Repeat this process 2 more times to have a total of three stocks in your portfolio.
(Make each stock with a different Date)

12. Once you have done this you can check to make sure they have been properly added
By returning entering '5' on your keyboard.

ACTIVE PORTFOLIOS (Test, )
ENTER A PORTFOLIO NAME:

13. You will be prompted to enter the name of the portfolio you wish to inspect.
Enter the name of your portfolio and press the enterkey.

ENTER DATE YEAR (E.G. 999 or 2023):

ENTER DATE MONTH (E.G. 1 or 12):

ENTER DATE DAY (E.G. 1 or 30):

14. Now you can enter the date of your chosen stock you to wish to buy.
The program will ask in order of Year, Month, Day.
Enter a positive whole number after each prompt in compliance with valid dates and press the enterykey.
(Dates given before certain Transaction of Stocks have happened won't be accounted for.)

STOCKS IN Test: GOOG: 10.0, NVDA: 15.0, PFE: 2.0,

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

16. Now, to check the value of your portfolio on a specific day you will enter '4'
On your keyboard.

ACTIVE PORTFOLIOS (Test, ad, )
ENTER A PORTFOLIO NAME:

17. Enter the name of the portfolio you want to find the value of.

ENTER DATE YEAR (E.G. 999 or 2023):

ENTER DATE MONTH (E.G. 1 or 12):

ENTER DATE DAY (E.G. 1 or 30):

19. Now you can enter the date of your chosen stock you to wish to buy.
The program will ask in order of Year, Month, Day.
Enter a positive whole number after each prompt in compliance with valid dates and press the enterykey.
(Dates given before certain Transaction of Stocks have happened won't be accounted for.)
(Try Doing one where all the Stock Transaction have happened and one where not all Stock Transaction have happened.)

VALUE OF: Test ON 2024-01-02: $40052.40

||———————————————— PORTFOLIO MENU ————————————————||
- '1' : ADD PORTFOLIO
- '2' : REMOVE PORTFOLIO
- '3' : ADD OR REMOVE STOCKS FROM A PORTFOLIO
- '4' : FIND A PORTFOLIO VALUE
- '5' : LIST STOCKS IN A PORTFOLIO
- '6' : LIST ALL PORTFOLIOS
- 'm' : MAIN MENU

19. The program will print the value and bring you back to the portfolio menu!
20. Repeat the past few steps to find the value of your other portfolio.
21. From the portfolio menu you can also press 'm' to return to the main menu

||—————————— MAIN MENU ——————————||
- '1' : STOCK OPERATIONS
- '2' : PORTFOLIO OPERATIONS
- 'q' : QUIT

22. Enter 'q' and press the enterkey if you wish to exit the program.
