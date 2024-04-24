package SupermarketSimulation.View;

import SimulationLibrary.View.View;

import SupermarketSimulation.SupermarketState.SupermarketState;

import java.util.ArrayList;
import java.util.Observable;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The SupermarketView class represents the view for the supermarket simulation,
 * and updates the view based on changes in the SupermarketState.
 */

public class SupermarketView extends View {

    public SupermarketView(SupermarketState state){

       if(!state.isOptimize()){
           printParam(state.getParameters());
           printHeader();
       }
    }

    private void printHeader() {
        System.out.printf("\t%s \t%7s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \n",
              "Tid", "Händelse", "Kund", "?", "led", "ledT", "I", "$", ":-(", "köat", "köT", "köar", "[Kassakö..]");

    }
    /**
     * This method overrides the update method of the Observer interface.
     * @param o The observable object that triggered the update.
     * @param superState The new state provided by the observable object.
     */
    @Override
    public void update(Observable o, Object superState) {
        super.update(o, superState);

        printall((SupermarketState) superState);
    }
    //STATIC BCUSE ITS JUST OUTPRINT OF VARIABLES
    public static void printParam(ArrayList<Object> parametrs){
        System.out.println("PARAMETRAR");
        System.out.println("==========");
        System.out.printf("Antal kassor, N...........: %s \n",parametrs.get(0));
        System.out.printf("Max som ryms, M...........: %s \n",parametrs.get(1));
        System.out.printf("Ankomsthastighet, lambda..: %s \n" ,parametrs.get(2));
        System.out.printf("Plocktider, [P_min..P_max]: [%s..%s] \n" ,parametrs.get(3),parametrs.get(4));
        System.out.printf("betaltider, [K_min..K_max]: [%s..%s] \n" ,parametrs.get(5), parametrs.get(6));
        System.out.printf("Frö, f....................:  %s \n\n", parametrs.get(7) );


    }

    private void printall(SupermarketState state) {
/*
        if (state.isOptimize()) {
            if(state.isPrintRes()) {

                if (!state.isSimulatorRunning()) {
                    printParam(state.getParameters());

                    System.out.printf("Stängning sker vid %.2f och stop hädnesle sker vid 999 \n", state.getCloseTime());
                    System.out.printf("Minsta antal kassor som ger minimalt antal missade (%s) (KUNDER): %s (KASOR).",
                            state.getRejectionCount(), state.getCheckoutTOTAL());
                }

            }*/



            String queueTime = String.format("%.2f", state.getTotQueuTime());
            String eventTime = String.format("%.2f", state.getEventTime());
            String freeCashoutTime = String.format("%.2f", state.getfreeCashOutTime());

            if (state.getEventName().equals("Start")) {

                System.out.printf("\t%s \t%8s \n", eventTime, state.getEventName());

            } else if (state.getEventName().equals("Stängning ")) {

                System.out.printf("\t%s  \t%s" +
                                "\t%5s \t%2s \t%8s " +
                                "\t%s \t%s \t%s " +
                                "\t\t%s \t\t%s \t%s \t\t  %s \n",
                        eventTime, "Stänger \t---",
                        state.getStoreStatus(), state.getFreeCashoutCount(), freeCashoutTime,
                        state.getTotCustomersInStore(), state.getNumValidBuyers(), state.getRejectionCount()
                        , state.getOverallQueueppl(), queueTime, state.getTotQueueCustomer(), state.getQueue());
            } else if (!state.isSimulatorRunning()) {
                System.out.println("\t999 \tSTOP  \n");
                printResults(state);
            } else {

                System.out.printf("\t%s \t%8s \t%s" +
                                "\t%5s \t%2s \t%8s " +
                                "\t%s \t%s \t%s " +
                                "\t\t%s \t\t%s \t%s \t\t  %s \n",
                        eventTime, state.getEventName(), state.getCustomerID(),
                        state.getStoreStatus(), state.getFreeCashoutCount(), freeCashoutTime,
                        state.getTotCustomersInStore(), state.getNumValidBuyers(), state.getRejectionCount()
                        , state.getOverallQueueppl(), queueTime, state.getTotQueueCustomer(), state.getQueue());
            }


    }



    private void printResults(SupermarketState state) {
            {
                double avgFree = state.getfreeCashOutTime()/state.getTotCheckouts(); // (tiden kassor stod ledig / antal kassor)
                int allCustomer = (state.getNumberInStore() + state.getRejectionCount());
                double avgFreePercent = (avgFree/state.getLastPayTime()) * 100;  //
                double avgQueueTime = state.getTotQueuTime()/state.getOverallQueueppl();
                    String s = String.format("%.2f", state.getEventTime());
                    System.out.println("RESULTAT");
                    System.out.println("========");
                    System.out.println("1) Av " + allCustomer  + " kunder handlade " + (state.getNumValidBuyers()) +
                            " medan " + state.getRejectionCount() + " missades.");
                    System.out.printf("2) Total tid " + state.getTotCheckouts() + " kassor varit lediga: %.2f te.\n", state.getfreeCashOutTime());

                    System.out.printf("   Genomsnittlig ledig kassatid: %.2f te (dvs %.2f%% av tiden från öppning tills sista kunden betalat).\n",
                            avgFree, (avgFreePercent));
                    System.out.printf("3) Total tid %d kunder tvingats köa: %.2f te.\n", state.getOverallQueueppl(), state.getTotQueuTime());
                    System.out.printf("   Genomsnittlig kötid: %.2f te.\n\n\n", (avgQueueTime));

                }


        }





}
