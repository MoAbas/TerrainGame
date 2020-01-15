### TerrainGame
##### A Very Simple Terrain Game Implemented in Java. The terrain consists of h x w tiles.<br/>Players, Titans or Obstacles are distributed randomly across the tiles.<br/>The numbers of Players, Titans and Obstacles can be set by the user.<br/>There are three types of Titans:
- Three-meter
- Five-meter
- 15-meter
##### Each have a score based on their size. All players have a zero score at the beginning of the game.<br/>The status of the terrain for each turn is recorded so that the game can be shown step by step.<br/>There is an option for the game to be run either step by step or quickly(automatically).
#### Rules of The Game:
- If a player encounters another player, both of their scores are increased by one.
- If a player encounters a Titan:
   -  Three-meter Titan: if the Player's score is higher than the Titan's, the Titan is dead, otherwise the Player's score is decreased by one and the Player is placed in a random tile.
   -  Fve-meter Titan: if the Player's score is higher than the Titan's, the Titan is dead, otherwise the Player's score is decreased by half and the Player is placed in a random tile.
   - 15-meter Titan: if the Player's score is higher than the Titan's, the Titan's score is decreased by half (if the titan score is one, then the Titan is dead) and the Player is placed in a random tile, otherwise the Player is dead.
- If a Player encounters a dead Player, the Player adds the dead Player’s score to itself and the dead Player’s score is set to zero.
- The dead Players’ scores are decreased by one every ﬁve turns after their death.
- All Players and Titans can move one tile at a turn.
- All movements must be in random direction (north, south, east or west).
- If a Player moves to an Obstacle, it remains inactive in its place for that turn. If a Titan moves to an Obstacle, the Obstacle is destroyed and the titan completes its movement.
- If all Titans are dead, the game is over.
##### The game has forward and backward buttons to skip through each step.
