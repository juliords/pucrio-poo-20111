/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucriopoo20111;

import graph.VerticeData;
import java.io.Serializable;

/**
 *
 * @author juliords
 */
public class City implements VerticeData, Serializable
{

    private String name;
    private int population;
    private double area;
    private int x, y;

    public City(String name, int population, double area, int x, int y)
    {
        this.name = name;
        this.population = population;
        this.area = area;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if( obj instanceof City )
        {
            City c = (City) obj;

            if( ! this.name.equals(c.name) ) return false;
            if( this.population != c.population ) return false;
            if( this.area != c.area ) return false;

            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    public double getArea()
    {
        return area;
    }

    public void setArea(double area)
    {
        this.area = area;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void print()
    {
        System.out.println("City: Name = "+getName()+"; Population = "+getPopulation()+"; Area = "+getArea());
    }
}
