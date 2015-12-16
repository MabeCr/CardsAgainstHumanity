/**
 * Created by Chris Mabe on 12/13/2015.
 * This program will run a game of Cards Against Humanity with custom classes.
 * The players will be given 7 cards, and one player will be the judge.
 * The other players will play the white card they feel is the best, and
 * the judge will pick the best card. That person will receive a point.
 * This may be copyright infringement, but it's not going to be sold
 * or marketed in any way. This is just to see if we can figure it out.
 */

import java.io.File;
import java.util.Scanner;

public class Main {

    public static final boolean white = true;
    public static final boolean black = false;

    public static int judgeId;
    public static int playerNumber;

    public static void main(String[] args) {
        //Create basic white and black card decks
        Deck whiteDeck = new Deck(white);
        Deck blackDeck = new Deck(black);

        Scanner scanner = new Scanner(System.in);

        //Create hand to store 7 cards in
        Hand hand1 = new Hand("one", whiteDeck);
        Hand hand2 = new Hand("two", whiteDeck);
        Hand hand3 = new Hand("three", whiteDeck);
        Hand hand4 = new Hand("four", whiteDeck);

        Hand[] players = new Hand[4];
        //Setting this manually for testing
        playerNumber = 4;
        players[0] = hand1;
        players[1] = hand2;
        players[2] = hand3;
        players[3] = hand4;

        //Get the custom card pack name from the user
        String packName = getPackName(scanner);
        System.out.println("");

        //Add the custom white and black card to the existing decks
        addPacksToDeck(packName, whiteDeck, blackDeck);

        //Shuffle both decks to mix the cards up
        whiteDeck.shuffle();
        blackDeck.shuffle();

        //Deal 7 cards to the hands and then print them out to the screen
        for (Hand h : players) {
            System.out.println("Player " + h.getHandName());
            h.dealHand(whiteDeck);
            h.printHand();
            System.out.println("");
        }

        do {
            //Draw one black card that will be the for the first round
            Card currentBlackCard = blackDeck.draw();
            System.out.println("");
            System.out.println(currentBlackCard.getString());
            System.out.println("");

            //Is only 3, because one person is a judge
            Card[] choices = new Card[3];

            for (int i = 0; i < playerNumber; i++) {
                if(i != judgeId) {
                    if(i > judgeId) {
                        choices[i - 1] = players[i].playCard(0);
                    }
                    else
                        choices[i] = players[i].playCard(0);
                }
            }

            for (Card c : choices) {
                if(c.getString() != null)
                    System.out.println(c.getString());
            }

            int winner = chooseWinner(judgeId, players, scanner);
            System.out.println("The winner is: " + winner);
            System.out.println("");
            players[winner].handWins();

            for (Hand h : players) {
                System.out.println("Player " + h.getHandName() + " has " + h.getWins() + " wins.");
                h.printHand();
                System.out.println("");
            }

            judgeId = (judgeId + 1) % playerNumber;
        } while (!playerWins(players));

        scanner.close();
    }

    public static String getPackName(Scanner scan) {
        System.out.print("Please enter a pack name. If you don't want to use one, press enter: ");
        return scan.nextLine();
    }

    public static void addPacksToDeck(String packName, Deck whiteDeck, Deck blackDeck) {
        //If the user enters a pack name
        if (!packName.equals("")) {
            //Set file to point to custom pack white card
            File packLoadWhite = new File("src\\Cards\\" + packName + "WhiteCards.txt");
            //Add the custom pack white cards to the original deck
            whiteDeck.addCards(packLoadWhite, white);
            //Set file to point to c
            File packLoadBlack = new File("src\\Cards\\" + packName + "BlackCards.txt");
            blackDeck.addCards(packLoadBlack, black);
        }
    }

    public static int chooseWinner(int judgeId, Hand[] players, Scanner scan) {
        System.out.println("Player " + players[judgeId].getHandName() + " is the judge.");
        System.out.print("Please enter your winner by typing 1, 2, or 3: ");
        return Integer.parseInt(scan.nextLine()) - 1;
    }

    public static boolean playerWins(Hand[] players) {
        boolean hasWon = false;
        for (Hand h : players) {
            if (h.getWins() >= 2) //2 will be changed to the number of cards needed to win
                hasWon = true;
        }

        return hasWon;
    }
}