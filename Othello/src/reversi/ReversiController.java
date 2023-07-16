package reversi;

public class ReversiController implements IController 
{
	IModel model;
	IView view;

	@Override
	public void initialise(IModel model, IView view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void startup()
	{
		model.setFinished(false);
		// Initialise board
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		for ( int x = 0 ; x < width ; x++ )
			for ( int y = 0 ; y < height ; y++ )
				model.setBoardContents(x, y, 0);
		
		// set up initial pieces
		//white pieces
		model.setBoardContents((width/2)-1, (height/2)-1, 1);
		model.setBoardContents(width/2, height/2, 1);
		//black pieces
		model.setBoardContents(width/2, (height/2)-1, 2);
		model.setBoardContents((width/2)-1, height/2, 2);
		
		//label the active player - white starts
		model.setPlayer(1);
		
		// Refresh all messages and frames
		view.feedbackToUser(1, "White player - choose where to put your piece");
		view.feedbackToUser(2, "Black player - not your turn");
		view.refreshView();
	}

	public int checkCaptures(int player, int x, int y, boolean perform)
	{
		int numCaptures = 0;
		int oppositePlayer = 1;
		if(player == 1)
			oppositePlayer = 2;
		
		int[][] toCapture = new int[model.getBoardHeight()*model.getBoardWidth()][2];
		
		//check if the square already has a piece
		if(model.getBoardContents(x, y) == 1 || model.getBoardContents(x, y) == 2)
		{
			return 0;
		}
		
		//flag the square actually selected
		if(perform)
			model.setBoardContents(x, y, player);
		for (int xoffset=-1; xoffset<=1; xoffset++)
		{
			for (int yoffset=-1; yoffset<=1; yoffset++)
			{				
				boolean keepGoing = true;
				int checkX = x;
				int checkY = y;
				int counter = 0;
				
				if(xoffset == 0 && yoffset == 0) //ignore this case
					keepGoing = false;

				//while loop goes here
				while(keepGoing)
				{
					checkX = checkX + xoffset;
					checkY = checkY + yoffset;
					//check if we are on the edge of the board...
					if(checkX >= model.getBoardWidth() || checkY >= model.getBoardHeight())
					{
						counter = 0;
						break;
					}
					if(checkX < 0 || checkY < 0)
					{
						counter = 0;
						break;
					}
					//opposite player's piece...
					if(model.getBoardContents(checkX,  checkY) == oppositePlayer)
					{
						//add coordinates to the array in case we need to perform capture
						toCapture[counter][0] = checkX;
						toCapture[counter][1] = checkY;
						counter++;
					}
					//player's own piece...
					else if(model.getBoardContents(checkX,  checkY) == player)
					{
						if(perform)
						{
							//perform the capture here
							if(counter > 0)
							{
								for(int i=0; i<counter; i++)
								{
									model.setBoardContents(toCapture[i][0], toCapture[i][1], player);
								}
							}
							
						}
						keepGoing = false;
					}
					//empty square...
					else
					{
						counter = 0;
						keepGoing = false;
					}
				}
				numCaptures += counter;
			}
		}
		if(perform)
			view.refreshView();
		return numCaptures;
	}
	
	@Override
	public void squareSelected(int player, int x, int y)
	{		
		//do nothing if the game has already ended
		if(model.hasFinished() == true)
		{
			return;
		}
		
		//now check if it's the player's turn
		if(player != model.getPlayer())
		{
			view.feedbackToUser(player, "It is not your turn!");
			return;
		}
		int oppositePlayer = 1;
		if(player == 1)
			oppositePlayer = 2;
		
		//now check if the move played is valid... and perform the capture
		if(checkCaptures(player, x, y, false) > 0)
		{
			checkCaptures(player, x, y, true);
			model.setPlayer(oppositePlayer);
			update();
		}
	}

	@Override
	public void doAutomatedMove(int player)
	{
		int xToPlay = 0;
		int yToPlay = 0;
		int potentialCaptures = 0;
		for(int x=0; x<model.getBoardWidth(); x++)
		{
			for(int y=0; y<model.getBoardHeight(); y++)
			{
				if(checkCaptures(player, x, y, false) > potentialCaptures)
				{
					potentialCaptures = checkCaptures(player, x, y, false);
					xToPlay = x;
					yToPlay = y;
				}
			}
		}
		squareSelected(player, xToPlay, yToPlay);
	}

	@Override
	public void update()
	{
		model.setFinished(false);
		
		//check if there are any available moves for either player
		int playerOptions = 0;
		int othersOptions = 0;
		int oppositePlayer = 1;
		if(model.getPlayer()==1)
			oppositePlayer = 2;
		int whitePieces = 0;
		int blackPieces = 0;
		for(int xSearch=0; xSearch<model.getBoardWidth(); xSearch++)
		{
			for(int ySearch=0; ySearch<model.getBoardHeight(); ySearch++)
			{
				if(model.getBoardContents(xSearch, ySearch) == 1)
					whitePieces++;
				else if(model.getBoardContents(xSearch, ySearch) == 2)
					blackPieces++;
				//no piece in that square - check potential captures to see if game finished
				else
				{
					if(checkCaptures(model.getPlayer(), xSearch, ySearch, false)>0)
						playerOptions++;
					if(checkCaptures(oppositePlayer, xSearch, ySearch, false)>0)
						othersOptions++;
				}
			}
		}
		//if neither can play
		if(playerOptions == 0 && othersOptions == 0)
		{
			model.setFinished(true);
		}
		//if current player can't play
		else if(playerOptions == 0)
		{
			model.setPlayer(oppositePlayer);
		}
		
		//set labels for current player
		if(model.getPlayer() == 1)
		{
			view.feedbackToUser(1, "White player - choose where to put your piece");
			view.feedbackToUser(2, "Black player - not your turn");
		}
		else
		{
			view.feedbackToUser(2, "Black player - choose where to put your piece");
			view.feedbackToUser(1, "White player - not your turn");
		}
		//set labels if finished is true
		if(model.hasFinished())
		{
			if(whitePieces > blackPieces) //white wins
			{
				view.feedbackToUser(1, "White won. White " + whitePieces + " to Black " + blackPieces + ". Reset the game to replay.");
				view.feedbackToUser(2, "White won. White " + whitePieces + " to Black " + blackPieces + ". Reset the game to replay.");
			}
			else if(blackPieces > whitePieces) //black wins
			{
				view.feedbackToUser(1, "Black won. Black " + blackPieces + " to White " + whitePieces + ". Reset the game to replay.");
				view.feedbackToUser(2, "Black won. Black " + blackPieces + " to White " + whitePieces + ". Reset the game to replay.");
			}
			else //draw
			{
				view.feedbackToUser(1, "Draw. Both players ended with " + whitePieces + " pieces. Reset game to replay.");
				view.feedbackToUser(2, "Draw. Both players ended with " + whitePieces + " pieces. Reset game to replay.");
			}
		}
		view.refreshView();
	}
}
