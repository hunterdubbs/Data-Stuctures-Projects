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

/**
 * Maze Assignment
 * @author Hunter Dubbs
 * @version 11/3/2018
 * made for CIT360 at PCT
 *
 * This program uses JavaFX to generate random mazes and then display the paths from two
 * random points, as calculated by the program using both a depth-first search (blue) and
 * a breadth-first search (green). Additional mazes can be generated using the "Generate Maze"
 * button near the bottom of the window. If there is no path between the two points, the program
 * will display the message "No valid path exists" beside the generate new maze button.
 */
public class Main extends Application {

    //the distance from the edge of the window to the edge of the maze
    private final static int margin = 25;
    //density is broken up into these three values for greater control
    private final static double density = 0.75;
    private final static double pathTerminationWeight = 0.05;
    private final static double pathTurnWeight = 0.4;
    //defines the pixel size of the maze
    private final static int overallMazeWidth = 900;
    private final static int overallMazeHeight = 600;

    //the number of blocks in the maze and the size of those blocks
    private static int mazeWidth, mazeHeight, cellSize;

    private static boolean[][] walls;
    private static Point start, finish;

    private static Button generateButton;
    private static Stage stage;
    private static Group root;
    private static HBox infoArea;
    private static Text resultText;

    /**
     * The start method sets up the window and calls an initial maze.
     * @param primaryStage the application's window
     */
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        stage.setResizable(false);
        resultText = new Text(); //error messages are displayed using this Text.
        generateButton = new Button("Generate New Maze");
        //calls all methods necessary to generate and solve a new maze after a button push
        generateButton.setOnAction(event -> {
            generateMazeParams();
            generateMaze();
            buildScene();
            solveMaze();
            primaryStage.setScene(new Scene(root, mazeWidth * cellSize + margin * 2, mazeHeight * cellSize + margin * 2 + 20));
        });
        //generates and solves the initial maze
        generateMazeParams();
        generateMaze();
        buildScene();
        solveMaze();
        //configures window
        primaryStage.setTitle("Maze Solver");
        primaryStage.setScene(new Scene(root, mazeWidth * cellSize + margin * 2, mazeHeight * cellSize + margin * 2 + 20));
        primaryStage.show();
    }

    /**
     * The main method starts the application.
     * @param args the program's launch arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method sets the number of blocks in the maze both vertically
     * and horizontally as well as the size of each maze cell. The parameters
     * that define the overall maze size are used to keep each maze the same
     * size and maintain the ratio.
     */
    private static void generateMazeParams(){
        mazeWidth = (((int) Math.floor(Math.random() * 3) + 1) * 15);
        cellSize = overallMazeWidth / mazeWidth;
        mazeHeight = overallMazeHeight / cellSize;
    }

    /**
     * This method reads the 2D array walls and draws the maze according
     * to where that array specifies they should be. It also draws the start
     * and finish points as well as the generate maze button and the result text.
     */
    private static void buildScene(){
        //places button and result text
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
        //draw start and finish points
        root.getChildren().add(new Circle(start.getX() * cellSize + cellSize / 2 + margin,
                start.getY() * cellSize + cellSize / 2 + margin, cellSize / 3, new Color(1, 0, 0, 1)));
        root.getChildren().add(new Circle(finish.getX() * cellSize + cellSize / 2 + margin,
                finish.getY() * cellSize + cellSize / 2 + margin, cellSize / 3, new Color(1, 0, 0, 1)));
    }

    /**
     * This method determines which cells of the maze will be walls,
     * and marks them in the 2D array walls.
     */
    private static void generateMaze(){
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
        //generates interior walls as a series of randomly generated paths
        int x, y, deltaX, deltaY;
        //how many paths will be generated
        for(int i = 0; i < mazeHeight * mazeWidth  * density / 5; i++){
            //starts them at a random point in the maze
            x = (int) Math.floor(Math.random() * mazeWidth);
            y = (int) Math.floor(Math.random() * mazeHeight);
            deltaX = 0;
            deltaY = 0;
            //determines which direction they will go along
            if(randSign() == 1){
                deltaX = randSign();
            }else{
                deltaY = randSign();
            }
            do{
                walls[x][y] = true;
                x += deltaX;
                y += deltaY;
                //after each cell, they have a chance of changing direction
                if(Math.random() < pathTurnWeight){
                    if(deltaX != 0){
                        deltaX = 0;
                        deltaY = randSign();
                    }else{
                        deltaY = 0;
                        deltaX = randSign();
                    }
                }
                //keeps the paths from "hugging" the exterior walls
                if(x > 1 && y > 1 && x < mazeWidth - 2 && y < mazeHeight - 2 && walls[x + deltaX][y + deltaY]){
                    break;
                }
                if(x > 0 && y > 0 && x < mazeWidth - 1 && y < mazeHeight - 1) {
                    //helps keep paths open by prevent walls from excessively generating right beside each other, forming large blobs
                    if (deltaX == 0 && (walls[x + 1][y + deltaY] || walls[x - 1][y + deltaY])) {
                        break;
                    } else if (walls[x + deltaX][y + 1] || walls[x + deltaX][y - 1]) {
                        break;
                    }
                }
                //after each cell, the paths have a chance of stopping
            }while(Math.random() > pathTerminationWeight && x > 0 && x < mazeWidth - 1 && y > 0 && y < mazeHeight - 1);
        }
        //generate start and finish points
        start = randEmptyPoint();
        finish = randEmptyPoint();
    }

    /**
     * This helper method can be called in calculations to get a random sign.
     * @return returns 1 or -1
     */
    private static int randSign(){
        if(Math.random() > 0.5){
            return 1;
        }
        return -1;
    }

    /**
     * This method finds an empty point in the maze and returns it.
     * @return an available point
     */
    private static Point randEmptyPoint(){
        int x, y;
        do{
            x = (int) (Math.random() * mazeWidth);
            y = (int) (Math.random() * mazeHeight);
        }while(walls[x][y]);
        return new Point(x, y);
    }

    /**
     * This method utilizes the java.util Stack to perform a depth-first
     * search using the information stored in the walls array. It will locate
     * a path from the points start and finish and then return point along that
     * path in the form of a stack. If there is no path, it will return null.
     * @return a stack of points leading to the finish point
     */
    private static Stack<Point> findPathDFS(){
        Stack<Point> path = new Stack();
        //keeps track of which cells it has already visited
        boolean[][] visited = new boolean[mazeWidth][mazeHeight];
        int x, y;
        //starts at the start point
        path.push(start);
        //continues looking so long as there is a point to progress from
        while(!path.isEmpty()){
            x = (int) path.peek().getX();
            y = (int) path.peek().getY();
            visited[x][y] = true;
            //checks if it is at destination, and returns the stack if it is
            if(finish.getX() == x && finish.getY() == y){
                return path;
            }else if(!walls[x][y - 1] && !visited[x][y - 1]){ //checks down
                path.push(new Point(x, y - 1));
            }else if(!walls[x][y + 1] && !visited[x][y + 1]){ //checks up
                path.push(new Point(x, y + 1));
            }else if(!walls[x + 1][y] && !visited[x + 1][y]){ //checks right
                path.push(new Point(x + 1, y));
            }else if(!walls[x - 1][y] && !visited[x - 1][y]){ //checks left
                path.push(new Point(x - 1, y));
            }else{
                //if there is no path forward, it will backtrack
                path.pop();
            }
        }
        //if everything as been checked and it did not reach the finish, there is not path
        return null;
    }

    /**
     * This method uses a queue to perform a breadth-first search to find
     * one of the shortest paths from the start point to the finish point.
     * The path will be returned as a series of points stored in an ArrayList.
     * If there is no path, it will return null.
     * @return an ArrayList of points leading to the finish point
     */
    private static ArrayList<Point> findPathBFS(){
        //the queue that stores the points to check
        ArrayQueue<Point> points = new ArrayQueue<Point>();
        //stores how far away each point is from the start point
        int[][] distance = new int[mazeWidth][mazeHeight];
        //keeps track of cells that have already been visited
        boolean[][] visited = new boolean[mazeWidth][mazeHeight];
        int x, y;
        int curDist = 0;
        //starts with the point start
        points.enqueue(start);
        //keeps going as long as there are points to go from
        while(!points.isEmpty()){
            Point point = points.dequeue();
            x = (int) point.getX();
            y = (int) point.getY();
            //checks to see if it is at the finish point
            if(finish.getX() == x && finish.getY() == y){
                //track back to get the path and return it
                ArrayList<Point> path = new ArrayList();
                path.add(finish);
                //uses this method to transform the distance array into a usable path
                traceBack(path, point, distance, distance[x][y] - 1);
                return path;
            }else{
                //expands search out in all directions assuming there is no walls in the way
                curDist = distance[x][y] + 1;
                if(!walls[x][y - 1] && !visited[x][y - 1]){ //down
                    points.enqueue(new Point(x, y - 1));
                    visited[x][y - 1] = true;
                    distance[x][y - 1] = curDist;
                }
                if(!walls[x][y + 1] && !visited[x][y + 1]){ //up
                    points.enqueue(new Point(x, y + 1));
                    visited[x][y + 1] = true;
                    distance[x][y + 1] = curDist;
                }
                if(!walls[x + 1][y] && !visited[x + 1][y]){ //right
                    points.enqueue(new Point(x + 1, y));
                    visited[x + 1][y] = true;
                    distance[x + 1][y] = curDist;
                }
                if(!walls[x - 1][y] && !visited[x - 1][y]){ //left
                    points.enqueue(new Point(x - 1, y));
                    visited[x - 1][y] = true;
                    distance[x - 1][y] = curDist;
                }
            }
        }
        //if the point finish was not found and there are no more points in the queue, there is no path
        return null;
    }

    /**
     * This recursive method builds an ArrayList of points of a shortest path
     * working backwards from the finish point and looking for the next distance
     * value in the distance array as it goes. Once it makes it to distance 0,
     * it has made it back to the start point and is finished building the path.
     * @param path the ArrayList of points that is being built
     * @param point the current point that it is checking around
     * @param distance the array of distances from the start point for each visited cell
     * @param dist the distance from the start point that it is looking for
     */
    private static void traceBack(ArrayList<Point> path, Point point, int[][] distance, int dist){
        //check if it is done
        if(dist == 0) {
            path.add(point);
            path.add(start);
            return;
        }
        //else call the method again for the adjacent point that has the next distance value
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

    /**
     * This method calls both the depth-first search algorithm and the
     * breadth-first search algorithm, then draws the paths on the maze
     * if there were available paths. Otherwise, it will indicate that
     * there are no paths in the result text.
     */
    private static void solveMaze(){
        //performs the depth-first search
        Stack<Point> dfsPath = findPathDFS();
        //checks if it returned a path
        if(dfsPath == null || dfsPath.isEmpty()){
            resultText.setText("No Valid Path Exists");
            //does not bother calling the other search if the first one does not find a path
        }else{
            //iterates through the path in the stack of points and draws the path
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
            //performs the breadth-first search
            ArrayList<Point> bfsPath = findPathBFS();
            //iterates through the ArrayList of points and draws the path
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
            //sets the result to indicate that a path was found
            resultText.setText("Path Found");
        }
    }

}
