import java.util.Scanner;
//This interface represents a player within the game of Hex and the methods that a player should make
public class HumanPlayer implements PlayerInterface
{
    private Piece playerColour = Piece.UNSET;
    private GameState state = GameState.INCOMPLETE;
    
    private  boolean isHumanPlayer = true;

    private MoveInterface move;
    private BoardInterface board;
    /**
     * Ask the player to make a move.
     * 
     * @param  boardView   the current state of the board
     * @return        a Move object representing the desired place to place a piece
     * 
     * @throws NoValidMovesException Indicates that no valid moves are possible - e.g. all cells on the
     * board are already occupied by a piece.
     */
    public MoveInterface makeMove(Piece[][] boardView) throws NoValidMovesException
    {
        int widthX = boardView.length;
        int heightY = boardView[0].length;
        
        //checks to see if every cell has been taken, and throws an exception if it has
        int cellCount = 0;
        for (int xCoOrdinate = 0; xCoOrdinate < widthX; xCoOrdinate ++)
        {
            for (int yCoOrdinate = 0; yCoOrdinate < heightY; yCoOrdinate ++)
            {
                if(boardView[xCoOrdinate][yCoOrdinate] != Piece.UNSET)
                {
                    cellCount++;
                }
            }
        }
        if(cellCount == widthX*heightY)
        {
            throw new NoValidMovesException();
        }

        
        boolean evenHeight = true; //Used when printing the board
        if(heightY%2 == 0)
        {
            evenHeight = true;
        }
        else
        {
            evenHeight = false;
        }

        System.out.println(" ");
        for (int i = 0; i<=((widthX*4)-1); i++)
        {
            System.out.print(" ");
        }
        System.out.print("RED");
        System.out.println(" ");

        for (int x = 0; x< heightY; x++)
        {
            System.out.print(" ");
            if(x == 0)
            {
                for (int i = 0; i< widthX; i++)
                {
                    System.out.print("    ^   ");
                }

                System.out.println(" ");
                System.out.print(" ");
                for (int i = 0; i< widthX; i++)
                {
                    System.out.print("  /   \\ ");
                }
                System.out.println(" ");
                System.out.print(" ");
                for (int i = 0; i< widthX; i++)
                {
                    System.out.print(" /     \\");
                }
            }
            System.out.println(" ");
            System.out.print(" ");

            for (int y = 0; y< x; y++) //Creates the indent for future rows
            {
                System.out.print("    ");
            }
            System.out.print("|"); //Top line
            for (int i = 0; i< widthX; i++)
            {
                System.out.print("       |");
            }
            System.out.println(" ");

            //if (y != heightY/2)
            for (int y = 0; y<x; y++) //Creates the indent for future rows
            {
                if((x == heightY/2) && (evenHeight==false) && (y == x-1))
                {
                    System.out.print("BLUE");
                }
                else
                {
                    System.out.print("    ");
                }
            }
            System.out.print(" ");
            System.out.print("|"); //middle line IMPORTANT ONE THIS
            for (int i = 0; i< widthX; i++)
            {
                if (boardView[i][x] == Piece.UNSET)
                {
                    if (i < 10 && x < 10)
                    {
                        System.out.print("  "+ i + "," +  x + "  |"); //gives co-ordinates
                    }
                    else if (i >= 10 && x >= 10)
                    {
                        System.out.print(" "+ i + "," +  x + " |"); //gives co-ordinates
                    }
                    else if (i >= 10)
                    {
                        System.out.print(" "+ i + "," +  x + "  |"); //gives co-ordinates
                    }
                    else if (x >= 10)
                    {
                        System.out.print("  "+ i + "," +  x + " |"); //gives co-ordinates
                    }
                    
                    
                }
                else if (boardView[i][x] == Piece.RED)
                {
                    System.out.print("  RED  |");
                }
                else if (boardView[i][x] == Piece.BLUE)
                {
                    System.out.print(" BLUE  |");
                }

            }
            if((x == heightY/2) && (evenHeight==false))
            {
                System.out.print(" BLUE");
            }
            System.out.println(" ");
            System.out.print(" ");
            for (int y = 0; y< x; y++) //Creates the indent for future rows
            {
                System.out.print("    ");
            }
            System.out.print("|"); //bottom line
            for (int i = 0; i< widthX; i++)
            {
                System.out.print("       |");
            }
            System.out.println(" ");
            System.out.print(" ");
            for (int y = 0; y< x; y++) //Creates the indent for future rows
            {
                System.out.print("    ");
            }
            for (int i = 0; i< widthX; i++)
            {
                System.out.print(" \\     /");
            }
            if(x+1 != heightY) //Prevents an extra dash from being created on the last row
            {
                System.out.print(" \\");  
            }
            System.out.println(" ");
            System.out.print(" ");
            for (int y = 0; y< x; y++) //Creates the indent for future rows
            {
                System.out.print("    ");
            }
            for (int i = 0; i< widthX; i++)
            {
                System.out.print("  \\   / ");
            }
            if(x+1 != heightY) //Prevents an extra dash from being created on the last row
            {
                System.out.print("  \\");  
            }
            System.out.println(" ");

            for (int y = 0; y<x; y++) //Creates the indent for future rows
            {
                if((x == (heightY/2)-1) && (evenHeight==true) && (y == x-1))
                {
                    System.out.print("BLUE");
                }
                else
                {
                    System.out.print("    ");
                }
            }
            System.out.print(" ");
            for (int i = 0; i< widthX; i++)
            {
                System.out.print("    v   ");
            }
            if(x+1 != heightY) //Prevents an extra dash from being created on the last row
            {
                System.out.print("   \\");  
            }
            if((x == (heightY/2)-1) && (evenHeight==true))
            {
                System.out.print("  BLUE");
            }

            if(x == heightY-1)
            {
                System.out.println(" ");
                for (int y = 0; y< x; y++) //Creates the indent for future rows
                {
                    System.out.print("    ");
                }
                for (int i = 0; i<=((widthX*4)-1); i++)
                {
                    System.out.print(" ");
                }
                System.out.print("RED");
            }
        }
        System.out.println(" ");

        move = new Move();

        Scanner decision = new Scanner(System.in);
        System.out.println("Enter anything to make a move or enter 'concede' to forfeit");
        String test = decision.next();
        if (test.equals("concede"))
        {
            move.setConceded();
            return move;
        }

        boolean coOrdinatesValidity = false;

        while (coOrdinatesValidity == false)
        {
            try
            {
                Scanner x = new Scanner(System.in);  
                System.out.println("Enter a desired x value: ");
                int xInput = x.nextInt();

                Scanner y = new Scanner(System.in);  
                System.out.println("Enter a desired y value: ");
                int yInput = y.nextInt();

                move.setPosition(xInput,yInput);

                coOrdinatesValidity = true;

            }
            catch(InvalidPositionException e)
            {

            }
        }
        
        return move;
    }

    /**
     * Set the colour that this player will be
     * 
     * @param colour  A Piece (RED/BLUE) that this player will be
     * @return   true indicating that the method succeeded
     * 
     * @throws InvalidColourException   A colour other than RED/BLUE was provided
     * @throws ColourAlreadySetException  The colour has already been set for this player.
     */
    public boolean setColour(Piece colour) throws InvalidColourException, ColourAlreadySetException
    {
        if(colour != Piece.BLUE && colour != Piece.RED)
        {
            throw new InvalidColourException();
        }

        if(colour == playerColour)
        {
            throw new ColourAlreadySetException();
        }

        playerColour = colour;

        return true;
    }

    /**
     * Informs the player of the final game state. Player has Won, lost.
     * 
     * @param state   either WON or LOST
     * @return   true indicating method has compleated successfully.
     * 
     */
    public boolean finalGameState(GameState state)
    {
        this.state = state;
        return true;
    }

}