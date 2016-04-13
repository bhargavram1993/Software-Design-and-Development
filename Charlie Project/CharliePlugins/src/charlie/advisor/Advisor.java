/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author Sri Vivek
 */
public class Advisor implements IAdvisor {

    /**
     * Play table to map from human input to advice
     */
    /**
     * Reader to get player input
     */
    protected BufferedReader br;

    /**
     * Constructor
     */
    public Advisor() {
        fillHashMap();
    }

    protected HashMap<String, String> tableMap = new HashMap<>();

    /**
     * Gives advice.
     *
     * @param myHand Player's hand
     * @param upCard Dealer's up-card
     * @return
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        while (true) {
            try {
//        //read the players cards
                Card card1 = myHand.getCard(0);
                Card card2 = myHand.getCard(1);
//        Card card1 = new Card(4,Card.Suit.CLUBS);
//        Card card2 = new Card(3,Card.Suit.DIAMONDS);

                //check the value of first card for an Ace
                String card1Val;
                if (card1.isAce()) {
                    card1Val = "A";
                } else {
                    card1Val = Integer.toString(card1.value());
                }

                //check the value of second card for an Ace
                String card2Val;
                if (card2.isAce()) {
                    card2Val = "A";
                } else {
                    card2Val = Integer.toString(card2.value());
                }

                //check the value of dealers card for an Ace 
                String dCardVal;
                if (upCard.isAce()) {
                    dCardVal = "A";
                } else {
                    dCardVal = Integer.toString(upCard.value());
                }

                String myHandValue = Integer.toString(myHand.getValue());
                int handSize = myHand.size();

                Play newVal = null;
                int row;
                String num = "";
                //cards from "A,A" to "2,2" (17 to 25 in array)
                if (card1Val.equals(card2Val) && handSize == 2) {
                    if ("8".equals(card1Val)) {
                        card1Val = "A";
                        card2Val = "A";
                    }
                    num = tableMap.getOrDefault(card1Val + card2Val, null);
                } //cards from A10-A2//Part Two:cards from A10-A2
                else if ((card1.isAce() || card2.isAce()) && handSize == 2) {
                    String val = card1.isAce() ? card2Val : card1Val;
                    num = tableMap.getOrDefault("A" + val, null);
                } //cards from 5-17+//part Three:cards from 5-17+
                else {
                    if (Integer.parseInt(myHandValue) >= 17) {
                        myHandValue = "17";
                    }
                    num = tableMap.getOrDefault(myHandValue, null);
                }
                if (num == null) {
                    return Play.NONE;
                } else {
                    row = Integer.parseInt(num);
                }

                //Get the advise  value using dealer's card
                if ("A".equals(card1Val)) {
                    newVal = strategyTable[row][9];
                } else {
                    newVal = strategyTable[row][Integer.parseInt(dCardVal) - 2];
                }

                return newVal;
            } catch (Exception e) {
                System.out.println("Error in BasicStrategyAdvisor.java file in advice() " + e.getMessage());
                return Play.NONE;
            }

        }
    }

    private void fillHashMap() {
        tableMap.put("17", "0");
        tableMap.put("16", "1");
        tableMap.put("15", "2");
        tableMap.put("14", "3");
        tableMap.put("13", "4");
        tableMap.put("12", "5");
        tableMap.put("11", "6");
        tableMap.put("10", "7");
        tableMap.put("9", "8");
        tableMap.put("8", "9");
        tableMap.put("A8,10", "10");
        tableMap.put("A7", "11");
        tableMap.put("A6", "12");
        tableMap.put("A5", "13");
        tableMap.put("A4", "14");
        tableMap.put("A3", "15");
        tableMap.put("A2", "16");
        tableMap.put("AA", "17");
        tableMap.put("101O", "18");
        tableMap.put("99", "19");
        tableMap.put("77", "20");
        tableMap.put("66", "21");
        tableMap.put("55", "22");
        tableMap.put("44", "23");
        tableMap.put("33", "24");
        tableMap.put("22", "25");
    }
    /**
     * @param args the command line arguments
     */
    private final Play[][] strategyTable = {
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY},//17+--0
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//16--1
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//15--2
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//14--3
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//13--4
        {Play.HIT, Play.HIT, Play.STAY, Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//12--5
        {Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT},//11--6
        {Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT, Play.HIT},//10--7
        {Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//9--8
        {Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//5-8--9
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY},//A8-10--10
        {Play.STAY, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.STAY, Play.STAY, Play.HIT, Play.HIT, Play.HIT},//A7 --11
        {Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//A6--12
        {Play.HIT, Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//A5--13
        {Play.HIT, Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//A4--14
        {Play.HIT, Play.HIT, Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//A3--15
        {Play.HIT, Play.HIT, Play.HIT, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN,
            Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//A2--16
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT,
            Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT},//AA,88--17
        {Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY, Play.STAY},//10,10--18
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.STAY, Play.SPLIT, Play.SPLIT, Play.STAY, Play.STAY},//9,9--19
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//7,7--20
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//6,6--21
        {Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.DOUBLE_DOWN, Play.HIT, Play.HIT},//5,5--22
        {Play.HIT, Play.HIT, Play.HIT, Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//4,4--23
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//3,3--24
        {Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.SPLIT, Play.HIT, Play.HIT, Play.HIT, Play.HIT},//2,2--25
    };
}
