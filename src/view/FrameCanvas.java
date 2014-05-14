
package view;

import control.FrameCanvasControl;
import control.MainWindowControl;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.JOptionPane;

import graph.GraphModel;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class FrameCanvas extends Canvas
{

    private FrameCanvasControl canvasControl;
    private Float fator = 2f;
    private Float offsetX = 0f;
    private Float offsetY = 0f;
    private float moveX;
    private float moveY;

    public FrameCanvas(FrameCanvasControl canvasControl)
    {
        super();

        this.canvasControl = canvasControl;
        
        /* adicionando listener no model, para quando houver edições */
        final GraphModel gm = MainWindowControl.getUniqueInstance().getGraphModel();
        gm.addListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        Image mImage = createImage(this.getWidth(), this.getHeight());
        Graphics2D ga = (Graphics2D) mImage.getGraphics();

        ga.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        ga.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ga.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        ga.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
        ga.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        ga.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        ga.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        ga.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        ga.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (String nome : canvasControl.getVisibleKeys())
        {
            MainWindowControl.getUniqueInstance().getGraphCanvasList().get(nome).paint(ga,this);
        }

        g.drawImage(mImage, 0, 0, null);
    }

    public void reset()
    {
        setOffsetX(0f);
        setOffsetY(0f);
        setFator(2f);
        repaint();
    }

    public Float getFator()
    {
        return fator;
    }

    public void setFator(Float fator)
    {
        this.fator = fator;
    }

    public float getMoveX()
    {
        return moveX;
    }

    public void setMoveX(float moveX)
    {
        this.moveX = moveX;
    }

    public float getMoveY()
    {
        return moveY;
    }

    public void setMoveY(float moveY)
    {
        this.moveY = moveY;
    }

    public Float getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(Float offsetX)
    {
        this.offsetX = offsetX;
    }

    public Float getOffsetY()
    {
        return offsetY;
    }

    public void setOffsetY(Float offsetY)
    {
        this.offsetY = offsetY;
    }
    
    public String askName()
    {
        String input = JOptionPane.showInputDialog("Name:");
        return input;
    }
    
    public void notifyExistingName()
    {
        JOptionPane.showMessageDialog(null, "Already used name!");
    }
}
