package View;

import Controller.Highlighted;
import Model.Board;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class Menu {
    private Board board = new Board();
    private static int boardSide = 800;
    private static int width = 1000;
    private Button restart = new Button("Restart");

    Scene createMenu(){
        board.newBoard();
        return repaint();
    }

    private void update(){
        ReversiApp.stage.setScene(repaint());
        ReversiApp.stage.show();
    }

    private void gameOver(){ //no css we die like noobs :(
        Pane gOver = new Pane();
        Image img = new Image(ReversiApp.class.getResourceAsStream("/gameover.png"));
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        gOver.setBackground(new Background(backgroundImage));
        gOver.setPrefSize(width,boardSide);

        Label playerWin = new Label(board.getWinner());
        playerWin.setFont(new Font("Arial", 30));
        playerWin.setTextFill(Color.GOLD);
        playerWin.setLayoutX(240);
        playerWin.setLayoutY(360);

        Label score = new Label(board.getScore());
        score.setFont(new Font("Arial", 30));
        score.setTextFill(Color.GOLD);
        score.setLayoutX(240);
        score.setLayoutY(560);

        restart.setOnMouseClicked(e -> {
            ReversiApp.stage.setScene(createMenu());
            ReversiApp.stage.setTitle("Reversi");
            ReversiApp.stage.show();
        });
        restart.setPrefSize(70,50);
        restart.setLayoutX(540);
        restart.setLayoutY(600);

        gOver.getChildren().addAll(playerWin, restart, score);

        ReversiApp.stage.setScene(new Scene(gOver));
        ReversiApp.stage.setTitle(board.getWinner());
        ReversiApp.stage.show();

    }

    private Scene repaint(){

        Pane brd = new Pane();
        brd.setPrefSize(width, boardSide);

        Label scores = new Label(board.getScore());
        scores.setFont(new Font("Arial", 20));
        scores.setTextFill(Color.WHITE);
        scores.setLayoutX(boardSide + 20);
        scores.setLayoutY(50);

        Label player = new Label(board.getPlayer());
        player.setFont(new Font("Arial", 20));
        player.setPrefWidth(100);
        player.setTextFill(Color.WHITE);
        player.setWrapText(true);
        player.setLayoutX(boardSide + 20);
        player.setLayoutY(400);

        restart.setOnMouseClicked(e -> {
            ReversiApp.stage.setScene(createMenu());
            ReversiApp.stage.show();
        });
        restart.setPrefSize(70,50);
        restart.setLayoutX(boardSide + 20);
        restart.setLayoutY(600);

        brd.getChildren().addAll(scores, player, restart);

        //adding disks and highlights
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                brd.getChildren().add(board.get(x,y) == 3? new Highlighted(x, y) : new Disk(x, y, board.get(x,y)));
            }
        }

        brd.setOnMouseClicked(e -> {
            Node node = e.getPickResult().getIntersectedNode();
            if (node instanceof Highlighted) {
                if (board.putDisc(((Highlighted) node).x, ((Highlighted) node).y)) { //false if no possible moves for next player found
                    update();
                } else {
                    gameOver();
                }

            }});


        Image img = new Image(ReversiApp.class.getResourceAsStream("/board.png"));
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        brd.setBackground(new Background(backgroundImage));

        return new Scene(brd);
    }

}
