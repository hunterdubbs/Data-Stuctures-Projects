package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			drawTriangle(1, 1, 200, 400, 400, 1, 5, root);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int n, Group root) {
		n--;
		if(n == 0) {
			root.getChildren().add(new Line(x1, y1, x2, y2));
			root.getChildren().add(new Line(x2, y2, x3, y3));
			root.getChildren().add(new Line(x1, y1, x3, y3));
		}
		if(n > 0) {
			drawTriangle(mid(x1, x2), mid(y1, y2), mid(x1, x3), mid(y1, y3), x1, y1, n, root);
			drawTriangle(mid(x1, x2), mid(y1, y2), mid(x2, x3), mid(y2, y3), x2, y2, n, root);
			drawTriangle(mid(x1, x3), mid(y1, y3), mid(x2, x3), mid(y2, y3), x3, y3, n, root);

		}
	}
	
	private static int mid(int x1, int x2) {
		return (x1 + x2) / 2;
	}
}
