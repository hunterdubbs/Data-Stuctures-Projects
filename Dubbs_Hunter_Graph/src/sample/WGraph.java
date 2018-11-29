package sample;

public class WGraph<T> extends WDGraph<T>{

    public WGraph() {
        super();
    }

    @Override
    public int numEdges() {
        return super.numEdges()/2;
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(isValidVertex(vertex1)  && isValidVertex(vertex2) && vertex1 != vertex2 && weight >= 0){
            super.addEdge(vertex1, vertex2, weight);
            super.addEdge(vertex2, vertex1, weight);
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        super.removeEdge(vertex1, vertex2);
        super.removeEdge(vertex2, vertex1);
    }
}