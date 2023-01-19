/**
 * This program represents a rent managing system 
 * for a car rental company
 * 
 * This class represents a Rent object
 * With diffrent methods the user can access 
 * and apply on diffrent Rent objects
 * 
 * 
 * @version (2023a)
 * @author Guy Rosman
 */

public class Rent {
    private String _name;
    private Car _car;
    private Date _pickDate, _returnDate;

    // prices for difftent types of cars
    private final int TYPE_A_PRICE = 100;
    private final int TYPE_B_PRICE = 150;
    private final int TYPE_C_PRICE = 180;
    private final int TYPE_D_PRICE = 240;
    private final int WEEK_LENGTH = 7; // number of days in a week
    private final double DISCOUNT = 0.9; // 10% discount factor

    /**
     * 
     * Creates a new Rent object
     * The return date must be at least one day after the pickup date,
     * otherwise set it to one day after the pick up date.
     * 
     * @param name the client's name
     * @param car  the rented car
     * @param pick the pickup date
     * @param ret  the return date
     */
    public Rent(String name, Car car, Date pick, Date ret) {
        _name = name;
        _car = new Car(car);
        _pickDate = new Date(pick);

        // Check if there is at least 1 day diffrence between the pickup and return date
        // Check if the pickup date is before the return date
        if (pick.difference(ret) < 1 || pick.after(ret)) {
            _returnDate = new Date(pick.tomorrow());
        } else {
            _returnDate = new Date(ret);
        }
    }

    /**
     * Copy constructor
     * 
     * @param other the rent to be copied
     */
    public Rent(Rent other) {
        _name = other._name;
        _car = new Car(other._car);
        _pickDate = new Date(other._pickDate);
        _returnDate = new Date(other._returnDate);
    }

    /**
     * Get the name of the client from a given rent
     * 
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the car of given rent
     * 
     * @return the car
     */
    public Car getCar() {
        return new Car(_car);
    }

    /**
     * Get the pick up date of given rent
     * 
     * @return the pick up date
     */
    public Date getPickDate() {
        return new Date(_pickDate);
    }

    /**
     * Get the return date of given rent
     * 
     * @return the return date
     */
    public Date getReturnDate() {
        return new Date(_returnDate);
    }

    /**
     * Sets the client's name of a given rent
     * 
     * @param name the client name (You can assume that the name is a valid name)
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * 
     * Sets the rented car of a given rent
     * 
     * @param car the rented car (You can assume that car is not null)
     */
    public void setCar(Car car) {
        _car = new Car(car);
    }

    /**
     * Sets the pick up date
     * The pickup date must be at least one day before the return date,
     * otherwise - don't change the pick up date
     * 
     * @param pickDate the pick up date (You can assume that the pick up date is
     *                 not null)
     */
    public void setPickDate(Date pickDate) {
        // check if the return date is after
        // the given pick up date
        if (this._returnDate.after(pickDate))
            _pickDate = new Date(pickDate);
    }

    /**
     * Sets the return date
     * TThe return date must be at least one day after the pick up date,
     * otherwise - don't change the return date
     * 
     * @param returnDate the return date (You can assume that the pick up date is
     *                   not null)
     */
    public void setReturnDate(Date returnDate) {
        // check if the pick up date is before
        // the given return date
        if (this._pickDate.before(returnDate))
            _returnDate = new Date(returnDate);
    }

    /**
     * Check if 2 rents are the same
     * 
     * @param other the rent to compare this rent to
     * @return true if the rents are the same
     */
    public boolean equals(Rent other) {
        // Check if both name strings are equal
        boolean nameEquals = this._name.equals(other._name);
        // Check if both car objects are equal`
        boolean carEquals = this._car.equals(other._car);
        // Check if both return and pick up dates are equal
        boolean dateEquals = this._pickDate.equals(other._pickDate) && this._returnDate.equals(other._returnDate);

        // if all of the paramatrs are true return true
        return (nameEquals && carEquals && dateEquals);
    }

    /**
     * Return the number of rent days of a given rent
     * 
     * @return the number of rent days
     */
    public int howManyDays() {
        // uses the difference method from date class to calculate the diffrence
        return (this._pickDate.difference(this._returnDate));
    }

    /**
     * Returns the total price of a given rent
     * 
     * @return the total rent price
     */
    public int getPrice() {
        /*
         * every type of car has a diffrent price per every rent day
         * for every week (7 days) that a rent is going a 10% discount will be applied
         * on them
         *
         */

        // Calculate the number of whole weeks
        double totalWeeks = this.howManyDays() / WEEK_LENGTH;
        // Calculate the number of days that left (that doesn't complete to a whole
        // week)
        double totalDays = this.howManyDays() % WEEK_LENGTH;

        // Applying discount to the whole weeks
        double totalPrice = (totalWeeks * WEEK_LENGTH * DISCOUNT) + totalDays;

        return (int) (totalPrice * this.typePrice());
    }

    // Returns the price per day of a given type
    private int typePrice() {
        switch (this._car.getType()) {
            case 'A':
                return TYPE_A_PRICE;
            case 'B':
                return TYPE_B_PRICE;
            case 'C':
                return TYPE_C_PRICE;
            default:
                return TYPE_D_PRICE;
        }
    }

    /**
     * Try to upgrade the car to a better car
     * If the given car is better than the current car of the rent, upgrade it and
     * return the upgrade additional cost, otherwise - don't upgrade
     * 
     * @param newCar the car to upgrade to
     * @return the upgrade cost
     */
    public int upgrade(Car newCar) {
        // This parameter indicates the upgrade cost`
        int addCost = 0;

        // Create a new rent object with the car to upgrade to
        // thus we can compare bwtween the 2 rent objects more easily
        Rent rentNewCar = new Rent(this._name, newCar, this._pickDate, this._returnDate);

        // Check if the new car is better than the current
        if (newCar.better(this._car)) {
            // If so calculate the cost of the upgrade
            addCost = (rentNewCar.getPrice() - this.getPrice());
            // Set the new car to this rent
            this.setCar(newCar);
        }

        // returns the upgrade cost
        return addCost;
    }

    /**
     * Check if there is a double listing of a rent for the same person and car with
     * an overlap in the rental days
     * If there is - return a new rent object with the unified dates, otherwise -
     * return null.
     * 
     * @param other thr other rent
     * @return unified rent or null
     */
    public Rent overlap(Rent other) {

        // Check if the rents has diffrent name or car, if so return null
        if (!this._name.equals(other._name) || !this._car.equals(other._car)) {
            return null;
        }

        // Flag that represent if the other pick up date is in the range of this rent
        // period
        boolean isOtherPickDateBetweenDates = this._pickDate.before(other._pickDate)
                && other._pickDate.before(this._returnDate);

        // Flag that represent if this rent's pick up date is in the range of the other
        // rent period
        boolean isThisPickDateBetweenOther = other._pickDate.before(this._pickDate)
                && this._pickDate.before(other._returnDate);

        // Flag that represent if both rents have the same pick up date
        boolean isSamePickDate = this._pickDate.equals(other._pickDate);

        // Flag that represent if both rents have return date
        boolean isSameReturnDate = this._returnDate.equals(other._returnDate);

        //  Flag that represent if the rents have an overlaping return and pick up date
        boolean isPickDateSameReturnDate = this._returnDate.equals(other._pickDate) || this._pickDate.equals(other._returnDate);

        // Copy this rent into a new object to unified the periodes
        // in case of an overlap
        Rent overlapRent = new Rent(this);

        // Flag to check if an overlap is obligale to occur 
        boolean isOverlap = false;

        // Check if one of the conditions of an overlaping dates is true
        if (isThisPickDateBetweenOther || isOtherPickDateBetweenDates || isSamePickDate || isPickDateSameReturnDate || (isSameReturnDate && !isSamePickDate)) {

            isOverlap = true;
            // Check which pick up date is earliest,
            // the earliest will be set into the new rent object
            if (this._pickDate.before(other._pickDate)) {
                overlapRent.setPickDate(this._pickDate);
            } else {
                overlapRent.setPickDate(other._pickDate);
            }

            // Check which return date is the latest,
            // the latest will be set into the new rent object
            if (this._returnDate.after(other._returnDate)) {
                overlapRent.setReturnDate(this._returnDate);
            } else {
                overlapRent.setReturnDate(other._returnDate);
            }
        }
        

        // retuen the rent object if dates been unified, 
        // otherwise retuen null
        if(isOverlap)
        {
            return overlapRent;
        }
        else
        {
            return null;
        }
        
    }
    
    /**
     * Returns a String that represents this rent
     * 
     * @return String that represents this rent in the following format:
     *         Name:Rama From:30/10/2022 To:12/11/2022 Type:B Days:13 Price:1845
     * 
     */
    public String toString()
    {
        return ("Name:" + this._name + " " + "From:" + this._pickDate.toString() + " " + "To:" + this._returnDate.toString() + " " + "Type:" + this._car.getType() + " " + "Days:" + this.howManyDays() + " " + "Price: " + this.getPrice());
    }

}
