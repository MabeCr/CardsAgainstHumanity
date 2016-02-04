/**
 * Created by Chris on 2/4/2016.
 *
 * This class is a revision of the Server class. Since we are no longer
 * using sockets in our project, and instead writing to a text file,
 * this program needs to be rewritten. (I failed to take into account
 * the fact the Jackson could do a lot more with the PHP than I
 * imagined. Such a damn shame.)
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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

            //Set up the PrintWriter that will be writing to the control text file
            PrintWriter toActions = new PrintWriter(actions);
            PrintWriter toData = new PrintWriter(data);
        } catch (IOException e){
            System.err.println("One or more files were not found. Please try again.");
        }


    }
}
