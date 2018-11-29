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

public class Main extends Application {

    private static WGraph<String> graph = new WGraph();
    private static Vertex vertexIndex = new Vertex();
    private static String start, end;
    private static Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception{
        canvas = new Canvas(500, 400);
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(5);
        loadDataAndDraw();
        drawPath(graph.shortestPathToPoint(start, end));
        Group root = new Group(canvas);
        primaryStage.setTitle("Shortest Path Graph");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void loadDataAndDraw() throws IOException{
        Scanner sc = new Scanner(new File("graph.dat"));
        int numVertices = Integer.parseInt(sc.nextLine());
        String[] args;
        for(int i = 0; i < numVertices; i++){
            args = sc.nextLine().split(" ");
            vertexIndex.addVertex(args[2], Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            graph.addVertex(args[2]);
            canvas.getGraphicsContext2D().fillOval(Integer.parseInt(args[0]) - 5,Integer.parseInt(args[1]) - 5, 10, 10);
        }
        int numEdges = Integer.parseInt(sc.nextLine());
        for(int i = 0; i < numEdges; i++){
            args = sc.nextLine().split(" ");
            graph.addEdge(args[0], args[1], vertexIndex.getDistance(args[0], args[1]));
            canvas.getGraphicsContext2D().strokeLine(vertexIndex.getLocation(args[0]).x,
                    vertexIndex.getLocation(args[0]).y, vertexIndex.getLocation(args[1]).x,
                    vertexIndex.getLocation(args[1]).y);
        }
        sc.nextLine();
        args = sc.nextLine().split(" ");
        start = args[0];
        end = args[1];
        sc.close();
    }

    private static void drawPath(ArrayList<String> path){
        //draw start and end points
        canvas.getGraphicsContext2D().setFill(Color.RED);
        canvas.getGraphicsContext2D().fillOval(vertexIndex.getLocation(start).x - 10,
                vertexIndex.getLocation(start).y - 10, 20, 20);
        canvas.getGraphicsContext2D().fillOval(vertexIndex.getLocation(end).x - 10,
                vertexIndex.getLocation(end).y - 10, 20, 20);
        //draw shortest path
        String prev = end;
        canvas.getGraphicsContext2D().setStroke(Color.RED);
        canvas.getGraphicsContext2D().setLineWidth(3);
        for(String str : path){
            canvas.getGraphicsContext2D().strokeLine(vertexIndex.getLocation(prev).x, vertexIndex.getLocation(prev).y,
                    vertexIndex.getLocation(str).x, vertexIndex.getLocation(str).y);
            prev = str;
        }
    }
}
