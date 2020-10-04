/*
Author:         Shane Stacy
Description:    Contains function definitions for a self-organizing list.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


//  The first node is a header node that points (next) to the first ELEMENT node.
//  There's a few different ways to remove elements:
//  Doubley Linked Lists (->prev and ->next pointers)
//  Look-Ahead (->next->next)
//  Two Pointers (save into another pointer to be used later)

typedef struct Node* LINK;

int sizeOfList(LINK head);
int searchForWord(LINK head, char word[]);
int searchForCount(LINK head, int num);
int insertFirst(char d[], int num, LINK head);
int deleteFirst(LINK head);
int insertAtIndex(char d[], int num, int elem, LINK head);
int insertLast(char d[], int elem, LINK head);

struct Node {
	char elemString[25];
    int elemInt;
	LINK next;
	//LINK prev;  doubley linked list
};

//  make an empty list
LINK makeEmpty() {

	LINK head = malloc(sizeof(struct Node));
	head->next = NULL;
	return head;
}

//  is the list empty?
int isEmpty(LINK head) {

	if (head->next == NULL) {
		return 1;
	}
	return 0;
}

//  HELPER:  returns the current size of the list
int sizeOfList(LINK head) {

    LINK curr = head->next;
    int v = 0;

    while (curr != NULL) {
        v++;
        curr = curr->next;
    }
    return v;
}

//  search for the word and return the index
int searchForWord(LINK head, char word[]) {

    if (isEmpty(head)) {
	    return 0;
	}

    LINK curr = head->next;
    int i = 1;

    while(curr != NULL) {

        if (strcmp(word, curr->elemString) == 0) {
            return i;
        }
	    curr = curr->next;
        i++;
	}
    return 0;
}

//  HELPER:  search for the first word with the same count and return the index
int searchForCount(LINK head, int num) {

    if (isEmpty(head)) {
	    return 0;
	}

    LINK curr = head->next;
    int i = 1;

    while (curr != NULL) {

        if (curr->elemInt > num) {
            curr = curr->next;
        }
        else if (curr->elemInt < num) {
            return i - 1;
        }
        else {
            return i;
        }
        i++;
    }

    return -1;
}

//  HELPER:  insert a word at the front of the list
int insertFirst(char d[], int num, LINK head) {

	LINK ins = (LINK)malloc(sizeof(struct Node));
    LINK temp;

    strcpy(ins->elemString, d);
    ins->elemInt = num;
	temp = head->next;
	head->next = ins;
    ins->next = temp;
	return 1;
}
 //  HELPER:  delete the first element
int deleteFirst(LINK head) {

	LINK temp;

	if (isEmpty(head)) {
	    return 0;
	}
	
	temp = head->next;
	head->next = temp->next;
	free(temp);
	return 1;
}

//  HELPER:  insert a word at the index
int insertAtIndex(char d[], int num, int elem, LINK head) {
    
    if (isEmpty(head)) {  //  if the list is empty, insert at the front
	    insertFirst(d, elem, head);
        return 1;
	}

    if (num == 0) {  //  if the index is 0, insert at the front
	    insertFirst(d, elem, head);
        return 1;
	}

    if (sizeOfList(head) + 1 == num) {  //  if the index is 1 beyond the scope, insert at the end
        insertLast(d, elem, head);
        return 1;
    }

    if (sizeOfList(head) < num) {  //  if the index is still beyond the scope, return failure
        return 0;
    }

    int v = sizeOfList(head);
    int i = 1;
    LINK temp = head->next;
    LINK temp2;
    LINK ins = (LINK)malloc(sizeof(struct Node));

    while (i < num) {
        temp = temp->next;
        i++;
    }
    
    temp2 = temp->next;

    temp->next = ins;
    ins->next = temp2;
    ins->elemInt = elem;
    strcpy(ins->elemString, d);
    return 1;
}

//  insert a word at the end of the list
int insertLast(char d[], int elem, LINK head) {

    LINK curr = head;

	while(curr->next != NULL) {
	    curr = curr->next;
	}

    LINK temp = (LINK)malloc(sizeof(struct Node));
    strcpy(temp->elemString, d);
    temp->elemInt = elem;
    curr->next = temp;
    return 1;
}

//  sort the list
int sortTheList(LINK unsorted, LINK sorted) {

    int i = 0;
    LINK temp;
    LINK temp2;

    temp = unsorted->next;
    temp2 = unsorted->next;
    while (temp != NULL) {
        temp2 = temp;
        if (searchForWord(sorted, temp->elemString) == 0) {
            while (temp2 != NULL) {
                if (strcmp(temp2->elemString, temp->elemString) == 0) {
                    i++;
                    printf("Found %s %d times.\n", temp->elemString, i);
                }
                temp2 = temp2->next;
            }
            int newIndex = searchForCount(sorted, i);
            printf("New index returned %d\n", newIndex);
            if (newIndex == -1) {
                insertLast(temp->elemString, i, sorted);
                printf("Inserting %s at the end.\n", temp->elemString);
            }
            else {
                insertAtIndex(temp->elemString, newIndex, i, sorted);
                printf("Inserting %s at the index %d.\n", temp->elemString, newIndex);
            }
            i = 0;
        }
        else {
            printf("%s was already found in the sorted list.  Skipping...\n", temp->elemString);
        }
        printf("Moving on to next word.\n");
        temp = temp->next;
    }
    return 1;
}

//  clear the list
void clear(LINK head) {

	while (!isEmpty(head))
		deleteFirst(head);
}

//  print the list
void showList (LINK head, FILE *output) {

	LINK curr = head->next;
	while (curr != NULL) {
		printf("%s occurred %d times.\n", curr->elemString, curr->elemInt);
        fprintf(output, "%s occurred %d times.\n", curr->elemString, curr->elemInt);
		curr = curr->next;
	}

}

