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

        //Set the files that will be used to control the game
        try {
            File actions = new File("TextFiles\\actions.txt");
            File data = new File("TextFiles\\game-data.txt");

            //Set up the PrintWriter that will be writing to the data text file
            PrintWriter toData = new PrintWriter(data);

            //Set up the Reader that will be reading in from the actions text file
            BufferedReader fromActions = new BufferedReader(new FileReader(actions));

        } catch (IOException e){
            System.err.println("One or more files were not found. Please try again.");
        }

        //Create the basic white and black decks with no expansions added
        Deck whiteDeck = new Deck(white);
        Deck blackDeck = new Deck(black);

        /**
         *PSEUDO - Create a hand for each player
         *Search through the data text file for all the lines with a player name
         *up until there is a period, incrementing on each. Set playerNumber to that
         *number.
         *
         * For now, this is going to be manually set to match the text file
         */


    }
}
