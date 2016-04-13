/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.server;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GopiChand
 */
public class HueyStay implements IBot,Runnable{
protected final int THINK_TIME=5;
Seat mine;
Hand myHand;
Dealer dealer;
Random ran = new Random();
       protected void think() throws InterruptedException
        {
        int thinking = ran.nextInt(THINK_TIME)*1000;
        Thread.sleep(thinking);
        }
    @Override
    public void run() {
    try {
        think();
        dealer.stay(this,myHand.getHid() );
        
    }
    catch (InterruptedException ex) {
        Logger.getLogger(Huey.class.getName()).log(Level.SEVERE, null, ex);
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
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    @Override
    public void endGame(int shoeSize) {
        
    }

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        
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
