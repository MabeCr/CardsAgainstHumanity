/**
 * Created by Chris on 2/4/2016.
 *
 * This class is a revision of the Server class. Since we are no longer
 * using sockets in our project, and instead writing to a text file,
 * this program needs to be rewritten. (I failed to take into account
 * the fact the Jackson could do a lot more with the PHP than I
 * imagined. Such a damn shame.)
 */

import java.io.*;

public class NewServer {

    public static final boolean white = true;
    public static final boolean black = false;

    public static int judgeId;
    public static int playerNumber;

    public static void main(String[] args){
        //Set the judge ID to the first player
        judgeId = 0;

        //Create the basic white and black decks with no expansions added
        Deck whiteDeck = new Deck(white);
        Deck blackDeck = new Deck(black);

        //Set the files that will be used to control the game
        File actions = new File("TextFiles\\actions.txt");
        File data = new File("TextFiles\\game-data.txt");

        try {
            //Set up the PrintWriter that will be writing to the data text file
            //PrintWriter toData = new PrintWriter(new BufferedWriter(new FileWriter(data)));

            //Set up the Reader that will be reading in from the actions text file
            BufferedReader fromActions = new BufferedReader(new FileReader(actions));
            BufferedReader fromData = new BufferedReader(new FileReader(data));

            Hand[] players = new Hand[3];
            createHandArray(fromActions, players, whiteDeck);
            playerNumber = players.length;

            for (int i = 0; i < playerNumber; i++) {
                System.out.println("Player " + players[i].getHandName());
                players[i].dealHand(whiteDeck);
                players[i].printHand();
                System.out.println("__________________________________________________");
                System.out.println("");
            }

        } catch (IOException e){
            System.err.println("One or more files were not found. Please try again.");
        }



    }

    public static void createHandArray(BufferedReader in, Hand[] hands, Deck deck){
        String text;
        int nameIndex;
        int arrayIndex = 0;

        String name;

        try{
            while((text = in.readLine()) != null) {
                if(text.length() == 0){

                }
                else if(text.substring(0, 2).equals("co")) {
                    nameIndex = text.indexOf(" ", 10);
                    name = text.substring(10, nameIndex);

                    Hand hand = new Hand(name, deck);
                    hands[arrayIndex] = hand;
                    arrayIndex++;
                }
            }
        } catch (IOException e){
            System.err.println("Something in your IO got fucked, bruh");
        }
    }
}
