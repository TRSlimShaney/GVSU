#include <stdio.h>
#include "wav.h"
#include "file.h"
#include "stdlib.h"
#include <string.h>
/*******************************************
 * main.c
This file should take two parameters and then create a pointer to some memory that will hold chars.
If you are careful you can think of this area as a string.  You should not malloc any memory in
this file, as you don't know how long the wave file you are going to read is going to be.
It is up to the file library to figure that out.  You will then call the read_file function to
load the contents of the wave file into your memory.  Parse the file so that you can hold
information about the file in a wav_file struct.  Output file statistics, something like this:

File: StarWars60.wav

====================

- File size is 2646044.  Read 2646036 bytes.

- Format is "fmt " with format data length 16.

- Format type is WAVEfmt

- 1 channels with a sample rate of 22050.

- 44100 byte rate, 2 alignment, 16 bits per sample.

- Data is "data" and data size is 2646000

Then, the program will reverse the data section, write out to a file named in argv[2], and exit cleanly.
 Any errors will be reported appropriately.

 @author Shane Stacy
 @version For 2/3/2020
****************************************************/


/*********************************************************
 * Main execution
 * @param argc the number of arguments
 * @param argv the arguments
 ********************************************************/
int main(int argc, char **argv) {

    //  we need a buffer of bytes to store the data
    char* buffer;
    //  we need a pointer to store the location of the parsed file
    wav_file* theReadFile;

    //  check if input and output files were provided
    if (argc < 2) {
        printf("ERROR:  You need to specify a WAV file to reverse.  Exiting...\n");
        return 0;
    }
    else if (argc < 3) {
        printf("ERROR:  You need to specify an output file.  Exiting...\n");
        return 0;
    }


    //  Step 1.  Read the file from disk.  Returns size_t.
    printf("Reading the file...\n");
    int bufferSize = read_file(argv[1], &buffer);


    
    //  Step 2.  Parse the file into wav_file struct, reversing the data section.  Returns wav_file struct pointer
    printf("Parsing the file...\n");
    theReadFile = parse(buffer);

    if (theReadFile == NULL) {
        printf("ERROR:  NULL pointer returned!  Exiting...");
        free(theReadFile);
        return 1;
    }

    //  print the file statistics
    printf("File:  %s\n", argv[1]);
    printf("==========================\n");
    printf("- File size is %d bytes.  Read %d bytes.\n", bufferSize, theReadFile->chunkSize);
    printf("- Format is %s with format data length %d.\n", theReadFile->fmt, theReadFile->formatLength);
    printf("- Format type is %d.\n", theReadFile->formatType);
    printf("- %d channels with a sample rate of %d KHz.\n", theReadFile->numberChannels, theReadFile->sampleRate);
    printf("- %d bytes per second, %d alignment, %d bits per sample.\n", theReadFile->bitsPerSample, theReadFile->blockAlignment, theReadFile->bitDepth);
    printf("- Data is %s and data size is %d bytes.\n", theReadFile->dataHeader, theReadFile->dataSize);


    //  Step 3.  Reverse the data section of the buffer and write it to disk.
    //  Reversed Buffer of original buffer size
    char reversedBuffer[bufferSize];

    printf("Writing header data...\n");
    // copy header data from buffer to reversedBuffer
    memcpy(reversedBuffer, buffer, 44 * sizeof(char));

    printf("Writing reversed data section...\n");
    //  copy reversed data to reversedBuffer
    //  check to see what alignment we have and reverse accordingly
    //  we can reverse with 2 or 4 alignment
    if (theReadFile->blockAlignment == 2) {
        for (int i = 0; i < bufferSize - 44; i += 2) {
            reversedBuffer[44 + i] = buffer[bufferSize - 2 - i];
            reversedBuffer[45 + i] = buffer[bufferSize - 1 - i];
        }
    }
    else if (theReadFile->blockAlignment == 4) {
        for (int i = 0; i < bufferSize - 44; i += 4) {
            reversedBuffer[44 + i] = buffer[bufferSize - 4 - i];
            reversedBuffer[45 + i] = buffer[bufferSize - 3 - i];
            reversedBuffer[46 + i] = buffer[bufferSize - 2 - i];
            reversedBuffer[47 + i] = buffer[bufferSize - 1 - i];
        }
    }

    printf("Writing the reversed file to disk...\n");
    write_file(argv[2], reversedBuffer, bufferSize);
    printf("Done! File saved to %s.\n", argv[2]);
    //  free the buffer since we are done with it
    free(buffer);
    //  free the memory because we no longer need this structure
    free(theReadFile);
    //  Return Success
    return 0;
}
