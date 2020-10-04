
Notice that the starter code also contains a video that demonstrates the behavior of the app.
The video is a *supplement* for the written directions below.  It does not replace them.  
(There may be details below that do  not appear in the video.)

Problem 1:  CSS

Edit `styling.html` to complete each of the styling tasks below using the simplest CSS rule possible.

  1. Add spacing of 5px between buttons
  2. Set the text of final summaries to be colored purple
  3. Make the text of the summaries inside the sections appear with a strikethrough.  (Hint:  use `text-decoration:   line-through`)

`problem1.png` shows the expect result.

Problem 2: JavaScript

This problem has several "levels".  Each completed level earns you more points. (Optionally, you can skip to the "Fast forward".)

Level 1:
  1.  Begin with `states.html` a web page listing all 50 U.S. states.
  2.  Write a JavaScript method to add a button to each `li` element.  
      * The button's text should be "Delete".
  3.  Add a click handler to this button that will delete the corresponding `li` element when clicked.
      * Or, if you don't want to delete the element, you can make a distinct change to the styling of the state name (strikethrough, red color, etc.)
  4.  Write a JavaScript method that will count the number remaining (and undecorated) states.
  5. Every time a delete button is pressed, update the `span` with id `count` so that the page shows the number of remaining states.

There are several hints at the bottom of `states.html`.

Level 2:
  1. Begin with `dynamicStates.html`.
  2. Write JavaScript to dynamically build a nicely-formatted list of states from the provided array of states. The list should be a single paragraph with commas between each state (and no extra trailing comma at the end.)

Level 3:
  1. Begin with `ajaxList.html`
  2. When the `Submit` button is pressed, use AJAX to load and display the requested list.
     * The JavaScript to detect the chosen list is already written for you.
     * The lists are online at `https://www.cis.gvsu.edu/~kurmasz/lists`.  The id in the `option`'s value gives you the name of the list.  For example, the id on the  "Shakespeare Plays" option is `plays`.  Therefore, you would issue a GET request to `https://www.cis.gvsu.edu/~kurmasz/lists/plays.json`
     * CORS is already set up on the server.
  3. The data identifies one item as `special`.  Give that item special stzyling of some kind.

Fast Forward:  You may optionally do the following *instead* of the three separate levels above:
  * Write a React app that will load a `json` file containing a list.  Display the list in a table where each row contains a list item and a "delete" button.  Clicking the delete button should remove the row.  At the end of the list, display the number of rows that remain.