/**
 * Created by Chris on 12/13/2015. This class is for a deck, which consists of a
 * bunch of card objects. They come in either white or black varieties, and have
 * assorted methods associate them that let you manipulate and return deck
 * properties.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;
    private boolean isWhite;

    /**
     * Creates a deck filled with either black or white cards
     * @param isWhite
     *      true if the deck is white, false if it is black
     */
    public Deck(boolean isWhite){
        cards = new ArrayList<>();
        this.isWhite = isWhite;
        setDefaultFiles();
    }


    /**
     * Shuffles the cards that are still in the deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Returns the next card in the deck, or null if there are no cards remaining.
     * Every time this method is called, the deck has one less card in it.
     * @return the next card in the deck
     */
    public Card draw(){
        return cards.size() > 0 ? cards.remove(0) : null;
    }

    /**
     * Gets the number of cards left in the deck
     * @return the number of cards left in the deck
     */
    public int size(){
        return cards.size();
    }

    /**
     * Returns whether or not the deck is empty
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * Sets the filename that will be called in add Cards
     */
    public void setDefaultFiles(){
        cards.clear();
        if(isWhite) {
            File whiteCards = new File("src\\Cards\\originalWhiteCards.txt");
            addCards(whiteCards, true);
        } else {
            File blackCards = new File("src\\Cards\\originalBlackCards.txt");
            addCards(blackCards, false);
        }
    }

    /**
     * Used to create a new deck of a certain card color. If isWhite is true,
     * a deck of white cards will be created with their beenUsed values set to false.
     * @param file
     *      the filename that card strings are being pulled from
     * @param isWhite
     *      whether or not the cards are white cards
     */
    public void addCards(File file, boolean isWhite){
        String cardText;
        try {
            FileReader textIn = new FileReader(file);
            BufferedReader buffer = new BufferedReader(textIn);
            while((cardText = buffer.readLine()) != null) {
                cards.add(new Card(cardText, isWhite));
            }
            buffer.close();
            textIn.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File was not found. Deck could not be made.");
            System.exit(22);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
