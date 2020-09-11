//it's not actually "board" class, more of "logic"
class Board {
    private int scoreP1;
    private int scoreP2;
    private int[][] board = new int[8][8];
    private int player;

    // creating or resetting a board
    void newBoard(){
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

        scoreP1 = 20;
        scoreP2 = 20;

        this.player = 1;
    }

    int get(int i, int j){ return board[i][j]; }


    //string for score label
    String getScore() { return "Score " + scoreP1 + " : " + scoreP2; }

    //string for player label
    String getPlayer() { return "Now playing as: " + (player == 1?"black":"white"); }

    //string for winner label
    String getWinner() {
        if (scoreP1 == scoreP2) return "You all lost";
        return "Player " + (scoreP1>scoreP2 ? "1" : "2") + " won!";
    }


    //when disk has been placed:
    boolean putDisc(int x, int y) {
        cleanNextMoves();

        //count score AND flip the enemy disks. not very OOP.
        int scoreDiff = checkMove(player, x, y, false); //onlyCheck == false when we need to turn disks
        //put the new disk itself
        board[x][y] = player;


        //update score
        if(player == 1){
            scoreP1 += (scoreDiff + 1)  * 10; // +1 because new disk also counts
            scoreP2 -= scoreDiff * 10;
        }
        else{
            scoreP2 += (scoreDiff + 1) * 10;
            scoreP1 -= scoreDiff * 10;
        }

        player = player == 1 ? 2 : 1; // swithcing the player after the move

        return findNextMoves(player); //false means gameover
    }

    //checking every tile with checkMove(); if no moves left - return false
    private boolean findNextMoves(int player){
        boolean thereAreMoves = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //when onlycheck == true the outputs are: 1 for possible move found and 0 for not found
                if(checkMove(player, i, j, true) == 1){
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

    //output when check: 0 - move not possible, 1 - possible; otherwise returns score difference
    //i check, in 1 direction at a time, for proper move conditions: row of enemy disks AND a player disk at the end
    private int checkMove(int player, int i, int j, boolean onlyCheck) {
        //need tile to be free for move
        if (board[i][j] != 0) return 0;

        // movingX and movingY; c is count
        int mX, mY, c, sum = 0;
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
                        if (onlyCheck) return 1;

                        //now turn the disks in ONE direction while moving back
                        for(int it = c; it > 0; it--) {
                            mX -= dirx;
                            mY -= diry;
                            this.board[mX][mY] = player; }

                        //add score for ONE direction to the sum
                        sum += c;
                        break; //no need to move the same dir anymore

                    } else if (board[mX][mY] == enemy){ //just keep moving
                        mX += dirx;
                        mY += diry;
                        c++;
                    } else break; //nothing here
                }
            }
        }
        //if we were just checking, then we found nothing, return 0; otherwise return scores
        return onlyCheck ? 0: sum;
    }
}