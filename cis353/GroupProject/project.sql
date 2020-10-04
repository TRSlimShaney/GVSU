SPOOL project.out
SET ECHO ON
/*
CIS 353 - Database Design Project
Shane Stacy
Kyler Kupres
*/
--
-- Drop the tables (in case they already exist)
--
DROP TABLE librarian CASCADE CONSTRAINTS;
DROP TABLE shelf CASCADE CONSTRAINTS;
DROP TABLE book CASCADE CONSTRAINTS;
DROP TABLE borrower CASCADE CONSTRAINTS;
DROP TABLE transact CASCADE CONSTRAINTS;
DROP TABLE author CASCADE CONSTRAINTS;
DROP TABLE feeHistory CASCADE CONSTRAINTS;
--
-- Create the tables
--
CREATE TABLE librarian (
	ssn			char(9) PRIMARY KEY,
	ID			varchar2(20),
	name		varchar2(30),
	rating		number(10)
);
--
CREATE TABLE shelf (
	capacity	number(100),
	shelfID		varchar2(15),
	floorNumber	number(4)
);
--
CREATE TABLE book (
	pages		number(1000),
	ISBN		varchar2(15),
	condition	number(10),
	title		varchar2(50),
	bookID		varchar2(20),
	genre		varchar2(15),
	coverType	varchar2(5)
);
--
CREATE TABLE borrower (
	name		varchar2(30),
	ID			varchar2(20),
	dueFees		decimal(19, 4),
	rating		number(10)
);
--
CREATE TABLE transact (
	librarianID	varchar2(20),	
	dateCheck	datetime,
	borrowCond	number(10),
	returnCond	number(10),
	dateReturn	datetime,
	damageFee	decimal(19, 4),
	lateFee		decimal(19, 4),
	bookID		varchar2(20),
	borrowerID	varchar2(20)
);
--
CREATE TABLE author (
	name        varchar2(30)
);
--
CREATE TABLE feeHistory (
    fee         decimal(19, 4)
);
--
-- Add the foreign keys:
--
ALTER TABLE transact
ADD FOREIGN KEY (librarianID) REFERENCES librarian(ID)
DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE transact
ADD FOREIGN KEY (bookID) REFERENCES book(bookID)
DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE transact
ADD FOREIGN KEY (borrowerID) REFERENCES borrower(ID)
DEFERRABLE INITIALLY DEFERRED;
--
SET FEEDBACK OFF
--
-- Populate the database
--
INSERT INTO librarian VALUES(123456789, '1', 'Bob', 6);
INSERT INTO librarian VALUES(223456789, '2', 'Jill', 8);
INSERT INTO librarian VALUES(323456789, '3', 'John', 7);
--
INSERT INTO shelf VALUES(50, '1', 1);
--
INSERT INTO book VALUES(309, '9780590353427', 6, 'Harry Potter and the Sorcerer\'s Stone', '1', 'Fantasy', 'Soft');
--
INSERT INTO borrower VALUES ('Joe', '1', 0.00, 10);
INSERT INTO borrower VALUES ('Bill', '2', 1.00, 6);
INSERT INTO borrower VALUES ('Chad', '3', 8.00, 3);
--
INSERT INTO transact VALUES ('1', '2019-03-25 14:01:45', 10, 9, '2019-04-01 09:00:23', 0.00, 0.00, '1', '1');
--
--
-- 
SET FEEDBACK ON
COMMIT;
--
--
--
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
--
--
--
/*
< The insert/delete/update statements to test the enforcement of ICs >
Include the following items for every IC that you test (Important: see the next section titled
“Submit a final report” regarding which ICs to test).
 A comment line stating: Testing: < IC name>
 A SQL INSERT, DELETE, or UPDATE that will test the IC.
*/
--
--
--
COMMIT;
--
SPOOL OFF
