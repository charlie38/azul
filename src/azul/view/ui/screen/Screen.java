package azul.view.ui.screen;

import azul.model.Game;
import azul.view.Display;
import azul.view.component.JHintTextField;
import azul.view.resource.ResourcesLoader;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observer;

public abstract class Screen extends JPanel implements Observer
{
    // Layout padding.
    public static final int HGAP = 100 ;
    public static final int VGAP = 20 ;

    // Root ref.
    private Display mDisplay ;

    /**
     * Contains a UI screen.
     * @param display the root.
     * @param nbRows the layout number of rows.
     * @param nbCols the layout number of columns.
     */
    public Screen(Display display, int nbRows, int nbCols)
    {
        mDisplay = display ;

        getGame().addObserver(this) ;

        setLayout(new GridLayout(nbRows, nbCols, HGAP, VGAP)) ;
    }

    @Override
    public void update(java.util.Observable observable, Object o)
    {
        // Do nothing for most of screens.
    }

    protected JButton createButton(String message, Color color, Color bgColor, float pt, ActionListener listener)
    {
        JButton button = new JButton(message) ;
        button.setFont(getResourcesLoader().getFont(pt)) ;
        button.setBackground(bgColor) ;
        button.setForeground(color) ;
        button.setBorderPainted(false) ;
        button.setFocusPainted(false) ;
        button.addActionListener(listener) ;

        return button ;
    }

    protected JButton createButton(Image image, Color color, Color bgColor, float pt, ActionListener listener)
    {
        JButton button = createButton("", color, bgColor, pt, listener) ;
        button.setIcon(new ImageIcon(image)) ;

        return button ;
    }

    protected JButton createButton(String message, Image image, Color color, Color bgColor, float pt, ActionListener listener)
    {
        JButton button = createButton(message, color, bgColor, pt, listener) ;
        button.setIcon(new ImageIcon(image)) ;

        return button ;
    }

    protected JButton createButtonIconSide(String message, Image image, boolean iconRightSide, Color color, Color bgColor,
                                           float pt, ActionListener listener)
    {
        JButton button = createButton(
                iconRightSide ? "<html><div align=left width=200px>" + message + "</dive></html>" : message,
                image, color, bgColor, pt, listener) ;
        button.setIconTextGap(40) ;
        button.setHorizontalAlignment(iconRightSide ? SwingConstants.LEFT : SwingConstants.RIGHT) ;
        button.setHorizontalTextPosition(iconRightSide ? SwingConstants.RIGHT : SwingConstants.LEFT) ;

        return button ;
    }

    protected JButton createButtonIconTop(String message, Image image, Color color, Color bgColor,
                                          float pt, ActionListener listener)
    {
        JButton button = createButton(message, image, color, bgColor, pt, listener) ;
        button.setIconTextGap(10) ;
        button.setVerticalTextPosition(SwingConstants.BOTTOM) ;
        button.setHorizontalTextPosition(SwingConstants.CENTER) ;

        return button ;
    }

    protected JLabel createLabel(String message, Color color, int pt)
    {
        JLabel label = new JLabel(message, SwingConstants.CENTER) ;
        label.setFont(getResourcesLoader().getFont(pt)) ;
        label.setForeground(color) ;

        return label ;
    }

    protected JLabel createLabel(String message, Color color, Color bgColor, int pt)
    {
        JLabel label = createLabel(message, color, pt) ;
        label.setBackground(bgColor); ;

        return label ;
    }

    protected JHintTextField createTextField(String hint, Color hintColor, Color inputColor, Color bgColor, int pt)
    {
        JHintTextField label = new JHintTextField(hint, hintColor) ;
        label.setFont(getResourcesLoader().getFont(pt)) ;
        label.setForeground(inputColor);
        label.setBackground(bgColor) ;
        label.setHorizontalAlignment(SwingConstants.CENTER) ;

        return label ;
    }

    protected Choice createSpinner(String[] values, Color txtColor, Color bgColor, int pt)
    {
        Choice choice = new Choice() ;

        for (String value : values)
        {
            choice.addItem(value) ;
        }

        choice.select(values[0]) ;
        choice.setFont(getResourcesLoader().getFont(pt)) ;
        choice.setForeground(txtColor);
        choice.setBackground(bgColor) ;

        return choice ;
    }

    protected Display getDisplay()
    {
        return mDisplay ;
    }

    protected ResourcesLoader getResourcesLoader()
    {
        return mDisplay.getResourcesLoader() ;
    }

    protected Game getGame()
    {
        return mDisplay.getGame() ;
    }

    protected UIPanel getUIPanel()
    {
        return mDisplay.getUIPanel() ;
    }
}