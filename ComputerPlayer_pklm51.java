//This interface represents a player within the game of Hex and the methods that a player should make
public class ComputerPlayer_pklm51 implements PlayerInterface
{
    //BoardInterface board;
    //public Piece playerColour;

    private Piece playerColour = Piece.UNSET;
    private GameState state = GameState.INCOMPLETE;
    private boolean isHumanPlayer = false;
    private boolean firstMove = true;

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

        int xInput = 0;
        int yInput = 0;

        move = new Move();

        //calculate first move
        if (firstMove == true)
        {
            //if computer is RED
            if(playerColour == Piece.RED)
            {
                //place first piece
                if (boardView[widthX/2][0] == Piece.UNSET)
                {
                    try
                    {                        
                        move.setPosition(widthX/2,0);
                    }
                    catch(InvalidPositionException e)
                    {} 
                    firstMove = false;
                    return move;
                }

                else
                {
                    try
                    {
                        move.setPosition((widthX/2+1),0);
                    }
                    catch(InvalidPositionException e)
                    {} 
                    firstMove = false;
                    return move;
                }
            }
            //if computer is BLUE
            if(playerColour == Piece.BLUE)
            {
                //place first piece

                if (boardView[0][heightY/2] == Piece.UNSET)
                {
                    try
                    {
                        move.setPosition(0,heightY/2);
                    }
                    catch(InvalidPositionException e)
                    {} 
                    firstMove = false;
                    return move;
                }

                else
                {
                    try
                    {
                        move.setPosition(0,(heightY/2)+1);
                    }
                    catch(InvalidPositionException e)
                    {} 
                    firstMove = false;
                    return move;
                }
            }

        }

        //traverse further if possible
        if(playerColour == Piece.RED)
        {
            for (int redTraverseY = heightY-1; redTraverseY >= 0 ; redTraverseY --)
            {
                for (int redTraverseX = widthX-1; redTraverseX >= 0; redTraverseX --)
                {
                    //try find neighbour
                    if(boardView[redTraverseX][redTraverseY] == Piece.UNSET)
                    {
                        if (redTraverseY>0)
                        {
                            if(boardView[redTraverseX][redTraverseY-1] == Piece.RED )
                            {
                                try
                                {
                                    move.setPosition(redTraverseX,redTraverseY);
                                }
                                catch(InvalidPositionException e)
                                {} 

                                return move;
                            }
                        } //up left node
                        if (redTraverseY>0 && redTraverseX<(widthX-1))
                        {
                            if(boardView[redTraverseX+1][redTraverseY-1] == Piece.RED)
                            {
                                try
                                {
                                    move.setPosition(redTraverseX,redTraverseY);
                                }
                                catch(InvalidPositionException e)
                                {} 

                                return move;
                            }
                        } //up right node
                    }
                }
            }
        }
        if(playerColour == Piece.BLUE)
        {
            for (int blueTraverseX = widthX-1; blueTraverseX >= 0 ; blueTraverseX --)
            {
                for (int blueTraverseY = heightY-1; blueTraverseY >= 0; blueTraverseY --)
                {
                    //try find neighbour
                    if(boardView[blueTraverseX][blueTraverseY] == Piece.UNSET)
                    {
                        if (blueTraverseX>0)
                        {
                            if(boardView[blueTraverseX-1][blueTraverseY] == Piece.BLUE)
                            {
                                try
                                {
                                    move.setPosition(blueTraverseX,blueTraverseY);
                                }
                                catch(InvalidPositionException e)
                                {} 

                                return move;
                            }
                        } //left node
                        if (blueTraverseX >0 && blueTraverseY<(heightY-1))
                        {
                            if(boardView[blueTraverseX-1][blueTraverseY+1] == Piece.BLUE)
                            {
                                try
                                {
                                    move.setPosition(blueTraverseX,blueTraverseY);
                                }
                                catch(InvalidPositionException e)
                                {} 

                                return move;
                            }
                        } //down left node
                    }
                }
            }
        }

        //ensures a move is always made
        for (int xCoOrdinate = 0; xCoOrdinate < widthX; xCoOrdinate ++)
        {
            for (int yCoOrdinate = 0; yCoOrdinate < heightY; yCoOrdinate ++)
            {
                if(boardView[xCoOrdinate][yCoOrdinate] == Piece.UNSET)
                {
                    try
                    {
                        move.setPosition(xCoOrdinate,yCoOrdinate);
                    }
                    catch(InvalidPositionException e)
                    {}

                    return move;
                }
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