#include "wav.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/*********************************************************
// Created by shane on 1/18/20.
wav.h and wav.c
This h file should define the struct "wav_file".  It will also have the signature for the
following function:

wav_file* parse(char* contents);

The c file will have the implementation of this function's code.  Notice that this function
returns a pointer to a wav_file struct.  The point of this file is to illustrate you can
create and work with types and understand the memory model.

@author Shane Stacy
@version For 2/3/2020
*********************************************************/







/*********************************************************
 * Parses the buffer into the wav_file struct
 * @param contents the bytes of the buffer
 * @return pointer to wav_file struct
 ********************************************************/
wav_file* parse(char* contents) {
    //  allocate memory for our struct
    wav_file* file = malloc(sizeof(wav_file));
    if (!file) {
        printf("ERROR:  Could not allocate memory to wav_file structure!  Exiting...\n");
        return NULL;
    }
    //  copy the bytes right over into the struct
    memcpy(file, contents, 44 * sizeof(char));
    //  tell the pointer where the data is
    file->data = &contents[44];
    return file;
}