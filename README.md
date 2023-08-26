# Othello game in Java
A fully functional graphical implementation of the classic board game, Othello/Reversi, using Java Swing. This Object Oriented project adheres to the Model-View-Controller (MVC) architecture to ensure separation of concerns and modularity. The game provides a graphical user interface (GUI) that allows players to interact with the game board, make moves, and compete against each other or an AI opponent.
### GUIView Class
The ```GUIView``` class, a subclass of the interface ```IView```, is responsible for displaying the game board and managing user interactions through the graphical interface. It displays the Reversi board with initial pieces already played; players can place their pieces during their turns, and the GUI automatically updates the colors of captured pieces. The class interacts with the ```SimpleModel``` and ```SimpleController``` classes through ```IView``` methods, maintaining separation between the GUI and game logic.
### ReversiController Class
The ```ReversiController``` class, a subclass of ```IController```, is implemented to handle user inputs, validate moves, update the game state, and check for win conditions. This class, too, ensires modularity and testing ease.
### SimpleModel Class
The project utilizes the provided `SimpleModel` class to manage the game's state, including the current positions of the pieces on the board.

## Key Features

- **MVC Architecture**: The project follows the MVC architecture, ensuring clear separation between the model (game logic), view (GUI), and controller (user input handling).

  - **Modular Design**: The classes are designed to be modular and reusable, allowing for easy testing and future enhancements. The project demonstrates understanding of object-oriented principles, object instantiation, and referencing.

  - **Compatibility**: The game is designed to be versatile, allowing interchangeable use of different controllers and views due to the dynamic main function.

- **Game Play**: 
  - **Player Interaction**: Players place their pieces on the board by clicking on the available cells during their turns. The controller ensures that only valid moves are enforced, and the game alternates between the players.

  - **Piece Flipping**: When a player makes a move, the pieces of the opponent's color that are trapped between the new piece and existing pieces of the player's color are automatically flipped to the player's color.

  - **Win Detection**: The game detects when a player wins by counting the number of pieces of each color on the board. It announces the winner based on the higher count of pieces.

  - **Initial State**: The game starts with a predefined initial state, where four pieces are already played, mimicking the traditional starting position of Reversi.

  - **Greedy AI**: An additional feature is the implementation of a simple 'greedy' AI, allowing players to play against the computer. The AI makes decisions based on maximizing the number of pieces it can flip in a single move.

- **Standard GUI Elements**: The GUI is created using standard Swing and AWT GUI classes, providing a familiar and user-friendly interface for players.
 
 
An example of the initial board state is as follows:
<p align="center">
  <img src="https://github.com/Tanaya-27/java-othello-game/assets/75646651/a3abd2e2-30c3-4309-bf06-4c0e76c7c589">
</p>



