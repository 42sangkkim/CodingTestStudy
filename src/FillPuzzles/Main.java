package FillPuzzles;

public class Main {
    public static void main(String[] args) {
        Solution testSolution = new Solution();
        int[][][] game_boards = {
                {{1,1,0,0,1,0},{0,0,1,0,1,0},{0,1,1,0,0,1},{1,1,0,1,1,1},{1,0,0,0,1,0},{0,1,1,1,0,0}},
                {{0,0,0},{1,1,0},{1,1,1}},
        };
        int[][][] tables = {
                {{1,0,0,1,1,0},{1,0,1,0,1,0},{0,1,1,0,1,1},{0,0,1,0,0,0},{1,1,0,1,1,0},{0,1,0,0,0,0}},
                {{1,1,1},{1,0,0},{0,0,0}}
        };
        int[] ans = {14, 0};

        int myAns;
        for (int i = 0; i < 2; i++) {
            System.out.println("  문 제 " + (i + 1));

            myAns = testSolution.solution(game_boards[i], tables[i]);
            System.out.print("기댓값 : " + ans[i] + ", 결과값 : " + myAns);
        }
    }
}
