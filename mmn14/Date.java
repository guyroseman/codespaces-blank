/**
 * This program represents a rent managing system
 * for a car rental company
 * 
 * This class represents a Date object
 * With diffrent methods the user can access
 * and apply on diffrent Date objects
 * 
 * @version (2023a)
 * @author Guy Rosman
 */
public class Date 
{
    private int _day, _month, _year;
    private final int DEFAULT_DAY = 1;
    private final int DEFAULT_MONTH = 1;
    private final int DEFAULT_YEAR = 2000; 

    private final int MIN_DAY = 1;      // Min day value 
    private final int MAX_DAY = 31;     // Max day value in the last month of the year 
    private final int MAX_DAY_LEAP_MONTH = 29; // the number of days in the 2nd month in a leap year
    private final int LEAP_MONTH = 2;          //in a leap year the 2nd month gets an extra day
    private final int MIN_MONTH = 1;    // Min month value
    private final int MAX_MONTH = 12;   // Max month value



    //constructors:
    /**
     *  If the given date is valid - creates a new Date object, otherwise creates the date 1/1/2000
     * @param _day t day in the month (1-31)
     * @param _month t month in the year (1-12)
     * @param _year t year (in 4 digits)
     */
    public Date(int day, int month, int year) {
        
        if (dateIsLegal(day,month,year))
        {
            _day = day;
            _month = month;
            _year = year;
        }
        else
        {
            _day = DEFAULT_DAY;
            _month = DEFAULT_MONTH;
            _year = DEFAULT_YEAR;
        }
    }

    /**
     * Copy Constructor
     * @param other the date to be copied
     */
    public Date(Date other)
    {
        _day = other._day;
        _month = other._month;
        _year = other._year;
    }

    /**
     * Gets the day of a given date
     * @return  the day
     */
    public int getDay() 
    {
        return _day;
    }

    /**
     * Gets the month of a given date
     * @return  the month 
     */
    public int getMonth() 
    {
        return _month;
    }

    /**
     * Gets the year of a given date
     * @return  the year
     */
    public int getYear()
    {
        return _year;
    }
    
    /**
     * Set the day of a given date (only if the date remains valid)
     * @param dayToSet the day to be set
     */
    public void setDay(int dayToSet)
    {
        if(dateIsLegal(dayToSet, this._month, this._year))
            _day = dayToSet;
    }   

    /**
     * Set the month of a given date (only if the date remains valid)
     * @param monthToSet the month to be set
     */
    public void setMonth(int monthToSet)
    {
        if (dateIsLegal(this._day, monthToSet, this._year))
            _month = monthToSet;
    }
    
    /**
     * Set the year of a given date (only if the date remains valid)
     * @param yearToSet the year to be set
     */
    public void setYear(int yearToSet)
    {
        if (dateIsLegal(this._day, this._month, yearToSet))
            _year = yearToSet;
    }

    /**
     * Check if 2 dates equal to eachother
     * @param other the date to compare this date to
     * @return  true if the dates are the same, otherwise false
     */
    public boolean equals(Date other)
    {
        return (this._day == other._day && this._month == other._month && this._year == other._year);
    }

    /**
     * Check if this date is before other date
     * @param other the date to compare this date to
     * @return  true if this date is before other date, otherwise false
     */
    public boolean before(Date other)
    {
        // uses a private method
        // that calcultes the distance of a given date from the beginning of the Christian counting of years and
        
        return (calculateDate(this._day, this._month, this._year) < calculateDate(other._day, other._month, other._year));// compares the given values that the private method return for this and other date.

    }

    /**
     * Check if this date is after other date
     * by using the before method to check if this date is not before the other date
     * 
     * @param other date to compare this date to
     * @return  true if this date is after other date, otherwise false
     */
    public boolean after (Date other)
    {   
        // if this isn't before the other date
        // it means it's after it
        return !this.before(other);
    }

    /**
     * Calculates the difference in days between two dates
     * 
     * @param other the date to calculte the diffrence between
     * @return  the number of days between the dates (non negative value)
     */
    public int difference (Date other)
    {   
        // if this date equals to other there is no change between the two,
        // the diffrence is 0 and we can break this method.
        if(this.equals(other))
        {
            return 0;
        }
        else
        {   //Check the number of days of each date since the beginning of the christian era
            int thisTotalDays = this.calculateDate(this._day, this._month, this._year);
            int otherTotalDays = other.calculateDate(other._day, other._month, other._year);
            
            //return an absloute value of the diffrence between the two
            return Math.abs(thisTotalDays - otherTotalDays);
        }

    }

    /**
     * Returns a String that represents this date
     * 
     * @return  String that represents this date in the following format: day (2
     *         digits) / month(2 digits) / year (4 digits) for example: 02/03/1998
     */
    public String toString()
    {   
       /* 
        * If a given month/day 's value is lower than 10
        * the method will return its value with a '0' digit 
        * to match the required date string representation format.
        */
        if(this._month < 10 && this._day < 10)
            return ("0" + this._day + "/" + "0" + this._month + "/" + this._year);

        else if(this._month >= 10 && this._day < 10)
            return ("0" + this._day + "/" + this._month + "/" + this._year);

        else if(this._month < 10 && this._day >= 10)
            return (this._day + "/" + "0" + this._month + "/" + this._year);

        else
            return (this._day + "/" + this._month + "/" + this._year);
    
    }

    /**
     * Calculate the date of tomorrow
     * 
     * @return  the date of tomorrow
     */
    public Date tomorrow()
    {
       /*
        * Copy this date into tomorrow date object 
        * The tomorrow object will be set to be the date of the next day,
        * after the chnges it will be returned by this method
        */
        Date tomorrow = new Date(this);

        // Check if this is the last day and month of the year 
        if (this._month == MAX_MONTH && this._day == MAX_DAY)
        {
            // if so set month and day to thier minimal value 
            // and add 1 to the given year. 
            tomorrow.setDay(MIN_DAY);
            tomorrow.setMonth(MIN_MONTH);
            tomorrow.setYear(tomorrow.getYear() + 1);
        }

        // Check if this is the last day of the month,
        // and this is not the last month of the year
        else if (daysInMonth(this._month) == this._day && this._month != MAX_MONTH)
        {
            // if so add 1 to the given month
            // set the given day to its minimal value
            tomorrow.setDay(MIN_DAY);
            tomorrow.setMonth(tomorrow.getMonth() + 1);
        }

       /* 
        * Check if this is a leap year
        * Check if the given month is the month that gets extra day in a leap year (leap month)
        * Check if this is the maximal day in this "special month"
        */
        else if (isLeap(this._year) && this._month == LEAP_MONTH && this._day == MAX_DAY_LEAP_MONTH)// check how to improve it
        {
            // if so add 1 to the given month
            // set the given day to its minimal value
            tomorrow.setDay(MIN_DAY);
            tomorrow.setMonth(tomorrow.getMonth() + 1);
        }
        else
        {
            tomorrow.setDay(tomorrow.getDay() + 1);
        }

        return tomorrow;
    }

    //private methods

    //Check if a given year is a leap year
    private boolean isLeap(int year)
    {
        boolean leap = false;
        // if the year is a multiple of 4
        if (year % 4 == 0)
        {
            // if the year is century
            if (year % 100 == 0)
            {
                // if year is divided by 400
                // then it is a leap year
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }
            // if the year is not century
            else
            {
                leap = true;
            }
        }
        return leap;

    }

    // Check if a given date is valid,
    // if so return true, otherwise return false
    private boolean dateIsLegal(int day,int month, int year)
    {
        // The method returns legal, legal will be set fasle if one of the date's conditions are not valid 
        boolean legal = true;

        // Check if a given day/month/year is in a legal range 
        if(day < MIN_DAY && day > daysInMonth(month))
        {
            legal = false;
        }
        else if (month < MIN_MONTH || month > MAX_MONTH)
        {
            legal = false;
        }

        // If an intiger is a 4 digit positive number,
        // the division of it by 1000 will return a number in the range of (1-10)
        else if (year / 1000.0 < 1 || year / 1000.0 > 10)
        {
            legal = false;
        }

        // Check if a given date and month are still valid in case of a leap year (29/02/year)
        else if (isLeap(year)  && month == LEAP_MONTH && day > MAX_DAY_LEAP_MONTH)
        {
            legal = false;
        }
        return legal;
    }

    // Calculate the number of days that passed since the beginning of the christian era
    private int calculateDate(int day, int month, int year)
    {
        if (month < 3) {
            year--;
            month = month + 12;
        }
        // Returns the number of says since the begging of the christian counting
        return 365 * year + year / 4 - year / 100 + year / 400 + ((month + 1) * 306) / 10 + (day - 62);
    }

    // Returns the number of days in a given month 
    // doesn't take into account a case of a leap year. 
    private int daysInMonth(int month)
    {
        switch(month)
        {
            case 1, 3, 5, 7, 8, 10, 12:
                return 31;
            case 4, 6, 9, 11:
                return 30;
            case 2:
                return 28;
            default:
                return 0; // if a given month isn't valid return 0
        }
    }
}