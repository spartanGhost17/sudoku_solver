# sudoku_solver
this is a sudoku solver for a 9x9 board using backtracking 

# This is a view of the board before it is solved

![unsolved](/images/unsolved_sudoku.JPG)

# How it works
The board is solved by checking if a number to be inserted is neither in a row, column or the current sub-grid (sub-grid of its current solution),
until an impass is reached (no good number to insert can be found). If no good number [1..9] can be found; return to previous stack frame, and so on until a find a satifying solution. 

# This is a view of the board after the backTracking solution was applied 

![unsolved](/images/solved_sudoku.JPG)
