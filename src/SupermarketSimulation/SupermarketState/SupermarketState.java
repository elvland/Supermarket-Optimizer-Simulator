package SupermarketSimulation.SupermarketState;

import SimulationLibrary.Events.Event;
import SimulationLibrary.State.State;

import SupermarketSimulation.Extra.Customer;
import SupermarketSimulation.Extra.CustomerFactory;
import SupermarketSimulation.Extra.Enum.CheckoutStatus;
import SupermarketSimulation.Extra.Enum.StoreStatus;
import SupermarketSimulation.Extra.FIFIOqueue;
import SupermarketSimulation.Extra.TimeHandler.ArrivalTime;
import SupermarketSimulation.Extra.TimeHandler.GatherTime;
import SupermarketSimulation.Extra.TimeHandler.PayTime;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Anton Alexandersson
 * Olle Elvland
 * Lukas Eriksson
 * Vincent Gustafsson
 * <p>
 * The SupermarketState class represents the state of the supermarket simulation,
 * handling customer arrivals, checkout processes, and store status.
 */

public class SupermarketState extends State {
    private final CheckoutHelper checkoutHelper;
    private final FIFIOqueue checkoutQueue;
    private final CustomerFactory customerFactory;
    private final ArrayList<Customer> customers;
    private final ArrayList<Object> parameters;
    private StoreStatus storeStatus;
    private double currentTime;
    private int purchasCount;
    private int rejectionCount;
    private double totFreeCashoutTime;
    private double totQueueTime;
    private final ArrivalTime arrivalTime;
    private final GatherTime gatherTime;
    private final PayTime payTime;
    private String eventName;
    private int totCustomersInStore;
    private final int maxCustomers;
    private double lastPayTime;
    private int customerID;
    private int freeCashoutCount;
    private int totQueueCustomer;
    private int numberinstore;
    private double preEventTime;
    private final int totcheck;
    private int overallqueuePeople;
    private final boolean optimize;
    private final boolean printRes;
    private double closeTime;

    //-----------TILLSTÅNDSVARIABLER--------------//
    private final int totCheckouts;
    private final CheckoutStatus[] checkouts;


    public SupermarketState(int nAmountOfCheckOuts, int maxCustomers, double lambda,
                            double pmin, double pmax, double kmin,
                            double kmax, long seed, boolean optimize, boolean printRes) {
        this.parameters = new ArrayList<>();
        this.parameters.addAll(Arrays.asList(nAmountOfCheckOuts, maxCustomers, lambda, pmin, pmax, kmin, kmax, seed));

        this.printRes = printRes;
        this.optimize = optimize;
        this.totCheckouts = nAmountOfCheckOuts;

        this.checkouts = new CheckoutStatus[nAmountOfCheckOuts];
        this.checkoutHelper = new CheckoutHelper(this.checkouts);
        this.totcheck = nAmountOfCheckOuts;

        //Store & Customer & checkots setup
        this.storeStatus = StoreStatus.Open;
        this.maxCustomers = maxCustomers;
        this.checkoutQueue = new FIFIOqueue();          //QUEUE FOR CHECKOUT
        this.customerFactory = new CustomerFactory();   //CREATE CUSTOMERS
        this.customers = new ArrayList<>();             //STORING CUSTOMERS

        // TIME MANAGEMENT RANDOMNESS
        this.arrivalTime = new ArrivalTime((long) lambda, seed);
        this.gatherTime = new GatherTime(pmin, pmax, seed);
        this.payTime = new PayTime(kmin, kmax, seed);


        //Stats & State varaibles
        this.currentTime = 0;                                               //TID)
        this.eventName = "";                                                //HÄNDELSE)
        this.customerID = 0;                                                //KUND NUMMER )
        this.storeStatus = StoreStatus.Open;                                // ?)
        this.freeCashoutCount = checkoutHelper.countFreeCashiers(checkouts);//led
        this.totFreeCashoutTime = 0;                                        //ledT)
        this.totCustomersInStore = 0;                                       //I)
        this.purchasCount = 0;                                              //$)
        this.rejectionCount = 0;                                            // :-()
        this.totQueueCustomer = 0;                                          //köat)
        this.totQueueTime = 0;                                              //köT)


        //Results
        this.lastPayTime = 0;
        this.overallqueuePeople = 0;

    }

    public boolean isOptimize() {
        return this.optimize;
    }

    public ArrayList<Object> getParameters() {
        return this.parameters;
    }

    public double getCloseTime() {
        return this.closeTime;
    }

    public void setCloseTime(double closeTime) {
        this.closeTime = closeTime;
    }
    public boolean isPrintRes() {
        return this.printRes;
    }

    //------------------View GETTERS-------------------//

    public int getCheckoutTOTAL() {
        return this.totCheckouts;
    }

    public double getEventTime() { //EVENT START-TIME
        return this.currentTime;
    }

    public String getEventName() { //EVENTNAME
        return this.eventName;
    }

    public int getCustomerID() { //CUSTOMER ID
        return this.customerID;
    }

    public String getStoreStatus() { // Storestatus string (ö = öppen, s = stängt)
        return this.storeStatus == StoreStatus.Open ? "ö" : "s";
    }

    public int getNumberInStore() {
        return this.numberinstore;
    }

    private void setNumberInStore() { //ALL TIME COUNT CUSTOMERS
        this.numberinstore++;
    }

    public double getfreeCashOutTime() { //TOTAL FREE/WASTED CASHOUT TIME
        return this.totFreeCashoutTime;
    }

    public int getFreeCashoutCount() { // NUMBEER OF FREE CASHOTS
        freeCashoutCount = checkoutHelper.countFreeCashiers(checkouts);
        return this.freeCashoutCount;
    }

    public int getTotCustomersInStore() { //NUMBER OF CUSTOMERS IN STORE
        return this.totCustomersInStore;
    }

    public int getNumValidBuyers() { //NUMBER OF SUCCESFULL PURCHASES
        return this.purchasCount;
    }

    public int getRejectionCount() { //NUMBER OF MISSED CUSTOMERS
        return this.rejectionCount;
    }

    public int getTotQueueCustomer() { //NUMBEER OF CUSTOMERS QUEUED
        return this.totQueueCustomer;
    }

    public double getTotQueuTime() { //CURRENTLY NUMBER OF CUSTOMER IN QUEUE
        return this.totQueueTime;
    }

    public int getTotCheckouts() {
        return this.totcheck;
    }

    private void setPreEventTime(double time) {
        this.preEventTime = time;
    }

    public double getPreEventTime() {
        return this.preEventTime;
    }

    //------------------CustomerStats-------------------//
    private void setPreCustomer(int customerID) {
        this.customerID = customerID;
    }

    public void addRejectedCustomer() {
            this.rejectionCount++;
    }

    public void addValidBuyer() {
        this.purchasCount++;
    }

    public int getOverallQueueppl() {
        return this.overallqueuePeople;
    }

    public void addCustomerInStore() {
        setNumberInStore(); //OVERALL TOTAL CUSTOMERS IN STORE
        this.totCustomersInStore++; //CURRENT TOT CUSTOMER IN STORE
    }

    public void removeCustomer(Customer customer) {
        this.totCustomersInStore--;
        customers.remove(customer);
    }

    //------------------CheckoutFunctions------------------//

    public String getQueue() {
        return this.checkoutQueue.toString();
    }

    public void useCheckout() {
        this.checkoutHelper.occupyCheckout(checkouts);
    }

    public void freeCheckout() {
        this.checkoutHelper.freeCheckout(checkouts);
    }

    public void addCustomerQueue(Customer c) {
        this.totQueueCustomer++;
        this.overallqueuePeople++;
        this.checkoutQueue.läggtill(c);
    }

    public Customer nextInLine() {
        this.totQueueCustomer--;
        return checkoutQueue.getFirstInLine();
    }

    public boolean hasCustomerInQueue() {
        return !this.checkoutQueue.isQueueEmpty();
    }

    public boolean hasFreeChckout() {
        return this.freeCashoutCount > 0;
    }


    /**
     * Calculates total time all the cashouts stood unused in the store
     * Time diff
     */
    private void freeCashoutTime() {
        double eventTimeDifference = getEventTime() - getPreEventTime(); //Delta tid between two adjacent events
        double cashoutTime = eventTimeDifference * getFreeCashoutCount();

        //Customers arriving after closetime shouldn't affect free cashout time
        if (storeStatus == StoreStatus.Closed && !getEventName().equals("Ankomst")) {
            this.totFreeCashoutTime += cashoutTime;
        } else if (storeStatus == StoreStatus.Open) {
            this.totFreeCashoutTime += cashoutTime;
        }

        setPreEventTime(getEventTime()); //Assign eventime to ease calculations for the next events time
    }

    /**
     * Calculates total time all the customers stood in queue waiting for a chashout in the store
     */
    private void calcQueueTime(double calculatedTime) {
        if (hasCustomerInQueue()) {
            this.totQueueTime += (calculatedTime - getPreEventTime()) * getTotQueueCustomer();
        }
    }


    //------------------StoreStatus------------------//

    public boolean isStoreOpen() {
        return this.storeStatus == StoreStatus.Open;
    }

    public void changeStoreStatus() {
        //Swaps storeStatus to opposite store status (open or closed)
        this.storeStatus = (this.storeStatus == StoreStatus.Open) ? StoreStatus.Closed : StoreStatus.Open;
    }

    public boolean isStoreFree() {
        return this.getTotCustomersInStore() < this.maxCustomers;
    }
    //---------------------TIME---------------------//

    public double getArriveTime(double preEndTime) {
        Object eventType = this.arrivalTime.getEventType();
        return nextEventTime(preEndTime, eventType);
    }

    public double getGatherTime(double preEndTime) {
        Object eventType = this.gatherTime.getEventType();
        return nextEventTime(preEndTime, eventType);
    }

    public double getPayTime(double preEndTime) {
        Object eventType = this.payTime.getEventType();
        return nextEventTime(preEndTime, eventType);
    }

    public Customer createNewCustomer() {
        return this.customerFactory.createCustomer();
    }


    public double getLastPayTime() {
        return this.lastPayTime;
    }

    /**
     * The event time for next event is calculated. It is calculated differently based on what event type is passed into the function
     * nextEventtime = previous eventtime + random generated DELTA
     * (Delta =uniform distributed for gathertime and paytime / Expo random for arrivetime)
     * <p>
     * Sets preEventtime in a variable for upcoming calulations simulation time to next
     *
     * @param preEndTime eventTime for previous event (discrete)
     * @param eventType  Different event time-type of either arrivetime, gathertime or paytime
     * @return The event time for NEXT upcoming event
     */
    private double nextEventTime(double preEndTime, Object eventType) {
        double nextEventTime;


        if (eventType instanceof ArrivalTime || preEndTime == 0) {
            nextEventTime = arrivalTime.finishEventTime(preEndTime);
        } else if (eventType instanceof GatherTime) {
            nextEventTime = gatherTime.finishEventTime(preEndTime);
        } else if (eventType instanceof PayTime) {
            nextEventTime = payTime.finishEventTime(preEndTime);
            this.lastPayTime = nextEventTime;
        } else {
            return 0;
        }

        super.setTime(nextEventTime); //Sets new Simulator time to next event time

        return nextEventTime;
    }

    private void setEventTime(double eventTime) {
        this.currentTime = eventTime;
    }

    private void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Updates state based on event and customer ID:
     * Calculates queue time
     * Sets event time and name
     * Updates free cashout time
     * Sets current customer ID
     * Notifies observer about state update.
     *
     * @param preEvent   The event preceding the current event.
     * @param customerID The ID of the customer associated with the event.
     */
    public void info(Event preEvent, int customerID) {
        calcQueueTime(preEvent.getCalculatedTime());
        setEventTime(preEvent.getCalculatedTime());
        setEventName(preEvent.toString());
        freeCashoutTime();
        setPreCustomer(customerID);
        super.informObservers();
    }


}
