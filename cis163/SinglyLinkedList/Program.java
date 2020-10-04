class Program
{
    public static void main(String[] args)
    {
        List list0 = new List();
        System.out.println("list0.add(33)");
        System.out.println("list0.add(22)");
        System.out.println("list0.add(null)");
        System.out.println("list0.add(11)");
        System.out.println("list0.add(00)");
        list0.add(33);
        list0.add(22);
        list0.add(null);
        list0.add(11);
        list0.add(00);
        System.out.println("\tList:  " + list0.display(0, list0.size()-1));

        System.out.println( );
        System.out.println("<<< NEW LIST >>>");
        System.out.println("\n\t --- testing add ---");
        System.out.println("list.add(0, \"zero\")");
        System.out.println("list.add(1, \"one\")");
        System.out.println(". . .");
        list0.add(22);
        List list = new List();
        list.add(0, "zero");
        list.add(1, "one");
        list.add(2, "two");
        list.add(3, "three");
        list.add(4, "four");
        list.add(5, "five");
        list.add(6, "six");
        list.add(7, "seven");
        //                       list.add(null);
        System.out.println("\tList:  " + list.display(0, list.size() - 1));

        System.out.println();
        System.out.println("\n\t --- testing get ---");
        System.out.println("list.size() = " + list.size());

        for (int n = 0; n < list.size(); n++)
        {
            System.out.println("list.get( " + n + " ) = " + list.get(n));
        }

        System.out.print("\n list.get(list.size()) = " );
        System.out.println( list.get(list.size()));

        System.out.println();
        System.out.println("\n\t --- testing remove ---");
        System.out.println("List:  " + list.display(0, list.size()-1));
        System.out.println(" list.remove( 7 ) = " + list.remove( 7 ));
        System.out.println(" list.remove( 4 ) = " + list.remove( 4 ));
        System.out.println(" list.remove( 0 ) = " + list.remove( 0 ));

        System.out.println("\tList:  " + list.display(0, list.size()-1));

        System.out.println("\n\t --- testing swap ---");
        System.out.println(" list.swap( 0, list.size()-1 )" );

        list.swap( 0, list.size()-1 );
        System.out.println("\tList:  " + list.display(0, list.size()-1));

        System.out.println("\n\t --- testing swap with index out of bounds ---");
        System.out.println(" list.swap( 0, list.size() )" );
        list.swap( 0, list.size() );
        System.out.println("\tList:  " + list.display(0, list.size()-1));

        System.out.println("\n\t --- testing indexOf ---");

        System.out.print("list.indexOf(\"zero\" )\t = " );
        System.out.println(list.indexOf("zero" ) );
        System.out.print("list.indexOf(\"one\")\t = ");
        System.out.println(list.indexOf("one") );
        System.out.print(" list.indexOf(\"two\")\t = ");
        System.out.println( list.indexOf("two") );

    }
}