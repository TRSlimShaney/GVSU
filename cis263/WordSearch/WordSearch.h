//
//  WordSearch.h
//  CS263-HW4
//
//  Created by Hans Dulimarta
//

#ifndef __CS263_HW4__WordSearch__
#define __CS263_HW4__WordSearch__

#include <iostream>
#include <string>
#include <map>
#include <unordered_map>
#include <set>
#include <unordered_set>
#include <stdexcept>
#include "FileSystem.h"
using namespace std;

class WordSearch {
private:
    unordered_set<string> ignored_words;
    unordered_set<string> theReadSet_words;
    vector<string> theReadVector_words;
    map<int, set<string>> theReadMapOccKey_words;
    map<string, int> theReadMapStringKey_words;
    map<string, vector<string>> theAfterVector_words;
    map<string, set<string>> theAfterSet_words;
    map<string, map<string, int>> theAfterMap_words;
    /* TODO: declare additional data structures as needed */
    
    void read_words (const string& file_name);
    void load_ignored_words(const string& file_name);
public:
    WordSearch ();
    WordSearch (const string& topdir, const string& ignore_file);
    
    /* number of words in all the documents,
     * excluding ignored words.
     */
    unsigned long word_count() const;
    
    /* return a set of words of certain length, return an empty set
     when no words of the requested length */
    set<string> words_of_length (int L) const;
    
    /* the first of the pair is the number of occurrences,
     * the second of the pair is the word(s) that occur the most
     * the function throw a length_error exception when your data structure contains
     * no data.
     */
#ifndef _WIN32
    pair<unsigned int,set<string>> most_frequent_words() const throw(length_error);
#else
    pair<unsigned int,set<string>> most_frequent_words() const;
#endif
    /* return a set of words occurring less or equal to the given count */
    set<string> least_frequent_words (int count) const;
    
    /* return the most probable word after the given word */
    string most_probable_word_after (const string& word) const;
    
};
#endif /* defined(__CS263_HW4__WordSearch__) */
