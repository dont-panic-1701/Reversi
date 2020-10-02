package Model;

public class Board {
    private int[] scores = new int[2];
    private int[][] board = new int[8][8];
    private int player;

    //for tests; not used in game
    public void setBoard(int[][] newb){
        board = newb;
    }
    public void setPlayer(int newPlayer){
        this.player = newPlayer;
    }

    // creating or resetting a board
    public void newBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=0;
            }
        }
        //initial board state
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;

        board[4][2] = 3;
        board[2][4] = 3;
        board[3][5] = 3;
        board[5][3] = 3;

        scores = new int[]{20,20};

        this.player = 1;
    }

    public int get(int i, int j){ return board[i][j]; }
    public int getPlayer(){ return player; }

    //string for score label
    public String getScore() { return "Score " + scores[0] + " : " + scores[1]; }

    //string for player label
    public String getPlayers() { return "Now playing as: " + (player == 1?"black":"white"); }

    //string for winner label
    public String getWinner() {
        if (scores[0] == scores[1]) return "You all lost";
        return "Player " + (scores[0] > scores[1] ? "1" : "2") + " won!";
    }


    //when disk has been placed:
    public boolean putDisc(int x, int y) {
        cleanNextMoves();

        //flip the enemy disks
        checkMove(player, x, y, false); //onlyCheck == false when we need to turn disks

        //put the new disk itself
        board[x][y] = player;

        //update score
        scores = countScore();

        if (!findNextMoves(player == 1 ? 2 : 1)) return false; //false means gameover

        // switching the player after the move
        player = player == 1 ? 2 : 1;

        return true;
    }

    //checking every tile with checkMove(); if no moves left - return false
    public boolean findNextMoves(int player){
        boolean thereAreMoves = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(checkMove(player, i, j, true)){
                    board[i][j] = 3;
                    thereAreMoves = true;
                }
            }
        }
        return thereAreMoves;
    }

    private void cleanNextMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 3) board[i][j] = 0;
            }
        }
    }

    //output when check: false when no move possible
    //i check, in 1 direction at a time, for proper move conditions: row of enemy disks AND a player disk at the end
    public boolean checkMove(int player, int i, int j, boolean onlyCheck) {
        //need tile to be free for move
        if (board[i][j] != 0) return false;

        // movingX and movingY; c is count
        int mX, mY, c;
        int enemy = (player == 1) ? 2 : 1;

        // cycle for all 8 possible directions (complicated but better than 85 lines of repeats?)
        for (int dirx = -1; dirx <= 1; dirx++) {
            for (int diry = -1; diry <= 1; diry++) {

                if (dirx == 0 && diry == 0) continue; // skip the zero directions

                //starting with 1 step
                mX = i + dirx;
                mY = j + diry;
                c = 0;

                while (mX >= 0 && mY >= 0 && mX <= 7 && mY <= 7) { //bounds check


                    if (board[mX][mY] == player && c > 0){
                        //valid move found!
                        if (onlyCheck) return true;

                        //now turn the disks in ONE direction while moving back
                        for(int it = c; it > 0; it--) {
                            mX -= dirx;
                            mY -= diry;
                            this.board[mX][mY] = player; }

                        break; //no need to move the same dir anymore

                    } else if (board[mX][mY] == enemy){ //just keep moving
                        mX += dirx;
                        mY += diry;
                        c++;
                    } else break; //nothing here
                }
            }
        }
        //we found nothing
        return false;
    }

    public int[] countScore(){
        int[] scores = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j]==1) scores[0] += 10;
                if (board[i][j]==2) scores[1] += 10;
            }
        }
        return scores;
    }

    public boolean boardIsFull(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) return false; //if there are empty spaces - board not full
            }
        }
        return true;
    }

}