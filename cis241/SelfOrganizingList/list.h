/*
Author:         Shane Stacy
Description:    Contains type definitions and function prototypes for a self-organizing list.
                Only the interfaces for proj2.c are exposed here.
*/

typedef struct Node* LINK;

LINK makeEmpty();
int isEmpty(LINK head);
int insertFirst(char d[], int num, LINK head);
int sortTheList(LINK unsorted, LINK sorted);
void clear(LINK head);
void showList(LINK head, FILE *output);

