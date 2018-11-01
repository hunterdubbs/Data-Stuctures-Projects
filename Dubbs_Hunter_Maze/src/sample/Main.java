package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

public class Main extends Application {

    private final static int margin = 25;
    //density is broken up into these three values for greater control
    private final static double density = 0.75;
    private final static double pathTerminationWeight = 0.05;
    private final static double pathTurnWeight = 0.4;
    //defines the pixel size of the maze
    private final static int overallMazeWidth = 900;
    private final static int overallMazeHeight = 600;

    //the specifications on the number of blocks in the maze
    private static int mazeWidth, mazeHeight, cellSize;

    private static boolean[][] walls;
    private static Point start, finish;

    private static Button generateButton;
    private static Stage stage;
    private static Group root;
    private static HBox infoArea;
    private static Text resultText;

    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        stage.setResizable(false);
        resultText = new Text();
        generateButton = new Button("Generate New Maze");
        generateButton.setOnAction(event -> {
            generateMazeParams();
            generateMaze();
            buildScene();
            solveMaze();
            primaryStage.setScene(new Scene(root, mazeWidth * cellSize + margin * 2, mazeHeight * cellSize + margin * 2 + 20));
        });
        generateMazeParams();
        generateMaze();
        buildScene();
        solveMaze();
        primaryStage.setTitle("Maze Solver");
        primaryStage.setScene(new Scene(root, mazeWidth * cellSize + margin * 2, mazeHeight * cellSize + margin * 2 + 20));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void generateMazeParams(){
        mazeWidth = (((int) Math.floor(Math.random() * 3) + 1) * 15);
        cellSize = overallMazeWidth / mazeWidth;
        mazeHeight = overallMazeHeight / cellSize;
    }

    public static void buildScene(){
        //start with an empty maze
        infoArea = new HBox(25, generateButton, resultText);
        infoArea.setLayoutX(mazeWidth * cellSize / 2 + margin - 100);
        infoArea.setLayoutY(mazeHeight * cellSize + margin + 10);
        root = new Group();
        root.getChildren().add(infoArea);
        //draw walls
        for(int i = 0; i < mazeWidth; i++){
            for(int j = 0; j < mazeHeight; j++){
                if(walls[i][j]){
                    root.getChildren().add(new Rectangle(i * cellSize + margin, j * cellSize + margin, cellSize, cellSize));
                }
            }
        }
        //
        root.getChildren().add(new Circle(start.getX() * cellSize + cellSize / 2 + margin,
                start.getY() * cellSize + cellSize / 2 + margin, cellSize / 3, new Color(1, 0, 0, 1)));
        root.getChildren().add(new Circle(finish.getX() * cellSize + cellSize / 2 + margin,
                finish.getY() * cellSize + cellSize / 2 + margin, cellSize / 3, new Color(1, 0, 0, 1)));
    }

    public static void generateMaze(){
        //initializes walls 2D array
        walls = new boolean[mazeWidth][mazeHeight];
        //creates a perimeter around the maze
        for(int i = 0; i < mazeWidth; i++){
            walls[i][0] = true;
            walls[i][mazeHeight - 1] = true;
        }
        for(int i = 0; i < mazeHeight; i++){
            walls[0][i] = true;
            walls[mazeWidth - 1][i] = true;
        }
        //generate interior walls
        int x, y, deltaX, deltaY;
        for(int i = 0; i < mazeHeight * mazeWidth  * density / 5; i++){
            x = (int) Math.floor(Math.random() * mazeWidth);
            y = (int) Math.floor(Math.random() * mazeHeight);
            deltaX = 0;
            deltaY = 0;
            if(randSign() == 1){
                deltaX = randSign();
            }else{
                deltaY = randSign();
            }
            do{
                walls[x][y] = true;
                x += deltaX;
                y += deltaY;
                if(Math.random() < pathTurnWeight){
                    if(deltaX != 0){
                        deltaX = 0;
                        deltaY = randSign();
                    }else{
                        deltaY = 0;
                        deltaX = randSign();
                    }
                }
                if(x > 1 && y > 1 && x < mazeWidth - 2 && y < mazeHeight - 2 && walls[x + deltaX][y + deltaY]){
                    break;
                }
                if(x > 0 && y > 0 && x < mazeWidth - 1 && y < mazeHeight - 1) {
                    if (deltaX == 0 && (walls[x + 1][y + deltaY] || walls[x - 1][y + deltaY])) {
                        break;
                    } else if (walls[x + deltaX][y + 1] || walls[x + deltaX][y - 1]) {
                        break;
                    }
                }

            }while(Math.random() > pathTerminationWeight && x > 0 && x < mazeWidth - 1 && y > 0 && y < mazeHeight - 1);
        }
        //generate start and finish points
        start = randEmptyPoint();
        finish = randEmptyPoint();
    }

    private static int randSign(){
        if(Math.random() > 0.5){
            return 1;
        }
        return -1;
    }

    //write an actual efficient solution later
    private static Point randEmptyPoint(){
        int x, y;
        do{
            x = (int) (Math.random() * mazeWidth);
            y = (int) (Math.random() * mazeHeight);
        }while(walls[x][y]);
        return new Point(x, y);
    }

    private static Stack<Point> findPathDFS(){
        Stack<Point> path = new Stack();
        boolean[][] visited = new boolean[mazeWidth][mazeHeight];
        int x, y;
        path.push(start);
        while(!path.isEmpty()){
            x = (int) path.peek().getX();
            y = (int) path.peek().getY();
            visited[x][y] = true;
            if(finish.getX() == x && finish.getY() == y){
                return path;
            }else if(!walls[x][y - 1] && !visited[x][y - 1]){
                path.push(new Point(x, y - 1));
            }else if(!walls[x][y + 1] && !visited[x][y + 1]){
                path.push(new Point(x, y + 1));
            }else if(!walls[x + 1][y] && !visited[x + 1][y]){
                path.push(new Point(x + 1, y));
            }else if(!walls[x - 1][y] && !visited[x - 1][y]){
                path.push(new Point(x - 1, y));
            }else{
                path.pop();
            }
        }
        return null;
    }

    private static ArrayList<Point> findPathBFS(){
        ArrayQueue<Point> points = new ArrayQueue<Point>();
        int[][] distance = new int[mazeWidth][mazeHeight];
        boolean[][] visited = new boolean[mazeWidth][mazeHeight];
        int x, y;
        int curDist = 0;
        points.enqueue(start);
        while(!points.isEmpty()){
            Point point = points.dequeue();
            x = (int) point.getX();
            y = (int) point.getY();
            if(finish.getX() == x && finish.getY() == y){
                //track back to get the path and return it
                ArrayList<Point> path = new ArrayList();
                path.add(finish);
                traceBack(path, point, distance, distance[x][y] - 1);
                return path;
            }else{
                curDist = distance[x][y] + 1;
                if(!walls[x][y - 1] && !visited[x][y - 1]){
                    points.enqueue(new Point(x, y - 1));
                    visited[x][y - 1] = true;
                    distance[x][y - 1] = curDist;
                }
                if(!walls[x][y + 1] && !visited[x][y + 1]){
                    points.enqueue(new Point(x, y + 1));
                    visited[x][y + 1] = true;
                    distance[x][y + 1] = curDist;
                }
                if(!walls[x + 1][y] && !visited[x + 1][y]){
                    points.enqueue(new Point(x + 1, y));
                    visited[x + 1][y] = true;
                    distance[x + 1][y] = curDist;
                }
                if(!walls[x - 1][y] && !visited[x - 1][y]){
                    points.enqueue(new Point(x - 1, y));
                    visited[x - 1][y] = true;
                    distance[x - 1][y] = curDist;
                }
            }
        }
        return null;
    }

    private static void traceBack(ArrayList<Point> path, Point point, int[][] distance, int dist){
        if(dist == 0) {
            path.add(point);
            path.add(start);
            return;
        }
        if(distance[(int)point.getX() - 1][(int)point.getY()] == dist){
            path.add(new Point((int)point.getX() - 1, (int)point.getY()));
            traceBack(path, new Point(point.x - 1, point.y), distance, dist - 1);
        }else if(distance[(int)point.getX() + 1][(int)point.getY()] == dist){
            path.add(new Point((int)point.getX() + 1, (int)point.getY()));
            traceBack(path, new Point(point.x + 1, point.y), distance, dist - 1);
        }else if(distance[(int)point.getX()][(int)point.getY() - 1] == dist){
            path.add(new Point((int)point.getX(), (int)point.getY() - 1));
            traceBack(path, new Point(point.x, point.y - 1), distance, dist- 1);
        }else if(distance[(int)point.getX()][(int)point.getY() + 1] == dist){
            path.add(new Point((int)point.getX(), (int)point.getY() + 1));
            traceBack(path, new Point(point.x , point.y + 1), distance, dist- 1);
        }
    }

    private static void solveMaze(){
        Stack<Point> dfsPath = findPathDFS();
        if(dfsPath == null || dfsPath.isEmpty()){
            resultText.setText("No Valid Path Exists");
        }else{
            Point dfsPathPoint, nextDfsPathPoint;
            Line dispLine;
            int offset = cellSize / 2 + margin;
            nextDfsPathPoint = dfsPath.pop();
            while(!dfsPath.isEmpty()){
                dfsPathPoint = nextDfsPathPoint;
                nextDfsPathPoint = dfsPath.pop();
                dispLine = new Line(dfsPathPoint.x * cellSize + offset, dfsPathPoint.y * cellSize + offset,
                        nextDfsPathPoint.x * cellSize + offset, nextDfsPathPoint.y * cellSize + offset);
                dispLine.setStroke(new Color(0, 0, 1, 1));
                dispLine.setStrokeWidth(7);
                root.getChildren().add(dispLine);
            }
            ArrayList<Point> bfsPath = findPathBFS();
            Point bfsPathPoint, nextBfsPathPoint;
            nextBfsPathPoint = bfsPath.get(0);
            for(int i = 1; i < bfsPath.size(); i++){
                bfsPathPoint = nextBfsPathPoint;
                nextBfsPathPoint = bfsPath.get(i);
                dispLine = new Line(bfsPathPoint.x * cellSize + offset, bfsPathPoint.y * cellSize + offset,
                        nextBfsPathPoint.x * cellSize + offset, nextBfsPathPoint.y * cellSize + offset);
                dispLine.setStroke(new Color(0, 1, 0, 1));
                dispLine.setStrokeWidth(4);
                root.getChildren().add(dispLine);
            }
            resultText.setText("Path Found");
        }
    }

}
