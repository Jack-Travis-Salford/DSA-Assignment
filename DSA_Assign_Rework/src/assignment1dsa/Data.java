package assignment1dsa;

public class Data {
    char var; //Holds variable(s)

    /**
     * Constructor for opData (Object thats added to assignment1dsa.LinkedStack
     * @param var Holds operator
     */
    public Data(char var) {
        this.var = var;
    }



    /**
     * Gets operator
     * @return operator
     */
    public char getVar() {
        return var;
    }

    public int getPrecedence() {
        return -1;
    }
}