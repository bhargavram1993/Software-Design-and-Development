
package charlie.bot.server;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.List;
import java.util.Random;
import mycharlie.plugin.CompleteAdvisor;


/**
 *
 * @author Bhargav Uppalapati/GopiChand
 */
public class Huey implements IBot,Runnable{
    protected Hand dealerHand;
    protected final int THINK_TIME=5;
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
Random ran = new Random();
       protected void think() throws InterruptedException
        {
        int thinking = ran.nextInt(THINK_TIME)*1000;
        Thread.sleep(thinking);
        }
    
     @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        Seat seat = Seat.NONE;
            for (int count = 0; count < hids.size(); count++) {
                if (hids.get(count).getSeat() == Seat.DEALER) {
                    seat = hids.get(count).getSeat();
                }
            }
            Hand hueyhand = new Hand(new Hid(seat));
            dealerHand = hueyhand;
    }
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if(card == null)
            return;
        if(hid.getSeat()==Seat.DEALER)
            dealerHand.hit(card);   
    }
 
    @Override
    public void run() {
        try {         
            
            Card upCard = dealerHand.getCard(0);
            CompleteAdvisor hueyAdvise=new CompleteAdvisor();
            Play hueyplay=hueyAdvise.getPlay(myHand, upCard);
            //invoking think from parent class
            think(); 
            //playing STAY
            if(hueyplay==Play.STAY){
                dealer.stay(this,myHand.getHid());
               
             }
            //playing HIT
           if(hueyplay==Play.HIT){
                dealer.hit(this,myHand.getHid());
                 Hid hid = myHand.getHid();
              if(hid.getSeat() != mine)
                    return;
              new Thread(this).start();
           }
           //playing Double Down
           if(hueyplay==Play.DOUBLE_DOWN)
           {
                dealer.doubleDown(this,myHand.getHid());
                 Hid hid = myHand.getHid();
              if(hid.getSeat() != mine)
                    return;
              new Thread(this).start();
           }
           //handling split
           if(hueyplay==Play.SPLIT)
           {
               int dealerHandVal = dealerHand.getValue();
                  //play stay if hand >=18
                  if(myHand.getValue() >= 18){
                     dealer.stay(this,myHand.getHid());
                    
                    }
                //play stay if dealerhand >=8
                if(dealerHandVal >= 8){
                   dealer.stay(this,myHand.getHid());
                   
                }
                //play hit if hand<=12
                if(myHand.getValue() <= 12){
                  dealer.hit(this,myHand.getHid());
                  Hid hid = myHand.getHid();
                  if(hid.getSeat() != mine)
                    return;
                  new Thread(this).start();
                }
                //in other cases play stay
              dealer.stay(this,myHand.getHid());
             
           }
        } 
        catch (InterruptedException ex) {
            
        }
   }

    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer= dealer;
    }

    @Override
    public void sit(Seat seat) {
        this.mine = seat;
         Hid hid = new Hid(seat);
         Hand hand = new Hand(hid); 
         this.myHand = hand;
    }

    @Override
    public void endGame(int shoeSize) {
        
    }

    @Override
    public void insure() {
       
    }

    @Override
    public void bust(Hid hid) {
        
    }

    @Override
    public void win(Hid hid) {
        
    }

    @Override
    public void blackjack(Hid hid) {
        
    }

    @Override
    public void charlie(Hid hid) {
        
    }

    @Override
    public void lose(Hid hid) {
        
    }

    @Override
    public void push(Hid hid) {
        
    }

    @Override
    public void shuffling() {
        
    }

    @Override
    public void play(Hid hid) {
         if(hid.getSeat() != mine)
            return;
        new Thread(this).start();
        
    }
   
}

    


