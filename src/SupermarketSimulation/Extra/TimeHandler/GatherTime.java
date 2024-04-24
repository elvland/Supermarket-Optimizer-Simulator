package SupermarketSimulation.Extra.TimeHandler;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 */

/**
 * The GatherTime class implements the TimeStrategy interface to calculate the time it takes for a customer to gather items.
 */

public class GatherTime implements  TimeStrategy{

    private UniformRandomStream uniRandom;
    public GatherTime(double pmin, double pmax,long seed){
        this.uniRandom = new UniformRandomStream(pmin,pmax,seed);
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
