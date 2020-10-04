#include <stdio.h>
#include <ctype.h>

int main () {
	char ch;

	printf ("Enter text (Ctrl-D to quit).\n");
	while ( ch = getchar(), ch != EOF ) {
		
		if (isupper(ch)) {
			printf("%d", ch - 'A');
		}
		else if (islower(ch)) {
			printf("%d", ch - 'a');
		}
		else {
			printf("%c", ch);
		}
	}

	return 0;

}