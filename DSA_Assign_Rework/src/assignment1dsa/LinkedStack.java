package assignment1dsa;

import java.util.EmptyStackException;

public class LinkedStack implements  Stack{
    protected LinkNode top;
    /**
     * Default constructor
     */
    public LinkedStack() {
        top = null;
    }
    /**
     * Checks to see if assignment1dsa.Stack is empty (defined in implement)
     * @return boolean. True = empty, otherwise = false
     */
    public boolean isEmpty() {
        return  top == null;
    }

    /**
     * Pushes object onto assignment1dsa.Stack
     * @input Object to push
     */
    public void push(Data theObject) {
        top = new LinkNode(theObject, top);
    }

    /**
     * Pushes object from stack (Last item in is first out)
     *@throws EmptyStackException if stack is empty
     * @return Object
     */

    public Data pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            Data object = top.theObject;
            top = top.next();
            return object;
        }
    }

    /**
     * Returns top element, without popping it
     * @throws EmptyStackException if stack is empty
     * @return object
     */
    public Data peek() {
        if(isEmpty()) {
            throw  new EmptyStackException();
        }
        else
        {
            return  top.theObject;
        }
    }

}