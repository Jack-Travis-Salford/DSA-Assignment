package assignment1dsa;

public interface Stack {
    /**
     * Defines standard interface for Stacks
     * The main uses are:
     * pop() - Get top item from stack
     * push(Object object) - Push given object to top of stack
     * isEmpty() - Checks to see if stack is empty
     * peek() = Get object at top of stack, without removing it
     */
    public Data pop();
    public void push(Data object);
    public boolean isEmpty();
    public Data peek();

}