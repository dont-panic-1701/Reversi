import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Highlighted extends Rectangle {
    final int x, y;
    private final static double TILE_SIZE = 100.0;


    Highlighted(int x, int y) {
        this.x = x;
        this.y = y;

        relocate( x * TILE_SIZE, y*TILE_SIZE);
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        setFill(Color.valueOf("1E721C")); //bg color
        setStroke(Color.valueOf("F0F52C"));
        setStrokeWidth(8);
        setOnMouseEntered(e -> setFill(Color.valueOf("86DF1A")));
        setOnMouseExited(e -> setFill(Color.valueOf("1E721C"))); //bg color
    }
}
