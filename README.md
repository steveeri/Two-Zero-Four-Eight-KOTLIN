# Two-Zero-Four-Eight-KOTLIN
Android Kotlin version of the classic 2048 game.

Game Overview

2048 is a single-player sliding block puzzle game designed by Italian web developer Gabriele Cirulli. 
The game's objective is to slide numbered tiles on a grid to combine them to create a tile with the number 2048. 
However, one can generally continue to play the game after reaching the goal, creating tiles with larger numbers.

Game Logic Rules

2048 is played on a 4×4 grid, with numbered tiles that slide when a player moves them using directional input matching [Up, Down, Left, Right].
The grid is seeded initially with two randomly placed times of a value of either 2 or 4.
On each play (the program responds to an actionable directiomal input), a new tile will randomly appear in an empty spot on the board with a value of either 2 or 4. 
Tiles in the grid will then slide as far as possible in the chosen direction until they are stopped by another tile, or the edge of the grid.
If two tiles of the same value collide during play, they merge into a new tile into the receiving tile position in the grid.  
The value of the newly merged tile is twice the value of the colliding tiles.
The resulting tile cannot merge with another tile again in the same move. 
Generally the higher the value of the tile the more prominant it becomes with respect to colour and animation.
A user's score starts at zero, and is updated when the new value of any combining tiles is higher than the running score. The score is updated to the value of the newly combined tiles.
The users score during corresponds to the maximum value of any tile in the grid.

Winning and Losing

The game is won when a tile with a value of 2048 appears on the board, hence the name of the game.
After reaching the 2048 tile players "can generally" continue to play to reach higher scores.  Some implementations may cease when the score reaches 2048.
The game ceases when there are no legal sliding or merging moves remaining. E.g. there are no empty spaces and no adjacent tiles with the same value.
The users score at that time remains the value of the maximum value tile in the grid.


Reference => https://en.wikipedia.org/wiki/2048_(video_game)
