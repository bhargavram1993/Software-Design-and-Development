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
public class HumanAdvisor implements IAdvisor {
    /** Play table to map from human input to advice */
    protected HashMap<String, Play> plays = new HashMap<>();
    
    /** Reader to get player input */
    protected BufferedReader br;
    
    /**
     * Constructor
     */
    public HumanAdvisor() {
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
            try {
                System.out.print("Advice [S, H, D, or P]? ");
                String input = br.readLine();

                if (input == null || input.length() == 0) {
                    return Play.NONE;
                }

                Play play = plays.get(input.trim().toUpperCase());
                if (play == null) {
                    continue;
                }

                return play;
            } catch (IOException e) {
                e.printStackTrace();
                return Play.NONE;
            }

        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HumanAdvisor sc = new HumanAdvisor();
        
        System.out.println(sc.advise(null,null));
    }
}
