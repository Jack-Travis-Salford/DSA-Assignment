package assignment1dsa;

public class OpData extends  Data{
    int precedence; //Holds precedence of operator (reduced comparisons when turning infix expression to postfix.
    //(=0, + or - = 1 , * or / = 2, ^ = 3


    /**
     * Constructor for opData (Object thats added to assignment1dsa.LinkedStack
     * @param precedence Holds precedence of operator (reduced comparisons when turning infix expression to postfix. (=0, + or - = 1 , * or / = 2, ^ = 3
     * @param operator Holds operator, passed to super
     */
    public OpData(int precedence, char operator) {
        super(operator);
        this.precedence = precedence;
    }

    /**
     * Gets precedence - overrides super
     * @return precedence
     */
    public int getPrecedence() {
        return precedence;
    }


}