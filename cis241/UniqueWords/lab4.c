#include <stdio.h>
#include <string.h>

#define MaxLen 20

int main ()
{
	char curr[MaxLen + 1];        // current string
	char prev[MaxLen + 1];        // previous string

	// set the first element of array prev to NULL (an empty string)
	prev[0] = '\0';

	// read a string into array curr from the keyboard,
	printf("Enter a String: \n");
	while (scanf("%s", curr) != EOF) {
		
	// display it if it is different from the previous one
	// copy the string in array curr into array prev
	// repeat the above steps until EOF is reached

		if (strcmp(prev, curr) != 0) {
			printf("%s", curr);
			printf("\n");
			strcpy(prev, curr);
		}
	}

	return 0;
}