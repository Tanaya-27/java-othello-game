package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;

public class GUIView implements IView
{
	IModel model;
	IController controller;
	
	/**
	 * Constructor
	 */
	public GUIView()
	{
	}
	
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JLabel message1 = new JLabel();
	JLabel message2 = new JLabel();
	JPanel grid1Area = new JPanel();
	JPanel grid2Area = new JPanel();

	@Override
	public void initialise(IModel model, IController controller) 
	{
		// TODO Auto-generated method stub
		this.model = model;
		this.controller = controller;
		
		//grid1Area = buildGrid();
		//grid2Area = buildReverseGrid();
		
		//create the UI
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame1.setTitle("Reversi - White Player");
		frame1.setLocation(100, 50);
		frame2.setTitle("Reversi - Black Player");
		frame2.setLocation(600, 50);
		
		frame1.getContentPane().setLayout(new BorderLayout());
		frame2.getContentPane().setLayout(new BorderLayout());
		
		message1.setFont( new Font( "Arial", Font.BOLD, 14 ));
		message2.setFont( new Font( "Arial", Font.BOLD, 14 ));
		
		frame1.setPreferredSize(new Dimension(500,500));
		frame2.setPreferredSize(new Dimension(500,500));
		
		//grid interfaces
		
		//add bits to the frames
		//message goes at the top, grid centre, buttons bottom
		message1.setText("Initial Text...");
		frame1.add(message1, BorderLayout.NORTH);
		frame1.add(grid1Area, BorderLayout.CENTER);
		
		message2.setText("Initial Text...");
		frame2.add(message2, BorderLayout.NORTH);
		frame2.add(grid2Area, BorderLayout.CENTER);
		
		//AI button
		JButton btnAI1 = new JButton("Greedy AI (play white)");
		btnAI1.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(1); } } );
		//restart button
		JButton btnRestart1 = new JButton("Restart");
		btnRestart1.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.startup(); } } );
		//add buttons to south of border as a panel
		JPanel buttons1 = new JPanel();
		buttons1.setLayout(new BorderLayout());
		buttons1.add(btnAI1, BorderLayout.NORTH);
		buttons1.add(btnRestart1, BorderLayout.SOUTH);
		frame1.add(buttons1, BorderLayout.SOUTH);
		
		//now do for frame 2
		JButton btnAI2 = new JButton("Greedy AI (play black)");
		btnAI2.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(2); } } );		
		JButton btnRestart2 = new JButton("Restart");
		btnRestart2.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.startup(); } } );
		JPanel buttons2 = new JPanel();
		buttons2.setLayout(new BorderLayout());
		buttons2.add(btnAI2, BorderLayout.NORTH);
		buttons2.add(btnRestart2, BorderLayout.SOUTH);
		frame2.add(buttons2, BorderLayout.SOUTH);
		
		frame1.pack();
		frame1.setVisible(true);
		frame2.pack();
		frame2.setVisible(true);

	}
	
	/**
	 * Build the grid
	 * @return the JPanel grid1
	 */
	public JPanel buildGrid()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		JPanel returnGrid = new JPanel();
		
		returnGrid.setLayout(new GridLayout(width, height));
		
		for ( int y = 0 ; y < height ; y++ )
		{
			int yCoord= y;
			for ( int x = 0 ; x < width ; x++ )
			{
				int xCoord = x;
				GUISingleSquare square;
				switch( model.getBoardContents(x, y) )
				{
					case 1:
					{
						square = new GUISingleSquare(Color.white, Color.black, controller, model);
						returnGrid.add(square);
						break;
					}
					case 2:
					{
						square = new GUISingleSquare(Color.black, Color.white, controller, model);
						returnGrid.add(square);
						break;
					}
					default:
					{
						square = new GUISingleSquare(controller, model);
						returnGrid.add(square);
						break;
					}
				}
				square.addActionListener( new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						//model.setPlayer(1);//GET RID OF THIS LATER
						controller.squareSelected(1, xCoord, yCoord); 
					} 
				} );
			}
		}
		
		return returnGrid;
	}
	
	/**
	 * Build the rotated grid for player 2
	 * @return the JPanel grid2
	 */
	public JPanel buildReverseGrid()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		JPanel returnGrid = new JPanel();
		
		returnGrid.setLayout(new GridLayout(width, height));
		
		for ( int y = height-1 ; y >= 0 ; y-- )
		{ 
			int yCoord = y;
			for ( int x = width-1 ; x >= 0 ; x-- )
			{
				int xCoord = x;
				GUISingleSquare square;
				switch( model.getBoardContents(x, y) )
				{
					case 1:
					{
						square = new GUISingleSquare(Color.white, Color.black, controller, model);
						returnGrid.add(square);
						break;
					}
					case 2:
					{
						square = new GUISingleSquare(Color.black, Color.white, controller, model);
						returnGrid.add(square);
						break;
					}
					default:
					{
						square = new GUISingleSquare(controller, model);
						returnGrid.add(square);
						break;
					}
				}
				square.addActionListener( new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						//model.setPlayer(2); //GET RID OF THIS LATER
						controller.squareSelected(2, xCoord, yCoord); 
					} 
				} );
			}

		}
		
		return returnGrid;
	}

	@Override
	public void refreshView() 
	{
		frame1.remove(grid1Area);
		grid1Area = buildGrid();
		frame1.add(grid1Area, BorderLayout.CENTER);
		
		frame2.remove(grid2Area);
		grid2Area = buildReverseGrid();
		frame2.add(grid2Area, BorderLayout.CENTER);
		
		SwingUtilities.updateComponentTreeUI(frame1);
		SwingUtilities.updateComponentTreeUI(frame2);

		//frame1.repaint();
		//frame2.repaint();
	}

	@Override
	public void feedbackToUser(int player, String message) 
	{
		if ( player == 1 )
			message1.setText(message);
		else if ( player == 2 )
			message2.setText(message);
	}
	
}
