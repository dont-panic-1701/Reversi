import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Disk extends Circle {

    Disk(int x, int y, int player){

        int TILE_SIZE = 100;
        setRadius(TILE_SIZE *0.45);
        Color[] colors = new Color[]{Color.valueOf("#1e721c"), Color.BLACK, Color.WHITE};
        //colors[0] is a bg color, to make disks invisible.

        setFill(colors[player]);

        relocate(x * TILE_SIZE + 5, y * TILE_SIZE + 5);
    }
}
