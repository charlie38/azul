package azul.view.ui.screen;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import azul.view.Display;
import azul.view.drawable.DrawingPanel;
import azul.view.drawable.DrawingPanelTuto;

public class Tutorial extends Screen {
	
	//Label messages
	public static String MESSAGE_START = "Welcome to the Azul tutorial !";
	public static String MESSAGE_0_1 = "This game seems complicate ? Don't worry, I'm here to help you !";
	public static String MESSAGE_0_2 = "At the start of your turn, you need to pick one type of ingredient in the bowl. Do it !";
	public static String MESSAGE_NEXT = "Next";
	
	//Message queue
	public static String[] MESSAGE_QUEUE = new String[] {MESSAGE_START,MESSAGE_0_1,MESSAGE_0_2};
	public int numStep;
	
	//JPanel
	private JPanel mExplanation;
	private DrawingPanelTuto mDrawTuto;
	
	//JButton
	private JButton mNext;
	
	//JLabel
	private JLabel mMessage;


	public Tutorial(Display display) {
		super(display,3, 1);
		
		setBackground(Display.BG_TUTORIAL) ;
		setBorder(new EmptyBorder(25, 25, 25, 25)) ;
		
		// Create TextArea
		initExplanation();
		mDrawTuto = new DrawingPanelTuto(display);
		
		add(mExplanation);
		add(mDrawTuto);
		add(createButtonIconTop("SETTINGS", getResourcesLoader().getSettings(), Display.CD_SECONDARY,
                Display.CL_PRIMARY, 20,
                actionEvent -> getDisplay().onGoSettings())) ;
	}
	
	private void initExplanation()
	{
		mExplanation = new JPanel(new GridLayout(2,1));
		mExplanation.setBorder(new EmptyBorder(25, 100, 25, 100));
		mExplanation.setBackground(Display.BG_TUTORIAL_LABEL);
		
		numStep = 0;
		mMessage = new JLabel();
		goToNextStep();
		mMessage.setFont(new Font("Sherif",Font.PLAIN, 25));
		mMessage.setForeground(Display.CL_PRIMARY);
		
		mNext = createButton(MESSAGE_NEXT, Display.CD_SECONDARY, Display.CL_PRIMARY, 20, actionEvent -> goToNextStep());
		mNext.setPreferredSize(new Dimension(650, 50)) ;
	
		mExplanation.add(mMessage);
		mExplanation.add(mNext);
	}
	
	private void goToNextStep()
	{
		if(numStep < MESSAGE_QUEUE.length)
		{
			switch(numStep)
			{
			case 2:
				mNext.setVisible(false);

			}
			mMessage.setText(MESSAGE_QUEUE[numStep]);
		}
		else
		{
			numStep = 0;
			mMessage.setText(MESSAGE_QUEUE[numStep]);
		}
		numStep++;
	}

}
