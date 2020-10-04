
/*************************************************
 * Keeps track of the customer getting in line.
 *
 * @author Shane Stacy
 * @version 11-19-2017
 ************************************************/
public class GVarrival extends GVevent
{
    /**************************************************
     * Creates the customer
     * @param MarketPlace store the store object
     * @param double time the even time
     *************************************************/
    public GVarrival (MarketPlace store, double time) {
        super(store, time);  
    }

    /**************************************************
     * Process
     *************************************************/
    public void process () {
        store.customerGetsInLine();   
    }

}
