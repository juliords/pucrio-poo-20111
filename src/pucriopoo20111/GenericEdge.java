/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pucriopoo20111;

import java.util.HashMap;
import java.util.Set;
import graph.EdgeData;
import java.io.Serializable;

/**
 *
 * @author juliords
 */
public class GenericEdge<E> implements EdgeData, Serializable {

    private HashMap<String, E> attrMap;
    private boolean fixed = false;
    private String name = "";
    private double cost = 0;

    public GenericEdge()
    {
        this.attrMap = new HashMap<String, E>();
    }

    public GenericEdge( String name, Set<String> attrList)
    {
        this();
        this.fixed = true;

        for(String s : attrList)
        {
            this.attrMap.put(s, null);
        }
    }
    
    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setAttr(String attr, E value)
    {
        if(this.fixed && !this.containsAttr(attr))
        {
            return;
        }
        
        this.attrMap.put(attr, value);
    }

    public E getAttr(String attr)
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
