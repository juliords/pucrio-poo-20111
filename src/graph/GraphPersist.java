
package graph;

import graph.exception.CannotCreateFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Julio Ribeiro
 * @author Luís Henrique Pelosi
 * @author Peter Brasil
 */
public class GraphPersist
{

    public static <T extends Serializable> void save(T graph, String filePath) throws CannotCreateFile, java.io.IOException
    {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try
        {
            fos = new FileOutputStream(filePath);
        }
        catch(java.io.FileNotFoundException ex)
        {
            File file = new File(filePath);

            if(file.createNewFile())
            {
                fos = new FileOutputStream(filePath);
            }
            else
            {
                throw new CannotCreateFile();
            }
        }
        out = new ObjectOutputStream(fos);

        out.writeObject(graph);

        out.close();
        fos.close();
    }

    public static Object load(String filePath)
            throws java.io.FileNotFoundException,
            java.io.IOException, java.lang.ClassNotFoundException
    {
        FileInputStream fos = null;
        ObjectInputStream in = null;

        fos = new FileInputStream(filePath);
        in = new ObjectInputStream(fos);

        return in.readObject();
    }
}
