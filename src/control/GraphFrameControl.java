

package control;

import graph.GraphModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import view.GraphFrame;
import view.MainWindow;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class GraphFrameControl
{
    private FrameCanvasControl canvasControl;
    private GraphFrame frame;

    public GraphFrameControl(String nome)
    {
        frame = new GraphFrame(nome);
        canvasControl = new FrameCanvasControl();
        frame.getContentPane().add(canvasControl.getFrameCanvas());

        /* adicionando listener no model, para quando um grafo for carregado */
        final GraphModel gm = MainWindowControl.getUniqueInstance().getGraphModel();
        gm.addChecklistListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addcheckbox(gm.getLastIndex());
                //canvasControl.addGraph(gm.getLastIndex());
                canvasControl.setVisible(gm.getLastIndex(), true);
            }
        });

        for (String name : gm.getGraphNames())
        {
            this.addcheckbox(name);
            //canvasControl.addGraph(name);
            canvasControl.setVisible(name, true);
        }
    }

    private void addcheckbox(final String nome)
    {
        JPanel checkPanel = frame.getCheckBoxPanel();

        JCheckBox chk = new JCheckBox(nome);
        chk.setSelected(true);

        chk.addItemListener(new ItemListener()
        {

            public void itemStateChanged(ItemEvent e)
            {
                canvasControl.setVisible(nome, e.getStateChange() == ItemEvent.SELECTED);
                canvasControl.getFrameCanvas().repaint();
                
                MainWindowControl mwc = MainWindowControl.getUniqueInstance();
                mwc.setCurrGraph(mwc.getGraphCanvasList().get(nome));
            }
        });
        checkPanel.add(chk);
        checkPanel.revalidate();
    }

    public GraphFrame getGraphFrame()
    {
        return this.frame;
    }
}
