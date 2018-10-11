import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Simple Stack Assignment
 * @author Hunter Dubbs
 * @version 10/11/2018
 * made for CIT360 at PCT
 *
 * This program consists of two algorithms that make use
 * of the stack data structure to process expressions read
 * from text files. The first algorithm verifies that an
 * expression has the correct syntax of parenthesis, brackets,
 * and braces. The second algorithm evaluates postfix expressions.
 */
public class StackProblemsDriver {

    /**
     * The main method of the program reads the data and calls
     * both algorithms, outputting their results to the console.
     * @param args the program arguments
     */
    public static void main(String[] args) {

        /*
         * Problem 1 - Well Formed Expressions
         *
         * This reads the file "problem1.txt" and interprets each
         * line as an expression. Each expression is than checked
         * to see if it is well formed and all the grouping characters
         * "(", ")", "[", "]", "{", "}" have matching open and closing
         * characters that are in the correct syntax. If it is in the
         * correct syntax, the program outputs "Correct", otherwise it
         * outputs "Incorrect".
         */
        System.out.println("====================================\n"
                + "Problem 1 - Well Formed Expressions\n"
                + "====================================");
        Stack p1Stack = new Stack<String>();
        //accesses the file
        Scanner sc = null;
        try {
            sc = new Scanner(new File("problem1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //reads each line, parses it, and passes it to the algorithm
        while(sc.hasNextLine()){
            if(verifyExpression(parseExpression(sc.nextLine()), p1Stack)){
                System.out.println("Correct");
            }else{
                System.out.println("Incorrect");
            }
        }
        sc.close();

        /*
         * Problem 2 - Expression Evaluator
         *
         * This reads the file "problem2.txt" and interprets each
         * line as an expression. Each expression is then evaluated
         * with the assumption that it is in a postfix notation. If
         * it is not in the correct notation, it will output "Ill-formed".
         * Otherwise, it will output the computed value.
         */
        System.out.println("\n====================================\n"
                + "Problem 2 - Expression Evaluator\n"
                + "====================================");
        Stack p2Stack = new Stack<String>();
        int result;
        //accesses the file
        try {
            sc = new Scanner(new File("problem2.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //reads each line, parses it, and then evaluates it
        while(sc.hasNextLine()){
            System.out.println(evaluateExpression(parseExpression(sc.nextLine()), p2Stack));
        }
        sc.close();


    }

    /**
     * This method takes an expression and breaks it down
     * into an array of Strings, each element of which contains
     * a single token from the expression.
     * @param str the String to be parsed
     * @return a String array of the tokens
     */
    private static String[] parseExpression(String str){
        String p1In = "";
        //A space is inserted between every character to ensure
        //that the tokens are correctly separated.
        for(int i = 0; i < str.length(); i++){
            p1In += str.charAt(i) + " ";
        }
        return p1In.split(" ");
    }

    /**
     * This method is used for problem 1. It takes the array of
     * tokens and pushes opening characters into a stack. Upon
     * finding a closing character, it attempts to match it to
     * the one on the top of the stack. If they all match up and
     * there are no extra opening characters left at the end, the
     * method returns true. It returns false at the first non-matched
     * character.
     * @param tokens the String array of tokens
     * @param stack the stack used to check the expression
     * @return whether or not the expression is correct
     */
    private static boolean verifyExpression(String[] tokens, Stack stack){
        //makes sure the stack is empty before starting
        stack.emptyList();
        for(String str : tokens){
            //opening characters are placed into the stack
            if(str.equals("(") || str.equals("[") || str.equals("{")){
                stack.push(str);
                //ignores everything else except for closing characters
            }else if(str.equals(")")){
                //if the matching opening character is present, remove it and continue
                if(!stack.isEmpty() && stack.peek().equals("(")){
                    stack.remove();
                }else{
                    //otherwise it is incorrect
                    return false;
                }
            }else if(str.equals("]")){
                if(!stack.isEmpty() && stack.peek().equals("[")){
                    stack.remove();
                }else{
                    return false;
                }
            }else if(str.equals("}")){
                if(!stack.isEmpty() && stack.peek().equals("{")){
                    stack.remove();
                }else{
                    return false;
                }
            }
        }
        //if there are extra opening characters in the stack, it is
        //incorrect; otherwise, it is correct
        return stack.isEmpty();
    }

    /**
     * This method is used for problem 2. It takes an array of the
     * tokens and pushes each number onto the stack. When it reaches
     * an operation sign, it pops the top two numbers and uses them
     * as arguments for the operation. The result is then pushed onto
     * the stack. If there are not at least two numbers in the stack
     * when an operation is reached or there is more than one value
     * on the stack at the end, this method outputs "Ill-formed".
     * Otherwise, it outputs the computed value.
     * @param tokens the String array of tokens
     * @param stack the stack used to evaluate the expression
     * @return the computed value or the error message
     */
    private static String evaluateExpression(String[] tokens, Stack stack){
        //ensures that the stack starts empty
        stack.emptyList();
        int num1, num2;
        int result = 0;
        for(String str : tokens){
            //numbers are pushed to the stack
            if(str.matches("\\d")){
                stack.push(str);
            }else if(str.matches("[+\\-*/%]")){
                //checks if there is enough numbers in the stack to perform an operation
                if(stack.size() < 2){
                    return "Ill-formed";
                }
                //pulls the top two numbers from the stack in reverse order
                num2 = Integer.parseInt((String)stack.remove());
                num1 = Integer.parseInt((String)stack.remove());
                //performs the operation
                if(str.equals("+")){
                    result = num1 + num2;
                }else if(str.equals("-")){
                    result = num1 - num2;
                }else if(str.equals("*")){
                    result = num1 * num2;
                }else if(str.equals("/")){
                    result = num1 / num2;
                }else if(str.equals("%")){
                    result = num1 % num2;
                }
                //pushes the result to the stack
                stack.push(result + "");
            }

        }
        //ensures that only the result remains at the end
        if(stack.size() == 1) {
            return (String) stack.remove();
        }
        return "Ill-formed";
    }

}
