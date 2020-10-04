
while read course proj
do
    echo $course $proj 
    if [ ! -d "$course" ]
    then
        mkdir $course
    fi
touch $course/$proj
done < lab8.dat

currTime="date +%r"
echo "Current time is $($currTime)"

