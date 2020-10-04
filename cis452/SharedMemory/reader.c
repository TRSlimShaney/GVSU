#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <stdbool.h>
#include <string.h>



int main() {

    int key = ftok("/home/weaverad/CS452/lab5/writer.c", 123);

    struct shared {
        bool beenRead;
        char buffer[50];
    };

    int shmId;
    struct shared* data;

	size_t shmsize = sizeof(struct shared);

    // Create shared memory location
    if ((shmId =
        shmget (key, shmsize,
                S_IRUSR | S_IWUSR)) < 0) {
        perror ("Shared memory location not found\n");
        exit (1);
    }
  
    // Attach this process to the shared memory location
    if ((data = (struct shared*) shmat (shmId, (void*) 0, 0)) == (void *) -1) {
        perror ("Attach failed\n");
        exit (1);
    }

    // Loop reading from the shared memory, wasting time if there is no update to read.
    while(true) {
		if (data->beenRead) {
			continue;
		}
		char toPrint[50];

		strcpy(toPrint, data->buffer);
		if (strcmp(toPrint, "quit\n") == 0) {
			printf("Received quit command from buffer\n");
			printf("Now terminating...\n");
			break;
		}

    	// Get the written text from the shared memory location. DO NOT erase it!
		printf("%s", toPrint);
		data->beenRead = true;
	}

	printf("Now attempting to detach from shared memory...\n");
	//  Detach from shared memory. NOTE: The writer.c program handles deallocation
	if (shmdt(data) < 0) {
        perror ("Detach from shared memory failed\n");
        exit (1);
    }
	printf("Detach successful!\n");
	printf("Now terminating...\n");

    return 0;
}
