package sample;

import java.util.ArrayList;
import java.util.List;

public class WDGraph<T>{


    private int CAPACITY = 2;
    private final double INFINITY = Double.POSITIVE_INFINITY;
    private int numVertices;
    private int numEdges;
    private double[][] adjMatrix;
    private T[] vertices;

    public WDGraph() {
        numVertices = 0;
        numEdges = 0;
        adjMatrix = new double[CAPACITY][CAPACITY];
        vertices = (T[]) new Object[CAPACITY];

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                adjMatrix[i][j] = INFINITY;
            }
        }
    }

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

    public int numVertices() {
        return numVertices;
    }

    public int numEdges() {
        return numEdges;
    }

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
     *
     * @param vertex
     * @return  the index of the vertex.  return -1 if not found
     */
    protected int vertexIndex(T vertex){
        for(int i = 0; i < numVertices; i++)
            if(vertices[i].equals(vertex))
                return i;
        return -1;
    }


    protected boolean isValidVertex(T vertex){
        for(int i = 0; i < numVertices; i++)
            if(vertices[i].equals(vertex))
                return true;
        return false;
    }


    /**
     * add or update an edge
     */
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(isValidVertex(vertex1)  && isValidVertex(vertex2) && vertex1 != vertex2 && weight >= 0){
            if(!this.existEdge(vertex1, vertex2))
                numEdges++;
            adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] = weight;

        }
    }

    public void removeVertex(T vertex) {
        // TODO Auto-generated method stub

    }

    public void removeEdge(T vertex1, T vertex2) {
        if(existEdge(vertex1, vertex2)){
            adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] = INFINITY;
            numEdges--;
        }
    }

    public boolean isEmpty() {
        return numVertices == 0;
    }

    public boolean existEdge(T vertex1, T vertex2) {
        return isValidVertex(vertex1) &&
                isValidVertex(vertex2) &&
                (adjMatrix[vertexIndex(vertex1)][vertexIndex(vertex2)] != INFINITY);
    }

    public int numComponents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }


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
     * lists all the beighbors of the gioven vertex
     * @param vertex
     * @return
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
     * returns the next neighbor after the current neighbor, if exists
     * @param vertex
     * @param currNeighbor
     * @return
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
     * method uses Dijksra's single source shortest path algorithm
     * It returns the previous array containing the shortest previous
     * vertices from the given source vertex to all other
     * vertices.
     *
     * @param sourceVertex
     * @return
     */
    public T[] singleSourceShortestPath(T sourceVertex) {
        double distances[] = new double[numVertices];
        boolean processed[] = new boolean[numVertices];
        T prev[] = (T[]) new Object[numVertices];
        ArrayList<T> neighbors;
        for(int i = 0; i < distances.length; i++){
            distances[i] = INFINITY;
        }
        distances[vertexIndex(sourceVertex)] = 0;
        boolean done = false;
        int minIndex;
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

    public ArrayList<T> shortestPathToPoint(T sourceVertex, T destinationVertex){
        T[] paths = singleSourceShortestPath(sourceVertex);
        ArrayList<T> shortestPath = new ArrayList();
        T curVertex = destinationVertex;
        shortestPath.add(curVertex);
        while(true){
            if(curVertex.equals(sourceVertex)){
                return shortestPath;
            }
            curVertex = paths[vertexIndex(curVertex)];
            shortestPath.add(curVertex);
        }
    }
}