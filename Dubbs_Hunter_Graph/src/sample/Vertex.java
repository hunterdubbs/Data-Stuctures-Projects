package sample;

/**
 * @author Hunter Dubbs
 * @version 12/3/2018
 * made for CIT360 at PCT
 *
 * The Vertex class holds the name of this Vertex as well as its X and Y
 * coordinates. It also contains a getDistance method to find the geographical
 * distance between this Vertex and another Vertex.
 */
public class Vertex {

    private int x, y;
    private String name;

    /**
     * The full constructor for Vertex allows you to set all fields.
     * @param name the name of the Vertex
     * @param x the X position of the Vertex
     * @param y
     */
    public Vertex(String name, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * This method creates and returns a copy of this Vertex.
     * @return a copy of this Vertex
     */
    public Vertex clone(){
        return new Vertex(name, x, y);
    }

    /**
     * This method determines whether or not two Vertex objects are the same by
     * examining the fields.
     * @param vertex the Vertex to compare this Vertex to
     * @return whether or not the Vertex objects are the same
     */
    public boolean equals(Vertex vertex){
        return name.equals(vertex.getName()) && x == vertex.getX() && y == vertex.getY();
    }

    /**
     * This method returns the name of the Vertex.
     * @return the name of the Vertex
     */
    public String getName(){
        return name;
    }

    /**
     * This method returns the X coordinate of the Vertex.
     * @return the X coordinate of the Vertex
     */
    public int getX(){
        return x;
    }

    /**
     * This method returns the Y coordinate of the Vertex.
     * @return the Y coordinate of the Vertex
     */
    public int getY(){
        return y;
    }

    /**
     * This method sets the X coordinate to the given value.
     * @param x the new X coordinate
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * This method sets the Y coordinate to the given value.
     * @param y the new Y coordinate
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * This method sets the name of the Vertex to the given String.
     * @param name the new name of the Vertex
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * This method returns the distance from this Vertex to the given Vertex.
     * @param vertex the Vertex to find the distance to
     * @return the distance between the two Vertex objects
     */
    public double getDistance(Vertex vertex){
        return Math.sqrt(Math.pow(vertex.getX() - x, 2) + Math.pow(vertex.getY() - y, 2));
    }

    /**
     * This method creates a String representation of this Vertex.
     * @return the String representation
     */
    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
