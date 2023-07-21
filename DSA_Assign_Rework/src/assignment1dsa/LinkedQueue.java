package assignment1dsa;

public class LinkedQueue implements  Queue_interface{

    protected LinkNode front, rear; //Pointers to  front and rear of queue


    // default constructor
    public LinkedQueue()
    {
        front = rear = null; //Both equal null, because they haven't yet been created
    }

    // @return true if list is empty
    public boolean isEmpty()
    {
        return front == null;
    }

    // returns the element from the front of the queue
    public Data peek()
    {
        if (isEmpty())
            return null;
        else
            return front.theObject;
    }


    public void put(Data theObject)
    {
        // creates a node for the new element
        LinkNode p = new LinkNode(theObject, null);
        // append p to the queue
        if (front == null)
        // empty queue
            front = p;
        else
        // non-empty queue
            rear.next = p;
        rear = p;
    }
    public Data remove()
    {
        if (isEmpty())
            return null;
        Data frontObject = front.theObject;
        front = front.next;
        if (isEmpty())
            rear = null; // to allow garbage collection
        return frontObject;
    }
}
