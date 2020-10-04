#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

/******************************************************
// Created by shane on 1/18/20.
 file.h and file.c
These files will provide the file saving and loading functions.  They have the following signatures:

size_t read_file( char* filename, char **buffer );

size_t write_file( char* filename, char *buffer, size_t size);

Note that these signatures imply that you pass a filename and a buffer when reading a file.
Look closely and you will notice that the buffer is a double pointer in read file.
This is because the program that calls your read file function doesn't know how big the file is.
This implies that read_file must figure that out and allocate the proper amount of memory,
fill that memory with the contents of the file.  In the write file portion the memory is already setup.
We are just passing it a chunk of chars (bytes).  This part of the assignment tests if you can work
with pointers and understand context.

@author Shane Stacy
@version For 2/3/2020
******************************************************/






/***************************************************
 * Reads the file into the buffer
 * @param filename the name of the file
 * @param buffer the buffer of bytes
 * @return the size of the buffer
 **************************************************/
size_t read_file(char* filename, char **buffer) {
    //  open a file pointer to read the file
    FILE* file = fopen(filename, "rb");

    //  seek to the end of the file
    fseek(file, 0, SEEK_END);
    //  store the position of the pointer
    long int size = ftell(file);
    //  go back to the start of the file
    fseek(file, 0, SEEK_SET);

    //  allocate the memory using the size we determined
    *buffer = malloc(size * sizeof(char));
    if (!*buffer) {
        printf("The buffer could not be initially allocated!\n");
        return 0;
    }
    //  if end-of-file, break
    if (feof(file)) {
        printf("eof\n");
        return 0;
    }
    //  get the bytes into the buffer
    //buffer[pos] = (char) fgetc(file);
    fread(*buffer, sizeof(char), size, file);
    printf("bytes read\n");
    //  close the file stream
    fclose(file);
    printf("file closed\n");
    return size;
}

/***************************************************
 * Writes the reversed buffer to file on disk
 * @param filename the name of the file
 * @param buffer the buffer of bytes
 * @param size the size of the file to be written
 * @return the number of bytes written
 **************************************************/
size_t write_file(char* filename, char *buffer, size_t size) {
    //  open a file pointer to write the binary file
    FILE* file = fopen(filename, "wb");
    printf("Saving to disk...\n");
    //  until all bytes are written
    fwrite(buffer, sizeof(char), size, file);

    //  close the file stream
    fclose(file);
    return 0;
}