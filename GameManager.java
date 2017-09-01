import java.util.*;
public class GameManager implements GameManagerInterface
{

    public static void main(String[] args) throws Exception {
        GameManager game = new GameManager();
        HumanPlayer player1 = new HumanPlayer();
        ComputerPlayer_pklm51 player2 = new ComputerPlayer_pklm51();

        game.boardSize(5,5);
        game.specifyPlayer(player1, Piece.RED);
        game.specifyPlayer(player2, Piece.BLUE);

        game.playGame();
    }
    private BoardInterface board;
    private MoveInterface currentMove;
    private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
    private boolean isRedTaken = false;
    private boolean isBlueTaken = false;
    private boolean isPlayer1Set = false;
    private boolean isPlayer2Set = false;
    private boolean isBoardSet = false;
    private Piece player1Colour = Piece.UNSET;
    private Piece player2Colour = Piece.UNSET;
    private PlayerInterface player1;
    private PlayerInterface player2;

    /**
     * Define who will be playing each colour. This method will be called twice for each game once for
     * RED and once for BLUE.
     * 
     * @param  player     the player who will be playing red
     * @param  colour     the enum for a Piece (RED or BLUE)
     * @return boolean    true if the player was successfully set to the specified colour
     * 
     * @throws ColourAlreadySetException  If the colour is alredy allocated to a player
     */
    public boolean specifyPlayer(PlayerInterface player, Piece colour) throws InvalidColourException, ColourAlreadySetException
    {
        if (colour != Piece.RED && colour != Piece.BLUE)
        {
            throw new InvalidColourException();
        }

        if ((colour == Piece.RED && isRedTaken == true)||(colour == Piece.BLUE && isBlueTaken == true))
        {
            throw new ColourAlreadySetException();
        }

        player.setColour(colour);
        if (isPlayer1Set == false)
        {
            player1 = player;
            player1Colour = colour;
            isPlayer1Set = true;
        }
        else
        {
            player2 = player;
            player2Colour = colour;
            isPlayer2Set = true;
        }

        if(colour == Piece.RED)
        {
            isRedTaken = true;
        }
        else
        {
            isBlueTaken = true;
        }

        return true;
    }

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
    public boolean boardSize(int sizeX, int sizeY) throws InvalidBoardSizeException, BoardAlreadySizedException
    {

        if(isBoardSet == true)
        {
            throw new BoardAlreadySizedException();
        }
        if(sizeX < 1 || sizeY < 1)
        {
            throw new InvalidBoardSizeException();
        }

        board = new Board();
        board.setBoardSize(sizeX,sizeY);

        isBoardSet = true;
        return true;
    }

    /**
     * The core of the game manager. This requests each player to make a move and plays these out on the 
     * game board.
     */
    public boolean playGame()
    {
        if(player1Colour == Piece.UNSET || player2Colour == Piece.UNSET)
        {
            System.out.println("Both players must be defined before you play a game.");
            return false;
        }

        if(isBoardSet == false)
        {
            board = new Board();
        }
        //Ensures that a board has been set. If not, then allows the user to create one.
        try
        {
            board.getBoardView();
        }
        catch (NoBoardDefinedException e)
        {
            boolean boardValidty = false;
            while (boardValidty == false)
            {
                try{
                    System.out.println("A board must be defined to play a game.");

                    Scanner widthInput = new Scanner(System.in); 
                    System.out.println("Please enter your desired width:");
                    int width = widthInput.nextInt();

                    Scanner heightInput = new Scanner(System.in);
                    System.out.println("Please enter your desired height:");
                    int height = heightInput.nextInt();

                    boardSize(width,height);
                    boardValidty = true;
                }
                catch(InvalidBoardSizeException exc)
                {
                }
                catch(BoardAlreadySizedException exc)
                {
                }
            }
        }

        System.out.println(" ");
        System.out.println(" ");
        GameState isGameWon = GameState.INCOMPLETE;
        GameState player1GameState = GameState.INCOMPLETE;
        GameState player2GameState = GameState.INCOMPLETE;
        //remember to update ALL exceptions!
        try{
            while (isGameWon == GameState.INCOMPLETE)
            {
                //player1
                if (isGameWon == GameState.INCOMPLETE)
                {
                    if (player1 instanceof HumanPlayer)
                    {
                        System.out.println("It is player 1's go (" + player1Colour + ")");
                        boolean fairMovePlayer1 = false;
                        while(fairMovePlayer1 == false)
                        {
                            try{
                                currentMove = new Move();
                                currentMove = player1.makeMove(board.getBoardView());
                                if (currentMove.hasConceded() == true)
                                {
                                    player1GameState = GameState.LOST;
                                    player2GameState = GameState.WON;
                                    isGameWon = GameState.WON;
                                    break;
                                }
                                board.placePiece(player1Colour , currentMove);
                                System.out.println("---------------- MOVE SUCCESSFUL ----------------");
                                fairMovePlayer1 = true;
                            }
                            catch(InvalidPositionException e)
                            {
                                System.out.println("The coordinates you entered were out of bounds. Please try again.");
                            }
                            catch(NoValidMovesException e)
                            {
                            }
                            catch(PositionAlreadyTakenException e)
                            {
                                System.out.println("There is a piece already placed here. Please try again.");
                            }
                        }
                    }
                    if (player1 instanceof ComputerPlayer_pklm51)
                    {
                        System.out.println("It is player 1's go (" + player1Colour + ")");
                        System.out.println("The computer is thinking 'where shall i go'");
                        currentMove = new Move();
                        currentMove = player1.makeMove(board.getBoardView());
                        board.placePiece(player1Colour , currentMove);
                        System.out.println("The computer has put a " + player1Colour + " piece on the following position:");
                        System.out.println("("+ currentMove.getXPosition() +","+ currentMove.getYPosition() +")");
                        System.out.println("---------------- MOVE SUCCESSFUL ----------------");
                    }
                    if(board.gameWon() == player1Colour) //check for winner
                    {
                        player1GameState = GameState.WON;
                        player2GameState = GameState.LOST;
                        isGameWon = GameState.WON;
                        break;
                    }
                }

                //player2
                if (isGameWon == GameState.INCOMPLETE)
                {
                    if (player2 instanceof HumanPlayer)
                    {
                        System.out.println("It is player 2's go (" + player2Colour + ")");
                        boolean fairMovePlayer2 = false;
                        while(fairMovePlayer2 == false)
                        {
                            try{
                                currentMove = new Move();
                                currentMove = player2.makeMove(board.getBoardView());
                                if (currentMove.hasConceded() == true)
                                {
                                    player1GameState = GameState.WON;
                                    player2GameState = GameState.LOST;
                                    isGameWon = GameState.WON;
                                    break;
                                }
                                board.placePiece(player2Colour , currentMove);
                                System.out.println("---------------- MOVE SUCCESSFUL ----------------");
                                fairMovePlayer2 = true;
                            }
                            catch(InvalidPositionException e)
                            {
                                System.out.println("The coordinates you entered were out of bounds. Please try again.");
                            }
                            catch(NoValidMovesException e)
                            {
                            }
                            catch(PositionAlreadyTakenException e)
                            {
                                System.out.println("There is a piece already placed here. Please try again.");
                            }
                        }
                    }
                    if (player2 instanceof ComputerPlayer_pklm51)
                    {
                        System.out.println("It is player 2's go (" + player2Colour + ")");
                        System.out.println("The computer is thinking 'where shall i go'");
                        currentMove = new Move();
                        currentMove = player2.makeMove(board.getBoardView());
                        board.placePiece(player2Colour , currentMove);
                        System.out.println("The computer has put a " + player2Colour + " piece on the following position:");
                        System.out.println("("+ currentMove.getXPosition() +","+ currentMove.getYPosition() +")");
                        System.out.println("---------------- MOVE SUCCESSFUL ----------------");
                    }
                    if(board.gameWon() == player2Colour) //check for winner
                    {
                        player1GameState = GameState.LOST;
                        player2GameState = GameState.WON;
                        isGameWon = GameState.WON;
                        break;
                    }

                }
            }
        }

        catch (NoBoardDefinedException e)
        {}
        catch(InvalidColourException e)
        {}
        catch(InvalidPositionException e)
        {}
        catch(NoValidMovesException e)
        {}
        catch(PositionAlreadyTakenException e)
        {}
        player1.finalGameState(player1GameState);
        player2.finalGameState(player2GameState);
        System.out.println(" ");
        System.out.println("Player 1 (" + player1Colour + ") has " + player1GameState);
        System.out.println("Player 2 (" + player2Colour + ") has " + player2GameState);



        return true;
    }
}