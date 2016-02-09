import java.io.PrintWriter;

/**
 * Created by Chris on 12/13/2015. This class is for a player's hand, which is also sort
 * analogous to a player. Each player has 7 cards in hand at all times, and can do different
 * things with them. Hand also keeps track of the score, and is responsible for determining
 * if there is a winner for the entire game.
 */
public class Hand {

    private Card[] playerHand = new Card[7];
    private int wins;
    private Deck deck;
    private String handName;

    /**
     * Creates a hand (or a player) with a name and which deck they'll draw from.
     * @param handName
     *      A string to represent the hand name
     * @param deck
     *      A deck object, which the hand will draw from
     */
    public Hand(String handName, Deck deck){
        playerHand = new Card[7];
        this.handName = handName;
        this.deck = deck;
        this.wins = 0;
        resetHand();
    }

    /**
     * Resets a player's hand so that every card slot is null
     */
    public void resetHand(){
        for(int i = 0; i < 7; i++){
            playerHand[i] = null;
        }
    }

    /**
     * Gets the string representation of the hand
     * @return
     *      string representation of the hand
     */
    public String getHandName(){
        return handName;
    }

    /**
     * Prints the hand out to the screen
     */
    public void printHand(){
        for(int i = 0; i < 7; i++) {
            System.out.println(playerHand[i].getString());
        }
    }

    /**
     * This hand has won a round, so increment the number of wins it has
     */
    public void handWins(){
        wins++;
    }

    /**
     * Returns the current number of wins this hand has
     * @return
     *      the current number of wins this hand has
     */
    public int getWins(){
        return wins;
    }

    /**
     * Adds 1 card at a time to the player's hand, only if there is a card left
     * in the deck.
     * @param deck
     *      the deck that is being dealt from
     */
    public void dealHand(Deck deck){
        for(int i = 0; i < 7; i++){
            if(!deck.isEmpty()) {
                playerHand[i] = deck.draw();
            }
        }
    }

    /**
     * Plays a card from the player's hand using cardPosition to determine
     * which card is being played
     * @param cardPosition
     *      the position of the card being played
     * @return
     *      the card that has been [played]
     */
    public Card playCard(int cardPosition){
        Card temp = playerHand[cardPosition];
        playerHand[cardPosition] = deck.draw();

        return temp;
    }
}
