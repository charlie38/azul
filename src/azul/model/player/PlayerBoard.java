package azul.model.player;

import azul.model.tile.Tile;

import java.util.ArrayList;
import azul.model.Couple;
public class PlayerBoard
{
    // Size of the board components.
    public static final int MAX_SCORE_TRACK = 100 ;
    public static final int SIZE_WALL = 5 ;
    public static final int SIZE_PATTERN_LINES = 5 ;
    public static final int SIZE_FLOOR_LINE = 7 ;

    // At the top of the board used to track user score.
    private int mScoreTrack ;
    // The stair on the board, where you put your tiles
    // /!\ (the tiles are added from the left to the right but should be displayed from the right to the left).
    private ArrayList<Tile[]> mPatternLines ;
    // To the right of the pattern lines, where you put your tiles at the end.
    private ArrayList<Tile[]> mWall ;
    // At the bottom of the board, where you put your extra tiles.
    private ArrayList<Tile> mFloorLine ;

    /**
     * Class used to keep in memory the player board during all the game.
     * Instantiated for each player before a game start.
     */
    public PlayerBoard()
    {
        mScoreTrack = 0 ;

        initializePatternLines() ;
        initializeWall() ;
        initializeFloorLine() ;
    }

    /**
     * Decorate the wall with the tiles in the pattern lines and track the score.
     * Called by the player at the end of the round.
     * @param asideTiles the tiles in the box cover.
     */
    protected void decorateWall(ArrayList<Tile> asideTiles)
    {
        // Remove floor line tiles.
        for (int i = 0 ; i < SIZE_FLOOR_LINE ; i ++)
        {
            Tile tile = mFloorLine.get(i) ;

            if (tile != Tile.EMPTY && tile != Tile.FIRST_PLAYER_MAKER)
            {
                asideTiles.add(tile) ;

                switch (i)
                {
                    case 0 : case 1 : mScoreTrack -- ; break ;
                    case 2 : case 3 : case 4 : mScoreTrack -= 2 ; break ;
                    case 5 : case 6 : mScoreTrack -= 3 ; break ;
                    default : break ;
                }
            }

            mFloorLine.set(i, Tile.EMPTY) ;
        }

        Couple coordonneesTuilePlacee = new Couple(-1,-1) ;
        // Browse the pattern lines.
        for (int i = 0 ; i < SIZE_PATTERN_LINES ; i ++)
        {
            if (isPatterLineFull(i + 1))
            {
                ArrayList<Tile> tiles = new ArrayList<>() ;
                // If the pattern line is full, add the rightmost (leftmost in the array) tile to the wall.
                try
                {
                    coordonneesTuilePlacee = addToWall(mPatternLines.get(i)[0], i + 1) ;
                }
                catch (PlayerBoardException e)
                {
                    e.printStackTrace() ;
                }
                // Track the player's score.
                mScoreTrack += adjacents(coordonneesTuilePlacee) + bonusIngredient();
                // Remove the remaining tiles of this pattern line.
                for (int j = 0 ; j <= i ; j ++)
                {
                    tiles.add(mPatternLines.get(i)[j]) ;
                    // Initialize the pattern line (which is empty now).
                    mPatternLines.get(i)[j] = Tile.EMPTY ;
                }
                // And put them inside the box cover.
                asideTiles.addAll(tiles) ;
            }
        }
    }

    /**
     * Initialize
     */

    private void initializePatternLines()
    {
        mPatternLines = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_PATTERN_LINES ; i ++)
        {
            Tile[] tiles = new Tile[SIZE_PATTERN_LINES] ;

            for (int j = 0 ; j < i ; j ++)
            {
                tiles[j] = Tile.EMPTY ;
            }

            mPatternLines.add(tiles) ;
        }
    }

    private void initializeWall()
    {
        mWall = new ArrayList<>() ;

        for (int i = 1 ; i <= SIZE_WALL ; i ++)
        {
            Tile[] tiles = new Tile[SIZE_WALL] ;

            for (int j = 0 ; j < SIZE_WALL ; j ++)
            {
                tiles[j] = Tile.EMPTY ;
            }

            mWall.add(tiles) ;
        }
    }

    private void initializeFloorLine()
    {
        mFloorLine = new ArrayList<>() ;

        for (int i = 1; i <= SIZE_FLOOR_LINE; i ++)
        {
            mFloorLine.add(Tile.EMPTY) ;
        }
    }

    /**
     * Add tiles.
     */

    public void addToPatternLine(Tile tile, int row) throws PlayerBoardException
    {
        // Check if it's a good tile color for this row.
        if (! canBePlacedOnPatternLine(tile, row))
        {
            // Try to add a tile on wall case of a different color than the tile one.
            throw new PlayerBoardException(PlayerBoardException.COLOR_PATTERN_LINES) ;
        }

        for (int i = 0 ; i < row ; i ++)
        {
            if (mPatternLines.get(row - 1)[i] == Tile.EMPTY)
            {
                mPatternLines.get(row - 1)[i] = tile ;
                return ;
            }
        }
        // Try to add a tile on a full line.
        throw new PlayerBoardException(PlayerBoardException.FULL_PATTERN_LINES) ;
    }

    public Couple addToWall(Tile tile, int row) throws PlayerBoardException
    {
    	switch(tile) {
    		case CRYSTAL: nbcrystal++; break;
    		case EYE: nbeyes++; break;
    		case CLAW: nbclaws++; break;
    		case FLOWER: nbflower++; break;
    		case MUSHROOM: nbmushrooms++; break;
    	}
        // Search for the corresponding case in the wall, depending on the tile color.
        for (int i = 1 ; i <= SIZE_WALL ; i ++)
        {
            if (tile == Tile.CRYSTAL && isWallCaseCrystal(row, i) && ! isWallCaseNotEmpty(row, i))
            {
                // Add the tile to the wall.
                mWall.get(row - 1)[i - 1] = tile ;
                return new Couple(i,row);
            }
            else if (tile == Tile.EYE && isWallCaseEye(row, i) && ! isWallCaseNotEmpty(row, i))
            {
                // Add the tile to the wall.
                mWall.get(row - 1)[i - 1] = tile ;
                return new Couple(i,row);
            }
            else if (tile == Tile.CLAW && isWallCaseClaw(row, i) && ! isWallCaseNotEmpty(row, i))
            {
                // Add the tile to the wall.
                mWall.get(row - 1)[i - 1] = tile ;
                return new Couple(i,row);
            }

            else if (tile == Tile.FLOWER && isWallCaseFlower(row, i) && ! isWallCaseNotEmpty(row, i))
            {
                // Add the tile to the wall.
                mWall.get(row - 1)[i - 1] = tile ;
                return new Couple(i,row);
            }
            else if (tile == Tile.MUSHROOM && isWallCaseMushroom(row, i) && ! isWallCaseNotEmpty(row, i))
            {
                // Add the tile to the wall.
                mWall.get(row - 1)[i - 1] = tile ;
                return new Couple(i,row);
            }
        }

        // Tried to add a tile in a not empty case.
        throw new PlayerBoardException(PlayerBoardException.FULL_WALL) ;
        
    }

    public void addToFloorLine(Tile tile) throws PlayerBoardException
    {
        for (int i = 0 ; i < SIZE_FLOOR_LINE ; i ++)
        {
            if (mFloorLine.get(i) == Tile.EMPTY)
            {
                mFloorLine.set(i, tile) ;
                return ;
            }
        }
        // Try to add a tile on a full floor line.
        throw new PlayerBoardException(PlayerBoardException.FULL_FLOOR_LINE) ;
    }

    /**
     * Getters.
     */

    public int getScoreTrack()
    {
        return mScoreTrack ;
    }

    public Tile getInPatternLines(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_PATTERN_LINES);
        }

        return mPatternLines.get(i)[j] ;
    }

    public Tile getInWall(int i, int j) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_WALL);
        }
        return mWall.get(i)[j] ;
    }

    public Tile getInFloorLine(int i) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINE || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_FLOOR_LINE);
        }

        return mFloorLine.get(i) ;
    }

    public boolean isPatterLineFull(int row)
    {
        return mPatternLines.get(row - 1)[row - 1] != Tile.EMPTY ;
    }

    public boolean isWallCaseNotEmpty(int row, int column)
    {
        return mWall.get(row - 1)[column - 1] != Tile.EMPTY ;
    }

    public boolean isFloorLineFull()
    {
        return mFloorLine.get(SIZE_FLOOR_LINE - 1) != Tile.EMPTY ;
    }

    public boolean canBePlacedOnPatternLine(Tile tile, int row)
    {
        // Tiles should be of the same color in a row.
        // <=> The tile is the wall is empty
        //      && (this will be the first tile or the tile in the row are of the same color).
        return ! isWallCaseNotEmpty(row, wallColumnFor(tile, row))
                && (mPatternLines.get(row - 1)[0] == Tile.EMPTY || mPatternLines.get(row - 1)[0] == tile) ;
    }

    private int wallColumnFor(Tile tile, int row)
    {
        for (int i = 1 ; i <= SIZE_WALL ; i ++)
        {
            switch (tile)
            {
                case EYE : if (isWallCaseEye(row, i)) return i ; break ;
                case MUSHROOM : if (isWallCaseMushroom(row, i)) return i ; break ;
                case FLOWER : if (isWallCaseFlower(row, i)) return i ; break ;
                case CLAW : if (isWallCaseClaw(row, i)) return i ; break ;
                case CRYSTAL : if (isWallCaseCrystal(row, i)) return i ;
            }
        }

        return 1 ;
    }

    public static boolean canBePlacedOnWall(Tile tile, int row, int column)
    {
        // Check if the corresponding case in the wall is of the tile color.
        return tile == Tile.CRYSTAL && isWallCaseCrystal(row, column)
                || tile == Tile.EYE && isWallCaseEye(row, column)
                || tile == Tile.CLAW && isWallCaseClaw(row, column)
                || tile == Tile.FLOWER && isWallCaseFlower(row, column)
                || tile == Tile.MUSHROOM && isWallCaseMushroom(row, column) ;
    }

    public static boolean isWallCaseMushroom(int row, int column)
    {
        return row == column ;
    }

    public static boolean isWallCaseCrystal(int row, int column)
    {
        return (row == 1 && column == 5) || (row != 1 && row - 1 == column) ;
    }

    public static boolean isWallCaseEye(int row, int column)
    {
        return (row == 1 && column == 4) || (row == 2 && column == 5)
                || (row != 1 && row != 2 && row - 2 == column) ;
    }

    public static boolean isWallCaseClaw(int row, int column)
    {
        return (row == 4 && column == 1) || (row == 5 && column == 2)
                || (row != 4 && row != 5 && row + 2 == column) ;
    }

    public static boolean isWallCaseFlower(int row, int column)
    {
        return (row == 5 && column == 1) || (row != 5 && row + 1 == column) ;
    }

    public boolean isWallRowFull()
    {
        // Check if a row in the wall is full.
        for (int i = 0 ; i < SIZE_WALL ; i ++)
        {
            for (int j = 0 ; j < SIZE_WALL ; j ++)
            {
                if (mWall.get(i)[j] == Tile.EMPTY)
                {
                    break ;
                }

                if (j == SIZE_WALL - 1)
                {
                    return true ;
                }
            }
        }

        return false ;
    }

    public static Tile getWallTile(int row, int column)
    {
        if ((row == 1 && column == 5) || (row != 1 && row - 1 == column))
        {
            return Tile.CRYSTAL ;
        }
        else if ((row == 1 && column == 4) || (row == 2 && column == 5) || (row != 1 && row != 2 && row - 2 == column))
        {
            return Tile.EYE ;
        }
        else if ((row == 4 && column == 1) || (row == 5 && column == 2) || (row != 4 && row != 5 && row + 2 == column))
        {
            return Tile.CLAW ;
        }
        else if ((row == 5 && column == 1) || (row != 5 && row + 1 == column))
        {
            return Tile.FLOWER ;
        }
        else
        {
            return Tile.MUSHROOM ;
        }
    }

    public Tile[] getPatternLine(int row)
    {
        return mPatternLines.get(row - 1) ;
    }

    public ArrayList<Tile> getFloorLine()
    {
        return mFloorLine ;
    }

    /**
     * Setters.
     */

    public void setScoreTrack(int newScore) throws PlayerBoardException
    {
        if (newScore > MAX_SCORE_TRACK || newScore < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_SCORE_TRACK);
        }

        mScoreTrack = newScore ;
    }

    public void setInPatterLines(int i, int j, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_PATTERN_LINES || j >= SIZE_PATTERN_LINES || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_PATTERN_LINES);
        }

        mPatternLines.get(i)[j] = tile ;
    }

    public void setInWall(int i, int j, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_WALL || j >= SIZE_WALL || i < 0 || j < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_WALL);
        }

        mWall.get(i)[j] = tile ;
    }

    public void setInFloorLine(int i, Tile tile) throws PlayerBoardException
    {
        if (i >= SIZE_FLOOR_LINE || i < 0)
        {
            throw new PlayerBoardException(PlayerBoardException.OUT_FLOOR_LINE);
        }

        mFloorLine.set(i, tile) ;
    }

    @Override
    public Object clone()
    {
        PlayerBoard board = new PlayerBoard() ;
        board.mScoreTrack = mScoreTrack ;
        board.mPatternLines = new ArrayList<>() ;
        board.mWall = new ArrayList<>() ;
        board.mFloorLine = (ArrayList<Tile>) mFloorLine.clone();

        for (int i = 0 ; i < SIZE_PATTERN_LINES ; i ++)
        {
            board.mPatternLines.add(mPatternLines.get(i).clone()) ;
        }

        for (int i = 0 ; i < SIZE_WALL ; i ++)
        {
            board.mWall.add(mWall.get(i).clone()) ;
        }

        return board ;
    }

    public static class PlayerBoardException extends Exception
    {
        // Messages that can be threw.
        private static final String FULL_PATTERN_LINES = "This pattern line is full." ;
        private static final String FULL_WALL = "This wall case is not empty." ;
        private static final String FULL_FLOOR_LINE = "The floor line full." ;
        private static final String COLOR_PATTERN_LINES = "This pattern line is not of the tile color." ;
        private static final String OUT_SCORE_TRACK = "Score track outbound." ;
        private static final String OUT_PATTERN_LINES = "Pattern lines outbound." ;
        private static final String OUT_WALL = "Wall outbound." ;
        private static final String OUT_FLOOR_LINE = "Floor line outbound." ;

        /**
         * To throw exception on outbound board values.
         * @param message message threw.
         */
        public PlayerBoardException(String message)
        {
            super(message) ;
        }
    }

    /**
     * Score track.
     */

    private int nbmushrooms=0;
    private int nbclaws=0;
    private int nbeyes=0;
    private int nbcrystal=0;
    private int nbflower=0;

    protected int adjacentsHorizontales(Couple c){
        int result=0;
        int counter=0;

        //Compter les adjacentes a gauche de la case c
        while(c.x+counter>=1 && isWallCaseNotEmpty(c.y,c.x+counter)) {
            result++;
            counter--;
        }
        counter=1;
        //Compter les adjacentes a droite de la case c
        while(c.x+counter<=5 && isWallCaseNotEmpty(c.y,c.x+counter)) {
            result++;
            counter++;
        }
        //Bonus de ligne
        if(result==5) {
            return result+2;
        }
        return result;
    }

    protected int adjacentsVerticales(Couple c) {
        int result=0;
        int counter=0;

        //Compter les adjacentes en haut de la case c
        while(c.y+counter>=1 && isWallCaseNotEmpty(c.y+counter,c.x)) {
            result++;
            counter--;
        }
        counter=1;
        //Compter les adjacentes en bas de la case c
        while(c.y+counter<=5 && isWallCaseNotEmpty(c.y+counter,c.x)) {
            result++;
            counter++;
        }
        //Si result est 1 ca veut dire qu'il n'y a pas de tuiles adjacentes verticalement (donc on renvoit 0)
        if(result==1) return 0;

        //Bonus colonne
        if(result==5) {
            return result+7;
        }
        return result;
    }

    public int adjacents(Couple c) {
        return adjacentsHorizontales(c)+adjacentsVerticales(c);
    }

    public int bonusIngredient() {
        //remettre le nombre d'elements de l'ingredient a -1 permet d'eviter plusieurs fois le bonus
        if(nbeyes==5) {
            nbeyes=-1;
            return 10;
        }
        if(nbmushrooms==5) {
            nbmushrooms=-1;
            return 10;
        }
        if(nbflower==5) {
            nbflower=-1;
            return 10;
        }
        if(nbclaws==5) {
            nbclaws=-1;
            return 10;
        }
        if(nbcrystal==5) {
            nbcrystal=-1;
            return 10;
        }
        return 0;
    }

    //Methode pour retourner 10 si on peut avoir un bonus en remplissant la case de coordonnees c
    //Utilisee pour l'evaluation des configurations de l'IA minimax et l'IA "facile"
    public int bonusPotentielIngredient(Couple c) {

        switch(getWallTile(c.y,c.x)) {
            case CRYSTAL: if(nbcrystal==4) return 10;
            case CLAW: if(nbclaws==4) return 10;
            case FLOWER: if(nbflower==4) return 10;
            case MUSHROOM: if(nbmushrooms==4) return 10;
            case EYE: if(nbeyes==4) return 10;
        }
        return 0;
    }

    public int pointsPotentiels(Couple c) {
        return adjacents(c)+bonusPotentielIngredient(c);
    }
}