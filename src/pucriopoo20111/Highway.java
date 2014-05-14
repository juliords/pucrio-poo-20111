/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pucriopoo20111;

import java.io.Serializable;

/**
 *
 * @author juliords
 */
public class Highway implements graph.EdgeData, Serializable{

    private String name;
    private double distance;

    public Highway(String name, double distance)
    {
        this.name = name;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object obj)
    {
        if( obj instanceof Highway )
        {
            Highway h = (Highway)obj;

            if( ! this.name.equals(h.name) ) return false;
            if( this.distance != h.distance ) return false;

            return true;

        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getCost()
    {
        return this.distance;
    }

    public void print()
    {
        System.out.println("Highway: Name = "+getName()+"; Distante = "+getDistance());
    }

}
