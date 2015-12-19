/**
 * Created by Chris Mabe on 12/13/2015.
 * This program will run a game of Cards Against Humanity with custom classes.
 * The players will be given 7 cards, and one player will be the judge.
 * The other players will play the white card they feel is the best, and
 * the judge will pick the best card. That person will receive a point.
 * This may be copyright infringement, but it's not going to be sold
 * or marketed in any way. This is just to see if we can figure it out.
 *
 * This is a test comment to see if the VCS works on this project.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static final boolean white = true;
    public static final boolean black = false;

    public static int judgeId;
    public static int playerNumber;

    public static void main(String[] args) {
        judgeId = 0;
        int portNumber = Integer.parseInt(args[0]);

        try(ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocketOne = serverSocket.accept();
            PrintWriter outOne = new PrintWriter(clientSocketOne.getOutputStream(), true);

            Socket clientSocketTwo = serverSocket.accept();
            PrintWriter outTwo = new PrintWriter(clientSocketTwo.getOutputStream(), true);

            Socket clientSocketThree = serverSocket.accept();
            PrintWriter outThree = new PrintWriter(clientSocketThree.getOutputStream(), true);

            BufferedReader inOne = new BufferedReader(new InputStreamReader(clientSocketOne.getInputStream()));
            BufferedReader inTwo = new BufferedReader(new InputStreamReader(clientSocketTwo.getInputStream()));
            BufferedReader inThree = new BufferedReader(new InputStreamReader(clientSocketThree.getInputStream()))
        ) {

            PrintWriter[] writers = new PrintWriter[3];
            writers[0] = outOne;
            writers[1] = outTwo;
            writers[2] = outThree;

            BufferedReader[] readers = new BufferedReader[3];
            readers[0] = inOne;
            readers[1] = inTwo;
            readers[2] = inThree;


            //Create basic white and black card decks
            Deck whiteDeck = new Deck(white);
            Deck blackDeck = new Deck(black);

            Scanner scanner = new Scanner(System.in);

            //Create hand to store 7 cards in
            Hand hand1 = new Hand("one", whiteDeck);
            Hand hand2 = new Hand("two", whiteDeck);
            Hand hand3 = new Hand("three", whiteDeck);

            Hand[] players = new Hand[3];
            //Setting this manually for testing
            playerNumber = 3;
            players[0] = hand1;
            players[1] = hand2;
            players[2] = hand3;

            //Get the custom card pack name from the user
            String packName = getPackName(inOne, writers);
            System.out.println("");

            //Add the custom white and black card to the existing decks
            addPacksToDeck(packName, whiteDeck, blackDeck);

            //Shuffle both decks to mix the cards up
            whiteDeck.shuffle();
            blackDeck.shuffle();

            //Deal 7 cards to the hands and then print them out to the screen
            for (int i = 0; i < players.length; i++) {
                System.out.println("Player " + players[i].getHandName());
                players[i].dealHand(whiteDeck);
                players[i].printHand(writers[i]);
                System.out.println("");
            }

            do {
                //Draw one black card that will be the for the first round
                Card currentBlackCard = blackDeck.draw();
                System.out.println("");
                System.out.println(currentBlackCard.getString());
                System.out.println("");

                //Is only 3, because one person is a judge
                Card[] choices = getCardChoices(playerNumber, judgeId, players, writers, readers);

                for (Card c : choices) {
                    if (c != null && c.getString() != null)
                        System.out.println(c.getString());
                }

                int winner = chooseWinner(judgeId, players, writers, readers);
                System.out.println("The winner is: " + winner);
                System.out.println("");
                players[winner].handWins();

                for (Hand h: players) {
                    System.out.println("Player " + h.getHandName() + " has " + h.getWins() + " wins.");
                    h.printHandtoConsole();
                    System.out.println("");
                }

                judgeId = (judgeId + 1) % playerNumber;
            } while (!playerWins(players));

            scanner.close();
            inOne.close();
            inTwo.close();
            clientSocketOne.close();
            serverSocket.close();

        } catch (IOException e){
            System.err.println("Exception caught when listening on port " + portNumber +
                " or listening for a connection");
        }
    }

    public static String getPackName(BufferedReader in, PrintWriter[] writer) {
        String answer = null;
        writer[0].println("Please enter a pack name. If you don't want to use one, press enter: ");
        try{
            while (true) {
                writer[0].println("enter text");
                if ((answer = in.readLine()) != null) {
                    System.out.println("The judge chose card " + answer);
                    break;
                }
            }
        } catch (IOException e){
            System.out.println("Card pack not recognized");
        }
        return answer;
    }

    public static void addPacksToDeck(String packName, Deck whiteDeck, Deck blackDeck) {
        //If the user enters a pack name
        if (!packName.equals("")) {
            //Set file to point to custom pack white card
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
            // This is set so I can run the code from cmd to test whether or not the sockets are working
            // To run in the IDE, set the commented out file to be the used one
            //File packLoadWhite = new File("src\\Cards\\" + packName + "WhiteCards.txt");
            File packLoadWhite = new File("Cards\\" + packName + "WhiteCards.txt");
            //Add the custom pack white cards to the original deck
            whiteDeck.addCards(packLoadWhite, white);
            //Set file to point to custom pack black card
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
            // This is set so I can run the code from cmd to test whether or not the sockets are working
            // To run in the IDE, set the commented out file to be the used one
            //File packLoadBlack = new File("src\\Cards\\" + packName + "BlackCards.txt");
            File packLoadBlack = new File("Cards\\" + packName + "BlackCards.txt");
            blackDeck.addCards(packLoadBlack, black);
        }
    }

    public static Card[] getCardChoices(int playerNumber, int judgeId, Hand[] players, PrintWriter[] writers, BufferedReader[] readers){
        Card[] choices = new Card[playerNumber];
        String userInput;
        try {
            for (int i = 0; i < playerNumber; i++) {
                if (i != judgeId) {
                    writers[i].println("Please make your card choice in the form of 1 - 7");
                    writers[i].println("enter text");
                    userInput = readers[i].readLine();
                    if (i > judgeId) {
                        writers[i].println("enter text");
                        if (userInput != null) {
                            choices[i - 1] = players[i].playCard(Integer.parseInt(userInput) - 1);
                        }
                    }
                    else if(userInput != null)
                        choices[i] = players[i].playCard(Integer.parseInt(userInput) - 1);
                }
            }
        } catch (IOException e){
            System.out.println("IO Exception");
        }

        return choices;
    }

    public static int chooseWinner(int judgeId, Hand[] players, PrintWriter[] writers, BufferedReader[] readers) {
        System.out.println("Player " + players[judgeId].getHandName() + " is the judge.");
        writers[judgeId].println("You are the judge.");
        writers[judgeId].println("Please enter your winner by typing 1 or 2: ");
        String returnString;
        int returnValue;

        try{
            while (true) {
                System.out.println("");
                System.out.println("Waiting for answer from other program...");
                writers[judgeId].println("enter text");
                if ((returnString = readers[judgeId].readLine()) != null) {
                    System.out.println("The judge chose card " + returnString);
                    returnValue = Integer.parseInt(returnString);
                    break;
                }
            }
        } catch (IOException e){
            System.out.println("Could not receive your input");
            returnValue = 0;
        }
        if (judgeId == 1) {
            if(returnValue == 1)
                returnValue = 0;
        }
        else if(judgeId == 2){
            returnValue--;
        }

        return returnValue;
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
