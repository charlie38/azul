package azul.modele.move;

/**
 * The different types of moves.
 */
public enum Type
{
    PLAYER_TAKE_FACTORY,             // The player takes all the tiles (of the same color) from the factory,
                                     // and put the rest in the middle of the table.
    PLAYER_TAKE_TABLE,               // The player takes all the tiles (of the same color) from the middle of the table.
    PLAYER_PLACE_TILE_IN_PATTERN,    // The player places his tile on one of his board pattern lines.
    PLAYER_PLACE_TILE_IN_WALL,       // The player places his tile on the wall.
    PLAYER_PLACE_TILE_IN_FLOOR       // The player places his tile on his board floor line.
}
