package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Set;
import view.FrameCanvas;
import graph.GraphModel;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import pucriopoo20111.GenericVertice;
import pucriopoo20111.GenericEdge;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class FrameCanvasControl
{

    private Set<String> visibleKeys = new HashSet<String>();
    private FrameCanvas canvas;
    private GraphCanvas editingGraph;

    public FrameCanvasControl()
    {
        super();
        canvas = new FrameCanvas(this);

        canvas.addMouseListener(new MouseListener()
        {

            public void mouseClicked(MouseEvent e)
            {
                switch (MainWindowControl.getUniqueInstance().getCurrTool())
                {
                    case NEWVERTICE:
                        addVertice(e.getX(), e.getY());
                        MainWindowControl.getUniqueInstance().setDefaultTool();
                        break;
                    case DELETE:
                        deleteAt(e.getX(), e.getY());
                        MainWindowControl.getUniqueInstance().setDefaultTool();
                        break;
                    default:
                        float mX = e.getX();
                        float mY = e.getY();

                        if ((e.getModifiers() & e.BUTTON3_MASK) != 0)
                        {
                            for (String graphName : visibleKeys)
                            {
                                final GraphCanvas g = MainWindowControl.getUniqueInstance().getGraphCanvasList().get(graphName);
                                MainWindowControl.getUniqueInstance().setCurrGraph(g);

                                if (g.ActivateVertice(mX, mY))
                                {
                                    String verticeName = g.getActiveVerticeName();

                                    final GenericVertice vertice = (GenericVertice) MainWindowControl.getUniqueInstance().getGraphModel().getVerticeFrom(graphName, verticeName);

                                    final HashMap<String, JTextField> textList = new HashMap<String, JTextField>();

                                    final JPopupMenu jpm = new JPopupMenu();
                                    for (Object o : vertice.getAttrSet())
                                    {
                                        String field = (String) o;

                                        JLabel label = new JLabel();
                                        label.setText(field + ":");
                                        JTextField text = new JTextField(15);
                                        text.setText((String) vertice.getAttr(field));
                                        jpm.add(label);
                                        jpm.add(text);
                                        textList.put(field, text);

                                    }
                                    if (vertice.getAttrSet().size() > 0)
                                    {
                                        JButton saveItem = new JButton("Salvar");
                                        saveItem.addActionListener(new ActionListener()
                                        {

                                            public void actionPerformed(ActionEvent e)
                                            {
                                                Set<String> fields = textList.keySet();
                                                for (String s : fields)
                                                {
                                                    String value = textList.get(s).getText();
                                                    vertice.setAttr(s, value);

                                                    jpm.setVisible(false);
                                                }
                                            }
                                        });
                                        jpm.add(saveItem);
                                    }

                                    JButton addItem = new JButton("Adicionar campo");
                                    addItem.addActionListener(new ActionListener()
                                    {

                                        public void actionPerformed(ActionEvent e)
                                        {
                                            String name = canvas.askName();
                                            vertice.setAttr(name, "");
                                            jpm.setVisible(false);
                                        }
                                    });
                                    jpm.add(addItem);

                                    jpm.show(canvas, (int) mX, (int) mY);
                                    jpm.setVisible(true);
                                    return;
                                }
                                if (g.activateEdge(mX, mY))
                                {
                                    final String edgeName = g.getActiveEdgeName();

                                    final GenericEdge edge = (GenericEdge) MainWindowControl.getUniqueInstance().getGraphModel().getEdgeFrom(graphName, edgeName);

                                    final HashMap<String, JTextField> textList = new HashMap<String, JTextField>();

                                    final JPopupMenu jpm = new JPopupMenu();

                                    JLabel labelCost = new JLabel();
                                    labelCost.setText("Cost:");
                                    final JTextField textCost = new JTextField(15);
                                    textCost.setText(String.valueOf(edge.getCost()));
                                    jpm.add(labelCost);
                                    jpm.add(textCost);

                                    for (Object o : edge.getAttrSet())
                                    {
                                        String field = (String) o;

                                        JLabel label = new JLabel();
                                        label.setText(field + ":");
                                        JTextField text = new JTextField(15);
                                        text.setText((String) edge.getAttr(field));
                                        jpm.add(label);
                                        jpm.add(text);
                                        textList.put(field, text);

                                    }
                                    JButton saveItem = new JButton("Salvar");
                                    saveItem.addActionListener(new ActionListener()
                                    {

                                        public void actionPerformed(ActionEvent e)
                                        {
                                            double cost = Double.valueOf(textCost.getText());
                                            edge.setCost(cost);
                                            MainWindowControl.getUniqueInstance().getGraphModel().notifyListeners();
                                            g.setActiveEdgeCost(cost);

                                            Set<String> fields = textList.keySet();
                                            for (String s : fields)
                                            {
                                                String value = textList.get(s).getText();
                                                edge.setAttr(s, value);

                                                jpm.setVisible(false);
                                            }
                                        }
                                    });
                                    jpm.add(saveItem);

                                    JButton addItem = new JButton("Adicionar campo");
                                    addItem.addActionListener(new ActionListener()
                                    {

                                        public void actionPerformed(ActionEvent e)
                                        {
                                            String name = canvas.askName();
                                            edge.setAttr(name, "");
                                            jpm.setVisible(false);
                                        }
                                    });
                                    jpm.add(addItem);

                                    jpm.show(canvas, (int) mX, (int) mY);
                                    jpm.setVisible(true);
                                    return;
                                }

                            }

                            break;
                        }
                }
            }

            public void mousePressed(MouseEvent e)
            {
                float mX = e.getX();
                float mY = e.getY();
                editingGraph = null;
                switch (MainWindowControl.getUniqueInstance().getCurrTool())
                {
                    case DRAG:
                        for (String graphName : visibleKeys)
                        {
                            GraphCanvas graph = MainWindowControl.getUniqueInstance().getGraphCanvasList().get(graphName);
                            if (graph.ActivateVertice(mX, mY))
                            {
                                editingGraph = graph;
                                MainWindowControl.getUniqueInstance().setCurrGraph(graph);
                            }
                        }
                        canvas.setMoveX(mX);
                        canvas.setMoveY(mY);
                        break;
                    case NEWEDGE:
                        for (String graphName : visibleKeys)
                        {
                            GraphCanvas graph = MainWindowControl.getUniqueInstance().getGraphCanvasList().get(graphName);
                            if (graph.ActivateVertice(mX, mY))
                            {
                                editingGraph = graph;
                                MainWindowControl.getUniqueInstance().setCurrGraph(graph);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            public void mouseReleased(MouseEvent e)
            {
                switch (MainWindowControl.getUniqueInstance().getCurrTool())
                {
                    case NEWEDGE:
                        MainWindowControl.getUniqueInstance().setDefaultTool();
                        if (editingGraph == null
                                || editingGraph.ActivateSecondVertice(e.getX(), e.getY()) == false)
                        {
                            return;
                        }
                        addEdge();
                        break;
                    default:
                        break;
                }
            }

            public void mouseEntered(MouseEvent e)
            {
            }

            public void mouseExited(MouseEvent e)
            {
            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener()
        {

            public void mouseDragged(MouseEvent e)
            {
                switch (MainWindowControl.getUniqueInstance().getCurrTool())
                {
                    case DRAG:
                        float deltaX = (e.getX() - canvas.getMoveX()) / canvas.getFator();


                        float deltaY = (e.getY() - canvas.getMoveY()) / canvas.getFator();


                        if (editingGraph != null)
                        {
                            editingGraph.moveActiveVertice(deltaX, deltaY);
                            MainWindowControl.getUniqueInstance().getGraphModel().notifyListeners();


                        } else
                        {
                            canvas.setOffsetX(canvas.getOffsetX() + deltaX);
                            canvas.setOffsetY(canvas.getOffsetY() + deltaY);
                            canvas.repaint();


                        }
                        canvas.setMoveX(e.getX());
                        canvas.setMoveY(e.getY());


                        break;


                    default:
                        break;


                }
            }

            public void mouseMoved(MouseEvent e)
            {
            }
        });

        canvas.addMouseWheelListener(new MouseWheelListener()
        {

            public void mouseWheelMoved(MouseWheelEvent e)
            {
                canvas.setFator(canvas.getFator() - (e.getUnitsToScroll() / 2));
                canvas.repaint();


            }
        });


    }

    public void setVisible(String nome, boolean visible)
    {
        if (visible)
        {
            visibleKeys.add(nome);


        } else
        {
            visibleKeys.remove(nome);


        }
    }

    public FrameCanvas getFrameCanvas()
    {
        return this.canvas;


    }

    public Set<String> getVisibleKeys()
    {
        return this.visibleKeys;


    }

    private void addVertice(float x, float y)
    {
        GraphCanvas graphCanvas = MainWindowControl.getUniqueInstance().getCurrGraph();
        String graphName = graphCanvas.getName();
        GraphModel gm = MainWindowControl.getUniqueInstance().getGraphModel();

        String verticeName = canvas.askName();


        if (verticeName == null)
        {
            return;


        }

        try
        {
            GenericVertice vertice = new GenericVertice(verticeName);
            gm.addVerticeTo(graphName, vertice);
            x = (x / canvas.getFator()) - canvas.getOffsetX();
            y = (y / canvas.getFator()) - canvas.getOffsetY();
            graphCanvas.addVertice(verticeName, x, y);
            gm.notifyListeners();


        } catch (graph.exception.AlreadyHasVertice e)
        {
            // Não faz sentido ocorrer.
        } catch (graph.exception.NotUniqueName e)
        {
            canvas.notifyExistingName();


        }
    }

    private void addEdge()
    {
        GraphCanvas graphCanvas = MainWindowControl.getUniqueInstance().getCurrGraph();
        String graphName = graphCanvas.getName();
        GraphModel gm = MainWindowControl.getUniqueInstance().getGraphModel();

        String originName = graphCanvas.getActiveVerticeName();
        String destinationName = graphCanvas.getSecondActiveVerticeName();



        try
        {
            GenericEdge edge = new GenericEdge();
            String edgeName = String.valueOf(Calendar.getInstance().getTimeInMillis());
            edge.setName(edgeName);
            gm.addEdgeTo(graphName, edge, originName, destinationName);
            editingGraph.ConnectWithEdge(edgeName);
            gm.notifyListeners();


        } catch (graph.exception.AlreadyHasEdge e)
        {
            // Não faz sentido ocorrer.
        } catch (graph.exception.MissingVertice e)
        {
            // Não foi encontrado vertices com nomes de origem ou destino.
            System.out.println(e);


        }
    }

    private void deleteAt(float x, float y)
    {
        GraphModel gm = MainWindowControl.getUniqueInstance().getGraphModel();
        editingGraph = null;

        // Prioridade para os vertices.


        for (String graphName : visibleKeys)
        {
            GraphCanvas graph = MainWindowControl.getUniqueInstance().getGraphCanvasList().get(graphName);


            if (graph.ActivateVertice(x, y))
            {
                editingGraph = graph;


            }
        }
        if (editingGraph != null)
        {
            String verticeName = editingGraph.getActiveVerticeName();
            editingGraph.removeActiveVertice();
            gm.removeVerticeFrom(editingGraph.getName(), verticeName);
            gm.notifyListeners();


            return;


        } // Por fim as arestas
        for (String graphName : visibleKeys)
        {
            GraphCanvas graph = MainWindowControl.getUniqueInstance().getGraphCanvasList().get(graphName);


            if (graph.activateEdge(x, y))
            {
                editingGraph = graph;


            }
        }
        if (editingGraph != null)
        {
            String originVerticeName = editingGraph.getActiveEdgeOrigin();
            String destinationVerticeName = editingGraph.getActiveEdgeDestination();
            editingGraph.removeActiveEdge();
            gm.removeEdgeFrom(editingGraph.getName(), originVerticeName, destinationVerticeName);
            gm.notifyListeners();


            return;

        }
    }
}
