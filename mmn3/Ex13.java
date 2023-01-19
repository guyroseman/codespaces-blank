/*
 * There are 2 kinds of alternating sequence:
 *  - a sequence that starts with a '0':
 *      010101....
 *  - and a sequence that starts with a '1':
 *      101010....
 * 
 */

public class Ex13 
{

    /**
     * Time complexity: O(n)
     * n is the length of the input string "s". 
     * This is because the code iterates through the entire string once.
     * 
     * Memory complexity: O(1)
     * because only a constant amount of memory is used 
     * (two variables, "switchCount" and "expectedChar") 
     * 
     * 
     * The method checks how many chars are misseplaced for the series that starts with 0
     * for example 000111 in the series that starts with 0 only 2 chars are missplaed 
     * compares between the misplaced chars in the series that starts with 1. 
     * returns the minimum/2 
     * we divide by 2 because each 2 misplaced  chars equals to 1 required swap.
     * 
     * 
     * @param s The given series that we calculate the amount of minimum swaps to reach a valid alternating sequence
     * @return The minimum number of swaps that are needed for the given series to turn into a valid alternating sequence
     */
    public static int alternating (String s)
    {
        // Counts the number of chars that need to be switched
        int switchCount = 0;
        // Flag for the expected char for an appropriate sequence
        char expectedChar = '0';
        
        for (int i = 0; i < s.length(); i++)
        {
            // Checks if the curretnt char equals to the expected sorted sequence
            if(s.charAt(i)== expectedChar)
            {
                switchCount++;
            }
        
            // Switches the vlaue of the expected char
            if(expectedChar == '0')
            {
                expectedChar++;
            }
            else
            {
                expectedChar--;
            }
        }
        
        /*  We calculate the complement which is how many swaps are needed for a sequence that starts with '1'
         * doing so by substracting from the length of the whole string.
         * returning the minimum/2 
         */
    
        int result = Math.min(switchCount, s.length() - switchCount) / 2;
        return result;
    }
 
    /**
     * A. the method what returns the length of the series with the longest even sum in the given array a.
     * 
     * B. Time comlexity: O(n^3)
     *    Meomory complexity O(1)
     * 
     * D. Time comlexity: O(n^2)
     *    n is the length of the input array a. 
     *    This is because the code has two nested loops, the outer loop iterates through each element in the array, 
     *    while the inner loop iterates through all elements that come after the current element in the outer loop.
     *    
     *    Meomory complexity O(1)
     *    because only a constant amount of memory is used (one variable, "temp").
     * 
     * @param a the given array which the method tries to find the longest series with an even sum in. 
     * @return the size of the longest series with an even sum in the given array. 
     */
    public static int what (int []a)
    {
        int longest = 0;

        // Checks that i doesn't get out of the array bounds
        for (int i=0; i<a.length; i++)
        {
            int sum = 0;

            // Checks the sum from the index of i
            for (int j=i; j<a.length; j++)
            {
                // Sums up the value of the current cell 
                sum += a[j];
                if (sum%2 == 0)
                {
                    // If the length of this current series is longer than the current value,
                    // sets it to be the longest length
                    if (j-i+1 > longest)
                        longest = j-i+1;
                }
            }
        }
        return longest;
    }

    /**
     * The boolean method gets an array of intigers, 
     * uses a private method that returns True if there is a legal path from the cell 0 to the last cell in the array.
     * the method advances in the array a number of steps to the right or left according to the value in the cell
     * 
     * @param a array of integers which the method checks the existence of a legal path
     * @return True if there is a legal path
     */
    public static boolean isWay(int[] a)
    {
        return isWay(a, 0, 0);
    }

    /**
     * This recursive boolean method gets an array of intigers, 
     * returns True if there is a legal path from the cell '0' to the last cell in the array.
     * the method advances in the array a number of steps to the right or left according to the value in the cell
     * 
     * @param a array of integers which the method checks the existence of a legal path.
     * @param i the index of the starting point of the path (0).
     * @param steps length of the calculated path
     * @return True if there is a valid path
     */
    private static boolean isWay(int[] a, int i, int steps) 
    { 
        // Out of bounds, not a valid path
        if (i < 0 || i >= a.length)
            return false;

        // Already visited this cell, not a valid path
        else if (a[i] == -1) 
            return false;

        // End of the array, valid path
        else if (i == a.length - 1) 
            return true;

        // The path is greater that the length of the array, not a valid path
        else if (steps >= a.length - 1) 
            return false;

        int numSteps = a[i];
        a[i] = -1; // Mark cell as visited

        boolean result = isWay(a, i + numSteps, steps + 1) || isWay(a, i - numSteps, steps + 1);

        a[i] = numSteps; // Reset cell
        return result;
    }
    
    /**
     * The method gets a squared array of positive integers anf the number '-1'.
     * and uses a private method that
     * returns the length of the shortest path to the number '-1' from a given cell.
     * if there is no path the method will return '-1'.
     * 
     * @param drm squared array of positive integers and the number '-1'.
     * @param i row index of the starting point.
     * @param j column index of the starting point.
     * @return the length of the shortest path to the number '-1' or if there is no such valid path returns the number '-1'.
     */
    public static int prince(int[][] drm, int i, int j)
    {
        // All values will be false by default.
        boolean visited[][]  = new boolean[drm.length][drm[0].length];
       
        // Calculates the vaule of the shortest path, usng the private method princeRoad. 
        int result = princeRoad(drm, visited, i, j, 1);
        
        /*
         * if the result equals to the biggest value an integer could recive,
         * it means that there is no vaild path to the number -1 in the given array.
         */
        if(result == Integer.MAX_VALUE)
            return -1;
        return result;
        
    }
    
    /**
     * The method gets a squared array of positive integers anf the number '-1'.
     * returns the length of the shortest path to the number -1 from a given cell.
     * if there is no path the method will return '-1'.
     * 
     * rules for jumping to the next cell:
     * - can go forward or backwards in a row or in a coulmn each time
     * 
     * - if the value of the neighbor's cell is bigger, 
     *   a jump will be valid only if it's value is greater than the current cell's value not more than 1 points. 
     * 
     * - if the value of the neighbor's cell is smaller, 
     *   a jump will be valid only if it's value is smaller than the current cell's value not more than 2 points. 
     * 
     * - if the value of both cells are equal the jump is valid.
     * 
     * 
     * 
     * @param drm squared array of positive integers and the number '-1'.
     * @param visited a boolean array that indicates if a cell has been visited already.
     * @param i the row index of the current cell. 
     * @param j the column index of the current cell.
     * @param pathLength the length of the path 
     * @return the length of the shortest path to the number '-1' or if there is no such valid path returns "Integer.MAX_VALUE".
     */
    public static int princeRoad(int[][] drm,boolean[][] visited,int i, int j, int pathLength) {
    // Check if the current position is out of bounds or has been visited
    if (i < 0 || i >= drm.length || j < 0 || j >= drm[0].length || visited[i][j]) 
    {
        return Integer.MAX_VALUE;
    }

    // Check if the current position is -1
    if (drm[i][j] == -1) 
    {
        return pathLength;
    }

    // Mark the current position as visited
    visited[i][j] = true;


    /*
     * the method uses the integer minPathLength as a tool to find the shortest path. 
     * we set it's value to the highest value an integer could get.
     * thus if any of the calls to the method will land on the cell with the value of '-1',
     * it'll return the sum of number of steps that have been made in its path so far
     * (which is being indicated by the integer pathLenght that we add 1 to it's value each time we call the method)
     */

    int minPathLength = Integer.MAX_VALUE;

    // Try to move to the top neighboring cell
    if (jumpValid(drm, visited, i, j, -1, 0)) 
    {
        minPathLength = Math.min(minPathLength, princeRoad(drm, visited,i - 1, j, pathLength + 1));

        //clears the cell from the visited value, so paths could be checked using the same cell.
        visited[i - 1][j] = false; 
    }

    // Try to move to the bottom neighboring cell
    if (jumpValid(drm, visited, i, j, 1, 0)) 
    {
        minPathLength = Math.min(minPathLength, princeRoad(drm, visited, i + 1, j, pathLength + 1));
        visited[i + 1][j] = false;
    }

    // Try to move to the left neighboring cell
    if (jumpValid(drm, visited, i, j, 0, -1)) 
    {
        minPathLength = Math.min(minPathLength, princeRoad(drm, visited, i, j - 1, pathLength + 1));
        visited[i][j - 1] = false;
    }

    // Try to move to the right neighboring cell
    if (jumpValid(drm, visited, i, j, 0, 1)) 
    {
        minPathLength = Math.min(minPathLength, princeRoad(drm, visited, i, j + 1, pathLength + 1));
        visited[i][j + 1] = false;
    }

    return minPathLength;
}

    /**
     * A boolean method that checks if a jump between cells is valid. 
     * 
     * @param drm squared array of positive integers and the number '-1'.
     * @param visited a boolean array that indicates if a cell has been visited already.
     * @param i row index of the current cell. 
     * @param j column index of the current cell.
     * @param di the diffrence in the row index from the current cell to the new cell.
     * @param dj the diffrence in the column index from the current cell to the new cell. 
     * @return True if a jump between cells is valid. 
     */
    public static boolean jumpValid(int[][] drm, boolean[][] visited, int i, int j, int di, int dj) 
    {

        // Check if the new position is out of bounds
        if (i + di < 0 || i + di >= drm.length || j + dj < 0 || j + dj >= drm[0].length || visited[i + di][j + dj]) {
            return false;
        }
        // Check if the new position is -1
        else if(drm[i + di][j + dj] == -1)
            return true;

        // Check if the value of the neighboring cell is not more than '2' points lower or '1' point higher than the value of the current cell
        int allowedDown = 2;
        int allowedUp = -1; 

        // The diffrence between the values of the current cell and the neigbor's cell
        int difference = drm[i][j] - drm[i + di][j + dj];
        
        /*
         * If the difference is smaller than '0'
         * it meand that the vaule of the neighbor's cell is greater that the current cell's value, 
         * so we check that the diffrence isn't lower than the value of '-1' (the limit for going upwards in value between cells).
         * 
         * else the value of the neighbor's cell is smaller than the current cell's value,
         * so we check that the diffrence isn't greater than the vaule of 2 (the limit of going downwards in value between cells).
         */
        if(difference < 0)
            return(allowedUp <= difference);
        return(allowedDown >= difference);
    }

}
