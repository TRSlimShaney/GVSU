# Author:  Shane Stacy
# Description:  This script does stuff.

sort -t, -k2 lab9.dat >> lab9a

gawk -F, '{printf "%s\t%s\t%s\n", $3, $2, $1}' lab9a >> lab9b

sed -n -e s/"Fall "/F/p -e s/"Winter "/W/p lab9b

path=courses/cs241/examples/demo.c
echo $path | gawk -F/ '{print $NF}'
