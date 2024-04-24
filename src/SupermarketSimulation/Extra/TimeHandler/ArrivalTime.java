package SupermarketSimulation.Extra.TimeHandler;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 */

/**
 * The ArrivalTime class handles the arrival time of customers in the simulation.
 */

public class ArrivalTime implements TimeStrategy {

    private ExponentialRandomStream expo;

    public ArrivalTime(long lambda, long seed){
        this.expo = new ExponentialRandomStream(lambda,seed);
    }

    @Override
    public double finishEventTime(double currentTime) {
        return currentTime + expo.next(); //CURRENT TIME + DELTA = END EVENTTIME
    }

    @Override
    public Object getEventType() {
        return this;
    }



}
