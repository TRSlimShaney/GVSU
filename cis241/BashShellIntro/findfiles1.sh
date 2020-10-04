# find all the files according to given search key

echo
if test $# -eq 0
then
   echo "usage: findfiles searchkey [dictionary]"
   echo
   exit 1
fi

if test $# -eq 2
then
   num=$(ls -l $2 | grep -ic $1)
else 
   num=$(ls -l | grep -ic $1)
fi   

echo "$num files"
echo

