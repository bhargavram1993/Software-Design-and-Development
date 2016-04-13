package charlie.sidebet.view;

/**
 * file: SideBetView.java author: GopiChand Ponnam, Bhargav Uppalapati course:
 * Software Design and Development assignment: Assignment 3 - Side bets due
 * date: Apr 8, 2016
 *
 * This file contains the implementation of SideBet View.
 */
import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AHand.Outcome;
import charlie.view.AMoneyManager;
import charlie.view.sprite.Chip;
import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SideBetView This class implements the side bet view
 */
public class SideBetView implements ISideBetView {

    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    protected int coinX = 0;
    protected int coinY = 0;
    protected List<Chip> coins = new ArrayList<>();
    protected Random ran = new Random();
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    protected Outcome outcome = Outcome.None; 
    String text="";
    Color mybackground=Color.WHITE;
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);
    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;

    /**
     * SideBetView.
     */
    public SideBetView() {
        LOG.info("side bet view constructed");
    }

    /**
     * Sets the money manager.
     *
     * @param moneyManager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }

    /**
     * Registers a click for the side bet.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;
        // Test if any chip button has been pressed.
        for (ChipButton button : buttons) {
            if (button.isPressed(x, y)) {
                amt += button.getAmt();
                int chipSize = coins.size();
                coinX = X + chipSize * (10) + ran.nextInt(5);
                coinY = Y + ran.nextInt(5) + 5;
                Chip chip = new Chip(button.getImage(), coinX, coinY, amt);
                coins.add(chip);
                SoundFactory.play(Effect.CHIPS_IN);
                LOG.info("A. side bet amount " + button.getAmt() + " updated new amt = " + amt);
            }
        }
        if(x>X-20&&x<X+20&&y>Y-20&&y<Y+20)
        {
            if (oldAmt == amt) {
            amt = 0;
            coins.clear();
            SoundFactory.play(Effect.CHIPS_OUT);
            outcome=Outcome.None;
            LOG.info("B. side bet amount cleared");
        }
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for
     * the hand.
     *
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        if (bet == 0) {
            return;
        }
        if(bet>0)
            outcome=Outcome.Win;
        if(bet<0)
            outcome=Outcome.Lose;
        
        LOG.info("side bet outcome = " + bet);
        // Update the bankroll
        moneyManager.increase(bet);
        LOG.info("new bankroll = " + moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
    }

    /**
     * Gets the side bet amount.
     *
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     *
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        g.setStroke(dashed);
        g.drawOval(X - DIAMETER / 2, Y - DIAMETER / 2, DIAMETER, DIAMETER);
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int width=fm.stringWidth(""+amt);
        g.drawString(""+amt, (X - width/2), Y + 5);
        g.setColor(Color.ORANGE);
        g.setFont(font);
        g.drawString("SUPER 7 PAYS 3:1", X + 30, Y - 55);
        g.drawString("ROYAL MATCH PAYS 25:1", X + 30, Y - 15);
        g.drawString("EXACTLY 13 PAYS 1:1", X + 30, Y - 35);
        for (int i = 0; i < coins.size(); i++) {
            Chip chip = coins.get(i);
            chip.render(g);
        }
        if(outcome!=Outcome.None){
        text = outcome.toString()+"!";
        renderState(g,text);
        }
        
    }
    protected void renderState(Graphics2D g, String text)
    {

        FontMetrics fm = g.getFontMetrics();
        int width=fm.stringWidth(text);
        if("Win!".equals(text))
        {
          g.setColor(Color.GREEN);
          g.fillRoundRect(X+15, Y-17, width+10, 22, 5 , 5);
          g.setColor(Color.BLACK);
          g.drawString(text, X+20, Y);
        } 
        if("Lose!".equals(text))
        {
          g.setColor(Color.RED);  
          g.fillRoundRect(X+15, Y-17, width+10, 22, 5 , 5);
          g.setColor(Color.WHITE);
          g.drawString(text, X+20, Y);
        }
        
        
       
    }
}
