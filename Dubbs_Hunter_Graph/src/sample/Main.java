package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Hunter Dubbs
 * @version 12/3/2018
 * made for CIT360 at PCT
 *
 * This program reads in a series of points and edges from a file, draws them
 * in a GUI, and then displays the shortest path between the start and end points
 * specified in the file. This path is displayed in red and is found using Dijksra's
 * Single Source Shortest Path algorithm. The points and edges are stored and manipulated
 * using the Weighted Graph data structure.
 */
public class Main extends Application {

    //the weighted graph to store the graph
    private static WGraph<Vertex> graph = new WGraph();
    private static Vertex start, end;
    private static Canvas canvas;

    /**
     * This method builds the window and calls the methods to load
     * and process the data before drawing it to the window.
     * @param primaryStage the main window
     * @throws Exception runtime exceptions and IO exceptions
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        canvas = new Canvas(500, 400);
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(5);
        loadDataAndDraw(); //load the data and draw the graph
        drawPath(graph.shortestPathToPoint(start, end)); //find and draw the shortest path
        Group root = new Group(canvas);
        primaryStage.setTitle("Shortest Path Graph");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }

    /**
     * This is the main method of the program that calls the application
     * launch method.
     * @param args launch arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method reads the "graph.dat" file and draws and saves the points,
     * the edges, and saves the start and end points.
     * @throws IOException throws exception if the file cannot be read
     */
    private static void loadDataAndDraw() throws IOException{
        Scanner sc = new Scanner(new File("graph.dat"));
        int numVertices = Integer.parseInt(sc.nextLine());
        String[] args;
        for(int i = 0; i < numVertices; i++){
            args = sc.nextLine().split(" ");
            graph.addVertex(new Vertex(args[2], Integer.parseInt(args[0]), Integer.parseInt(args[1])));
            canvas.getGraphicsContext2D().fillOval(Integer.parseInt(args[0]) - 5,Integer.parseInt(args[1]) - 5, 10, 10);
        }
        int numEdges = Integer.parseInt(sc.nextLine());
        Vertex v1, v2;
        for(int i = 0; i < numEdges; i++){
            args = sc.nextLine().split(" ");
            v1 = graph.getVertexByName(args[0]);
            v2 = graph.getVertexByName(args[1]);
            graph.addEdge(v1, v2, v1.getDistance(v2));
            canvas.getGraphicsContext2D().strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
        }
        sc.nextLine();
        args = sc.nextLine().split(" ");
        start = graph.getVertexByName(args[0]);
        end = graph.getVertexByName(args[1]);
        sc.close();
    }

    /**
     * This method draws the start and end points on the graph
     * as well as a red path following the path passed into it as
     * an argument. To find the shortest path, a method that returns
     * the shortest path should be passed into this method.
     * @param path an ArrayList of the points along the path to be drawn
     */
    private static void drawPath(ArrayList<Vertex> path){
        //draw start and end points
        canvas.getGraphicsContext2D().setFill(Color.RED);
        canvas.getGraphicsContext2D().fillOval(start.getX() - 10,
                start.getY() - 10, 20, 20);
        canvas.getGraphicsContext2D().fillOval(end.getX() - 10,
                end.getY() - 10, 20, 20);
        //draw shortest path
        Vertex prev = end;
        canvas.getGraphicsContext2D().setStroke(Color.RED);
        canvas.getGraphicsContext2D().setLineWidth(3);
        for(Vertex v : path){
            canvas.getGraphicsContext2D().strokeLine(prev.getX(), prev.getY(),
                    v.getX(), v.getY());
            prev = v;
        }
    }
}
