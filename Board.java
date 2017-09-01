public class Board implements BoardInterface
{
    private Piece [][] Board = null; //new int[0][0];
    private boolean [][] visitedNodes = null;
    private boolean [][] adjacentNodes = null;
    private boolean isBoardSet = false;
    private int boardWidth = 0;
    private int boardHeight = 0;

    private Piece piece;

    /**
     * Specifiy the size of the board that we are playing on. Both numbers must be greater than zero
     * 
     * @param  sizeX      how wide the board will be
     * @param  sizeY      how tall the board will be
     * @returns boolean   true if the board could be set successfully
     * 
     * @throws InvalidBoardSizeException  If either size value is less than one.
     * @throws BoardAlreadySizedException If the board has already been created.
     */
    public boolean setBoardSize(int sizeX, int sizeY) throws InvalidBoardSizeException, BoardAlreadySizedException
    {
        boardWidth = sizeX;
        boardHeight = sizeY;

        if(isBoardSet == true)
        {
            throw new BoardAlreadySizedException();
        }

        if(sizeX < 1 || sizeY < 1)
        {
            throw new InvalidBoardSizeException();
        }

        Board = new Piece [sizeX][sizeY];

        for (int xCoOrdinate = 0; xCoOrdinate < boardWidth; xCoOrdinate ++)
        {
            for (int yCoOrdinate = 0; yCoOrdinate < boardHeight; yCoOrdinate ++)
            {
                Board[xCoOrdinate][yCoOrdinate] = Piece.UNSET;
            }
        }

        isBoardSet = true;

        return true;
    }

    /**
     * This method will return a two dimentional array of Pieces which represents the current state of the 
     * board. As this is just a copy of the data it is safe to send to a Player.
     * 
     * @returns Piece[][]  a two dimentional representation of the game board.
     * 
     * @throws  NoBoardDefinedException   Thrown when a call is made to this method before the boardSize 
     * method.
     */
    public Piece[][] getBoardView() throws NoBoardDefinedException
    {
        if(isBoardSet == false)
        {
            throw new NoBoardDefinedException();
        }

        return Board;
    }

    /**
     * Places a piece on the board at the specified location.
     * 
     * @param colour     the colour of the piece to place (RED or BLUE)
     * @param move       the position where you wish to place a piece
     * @return boolean   true if the piece was placed successfully
     * 
     * @throws PositionAlreadyTakenException   if there is already a Piece in this position
     * @throws InvalidPositionException        if the specified position is invalid - e.g. (-1, -1)
     * @throws InvalidColourException          if the colour being set is invalid. E.g. if you try to place two BLUE pieces one after the other
     * @throws NoBoardDefinedException         if the board has yet to be defined with setBoardSize()
     */
    public boolean placePiece(Piece colour, MoveInterface move) throws PositionAlreadyTakenException, InvalidPositionException, InvalidColourException, NoBoardDefinedException
    {

        int desiredX = move.getXPosition();
        int desiredY = move.getYPosition();

        
        if (desiredX < 0 || desiredY < 0 || desiredX >= boardWidth || desiredY >= boardHeight)
        {
            throw new InvalidPositionException();
        }
        if(colour != Piece.RED && colour != Piece.BLUE)
        {
          throw new InvalidColourException();  
        }
        if(isBoardSet == false)
        {
          throw new NoBoardDefinedException();  
        }
        if ( Board [desiredX][desiredY] != Piece.UNSET)
        {
            throw new PositionAlreadyTakenException();
        }
        
        Board [desiredX][desiredY] = colour;
        return true;
    }

    /**
     * Checks to see if either player has won.
     * 
     * @return Piece   RED if red has won, BLUE if blue has won, UNSET if neither player has won.
     * 
     * @throws NoBoardDefinedException  Indicates that this method has been called before the boardSize 
     * method
     */
    public Piece gameWon() throws NoBoardDefinedException
    {
        if(Board == null)
        {
            throw new NoBoardDefinedException();
        }
        int widthX = Board.length; //establishes the boards width
        int heightY = Board[0].length; //establishes the boards height

        for (Piece colourBeingChecked : Piece.values()) //check for both colours
        {
            if (colourBeingChecked == Piece.RED || colourBeingChecked == Piece.BLUE) //so it doesn't check for 'Piece.UNSET'
            {

                adjacentNodes = new boolean [widthX][heightY]; //initialises empty adjacentNodes 2d list
                for (int xCoOrdinate = 0; xCoOrdinate < widthX; xCoOrdinate ++)
                {
                    for (int yCoOrdinate = 0; yCoOrdinate < heightY; yCoOrdinate ++)
                    {
                        adjacentNodes[xCoOrdinate][yCoOrdinate] = false;
                    }
                }

                visitedNodes = new boolean [widthX][heightY]; //initialises empty visitedNodes 2d list
                for (int xCoOrdinate = 0; xCoOrdinate < widthX; xCoOrdinate ++)
                {
                    for (int yCoOrdinate = 0; yCoOrdinate < heightY; yCoOrdinate ++)
                    {
                        visitedNodes[xCoOrdinate][yCoOrdinate] = false;
                    }
                }

                if (colourBeingChecked == Piece.RED) //if checking red, initialise any nodes on top row
                {
                    for (int xInTopRow = 0; xInTopRow < widthX; xInTopRow++) //For each of the pieces on the top row
                    {
                        //starts the search from a top nodes
                        if (Board[xInTopRow][0] == Piece.RED && visitedNodes[xInTopRow][0] == false ) 
                        {
                            adjacentNodes[xInTopRow][0] = true;
                        }
                    }
                }

                if (colourBeingChecked == Piece.BLUE) //if checking BLUE, initialise any nodes on left column
                {
                    for (int yInLeftColumn = 0; yInLeftColumn < heightY; yInLeftColumn++) //For each of the pieces on the top row
                    {
                        //starts the search from a left nodes
                        if (Board[0][yInLeftColumn] == Piece.BLUE && visitedNodes[0][yInLeftColumn] == false ) 
                        {
                            adjacentNodes[0][yInLeftColumn] = true;
                        }
                    }
                }

                //checks every node in the list of adjacent nodes}
                for (int yCoOrdinate = 0; yCoOrdinate < heightY; yCoOrdinate ++)
                {
                    for (int xCoOrdinate = 0; xCoOrdinate < widthX; xCoOrdinate ++)
                    {
                        if((adjacentNodes[xCoOrdinate][yCoOrdinate] == true)&& (visitedNodes[xCoOrdinate][yCoOrdinate] ==false))
                        {
                            int xNeighbour = xCoOrdinate;
                            int yNeighbour = yCoOrdinate;

                            //finds new adjacent nodes 
                            if (xNeighbour>0)
                            {
                                if(Board[xNeighbour-1][yNeighbour] == colourBeingChecked && visitedNodes[xNeighbour-1][yNeighbour]!=true )
                                {adjacentNodes[xNeighbour-1][yNeighbour]=true;}
                            } //left node
                            if (yNeighbour>0)
                            {
                                if(Board[xNeighbour][yNeighbour-1] == colourBeingChecked && visitedNodes[xNeighbour][yNeighbour-1]!=true )
                                {adjacentNodes[xNeighbour][yNeighbour-1]=true;}
                            } //up left node
                            if (yNeighbour>0 && xNeighbour<(widthX-1))
                            {
                                if(Board[xNeighbour+1][yNeighbour-1] == colourBeingChecked && visitedNodes[xNeighbour+1][yNeighbour-1]!=true )
                                {adjacentNodes[xNeighbour+1][yNeighbour-1]=true;}
                            } //up right node
                            if (xNeighbour<(widthX-1))
                            {
                                if(Board[xNeighbour+1][yNeighbour] == colourBeingChecked && visitedNodes[xNeighbour+1][yNeighbour]!=true )
                                {adjacentNodes[xNeighbour+1][yNeighbour]=true;}
                            } //right node
                            if (yNeighbour<(heightY-1))
                            {
                                if(Board[xNeighbour][yNeighbour+1] == colourBeingChecked && visitedNodes[xNeighbour][yNeighbour+1]!=true )
                                {adjacentNodes[xNeighbour][yNeighbour+1]=true;}
                            } //down right node
                            if (xNeighbour>0 && yNeighbour<(heightY-1))
                            {
                                if(Board[xNeighbour-1][yNeighbour+1] == colourBeingChecked && visitedNodes[xNeighbour-1][yNeighbour+1]!=true )
                                {adjacentNodes[xNeighbour-1][yNeighbour+1]=true;}
                            } //down left node

                            visitedNodes[xCoOrdinate][yCoOrdinate] = true;
                            yCoOrdinate = 0;
                            xCoOrdinate = 0;
                        }
                    }
                }

                if(colourBeingChecked == Piece.RED) //checks to see if red has won
                {
                    for (int xTestWon = 0; xTestWon < widthX; xTestWon++)
                    {
                        if(adjacentNodes[xTestWon][heightY-1] == true)
                        {
                            return Piece.RED;
                        }
                    }
                }
                
                if(colourBeingChecked == Piece.BLUE) //checks to see if blue has won
                {
                    for (int yTestWon = 0; yTestWon < heightY; yTestWon++)
                    {
                        if(adjacentNodes[widthX-1][yTestWon] == true)
                        {
                            return Piece.BLUE;
                        }
                    }
                }
            }

        }	

        //If here, there was no winner
        return Piece.UNSET;
    }
}