package charlie.sidebet.rule;
/**
 * file: SideBetRule.java
 * author: GopiChand Ponnam, Bhargav Uppalapati
 * course: Software Design and Development
 * assignment: Assignment 3 - Side bets
 * due date: Apr 8, 2016
 * 
 * This file contains the implementation of SideBet rules.
 */
import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SideBetRule
 * 
 * This class implements the side bet rule for Super 7.
 */
public class SideBetRule implements ISideBetRule {

    private final Logger LOG = LoggerFactory.getLogger(SideBetRule.class);
    private final Double PAYOFF_SUPER7 = 3.0;
    private final Double PAYOFF_ROYALMATCH = 25.0;
    private final Double PAYOFF_EASYMATCH = 2.50;
    private final Double PAYOFF_EXACT13 = 1.0;

    /**
     * apply
     *
     * Apply rule to the hand and return the payout if the rule matches and the
     * negative bet if the rule does not match.
     *
     * @param hand Hand to analyze.
     * @returns bet amount.
     */
    @Override
    public double apply(Hand hand) {
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = " + bet);
        if (bet == 0) {
            return 0.0;
        }
        LOG.info("side bet rule applying hand = " + hand);
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        if (card1.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            return bet * PAYOFF_SUPER7;
        }
        if (card1.getRank() + card2.getRank() == 13) {
            LOG.info("side bet EXACT 13 matches");
            return bet * PAYOFF_EXACT13;
        }
        if (card1.getSuit() == card2.getSuit()) {
            if (card1.getRank() == Card.KING && card2.getRank() == Card.QUEEN || card2.getRank() == Card.KING && card1.getRank() == Card.QUEEN) {
                LOG.info("side bet ROYAL MATCH");
                return bet * PAYOFF_ROYALMATCH;
            } else {
                LOG.info("side bet EASY MATCH");
                return bet * PAYOFF_EASYMATCH;
            }
        }
        LOG.info("side bet rule no match");
        return -bet;
    }
}
