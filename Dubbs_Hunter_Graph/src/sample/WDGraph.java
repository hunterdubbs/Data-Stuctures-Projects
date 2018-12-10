package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CIT360 at PCT
 * @version 12/3/2018
 * @param <T> the data type to be stored in the graph
 *
 * This class provides an implementation of a Weighted Directed Graph
 * and all of its associated methods. It's shortest path algorithm uses
 * Dijksra's Single Source Shortest Path Algorithm.
 */
public class WDGraph<T>{

    private int CAPACITY = 2; //the initial capacity of the graph
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private int numVertices;
    private int numEdges;
    private double[][] adjMatrix;
    private T[] vertices;

    /**
     * The default constructor for WDGraph initializes its internal arrays.
     */
    public WDGraph() {
        numVertices = 0;
        numEdges = 0;
        adjMatrix = new double[CAPACITY][CAPACITY];
        vertices = (T[]) new Object[CAPACITY];
        //default the adjacency array to INFINITY
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                adjMatrix[i][j] = INFINITY;
            }
        }
    }

    /**
     * This method returns a String representation showing the adjacency values
     * between the points in a table.
     * @return the String representation of the object
     */
    public String toString(){
        int GAP = 5;
        if(numVertices == 0)
            return "Graph is empty";
        String result = "";

        result += String.format("%7s", "");
        for (int i = 0; i < numVertices; i++)
            result += String.format("%7s", vertices[i]);
        result += "\n";

        for (int i = 0; i < numVertices; i++) {
            result += String.format("%7s", vertices[i]);

            for (int j = 0; j < numVertices; j++) {
                if(adjMatrix[i][j] == INFINITY)
                    //result += String.format("%7s", "inf");
                    result += String.format("%7c", '\u221e');
                else
                    result += String.format("%7.2f", adjMatrix[i][j]);
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices
     */
    public int numVertices() {
        return numVertices;
    }

    /**
     * Returns the number of edges in the graph.
     * @return the number of edges
     */
    public int numEdges() {
        return numEdges;
    }

    /**
     * This method adds a vertex to the graph, expanding the internal
     * arrays if necessary.
     * @param vertex the vertex to be added
     */
    public void addVertex(T vertex) {
        if(!isValidVertex(vertex)) {
            if(numVertices == CAPACITY)
                expand();
            vertices[numVertices] = vertex;    //check to see if expansion is needed
            numVertices++;
        }
        //need to throw exception or assertion if vertex already exists

    }

    /**
     * This method returns the index of the array in the internal array.
     * @param vertex the vertex to lookup
     * @return  the index of the vertex.  return -1 if not found
     */
    protected int vertexIndex(T vertex){
        for(int i = 0; i < numVertices; i++)
            if(vertices[i].equals(vertex))
                return i;
        return -1;
    }

    /**
     * This method returns whether or not the Vertex is in the graph.
     * @param vertex the vertex to validate
     * @return whether or not the Vertex is in the graph.
     */
    protected boolean isValidVertex(T vertex){
        for(int i = 0; i < numVertices; i++)
            if(vertices[i].equals(vertex))
                return true;
        return false;
    }

    /**
     * This method adds an edge between two vertices in the graph.
     * @param vertex1 the from vertex
     * @param vertex2 the to vertex
     * @param weight the weight of the edge
     */
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(isValidVertex(vertex1)  && isValidVertex(vertex2) && vertex1 != vertex2 && weight >= 0){
            if(!this.existEdge(vertex1, vertex2))
                numEdges++;
            adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] = weight;

        }
    }

    /**
     * This method removes an edge from the graph.
     * @param vertex1 the from vertex
     * @param vertex2 the to vertex
     */
    public void removeEdge(T vertex1, T vertex2) {
        if(existEdge(vertex1, vertex2)){
            adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] = INFINITY;
            numEdges--;
        }
    }

    /**
     * This method returns whether or not the graph is empty.
     * @return whether or not the graph is empty
     */
    public boolean isEmpty() {
        return numVertices == 0;
    }

    /**
     * Returns whether or not an edge exists between the two vertices.
     * @param vertex1 the from vertex
     * @param vertex2 the to vertex
     * @return whether or not there is an edge
     */
    public boolean existEdge(T vertex1, T vertex2) {
        return isValidVertex(vertex1) &&
                isValidVertex(vertex2) &&
                (adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] != INFINITY);
    }

    /**
     * Returns the number of vertices and edges in the graph.
     * @return the number of components
     */
    public int numComponents() {
        return numVertices + numEdges;
    }

    /**
     * This method expands the internal arrays of the graph to allow
     * more vertices to be added.
     */
    private void expand() {
        int newCapacity = CAPACITY * 2;

        //System.out.println("Expanding: old capacity = " + CAPACITY);
        double[][] newAdjMatrix = new double[newCapacity][newCapacity];
        T[] newVertices = (T[]) new Object[newCapacity];

        for(int i = 0; i < numVertices; i++)
            newVertices[i] = vertices[i];

        for (int i = 0; i < newAdjMatrix.length; i++)
            for(int j = 0; j < newAdjMatrix.length; j++)
                newAdjMatrix[i][j] = INFINITY;

        for (int i = 0; i < numVertices; i++)
            for(int j = 0; j < numVertices; j++)
                newAdjMatrix[i][j] = adjMatrix[i][j];

        adjMatrix = newAdjMatrix;
        vertices = newVertices;
        CAPACITY = newCapacity;
    }

    /**
     * This method returns a list of all of a vertex's neighbors.
     * @param vertex the vertex
     * @return the neighboring vertices
     */
    public List<T> neighbors(T vertex) {
        if(!isValidVertex(vertex))
            return null;
        int row = vertexIndex(vertex);
        ArrayList<T> list = new ArrayList();
        for(int i = 0; i < numVertices; i++)
            if(adjMatrix[row][i] != INFINITY)
                list.add(vertices[i]);
        return list;
    }

    /**
     * This method returns the next neighbor after the current neighbor, if it exists.
     * @param vertex the vertex
     * @param currNeighbor the current neighboring vertex
     * @return the next neighbor
     */
    public T nextNeighbor(T vertex, T currNeighbor) {
        if(!isValidVertex(vertex) || !isValidVertex(currNeighbor))
            return null;
        int row = vertexIndex(vertex);
        int col = vertexIndex(currNeighbor);
        for(int i = col + 1; i < numVertices; i++)
            if(adjMatrix[row][i] != INFINITY)
                return vertices[i];
        return null;
    }

    /**
     * This method uses Dijksra's single source shortest path algorithm
     * It returns the previous array containing the shortest previous
     * vertices from the given source vertex to all other vertices.
     *
     * @param sourceVertex the vertex to find paths from
     * @return the array of previous vertices along the paths
     */
    public T[] singleSourceShortestPath(T sourceVertex) {
        double distances[] = new double[numVertices];
        boolean processed[] = new boolean[numVertices];
        T prev[] = (T[]) new Object[numVertices];
        ArrayList<T> neighbors;
        //initialize the distance array to INFINITY
        for(int i = 0; i < distances.length; i++){
            distances[i] = INFINITY;
        }
        distances[vertexIndex(sourceVertex)] = 0;
        boolean done = false;
        int minIndex;
        //find each shortest distance and find the shortest path of its neighbors
        while(!done){
            minIndex = -1;
            done = true;
            for(int i = 0; i < distances.length; i++){
                if(!processed[i] && (minIndex == -1 || distances[i] < distances[minIndex])){
                    minIndex = i;
                    done = false;
                }
            }
            if(done){
                break;
            }
            processed[minIndex] = true;
            neighbors = (ArrayList<T>) neighbors(vertices[minIndex]);
            //if this path is shorter, make this the new path
            for(T vertex : neighbors){
                if(!processed[vertexIndex(vertex)] && adjMatrix[minIndex][vertexIndex(vertex)] + distances[minIndex]
                        < distances[vertexIndex(vertex)]){
                    distances[vertexIndex(vertex)] = adjMatrix[minIndex][vertexIndex(vertex)] + distances[minIndex];
                    prev[vertexIndex(vertex)] = vertices[minIndex];
                }
            }
        }
        return prev;
    }

    /**
     * This method calls the singleShortestPath() method and traces back the path
     * from an destination vertex to the source vertex, then returns it in a list.
     * @param sourceVertex the source vertex
     * @param destinationVertex the destination vertex
     * @return the vertices along the path from the destination to the source
     */
    public ArrayList<T> shortestPathToPoint(T sourceVertex, T destinationVertex){
        T[] paths = singleSourceShortestPath(sourceVertex);
        ArrayList<T> shortestPath = new ArrayList();
        T curVertex = destinationVertex;
        shortestPath.add(curVertex);
        //trace back the path along the paths array
        while(true){
            //end when it reaches the beginning of the path
            if(curVertex.equals(sourceVertex)){
                return shortestPath;
            }
            curVertex = paths[vertexIndex(curVertex)];
            shortestPath.add(curVertex);
        }
    }

    /**
     * In the event that this class stores objects of class Vertex, this
     * method allows the Vertex objects to be looked up by their name
     * attribute.
     * @param name the name of the Vertex to return
     * @return the Vertex
     */
    public T getVertexByName(String name){
        if(vertices[0].getClass().equals(Vertex.class)) {
            Vertex temp;
            for (int i = 0; i < vertices.length; i++) {
                temp = (Vertex) vertices[i];
                if (temp.getName().equals(name)) {
                    return vertices[i];
                }
            }
        }
        return null;
    }
}