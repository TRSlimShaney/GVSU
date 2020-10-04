/******************************************************************************
* Author:  Shane Stacy, Adam Weaver
* Description:  A simple shell
*******************************************************************************/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/resource.h>

#define TOKEN_BUFFER_SIZE 64

//  Define the helper functions
char *readLine(void);
char **splitLine(char *line);
int launchProgram(char **args);



//  Run the simple terminal
int main() {

    // A String to store the input line
    char *line;

    // An array of strings for the tokenized input
    char **args;

    // The status of the program. Initially set to 1.
    int statusCode = 1;

    // The usage of the CPU
    struct rusage usage;
    struct timeval time;
    long switches;

    // Run the terminal until the user quits.
    while (statusCode == 1) {

        //  Prompt the user for input
        printf("SimpleShell_> ");

        //  read the input
        line = readLine();

        //  split the input into tokens
        args = splitLine(line);

        //  if command is quit, cleanup and break
        if (strcmp(args[0], "quit") == 0) {
            free(line);
            free(args);
            break;
        }
        //  otherwise, launch the executable with arguments
        statusCode = launchProgram(args);

        //  Reset for the next loop
        free(line);
        free(args);

        //  get the resource usage of all children
        getrusage(RUSAGE_CHILDREN, &usage);

        //  involuntary context switches
        switches = usage.ru_nivcsw;

        //  user CPU time used
        time = usage.ru_utime;

	// Print usage and time
        printf("Involuntary Context Switches:  %ld\n", switches);
        printf("User Time Used:  %ld seconds and %ld microseconds\n", time.tv_sec, time.tv_usec);
    }
    return EXIT_SUCCESS;
}

//  Read the line from the terminal and return it as a string.
char *readLine(void) {

    //  we need a place to store the line
    char *line = NULL;

    //  initialize the buffer size which is managed by getline
    size_t bufferSize = 0;

    //  store the line into line, with managed bufferSize, from stdin
    //  getline takes care of all the work for us
    getline(&line, &bufferSize, stdin);

    //  return the line
    return line;
}

/*  Split the provided string into an array of tokens.
*   Given a string containing 0 or more tokens separated by
*   " ", a new line, or a tab, separate the string into its
*   individual tokens, storing pointers to those tokens into
*   an array, then return that array. Though the array size
*   is initially limited, the array will be reallocated as
*   needed to allow for storing the needed number of tokens.
*/
char **splitLine(char *line) {

    //  initialize the position to 0
    int pos = 0;

    //  use the defined size
    int bufferSize = TOKEN_BUFFER_SIZE;

    //  create the token buffer, dynamically allocated on the heap
    char **tokens = malloc(bufferSize * sizeof(char*));

    //  token pointer
    char *token;

    //  tokens backup pointer for during reallocation
    char **tokensBackup;

    //  if initial allocation fails, exit the program
    if (!tokens) {
        printf("The memory could not be allocated.\n");
        exit(EXIT_FAILURE);
    }

    //  get the initial token, separated by spaces, newline, returns, tabs
    token = strtok(line, " \n\r\t\a");

    //  while there are more tokens
    while (token != NULL) {
        //  add the token to the array
        tokens[pos] = token;
        //  increment the position
        ++pos;

        //  if the buffer is full or too small, make it bigger
        if (bufferSize <= pos) {

            bufferSize += TOKEN_BUFFER_SIZE;

            //  make a backup in case reallocation fails
            tokensBackup = tokens;

            //  reallocate with a larger buffer
            tokens = realloc(tokens, bufferSize * sizeof(char*));

            //  check if allocation was successful, if not then exit
            if (!tokens) {
                free(tokensBackup);
                printf("The memory could not be allocated.\n");
                exit(EXIT_FAILURE);
            }
        }

        //  get the next token, separated by spaces, newline, returns, tabs
        token = strtok(NULL, " \n\r\t\a");
    }

    //  cap the tokens with a null and return them
    tokens[pos] = NULL;
    return tokens;
}

/*  Given an array of strings, launch a program. The first string
*   in the array is the program to be executed, while the other
*   string(s) are arguments to be provided for that program.
*   Generates an error if execution fails, and returns an integer value
*   of 1 if the program was successfully launched.
*/
int launchProgram(char **args) {

    //  process id
    pid_t pid;

    //  status code
    int statusCode;

    //  create a forked process, store the process id
    pid = fork();

    //  if the process is the child, attempt to execute the program
    if (pid == 0) {
        //  execute, if error exit
        if (execvp(args[0], args) < 0) {
            perror("Could not execute.");
        }
        exit(EXIT_FAILURE);
    } else if (pid < 0) {
        //  if forking error
        perror("Could not fork.");
    } else {
        //  while the child has not exited or signalled its status
        do {
            //  wait for the child to finish
            waitpid(pid, &statusCode, WUNTRACED);
        } while (!WIFSIGNALED(statusCode) && !WIFEXITED(statusCode));
        printf("Child Process Finished!\n");
    }
    return 1;
}
