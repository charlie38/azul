package azul.view.ui.screen;

import azul.model.Game;
import azul.view.Display;
import azul.view.resource.ResourcesLoader;
import azul.view.ui.UIPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public abstract class Screen extends JPanel
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

        setLayout(new GridLayout(nbRows, nbCols, HGAP, VGAP)) ;
    }

    protected JButton createButton(String message, Color color, float pt, ActionListener listener)
    {
        JButton button = new JButton(message) ;
        button.setFont(getResourcesLoader().getFont(pt)) ;
        button.setBackground(color) ;
        button.setBorderPainted(false) ;
        button.setFocusPainted(false) ;
        button.addActionListener(listener) ;

        return button ;
    }

    protected JButton createButton(ImageIcon image, Color color, float pt, ActionListener listener)
    {
        JButton button = createButton("", color, pt, listener) ;
        button.setIcon(image) ;

        return button ;
    }

    protected JButton createButton(String message, ImageIcon image, Color color, float pt, ActionListener listener)
    {
        JButton button = createButton(message, color, pt, listener) ;
        button.setIcon(image) ;

        return button ;
    }

    protected JButton createButtonIconSide(String message, ImageIcon image, boolean iconRightSide, Color color, float pt,
                                           ActionListener listener)
    {
        JButton button = createButton(
                iconRightSide ? "<html><div align=left width=200px>" + message + "</dive></html>" : message,
                image, color, pt, listener) ;
        button.setIconTextGap(40) ;
        button.setHorizontalAlignment(iconRightSide ? SwingConstants.LEFT : SwingConstants.RIGHT) ;
        button.setHorizontalTextPosition(iconRightSide ? SwingConstants.RIGHT : SwingConstants.LEFT) ;

        return button ;
    }

    protected JButton createButtonIconTop(String message, ImageIcon image, Color color, float pt, ActionListener listener)
    {
        JButton button = createButton(message, image, color, pt, listener) ;
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