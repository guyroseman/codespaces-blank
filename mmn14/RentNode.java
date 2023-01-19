public class RentNode
{
    Rent _rent;
    RentNode _next;

    public RentNode(Rent r)
    {
        _rent = new Rent(r);
        _next = null;
    }

    public RentNode(Rent r, RentNode next)
    {
        _rent = new Rent(r);
        _next = next._next;
    }

    public RentNode(RentNode other)
    {
        _rent = new Rent(other._rent);
        _next = other._next;
    }

    public Rent getRent()
    {
        return new Rent(this._rent);
    }

    public RentNode getNext()
    {
        return this._next;
    }

    public void setRent(Rent r)
    {
        this._rent = new Rent(r);
    }

    public void setNext(RentNode next)
    {
        this._next = next;
    }

    public Date getNodeReturnDate()// check if it's okay to keep it as public overhere!!!
    {
        return this.getRent().getReturnDate();
    }

    public Date getNodePicknDate()// check if it's okay to keep it as public overhere!!!
    {
        return this.getRent().getPickDate();
    }
}
