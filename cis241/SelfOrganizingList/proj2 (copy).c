/*
Author:         Shane Stacy
Description:    Contains main() and other introduced functions.
                This program uses two singly linked lists to sort identifiers from a C source file.
                The first list will contain unsorted identifiers.
                The second one will contain the sorted identifiers, with the highest occurring
                being towards the front of the list.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "list.h"

static FILE *input;
static FILE *output;
static LINK unsortedList;
static LINK sortedList;

void initializeFiles(char input1[], char output1[]);
void closeFiles();

int main(int argc, char* argv[]) {
	
	//  int argc is an int
	//  argv[0] is the program
	//  argv[1] is the first parameter

    unsortedList = makeEmpty();
    sortedList = makeEmpty();

    char *theCharacter;
    char *otherWord;
    char unfilteredString[200];
    int z;

    printf("Opening file streams...\n");
    initializeFiles(argv[1], argv[2]);

    while ((theCharacter = (char)fgetc(input)) != EOF) {

        if (theCharacter[0] == '//') {
            
            if (getc(input) == '//'
        }
        
        
        printf("Got the first word!  Inserting into unsorted list...\n");
        while (theWord != NULL) {  //  while the next word isn't null

            for (z = 0; theWord[z]; z++)  //  convert the word to lowercase
                theWord[z] = tolower(theWord[z]);
            printf("Converted to lowercase!\n");
            
            if (isdigit(theWord[0])) {  //  if the first character of theWord is a number, move on to next word;
                printf("First character was a digit.  Word skipped...\n");
                theWord = strtok(NULL, "><)(;=//\\\"\n-#}{!][*,.+ ");
                continue;
            }
            else {
                insertFirst(theWord, 1, unsortedList);
                printf("%s inserted!\n", theWord);
                theWord = strtok(NULL, "><)(;=//\\\"\n-#}{!][*,.+ ");  //  get the next word
                printf("Got the next word! %s\n", theWord);
            }            
        }
    }

    //  the unsorted list is now full of identifiers that need to be sorted
    printf("Starting to sort the unsorted list into the sorted list!\n");
    sortTheList(unsortedList, sortedList);  //  sort the unsorted list into a sorted list
    printf("\n\nPrinting the sorted list!\n");
    showList(sortedList, output);  //  show it and print it to a file

    printf("Closing file streams!\n");
    closeFiles();  //  close file streams
    printf("All done!\n");
    return 0;	//  return success
}



//  defines the file streams
void initializeFiles(char input1[], char output1[]) {

	input = fopen(input1, "r");  //  open the input file
	output = fopen(output1, "w");  //  open the output file
}

//  closes file streams
void closeFiles() {

	fclose(input);
	fclose(output);
}
