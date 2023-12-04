How to implement

Rules of the game
In order to to place a piece on the board, it must flip at least one piece on the board 
Game is over when the board is either full or there are no valid moves for either player
Both players can pass in which they dont make a move.
Game has an initial starting state defineed in the assignment page.
Blac player moves first

Implementation(possible)

Interface for the player
-place piece method
-boolean variable if its their turn
-Enum class variable determining their color.
-method to get the color of the player

Interface for the Disc
-color instance variable for the disc
-toString and other standard methods

Interface for the Board
-startGame method(similar to Klondike)
-isGameOver method(similar to Klondike)
-getScore method(similar to klondike)
-variable to keep track of whose turn it is 
-method to get who is currently playing

Interface for BoardView
-toString method to get everything on the board

Quick Start: In our Implementation of Reversi with this unique Hexagonal board, we had to 
make a unique coordinate system in order to keep track of the pieces on the board.
We used a 2D array of Discs to keep track of the pieces on the board:

    BasicReversi game = new BasicReversi(4);
    game.startGame();

    //blacks turn
    game.placeTile(2, 4);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(BasicReversi.Color.BLACK,
            game.getTileAt(2, 4).getColor());

    //whites turn
    game.placeTile(5, 4);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(BasicReversi.Color.WHITE,
            game.getTileAt(5, 4).getColor());



Key components: We Follow the MVC convention however in this assignment we didn't implement
the controller yet 
In the Model: The Reversi Interface is the highest-level componet as BasicReversi Implements
all the functionalites such as startGame,placeTile etc. For the view ReversiView is a blank
interface, and TextualReversiView is the class that displays the "view" to the user

Key subcomponents: 
IDisc - The interface for the tile (X or 0)
Tuple - Custom Class (Coordinate System)

Source Organization:
Very straight forward, follows MVC convention in the Model handling the logic of the game
and the view handling the display of the game. Majority of the important tests are in the
ModelTests as the main core functionality of the game is in the model and being tested
In the viewTests we test the view to make sure it is displaying the correct information
to the user.


Changes Part 2

Some changes that we made for this part included the changes of adding a ReadOnlyReversi model that was not mutuable 
and then we added it to the view. We had created an IView interface in which we would have had methods in which methods 
such as click and pass move were contained in. We wrote a GUI class that implemented the methods in IView and displayed a
view of the Reversi board. We also created a Hexagon class to store data and attriutes for the hexagons on the board. 

Reversi Part 3 Section

Classes and Interfaces that were added. Features interface which the controller implements in order to act as listener to
the view. We also created ModelFeatures interface that was implemented by the Controller in order for it to act to as a 
listener to the Model. Finally, we created a PlayerFeatures interface that was implemented by the Controller in order for
it to act as a listener for player actions. Our controller class implemented all three of these interfaces in order to act 
as a listener. We also created a Player interface to implement a class for Human Players and AI players. In the human players 
class since the human interacts with the view, the human class did not implement some of the moves such as choose tile. 
However our AI class implemented these methods and hence was able to communicate with our controller.

In addition due to the poor performance on last weeks assignment, we made multiple changes to the view by completely 
redesigning it. We incoroporated some of the feedback from last week by removing the mutable model from the view and 
ensuring that view cannot mutate the model. We also abstracted our view into new classes and interfaces. We designed 
a new interface called IFrame which contained methods that would be implemented by the overall frame. Some of these
methods added listeners to the object allowing it to to listen for commands from the controller.
We also created a new interface called IPanel which was implemented by the panel class and contained methods to add the listeners to the panel.
We also added changes to the Panel class so that when the view was resized, the baord did not get cut off. 

In addition in order to have proper communication between the model and the controllers which acted as listeners, we changed 
the model to have an add listeners method in order to add different and multiple controllers. In addition to that method,
we also created multiple small private helper methods within the model to send notifications to the the listeners and trigger
their methods.