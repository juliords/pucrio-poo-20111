

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class GraphFrame extends JInternalFrame
{

    private JPanel checkPanel = new JPanel(new GridLayout(0, 1));

    public GraphFrame(String nome)
    {
        super();
        this.setVisible(true);
        this.setResizable(true);
        this.setClosable(true);
        this.setDoubleBuffered(true);
        this.setMaximizable(true);
        this.setTitle(nome);
        this.setAutoscrolls(true);
        this.setBackground(Color.WHITE);

        checkPanel = new JPanel(new GridLayout(0, 1));
        checkPanel.setAutoscrolls(true);
        checkPanel.setVisible(true);
        add(checkPanel, BorderLayout.EAST);
    }

    public JPanel getCheckBoxPanel()
    {
        return this.checkPanel;
    }
}
