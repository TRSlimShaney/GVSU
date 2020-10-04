import java.util.ArrayList;

public class Queue<T> extends ArrayList<T>
{
    public Queue()
    {
        super( );
    }

    public void enqueue( T object )
    {
            super.add(object); // 16 fix
    }

    public Object dequeue( )
    {
        return super.remove(0);    // 17) FIX
    }

    public int length( )
    {
        return super.size();      // 18) FIX

    }

    public static void main(String[] args)
    {
        Queue<Integer> q = new Queue<Integer>();
        
        System.out.println("q.enqueue(33)");
        System.out.println("q.enqueue(22)");
        System.out.println("q.enqueue(null)");
        System.out.println("q.enqueue(11)");
        System.out.println("q.enqueue(00)");
        q.enqueue( 33 );
        q.enqueue(22);
        q.enqueue(null);
        q.enqueue(11);
        q.enqueue(00);
        System.out.println("\tQueue:  " + q.toString( ));
        System.out.println("\tLength:  " + q.length( ));
        
        System.out.println("q.dequeue( ) = " + q.dequeue() );
        System.out.println("\tQueue:  " + q.toString( ));
        System.out.println("\tLength:  " + q.length( ));
    }

}
