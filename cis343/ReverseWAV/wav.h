/*****************************************************************
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
******************************************************************/

#ifndef CIS343_REVERSEWAV_WAV_H
#define CIS343_REVERSEWAV_WAV_H

#endif //CIS343_REVERSEWAV_WAV_H

/** wav_file struct */
typedef struct wav_file {
    char riff[4];
    int chunkSize;
    char WAVE[4];
    char fmt[4];
    int formatLength;
    short formatType;
    short numberChannels;
    int sampleRate;
    int bitsPerSample;
    short blockAlignment;
    short bitDepth;
    char dataHeader[4];
    int dataSize;
    char* data;
} wav_file;

/*********************************************************
 * Parses the buffer into the wav_file struct
 * @param contents the bytes of the buffer
 * @return pointer to wav_file struct
 ********************************************************/
wav_file* parse(char* contents);