
-- File: queryLibrary.sql  
-- Query the library.
/*
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL library.out
SET ECHO ON
--------------------------------------------------------------------------------------
--
-- Group Names: < ********** PLEASE ENTER GROUP NAMES HERE **************>
--
---------------------------------------------------------------------------------------
/*(1)
For every book in the library, find the book title, number of pages, and coverType of books that are of the 'Fantasy' genre, do not include duplicate books. Sort by book title.
*/
SELECT UNIQUE B.title, B.pages, B.coverType
FROM   book B
WHERE  B.genre = 'Fantasy'
ORDER BY B.title;
--
/*
For every 'Fiction' book in the library, list the book title, ISBN and bookID of all books that have pages less than 1000 by the author 'George R.R Martin'.
*/
SELECT B.title, B.ISBN, B.bookID
FROM book B, author A
WHERE B.pages < 1000 AND A.name = 'George R.R Martin';
-
/*
Find the librarian ssn, ID, name, and rating of all librarians who have a rating above or equal to 6 and have a name that starts with an A.
*/
SELECT L.ssn, L.ID, L.name, L.rating
FROM librarian L
WHERE L.rating >= 6 AND L.name LIKE 'A%'; 
-
/*
List the name, ID, and rating of all Borrower's that owe dueFees over $20. Order by name.
*/
SELECT B.name, B.ID, B.rating 
FROM borrower B
WHERE B.dueFees > 20 
ORDER BY B.name;
--
/*
For every book on a shelf on floor 2 that has genre type 'Non-Fiction': Find the title and ISBN. Sort the results by ISBN.
*/
SELECT B.title, B.ISBN
FROM book B, shelf S
WHERE B.genre = 'Non-Fiction' AND S.floorNumber = 2
ORDER BY B.ISBN;
--
/*
For every transation that was made by a librarian with: Find the librarianID, dateCheck, dateReturn, returnCond, and borrowerID for transactions where the borrowCond 
is > 9 and the borrower has more than one book checked out.
*/
SELECT T.librarianID, T.dateCheck, T.dateReturn, T.returnCond, T.borrowerID
FROM transact T
WHERE T.borrowCond > 9 AND (SELECT ... (Need to finish with a COUNT to get total amount of transactions?)
--
/*
Find all the transactions where the lateFee and damageFee are above $10
*/
SELECT B.name, B.ID, T.lateFee, T.damageFee
FROM borrower B, transact T
WHERE B.ID = T.borrowerID AND T.lateFee > 10 AND T.damageFee > 10;

SET ECHO OFF
SPOOL OFF