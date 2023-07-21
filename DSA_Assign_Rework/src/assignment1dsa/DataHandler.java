package assignment1dsa;

import java.util.Scanner;

public class DataHandler {
    LinkedStack opStack;  //assignment1dsa.Stack to hold operators and open parenthesis
    LinkedQueue postfixQueue; //Queue to hold final postfix expression
    /**
     * Default constructor
     */
    public DataHandler() {
        boolean moreInput = true; //Allows for multiple inputs (doesnt end execution) until exit is typed
        while (moreInput) {
            String input = GetUserInput(); //Uses scanner to read line
            if(input.equals("exit")){ //Checks for exit code
                moreInput = false;
            }
            else
            {
                boolean wasSuccessful = parseInput(input); //Changes expression from infix to postfix. Ensures validity of statement (see parseInput)
            }

        }

    }

    /**
     * Asks user for input, return said input
     * @return User input as String
     */
    private String GetUserInput() {
        System.out.println("Enter infix expression. Type 'exit' to exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }


    /**
     * Takes users infix expression and turns it into postfix expression
     * Input doesn't require to be fully parenthesised
     * Rather than checking the validity of the statement beforehand, validity is checked whilst parsing. This option
     * results in faster execution of the program overall (only required to iterate though input once, not checked against
     * regex expression), but potentially longer execution time before user receives error message in the case that input
     * isn't valid.
     * However, due to this being a small program, the difference should be negligible.
     * @param input Users string input
     */
    private boolean  parseInput(String input) {
        int lastCharType = 1; //See below
        int splitAllTerms = 0; //States how aa or 12a should be represented as (1 = a*a or 12*a, 2= aa or 12a)
        int openPar = 0; //Counts opened parenthesis
        opStack = new LinkedStack();
        postfixQueue = new LinkedQueue();
        /**Last char type
         * The value it holds refers to the type of character that was last entered
         * Values:
         * 1 - Last character was an open parenthesis '(' or expression not parsed yet
         * 2 - Last character was a number [0-9]
         * 3 - Last character was a letter [a-Z]
         * 4 - Last character was an operator [/*-+^]
         * 5 - Last character was a closed parenthesis ')'
         *
         * Operations
         * If lastCharType was 1 (open parenthesis), next char can be:
         *      a letter (2)
         *          SAVE TO thisVarQueue
         *      a number (3)
         *          SAVE TO thisVarQueue
         *      open parenthesis (1)
         *          ADD TO opStack
         *      - (negative var indicator)(6)
         *          SAVE to thisVarQueue
         *
         * If lastCharType was 2 ([a-Z]), next char is :
         *      open Parenthesis (1)
         *          *******************************************
         *          ADD thisVarQueue to postfixQueue
         *          ADD * to opStack
         *          ADD open parenthesis opStack
         *          *******************************************
         *          *******************************************
         *      closed Parenthesis (5)
         *      ************************************************
         *          ONLY if theres a matching open parenthesis
         *              PUSH thisVarQueue to postfixQueue
         *              POP opStack until open parenthesis
         *                  PUSH to postfixQueue in pop order
         *       ************************************************
         *       ************************************************
         *
         *      an operator (4)
         *      ************************************************
         *          ADD thisVarQueue to postfixQueue
         *            ADD * to opStack
         *      ************************************************
         *      ************************************************
         *If lastCharType was 3 ([0-9]), next char is:
         *      open Parenthesis (1)
         *      closed Parenthesis (5)
         *
         *      an operator (4)
         *
         *
         *If lastCharType was 4 [/*-+^], next char is:
         *      a number (3)
         *      a letter (2)
         *      open parenthesis (1)
         *      - (negative var) (6)
         *
         * If lastCharType was 5 (closed parenthesis):
         *      SEE ABOVE
         *
         * If lastCharType was 6, next char is:
         *      a letter (2)
         *      a number (3)
         */

        for (int x = 0; x < input.length(); x++) {
            char currentChar = input.charAt(x); //Get char at position x
            if (currentChar == ' ') {

            } else {
                int currentCharType = getCharType(currentChar); //Gets integer value based on data category
                Data dataObject;
                switch (lastCharType) {
                    case 1:
                        switch (currentCharType) {
                            case 1: //(
                                dataObject = new OpData(0, currentChar); //Creates operator data
                                opStack.push(dataObject);
                                openPar++;
                                break;
                            case 2: //[0-9]
                                dataObject = new VarData(currentChar);
                                postfixQueue.put(dataObject);
                                lastCharType = 2;
                                break;
                            case 3: //[a-Z]
                                dataObject = new VarData(currentChar);
                                postfixQueue.put(dataObject);
                                lastCharType = 3;
                                break;
                            default:
                                whereError(x);
                                System.out.println("Error: Unexpected character at position " + (x + 1));
                                return false;
                        }
                        break;
                    case 3:
                        switch (currentCharType) {
                            case 1: //(
                                caseVarBracket(currentChar);
                                openPar++;
                                lastCharType = 1;
                                break;
                            case 4: //[/*-+^]
                                caseOperator(currentChar);
                                lastCharType = 4;
                                break;
                            case 5: //)
                                if (openPar > 0) {
                                    openPar--;
                                    caseClosePar();
                                    lastCharType = 5;
                                    break;
                                } else {
                                    whereError(x);
                                    System.out.println("ERROR: No matching open parenthesis for character " + (x + 1));
                                    return false;
                                }
                            default:
                                whereError(x);
                                System.out.println("ERROR: Unexpected character at position " + (x + 1));
                                return false;
                        }
                        break;
                    case 2:
                        switch (currentCharType) {
                            case 1: //(
                                caseVarBracket(currentChar);
                                openPar++;
                                lastCharType = 1;
                                break;
                            case 4: //[/*-+^]
                                caseOperator(currentChar);
                                lastCharType = 4;
                                break;
                            case 5:
                                if (openPar > 0) {
                                    openPar--;
                                    caseClosePar();
                                    lastCharType = 5;
                                    break;
                                } else {
                                    whereError(x);
                                    System.out.println("ERROR: No matching open parenthesis for character " + (x + 1));
                                    return false;
                                }
                            default:
                                whereError(x);
                                System.out.println("ERROR: Unexpected character at position " + (x + 1));
                                return false;
                        }
                        break;
                    case 4:
                        switch (currentCharType) {
                            case 1: //(
                                dataObject = new OpData(0, currentChar); //Creates operator data
                                opStack.push(dataObject);
                                openPar++;
                                break;
                            case 2: //[0-9]
                                dataObject = new VarData(currentChar);
                                postfixQueue.put(dataObject);
                                lastCharType = 2;
                                break;
                            case 3://[a-Z]
                                dataObject = new VarData(currentChar);
                                postfixQueue.put(dataObject);
                                lastCharType = 3;
                                break;
                            default:
                                whereError(x);
                                System.out.println("ERROR: Unexpected character at position " + (x + 1));

                                return false;

                        }
                        break;
                    case 5://)
                        switch (currentCharType) {
                            case 1: //(
                                dataObject = new OpData(2, '*'); //Creates object for *
                                addOperator(dataObject);
                                dataObject = new OpData(0, currentChar);
                                opStack.push(dataObject); //Adds open parenthesis to stack
                                openPar++;
                                lastCharType = 1;
                                break;
                            case 4: //[/*-+^]
                                int precedence = getPrecedence(currentChar); //Gets precedence of operator
                                dataObject = new OpData(precedence, currentChar); //Creates assignment1dsa.Data object
                                addOperator(dataObject);
                                lastCharType = 4;
                                break;
                            case 5: //)
                                if (openPar > 0) {
                                    openPar--;
                                    caseClosePar();
                                    lastCharType = 5;
                                    break;
                                } else {
                                    whereError(x);
                                    System.out.println("ERROR: No matching open parenthesis for character " + (x + 1));
                                    return false;
                                }
                            default:
                                whereError(x);
                                System.out.println("ERROR: Unexpected character at position " + (x + 1));
                                return false;

                        }
                        break;
                    default:
                        whereError(x);
                        System.out.println("ERROR: Unexpected character at position " + (x + 1));
                        return false;
                }
            }
        }

        if (openPar != 0) { //If there are open parenthesis without matching closed parenthesis
            System.out.println("Error: Missing closing parenthesis");
            return false;
        } else {

            while (opStack.isEmpty() == false) { //Empty stack
                postfixQueue.put(opStack.pop()); //pops operator and adds to queue
            }
            while (postfixQueue.isEmpty() == false) //Empty queue
            {
                Data theData = postfixQueue.remove();
                System.out.print(theData.getVar());
            }
            System.out.println("\n");
            return true;

        }

    }


    private  void whereError(int position) {
            for(int y=0; y < position; y++){
                System.out.print(" ");
            }
            System.out.println("^");
    }
    /**
     * Works out current char type, so case switch can be used
     * @param currentChar Char of interest
     * @return integer:
     * 1 = (
     * 2 = [0-9]
     * 3 = [a-Z]
     * 4 = [/*+^]
     * 5 = )
     * 6 = -
     * 7 = Input char next expected: infix expression not valid
     */
    private int getCharType(char currentChar) {
        if (currentChar == '(') {
            return 1;
        }
        else if (currentChar >= '0' && currentChar <= '9'){
            return 2;
        }
        else if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
            return  3;
        }
        else if (currentChar == '+'|| currentChar == '^'|| currentChar =='/'|| currentChar =='*' || currentChar == '-') {
            return  4;
        }
        else if (currentChar == ')'){
            return 5;
        }

        else {
            return  7;
        }

    }

    /**
     * Case where expected operator is in input
     * @param currentChar Char of interest
     */
    private  void  caseOperator(char currentChar) {
        int precedence = getPrecedence(currentChar); //Gets precedence of operator
        Data op = new OpData(precedence, currentChar); //Creates data object
        addOperator(op);
    }


    /**
     * Takes operator char, returns precedence
     * @param currentChar Operator
     * @return Precedence level
     */
    private  int getPrecedence(char currentChar){
        if(currentChar == '+' || currentChar == '-') { //Operators with precedence of 1
            return 1;
        }
        else if (currentChar == '*' || currentChar == '/') { //Operators with precedence of 3
            return 2;
        }
        else { //Operators with precedence of 3 (^)
            return 3;
        }
    }


    /**
     * Used if current character is closed Parenthesis and there is a matching open parenthesis
     */
    private void caseClosePar(){
        boolean foundOpenPar = false; //True when open parenthesis found
        while (foundOpenPar == false){
            int precedence = opStack.peek().getPrecedence(); //Gets precedence of top item of opStack. If == 0, then it is (
            if(precedence == 0) {
                opStack.pop(); //removes open parenthesis
                foundOpenPar = true;
            }
            else {
                postfixQueue.put(opStack.pop()); //Pops opStack and put into postfixQueue
            }
        }
    }

    /**
     * Takes care of when open parenthesis appears directly after variable
     * @param currentChar
     */
    private void caseVarBracket(char currentChar) {
        Data op = new OpData(2, '*'); //Creates multiplication object
        addOperator(op);
        op = new OpData(0, '('); //Creates data object for open parenthesis
        opStack.push(op); //Adds it to opStack

    }


    private  void addOperator(Data op) {
        boolean isAdded = false; //True when operator added to stack
        while (isAdded == false) {
            if (opStack.isEmpty() || op.getPrecedence() == 0) { //If stack is empty or operator is open parenthesis
                opStack.push(op);
                isAdded = true;
            } else {
                int tSPrecedence = opStack.peek().getPrecedence(); //Gets precedence of data at top of stack
                if(op.getPrecedence() <= tSPrecedence) {
                    postfixQueue.put(opStack.pop()); //Pops top of opStack and puts it in postfixQueue
                }
                else
                {
                    opStack.push(op);
                    isAdded = true;
                }

            }
        }
    }
}