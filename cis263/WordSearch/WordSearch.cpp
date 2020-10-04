//
//  WordSearch.cpp
//  CS263-HW4
//

#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>  // needed for transform()
#include <exception>
#include <regex>
#include "WordSearch.h"

WordSearch::WordSearch() {
    /* default constructor requires no additional code */
}

WordSearch::WordSearch(const string& topdir, const string& ignore_file) {
    load_ignored_words(ignore_file);
    /* filter the directory only for files ending with "txt" */
    gvsu::FileSystem dir (topdir, regex{"txt$"});
    
    for (auto entry : dir) {
        cout << "Reading words from " << entry.second << endl;
        read_words (entry.first + "/" + entry.second);
    }
}

void WordSearch::load_ignored_words(const string& fname) {
    ifstream ign_file (fname);
    if (!ign_file.is_open()) {
        throw ios_base::failure {"Unable to load ignored.txt"};
        
    }
    string word;
    while (getline(ign_file, word))
        ignored_words.insert(word);
    ign_file.close();
}

void WordSearch::read_words(const string &file_name)
{
    /* a word is THREE OR MORE alphabetical characters (lowercase) */
    const regex word_re {"[a-z]{3,}"};
    
    /* Alternate declaration of the above regular expr
     const regex word_re {"[[:alpha:]]{3,}"};
     */
    ifstream txt (file_name); /* file is aumatically open */
    
    string one_line;
    string lastWord;
    int line = 1;
    string prev = "";
    while (getline(txt, one_line)) {
        /* change to lowercase */
        transform(one_line.begin(), one_line.end(), one_line.begin(), ::tolower);
        /* iterate over the string using a regular expression */
        auto re_begin = sregex_iterator {one_line.begin(),one_line.end(), word_re};
        auto re_end = sregex_iterator{};
        for (auto word = re_begin; word != re_end; ++word) {
            /* if the word is in the ignored list, don't print it */
            if (ignored_words.find(word->str()) == ignored_words.end())
            {
                /* TODO: REMOVE the following cout line */
                //cout << "Current word is " << word->str() << endl;
                /* TODO: use the current word to update your data structures */


                auto it = theReadSet_words.find(word->str());
                if (it == theReadSet_words.end()) {
                    theReadMapStringKey_words[word->str()] = 1;
                    theReadMapOccKey_words[1].insert(word->str());
                }
                else {
                        int count3 = theReadMapStringKey_words[word->str()];
                        theReadMapStringKey_words[word->str()] = count3 + 1;
                        theReadMapOccKey_words[count3].erase(word->str());
                        theReadMapOccKey_words[count3 + 1].insert(word->str());
                }

                theReadSet_words.insert(word->str());
                theReadVector_words.push_back(word->str());


                if (theReadVector_words.size() == 1) {
                    lastWord = word->str();
                }
                else if (theReadVector_words.size() > 1) {
                    theAfterVector_words[lastWord].push_back(word->str());
                    theAfterSet_words[lastWord].insert(word->str());
                    theAfterMap_words[lastWord][word->str()] = theAfterMap_words[lastWord][word->str()] + 1;
                    lastWord = word->str();
                }
            }
        }
        line++;
    }
    txt.close(); /* close the file */
}


unsigned long WordSearch::word_count() const {
    /* TODO complete this function */

    return theReadVector_words.size();
}

set<string> WordSearch::words_of_length (int L) const {
    /* TODO complete this function */

    set<string> theResults;

    auto it = theReadSet_words.begin();
    while (it != theReadSet_words.end()) {
        string word = *it;
        if (word.length() == L) {
            theResults.insert(*it);
        }
        it++;
    }

    return theResults;   /* return an empty set */
}

pair<unsigned int,set<string>> WordSearch::most_frequent_words() const
#ifndef _WIN32
  throw (length_error)
#endif
{
    if (theReadVector_words.size() == 0) {
        throw length_error("The document is empty.");
    }

    /* TODO complete this function */


    set<string> words;
    int currentCount = 0;
    pair<int, set<string>> results;
    auto it = theReadMapStringKey_words.begin();
    while (it != theReadMapStringKey_words.end()) {
        pair<string, int> temp = *it;
        if(temp.second > currentCount) {
            currentCount = temp.second;
            words.clear();
            words.insert(temp.first);
            results = make_pair(currentCount, words);
        }
        else if (temp.second == currentCount) {
            words.insert(temp.first);
            results = make_pair(currentCount, words);
        }
        it++;
    }

    return results;
}

set<string> WordSearch::least_frequent_words(int count2) const {

    if (theReadVector_words.size() == 0) {
        throw length_error("The document is empty.");
    }

    /* TODO complete this function */


    set<string> words;
    auto it = theReadMapStringKey_words.begin();
    while (it != theReadMapStringKey_words.end()) {
        pair<string, int> temp = *it;
        if(temp.second <= count2) {
            words.insert(temp.first);
        }
        it++;
    }

    return words;
}

string WordSearch::most_probable_word_after(const string& word) const {
    
    /* TODO complete this function */

    int count8 = 0;
    int maxValue = 0;
    pair <string, int> temp3;
    string temp2;
    string theWord;

    auto it = theReadSet_words.find(word);
    if (it != theReadSet_words.end()) {

        auto it3 = theAfterMap_words.at(word).begin();
            while (it3 != theAfterMap_words.at(word).end()) {
                temp3 = *it3;
                if (count8 < temp3.second) {
                    count8 = temp3.second;
                    theWord = temp3.first;
                }
                it3++;
            }
    }
    return theWord;
}
