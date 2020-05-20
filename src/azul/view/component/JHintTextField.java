package azul.view.component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JHintTextField extends JTextField
{
    // Hint attributes.
    private final String mHint ;
    private final Color mHintColor ;

    /**
     * Simple text field with an hint.
     * @param hint text.
     * @param hintColor color of the hint.
     */
    public JHintTextField(String hint, Color hintColor)
    {
        super("", SwingConstants.CENTER) ;
        mHint = hint ;
        mHintColor = hintColor ;
    }

    @Override
    public void setBorder(Border border)
    {
        // No border needed in the game style.
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g) ;

        if (getText().length() == 0)
        {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON) ;

            Insets insets = getInsets() ;
            FontMetrics fm = g.getFontMetrics() ;

            g.setColor(mHintColor) ;
            g.drawString(mHint, 8, getHeight() / 2 + fm.getAscent() / 2 - 2) ;
        }
    }
}