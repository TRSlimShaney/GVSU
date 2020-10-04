clear

if [ $# -ne 1 ]
then
    echo "Invalid number of arguments!  Acceptable arguments are input file."
    exit 1
fi

if [ ! -r $1 ]
then
    echo "Input file is not readable!"
    exit 1
fi

if [ ! -w $1 ]
then
    echo "Input file is not writable!"
    exit 1
fi

echo "Make a choice!"
echo "1)  Search the booklist for entries by author"
echo "2)  Add a new entry to the booklist"
echo "3)  Delete an entry from the booklist (using title as the key)"
echo "4)  Display all entries and the number of entries in the booklist"
echo "5)  Exit"

read choice

case $choice in
1) echo "Searching booklist by author..."
   clear
   echo "Enter the author to search for:"
   read author
   grep -F "$author" $1 | gawk -F: '{printf "%s\t%s\t%s\n", $1, $2, $3}'
   exit 0
   ;;
2) echo "Adding new entry to booklist..."
   clear
   echo "Make entry as follows:  Fname Lname:Title:year"
   read entry
   echo $entry >>$1
   exit 0
   ;;
3) echo "Deleting an entry from the booklist..."
   clear
   echo "Enter the title of the book you wish to delete:"
   read title
   sed -i "/$title/d" ./$1
   exit 0
   ;;
4) echo "Displaying all entries and the number of entries in the booklist..."
   clear
   sort -k2 $1 | gawk -F: '{printf "%s\t%s\t%s\n", $1, $2, $3}'
   numberOfLines=$(wc -l < $1)
   echo "$numberOfLines total entries returned!"
   exit 0
   ;;
5) echo "Exiting..."
   exit 0
   ;;
*) echo "Invalid choice entered!  Exiting..."
   exit 1
   ;;
esac
