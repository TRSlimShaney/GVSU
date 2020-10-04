
-- File: companyDML-b-solution  
-- SQL/DML HOMEWORK (on the COMPANY database)
/*
Every query is worth 2 point. There is no partial credit for a
partially working query - think of this hwk as a large program and each query is a small part of the program.
--
IMPORTANT SPECIFICATIONS
--
(A)
-- Download the script file company.sql and use it to create your COMPANY database.
-- Dowlnoad the file companyDBinstance.pdf; it is provided for your convenience when checking the results of your queries.
(B)
Implement the queries below by ***editing this file*** to include
your name and your SQL code in the indicated places.   
--
(C)
IMPORTANT:
-- Don't use views
-- Don't use inline queries in the FROM clause - see our class notes.
--
(D)
After you have written the SQL code in the appropriate places:
** Run this file (from the command line in sqlplus).
** Print the resulting spooled file (companyDML-b.out) and submit the printout in class on the due date.
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL companyDML-b.out
SET ECHO ON
-- ------------------------------------------------------------
-- 
-- Name: Shane Stacy
--
-- -------------------------------------------------------------
--
-- NULL AND SUBSTRINGS -------------------------------
--
/*(10B)
Find the ssn and last name of every employee whose ssn contains two consecutive 8's, and has a supervisor. Sort the results by ssn.
*/
SELECT DISTINCT E.ssn, E.lname
FROM employee E
WHERE E.ssn LIKE '%88%' AND E.supervisor IS NOT NULL;
--
-- JOINING 3 TABLES ------------------------------
-- 
/*(11B)
For every employee who works for more than 20 hours on any project that is controlled by the research department: Find the ssn, project number,  and number of hours. Sort the results by ssn.
*/
SELECT DISTINCT E.ssn, P.pnumber, WO.hours
FROM employee E, works_on WO, project P
WHERE E.ssn = WO.essn AND WO.hours > 20 AND P.dnum = 5;
--
-- JOINING 3 TABLES ---------------------------
--
/*(12B)
Write a query that consists of one block only.
For every employee who works less than 10 hours on any project that is controlled by the department he works for: Find the employee's lname, his department number, project number, the number of the department controlling it, and the number of hours he works on that project. Sort the results by lname.
*/
SELECT DISTINCT E.lname, E.dno, WO.pno, P.dnum, WO.hours
FROM employee E, works_on WO, project P
WHERE E.ssn = WO.essn AND WO.hours < 10 AND P.dnum = E.dno;
--
-- JOINING 4 TABLES -------------------------
--
/*(13B)
For every employee who works on any project that is located in Houston: Find the employees ssn and lname, and the names of his/her dependent(s) and their relationship(s) to the employee. Notice that there will be one row per qualyfing dependent. Sort the results by employee lname.
*/
SELECT DISTINCT E.ssn, E.lname, DEP.dependent_name, DEP.relationship
FROM employee E, works_on WO, project P, dependent DEP
WHERE E.ssn = WO.essn AND WO.pno = P.pnumber AND P.plocation = 'Houston';
--
-- SELF JOIN -------------------------------------------
-- 
/*(14B)
Write a query that consists of one block only.
For every employee who works for a department that is different from his supervisor's department: Find his ssn, lname, department number; and his supervisor's ssn, lname, and department number. Sort the results by ssn.  
*/
SELECT DISTINCT E1.ssn, E1.lname, E1.dno, E1.super_ssn, E2.ssn, E2.lname, E2.dno
FROM employee E1, employee E2
WHERE E1.super_ssn = E2.ssn AND E1.dno != E2.dno;
--
-- USING MORE THAN ONE RANGE VARIABLE ON ONE TABLE -------------------
--
/*(15B)
Find pairs of employee lname's such that the two employees in the pair work on the same project for the same number of hours. List every pair once only. Sort the result by the lname in the left column in the result. 
*/
SELECT DISTINCT E1.lname, E2.lname
FROM employee E1, employee E2, works_on WO1, works_on WO2
WHERE E1.ssn = WO1.essn AND E2.ssn = WO2.ssn AND WO1.pno = W02.pno;
--
/*(16B)
For every employee who has more than one dependent: Find the ssn, lname, and number of dependents. Sort the result by lname
*/
SELECT E.lname, E.ssn, COUNT(*)
FROM employee E, dependent DEP
WHERE E.ssn = DEP.essn
GROUP BY E.lname, E.ssn
-- 
/*(17B)
For every project that has more than 2 employees working on and the total hours worked on it is less than 40: Find the project number, project name, number of employees working on it, and the total number of hours worked by all employees on that project. Sort the results by project number.
*/
SELECT P.pno, P.pname, SUM(WO.hours)
FROM project P, works_on WO
WHERE (COUNT(P.pno) > 2) AND (SUM(WO.hours) < 40)
GROUP BY P.pno, P.pname, SUM(WO.hours)
--
-- CORRELATED SUBQUERY --------------------------------
--
/*(18B)
For every employee whose salary is above the average salary in his department: Find the dno, ssn, lname, and salary. Sort the results by department number.
*/
SELECT E.dno, E.ssn, E.lname, E.salary
FROM employee E
WHERE (E.salary > AVG(E.salary))
GROUP E.dno, E.ssn, E.lname, E.salary
--
-- CORRELATED SUBQUERY -------------------------------
--
/*(19B)
For every employee who works for the research department but does not work on any one project for more than 20 hours: Find the ssn and lname. Sort the results by lname
*/
SELECT E.ssn, E.lame
FROM employee E
WHERE E.dno = 5
MINUS (SELECT E.ssn E.lame
        FROM employee E, works_on WO
        WHERE E.ssn = WO.essn, SUM(WO.hours) <= 20)
--
-- DIVISION ---------------------------------------------
--
/*(20B) Hint: This is a DIVISION query
For every employee who works on every project that is controlled by department 4: Find the ssn and lname. Sort the results by lname
*/
SELECT E.ssn, E.lname
FROM employee E
WHERE NOT EXISTS((SELECT WO.essn
                  FROM works_on WO
                  WHERE WO.pno = 4)
                MINUS
                (SELECT  WO.essn
                 FROM works_on WO, project P
                 WHERE E.ssn = WO.essn AND WO.pno = P.pnumber AND WO.pno = 4)); 
--
SET ECHO OFF
SPOOL OFF


