
import java.lang.*;
import java.util.*;

class State {
    char[] board;
    public static int WIDTH = 4;

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
    }

    public int getScore() {
        // return game theoretic value of the board
        int dark = 0, light = 0;
        for (int i = 0; i < 16; i++) {
            if (board[i] == '1') dark++;
            else if (board[i] == '2') light++;
        }
        if (dark > light) return 1;
        else if (dark < light) return -1;
        else return 0;
    }
    
    public boolean isTerminal() {
    	
        // determine if the board is a terminal node or not and return boolean
        // when neither player can move on the board
        for(int i = 0; i < 16; i++) {
            if (board[i] != '0') continue;
            if (canPlace(i, '1') || canPlace(i, '2'))
                return false;
        }
        return true;
    }

    public boolean canPlace(int pos, char player) {
        if (pos < 0 || pos >= 16 || board[pos] != '0')
            return false;
        
        int y = pos / WIDTH;
        int x = pos % WIDTH;
        int sum = '1' + '2';
        char oppose = (char)(sum - player);
        if (x < WIDTH - 2 && board[pos + 1] == oppose && (board[WIDTH * y + 2] == player || board[WIDTH * y + 3] == player && board[WIDTH * y + 2] == oppose))
            return true;
        if (x > 1 && board[pos - 1] == oppose && (board[WIDTH * y + 1] == player || board[WIDTH * y] == player && board[WIDTH * y + 1] == oppose))
            return true;
        if (y < WIDTH - 2 && board[pos + WIDTH] == oppose && (board[x + 2 * WIDTH] == player || board[x + 3 * WIDTH] == player && board[x + 2 * WIDTH] == oppose))
            return true;
        if (y > 1 && board[pos - WIDTH] == oppose && (board[x + WIDTH] == player || board[x] == player && board[x + WIDTH] == oppose))
            return true;
        if (x + 1 == y && board[9] == oppose && (board[4] == player || board[14] == player))
            return true;
        if (x == y + 1 && board[6] == oppose && (board[1] == player || board[11] == player))
            return true;
        if (x == y && x < WIDTH - 2 && board[(y+1) * WIDTH + (x+1)] == oppose && (board[2 * WIDTH + 2] == player || board[3 * WIDTH + 3] == player && board[2 * WIDTH + 2] == oppose))
            return true;
        if (x == y && x > 1 && board[(y-1) * WIDTH + (x-1)] == oppose && (board[WIDTH + 1] == player || board[0] == player && board[WIDTH + 1] == oppose))
            return true;
        if (x + y == 2 && board[5] == oppose && (board[8] == player || board[2] == player))
            return true;
        if (x + y == 4 && board[10] == oppose && (board[13] == player || board[7] == player))
            return true;
        if (x + y == 3 && x < 2 && board[(y-1) * WIDTH + (x+1)] == oppose && (board[6] == player || board[3] == player && board[6] == oppose))
            return true;
        if (x + y == 3 && x >= 2 && board[(y+1) * WIDTH + (x-1)] == oppose && (board[9] == player || board[12] == player && board[9] == oppose))
            return true;
        return false;
    }

    public State[] getSuccessors(char player) {
        // get all successors and return them in proper order
        int sum = '1' + '2';
        char oppose = (char)(sum - player);
        ArrayList<State> successorList = new ArrayList<>();
        for (int pos = 0; pos < 16; pos++) {
            if (!canPlace(pos, player)) continue;
            char[] successBoard = Arrays.copyOf(board, board.length);
            successBoard[pos] = player;
            int y = pos / WIDTH;
            int x = pos % WIDTH;
            if (x < WIDTH - 2 && board[pos + 1] == oppose && (board[WIDTH * y + 2] == player || board[WIDTH * y + 3] == player && board[WIDTH * y + 2] == oppose)){
                successBoard[pos + 1] = player;
                if (successBoard[WIDTH * y + 2] != player) successBoard[WIDTH * y + 2] = player;
            }
            if (x > 1 && board[pos - 1] == oppose && (board[WIDTH * y + 1] == player || board[WIDTH * y] == player && board[WIDTH * y + 1] == oppose)) {
                successBoard[pos - 1] = player;
                if (successBoard[WIDTH * y + 1] != player) successBoard[WIDTH * y + 1] = player;
            }
            if (y < WIDTH - 2 && board[pos + WIDTH] == oppose && (board[x + 2 * WIDTH] == player || board[x + 3 * WIDTH] == player && board[x + 2 * WIDTH] == oppose)) {
                successBoard[pos + WIDTH] = player;
                if (successBoard[x + 2 * WIDTH] != player) successBoard[x + 2 * WIDTH] = player;
            }
            if (y > 1 && board[pos - WIDTH] == oppose && (board[x + WIDTH] == player || board[x] == player && board[x + WIDTH] == oppose)) {
                successBoard[pos - WIDTH] = player;
                if (successBoard[x + WIDTH] != player) successBoard[x + WIDTH] = player;
            }
            if (x + 1 == y && board[9] == oppose && (board[4] == player || board[14] == player)) {
                successBoard[9] = player;
            }
            if (x == y + 1 && board[6] == oppose && (board[1] == player || board[11] == player)) {
                successBoard[6] = player;
            }
            if (x == y && x < WIDTH - 2 && board[(y+1) * WIDTH + (x+1)] == oppose && (board[2 * WIDTH + 2] == player || board[3 * WIDTH + 3] == player && board[2 * WIDTH + 2] == oppose)) {
                successBoard[(y+1) * WIDTH + (x+1)] = player;
                if (successBoard[2 * WIDTH + 2] != player) successBoard[2 * WIDTH + 2] = player;
            }
            if (x == y && x > 1 && board[(y-1) * WIDTH + (x-1)] == oppose && (board[WIDTH + 1] == player || board[0] == player && board[WIDTH + 1] == oppose)) {
                successBoard[(y-1) * WIDTH + (x-1)] = player;
                if (successBoard[WIDTH + 1] != player) successBoard[WIDTH + 1] = player;
            }
            if (x + y == 2 && board[5] == oppose && (board[8] == player || board[2] == player)) {
                successBoard[5] = player;
            }
            if (x + y == 4 && board[10] == oppose && (board[13] == player || board[7] == player)) {
                successBoard[10] = player;
            }
            if (x + y == 3 && x < 2 && board[(y-1) * WIDTH + (x+1)] == oppose && (board[6] == player || board[3] == player && board[6] == oppose)) {
                successBoard[(y-1) * WIDTH + (x+1)] = player;
                if (successBoard[6] != player) successBoard[6] = player;
            }
            if (x + y == 3 && x >= 2 && board[(y+1) * WIDTH + (x-1)] == oppose && (board[9] == player || board[12] == player && board[9] == oppose)) {
                successBoard[(y+1) * WIDTH + (x-1)] = player;
                if (successBoard[9] != player) successBoard[9] = player;
            }
            successorList.add(new State(successBoard));
        }
        return successorList.toArray(new State[0]);
    }
 
    public void printState(int option, char player) {

        // print a State based on option (flag)
        int sum = '1' + '2';
        char oppose = (char)(sum - player);
        switch (option){
            case 1:
                State[] successors = getSuccessors(player);
                if (successors.length == 0) {
                    successors = getSuccessors(oppose);
                }
                for (int i = 0; i < successors.length; i++) {
                    System.out.println(successors[i].getBoard());
                }
                break;
            case 2:
                if (isTerminal()) {
                    System.out.println(getScore());
                } else {
                    System.out.println("non-terminal");
                }
                break;
            case 3:
                System.out.println(Minimax.run(this, player));
                System.out.println(Minimax.state_num);
                break;
            case 4:
                if (!isTerminal()) {
                    int bestScore = Minimax.run(this, player);
                    successors = getSuccessors(player);
                    if (successors.length == 0) {
                        System.out.println(getBoard());
                        break;
                    }
                    for (int i = 0; i < successors.length; i++) {
                        if (bestScore == Minimax.run(successors[i], (char)(sum - player))) {
                            System.out.println(successors[i].getBoard());
                            break;
                        }
                    }
                }
                break;
            case 5:
                System.out.println(Minimax.run_with_pruning(this, player));
                System.out.println(Minimax.state_num);
                break;
            case 6:
                if (!isTerminal()) {
                    int bestScore = Minimax.run_with_pruning(this, player);
                    successors = getSuccessors(player);
                    if (successors.length == 0) {
                        System.out.println(getBoard());
                        break;
                    }
                    for (int i = 0; i < successors.length; i++) {
                        if (bestScore == Minimax.run_with_pruning(successors[i], (char)(sum - player))) {
                            System.out.println(successors[i].getBoard());
                            break;
                        }
                    }
                }
        }

    }

    public String getBoard() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(this.board[i]);
        }
        return builder.toString().trim();
    }

    public boolean equals(State src) {
        for (int i = 0; i < 16; i++) {
            if (this.board[i] != src.board[i])
                return false;
        }
        return true;
    }
}

class Minimax {
    // implement Max-Value of the Minimax algorithm
    public static int state_num = 0;
	private static int max_value(State curr_state) {
        state_num++;
        if (curr_state.isTerminal()) {
            return curr_state.getScore();
        }
		State[] successors = curr_state.getSuccessors('1');
        if (successors.length == 0) return min_value(curr_state);
        int bestValue = -1;
        for (int i = 0; i < successors.length; i++) {
            bestValue = Math.max(min_value(successors[i]), bestValue);
        }
        return bestValue;

	}
	
	private static int min_value(State curr_state) {
        state_num++;
        if (curr_state.isTerminal()) {
            return curr_state.getScore();
        }
        State[] successors = curr_state.getSuccessors('2');
        if (successors.length == 0) return max_value(curr_state);
        int minValue = 1;
        for (int i = 0; i < successors.length; i++) {
            minValue = Math.min(max_value(successors[i]), minValue);
        }
        return minValue;
	}
	
	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        // implement Max-Value of the alpha-beta pruning algorithm
        state_num++;
        if (curr_state.isTerminal()) {
            return curr_state.getScore();
        }
        State[] successors = curr_state.getSuccessors('1');
        if (successors.length == 0) return min_value_with_pruning(curr_state, alpha, beta);
        for (int i = 0; i < successors.length; i++) {
            alpha = Math.max(alpha, min_value_with_pruning(successors[i], alpha, beta));
            if (alpha >= beta) return beta;
        }
        return alpha;
	}
	
	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        // implement Min-Value of the alpha-beta pruning algorithm
        state_num++;
        if (curr_state.isTerminal()) {
            return curr_state.getScore();
        }
        State[] successors = curr_state.getSuccessors('2');
        if (successors.length == 0) return max_value_with_pruning(curr_state, alpha, beta);
        for (int i = 0; i < successors.length; i++) {
            beta = Math.min(beta, max_value_with_pruning(successors[i], alpha, beta));
            if (alpha >= beta) return alpha;
        }
        return beta;
	}
	
	public static int run(State curr_state, char player) {

        // run the Minimax algorithm and return the game theoretic value
        if (player == '1') {
            return max_value(curr_state);
        } else {
            return min_value(curr_state);
        }
	}
	
	public static int run_with_pruning(State curr_state, char player) {
	    
        // run the alpha-beta pruning algorithm and return the game theoretic value
        if (player == '1') {
            return max_value_with_pruning(curr_state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            return min_value_with_pruning(curr_state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
	}
}

public class Reversi {
    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }
        int flag = Integer.valueOf(args[0]);
        char[] board = new char[16];
        for (int i = 0; i < 16; i++) {
            board[i] = args[2].charAt(i);
        }
        int option = flag / 100;
        char player = args[1].charAt(0);
        if ((player != '1' && player != '2') || args[1].length() != 1) {
            System.out.println("Invalid Player Input");
            return;
        }
        State init = new State(board);
        init.printState(option, player);
    }
}
