# Two-Zero-Four-Eight-KOTLIN
Android Kotlin version of the classic 2048 game.

Game Overview

2048 is a single-player sliding block puzzle game designed by Italian web developer Gabriele Cirulli. 
The game's objective is to slide numbered tiles on a grid to combine them to create a tile with the number 2048. 
However, one can generally continue to play the game after reaching the goal, creating tiles with larger numbers.


Game Logic Rules

2048 is played on a 4×4 grid, with numbered tiles that slide when a player moves them using directional input matching [ Up, Down, Left, Right ].
The grid is seeded initially with two randomly placed, and randomly valued tiles of a value of either 2 or 4.
On each play (the program responds to an actionable directiomal input), sliding or moving all tiles in the direction requested. Tiles in the grid will then slide as far as possible in the chosen direction until they are stopped by another tile, or the edge of the grid. 
If two tiles of the same value collide during play, the sliding tile merges into a new tile into the receiving tile position in the grid.
The value of the newly merged tile is twice the value of the colliding tiles. The resulting new tile cannot merge with another tile again in the same move cycle. At the end of the play all tiles have moved and compacted against the opposing edge to the move direction - such that there are no open or free spaces between tiles in either the horizontal or vertical direction - depending on the users selected move direction. To finish the play cycle a new tile is randomly placed in an empty spot on the board with a value of either 2 or 4. 
Generally the higher the value of the tile obtained the more prominant they are presented to the user with respect to colour and animation.
A user's rolling or recorded score starts at zero, and is updated by adding the value of any combining tiles during each play cycle. 


Winning and Losing

The game is won when a tile with a value of 2048 appears on the board, hence the name of the game.
After reaching the 2048 tile players "can generally" continue to play to reach higher scores.  The rolling score calculated as the game progresses has a reflection on the quality and level of progress achieved during play, but is not directly related to the objective of the game overall - which is to achieve a tile in the greid with a value of 2048. 
The game will otherwise cease when there are no empty cells in the grid, and no legal merging moves remaining. E.g. there are no empty spaces and no adjacent tiles with the same value.
The users rolling score at the end of the game will be the sum of all merged tile values obtained.


Reference => https://en.wikipedia.org/wiki/2048_(video_game)
