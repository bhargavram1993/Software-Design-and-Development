package mycharlie.plugin;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

public class CompleteAdvisor 
{
    public final static Play H = Play.HIT;
    public final static Play S = Play.STAY;
    public final static Play D = Play.DOUBLE_DOWN;
    public final static Play P = Play.SPLIT;
    int across=0,straight=0;
    Play suggestion=Play.NONE;
    public Play getPlay(Hand myHand, Card upCard) 
    {
        if(myHand.isPair())
            return rulePairs(myHand,upCard);
        
        else if(myHand.getCard(0).isAce() || myHand.getCard(1).isAce())
               return ruleAceandOther(myHand,upCard); 
        
        else if (myHand.getValue() >= 5 && myHand.getValue() <= 11)
            return rule11andLess(myHand, upCard);
        
        return rule12andMore(myHand,upCard);
    }
    Play rulePairs(Hand myHand, Card upCard) 
    {
        Play[] ruleAA = {P,P,P,P,P,P,P,P,P,P}; 
        Play[] rule1010 = {S,S,S,S,S,S,S,S,S,S};        
        Play[] rule99 = {P,P,P,P,P,S,P,P,S,S};
        Play[] rule88 = {P,P,P,P,P,P,P,P,P,P};        
        Play[] rule77 = {P,P,P,P,P,P,H,H,H,H};         
        Play[] rule66 = {P,P,P,P,P,H,H,H,H,H};        
        Play[] rule55 = {D,D,D,D,D,D,D,D,H,H};  
        Play[] rule44 = {H,H,D,D,D,H,H,H,H,H};   
        Play[] rule33 = {P,P,P,P,P,P,H,H,H,H};  
        Play[] rule22 = {P,P,P,P,P,P,H,H,H,H};
        
        Play[][] completerules ={ruleAA,rule1010,rule99,rule88,rule77,rule66,rule55,rule44,rule33,rule22};
     
        Card handcard = myHand.getCard(0);
        if(handcard.isAce())
            across=0;
        else
            across=(11-handcard.value());
        if(upCard.isAce())
            straight=(rule22.length-1);
        else
            straight=(upCard.value()-2);
        suggestion = completerules[across][straight];
        
        return suggestion;
    }
    Play ruleAceandOther(Hand myHand, Card upCard) 
    {
        Play[] ruleA10 = {S,S,S,S,S,S,S,S,S,S};
        Play[] ruleA9 = {S,S,S,S,S,S,S,S,S,S};
        Play[] ruleA8 = {S,S,S,S,S,S,S,S,S,S};        
        Play[] ruleA7 = {S,D,D,D,D,S,S,H,H,H};         
        Play[] ruleA6 = {H,D,D,D,D,H,H,H,H,H};        
        Play[] ruleA5 = {H,H,D,D,D,H,H,H,H,H};  
        Play[] ruleA4 = {H,H,D,D,D,H,H,H,H,H};   
        Play[] ruleA3 = {H,H,H,D,D,H,H,H,H,H};  
        Play[] ruleA2 = {H,H,H,D,D,H,H,H,H,H};
        
        Play[][] completerules ={ruleA10,ruleA9,ruleA8,ruleA7,ruleA6,ruleA5,ruleA4,ruleA3,ruleA2};
  
        Card handcard1 = myHand.getCard(0);
        Card handcard2 = myHand.getCard(1);
        if(!handcard1.isAce())
            across=handcard1.value();
        else
            across=handcard2.value();
        across=(10-across); 
        
       if(upCard.isAce())
            straight=(ruleA2.length-1);
        else
            straight=(upCard.value()-2);
        suggestion = completerules[across][straight];
        
        return suggestion;
    }
    protected Play rule11andLess(Hand myHand, Card upCard) 
    {
        Play[] rule11 = {D,D,D,D,D,D,D,D,D,H};
        Play[] rule10 = {D,D,D,D,D,D,D,D,H,H};
        Play[] rule9 =  {H,D,D,D,D,H,H,H,H,H};
        Play[] rule8 =  {H,H,H,H,H,H,H,H,H,H};        
        Play[] rule7 =  {H,H,H,H,H,H,H,H,H,H};         
        Play[] rule6 =  {H,H,H,H,H,H,H,H,H,H};        
        Play[] rule5 =  {H,H,H,H,H,H,H,H,H,H};  
        
        Play[][] completerules ={rule11,rule10,rule9,rule8,rule7,rule6,rule5};
        across = (11-myHand.getValue());
        if(upCard.isAce())
            straight=(rule5.length-1);
        else
            straight=(upCard.value()-2);
        suggestion = completerules[across][straight];
        
        return suggestion;
    }
    Play rule12andMore(Hand myHand, Card upCard) 
    {
        Play[] rule17above = {S,S,S,S,S,S,S,S,S,S};
        Play[] rule16 = {S,S,S,S,S,H,H,H,H,H};
        Play[] rule15 = {S,S,S,S,S,H,H,H,H,H};
        Play[] rule14 = {S,S,S,S,S,H,H,H,H,H};
        Play[] rule13 = {S,S,S,S,S,H,H,H,H,H};        
        Play[] rule12 = {H,H,S,S,S,H,H,H,H,H};  
        
        Play[][] completerules ={rule17above,rule16,rule15,rule14,rule13,rule12};
        
        if(myHand.getValue() < 17)
            across=(17 - myHand.getValue()); 
        if(upCard.isAce())
            straight=(rule12.length-1);
        else
            straight=(upCard.value()-2);
        suggestion = completerules[across][straight];
        
        return suggestion;
    }   
}
