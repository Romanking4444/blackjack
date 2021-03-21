// BlackjackV2.java
// Arda Utku
// Jan 14, 2021
// VERSION 2
// This program runs the game Blackjack.

import java.util.*;

public class BlackJackV2 
{

    // Global constants 
    final static int BLACKJACK_SUM = 21;
    final static int ACE_VALUE = 11;
    final static int MAX_HAND_SIZE = 13;
    final static int INITIAL_MONEY_AMOUNT = 1000;
    final static int DEALER_CARD_LIMIT = 16;
    final static int NUM_PLAYERS = 2;
    final static int PLAYER = 0;
    final static int DEALER = 1;
    
    /*===============================================================================|
    | void createDeck(int[] deck, String[] labels, int[] values)                     |
    |--------------------------------------------------------------------------------|
    | returns void                                                                   |
    |--------------------------------------------------------------------------------|
    | int[] deck - The deck of cards to be set up                                    |
    |--------------------------------------------------------------------------------|
    | String[] labels - The label of each card                                       |
    |--------------------------------------------------------------------------------|
    | int[] values - The game value of each card                                     |
    |--------------------------------------------------------------------------------|
    | This method will shuffle an ordered deck as it would in real life by switching |
    | the position of 2 cards on the deck multiple times.                            |
    ================================================================================*/

    public static void createDeck(int[] deck, String[] labels, int[] values)
    {
        // Variables 
        int m;
        int secondIndex;
        int firstIndex;

        // Assign labels to cards based on the card index (for example label 3 would be 2 of hearts)
        for (int i = 0; i < labels.length; i++)
        {
            m = i % 13;

            if (i < 13)
            {
                labels[i] = "♥ ";
            }
            else if (i < 26)
            {
                labels[i] = "♠ ";
            }
            else if (i < 39)
            {
                labels[i] = "♣ ";
            }
            else 
            {
                labels[i] = "♦ ";
            }

            if (m == 0)
            {
                labels[i] = labels[i] + "ACE";
            }
            else if (m < 10)
            {
                labels[i] = labels[i] + (m + 1);
            }
            else if (m == 10)
            {
                labels[i] = labels[i] + "JACK";
            }
            else if (m == 11)
            {
                labels[i] = labels[i] + "QUEEN";
            }
            else if (m == 12)
            {
                labels[i] = labels[i] + "KING";
            }
        }

        // Create an ordered deck for example deck[3] would be card index 3 which would have the label 2 of hearts
        // Position 0 is the top of the deck 
        for (int position = 0; position < deck.length; position++)
        {
            deck[position] = position;
        }

        // Shuffle the ordered deck by randomly selecting two cards and swapping them 52 times
        for (int i = 0; i < deck.length; i++)
        {
            firstIndex = (int)(Math.random() * 52);
            secondIndex = (int)(Math.random() * 52);

            int temp = deck[firstIndex];
            deck[firstIndex] = deck[secondIndex];   // Switches the position of two cards in the deck 
            deck[secondIndex] = temp;
        }

        // Assign card values for sum calculations
        for (int i = 0; i < values.length; i++)
        {
            m = i % 13;

            // 2 to 10 have the same numeric values. J,Q, and K will be 10, ACE will be 11 by defult 
            if (m == 0)
            {
                values[i] = ACE_VALUE;
            }
            else if (m < 10)
            {
                values[i] = (m + 1);
            }
            else
            {
                values[i] = 10;
            } 
        }
    }

    /*==================================================================================|
    | void printHand(int[] hand, int handSize, String[] labels, boolean hideSecondCard) |
    |-----------------------------------------------------------------------------------|
    | returns void                                                                      |
    |-----------------------------------------------------------------------------------|
    | int[] hand - The hand of a player to be printed                                   |
    |-----------------------------------------------------------------------------------|
    | int handSize - number card in hand                                                |
    |-----------------------------------------------------------------------------------|
    | String[] labels - The label of each card                                          |
    |-----------------------------------------------------------------------------------|
    | boolean hideSecondCard - Checks if the dealer's second card is hidden             |
    |-----------------------------------------------------------------------------------|
    | This method will print out a table with the dealer's and player's current hands   |
    | based on whichever is passed into the method.                                     |
    | Table will extend as the dealer or player draw more cards.                        |
    ===================================================================================*/

    public static void printHand(int[] hand, int handSize, String[] labels, boolean hideSecondCard)
    {
        int currentCardIndex;

        // Prints a table holding the player's and dealer's cards, outputed table updates and gets larger as more cards are drawn
        for (int i = 0; i < handSize; i++)
        {
            System.out.print("----------");
        }
        System.out.println("-");

        System.out.print("|");

        for (int i = 0; i < handSize; i++)
        {
            // Dealer's second card will remain hidden until the player is done his turn 
            if (i == 1 && hideSecondCard == true)
            {
                System.out.printf(" %-7s |", "HIDDEN"); 
            }
            else
            {
                currentCardIndex = hand[i];
                System.out.printf(" %-7s |", labels[currentCardIndex]);
            }
        }

        System.out.println("");
        for (int i = 0; i < handSize; i++)
        {
            System.out.print("----------");
        }
        System.out.println("-");

        // Slow down output to make it easier for the player to see what if going on
        delayPrint();
    }

    /*===============================================================================|
    | void delayPrint                                                                |
    |--------------------------------------------------------------------------------|
    | returns void                                                                   |
    |--------------------------------------------------------------------------------|
    | This method will delay the program output by 1 second to give the player time  |
    | to read the output.                                                            |
    ================================================================================*/

    public static void delayPrint()
    {
        try
        {
            Thread.sleep(1000); 
        }
        catch (Exception e)
        {
            // Ignore all excpetions
        }
    }

    /*===============================================================================|
    | void printHelp                                                                 |
    |--------------------------------------------------------------------------------|
    | returns void                                                                   |
    |--------------------------------------------------------------------------------|
    | This method prints out the help menu with the basic rules of the game.         |
    ================================================================================*/

    public static void printHelp()
    {
        System.out.println("Welcome to the help menu. Here you can read the rules of the game.\n");
        System.out.println("The rules of Blackjack are relativly simple.\n");
        System.out.println("- To win the game the player must either get BLACKJACK (having the sum of your cards add to 21) or have a value of cards higher then the dealers value of cards\n");
        System.out.println("- A player will bust if they have a value greater then 21, meaning they have lost the game.\n");
        System.out.println("- The rules above also apply to the dealer meaning they will also win the game if any of the aformentioned win conditions are met for his hand.\n");
        System.out.println("- Once the game starts and the players bet is in the dealer will shuffle a deck of 52 regular playing cards.\n");
        System.out.println("- An ace will be treated as either a 1 or an 11 dpending on which is more optimal based on the rest of the player or dealer's hand.");
        System.out.println("- Following that the dealer will deal 4 cards, two cards will go to the dealer and two cards will go to the player.\n");
        System.out.println("- The dealers second card will be hidden to the player until the player complets their turn.\n");
        System.out.println("- Once the cards have been delt the player has three choices: stand, hit, or double down.\n");
        System.out.println("- Stand will end your turn with your current hand, hit will draw a new card from the deck and be added to your hand,\n");
        System.out.println("  and double down will cause a hit but also double your current bet.\n");
        System.out.println("- Once the player has finished their turn the dealer will go and make a turn based on their hand\n");
        System.out.println("- The dealer must hit if their hand is less 16 or less, but will always stand if their hand is greater then 16.\n");
        System.out.println("- You now know the basic rules of the game. Good luck in your games!\n");
    }

    /*===============================================================================|
    | boolean isCardAnAce(int[] values, int currentCardIndex)                        |
    |--------------------------------------------------------------------------------|
    | returns boolean                                                                |
    |--------------------------------------------------------------------------------|
    | int[] values - The game value of each card                                     |
    |--------------------------------------------------------------------------------|
    | int currentCardIndex - Most recently drawn card                                |
    |--------------------------------------------------------------------------------|
    | Checks if the specified card is an ace.                                        |
    ================================================================================*/

    public static boolean isCardAnAce(int[] values, int currentCardIndex)
    {
        return (values[currentCardIndex] == ACE_VALUE);
    }

    /*===============================================================================|
    | boolean checkBlackjack(int sum, int numAces)                                   |
    |--------------------------------------------------------------------------------|
    | returns boolean                                                                |
    |--------------------------------------------------------------------------------|
    | int sum - Value of the selected hand                                           |
    |--------------------------------------------------------------------------------|
    | int numAces - The number of aces in the selected hand                          |
    |--------------------------------------------------------------------------------|
    | This method checks if either player or dealer has a Blackjack win.             |
    ================================================================================*/

    public static boolean checkBlackjack(int sum, int numAces)
    {
        if (sum == BLACKJACK_SUM)
        {
            return true;
        }
        else
        {
            for (int i = 1; i <= numAces; i++)
            {
                if (sum - (i * 10) == BLACKJACK_SUM)
                {
                    return true;
                }
            }
            return false;
        }
    }

    /*===============================================================================|
    | boolean checkOutOfRange(int sum, int numAces)                                  |
    |--------------------------------------------------------------------------------|
    | returns boolean                                                                |
    |--------------------------------------------------------------------------------|
    | int sum - Value of the selected hand                                           |
    |--------------------------------------------------------------------------------|
    | int numAces - The number of aces in the selected hand                          |
    |--------------------------------------------------------------------------------|
    | This method checks if either player or dealer is out of range.                 |
    ================================================================================*/

    public static boolean checkOutOfRange(int sum, int numAces)
    {
        sum = sum - (numAces * 10);

        if (sum > BLACKJACK_SUM)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    /*===============================================================================|
    | boolean checkWin(int sum1, int sum2)                                           |
    |--------------------------------------------------------------------------------|
    | returns boolean                                                                |
    |--------------------------------------------------------------------------------|
    | int sum1 - sum of player or dealer based on who is being checked               |
    |--------------------------------------------------------------------------------|
    | int sum2 - sum of player or dealer based on who is being compared against      |
    |--------------------------------------------------------------------------------|
    | This method compares the values of the dealer's/player's hand (or vice versa)  |
    | to determine a winner based on whose hand is greater.                          |
    ================================================================================*/

    public static boolean checkWin(int sum1, int sum2)
    {
        if ((sum1 <= BLACKJACK_SUM) && (sum1 > sum2))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
 
    public static void main(String[] args) 
    {
        // Variables, arrays and scanner
        int money = INITIAL_MONEY_AMOUNT;
        int gameNum = 1;
        int bet = 0;
        int currentCardIndex = 0;

        String gameStart;
        String nextMove;
        
        boolean startGame = false;
        boolean playGame = true;
        boolean stand = false;
        boolean gameOver = false;
        boolean hideSecondCard = true;

        int[] sums = new int[NUM_PLAYERS]; // Used to keep track of the value of each player's hand
        int[] numAces = new int[NUM_PLAYERS]; // Used to keep track of the number of ace cards in each player's hand
        int[] cardCounts = new int[NUM_PLAYERS]; // Used to keep track of the number of cards in each player's hand
        boolean[] blackjacks = new boolean[NUM_PLAYERS]; // Used to keep track which players have a blackjack
        boolean[] wins = new boolean[NUM_PLAYERS]; // Used to keepd track of which players have a winning hand
        boolean[] outOfRange = new boolean[NUM_PLAYERS]; // Used to keep trackof which players have a losing hand

        int[] deck = new int[52];
        String[] labels = new String[52];
        int[] values = new int[52];

        int[][] hands = new int[NUM_PLAYERS][MAX_HAND_SIZE];

        Scanner sc = new Scanner(System.in);

        // Game menu
        do
        {
            // User input to either start the game, go to the help menu, or exit the game
            System.out.printf("%nWelcome to Arda's Blackjack!%n%nPlease enter \"Start\" to start the game:%nEnter \"Exit\" if you would like to exit the game:%nEnter \"Help\" if you would like to see the help menu: ");
            gameStart = sc.next();

            // Switch and case that runs based on user input 
            switch (gameStart)
            {
                case "Start":
                case "start":
                case "START":
                {
                    startGame = true;

                    break;
                }
                case "Exit":
                case "exit":
                case "EXIT":
                {
                    System.out.println("\nWell, come back some other time when you want to play.");
                    return;
                }
                case "help":
                case "Help":
                case "HELP":
                {
                    printHelp();

                    break;
                }
                default:
                {
                    System.out.println("\nInvalid input! Please try again.");

                    break;
                }
            }
            
        } while (startGame == false);

        // Starting the game
        playGame = true;

        do
        {
            switch (gameStart)
            {
                case "Start":
                case "start":
                case "START":
                case "Yes":
                case "yes":
                case "YES":
                {
                    // Reseting values 
                    playGame = true;
                    bet = 0;
                    currentCardIndex = 0;
                    stand = false;
                    nextMove = "";
                    gameOver = false;
                    hideSecondCard = true;
                    hands = new int[NUM_PLAYERS][MAX_HAND_SIZE];
                    deck = new int[52];
                    labels = new String[52];
                    values = new int[52];

                    // Sets initial values for arrays 
                    for (int i = 0; i < NUM_PLAYERS; ++i)
                    {
                        sums[i] = 0;
                        numAces[i] = 0;
                        cardCounts[i] = 0;
                        blackjacks[i] = false;
                        wins[i] = false;
                        outOfRange[i] = false;
                    }
    
                    System.out.printf("%nStarting game number %d...%n", gameNum);         
                    System.out.printf("%nYou currently have $%d%n", money);

                    // User input for bet amount
                    // Do while that leaves when a valid bet is made
                    do
                    {
                        System.out.print("Enter the amount you would like to bet in dollars here: ");
                        try 
                        {
                            bet = sc.nextInt();
                        } 
                        catch (InputMismatchException e)
                        {
                            bet = 0;
                            sc.next(); // Clears the invalid scanner input 
                        } 
                        
                        // Checks if bet is valid 
                        if (bet > money || bet < 1)
                        {
                            System.out.printf("%nSorry your bet is invalid as you have made a bet of $%d which is more then your current amount of $%d!%n%n", bet, money);
                        }

                    } while (bet > money || bet < 1);

                    System.out.println("\nThe dealer shuffles the deck\n");

                    // Passes the three arrays to be initialized: deck, labels, and values 
                    // Returns a shuffled deck 
                    createDeck(deck, labels, values);

                    // Dealer gets the first and third cards from the top of the deck
                    // The player gets the second and the forth cards from the top of the deck

                    int topOfDeck = 0;

                    // Dealer pulls their first card from the top of the deck 
                    // The card at the top of the deck is now indexed, to be used to find its according label and mathematical value
                    currentCardIndex = deck[topOfDeck];
                    topOfDeck++; // Moves to the new top of the deck card for use in the next card draw later 

                    System.out.printf("Dealer's first card %s%n%n", labels[currentCardIndex]); // Prints the dealers first card 

                    sums[DEALER] = sums[DEALER] + values[currentCardIndex]; // Adds the card to the dealer's hand value 
                    hands[DEALER][cardCounts[DEALER]] = currentCardIndex; // 2D array that keeps track of, in this case, the dealer's cards 
                    cardCounts[DEALER]++; // Moves up to an empty index to be filled with a new card drawn by the dealer later on

                    // Checks if card is an ace
                    if (isCardAnAce(values, currentCardIndex))
                    {
                        numAces[DEALER]++;
                    }
                    
                    // Actions in line 341 - 352 repeat, but with the cards being indexed for the player instead of the dealer
                    currentCardIndex = deck[topOfDeck];
                    topOfDeck++;

                    // Sleep the program for 1 second  
                    delayPrint();

                    System.out.printf("Your first card %s%n%n", labels[currentCardIndex]);

                    sums[PLAYER] = sums[PLAYER] + values[currentCardIndex];
                    hands[PLAYER][cardCounts[PLAYER]] = currentCardIndex;
                    cardCounts[PLAYER]++;

                    if (isCardAnAce(values, currentCardIndex))
                    {
                        numAces[PLAYER]++;
                    }
                
                    // Dealer pulls his second card, which stays hidden until the player has completly finished their move later in the game
                    currentCardIndex = deck[topOfDeck];
                    topOfDeck++;

                    // Slow the output down by a second
                    delayPrint();

                    System.out.printf("Dealer's second card is hidden%n%n");

                    sums[DEALER] = sums[DEALER] + values[currentCardIndex];
                    hands[DEALER][cardCounts[DEALER]] = currentCardIndex;
                    cardCounts[DEALER]++;

                    if (isCardAnAce(values, currentCardIndex))
                    {
                        numAces[DEALER]++;
                    }

                    // Player pulls second card 
                    currentCardIndex = deck[topOfDeck];
                    topOfDeck++;
                    
                    delayPrint();

                    System.out.printf("Your second card %s%n%n", labels[currentCardIndex]);

                    sums[PLAYER] = sums[PLAYER] + values[currentCardIndex];
                    hands[PLAYER][cardCounts[PLAYER]] = currentCardIndex;
                    cardCounts[PLAYER]++;

                    if (isCardAnAce(values, currentCardIndex))
                    {
                        numAces[PLAYER]++;
                    }
                    delayPrint();

                    // Prints out a table with the dealers hand
                    System.out.println("\nDealer's current hand:");
                    printHand(hands[DEALER], cardCounts[DEALER], labels, hideSecondCard);

                    // Prints out table with the player's hand 
                    System.out.println("Your current hand:");
                    printHand(hands[PLAYER], cardCounts[PLAYER], labels, false);

                    // Player can get a blackjack with the first two cards
                    // Calls the method that checks if the player is out of range 
                    outOfRange[PLAYER]= checkOutOfRange(sums[PLAYER], numAces[PLAYER]);

                    // Calls the method that checks if player has Blackjack 
                    blackjacks[PLAYER] = checkBlackjack(sums[PLAYER], numAces[PLAYER]);

                    // Checks if any of the game ending conditions above have occured 
                    if (blackjacks[PLAYER])
                    {
                        wins[PLAYER] = true;
                    }
                    if (outOfRange[PLAYER])
                    {
                        wins[PLAYER] = false;
                    }

                    // gameOver would equal true if either of the game ending conditions were met
                    gameOver = (outOfRange[PLAYER] || blackjacks[PLAYER]);

                    // The only way for the game to have ended at this point is a play blackjack, if that condition is true the progeam will skip to the end of the code where it will
                    // Display a win message and ask if the user wants to play again
                    if (gameOver == true)
                    {
                        System.out.println("You got a blackjack at the start of the game.");
                    }
                    else        
                    {
                        // At this point both the dealer and the player has two cards on the table
                        do
                        {
                            // Player's turn to make a move, they can either stand, hit, or double down.
                            // Hit will draw a new card, double down will draw a new card and double their current bet, and stand will finish the players turn
                            System.out.print("\nIf you would like to stand type \"Stand\". If you would like to hit type \"Hit\". If you would like to double down type \"Double\": ");
                            nextMove = sc.next();

                            switch (nextMove)
                            {
                                case "Stand":
                                case "stand":
                                case "STAND":
                                {
                                    stand = true;
                                    break;
                                }

                                case "Double":
                                case "double":
                                case "DOUBLE":
                                {
                                    if ((bet * 2) > money)
                                    {
                                        // If double downing goes above the player's total money 
                                        System.out.println("You do not have enough money to double your bet.");
                                        break;
                                    }

                                    bet = bet * 2;
                                    System.out.printf("%n%nYou have doubled your bet to %d.%n", bet);

                                    // no break, program will go on to "hit" code since it would be the same action 
                                }

                                case "Hit":
                                case "hit":
                                case "HIT":
                                {
                                    // Get the card at the top of the deck
                                    currentCardIndex = deck[topOfDeck];

                                    // Move to the new top of the deck
                                    topOfDeck++;

                                    if (cardCounts[PLAYER] == 2)
                                    {
                                        System.out.printf("%nYour %drd card is %s%n", cardCounts[PLAYER] + 1, labels[currentCardIndex]);
                                    }
                                    else 
                                    {
                                        System.out.printf("%nYour %dth card is %s%n", cardCounts[PLAYER] + 1, labels[currentCardIndex]);
                                    }

                                    // Update player's hand and its value
                                    sums[PLAYER] = sums[PLAYER] + values[currentCardIndex];
                                    hands[PLAYER][cardCounts[PLAYER]] = currentCardIndex;
                                    cardCounts[PLAYER]++;

                                    // Checks for ace
                                    if (isCardAnAce(values, currentCardIndex))
                                    {
                                        numAces[PLAYER]++;
                                    }

                                    // Updates gameOver and blackjack for player
                                    outOfRange[PLAYER]= checkOutOfRange(sums[PLAYER], numAces[PLAYER]);
                                    blackjacks[PLAYER] = checkBlackjack(sums[PLAYER], numAces[PLAYER]);
                                    gameOver = (outOfRange[PLAYER] || blackjacks[PLAYER]);

                                    break;
                                }

                                default:
                                {
                                    System.out.printf("%nInvalid choice%n");

                                    break;
                                }                                
                            }

                            // Prints both hands for the current state of the game
                            System.out.println("\n\nDealer's current hand:");
                            printHand(hands[DEALER], cardCounts[DEALER], labels, hideSecondCard);

                            System.out.println("\nYour current hand:");
                            printHand(hands[PLAYER], cardCounts[PLAYER], labels, false);

                            // Checks if a win condition has been met, and if so skips to the end of the program 
                            if (gameOver == true)
                            {
                                break;
                            }

                        } while (stand == false);

                        // Dealer will now show his second card since the player turn is over
                        hideSecondCard = false;

                        System.out.println("\nDealer's current hand:");
                        printHand(hands[DEALER], cardCounts[DEALER], labels, hideSecondCard);

                        System.out.println("\nYour current hand:");
                        printHand(hands[PLAYER], cardCounts[PLAYER], labels, false);

                        // Runs the dealers turn only if a win or loss condition has not yet been met
                        if (gameOver == false)
                        {
                            // If the dealer has a hand worth less then the players he will draw one or more cards
                            if (sums[DEALER] < sums[PLAYER])
                            {
                                // Dealer's turn
                                // Sets ace's value to be most optimal to the dealer
                                for (int i = 0; i <= numAces[DEALER]; i++)
                                {
                                    int currentSum = sums[DEALER] - (i * 10);

                                    // Dealer continues to pull a card until his hand is worth more then 16
                                    while (currentSum <= DEALER_CARD_LIMIT)
                                    {
                                        System.out.printf("%nDealer takes a card %n");
                    
                                        // Takes card from top of deck and updates new top of deck
                                        currentCardIndex = deck[topOfDeck];
                                        topOfDeck++;
                    
                                        // Calculates the sum of the dealer's hand
                                        sums[DEALER] = sums[DEALER] + values[currentCardIndex];

                                        currentSum = sums[DEALER] - (i * 10);

                                        hands[DEALER][cardCounts[DEALER]] = currentCardIndex;
                                        cardCounts[DEALER]++;
                                        // Checks if an ace has been drawn 
                                        if (isCardAnAce(values, currentCardIndex))
                                        {
                                            numAces[DEALER]++;
                                        }

                                        // Prints both hands in their current state 
                                        System.out.println("\nDealer's current hand:");
                                        printHand(hands[DEALER], cardCounts[DEALER], labels, hideSecondCard);

                                        System.out.println("\nYour current hand:");
                                        printHand(hands[PLAYER], cardCounts[PLAYER], labels, false);
                                    }
                                    // Skips to the end of the program if Blackjack has been met as it gurantees a win or tie for the dealer 
                                    if (currentSum == BLACKJACK_SUM)
                                    {
                                        break;
                                    }
                                } 
                            }
                            // Checks win or loss conditions, weather dealer has blackjack or if hes out of range
                            outOfRange[DEALER]= checkOutOfRange(sums[DEALER], numAces[DEALER]);
                            blackjacks[DEALER] = checkBlackjack(sums[DEALER], numAces[DEALER]);
                            gameOver = (outOfRange[DEALER] || blackjacks[DEALER]);
                        }
                    }

                    // Runs if a gameOver condition has not yet been met
                    if (gameOver == false)
                    {
                        // Change ACE values if it is beneficial for the player 
                        if (sums[PLAYER] > BLACKJACK_SUM)
                        {
                            for (int i = 1; i <= numAces[PLAYER]; i++)
                            {
                                sums[PLAYER] = sums[PLAYER] - 10;

                                if (sums[PLAYER] <= BLACKJACK_SUM)
                                {
                                    break;
                                }
                            }
                        }

                        // Change ACE values if it is beneficial for the dealer
                        if (sums[DEALER] > BLACKJACK_SUM)
                        {
                            for (int i = 1; i <= numAces[DEALER]; i++)
                            {
                                sums[DEALER] = sums[DEALER] - 10;

                                if (sums[DEALER] <= BLACKJACK_SUM)
                                {
                                    break;
                                }
                            }
                        }

                        // If the game is not over by now this method will check for the final win condition where the winner is decided
                        // based on who has a higher value hand, player or dealer
                        wins[PLAYER] = checkWin(sums[PLAYER], sums[DEALER]);
                        wins[DEALER] = checkWin(sums[DEALER], sums[PLAYER]);
                        gameOver = true;
                    }

                    // Congradulations output and updated money if player wins by having a higher hand then the dealer
                    if (wins[PLAYER])
                    {
                        money = money + bet;
                        gameNum++;

                        System.out.printf("%nYOU WIN! Your hand of %d was greater then the dealers hand of %d.%n%n", sums[PLAYER], sums[DEALER]); 
                        System.out.printf("You have earned $%d. You now have a total of $%d%n", bet, money);
                    }
                    // Congradulations output and updated money if player wins by getting a blackjack
                    else if (blackjacks[PLAYER])
                    {
                        money = money + bet;
                        gameNum++;

                        System.out.printf("%nYOU WIN! You have been lucky and recived a Blackjack. You have earned $%d. You now have a total of $%d.%n%n", bet, money);
                    }
                    // Congradulations output and updated money if player wins by dealer going out of range (hand greater then 21)
                    else if (outOfRange[DEALER])
                    {
                        money = money + bet;
                        gameNum++;

                        System.out.printf("%nThe dealer has busted meaning you win! You have earned $%d. You now have a total of $%d.%n%n", bet, money); 
                    }
                    // Game result output and updated money if dealer wins by having a higher hand then the player
                    else if (wins[DEALER])
                    {
                        money = money - bet;
                        gameNum++;

                        System.out.printf("%nYou lose. The dealer's hand of %d is higher then your hand of %d. You have lost your bet and now have a total of $%d.%n%n", sums[DEALER], sums[PLAYER], money);
                    } 
                     // Game result output and updated money if dealer wins by getting blackjack
                    else if (blackjacks[DEALER])
                    {
                        money = money - bet;
                        gameNum++;

                        System.out.printf("%nYou lose. The dealer has gotten lucky and recived a Blackjack. You have lost your bet of $%d and now have a total of $%d.%n%n", bet, money);
                    }
                    // Game result output and updated money if dealer wins by player going out of range 
                    else if (outOfRange[PLAYER])
                    {
                        money = money - bet;
                        gameNum++;

                        System.out.printf("%nYou have busted meaning you lose your bet of $%d and now have a total of $%d.%n%n", bet, money);
                    }
                    // Game result output and updated money if player and dealer tie by having same value hands 
                    else
                    {
                        gameNum++;

                        System.out.printf("%nThe game was a tie. You keep your bet of $%d and still have a total of $%d.%n%n", bet, money);
                    }

                    if (money <= 0)
                    {
                        System.out.println("\nSorry, you have no money left, the game is over for you.");

                        gameStart = "no";
                    }
                    else 
                    {
                        // Ask the user if they would like to keep playing or exit the program
                        System.out.print("\nIf you would like to play again please enter \"Yes\", if you would like to exit type \"No\": ");

                        gameStart = sc.next();
                    }

                    break;
                }
                case "No":
                case "no":
                case "NO":
                {
                    playGame = false;

                    break;
                }
                default:
                {
                    // default which ask user if they would like to play again or exit the program, again, in case of a typo 
                    System.out.print("\nIf you would like to play again please enter \"Yes\", if you would like to exit type \"No\": ");
                    
                    gameStart = sc.next();

                    break;    
                }
            }
        } while (playGame);

        // Exit message 
        System.out.println("\nGoodbye, and thanks for playing Blackjack"); 
    }
}
