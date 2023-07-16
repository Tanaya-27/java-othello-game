package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class GUISingleSquare extends JButton
{
	//IModel model;
	//IController controller;
	
	Color drawColour;
	Color borderColour;
	int borderSize;
	int pieceWidth;
	int pieceHeight;
	Color pieceColour;
	int pieceBorderSize;
	Color pieceBorderCol;
	
	public GUISingleSquare(int width, int height, Color colour, int borderWidth, Color borderCol, int ovalWidth, int ovalHeight, 
			Color ovalColour, int ovalBorderW, Color ovalBorderCol, IController controller, IModel model)
	{
		//this.model = Model;
		//this.controller = Controller;
		
		drawColour = colour;
		borderColour = borderCol;
		borderSize = borderWidth;
		
		pieceWidth = ovalWidth;
		pieceHeight = ovalHeight;
		pieceColour = ovalColour;
		pieceBorderSize = ovalBorderW;
		pieceBorderCol = ovalBorderCol;
		
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		
//		addActionListener( new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				controller.squareSelected(model.getPlayer(), 1, 1); 
//			} 
//		} );
	}
	
	//simpler constructor, which uses the complex one ^ 
	//for green square with a piece - specify piece colour then opposite
	public GUISingleSquare(Color pieceColour, Color pieceBorderCol, IController controller, IModel model)
	{
		this(50, 50, Color.green, 1, Color.black, 46, 46, pieceColour, 1, pieceBorderCol, controller, model);
	}
	
	//default constructor - empty green square
	public GUISingleSquare(IController controller, IModel model)
	{
		this(50, 50, Color.green, 1, Color.black, 0, 0, null, 0, null, controller, model);
	}
	
	protected void paintComponent(Graphics g)
	{		
		if ( borderColour != null )
		{
			g.setColor(borderColour);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if ( drawColour != null )
		{
			g.setColor(drawColour);
			g.fillRect(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
		if( pieceBorderCol != null)
		{
			g.setColor(pieceBorderCol);
			g.fillOval((getWidth()-pieceWidth)/2, (getHeight()-pieceHeight)/2, pieceWidth, pieceHeight);
		}
		if ( pieceColour != null)
		{
			g.setColor(pieceColour);
			g.fillOval(((getWidth()-pieceWidth)/2)+pieceBorderSize, ((getHeight()-pieceHeight)/2)+pieceBorderSize, pieceWidth-pieceBorderSize*2, pieceHeight-pieceBorderSize*2);
		}
	}
	
	
	
	
	
	
}
