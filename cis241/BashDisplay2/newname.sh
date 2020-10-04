if [ $# -ne 2 ]
then
    echo "Incorrect number of arguments!  Acceptable arguments are input file, and output file."
    exit 1
fi

if [ ! -r $1 ]
then
    echo "$1 is not readable!"
    exit 1
fi

x=1
y=empty
if [ -f $2 ]
then
    while [ -f "$2.$x" ]
    do
    x=$(($x+1))
    done
y="$2.$x"
mv $1 $y
exit 0
fi

mv $1 $2
