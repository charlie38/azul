package azul.view.ui.screen;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import azul.view.Display;
import azul.view.drawable.DrawingTutoPanel;

public class Tutorial extends Screen {
	
	// Label messages.
	private final String MESSAGE_START = "Welcome to the Azul tutorial!" ;
	private final String MESSAGE_0_1 = "This game seems too complicated? Don't worry, I'm here to help you!" ;
	private final String MESSAGE_0_2 = "At the start of your turn, you need to pick one type of ingredient in the bowl." ;
	private final String MESSAGE_0_3 = "Do it yourself, try to pick a Mushroom !";
	private final String MESSAGE_PREVIOUS = "PREVIOUS" ;
	private final String MESSAGE_EXIT = "EXIT" ;
	private final String MESSAGE_NEXT = "NEXT" ;
	// Tutorial messages.
	private final String[] MESSAGE_QUEUE = new String[] { MESSAGE_START, MESSAGE_0_1, MESSAGE_0_2, MESSAGE_0_3 } ;

	// Tutorial.
	private JLabel mMessage ;
	// Navigation.
	private JButton mPrevious ;
	private JButton mNext ;
	private JButton mExit ;
	
	//Tutorial Panel.
	private DrawingTutoPanel mCanvas;

	// Track the tutorial step.
	public int mStep ;

	public Tutorial(Display display, DrawingTutoPanel canvas)
	{
		super(display,1, 1) ;
		
		mCanvas = canvas;

		setLayout(new BorderLayout(HGAP, VGAP)) ;
		setBorder(new EmptyBorder(25, 25, 25, 25)) ;
		setBackground(Display.BG_TUTORIAL) ;
		// Create components and add them.
		createExplanation() ;
		add(mCanvas, BorderLayout.CENTER) ;
		createNavigationFooter() ;
	}

	private void createExplanation()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, HGAP, VGAP)) ;
		panel.setBackground(Display.CL_TERTIARY) ;

		mMessage = createLabel("", Display.CD_SECONDARY, 20) ;
		mNext = createButton(MESSAGE_NEXT, Display.CD_SECONDARY, Display.CL_QUINARY, 20,
				actionEvent -> goToNextStep()) ;
		mNext.setPreferredSize(new Dimension(100, 50)) ;

		panel.add(mMessage) ;
		panel.add(mNext) ;

		add(panel, BorderLayout.NORTH) ;
	}

	private void createNavigationFooter()
	{
	    JPanel panel = new JPanel(new GridLayout(1, 6)) ;
	    panel.setBackground(Display.BG_TUTORIAL) ;

		mPrevious = createButton(MESSAGE_PREVIOUS, Display.CD_SECONDARY, Display.CL_QUINARY, 20,
				actionEvent -> goToPreviousStep()) ;
		mExit = createButton(MESSAGE_EXIT, Display.CD_SECONDARY, Display.CL_PRIMARY, 20,
				actionEvent -> getDisplay().onGoMainMenu()) ;

		panel.add(mPrevious) ;
		panel.add(Box.createHorizontalGlue()) ;
		panel.add(Box.createHorizontalGlue()) ;
		panel.add(Box.createHorizontalGlue()) ;
		panel.add(Box.createHorizontalGlue()) ;
		panel.add(mExit) ;

		add(panel, BorderLayout.SOUTH) ;
	}

	public void goToFirstStep()
	{
		mStep = 0 ;
		update() ;
	}

	private void goToPreviousStep()
	{
	    mStep -- ;
		update() ;
	}

	private void goToNextStep()
	{
		mStep ++ ;
		update() ;
	}

	private void update()
	{
		updatePrevious() ;
		updateNext() ;
		updateMessage() ;
		updateFactory();
	}

	private void updatePrevious()
	{
		mPrevious.setBackground(mStep > 0 ? Display.CL_PRIMARY : Display.CL_QUINARY) ;
		mPrevious.setEnabled(mStep > 0) ;
	}

	private void updateNext()
	{
		mNext.setVisible(mStep < MESSAGE_QUEUE.length - 1) ;
		mNext.setEnabled(mStep < MESSAGE_QUEUE.length - 1) ;
	}

	private void updateMessage()
	{
		mMessage.setText(MESSAGE_QUEUE[mStep]) ;
	}
	
	private void updateFactory()
	{
		if(mStep > 1)
		{
			mCanvas.setVisibleFactory(true);
		}
		else
		{
			mCanvas.setVisibleFactory(false);
		}
	}
}