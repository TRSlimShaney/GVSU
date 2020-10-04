
/**************************************************
 * Manages a customer's departure
 *
 * @author Shane Stacy
 * @version 11-19-17
 *************************************************/
public class GVdeparture extends GVevent
{

    
    /**************************************************
     * Creates the GVdeparture object
     * @param MarketPlace store the store
     * @param double time the time
     * @param int id the cashier id
     *************************************************/
    public GVdeparture (MarketPlace store, double time, int id) {
        
    super(store, time, id);    
        
    }
    /**************************************************
     * Process
     *************************************************/
    public void process () {
        
     store.customerPays(getCashier());   
        
    }
    
    
}
