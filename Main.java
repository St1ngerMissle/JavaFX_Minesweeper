import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    Minefield minefield;
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        minefield = new Minefield();
        Minefield centerPane = minefield;
        centerPane.setAlignment(Pos.CENTER);
        
        StackPane topPane = new StackPane();

        root.setTop(topPane);
        root.setCenter(centerPane);

        int buffer = 40;
        Scene scene = new Scene(root,minefield.getPixelWidth()+buffer,minefield.getPixelHeight()+buffer);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}