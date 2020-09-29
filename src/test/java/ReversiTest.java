import Model.Board;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ReversiTest {

    @Test
    public void putDisk(){
        Board board = new Board();
        int[][] testB = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                testB[i][j]=1;
            }
        }
        testB[0][0] = 3;
        board.setBoard(testB);
        board.setPlayer(2); //white

        assertFalse(board.putDisc(0, 0)); //also a findNextMoves test
        assertEquals("Now playing as: black",board.getPlayer()); //player change test
        assertEquals("Score 630 : 10", board.getScore()); //scores test
    }
    //тест для переворота и подсчёта всех возм направлений

    @Test
    public void checkMove(){
        Board board = new Board();
        //all directions from [3][3] tile
        int[][] testB = new int[8][8];
        testB[0][0] = 1;
        testB[0][3] = 1;
        testB[0][6] = 1;
        testB[3][0] = 1;
        testB[3][7] = 1;
        testB[6][0] = 1;
        testB[7][3] = 1;
        testB[7][7] = 1;

        testB[1][1] = 2;
        testB[1][3] = 2;
        testB[1][5] = 2;
        testB[2][2] = 2;
        testB[2][3] = 2;
        testB[2][4] = 2;
        testB[3][1] = 2;
        testB[3][2] = 2;
        testB[3][4] = 2;
        testB[3][5] = 2;
        testB[3][6] = 2;
        testB[4][2] = 2;
        testB[4][3] = 2;
        testB[4][4] = 2;
        testB[5][1] = 2;
        testB[5][3] = 2;
        testB[5][5] = 2;
        testB[6][3] = 2;
        testB[6][6] = 2;

        board.setBoard(testB);
        board.setPlayer(1);


        assertEquals(board.get(3,3), 0);
        board.checkMove(1,3,3,false);
        assertArrayEquals(new int[]{270,0}, board.countScore()); //scores test
        //tests for flipped disks
        assertEquals(board.get(1,1), 1);
        assertEquals(board.get(1,3), 1);
        assertEquals(board.get(1,5), 1);
        assertEquals(board.get(2,2), 1);
        assertEquals(board.get(2,3), 1);
        assertEquals(board.get(2,4), 1);
        assertEquals(board.get(3,1), 1);
        assertEquals(board.get(3,2), 1);
        assertEquals(board.get(3,4), 1);
        assertEquals(board.get(3,5), 1);
        assertEquals(board.get(3,6), 1);
        assertEquals(board.get(4,2), 1);
        assertEquals(board.get(4,3), 1);
        assertEquals(board.get(4,4), 1);
        assertEquals(board.get(5,1), 1);
        assertEquals(board.get(5,3), 1);
        assertEquals(board.get(5,5), 1);
        assertEquals(board.get(6,3), 1);
        assertEquals(board.get(6,6), 1);

    }
}
