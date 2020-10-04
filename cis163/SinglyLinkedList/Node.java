
public class Node
{

    public Object data; // the data stored in this node
    public Node next; // a reference to the next node in the chain

    public Node(Object data, Node next)
    {
        this.data = data;
        this.next = next;
    }

    /*********************************************************
     * The equals method for the Node class defers to the 
     * equals method defined for the data field to compare
     * it with some other object for equality.
     * 
     * A null data reference is a possibility.
     * 
     *********************************************************/
    public boolean equals(Object obj)
    {
        if (this.data == null)
        {
            return false;
        }

        return this.data.equals(obj);
    }

    public String toString()
    {
        return this.data.toString();
    }

    
    public static void main( String[ ] args )
    {
        Node node = new Node( "abc", null );
        System.out.println( node.toString( ) );

        Node node2 = new Node( new Integer( 17 ), null );
        System.out.println( node2.toString( ) );
    }
}