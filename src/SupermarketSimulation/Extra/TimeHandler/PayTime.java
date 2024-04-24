package SupermarketSimulation.Extra.TimeHandler;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The PayTime class implements the TimeStrategy interface to calculate the time it takes for a customer to pay.
 */

public class PayTime implements TimeStrategy{
    private UniformRandomStream uniRandom;
    public PayTime(double kmin, double kmax,long seed){
        this.uniRandom = new UniformRandomStream(kmin,kmax,seed);
    }
    @Override
    public double finishEventTime(double currentTime) {
        return  currentTime + uniRandom.next();
    }

    @Override
    public Object getEventType() {
        return this;
    }
}
