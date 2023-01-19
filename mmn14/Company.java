public class Company 
{
    RentNode _head;

    public Company()
    {
        _head = null;
    }

    public boolean addRent(String name, Car car, Date pick, Date ret)
    {
        Rent r = new Rent(name, car, pick, ret);
        
        //if head is null, list is empty -> set first list object
        if(_head == null)
        {   
            _head = new RentNode(r);
            return true;
        }

        return rentIsLegal(r, this._head, null);
    }

    private boolean rentIsLegal(Rent r, RentNode next, RentNode prev)
    { 
        Date newRentPick = r.getPickDate();
        Date currRentPick = next.getRent().getPickDate();
        
        while(next != null)
        {
            // Check if the rent's pick date is after the curr
            if(newRentPick.after(currRentPick))
            {
                addMe(new RentNode(r, next), next, prev);
                return true;    
            }
            else if(newRentPick.equals(currRentPick))
            {
                // Check rents aren't equal
                if(r.equals(next._rent))
                    return false;

                Date newRentRet = r.getReturnDate();
                Date currRentRet = next.getRent().getReturnDate();

                // Check if the new rent has a further return date
                if(newRentRet.after(currRentRet))
                {
                    addMe(new RentNode(r, next), next, prev);
                }
                // If not the curr will point the new rent
                else
                {
                    addMe(next, new RentNode(r, next._next), prev);
                }
                return true;
            }
            else
            {
                rentIsLegal(r, next._next, next);
            }
        }
        return false;
    }

    private void addMe(RentNode middle ,RentNode next, RentNode prev)
    {
        middle.setNext(next);
        prev.setNext(middle);
    }

    public boolean removeRent(Date d)
    {
        RentNode remove = _head;
        return removeRent(d, remove, null);
    }

    private boolean removeRent(Date d, RentNode node, RentNode prev)
    {
        Date returnDate = node.getRent().getReturnDate();
        if(node == null)
            return false;
        else if(d.equals(returnDate))
        {
            removeMe(node, node._next, prev);
            return true;
        }
        return removeRent(d, node._next, node);
    }

    private void removeMe(RentNode middle, RentNode next, RentNode prev)
    {
        middle.setNext(null);
        prev.setNext(next);
    }

    public int getNumOfRents()
    {
        return getNumOfRents(this._head, 0);
    }

    private int getNumOfRents(RentNode node, int numOfRents)
    {
        if(node == null)
            return numOfRents;
        
        return getNumOfRents(node._next, numOfRents + 1);
    }

    public int getSumOfPrices()
    {
        return getSumOfRents(this._head, 0);
    }

    private int getSumOfRents(RentNode node, int sumOfRents)
    {
        if(node == null)
            return sumOfRents;
        
        int addToSum = node.getRent().getPrice();
        return getSumOfRents(node._next, sumOfRents + addToSum);
    }

    public int getSumOfDays()
    {
        return getSumOfDays(this._head, 0);
    }

    private int getSumOfDays(RentNode node, int sumOfDays)
    {
        if(node == null)
            return sumOfDays;
        
        int addToSum = node.getRent().howManyDays();
        return getSumOfDays(node._next, sumOfDays + addToSum);
    }

    public int averageRent()
    {
        if(this._head == null)
            return 0;

        int sumOfDays = this.getSumOfDays();
        int numOfRents = this.getNumOfRents();

        return sumOfDays/numOfRents;
    }

    public Car lastCarRent()
    {
        if(this._head == null)
            return null;

        Rent headRent = this._head.getRent();
        Date headReturn = headRent.getReturnDate();
        // Car headCar = headRent.getCar();
        return lastCarRent(this._head, headRent, headReturn);
    }

    private Car lastCarRent(RentNode node, Rent latestRent, Date latestDate)
    {
        // Limit
        if(node == null)
        {
            Car latestCar = latestRent.getCar();
            return latestCar;
        }
        // Check if this node's return date is later 
        else if(node.getNodeReturnDate().after(latestDate))
        {
            // Keep checking with the updated latest return date + rent
            return lastCarRent(node._next, node.getRent(), node.getNodeReturnDate());
        }
        // Check if both have same return date 
        else if(node.getNodeReturnDate().equals(latestDate))
        {
            // if this node's pick date is before the current latest rent 
            if(node.getNodePicknDate().before(latestDate))
                // Keep checking for the the rent with the earliest pickup date 
                // it'll be shown first in out lisy
                return lastCarRent(node._next, node.getRent(), latestDate);
            
            //if not keep on checking for the next object
        }
        return lastCarRent(node._next, latestRent, latestDate);
    }

    public Rent longestRent()
    {
        if(this._head == null)
            return null;
        
        Rent headRent = this._head.getRent();
        return longestRent(this._head, headRent, 0);
    }

    private Rent longestRent(RentNode node, Rent r, int maxRent)
    {
        int nodeRentLength = node.getRent().howManyDays();
        // Limit
        if(node == null)
        {
            return r;
        }
        // Check if the current rent is longer than the current longest rent
        else if(nodeRentLength > maxRent)
        {
            return longestRent(node._next, node.getRent(), nodeRentLength);
        }
        
        // If equals to max or smaller keep checking the rest of the list 
        return longestRent(node._next, r, maxRent);
    }

    public char mostCommomRate()
    {
        if(this._head == null)
            return 'N';

        // Each cell represents the sum of types in the list    
        int typeSum[] = new int[3];
        for(int i = 0 ; i <= typeSum.length ; i++)
        {
            typeSum[i] = 0;
        }

        return mostCommomRate(_head, typeSum);
    }

    private char mostCommomRate(RentNode node ,int[] typeSum)
    {
        char thisRentType = node.getRent().getCar().getType();

        if(node == null)
        {
            return highestInTheArray(typeSum);
        }
        else if(thisRentType == 'A')
        {
            typeSum[0] += 1;
            return mostCommomRate(node._next, typeSum, mostCommon);
        }
        else if(thisRentType == 'B')
        {
            typeSum[1] += 1;
            return mostCommomRate(node._next, typeSum, mostCommon);
        }
        else if(thisRentType == 'C')
        {
            typeSum[2] += 1;
            return mostCommomRate(node._next, typeSum, mostCommon);
        }
        else if(thisRentType == 'D')
        {
            typeSum[3] += 1;
            return mostCommomRate(node._next, typeSum, mostCommon);
        }
    }

    private int highestInTheArray(int[] typeSum, char mostCommon)
    {
        int maxIndex = -1;
        int maxSum = 0;

        for(int i = 0; i < typeSum.length ; i++)
        {
            if(typeSum[i] > maxSum)
            {
                maxSum = typeSum[i];
            }

            if(typeSum[i] == maxSum && i > maxIndex && typeSum[i] != 0)
            {
                maxIndex = i;
            }
        }

        if(maxIndex == 0)
            return 'A';
        else if(maxIndex == 1)
            return 'B';
        else if(maxIndex == 2)
            return 'C';
        else if(maxIndex == 3)
            return 'D';

        return 'N';
    }
    
    // check if this method needs to check identitiy like equals - like the current method
    // or the exsitence of all other's rents in the given company. 
    public boolean includes(RentNode otherHead)
    {
        if(otherHead == null)
            return true;
        if(this._head == null)
            return false; 
        
            return includes(this._head, otherHead);
    }

    private boolean includes(RentNode thisNode, RentNode otherNode)
    {
        if(thisNode == null && otherNode == null)
        {
            return true; 
        }
        else if(thisNode == null || otherNode == null)
        {
            return false;
        }

        Rent thisNodeRent = thisNode.getRent();
        Rent otherNodeRent = otherNode.getRent();

        if(thisNodeRent.equals(otherNodeRent))
            return includes(thisNode._next, otherNode._next);
        return false; 
    }

    public void merge(RentNode mergeMeRent)
    {
        int numRentsOfThis = this.getNumOfRents();
        int numRentsOfOther = mergeMeRent.getNumOfRents();
        
    }

}
