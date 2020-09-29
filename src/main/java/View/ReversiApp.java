package View;

import javafx.application.Application;
import javafx.stage.Stage;

public class ReversiApp extends Application {
    static Stage stage;
    private Menu m = new Menu();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Reversi");
        stage.setResizable(false);
        stage.setScene(m.createMenu());
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
