# Author:  Shane Stacy
# Description:  This script does stuff.

echo "Enter a message..."
read msg

echo "Original:  $msg"
echo "$msg" | tr 'A-Za-z' 'E-ZA-De-za-d' >> encMsg
echo "Encrypted:"
cat encMsg

cat encMsg | tr 'E-ZA-De-za-d' 'A-Za-z' >> decMsg
echo "Decrypted:"
cat decMsg
rm encMsg
rm decMsg

key1="hey"
key2="hey1"
grep -l "$key1" $(grep -l "$key2" *)
