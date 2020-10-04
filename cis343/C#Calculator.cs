using System;
using System.Collections.Generic;
using System.Globalization;

/**********
* @author Shane Stacy
*
* Calculator is an order-of-operations supporting calculator.
* The program looks for specific operations and evaluates the operation looking for operands on the left and right.
* If no operand is found on the left, the program assumes you are continuing from your previous total.
* 
************/


namespace Calculator {
    class Program {

        List<string> tokens;
        double total;

        Program() {
            tokens = new List<string>();
            total = 0;
        }

        string parse() {
            //  read the line into an array of string
            var line = Console.ReadLine();
            //  add it to a list
            tokens = new List<string>(line.Split(' '));
            return tokens[0];
        }

        string iterate() {
            string[] ops = {"^", "*", "/", "+", "-"};
            int op = 0;
            double a = 0;
            double b = 0;
            double num = 0;

            //  while there are still more operations to do
            while (op < ops.Length) {
                //  if there is only one token left, check if it is a double
                //  if it is, that is the resultand the new total, otherwise error
                if (tokens.Count == 1) {
                     bool valid = double.TryParse(tokens[0], out a);
                     if (!valid) {
                         return "Syntax Error!";
                     }
                     total = a;
                     return a.ToString();
                }

                //  find the operator
                int index = tokens.FindIndex(token => token == ops[op]);
                //  if we don't, go to the next operator
                //  if we do and it's the only token, error
                //  if we do and it's the last token, error
                if (index == -1) {
                    op++;
                    continue;
                }
                else if (index == 0 && tokens.Count == 1) {
                    return "Syntax Error!";
                }
                else if (index == tokens.Count - 1) {
                    return "Syntax Error!";
                }

                //  if the index of the operator is 0 and there are at least 2 tokens left
                if (index == 0 && tokens.Count > 1) {
                    //  term a is total
                    a = total;
                    //  check if the next term is a double
                    bool valid = double.TryParse(tokens[index + 1], out b);
                    //  if it's not, return error
                    if (!valid) {
                        return "Syntax Error!";
                    }
                }
                else if (index > 0) {
                    //  if the index of the operator is greater than 0, try to parse the term before as a double
                    bool valid = double.TryParse(tokens[index - 1], out a);
                    //  if we can't, error
                    if (!valid) {
                        return "Syntax Error!";
                    }
                    //  try to parse the term after the operator as a double
                    valid = double.TryParse(tokens[index + 1], out b);
                    //  if we can't, error
                    if (!valid) {
                        return "Syntax Error!";
                    }
                }

                //  check if the operation is divide and if b is 0
                //  if it is, then we need to prevent DBZ error
                if (op == 2 && b == 0) {
                    return "Divide by Zero Error!";
                }

                //  calculate the operation
                num = calculate(a, tokens[index], b);

                //  pop the terms
                //  if the operator is at 0 and there are more than one terms, remove the first two terms
                //  if the operator index is greater than zero, remove the operator itself and one additional term from each side
                if (index == 0 && tokens.Count > 1) {
                    tokens.RemoveRange(index, 2);
                }
                else if (index > 0) {
                    tokens.RemoveRange(index - 1, 3);
                }

                //  insert the result back into the list for further evaluation
                //  if the list is empty after popping, put the result at index 0
                //  if the list is not empty, splice it in where the operator used to be
                if (tokens.Count == 0) {
                    tokens.Add(num.ToString());
                }
                else if (index == 0) {
                    tokens.Insert(index, num.ToString());
                }
                else {
                    tokens.Insert(index - 1, num.ToString());
                }
            }
            //  return the total
            return total.ToString();
        }

        double calculate(double a, string operation, double b) {
            if (operation.Equals("^")) {
                return Math.Pow(a, b);
            }
            else if (operation.Equals("*")) {
                return a * b;
            }
            else if (operation.Equals("/")) {
                return a / b;
            }
            else if (operation.Equals("+")) {
                return a + b;
            }
            else if (operation.Equals("-")) {
                return a - b;
            }
            return 0;
        }




        static void Main(string[] args) {
            Console.WriteLine("C# Calculator by Shane Stacy");
            Console.WriteLine("Written for .NET Core 3.1");
            Console.WriteLine("Seperate your operands and operators with spaces.");
            Console.WriteLine("Writing an operator first will assume basing operation on previous total.");
            Console.WriteLine("Type 'quit' to quit.");
            var calc = new Program();
            string result;
            while (true) {
                string valid = calc.parse();
                if (valid.Equals(null)) {
                    continue;
                }
                else if (valid.Equals("quit")) {
                    return;
                }
                else {
                    result = calc.iterate();
                }
                Console.WriteLine(result);
                
            }
        }
    }
}
