public class Company 
{
    RentNode _head;

    public Company()
    {
        _head = null;
    }

    public boolean addRent(String name, Car car, Date pick, Date ret)
    {
        
        Rent newRent = new Rent(name, car, pick, ret);
        RentNode current = _head;
        
        if(this.addRentHead(newRent))
            return true; 
        
        // iterate through the list
        while (current._next != null) 
        {
            RentNode next = current._next;
            Rent nextRent = next.getRent();
            Date currentPick = current.getRent().getPickDate();
        
            
            // check if the new rent is already in the list
            if (newRent.equals(nextRent) || newRent.equals(current.getRent()))
            {
                return false;
            }
            // check if the new rent's pick date is between the current and next pick date
            if (pick.after(currentPick) && pick.before(nextRent.getPickDate())) 
            {
                current._next = new RentNode(newRent, next);
                return true;
            } 

            // check if the new rent's pick date is equal to the current pick date
            if (pick.equals(currentPick)) 
            {
                if(addSamePickdate(current, newRent))
                    return true;

                current = current._next;
                
            } 
            else 
            {
                current = current._next;
            }
        }
        
        
        if(current.getRent().equals(newRent))
            return false;

        // check if the new rent's pick date is after the current rent's pick date
        if (pick.after(current.getRent().getPickDate())) 
        {
            current._next = new RentNode(newRent);
        }
        return true;
    }

    private Boolean addSamePickdate(RentNode current, Rent newRent) 
    {
        Date currentReturn = current.getRent().getReturnDate();
        Date newRentReturn = newRent.getReturnDate();
    
        // check if the new rent's return date is after the current return date
        if (newRentReturn.after(currentReturn)) 
        {
            RentNode prev = findPrev(this._head ,current);
            // check if prev is not null
            if (prev != null) 
            {
                prev._next = (new RentNode(newRent, current));
            }
            else 
            {
                this._head = new RentNode(newRent, current);
            }
            return true;
        }

        else if(newRentReturn.before(currentReturn) && 
        ((current._next.getNodePicknDate().equals(newRent.getPickDate()) 
            && newRentReturn.after(current._next.getNodeReturnDate()))) || 
            (current._next.getNodePicknDate().equals(newRent.getPickDate()) == false))
        {
            current.setNext(new RentNode(newRent, current._next));
            return true;
        }

        // check if the new rent's return date is before the current return date
        else if (newRentReturn.before(currentReturn) && current._next == null) 
        {
            current._next = new RentNode(newRent);
            return true;
        } 
        return false;
    }

private RentNode findPrev(RentNode head, RentNode current)
{
    RentNode prev = null;
    
    if (current == head)
        return prev;
    prev = head;

    while(prev._next != null)
    {
        if(current == prev._next)
        {
            return prev;
        }
        prev = prev._next;
    }
    return null; 
}

private boolean addRentHead (Rent newRent)
{
    
    // if the list is empty, set the first list object
    if (this._head == null) {   
        this._head = new RentNode(newRent);
        return true;
    }

    if(newRent.equals(this._head.getRent()))
    {
        return false;
    }

    // if new rent's pick date is before the this._head's pick date
    // make the new rent the new this._head
    if (newRent.getPickDate().before(this._head.getRent().getPickDate())) 
    {
        this._head = new RentNode(newRent, this._head);
        return true;
    }

    // Head and newRent are sharing the same pickDate 
    if (newRent.getPickDate().equals(this._head.getRent().getPickDate()))
    {
        int longer = Math.max(this._head.getRent().howManyDays(), newRent.howManyDays());
        
        if(newRent.howManyDays() == longer)
        {
            this._head = new RentNode(newRent, this._head);
            return true;
        }
    }
    return false;

}

/*    public boolean addRent(String name, Car car, Date pick, Date ret)
    {
        Rent newRent = new Rent(name, car, pick, ret);
        
        //if head is null list is empty -> set first list object
        if(_head == null)
        {   
            _head = new RentNode(newRent);
            return true;
        }
        else if(pick.before(_head.getRent().getPickDate()))
        {
            _head = new RentNode(newRent, _head);
            return true;
        }

        return rentIsLegal(newRent, this._head);
    }

    private boolean rentIsLegal(Rent newRent, RentNode currentRent)
    { 
        Date newRentPick = newRent.getPickDate();
        Date currRentPick = currentRent.getRent().getPickDate();
        RentNode newRentNode = new RentNode(newRent);
       
        // If the rents are equal
        if(newRent.equals(currentRent.getRent()))
        {
            return false;// Illegal term
        }

        // If we've reached the end of the list
        if(currentRent._next == null)
        {
            if(newRentPick.before(currRentPick))
                addMe(newRentNode, currentRent);
            else
                addMe(currentRent, newRentNode);
            return true;
        }
    
        Date nextRentPick = currentRent._next.getRent().getPickDate();

        // If the newRent is after the current object and after the next
        if(newRentPick.after(currRentPick) && newRentPick.before(nextRentPick))
        {
            // add the new rent between them two
            addMe(currentRent, newRentNode);
            return true;
        }

        
        //  * export this statment to a private method to check this edge case (check notepad)
        //  *
        if(newRentPick.equals(currRentPick))
        {
            Date newRentReturn = newRent.getReturnDate();
            Date currRentReturn = currentRent.getRent().getReturnDate(); 

            if(newRentReturn.after(currRentReturn))
            {
                Date nextPick = currentRent._next.getRent().getPickDate();

                if(newRentPick.equals(nextPick) && 
                newRentReturn.after(currRentReturn) && newRentReturn.before(nextPick))
                    {
                        addMe(currentRent, newRentNode);
                        return true;
                    }
                
            }
            // /else if(newRentReturn.before(currRentReturn) &&
            //  newRentReturn.after(currentRent))

            addMe(newRentNode, currentRent);
            
            return rentIsLegal(newRent, currentRent._next);


        }
        
        return rentIsLegal(newRent, currentRent._next);
    }

    private void addMe(RentNode currentRentNode ,RentNode newRentNode)
    {
        newRentNode.setNext(currentRentNode._next);
        currentRentNode.setNext(newRentNode);
    }
*/
    public boolean removeRent(Date d)
    {
        //checking the first object of the list
        RentNode remove = _head;
        //return the recursive method. 
        return removeRent(d, remove, null);
    }

    private boolean removeRent(Date d, RentNode node, RentNode prev)
    {
        Date returnDate = node.getRent().getReturnDate();

        // Limit of the recursive method - the end of the list
        if(node == null)
            return false;

        // If one of the rents has the required date - remove it
        if(d.equals(returnDate))
        {
            // Remove using the private method
            removeMe(node, node._next, prev);
            return true;
        }
        return removeRent(d, node._next, node);
    }

    private void removeMe(RentNode toBeRemoved, RentNode next, RentNode prev)
    {
        toBeRemoved.setNext(null); // Change the next to be null
        prev.setNext(next); // sets previous next to point the removed node next's
    }

    public int getNumOfRents()
    {
        return getNumOfRents(this._head, 0);
    }

    private int getNumOfRents(RentNode node, int numOfRents)
    {
        // Limit of the recursive method - the end of the list 
        if(node == null)
            return numOfRents;
        
        // For every node we apply the recursive method the variable "numOfRents" is increased by 1.
        return getNumOfRents(node._next, numOfRents + 1);
    }

    public int getSumOfPrices()
    {
        return getSumOfRents(this._head, 0);
    }

    private int getSumOfRents(RentNode node, int sumOfRents)
    {
        // Limit of the recursive method - the end of the list
        if(node == null)
            return sumOfRents;
        
        int addToSum = node.getRent().getPrice();
        
        // For every node we apply the recursive method
        // we add the price of the current rent to the varibel "sumOfRents"
        return getSumOfRents(node._next, sumOfRents + addToSum);
    }

    public int getSumOfDays()
    {
        return getSumOfDays(this._head, 0);
    }

    private int getSumOfDays(RentNode node, int sumOfDays)
    {
        //limit of the recursive
        if(node == null)
        {
            return sumOfDays;
        }

    
        int addToSum = node.getRent().howManyDays();

        return getSumOfDays(node._next, sumOfDays + addToSum);
    }

    public double averageRent()
    {
        if(this._head == null)
            return 0;

        double sumOfDays = this.getSumOfDays();
        double numOfRents = this.getNumOfRents();

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
                
        }
        //if not keep on checking for the next object using the recursive method
        return lastCarRent(node._next, latestRent, latestDate);
    }

    public Rent longestRent()
    {
        if(this._head == null)
        {
            return null;
        }

        RentNode current = this._head;
        Rent theLongestRent = new Rent(_head.getRent());
        int longest = 0;
    
        while(current != null)
        {
            int rentLength = current.getRent().howManyDays();

            if(rentLength > longest)
            {
                longest = rentLength;
                theLongestRent = new Rent(current.getRent());
            }
            
            current = current._next;
        }
        
        return theLongestRent;
    }

    public char mostCommonRate()
    {
        RentNode current = this._head;

        if(current == null)
            return 'N';

        int sumA = 0;
        int sumB = 0;
        int sumC = 0;
        int sumD = 0;

        while(current != null)
        {
            char currentType = current.getRent().getCar().getType();

            if(currentType == 'A')
                sumA++;
            if(currentType == 'B')
                sumB++;
            if(currentType == 'C')
                sumC++;
            if(currentType == 'D')
                sumD++;
            
            current = current._next;
        }

        return mostCommomRateFinder(sumA, sumB, sumC, sumD);
    }

    private char mostCommomRateFinder(int a, int b, int c, int d)
    {
        int maxIndex = -1;
        int maxSum = 0;
        int[] typeSum = new int[5];
        
        typeSum[1] = a;
        typeSum[2] = b;
        typeSum[3] = c;
        typeSum[4] = d;

        for(int i = 0; i < typeSum.length ; i++)
        {
            if(typeSum[i] >= maxSum && i > maxIndex)
            {
                typeSum[i] = maxSum;
                maxIndex = i-1;
            }
        }

        if(maxIndex == 1)
            return 'A';
        if(maxIndex == 2)
            return 'B';
        if(maxIndex == 3)
            return 'C';
        if(maxIndex == 4)
            return 'D';
        else
            return 'N';
    }
    
    public boolean includes(Company otherCompany)
    {
        RentNode otherHead = otherCompany._head;

        if(otherHead == null)
            return true;
        if(this._head == null)
            return false; 
        
        return includes(this._head, otherHead);
    }

    private boolean includes(RentNode thisNode, RentNode otherNode)
    {
        // both lists end have been reached 
        // or the end of thew given list have been reached
        if((thisNode == null && otherNode == null) ||
            (thisNode != null && otherNode == null))
        {
            return true; 
        }
        // this list's end have been reched while other haven't
        else if(thisNode == null && otherNode != null)
        {
            return false;
        }

        Rent thisNodeRent = thisNode.getRent();
        Rent otherNodeRent = otherNode.getRent();

        if(thisNodeRent.equals(otherNodeRent))
            // both rents are equal continue checking both lists
            return includes(thisNode._next, otherNode._next);
        
        // if both rents are not equal 
        // keep checking the other's rent with this next rent
        return includes(thisNode._next, otherNode); 
    }

    public void merge(Company otherList) 
    {
        
        RentNode current = otherList._head;
        
        while (current != null) 
        {
            Rent rent = current.getRent();
            addRent(rent.getName(), rent.getCar(), rent.getPickDate(), rent.getReturnDate());
            current = current._next;
        }
    }

    public String toString()
    {
        String part1 = ("The company has " + this.getNumOfRents() + " rents");
        String part2 = "";
        
        if(this.getNumOfRents() > 0)
        {
            part1 += ":" + "\n";
            RentNode current = this._head;

            while(current != null)
            {
                part2 += ("\n" + current.getRent().toString() + "\n");
                current = current._next;
            }
        }
        else
        {
            part1 += ".";
        }
        return(part1 + part2);
    }
}
