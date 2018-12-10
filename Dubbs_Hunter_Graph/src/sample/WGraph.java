package sample;

/**
 * @author CIT360 at PCT
 * @version 12/3/2018
 *
 * This class extends and modifies the WDGraph class to
 * implement a Weighted Graph.
 */
public class WGraph<T> extends WDGraph<T>{

    /**
     * The default constructor calls the super constructor.
     */
    public WGraph() {
        super();
    }

    /**
     * This method returns the number of edges in the graph.
     * @return the number of edges
     */
    @Override
    public int numEdges() {
        return super.numEdges()/2;
    }

    /**
     * This method adds an edge to the graph. Since it is built on a
     * directional graph, it creates two edges, one going each direction.
     * @param vertex1 the from vertex
     * @param vertex2 the to vertex
     * @param weight the weight of the edge
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(isValidVertex(vertex1)  && isValidVertex(vertex2) && vertex1 != vertex2 && weight >= 0){
            super.addEdge(vertex1, vertex2, weight);
            super.addEdge(vertex2, vertex1, weight);
        }
    }

    /**
     * This method removes an edge from the graph, removing the ones going in
     * each direction.
     * @param vertex1 the from vertex
     * @param vertex2 the to vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        super.removeEdge(vertex1, vertex2);
        super.removeEdge(vertex2, vertex1);
    }
}