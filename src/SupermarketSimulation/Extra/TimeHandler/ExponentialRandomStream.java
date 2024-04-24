package SupermarketSimulation.Extra.TimeHandler;

import java.util.Random;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 */

/**
 * The ExponentialRandomStream class generates exponentially distributed random numbers.
 */

public class ExponentialRandomStream {

	private Random rand;
	private double lambda;

	public ExponentialRandomStream(double lambda, long seed) {
		rand = new Random(seed);
		this.lambda = lambda;
	}





	public double next() {
		return  -Math.log(rand.nextDouble())/lambda;
	}
}

