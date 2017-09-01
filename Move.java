//Represents a move a player wishes to make
public class Move implements MoveInterface
{
    private int desiredX = 0;
    private int desiredY = 0;
    private boolean hasThePlayerConceded = false;
    /**
     * Set the position that the Player wishes to use - both x and y coordinate.
     * 
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @return    true indicating value set correctly
     * 
     * @throws  InvalidPositionException   The position is invalid. E.g. both x and y are negative.
     */
    public boolean setPosition(int x, int y) throws InvalidPositionException
    {
        desiredX = x;
        desiredY = y;

        if (x < 0 || y < 0)
        {
            System.out.println("The values you entered were not valid. Please try again");
            throw new InvalidPositionException();
        }
        
        return true;
    }
    
    /**
     * Has the player conceded in this move?
     * i.e. have they yielded to the fact that their opponent has won before all required
     * moves are made.
     *
     * @return true if the player has conceded.
     */
    public boolean hasConceded()
    {
        return hasThePlayerConceded;
    }
    
    /**
     * Get the x coordinate of the move.
     * 
     * @return the x coordinate.
     */
    public int getXPosition()
    {
        return desiredX;
    }
    
    /**
     * Get the y coordnate of the move.
     * 
     * @return the y coordinate.
     */
    public int getYPosition()
    {
        return desiredY;
    }
    
    /**
     * Indicate that the player has conceded in this move.
     * 
     * @return true indicating conceded is set.
     */
    public boolean setConceded()
    {
        hasThePlayerConceded = true;
        return true;
    }
}