/**
 * RentNode class represents a Node in a linked list, containing a Rent object and a pointer to the next RentNode in the list.<br>
 * The class provides methods for getting and setting the Rent object and next RentNode,<br>
 * as well as methods for getting the pick-up and return dates of the Rent object.<br>
 */
public class RentNode
{
    
    Rent _rent; // The Rent object stored in the node
    RentNode _next; // Pointer to the next RentNode in the list

    /**
     * Constructor for creating a RentNode with a Rent object.<br>
     * @param r the Rent object to be stored in the node.
     */
    public RentNode(Rent r)
    {
        _rent = new Rent(r); // Create a new Rent object to store in the node
        _next = null; 
    }

    /**
     * Constructor for creating a RentNode with a Rent object and a pointer to the next RentNode.<br>
     * @param r the Rent object to be stored in the node
     * @param next the next RentNode in the list
     */
    public RentNode(Rent r, RentNode next)
    {
        _rent = new Rent(r); // Create a new Rent object to store in the node
        _next = next; // Sets the pointer to the next node to be next
    }

    /**
     * Copy constructor for creating a RentNode from another RentNode.<br>
     * @param other the RentNode to copy
     */
    public RentNode(RentNode other)
    {
        _rent = new Rent(other._rent);// Create a new Rent object by copying the Rent object from the other RentNode
        _next = other._next; // Sets the pointer to point on the other node's next
    }

    /**
     * Get method for the Rent object stored in the node.<br>
     * @return a new Rent object that is a copy of the Rent object stored in the node
     */
    public Rent getRent()
    {
        return new Rent(this._rent);
    }

    /**
     * Get method for the next RentNode in the list.<br>
     * @return the next RentNode in the list
     */
    public RentNode getNext()
    {
        return this._next;
    }

    /**
     * Set method for the Rent object stored in the node.<br>
     * @param r the Rent object to be stored in the node
     */
    public void setRent(Rent r)
    {
        this._rent = new Rent(r);
    }

    /**
     * Set method for the next RentNode in the list.<br>
     * @param next the next RentNode in the list
     */
    public void setNext(RentNode next)
    {
        this._next = next;
    }
}
