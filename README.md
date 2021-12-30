# Monte-Carlo-Mazes
An assignment I solved for my algorithms class at Penn State.
Problem Description:
We have two pets - a cat and a mouse. In this challenge, we will be building the most important component of Monte Carlo system for creating mazes for our pets.

The maze will be acceptable if all of the following conditions are met:
  -The cat cannot reach the mouse.
  -The maze does not contain any loops in which the mouse might get stuck. Note that we are fine if the cat might get stuck in a loop.
  
We will consider the maze done, if it is acceptable, and the mouse can get its food.

Our maze will be constructed from an n-by-n grid of rooms, with a starting point for the mouse M in the top left corner, a starting point for the cat in the top-right corner, C,
and a location of the food F in the bottom right. The rows and columns are 0-indexed.

The Monte Carlo system will repeatedly propose randomly selected walls to be removed. We will control whether the removal succeeds by outputing one of the following decisions:

X: The removal is not allowed becase it creates a loop in which the mouse might get stuck.
K: The removal is allowed because the cat cannot reach the mouse and the mouse does not have any loops in which it could get stuck.
C: The removal is not allowed because the cat can reach the mouse.
D: The removal is allowed and the maze could be done - the mouse can now reach their food, the cat cannot reach the mouse, and the mouse does not have any loops in which it could
   get stuck.

Some additional rules:
  1.Any proposed removal that allows the cat to reach the mouse will be rejected, and we will output C. This is true even if a loop is created and/or the removal would also allow
    the mouse to reach its food.

  2.As long as rule 1 does not apply, any proposed removal that allows the mouse to reach its food but also allows the mouse to get stuck in a loop will be rejected, and we will
    output X.

  3.We will accept moves that do not allow the cat to reach the mouse and do not introduce a loop for the mouse to get stuck in, even if the move makes it impossible for the mouse
    to ultimately reach its food.
