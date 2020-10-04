/*
Author:         Shane Stacy
Description:    This program interfaces with the terminfo database to gather terminal
                capabilities and allows the user to move the cursor.
*/

#include <curses.h>
#include <term.h>
#include <iostream>
#include <unistd.h>

int main() {

    //  1.  display the number of rows and columns currently supported by the terminal
    //  setup the terminal
    setupterm(nullptr, 1, nullptr);
    putp(tparm(tigetstr("clear")));
    //  get the number of supported lines for the terminal
    int numLines = tigetnum("lines");
    //  get the number of supported columns
    int numCol = tigetnum("cols");
    //  print the line and column capabilities
    putp(tparm("Number of lines: "));
    char str[5];
    sprintf(str, "%d", numLines);
    putp(tparm(str));
    putp(tparm("     "));
    putp(tparm("Number of columns: "));
    sprintf(str, "%d", numCol);
    putp(tparm(str));
    putp(tparm("\r"));
    putp(tparm("\n"));

    //  2.  sleep for 5 seconds
    sleep(5);

    //  3.  clear the screen and home the cursor
    putp(tparm(tigetstr("clear")));

    //  7.  repeat steps 4->6
    while(true) {
        //  4.  print a prompt
        putp(tparm("Enter the line and column to move the cursor?"));

        //  5.  accept user input
        int line;
        int col;
        std::cin >> line >> col;

        //  8.  terminate upon some special input (line or col < 0)
        if(line < 0 | col < 0) {
            break;
        }

        //  6.  position the cursor at the location in the terminal window specified by the user
        putp(tparm(tigetstr("clear")));
        putp(tparm(tigetstr("cup"), line, col));
    }
    //  close terminal
    putp(tparm(tigetstr("clear")));
    endwin();
    //  return success
    return 0;
}
