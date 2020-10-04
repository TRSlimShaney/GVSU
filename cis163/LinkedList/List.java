import java.io.Serializable;
import java.util.NoSuchElementException;

/******************************************************************************
 * A List encapsulates a list of Objects, such as String, etc.
 * 
 * A List is created to have both a top dummy Node and a bottom dummy Node.
 * Cursor is initialized to match the top field. 
 * 
 * The end field is always a reference to the bottom dummy Node.
 * 
 * The convention that is implemented by this List structure goes as follows. 
 * For each value of the cursor field, 
 * 
 *                               cursor
 * 
 * the data are stored in the next Node,
 * 
 *                            cursor.next.data
 * 
 * -----------------------------------------
 *     An empty list
 * Below, cursor = top, i.e. the cursor index is 0
 * 
 * top.prev (cursor.next)   null
 * top.data (cursor.next)   dummy
 * top.next (cursor.next)   link forward
 *                 
 * end.prev                 link backward
 * end.data                 dummy
 * end.next                 null
 * 
 * 
 * 
 * -----------------------------------------
 *     A list of 1 element
 * Below, cursor = top, i.e. the cursor index is 0
 * 
 * top.prev (cursor.next)   null
 * top.data (cursor.next)   dummy
 * top.next (cursor.next)   link forward
 *                 
 * cursor.next.prev         links backward
 * cursor.next.data         "one"
 * cursor.next.next         link forward
 *                 
 * end.prev                 link backward
 * end.data                 dummy
 * end.next                 null
 * 
 * -------------------------------------------
 *     A list of 2 elements
 * Below, cursor = top.next, i.e. the cursor index is 1
 * 
 * top.prev                 null
 * top.data                 dummy
 * top.next                 link forward
 *                 
 * cursor.prev              link backward
 * cursor.data              "one"
 * cursor.next              link forward
 *                 
 * cursor.next.prev         link backward
 * cursor.next.data         "two"
 * cursor.next.next         link forward
 *                 
 * end.prev                 link backward
 * end.data                 dummy
 * end.next                 null
 * 
 * -------------------------------------------
 * 
 * For the initial cursor value, an item will be entered in the
 * 0th position in the list. Unless the cursor is changed, a 
 * subsequent item will be entered still in the 0th position, 
 * where each existing item in the list will be demoted by one place.
 * 
 **********************************************************************/

public class List implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected Node top;     // A top dummy Node to begin the list
    protected Node end;     // An end dummy Node to end the list

    protected Node cursor;  // The current Node in the list. 

    /*****************************************************************
     * Invokes the other constructor.
     *****************************************************************/
    public List( )
    {
        this( "" );
    }

    /*****************************************************************
     * Constructs the list with two dummy Nodes, 
     * one Node to always lead the list, and a
     * second Node to always trail the list.
     * 
     * The lead node contains the title;
     *****************************************************************/
    public List( String title )
    {
        this.top = new Node( title, null, null );    // dummy top Node
        this.end = new Node( null, this.top, null ); // dummy bottom Node
        this.top.next = this.end;

        this.cursor = this.top;

        this.setTitle( title );
    }

    public void setTitle( String title )
    {
        this.top.data = title;
    }

    public String getTitle(  )
    {
        return this.top.data.toString( );
    }

    /*****************************************************************
     * Returns whether the list is empty.
     *****************************************************************/
    public boolean isEmpty( ) 
    {
        return this.end.prev == this.top;  //this.top.next.next == null;
    }

    /*****************************************************************
     * Returns whether the cursor points to
     * the 0th location in the list
     *****************************************************************/
    public boolean cursorIsFirst( )
    {
        return this.cursor == this.top;
    }

    /*****************************************************************
     * Moves the cursor to the top, so that the first item in the
     * list is dereferenced as cursor.next.data
     *****************************************************************/
    public void cursorToFirst( )
    {
        cursor = top;
    }

    /*****************************************************************
     * Returns the first element in the list, i.e. this.get( 0 )
     *****************************************************************/
    public Object getFirst( )
    {
        return this.get(0);
    }
    /*****************************************************************
     * Returns whether the cursor points to
     * the end of the list, i.e. the location
     * for appending an item to the list.
     *****************************************************************/
    protected boolean cursorIsAtTheEnd( )
    {
        // 3 review and ?

        return  this.cursor == this.end.prev; //this.cursor.next == this.end;  //this.cursor.next.next == null; 
    }

    /*****************************************************************
     * Moves the cursor to the end of the list, which is at 
     * 
     *                    this.end.prev.
     *                    
     * No element is ever at the end of the list. Whenever an element
     * is added to the end of the list, it becomes the last element.
     *****************************************************************/
    protected void cursorToEnd( )
    {
        cursor = end.prev;
    }

    /*****************************************************************
     * Returns whether the cursor points to the last occupied
     *  location in the list
     *****************************************************************/
    protected boolean cursorIsLast( )
    {
        if (cursor == this.get(this.size())) {
        	return true;
        }
        return false;
    }

    /*****************************************************************
     * Moves the cursor to the Node immediately preceding the
     * last object in the list, if there is a last object.
     * 
     * No object is ever at the end of the list. Whenever an object
     * is added to the end of the list, it becomes the new last object.
     *****************************************************************/
    protected void cursorToLast( ) 
    {
    	this.cursorToEnd( );
        if (! this.cursorIsFirst()) {
        	this.cursorToPrevious();
        }
    }

    /*****************************************************************
     * Returns the last object in the list.
     *****************************************************************/
    public Object getLast( )
    {
        Object obj = null;

        

        
        return obj;
    }

    /*****************************************************************
     * Returns whether the list stores a next item.
     *****************************************************************/
    public boolean cursorHasNext( ) 
    {
        // 8 fix this

        return false;
    }

    /*****************************************************************
     * Advances the cursor to the next place in the list.
     *****************************************************************/
    protected void cursorToNext( )
    {
        if (cursorHasNext( ))
        {       
            // 9) fix this
        }
    }

    /*****************************************************************
     * Returns the current object, and advances the cursor.
     *****************************************************************/
    public Object getNext( ) 
    {
        Object obj = null;
        try
        {
            if (!cursorHasNext())
            {
                throw new Exception( "No element at the end of the list." );
            }
            // 10) fix this (two lines of code

        }
        catch (Exception e )
        {
            System.out.println( e +  " getNext: " );
        }
        return obj;
    }

    protected boolean cursorHasPrevious()
    {
        return this.cursor.prev != null;
    }

    /*****************************************************************
     * Backs up the cursor to the previous place in the list.
     *****************************************************************/
    protected void cursorToPrevious( )
    {

        //  11) fix this
    }

    /*****************************************************************
     * inserts a Node for the Object immediately after the Node
     * referenced by cursor.  The value of cursor does not change!
     *****************************************************************/
    public void add( Object obj ) 
    { 
    	this.cursor.next = new Node(obj, this.cursor, this.cursor.next);
    	this.cursor.next.next.prev = this.cursor.next;

    }

    /*****************************************************************
     * Inserts a Node for the element in the specified index place.
     *****************************************************************/
    public void add( int index, Object obj )
    {     
        if (0 <= index && index <= this.size( ))
        {        
        	this.cursorToIndex(index);
            this.add(obj);
        }
    }

    /******************************************************************
     * 
     ******************************************************************/
    public void addAll(int index, List c)
    {
    	if (0 <= index && index <= this.size( ))
        {
            c.reverse( );

            // 8 fix this - three lines of code
            c.reverse( );
        }
    }

    /*****************************************************************
     * Inserts a Node for the Object immediately after the top
     * dummy node for a new first object in the list.
     *****************************************************************/
    public void prefix( Object obj )
    { 
    	this.top.next = new Node(obj, this.top, this.top.next );
        this.top.next.next.prev = this.top.next;
    }

    /*****************************************************************
     * No object is ever at the end of the list. Whenever an object
     * is added to the end of the list, it becomes the last element.
     *
     *
     * This append method inserts a Node for the object at the end of
     * the list, which, physically, will immediately come before the
     * end (dummy) node.
     *****************************************************************/
    public void append( Object obj )
    {       
    	Node anchor = this.cursor; // saves cursor
    	this.cursorToLast();
    	Node newNode = new Node(obj, this.cursor, this.cursor.next);
         
         this.cursor = anchor;           // restores cursor

    }
    /*****************************************************************
     * This set method replaces the Object, obj, to the current.next
     * node data field, but only if it exists.
     *****************************************************************/
    public void set( Object obj )
    {
    	if (! this.cursorIsAtTheEnd())
        {
            this.cursor.next.data = obj;
        }
    }
    /*******************************************************************
     * If index is a legitimate list position, this set method 
     * moves the cursor to the specified index position, before
     * invoking the set Object method to complete the object
     * replacement.
     *****************************************************************/
    public void set( int index, Object obj )
    {

    	this.cursorToIndex(index);
        if (0 <= index && index <= this.size( ))
        {
            this.set(obj);
        }
    }

    /*****************************************************************
     * Returns the index of obj in the list for a first occurrence;
     * or -1 if obj is not contained in the list.
     * 
     *****************************************************************/
    public int indexOf( Object obj )
    {
        this.cursorToFirst( );
        int index = 0;
        while (! this.cursorIsAtTheEnd( ))
        {
            if (this.cursor.next.data.equals( obj ))
            {
                return index;
            }
            cursor = cursor.next;
        }
        return -1;
    }

    /*****************************************************************
     * Returns the index of obj for its last occurrence in the list;
     * or -1 if obj is not contained in the list.
     * 
     * contains ?
     *****************************************************************/
    public int lastIndexOf(Object obj)
    {
        int index = this.size()-1;

        this.cursorToLast( );
        while (! this.cursorIsFirst() )
        {
            if (this.cursor.next.data.equals( obj ))
            {
                return index;
            }

            
            cursor = cursor.prev;
        
        }
        return -1;
    }

    /*****************************************************************
     * The get method returns the object, cursor.next.data, if it exists.
     *****************************************************************/
    public Object get( ) 
    {
        Object obj = null;

        if (cursor.next.data != null) {
        	obj = cursor.next.data;
        }
        
        return obj;
    }

    /*****************************************************************
     * get moves the cursor to the specified index position, if the
     * index is legitemate, and then invokes get() to return the object.
     *****************************************************************/
    public Object get( int index )
    {
        Object obj = null;

        if (0 <= index && index <= this.size( ))
        {        
        	obj = this.get();
        }
        
        return obj;
    }

    /*****************************************************************
     * Returns the index for the cursor position in the list.
     *****************************************************************/
    public int getIndex( )
    {
        Node helper = cursor;
        
        int index = 0;
        this.cursor = this.top;

        while (this.cursor != helper)
        {
        this.cursorToNext();
        index++;
        }

        return index;
    }

    /*****************************************************************
     * Changes the cursor to reference the Node specified by the index
     * parameter, if the index is legitimate.
     *****************************************************************/
    protected void cursorToIndex( int index )
    {
        if (0 <= index && index <= this.size( ))
        {
            while (this.getIndex() != index) {
        	
        }
    }
    }

    /*******************************************************************
     * Returns a sub-list of objects from start to stop in this list.
     * The objects in the returned sub-list are shared by this list.
     ******************************************************************/
    public List subList(int start, int stop)
    {
    	List newList = new List( "sublist" );

        if (start < 0 || start > stop || stop > this.size( ))
        {
            throw new IllegalArgumentException( );
        }   

        this.cursorToIndex(start);

        while (! this.cursorIsAtTheEnd() && start <= stop)
        {
            newList.append( this.get( ) );

            this.cursorToNext();
            start++;
        }

        return newList;
    }

    /*****************************************************************
     * Swaps two objects in the list, the object at index j
     * with the object at index k.
     *****************************************************************/
    public void swap( int j, int k )
    {
    	Node anchor = this.cursor;      // saves the cursor

        Object temp = this.get( j );    
        this.set( j, this.get( k ) );
        this.set( k, temp );

        this.cursor = anchor;           // restores the cursor

    }

    /*****************************************************************
     * Reverses the order of objects in the list.
     *****************************************************************/
    public void reverse( )
    {

        for (int i = 0; i <= this.size(); i++) {
        	this.swap(this.size() - i, i);
        }
    }

    /*****************************************************************
     * Returns the object, curror.next.data, and re-links the list to exclude
     * the node that contained the data.  The value of cursor does not change!
     * 
     * @return
     * @throws Exception
     *****************************************************************/
    public Object remove( )
    {
    	Object obj = null;

        if (cursorHasNext())
        {
            obj = this.cursor.next.data;
            this.cursor.next      = this.cursor.next.next;
            this.cursor.next.prev = this.cursor.next;
        }

        return obj;
    }

    /*****************************************************************
     * First moves the cursor to the index specified location in the list,
     * and then calls this.remove to remove and return the object.
     * 
     * @return
     * @throws Exception
     *****************************************************************/
    public Object remove( int index )
    {
        Object obj = "";
        try
        {
            this.cursorToIndex(index);
            this.remove(index);
        }
        catch (Exception e)
        {
            System.out.println( "remove:  Exception: attempt to remove an item beyond on the end of the list" );
        }

        return obj;
    }

    /*****************************************************************
     * Empties the list.
     *****************************************************************/
    public void clearAll( )
    {
        this.cursorToFirst( );

        try
        {
            while (! this.isEmpty( ))
            {
                this.remove( );
            }
        }
        catch (Exception e)
        {
            System.out.println( "clearAll:  Exception. Attempt to remove from an empty list." );
        }
    }

    /*****************************************************************
     * Returns a count of how many objects are contained within the list.
     *****************************************************************/
    public int size( )
    {
        int count = 0;
        for (this.cursorToFirst( ); ! this.cursorIsAtTheEnd( ); this.cursorToNext() )
        {
           if (cursor.data != null) {
        	   count++;
           }
        }

        return count;
    }

    /*****************************************************************
     * Returns a string for all of the objects in the list from 
     * top to bottom.
     *****************************************************************/
    public String toString( )
    {
        Node anchor = this.cursor;     // saves the cursor

        String returnString = "";
        for (this.cursorToFirst( ); ! this.cursorIsAtTheEnd(); this.cursorToNext( )) 
        {
            returnString += this.get( ).toString( );
        }

        this.cursor = anchor;           // restores the cursor
        return returnString;
    }

    /*****************************************************************
     * Returns a string for the objects in the list indexed 
     * between start and stop, inclusively.
     *****************************************************************/
    public String display( int start, int stop )
    {
        Node anchor = this.cursor;     // saves the cursor  

        String returnString;

        if (start < 0 || start > stop || stop > this.size( ))
        {
            throw new IllegalArgumentException( );
        }   

        this.cursorToIndex( start );
        returnString = "";
        while (! this.cursorIsAtTheEnd() && start <= stop)
        {
            returnString += this.get( );

            this.cursorToNext();
            start++;
        }

        this.cursor = anchor;           // restores the cursor
        return returnString;
    }



    /*****************************************************************
     * 
     * 
     *****************************************************************/
    public static void main( String[ ] args )
    {
        List list = new List( "List:  " );

        list.add( "one" );
        list.add( "two" );
        list.add( "three" );

        System.out.println( list.toString( ) );

        list.reverse( );
        list.setTitle( "List reversed: " );
        System.out.println( list.toString( ) );

        list.reverse( );
        list.setTitle( "List reversed twice: " );
        System.out.println( list.toString( ) );
    }
}