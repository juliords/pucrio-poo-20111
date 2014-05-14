package view;

import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class MainWindow extends JFrame
{

    private static MainWindow mw = null;

    public static MainWindow getUniqueInstance()
    {
        if (mw == null)
        {
            mw = new MainWindow();
        }
        return mw;
    }
    
    private JDesktopPane desk;
    private JMenuBar menuBar;
    private HashMap<String, JMenu> arrayMenu;

    private MainWindow()
    {
        this.setSize(600, 600);

        menuBar = new JMenuBar();
        arrayMenu = new HashMap<String, JMenu>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(menuBar); //set the Frames JMenuBar
        this.setTitle("Graph Viewer"); //title of the frame
        this.setVisible(true); //show the Frame

        desk = new JDesktopPane();
        desk.setAutoscrolls(true);
        desk.setDoubleBuffered(true);
        desk.setVisible(true);
        this.setContentPane(desk);
    }

    public void addMenuItem(String menuName, String itemName, ActionListener listener)
    {
        JMenu menu;

        if (arrayMenu.containsKey(menuName))
        {
            menu = arrayMenu.get(menuName);
        } else
        {
            menu = new JMenu(menuName);
            menuBar.add(menu);
            arrayMenu.put(menuName, menu);
        }

        JMenuItem item = new JMenuItem(itemName);
        item.addActionListener(listener);

        menu.add(item);
    }

    public JDesktopPane getDesktopPane()
    {
        return this.desk;
    }
}
