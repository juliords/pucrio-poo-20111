
package control;

import graph.Graph;
import graph.GraphModel;
import graph.GraphPersist;
import graph.exception.CannotCreateFile;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.util.HashMap;
import view.MainWindow;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class MainWindowControl
{

    private static MainWindowControl mw = null;

    public static MainWindowControl getUniqueInstance()
    {
        if (mw == null)
        {
            mw = new MainWindowControl();
        }
        return mw;
    }
    private GraphModel model;
    private MainWindow view;
    
    private GraphCanvas currGraph;
    
    public enum Tool
    {
        DRAG, // Ferramenta padrão
        NEWVERTICE,
        NEWEDGE,
        DELETE,
        SHORTESTPATH
    }
    
    private Tool currTool = Tool.DRAG;
    
    private HashMap<String, GraphCanvas> graphCanvasList = new HashMap<String, GraphCanvas>();

    private MainWindowControl()
    {
        this.model = new GraphModel();
        this.view = MainWindow.getUniqueInstance();

        view.addMenuItem("File", "New", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String name = JOptionPane.showInputDialog("Name:");
                if(model.getGraph(name) != null)
                {
                    JOptionPane.showMessageDialog(null, "Grafo já exite...");
                    return;
                }

                model.newGraph(name);
                GraphCanvas gc = new GraphCanvas(name);
                graphCanvasList.put(name, gc);
                currGraph = gc;
            }
        });
        
        view.addMenuItem("File", "Load", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser file = new JFileChooser();

                int result = file.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION)
                {
                    String filePath = file.getSelectedFile().getPath();
                    String fileName = file.getSelectedFile().getName();
                    String name = fileName.split(model.SUFFIX)[0];

                    HashMap<Graph, GraphCanvas> hm;
                    
                    try
                    {
                        hm = (HashMap<Graph, GraphCanvas>) GraphPersist.load(filePath);
                    } catch (FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Arquivo não encontrado.");
                        return;
                    } catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao ler arquivo.");
                        return;
                    } catch (ClassNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Erro interno ao carregar arquivo.");
                        return;
                    }

                    Graph g = (Graph)hm.keySet().toArray()[0];
                    GraphCanvas gc = hm.get(g);
                    gc.setNewColor();

                    graphCanvasList.put(name, gc);
                    model.addGraph(g, name);
                    currGraph = gc;
                }
            }
        });

        view.addMenuItem("File", "Save", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(model.getLastIndex() == null)
                {
                    JOptionPane.showMessageDialog(null, "Nenhum grafo pronto para ser salvo.");
                    return;
                }

                JFileChooser file = new JFileChooser();
                file.setApproveButtonText("Salvar");
                file.setDialogTitle("Selecione a pasta onde o grafo será salvo");
                file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = file.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION)
                {
                    String filePath = file.getSelectedFile().getPath();
                    String fileName = model.getLastIndex();

                    HashMap<Graph,GraphCanvas> hm = new HashMap<Graph, GraphCanvas>();
                    hm.put(model.getGraph(fileName), graphCanvasList.get(fileName));

                    try
                    {
                        GraphPersist.save(hm, filePath+System.getProperty("file.separator")+fileName+".grf");
                    } catch (CannotCreateFile ex)
                    {
                        JOptionPane.showMessageDialog(null, "Não é possível salvar o grafo nesta pasta.");
                    } catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar o grafo.");
                    }
                }
            }
        });

        view.addMenuItem("File", "Exit", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        view.addMenuItem("View", "New", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                GraphFrameControl graphview = new GraphFrameControl("Graph View");
                view.getDesktopPane().add(graphview.getGraphFrame());
                resizeInternalFrames(e);
            }
        });

        view.addMenuItem("View", "Organize", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                resizeInternalFrames(e);
            }
        });

        // Mudei a partir daqui
        view.addMenuItem("Tools", "New Vertice", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currTool = Tool.NEWVERTICE;
            }
        });

        view.addMenuItem("Tools", "New Edge", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currTool = Tool.NEWEDGE;
            }
        });

        view.addMenuItem("Tools", "Delete", new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currTool = Tool.DELETE;
            }
        });
    }

    public void resizeInternalFrames(ActionEvent ev)
    {
        JDesktopPane desk = view.getDesktopPane();
        
        JInternalFrame[] allframes = desk.getAllFrames();
        int count = allframes.length;
        if (count == 0)
        {
            return;
        }

        // Define tamanho do grid
        int sqrt = (int) Math.sqrt(count);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < count)
        {
            cols++;
            if (rows * cols < count)
            {
                rows++;
            }
        }

        Dimension size = desk.getSize();

        int w = size.width / cols;
        int h = size.height / rows;
        int x = 0;
        int y = 0;

        // Itera sobre os frames, realocando e fazendo resize de cada
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++)
            {
                JInternalFrame f = allframes[(i * cols) + j];

                if (!f.isClosed() && f.isIcon())
                {
                    try
                    {
                        f.setIcon(false);
                    } catch (PropertyVetoException ignored)
                    {
                    }
                }

                desk.getDesktopManager().resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // próxima linha
            x = 0;
        }
    }

    public GraphModel getGraphModel()
    {
        return this.model;
    }

    public HashMap<String, GraphCanvas> getGraphCanvasList()
    {
        return this.graphCanvasList;
    }
    
    public Tool getCurrTool()
    {
        return currTool;
    }
    
    public void setDefaultTool()
    {
        currTool = Tool.DRAG;
    }
    
    public GraphCanvas getCurrGraph()
    {
        return currGraph;
    }

    public void setCurrGraph(GraphCanvas currGraph)
    {
        this.currGraph = currGraph;
    }
}
