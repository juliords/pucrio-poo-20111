package pucriopoo20111;

//import graph.Graph;
//import graph.VerticeDataIterator;
//import graph.EdgeDataIterator;
//import graph.exception.CannotCreateFile;
//import graph.exception.MissingVertice;
//import graph.exception.NegativeCycle;
//import java.io.*;
//import java.util.*;

import control.MainWindowControl;

public class Main
{

    public static void main(String[] args)
    {
        MainWindowControl.getUniqueInstance();

/*        Scanner scan = new Scanner(System.in);

        System.out.println("# Digite 'help' para a lista de comandos.");
        System.out.println("# Digite 'open' e depois o caminho de um arquivo que possa ser lido para um teste automatizado.");
        System.out.print("> ");

        String cmd = scan.next();

        if(cmd.equals("open"))
        {
            cmd = scan.next();
            File input = new File(cmd);
            if (!input.canRead())
            {
                System.out.println("Falha na abertura do arquivo.");
                return;
            }

            scan.close();

            try
            {
                scan = new Scanner(input);
            }
            catch (Exception e)
            {
                System.out.println();
                return;
            }

            cmd = scan.next();
        }

        HashMap<String, Graph<City, Highway>> graphMap = new HashMap<String, Graph<City, Highway>>();
        HashMap<String, City> cityMap = new HashMap<String, City>();
        HashMap<String, Highway> hwyMap = new HashMap<String, Highway>();

        VerticeDataIterator<City> viterator = null;
        EdgeDataIterator<Highway> eiterator = null;

        while (true)
        {
            if (cmd.equals("newCity"))
            {
                String name = scan.next();
                int population = scan.nextInt();
                double area = scan.nextDouble();
                int coordX = scan.nextInt();
                int coordY = scan.nextInt();

                if(cityMap.get(name) != null)
                {
                    System.out.println("Xiii... Essa cidade já existe tio...");
                }
                else
                {
                    City newCity = new City(name, population, area, coordX, coordY);
                    cityMap.put(name, newCity);
                }
            }
            else if (cmd.equals("newHwy"))
            {
                String name = scan.next();
                double distance = scan.nextDouble();

                if(hwyMap.get(name) != null)
                {
                    System.out.println("Xiii... Essa rodovia já existe tio...");
                }
                else
                {
                    Highway newHwy = new Highway(name, distance);
                    hwyMap.put(name, newHwy);
                }
            }
            else if (cmd.equals("newGraph"))
            {
                String name = scan.next();

                if(cityMap.get(name) != null)
                {
                    System.out.println("Xiii... Esse grafo já existe tio...");
                }
                else
                {
                    Graph<City, Highway> newHwy = new Graph<City, Highway>();
                    graphMap.put(name, newHwy);
                }
            }
            else if (cmd.equals("addVertice"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                else if(city == null)
                {
                    System.out.println("Tem essa cidade não tio...");
                }
                else
                {
                    try{
                        graph.addVertice(city, city.getName());
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Ops... já tem grafo com esse nome tio...");
                    }
                }
            }
            else if (cmd.equals("addEdge"))
            {
                String graphStr = scan.next();
                String hwyStr = scan.next();
                String cityOrigStr = scan.next();
                String cityDestStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                Highway hwy = hwyMap.get(hwyStr);
                City cityOrig = cityMap.get(cityOrigStr);
                City cityDest = cityMap.get(cityDestStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                else if(hwy == null)
                {
                    System.out.println("Tem essa rodovia não tio...");
                }
                else if(cityOrig == null)
                {
                    System.out.println("Tem essa cidade de origem não tio...");
                }
                else if(cityDest == null)
                {
                    System.out.println("Tem essa cidade de destino não tio...");
                }
                else
                {
                    try{
                        graph.addEdge(hwy, cityOrig, cityDest);
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Ops... deu não tio...");
                    }
                }
            }
            else if(cmd.equals("hasVertice"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                else if(city == null)
                {
                    System.out.println("Tem essa cidade não tio...");
                }
                else if(graph.hasVertice(city))
                {
                    System.out.println("O grafo tem esse vertice sim tio!");
                }
                else
                {
                    System.out.println("O grafo não tem esse vertice não tio!");
                }
            }
            else if(cmd.equals("hasEdge"))
            {
                String graphStr = scan.next();
                String hwyStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                Highway hwy = hwyMap.get(hwyStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                if(hwy == null)
                {
                    System.out.println("Tem essa rodovia não tio...");
                }
                else if(graph.hasEdge(hwy))
                {
                    System.out.println("O grafo tem essa aresta sim tio!");
                }
                else
                {
                    System.out.println("O grafo não tem essa aresta não tio!");
                }
            }
            else if(cmd.equals("removeVertice"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                else if(city == null)
                {
                    System.out.println("Tem essa cidade não tio...");
                }
                 else
                {
                    try{
                        graph.removeVertice(city);
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("removeEdge"))
            {
                String graphStr = scan.next();
                String hwyStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                Highway hwy = hwyMap.get(hwyStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo não tio...");
                }
                else if(hwy == null)
                {
                    System.out.println("Tem essa rodovia não tio...");
                }
                else
                {
                    try{
                        graph.removeEdge(hwy);
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Tem essa aresta aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("numVertices"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Esse grafo não existe não tio...");
                }
                else
                {
                    System.out.println("Cidades: "+graph.sizeInVertices());
                }
            }
            else if(cmd.equals("numEdges"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Esse grafo não existe não tio...");
                }
                else
                {
                    System.out.println("Rodovias: "+graph.sizeInEdges());
                }
            }
            else if(cmd.equals("destroyMap"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph != null)
                {
                    graph.clear();
                    graphMap.remove(graphStr);
                }
            }
            else if(cmd.equals("destroyEdges"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph != null)
                {
                    graph.clearEdges();
                }
            }
            else if(cmd.equals("dumpVertices"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                else
                {
                    ArrayList<City> array = graph.getVerticesData();

                    int i = 0;
                    for(City c : array)
                    {
                        System.out.print((++i)+") ");
                        c.print();
                    }
                }
            }
            else if(cmd.equals("dumpEdges"))
            {
                String graphStr = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                else
                {
                    ArrayList<Highway> array = graph.getEdgesData();

                    int i = 0;
                    for(Highway h : array)
                    {
                        System.out.print((++i)+") ");
                        h.print();
                    }
                }
            }
            else if(cmd.equals("dumpInEdges"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(city == null)
                {
                    System.out.println("Tem essa cidade aqui não tio...");
                }
                else
                {
                    try{
                        ArrayList<Highway> array = graph.getInputEdges(city);

                        int i = 0;
                        for(Highway h : array)
                        {
                            System.out.print((++i)+") ");
                            h.print();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("dumpOutEdges"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(city == null)
                {
                    System.out.println("Tem essa cidade aqui não tio...");
                }
                else
                {
                    try{
                        ArrayList<Highway> array = graph.getOutputEdges(city);

                        int i = 0;
                        for(Highway h : array)
                        {
                            System.out.print((++i)+") ");
                            h.print();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("dumpInVertices"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(city == null)
                {
                    System.out.println("Tem essa cidade aqui não tio...");
                }
                else
                {
                    try{
                        ArrayList<City> array = graph.getInputVertices(city);

                        int i = 0;
                        for(City c : array)
                        {
                            System.out.print((++i)+") ");
                            c.print();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("dumpOutVertices"))
            {
                String graphStr = scan.next();
                String cityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City city = cityMap.get(cityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(city == null)
                {
                    System.out.println("Tem essa cidade aqui não tio...");
                }
                else
                {
                    try{
                        ArrayList<City> array = graph.getOutputVertices(city);

                        int i = 0;
                        for(City c : array)
                        {
                            System.out.print((++i)+") ");
                            c.print();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("dumpInVertice"))
            {
                String graphStr = scan.next();
                String hwyStr = scan.next();

                Graph<City, Highway> graph = graphMap.get(graphStr);
                Highway hwy = hwyMap.get(hwyStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(hwy == null)
                {
                    System.out.println("Tem essa rodovia aqui não tio...");
                }
                else
                {
                    try{
                        City c =  graph.getInputVertice(hwy);

                        c.print();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem essa aresta aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("dumpOutVertice"))
            {
                String graphStr = scan.next();
                String hwyStr = scan.next();

                Graph<City, Highway> graph = graphMap.get(graphStr);
                Highway hwy = hwyMap.get(hwyStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(hwy == null)
                {
                    System.out.println("Tem essa rodovia aqui não tio...");
                }
                else
                {
                    try{
                        City c =  graph.getOutputVertice(hwy);

                        c.print();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Tem essa aresta aqui não tio...");
                    }
                }
            }
            else if(cmd.equals("shortestPath"))
            {
                String graphStr = scan.next();
                String srcCityStr = scan.next();
                String destCityStr = scan.next();

                Graph graph = graphMap.get(graphStr);
                City srcCity = cityMap.get(srcCityStr);
                City destCity = cityMap.get(destCityStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                else if(srcCity == null)
                {
                    System.out.println("Tem essa cidade de partida aqui não tio...");
                }
                else if(destCity == null)
                {
                    System.out.println("Tem essa cidade de destino aqui não tio...");
                }
                else
                {
                    try{
                        ArrayList<Highway> array = graph.computeShortestPath(srcCity, destCity);

                        int i=0;
                        for (Highway h : array)
                        {
                            System.out.print((++i)+" -> ");
                            h.print();
                        }
                        if(i == 0) System.out.println("Ihh... tem caminho não tio...");
                    }
                    catch (MissingVertice e)
                    {
                        System.out.println("Tem esse vertice aqui não tio...");
                    }
                    catch (NegativeCycle e)
                    {
                        System.out.println("Ciclo negativo detectado. Tem menor caminho não tio...");
                    }
                }
            }
            else if(cmd.equals("checkName"))
            {
                String graphStr = scan.next();
                String nome = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                if(graph.isNameUnique(nome) == true)
                {
                    System.out.println("Tem esse nome aqui não tio...");
                }
                else
                {
                    System.out.println("Xiiii... Tem esse nome aqui tio...");
                }
            }
            else if(cmd.equals("vIterator"))
            {
                String graphStr = scan.next();

                Graph<City, Highway> graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }

                viterator = graph.verticeIterator();
            }
            else if(cmd.equals("vIteratorNext"))
            {
                if(viterator == null)
                {
                    System.out.println("Tem esse iterador não tio...");
                }
                try
                {
                    City itCity = viterator.next();
                    itCity.print();
                }
                catch(java.util.NoSuchElementException e)
                {
                    System.out.println("Tem próximo desse iterador não tio...");
                }
                catch(java.util.ConcurrentModificationException e)
                {
                    System.out.println("O iterador perdeu o estado tio...");
                }
            }
            else if(cmd.equals("vIteratorHasNext"))
            {
                if(viterator == null)
                {
                    System.out.println("Tem esse iterador não tio...");
                }
                try
                {
                    boolean hasNext = viterator.hasNext();
                    if (hasNext==true) System.out.println("Tem próximo sim...");
                    else System.out.println("Tem próximo não...");
                }
                catch(java.util.ConcurrentModificationException e)
                {
                    System.out.println("O iterador perdeu o estado tio...");
                }
            }
            else if(cmd.equals("vIteratorReset"))
            {
                viterator.reset();
            }
            else if(cmd.equals("eIterator"))
            {
                String graphStr = scan.next();

                Graph<City, Highway> graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }

                eiterator = graph.edgeIterator();
            }
            else if(cmd.equals("eIteratorNext"))
            {
                if(eiterator == null)
                {
                    System.out.println("Tem esse iterador não tio...");
                }
                try
                {
                    Highway itHighway = eiterator.next();
                    itHighway.print();
                }
                catch(java.util.NoSuchElementException e)
                {
                    System.out.println("Tem próximo desse iterador não tio...");
                }
                catch(java.util.ConcurrentModificationException e)
                {
                    System.out.println("O iterador perdeu o estado tio...");
                }
            }
            else if(cmd.equals("eIteratorHasNext"))
            {
                if(eiterator == null)
                {
                    System.out.println("Tem esse iterador não tio...");
                }
                try
                {
                    boolean hasNext = eiterator.hasNext();
                    if (hasNext==true) System.out.println("Tem próximo sim...");
                    else System.out.println("Tem próximo não...");
                }
                catch(java.util.ConcurrentModificationException e)
                {
                    System.out.println("O iterador perdeu o estado tio...");
                }
            }
            else if(cmd.equals("eIteratorReset"))
            {
                eiterator.reset();
            }
            else if(cmd.equals("save"))
            {
                String graphStr = scan.next();
                String path = scan.next();

                Graph graph = graphMap.get(graphStr);

                if(graph == null)
                {
                    System.out.println("Tem esse grafo aqui não tio...");
                }
                else
                {
                    try{
                        graph.save(path);
                    }
                    catch(CannotCreateFile e)
                    {
                        System.out.println("Consegui criar esse arquivo não tio...");
                    }
                    catch(java.io.IOException e)
                    {
                        System.out.println("Xiii... Consegui gravar não tio...");
                    }
                }
            }
            else if(cmd.equals("load"))
            {
                String name = scan.next();
                String path = scan.next();

                if(graphMap.get(name) != null)
                {
                    System.out.println("Xiii... Esse grafo já existe tio...");
                }
                else
                {
                    try{
                        Graph<City, Highway> g = Graph.load(path);
                        graphMap.put(name, g);
                    }
                    catch(java.io.FileNotFoundException e)
                    {
                        System.out.println("Tem esse arquivo aqui não tio...");
                    }
                    catch(java.io.IOException e)
                    {
                        System.out.println("Xiii... Consegui gravar não tio...");
                    }
                    catch(java.lang.ClassNotFoundException e)
                    {
                        System.out.println("Xiii... Faltou alguma classe tio...");
                    }
                }
            }
            else if(cmd.equals("help"))
            {
                printHelp();
            }
            else if(cmd.equals("exit"))
            {
                break;
            }
            else
            {
                System.out.println("Comando invalido tio... Tenta de novo aí! E olha o help...");
            }

            System.out.print("> ");
            cmd = scan.next();
        }
    }
    static void printHelp()
    {
        System.out.println("Seja bem vindo! :-)                                                                         \n");
        System.out.println("Abaixo a dlista de comandos:                                                                 \n");
        System.out.println("\tnewCity         <city_name>    <population>       <area>                                    ");
        System.out.println("\tnewHwy          <highway_name> <distance>                                                   ");
        System.out.println("\tnewGraph        <map_name>                                                                  ");
        System.out.println("\taddVertice      <map_name>     <city_name>                                                  ");
        System.out.println("\taddEdge         <map_name>     <highway_name>     <source_city_name> <destination_city_name>");
        System.out.println("\thasVertice      <map_name>     <city_name>                                                  ");
        System.out.println("\thasEdge         <map_name>     <highway_name>                                               ");
        System.out.println("\tremoveVertice   <map_name>     <city_name>                                                  ");
        System.out.println("\tremoveEdge      <map_name>     <highway_name>                                               ");
        System.out.println("\tnumVertices     <map_name>                                                                  ");
        System.out.println("\tnumEdges        <map_name>                                                                  ");
        System.out.println("\tdestroyMap      <map_name>                                                                  ");
        System.out.println("\tdestroyEdges    <map_name>                                                                  ");
        System.out.println("\tdumpVertices    <map_name>                                                                  ");
        System.out.println("\tdumpEdges       <map_name>                                                                  ");
        System.out.println("\tdumpInEdges     <map_name>     <city_name>                                                  ");
        System.out.println("\tdumpOutEdges    <map_name>     <city_name>                                                  ");
        System.out.println("\tdumpInVertices  <map_name>     <city_name>                                                  ");
        System.out.println("\tdumpOutVertices <map_name>     <city_name>                                                  ");
        System.out.println("\tdumpInVertice   <map_name>     <highway_name>                                               ");
        System.out.println("\tdumpOutVertice  <map_name>     <highway_name>                                               ");
        System.out.println("\tshortestPath    <map_name>     <source_city_name> <destination_city_name>                   ");
        System.out.println("\tcheckName       <map_name>     <city_name>                                                  ");
        System.out.println("\tvIterator       <map_name>                                                                  ");
        System.out.println("\tvIteratorNext                                                                               ");
        System.out.println("\tvIteratorHasNext                                                                               ");
        System.out.println("\tvIteratorReset                                                                              ");
        System.out.println("\teIterator       <map_name>                                                                  ");
        System.out.println("\teIteratorNext                                                                               ");
        System.out.println("\teIteratorHasNext                                                                               ");
        System.out.println("\teIteratorReset                                                                              ");
        System.out.println("\tsave            <map_name>     <file_path>                                                  ");
        System.out.println("\tload            <map_name>     <file_path>                                                  ");
        System.out.println("\texit                                                                                        ");
    */}
}

