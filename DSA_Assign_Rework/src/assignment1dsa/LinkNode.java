package assignment1dsa;

public class LinkNode {
    /**
     * Allows the creation of LinkNodes, used for linked lists
     * @var theObject - Holds the data for node
     * @var - Points to next node. If there isnt one, next=null
     */
    Data theObject;
    LinkNode next;

    /**
     * Constructor for assignment1dsa.LinkNode. No default constructor, as values are requires for node to be initialized
     * @param theObject  - assignment1dsa.Data node will hold
     * @param next - Location of next node. If there isnt one, then = null
     */
    public LinkNode(Data theObject, LinkNode next)
    {
        this.theObject = theObject;
        this.next = next;
    }

    /**
     * Allows to get next node
     * @return Location of next node
     */
    public LinkNode next(){
        return  next;
    }

    /**
     * Allows to get data of current node
     * @return data of current node as Object
     */
    public Data data() {
        return theObject;
    }
}