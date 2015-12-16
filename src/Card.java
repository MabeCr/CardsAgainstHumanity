/**
 * Created by Chris Mabe on 12/13/2015. This is a card class that is a card with a
 * text string associated with it. It also has a color in the form of a boolean
 * "isWhite", which is exactly what you think it would be.
 */
public class Card {
    private String cardString;
    private final boolean isWhite;

    /**
     * Constructs a card with a specified string, if it has been used, and if it is white
     *
     * @param cardString
     *          the string that is associated with the card
     * @param isWhite
     *          determines if the card is a white card or not. If false, the card is a black card
     */
    public Card(String cardString, boolean isWhite){
        if(cardString == null || cardString.equals("")){
            throw new IllegalArgumentException("Card string is not legal");
        }

        this.cardString = cardString;
        this.isWhite = isWhite;
    }

    /**
     * Gets this card's string
     *
     * @return the string associated with this card
     */
    public String getString(){
        if(this.cardString == null)
            return "No string on the card";
        return cardString;
    }

    /**
     * Gets the color of the card
     *
     * @return the color of the card (white = true, black = false)
     */
    public boolean getColor(){
        return isWhite;
    }
}
