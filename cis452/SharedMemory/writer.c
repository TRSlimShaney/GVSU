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
                IPC_CREAT | S_IRUSR | S_IWUSR)) < 0) {
        perror ("Shared memory location not found or creation failed\n");
        exit (1);
    }
  
    // Attach this process to the shared memory location
    if ((data = (struct shared*) shmat (shmId, (void*) 0, 0)) == (void *) -1) {
        perror ("Attach failed\n");
        exit (1);
    }
	data->beenRead = true;
    
	char buffer[50];
    // Loop to read user input, wasting time if the last print hasn't been read by both yet
    while (true) {

		if (!data->beenRead) {
			continue;
		}

		printf("Please input the desired message:\n");
		fflush(stdout);
		fflush(stdin);
		fgets(buffer, sizeof buffer, stdin);
		
		strcpy(data->buffer, buffer);
		if (strcmp(buffer, "quit\n") == 0) {
			data->beenRead = false;
			break;
		}
		strcpy(data->buffer, buffer);
		data->beenRead = false;
	}

	printf("Now detaching and deallocating shared memory...\n");

	// Detach from shared memory
	if (shmdt (data) < 0) {
        perror ("Detach from shared memory failed\n");
        exit (1);
    }
	// Deallocate the shared memory
    if (shmctl (shmId, IPC_RMID, 0) < 0) {
        perror ("Deallocation FAILED\n");
        exit (1);
    }
	printf("Detach and deallocation successful.\n");
	printf("Now terminating...\n");

    return 0;
}
