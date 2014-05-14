/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pucriopoo20111;

import java.util.HashMap;
import java.util.Set;
import graph.VerticeData;
import java.io.Serializable;

/**
 *
 * @author juliords
 */
public class GenericVertice<T> implements VerticeData, Serializable {

    private HashMap<String, T> attrMap;
    private boolean fixed = false;
    private String name;

    public GenericVertice( String name )
    {
        this.name = name;
        this.attrMap = new HashMap<String, T>();
    }

    public GenericVertice( String name, Set<String> attrList)
    {
        this( name );
        this.fixed = true;

        for(String s : attrList)
        {
            this.attrMap.put(s, null);
        }
    }

    public String getName()
    {
        return name;
    }
    
    // TODO: Apagar daqui...
    public int getX()
    {
        return 0;
    }
    public int getY()
    {
        return 0;
    }
    // ... até aqui.
    
    public void setAttr(String attr, T value)
    {
        if(this.fixed && !this.containsAttr(attr))
        {
            return;
        }
        
        this.attrMap.put(attr, value);
    }

    public T getAttr(String attr)
    {
        return this.attrMap.get(attr);
    }

    public boolean containsAttr(String attr)
    {
        return this.attrMap.containsKey(attr);
    }

    public void removeAttr(String attr)
    {
        if(this.fixed && !this.containsAttr(attr))
        {
            return;
        }

        this.attrMap.remove(attr);
    }

    public Set<String> getAttrSet()
    {
        return this.attrMap.keySet();
    }

}
