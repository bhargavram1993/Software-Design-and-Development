package mycharlie.plugin;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Prototype advisor which uses human input as advice.
 * @author Ron.Coleman
 */
public class SimpleAdvisor implements IAdvisor {
    /** Play table to map from human input to advice */
    protected HashMap<String, Play> plays = new HashMap<>();
    
    /** Reader to get player input */
    protected BufferedReader br;
    
    /**
     * Constructor
     */
    public SimpleAdvisor() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        
        this.plays.put("S", Play.STAY);
        this.plays.put("H", Play.HIT);
        this.plays.put("D", Play.DOUBLE_DOWN);
        this.plays.put("P", Play.SPLIT);
    }
    
    /**
     * Gives advice.
     * @param myHand Player's hand
     * @param upCard Dealer's up-card
     * @return 
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        while (true) {
             Card handCard1 = myHand.getCard(0);
              Card handCard2 = myHand.getCard(1);  
              int handValue = myHand.getValue();
              int dealerCard_Up = upCard.getRank();
              if((handCard1.isAce() && handCard2.isAce())) 
                  return Play.SPLIT;
              else if(((handCard1.getRank() == 8) && (handCard2.getRank() == 8)))
                  return Play.SPLIT;
              else if((handValue >= 17))
                  return Play.STAY;
              else if(handValue <=10)
                  return Play.HIT;
              else if(handValue == 11)
                  return Play.DOUBLE_DOWN;
              else if((handValue >=12 && handValue <=16 && ((dealerCard_Up + 10) <= 16) ))
                  return Play.STAY;
              else if((handValue >=12 && handValue <=16 && ((dealerCard_Up + 10) > 16) ) )
                  return Play.HIT;
              else
                  return Play.NONE;

        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleAdvisor sc = new SimpleAdvisor();
        
        System.out.println(sc.advise(null,null));
    }
}
