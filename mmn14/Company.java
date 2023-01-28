/**
 * The Company class is a representation of a car rental company<br>
 * It uses a linked list to store Rent objects, which contain information about a rental,<br>
 * such as the customer's name, the rented car, and the pick-up and return dates.<br>
 * The class contains methods for adding and removing Rent objects from the list,<br>
 * as well as a method for getting the number of Rent objects in the list and the total sum of their prices.
 */
public class Company 
{
    //-----------------Class Variables-----------------//
    RentNode _head;

    //----------------------------------------Public Methods----------------------------------------//

    /**
     * Constructs an instance of the Company class.<br>
     * Initializes the _head of the linked list to null.
     */
    public Company()
    {
        _head = null;
    }

    /**
     * This boolean method gets paramaters of a rent,<br>
     * and adds it to a company. <br>
     * 
     * If there is an equal rent in the company already the method returns false.<br>
     *  
     * @param name the name of the costumer
     * @param car the rented car
     * @param pick the pickup date
     * @param ret the return date
     * @return true if the rent was added
     */
    public boolean addRent(String name, Car car, Date pick, Date ret)
    {
        
        Rent newRent = new Rent(name, car, pick, ret);
        RentNode current = _head;
        
        // Checks if this rent could be added before or after the head
        if(this.addRentHead(newRent))
        {
            return true; 
        }

        // Iterate through the list
        while (current._next != null) 
        {
            RentNode next = current._next;
            Rent currentRent = current.getRent();
            Rent nextRent = next.getRent();
            Date currentPick = current.getRent().getPickDate();
            Date nextPick = next.getRent().getPickDate();
            
            // Check if the new rent is already in the list
            if (newRent.equals(nextRent) || newRent.equals(currentRent))
            {
                return false;
            }

            // Check if the new rent's pick date is between the current and next pick date
            if (pick.after(currentPick) && pick.before(nextPick)) 
            {
                // Sets the current's new next to be the new rent that points to its old next
                current.setNext(new RentNode(newRent, next));
                return true;
            } 

            // Check if the new rent's pick date is equal to the current pick date
            if (pick.equals(currentPick)) 
            {
                // Check if the new rent is valid to be added next to the current rent with the same pick date 
                if(addSamePickdate(current, newRent))
                    return true;

                 // Move to the next Rent in the list 
                current = current._next;
                
            } 
            else 
            {
                 // Move to the next Rent in the list 
                current = current._next;
            }
        }
        
        // Last object of the company have been reached (current._next == null)

        // Check if the rents are equal
        if(current.getRent().equals(newRent))
            return false;

        // Check if the new rent's pick date is after the current rent's pick date
        if (pick.after(current.getRent().getPickDate())) 
        {
            // Sets the current's  new next to be the new rent that points to null (end of the list)
            current.setNext(new RentNode(newRent));
        }
        return true;
    }

    /**
     * This boolean method gets a date and date<br>
     * Removes the first rent with an equal return date to the given date<br>
     * 
     * Returns false if there is no such rent<br>
     * 
     * @param d the given return date
     * @return true if a rent has been removed
     */
    public boolean removeRent(Date d)
    {
        //checking the first object of the list
        RentNode remove = _head;

        // Check if the list is empty
        if(remove == null)
        {
            // If so the list is empty, therefore there is no rent to remove
            return false;
        }

        // Check if the head is the obly objext in the list, and that it owns the desired return date .
        if (remove._next == null && remove.getRent().getReturnDate().equals(d))
        {
            // if so sets it's rent to be null
            remove.setRent(null);
            return true;
        }
    
        // Enter the recursive method and iterate through the list
        return removeRent(d, remove._next, remove);        
    }

    /**
     * returns the number of Rents in the linked list.<br>
     * @return an int representing the number of rents in the linked list.
     */
    public int getNumOfRents()
    {
        return getNumOfRents(this._head, 0);
    }

    /**
     * returns the total sum of prices of all the Rent objects in the linked list.
     * @return an int representing the total sum of prices of all the Rent objects in the linked list.
     */
    public int getSumOfPrices()
    {
        // Initial call to the private method, starting at the head of the list and with an initial sum of 0
        return getSumOfPrices(this._head, 0);
    }

    /**
     * returns the total number of days in all the Rent objects in the linked list.<br>
     * 
     * @return total number of days in all the Rent objects in the linked list
     */
    public int getSumOfDays()
    {
        //This method returns the total number of days in all the Rent objects in the linked list
        return getSumOfDays(this._head, 0);
    }

    /**
     * This method calculates and returns the average number of days for all the rents in the list.<br>
     * @return the average number of days for all the rents in the list, or 0 if the list is empty.
     */
    public double averageRent()
    {
        // Check if the list is empty
        if(this._head == null)
        {
            return 0;
        }

        // Get the total number of days for all the rents
        double sumOfDays = this.getSumOfDays();
        // Get the total number of rents
        double numOfRents = this.getNumOfRents();


        // Return the average number of days per rent
        return sumOfDays/numOfRents;
    }

    /**
     * This method is used to find the car that was last rented<br>
     * 
     * @return Car object of the car that was last rented, or null if the list is empty
     */
    public Car lastCarRent()
    {
        if(this._head == null)
            return null;

        Rent headRent = this._head.getRent();
        Date headReturn = headRent.getReturnDate();
        // Car headCar = headRent.getCar();
        return lastCarRent(this._head, headRent, headReturn);
    }

    /**
     * This method returns the Rent object that has the longest length of renting days.<br>
     * If the list is empty it will return null.<br>
     * 
     * @returnthe Rent object with the longest renting days or null if the list is empty.
     */
    public Rent longestRent()
    {
        // Check if the list is empty
        if(this._head == null)
        {
            return null;
        }

        // Start at the head of the list
        RentNode current = this._head;
        // Initialize theLongestRent to the first Rent in the list
        Rent theLongestRent = new Rent(_head.getRent());
        // Initialize the longest length to 0
        int longest = 0;
    

        // Iterate through the list
        while(current != null)
        {
            // Get the length of the current Rent
            int rentLength = current.getRent().howManyDays();


            // If the current Rent's length is greater than the current longest length
            if(rentLength > longest)
            {
                // Update the longest length
                longest = rentLength;
                // Update the longest Rent
                theLongestRent = new Rent(current.getRent());
            }
            
            // Move to the next Rent in the list
            current = current._next;
        }
        
        // Return the longest Rent
        return theLongestRent;
    }

    /**
     * Returns the most common rate of car in the company.<br>
     * 
     * @return a char representing the most common rate of car in the company (A, B, C, D)
     */
    public char mostCommonRate()
    {
        RentNode current = this._head;

        // Check if the list is empty
        if(current == null)
            return 'N';

        // Initialize variables to keep track of the number of cars of each type    
        int sumA = 0;
        int sumB = 0;
        int sumC = 0;
        int sumD = 0;

        // Iterate through the list
        while(current != null)
        {
            char currentType = current.getRent().getCar().getType();

            // Count the number of cars of each type
            if(currentType == 'A')
                sumA++;
            if(currentType == 'B')
                sumB++;
            if(currentType == 'C')
                sumC++;
            if(currentType == 'D')
                sumD++;
            
            // Move to the next Rent in the list
            current = current._next;
        }

        //call the helper method to find the most common type of car
        return mostCommomRateFinder(sumA, sumB, sumC, sumD);
    }

    //helper method to find the most common type of car
    private char mostCommomRateFinder(int a, int b, int c, int d)
    {
        int maxIndex = -1;
        int maxSum = 0;
        int[] typeSum = new int[5];
       
        // Assign the count of each type to the corresponding index in the array
        typeSum[1] = a;
        typeSum[2] = b;
        typeSum[3] = c;
        typeSum[4] = d;

        // Iterate through the array to find the index of the highest count
        for(int i = 0; i < typeSum.length ; i++)
        {
            //if the current type count is greater than the current max and it's index is greater than the max index, update the max
            if(typeSum[i] >= maxSum && i > maxIndex)
            {
                maxSum = typeSum[i];
                maxIndex = i;
            }
        }

        // Return the corresponding type based on the index
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
    
    /**
        The includes method checks whether the current company's list of rents<br>
        includes all the rents in the given company's list.<br>

        @param otherCompany the company whose list of rents is being checked against the current company's list.
        @return true if the current company's list includes all the rents in the given company's list, false otherwise.
    */
    public boolean includes(Company otherCompany)
    {
        RentNode otherHead = otherCompany._head;

         // if the given company's list is empty, return true
        if(otherHead == null)
            return true;
         // if the current company's list is empty, return false
        if(this._head == null)
            return false; 
        
        // helper method
        return includes(this._head, otherHead);
    }

    // Private helper method for the includes method.
    // Compares each RentNode in the current company's list with the corresponding RentNode in the given company's list.
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

    /**

    This method merges another company's rent list with the current company's rent list.<br>
    The method iterates through the other company's rent list and adds each rent to the current company's list<br>
    using the addRent() method.

    @param otherList The other company's rent list to be merged with the current company's list.
    */
    public void merge(Company otherList) 
    {
        // start iterating through the other company's rent list
        RentNode current = otherList._head;
        
        // iterate through the other company's rent list
        while (current != null) 
        {
            // get the current rent from the other company's list
            Rent rent = current.getRent();
            // add the current rent to the current company's list
            addRent(rent.getName(), rent.getCar(), rent.getPickDate(), rent.getReturnDate());
            // move to the next rent in the other company's list
            current = current._next;
        }
    }


    /**
     * The toString method creates a string representation of the company and all of its rents.<br>
     * @return string representation of the company and all of its rents.
     */
    public String toString()
    {
        // Part 1 of the string, displays the number of rents the company has
        String part1 = ("The company has " + this.getNumOfRents() + " rents");
        // Part 2 of the string, displays the details of each rent
        String part2 = "";
        

        // if the company has rents
        if(this.getNumOfRents() > 0)
        {
            // add a colon and new line to part 1
            part1 += ":" + "\n";
            // start at the head of the list
            RentNode current = this._head;


            // while we haven't reached the end of the list
            while(current != null)
            {
                // add the rent to the string
                part2 += ("\n" + current.getRent().toString() + "\n");
                // move to the next rent
                current = current._next;
            }
        }
        else
        {
            // add a period to the end of part 1
            part1 += ".";
        }
        // return the full string
        return(part1 + part2);
    }


    //----------------------------------------Private Methods----------------------------------------//


    //--------------------addRent Private Methods--------------------//

    // In this method we assume that both rents are sharing the same pickup date
    // the rent with the return date that is further (after) will be considerd as a longer rent. 
    private Boolean addSamePickdate(RentNode current, Rent newRent) 
    {
        Date newRentReturn = newRent.getReturnDate();
        Date newRentPickDate = newRent.getPickDate();

        Date currentReturn = current.getRent().getReturnDate();
    
        Date nextPickDate = current._next.getRent().getPickDate();
        Date nextReturnDate = current._next.getRent().getReturnDate();
    
        /* 
         * Flag that points:
         * - If the both the new rent and the next are sharing the same pickup date
         * and
         * - The new rent's return date is after the next's.
         * 
         * if Both condiotins are met it means that the new rent is longer than the next 
         */  
        boolean newRentIsLongerThanNext = (newRentPickDate.equals(nextPickDate) && newRentReturn.after(nextReturnDate));
    
        // Check if the new rent's return date is after the current return date
        if (newRentReturn.after(currentReturn)) 
        {
            RentNode prev = findPrev(this._head ,current);
            // Check if prev is not null
            if (prev != null) 
            {
                // Sets the prev's new next to be the new rent that points to its old next (current)
                prev.setNext(new RentNode(newRent, current));
            }
            else 
            {
                // if the prev is null, it means that we are dealing with the head of the list. 
                // Therefore sets the new head to be the new rent with a pointer to the old head. 
                this._head = new RentNode(newRent, current);
            }
            return true;
        }

          
        /* 
         * Check if:
         * - The new rent is shorter than the current
         * - (The new rent is longer than the next) or (The new rent has a diffrent pickup date than the new rent)
         * 
         * if the whole statment is true, it means that the new rent could be placed between the current and the next
         */
        else if(newRentReturn.before(currentReturn) && 
        (newRentIsLongerThanNext) || (nextPickDate.equals(newRentPickDate) == false))
            
        {
            // Set the current's next to be the new rent with a pointer to the current's old next. 
            current.setNext(new RentNode(newRent, current._next));
            return true;
        }

        // Check if the new rent is shorter than the current 
        // and that this is the last object of the list
        else if (newRentReturn.before(currentReturn) && current._next == null) 
        {
            current._next = new RentNode(newRent);
            return true;
        } 
        return false;
    }

// This method fins the previous node of a given node. 
private RentNode findPrev(RentNode head, RentNode current)
{
    // the previous node of a head will always be null
    RentNode prev = null;
    
    // Check if the given node is the head
    if (current == head)
    {
        // Return prev = null
        return prev;
    }

     // Move to the next Rent in the list    
    prev = head;

    // Iterate through the list
    while(prev._next != null)
    {
        // Check if the previous next is the give node 
        if(current == prev._next)
        {
            return prev;
        }
        
        // Move to the next Rent in the list
        prev = prev._next;
    }
    //if the node wasn't found returns null 
    return null; 
}

// This method checks a private case of adding a rent before or after the head
// returns true if the new rent was added next to the head 
private boolean addRentHead (Rent newRent)
{
    Date newRentPickDate = newRent.getPickDate();
    // Check if there is any head 
    if (this._head == null) 
    {   
        // List is empty sets the the head to be the new rent 
        this._head = new RentNode(newRent);
        return true;
    }

    // Check if the rent is equal to the head
    if(newRent.equals(this._head.getRent()))
    {
        return false;
    }

    Date headPickDate = this._head.getRent().getPickDate();
    // If new rent's pick date is before the this._head's pick date
    // make the new rent the new this._head
    if (newRentPickDate.before(headPickDate))
    {
        this._head = new RentNode(newRent, this._head);
        return true;
    }

    // Head and newRent are sharing the same pickDate 
    if (newRentPickDate.equals(headPickDate))
    {
        // Check which rent is longer
        int longer = Math.max(this._head.getRent().howManyDays(), newRent.howManyDays());
        
        // If the new rent is longer
        if(newRent.howManyDays() == longer)
        {
            // Sets the new head to be the new rent with a pointer to the old head
            this._head = new RentNode(newRent, this._head);
            return true;
        }
    }
    // the new rent cannot be added next to the head 
    return false;
}

//--------------------removeRent Private Method--------------------//
    /*
     * This recursive boolean method iterate through the list and returns true if a rent has been removed from it. 
     * it gets the desired date of a rental's return date that is needed to be removed from the list. 
     * and the previus node. 
     */
    private boolean removeRent(Date d, RentNode current, RentNode prev)
    {
        // Limit of the recursive method - the end of the list
        if(current == null)
            return false;

        Date returnDate = current.getRent().getReturnDate();

        // If one of the rents has the required date - remove it
        if(d.equals(returnDate))
        {
            // Remove using the private method
            prev.setNext(current._next);
            return true;
        }

        // Recursively call the method with the next RentNode in the list
        return removeRent(d, current._next, current);
    }

    //--------------------getSumOfRents Private Method--------------------//
    // getSumOfRents method helper using recalls itself recursively until it reaches the end of the list
    // while summing up the number of rents. 
    private int getNumOfRents(RentNode node, int numOfRents)
    {
        // Limit of the recursive method - the end of the list 
        if(node == null)
            return numOfRents;
        
        // For every node we apply the recursive method the variable "numOfRents" is increased by 1.
        return getNumOfRents(node._next, numOfRents + 1);
    }

    //--------------------getSumOfDays Private Method--------------------//
    //This method is a private helper method that calculates the total number of rents in the linked list by
    private int getSumOfPrices(RentNode node, int sumOfRents)
    {
        // Limit of the recursive method - the end of the list
        if(node == null)
        {
            return sumOfRents;
        }

        // Get the price of the current RentNode
        int addToSum = node.getRent().getPrice();
        
        // Recursively call the method with the next RentNode in the list and add the current node's price to the sum
        return getSumOfPrices(node._next, sumOfRents + addToSum);
    }

    //--------------------getSumOfDays Private Method--------------------//
    // getSumOfDays method helper using recalls itself recursively until it reaches the end of the list
    // while summing up the number of days of each rent
    private int getSumOfDays(RentNode node, int sumOfDays)
    {
        //limit of the recursive
        if(node == null)
        {
            return sumOfDays;
        }

        //Get the number of days for the current Rent object
        int addToSum = node.getRent().howManyDays();

         //Recursively call the function on the next node, and add the current number of days to the running total
        return getSumOfDays(node._next, sumOfDays + addToSum);
    }

    //--------------------lastCarRent Private Method--------------------//

    //This helper method is a recursive method that finds the last car that was rented in the company
    private Car lastCarRent(RentNode node, Rent latestRent, Date latestDate)
    {
        // Limit
        if(node == null)
        {
            Car latestCar = latestRent.getCar();
            return latestCar;
        }
        // Check if this node's return date is later 
        else if(node.getRent().getReturnDate().after(latestDate))
        {
            // Keep checking with the updated latest return date + rent
            return lastCarRent(node._next, node.getRent(), node.getRent().getReturnDate());
        }
        // Check if both have same return date 
        else if(node.getRent().getReturnDate().equals(latestDate))
        {
            // if this node's pick date is before the current latest rent 
            if(node.getRent().getPickDate().before(latestDate))
                // Keep checking for the the rent with the earliest pickup date 
                // it'll be shown first in out lisy
                return lastCarRent(node._next, node.getRent(), latestDate);
                
        }
        //if not keep on checking for the next object using the recursive method
        return lastCarRent(node._next, latestRent, latestDate);
    }
}

