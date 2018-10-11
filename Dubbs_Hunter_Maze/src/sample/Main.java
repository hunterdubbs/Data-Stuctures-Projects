package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private final static int mazeWidth = 30;
    private final static int mazeHeight = 20;
    private final static int cellSize = 30;
    private final static int margin = 25;
    private final static double density = 0.75;
    private final static double pathTerminationWeight = 0.05;
    private final static double pathTurnWeight = 0.4;

    private static int[][] distance;
    private static boolean[][] walls;

    private static Stage stage;
    private static Group root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Group();
        generateMaze();
        buildScene();
        primaryStage.setTitle("Maze Solver");
        primaryStage.setScene(new Scene(root, mazeWidth * cellSize + margin * 2, mazeHeight * cellSize + margin * 2));
        primaryStage.show();
        stage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);

    }

    public static void buildScene(){
        Line hLine, vLine;
        int x, y;
        for(int i = 0; i < mazeWidth; i++){
            for(int j = 0; j < mazeHeight; j++){
                if(walls[i][j]){
                    x = i * cellSize + cellSize / 2 + margin;
                    y = j * cellSize + cellSize / 2 + margin;
                    hLine = new Line(x, y, x, y);
                    vLine = new Line(x, y, x, y);
                    if(i < mazeWidth - 1 && walls[i + 1][j]){
                        hLine.setEndX(hLine.getEndX() + cellSize / 2);
                    }
                    if(i > 0 && walls[i - 1][j]){
                        hLine.setStartX(hLine.getStartX() - cellSize / 2);
                    }
                    if(j < mazeHeight - 1 && walls[i][j + 1]){
                        vLine.setEndY(vLine.getEndY() + cellSize / 2);
                    }
                    if(j > 0 && walls[i][j - 1]){
                        vLine.setStartY(vLine.getStartY() - cellSize / 2);
                    }
                    hLine.setStrokeWidth(3);
                    vLine.setStrokeWidth(3);
                    root.getChildren().add(hLine);
                    root.getChildren().add(vLine);
                }
            }
        }
//        for(int i = 0; i < mazeWidth; i++){
//            for(int j = 0; j < mazeHeight; j++){
//                if(walls[i][j]){
//                    root.getChildren().add(new Rectangle(i * cellSize + margin,
//                            j * cellSize + margin, cellSize, cellSize));
//                }
//            }
//        }
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
    }

    private static int randSign(){
        if(Math.random() > 0.5){
            return 1;
        }
        return -1;
    }
}
