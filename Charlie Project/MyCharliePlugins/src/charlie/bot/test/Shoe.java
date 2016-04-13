
package charlie.bot.test;
import java.util.Random;
public class Shoe extends charlie.card.Shoe {
    @Override
    //overiding init method in charlie.card.shoe
    public void init() {
        ran = new Random(1);
        
        load();
        
        shuffle();         
    }
}
