package Optimize;

import SimulationLibrary.EventQueue;
import SimulationLibrary.Events.StopEvent;
import SimulationLibrary.Simulator.MainSimulator;

import SupermarketSimulation.Events.CloseEvent;
import SupermarketSimulation.Events.OpenEvent;
import SupermarketSimulation.SupermarketState.SupermarketState;
import SupermarketSimulation.View.SupermarketView;

import java.util.Random;


/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 * <p>
 * Optimize handlar om att hitta minsta antalet kassor som minimerar antal missade
 * kunder (dvs maximerar antalet kunder som handlar). Kund missas om den inte
 * kommer in då den anländer (måste vända och handla nån annanstans) pga att det
 * redan är maximalt med kunder i butiken, vilket antas bero på att för få kassor är
 * igång.
 */

public class Optimize {
    public static void main(String[] args) {

        /*
         * OPTIMIZE DELEN
         */
        int bestCashnum =0;
        int i =3;
        Optimize opt = new Optimize();
        if (i == 1) { //metod 1
          /* SupermarketState testState = opt.finalStateOPT(3, K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME,
                    K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.SEED, K.END_TIME,
                    K.STOP_TIME, true);

           */
        } else if (i == 2) { //metod 2

            bestCashnum = opt.minimalCheckoutOPT(K.M, K.L, K.LOW_COLLECTION_TIME,
                    K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME,
                    K.HIGH_PAYMENT_TIME, K.SEED, K.END_TIME,
                    K.STOP_TIME);



        } else if (i == 3) { //metod 3
            bestCashnum = opt.realisticCheckoutNum(K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME,
                    K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.SEED, K.END_TIME,
                    K.STOP_TIME);

        } else {
            bestCashnum = 999;
        }
        // metod 1
        SupermarketState optState = opt.finalStateOPT(bestCashnum, K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME,
                K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.SEED, K.END_TIME,
                K.STOP_TIME, true);

        opt.optPrint(optState);
        //System.out.println(metod3);
    }





    private SupermarketState finalStateOPT(int nCheckouts, int nMaxCustomer, double lambda,
                                           double pmin, double pmax, double kmin, double kmax,
                                           long seed, double close, double stopx, boolean printOk) {
        SupermarketState state = new SupermarketState(nCheckouts, nMaxCustomer, lambda, pmin, pmax, kmin, kmax, seed, true, printOk);
        SupermarketView vy = new SupermarketView(state);
        //state.addObserver(vy);

        SupermarketView.printParam(state.getParameters());
        EventQueue eventQueue = new EventQueue();

        OpenEvent openEvent = new OpenEvent(eventQueue);
        CloseEvent closeEvent = new CloseEvent(close, eventQueue);
        StopEvent stopEvent = new StopEvent(eventQueue,999);

        eventQueue.add(openEvent);
        eventQueue.add(closeEvent);
        eventQueue.add(stopEvent);

        MainSimulator simulator = new MainSimulator();
        return (SupermarketState) simulator.run(state, eventQueue);

    }

    /**
     * Vaad vi är ute efter är minsta antal kassor som minimerar antal missade kunder.
     * Finds and returns the minimum number of checkouts needed to minimize the number of missed customers.
     */
    private int minimalCheckoutOPT(int nMaxCustomer, double lambda, double pmin,
                                   double pmax, double kmin, double kmax,
                                   long seed, double close, double stop) {
        int minCheckOuts = 1;           //Keeps track of the best least checkouts to minimize amount of missed customer
        int preMinMissed = nMaxCustomer;     // number of missed customer for n used to compare n+1 checkouts
        int checkoutNumber = 1;         //Checkout number to test possible values
        boolean printOk = false;
        //
        // N Checkouts        1 2 3 4 5 6 7
        // N Missed customer [7,5,3,2,1,0,0]
        // DECLINING FUNCTION MissedCustomers(checkouts) = - ((Cashouts * N) + 1) EXAMPLE
        // max misses when only one checkout
        // minimum MISSES when checkoutnumbeer == total custoemrs in store
        while (checkoutNumber <= nMaxCustomer) {
            SupermarketState superState = finalStateOPT(checkoutNumber, nMaxCustomer, lambda,
                    pmin, pmax, kmin,
                    kmax, seed, close, stop, printOk);
            int numMissedCustomer = superState.getRejectionCount();

            // Update the minimum number of checkouts and missed customers if a new minimum MISSES (CUSTOMER) is found compared to previous BEST CHECKOUT NUM
            if (numMissedCustomer < preMinMissed) {
                preMinMissed = numMissedCustomer; //New minimum missed.
                minCheckOuts = checkoutNumber; //Sets temporary best number of mincheckouuts
            }
            if (numMissedCustomer == 0) { //Can't find a smaller number of checkouts than when numMissedCustomer == 0
                return minCheckOuts;
            }
            System.out.println(checkoutNumber + " antal kassor, och antal missade " + numMissedCustomer);
            checkoutNumber++;
        }
        return minCheckOuts;


    }


    /**
     * Finds more realistic highest possible cashouts number to minimize number of missed customer
     */
    private int realisticCheckoutNum(int nMaxCustomer, double lamda, double pmin,
                                      double pmax, double kmin, double kmax,
                                      int seed, double close, double stop) {
        int randSeed;
        int sameCounter = 0;
        int highestMinMisses = 0;
        Random rnd = new Random(seed);

        while (true) {
            randSeed = rnd.nextInt();
            int newMinCheckouts = minimalCheckoutOPT(nMaxCustomer, lamda, pmin,
                    pmax, kmin, kmax,
                    randSeed, close, stop);

            if (newMinCheckouts > highestMinMisses) { //Higher checkout means IT IS POSSIBLE for worse case = more realistic
                highestMinMisses = newMinCheckouts;
                sameCounter = 0;
            } else if (newMinCheckouts <= highestMinMisses) {
                sameCounter++;
                System.out.println(highestMinMisses + " is the best for different time");
            }

            if (sameCounter == 100) {
                //Prints out the results with the parameters of the highest cashout number found for minimize misses
                //finalStateOPT(highestMinMisses, nMaxCustomer, lamda, pmin, pmax, kmin, kmax, seed, close, stop, true);
                return highestMinMisses;

            }
        }
    }
    private void optPrint(SupermarketState optstate){
        System.out.printf("Stängning sker vid %.2f och stop hädnesle sker vid 999 \n", optstate.getCloseTime());
        if(optstate.getRejectionCount()>0 ){
            System.out.printf("Minsta antal kassor som ger minimalt antal missade (%s) (KUNDER): %s (KASOR).\n (OBS! Missar som minst %s kunder.)",
                    optstate.getRejectionCount(), optstate.getCheckoutTOTAL(),optstate.getRejectionCount());
        } else{
            System.out.printf("Minsta antal kassor som ger minimalt antal missade (%s) (KUNDER): %s (KASOR))",
                    optstate.getRejectionCount(), optstate.getCheckoutTOTAL());
        }


    }
}