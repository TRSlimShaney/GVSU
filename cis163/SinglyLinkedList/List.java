
/***********************************************************************************
 * Without the use of any dummy Nodes, List encapsulates a singly-linked list of
 * Objects, such as String, etc.
 * 
 * A singly-linked List is created to have a top/head reference and a cursor
 * Node reference, where both top and cursor are initialized to null.
 * 
 * Given that the size of the list is zero or more in count, the top reference is
 * always a reference to the 0th node, and cursor is allowed to float as a 
 * reference to any existing node.
 * 
 * The list implemented by a singly-linked structure goes as follows. The
 * 
 *                                cursor
 *                                
 * field is a reference to the current node, where the data are stored
 * in the 
 *                              cursor.data
 * field.
 * 
 **********************************************************************/

public class List
{

    protected Node top;   // head
    protected Node cursor;

    /*****************************************************************
     * Constructs the list
     *****************************************************************/
    public List()
    {
        this.top = null;
        this.cursor = null;
    }

    /*****************************************************************
     * The isEmpty() method returns whether the list is empty.
     *****************************************************************/
    public boolean isEmpty()
    {
        return this.top == null;
    }

    /*****************************************************************
     * The toString() method returns a string description of the
     * collection of objects in the list.
     *****************************************************************/
    //     public String toString()
    //     {
    //         Node anchor = this.cursor; // saves the cursor
    // 
    //         String returnString = "";
    //         for (cursor = top; cursor != null; cursor = cursor.next)
    //         {
    //             if (cursor.data != null)
    //             {
    //                 returnString += cursor.data.toString();
    //             }
    //         }
    // 
    //         this.cursor = anchor; // restores the cursor
    //         return returnString;
    //     }

    /*****************************************************************
     * The toString() method returns a string description of the
     * collection of objects in the list.
     *****************************************************************/
    public String toString()
    {
        Node anchor = this.cursor; // saves the cursor

        String returnString = "";
        cursor = top;
        while ( cursor != null)
        {
            if (cursor.data != null)
            {
                returnString += cursor.data.toString();
            }
            cursor = cursor.next;
        }

        this.cursor = anchor; // restores the cursor
        return returnString;
    }

    /*****************************************************************
     * The size() method returns a count of how many nodes/objects
     * are contained within the list.
     *****************************************************************/
    public int size()
    {
        int count = 0;
        Node anchor = cursor;
        cursor = top;
        while (cursor != null) {
        	
        	count++;
        	cursor = cursor.next;
        }
        cursor = anchor;
        return count;
    }

    /*****************************************************************
     * The cursorTo(int) method moves the cursor to the nth place
     * in the List, assuming n is a legitimate position in the list.
     * 
     * The Nodes are numbered conceptually as 0, 1, 2, etc. For a list
     * of strings 
     *                      "A","B","C", etc.
     * The following code                     
     *                  cursorTo( 0 );
     *                  System.out.println( cursor.data )
     * would print A, whereas
     * 
     *                  cursorTo( 1 );
     *                  System.out.println( cursor.data )
     * would print B.
     * 
     *****************************************************************/
    public boolean cursorTo(int n)
    {
        boolean success = false;

        if (0 <= n && n < this.size())
        {
            cursor = top;
            for (int count = 0; count < n; count++)
            {

            	cursor = cursor.next;
            }
            success = true;
        }

        return success;
    }

    /*****************************************************************
     * The add0(Object) method inserts a Node for the object at the
     * top/head of the list, at the logical 0th place.
     *****************************************************************/
    public void add0(Object obj)
    {
        top = new Node(obj, top);
        cursor = top;
    }

    /*****************************************************************
     * The add(int, Object) method inserts a Node for the object in
     * the specified nth place.
     *****************************************************************/
    public void add(int n, Object obj)
    {
        if (n == 0)
        {
            add0(obj);
        }
        else if (0 < n && n < this.size())
        {
            this.cursorTo(n);
            this.add(obj);
        }
        else if (n == this.size())
        {
            this.cursorTo(n-1);
            this.cursor.next = new Node(obj, this.cursor.next);
        }
    }

    /*****************************************************************
     * The add(Object) method inserts a Node for the element 
     * immediately after the Node referenced by cursor.
     *****************************************************************/
    public void add(Object obj)
    {
        if (top == cursor || top == null || cursor == null)
        {
            add0(obj);
        }
        else
        {
            this.cursor.next = new Node(obj, this.cursor.next);
        }
    }

    /*****************************************************************
     * The get(int) method returns the object 
     * 
     *                          cursor.data
     *                          
     * from the Node, at the nth place in the list.
     *****************************************************************/
    public Object get(int n)
    {
        try
        {
            if (!cursorTo(n))
            {
                throw new IllegalArgumentException( "List.get(int) : " + n + " is an index out of bounds");
            }
            
            if (cursorTo(n) == true) {
            
            	return cursor.data;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return null;
    }

    /*****************************************************************
     * The append(Object) method appends a Node for the obj at the end
     * of the list.
     *****************************************************************/
    public void append(Object obj)
    {
        add(size(), obj);
    }

    /*****************************************************************
     * The set(Object) replaces the data object, obj, in the current
     * node.
     *****************************************************************/
    public void set(Object obj)
    {
        if (cursor != null)
        {
            cursor.data = obj;

        }
    }

    /*******************************************************************
     * Assuming n is a legitimate list position, this set(int, Object)
     * method moves the cursor to the specified nth place, before
     * invoking the set(Object) method to complete the replacement.
     *****************************************************************/
    public void set(int index, Object obj)
    {
        if (0 <= index && index < this.size())
        {
            if (cursorTo(index) == true) {
            set(obj);	
            }
        }
    }

    /*****************************************************************
     * The indexOf( Object ) method returns the logical index of obj in
     * the list for a first occurrence; or -1 if obj is not contained
     * in the list.
     * 
     *****************************************************************/
    public int indexOf(Object obj)
    {
        int index = 0;

        Node anchor = this.cursor; // saves the cursor

        cursor = top;
        while (cursor != null)
        {

            if (get(index) == obj) {
            	return index;
            }
            cursor = cursor.next;
            index++;
        }

        this.cursor = anchor; // restores the cursor
        return -1;
    }

    /************************************************************************
     * The sublist(int, int) method returns a sublist of objects contained
     * in this list. The objects in the returned sublist are still shared by
     * this list.
     ************************************************************************/
    public List sublist(int start, int stop)
    {
        List newList = new List();

        try
        {
            if (start < 0 )
            {
                throw new IllegalArgumentException( "sublist:  " + start + " is an index out of bounds");
            }
            if ( stop > size())
            {
                throw new IllegalArgumentException( "sublist:  " + stop + " is an index out of bounds");
            }
            if (start > stop)
            {
                throw new Exception("sublist: " + start + " > " + stop );
            }
            while (start <= stop)
            {

                newList.append(get(start));
                start++;

            }            
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.toString());
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }

        return newList;
    }

    /*****************************************************************
     * The swaps(int, int) swaps two object in the list, the object at
     * index j with the object at index k.
     *****************************************************************/
    public void swap(int j, int k)
    {
        try
        {
            if (j < 0 || j > size()-1 )
            {
                throw new IllegalArgumentException( "swap:  " + j + " is an index out of bounds");
            }
            if (k < 0 || k > size()-1)
            {
                throw new IllegalArgumentException( "swap:  " + k + " is an index out of bounds");
            }

            Object tempObject = get(k);
            set(k, get(j));
            set(j, tempObject);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    /*****************************************************************
     * Assuming the the List not empty, the remove0 method returns the
     * value removed from
     *                         top.data,
     * after deleting the node and relinking the list without it.
     * 
     * @return
     * @throws Exception
     *****************************************************************/
    public Object remove0()
    {
        Object obj = null;

        if (top != null)
        {

            obj = top.data;
            top = top.next;
            cursor = top;
            
        }

        return obj;
    }

    /*****************************************************************
     * The remove method returns the value removed from
     *                         curror.next.data,
     * after deleting the node and relinking the list to excluding
     * the node that contained the data. 
     * 
     * @return
     * @throws Exception
     *****************************************************************/
    public Object remove()
    {
        Object obj = null;

        if (cursor != null)
        {
            obj = cursor.next.data;
            cursor.next = cursor.next.next;
        }
        return obj;
    }

    /*******************************************************************
     * The remove(int) method first moves the cursor to the logical nth
     * place in the list, and then calls the remove method to remove and
     * return the object.
     * 
     * Note that n == 0 is a special case.
     * 
     * @return
     * @throws Exception
     *******************************************************************/
    public Object remove(int n)
    {
        Object obj = null;

        try
        {
            if (n < 0 || n >= size())
            {
                throw new IllegalArgumentException( "remove:  " + n + " is an index out of bounds");
            }

            if (n == 0)
            {
                
            return remove0();
            
            }
            else {
                
            if (cursorTo(n - 1) == true) {
            	obj = remove();
            }

            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return obj;    }

    /********************************************************************
     * The display(int, int) method returns a string for display for the 
     * objects in the list positioned between start and stop, inclusively.
     ********************************************************************/
    public String display(int start, int stop)
    {
        Node anchor = this.cursor; // saves the cursor

        String returnString;

        try
        {
            if (start < 0 || start > size()-1 )
            {
                throw new IllegalArgumentException( "display: " + start + " is an index out of bounds");
            }
            if (stop < 0 || stop > size()-1)
            {
                throw new IllegalArgumentException( "display:  " + stop + " is an index out of bounds");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }

        returnString = "";
        while (start <= stop)
        {
            returnString += this.get( start ) + " ";
            start++;
        }

        this.cursor = anchor; // restores the cursor
        return returnString;
    }
    
    /********************************************************************
     * The display() method returns a string for display for all of
     * the objects in the list.
     ********************************************************************/
    public String display( )
    {
        return display( 0, size()-1);
    }
    
    /********************************************************************
     * Alternative display definition.
     * 
     * The display() method returns a string for display for all of
     * the objects in the list.
     ********************************************************************/
//     public String display()
//     {
//         Node anchor = this.cursor; // saves the cursor
// 
//         String returnString = "";
// 
//         cursor = top;
//         while (cursor != null)
//         {
//             returnString += cursor.data + " ";
//             cursor = cursor.next;
//         }
// 
//         this.cursor = anchor; // restores the cursor
//         return returnString;
//     }
}
