package SupermarketSimulation.SupermarketState;

import SupermarketSimulation.Extra.Enum.CheckoutStatus;

import java.util.ArrayList;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The CheckoutHelper class assists in managing checkout statuses in the supermarket simulation,
 * including methods to occupy and free checkout counters.
 */

public class CheckoutHelper {
    private final int totCheckouts;
    private int freeCashCounter;

    public CheckoutHelper(CheckoutStatus[] checkOutArray) {
        fillEmptyCheckOuts(checkOutArray);
        totCheckouts = checkOutArray.length;
    }

    protected void fillEmptyCheckOuts(CheckoutStatus[] checkOutArray){

        for (int i = 0; i <checkOutArray.length; i++) {
            checkOutArray[i] = CheckoutStatus.Free;
        }
        this.freeCashCounter = checkOutArray.length; // Start from the last cashier [0,1,2,3,4,5] <----
    }
    //protected
     protected int countFreeCashiers(CheckoutStatus[] checkOutArray) {
        int freeCount = 0;
        for (CheckoutStatus checkStatus : checkOutArray) {
            if (checkStatus == CheckoutStatus.Free) {
                freeCount++;
            }
        }
        return freeCount;
    }

    protected void occupyCheckout(CheckoutStatus[] checkOutArray) {
        if(availableCashouts(checkOutArray)){
        checkOutArray[--freeCashCounter] = CheckoutStatus.Occupied;
        } else {
            System.out.println("all checkoutsFull");
        }

    }

    protected void freeCheckout(CheckoutStatus[] checkOutArray) {
        if(allCheckoutsEmpty(checkOutArray)){
            System.out.println("all Chechouts Empty");

        } else{
            checkOutArray[freeCashCounter++] = CheckoutStatus.Free;
        }
    }

    private boolean allCheckoutsEmpty(CheckoutStatus[] checkOutArray){
        return countFreeCashiers(checkOutArray) == totCheckouts;
    }


    private boolean availableCashouts(CheckoutStatus[] checkOutArray) {

        return countFreeCashiers(checkOutArray) > 0;
    }
}
