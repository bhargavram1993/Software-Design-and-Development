/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bs.section3;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.util.Play;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Sri
 */ 
public class Test01_A2_2 {
    
    public Test01_A2_2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of advise method, of class BasicAdvisor.
     */
    @Test
    public void test() {
        System.out.println("advise");
        Hid hid = new Hid(Seat.YOU);

        Hand myHand = new Hand(hid);
        Card card1 = new Card(7,Card.Suit.CLUBS);
        Card card2 = new Card(10,Card.Suit.HEARTS);
        myHand.hit(card1);
        myHand.hit(card2);
        
        Card upCard = new Card(2,Card.Suit.CLUBS);
       
       Advisor instance = new Advisor();
        
        Play result = instance.advise(myHand, upCard);
       
        assert(result.equals(Play.STAY));   
    }    
}
