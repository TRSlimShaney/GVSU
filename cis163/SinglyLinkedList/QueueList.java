
/**
 * Write a description of class QueueList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class QueueList extends List
{
    public QueueList()
    {
        super( );
    }

    public void enQueue( Object object )
    {
            super.append(object); // 13 fix

    }
    
    public Object deQueue( )
    {
    	Object obj = super.remove0();
    	
        return obj;            // 14) FIX


    }
    
    public int length( )
    {
        return super.size();            // 15) FIX
    }
    
    public static void main(String[] args)
    {
        QueueList q = new QueueList();
        System.out.println("q.enQueue(33)");
        System.out.println("q.enQueue(22)");
        System.out.println("q.enQueue(null)");
        System.out.println("q.enQueue(11)");
        System.out.println("q.enQueue(00)");
        q.enQueue(33);
        q.enQueue(22);
        q.enQueue(null);
        q.enQueue(11);
        q.enQueue(00);
        System.out.println("\tQueueList:  " + q.display( ));
        System.out.println("\tLength:  " + q.length( ));
          
        System.out.println("q.deQueue( ) = " + q.deQueue( ) );
        System.out.println("\tQueue:  " + q.display( ));
        System.out.println("\tLength:  " + q.length( ));
    }
    
}
