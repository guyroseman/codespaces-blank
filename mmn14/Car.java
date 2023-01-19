/**
 * * This program represents a rent managing system
 * for a car rental company
 * 
 * This class represents a Car object
 * With diffrent methods the user can access
 * and apply on diffrent Car objects
 * 
 * @version (2023a)
 * @author Guy Rosman
 */
public class Car
{
    private int _id; //Id of the car 
    private char _type; // type of the car 
    private String _brand; // brand of the car
    private boolean _isManual; // flag that represent the car's gear (true = manual, false = automatic)

    private final int DEFAULT_ID = 9999999;
    private final char DEFAULT_TYPE = 65;
    private final int MIN_ID_VAL = 1_000_000;//lowest value that an id can be (7 digit positive number)
    private final int MAX_ID_VAL = 9_999_999;// highest value that an id can be (7 digit positive number)

    /**
     * Creates a new car object.
     * 
     * the constructor checks the validity of the parameters:
     * id- should be a 7 digits positive number, otherwise set it to 9999999
     * type- should be 'A'\'B'\'C'\'D', otherwise set it to 'A'
     * 
     * @param id       - the id of the car (7 digits positive number)
     * @param type     - the type of the car ('A','B','C' or 'D')   
     * @param brand    - the car's brand
     * @param isManual - flag indicating if the car is manual or automatic
     * 
     */
    public Car(int id, char type, String brand, boolean isManual)
    {
        if (idIsLegal(id))
        {
            _id = id;
        }
        else
        {
            _id = DEFAULT_ID;
        }

        if (typeIsLegal(type)) 
        {
            _type = type;
        }
        else
        {
            _type = DEFAULT_TYPE;
        }

        _brand = brand;
        _isManual = isManual; 
    }

    /**
    * Copy constructor
    * 
    * @param other
    */
    public Car(Car other)
    {
        _id = other._id;
        _type = other._type;
        _brand = other._brand;
        _isManual = other._isManual;
    }

    /**
    * Gets the id of a given car
    * @return the car's id 
    */
    public int getId()
    {
        return _id;
    }

    /**
     * Gets the type of a given car
     * @return the car's type
     */
    public char getType()
    {
        return _type;
    }

    /**
     * Gets the brand of a given car
     * 
     * @return the car's brand
     */    
    public String getBrand()
    {
        return _brand;
    }

    /**
     * Gets the isManual flag of a given car
     * 
     * @return the car's isManual flag
     */
    public boolean isManual()
    {
        return _isManual;
    }

    /**
     * Sets the id  of a given car
     * (only if the given id is valid)
     * 
     * @param id - the id value to be set 
     */
    public void setId (int id)
    {
        if(idIsLegal(id))
            _id = id;
    }

    /**
     * Sets the type of a given car
     * (only if the given given is valid)
     * 
     * @param type - the type value to be set
     */
    public void setType (char type)
    {
        if(typeIsLegal(type))
            _type = type; 
    }

    /**
     * Sets the brand of a given car
     * 
     * @param brand - the brand value to be set
     */
    public void setBrand (String brand)
    {
        _brand = brand;
    }

    /**
     * Sets the isManual flag of a given car
     * 
     * @param isManual - the isManual flag to be set
     */
    public void setIsManual (boolean isManual)
    {
        _isManual = isManual;
    }

    /**
     * Check if two cars are the same
     * Cars are considered the same if they have the same type, brand and gear
     * 
     * @param other - ;the car to compare this car to
     * @return - true if the cars are the same, otherwise false
     * cars will be considered the same if they have the same type, brand and gear.
     * 
     */
    public boolean equals(Car other) {
        boolean typeEquals = (this._type == other._type);
        boolean brandEquals = this._brand.equals(other._brand);
        boolean IsManualEquals = this._isManual == other._isManual;

        return (typeEquals && brandEquals && IsManualEquals);
    }

    /**
     * Check if this car is better than the other car
     * A car is considered better than another car if its type is higher.
     * If both cars have the same type, an automated car is better than a manual
     * car.
     * 
     * @param other - car to compare this car to
     * @return - true if this car is better than the other car, otherwise false
     * 
     */
    public boolean better (Car other)
    {
        boolean isBetter = this._type > other._type;

        if (this._type == other._type &&
            !this._isManual && other._isManual)
        {
            isBetter = true;
        }
        return isBetter;
    }
    
    /**
     * Check if this car is worse than the other car,
     * a car will be considered worse if it's not better than the other car.
     * 
     * @param other - car to compare this car to
     * @return - true if this car is worse than the other car, otherwise false
     * 
     */
    public boolean worse (Car other)
    {
        return !this.better(other);
    }
    
    /**
     * Returns a String object that represents this car
     * 
     * @return - String that represents this car in the following format:
     *         id:1234567 type:B brand:Toyota gear:manual (or auto)
     */
    public String toString() {
        String gear = new String("auto");//creates a new string to print the kind of gear of the given car
        if (_isManual)
            gear = new String("manual");

        return ("id:" + _id + " " + "type:" + _type + " " + "brand:" + _brand + " " + "gear:" + gear);
    }

    //privates
    private boolean idIsLegal(int id)
    {
        return (MIN_ID_VAL <= id && id <= MAX_ID_VAL);
    }

    private boolean typeIsLegal(char type)
    {
        return (type == 'A' || type == 'B' || type == 'C' || type == 'D');
    }
}

