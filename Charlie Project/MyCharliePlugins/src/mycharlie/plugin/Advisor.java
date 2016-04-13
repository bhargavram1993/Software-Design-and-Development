package mycharlie.plugin;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
public class Advisor implements IAdvisor {
     
    CompleteAdvisor ca = new CompleteAdvisor();
    
    @Override
    public Play advise(Hand myHand, Card upCard) {      
        return ca.getPlay(myHand, upCard);
    }  
}
