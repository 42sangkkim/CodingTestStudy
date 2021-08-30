package KakaoBlindRecruitment2018;

public class Friends4Block {

    class Solution {
        public int solution(int m, int n, String[] board) {
            int removeCount = 0;
            int tmpCount = 1;
            int cnt = 0;
            while (tmpCount > 0) {
                tmpCount = 0;
                boolean[][] removeMap = getRemoveMap(m, n, board);
                for (boolean[] row : removeMap) {
                    for (boolean col : row) {
                        if (col)
                            tmpCount += 1;
                    }
                }
                removeCount += tmpCount;
                pang(m, n, board, removeMap);
            }

            return removeCount;
        }

        private boolean[][] getRemoveMap(int m, int n, String[] board) {
            boolean[][] removeMap = new boolean[m][n];

            for (int i=0; i<m; i++) {
                for (int j=0; j<n; j++) {
                    char target = board[i].charAt(j);
                    if (target == ' ')
                        continue;
                    if (i<m-1 && j<n-1) {
                        if (board[i+1].charAt(j+1) == target &&
                                board[i].charAt(j+1) == target &&
                                board[i+1].charAt(j) == target){
                            removeMap[i][j] = true;
                            removeMap[i+1][j] = true;
                            removeMap[i][j+1] = true;
                            removeMap[i+1][j+1] = true;
                        }
                    }
                }
            }
            return removeMap;
        }
        private boolean canRemove(int x, int y, int m, int n, String[] board) {
            char target = board[x].charAt(y);
            if (target == ' ')
                return false;
            if (x>0 && y>0) {
                if (board[x-1].charAt(y)-1 == target &&
                        board[x].charAt(y)-1 == target &&
                        board[x-1].charAt(y) == target)
                    return true;
            }
            return false;
        }

        private void pang(int m, int n, String[] board, boolean[][] removeMap) {
            char[][] cells = new char[m][n];
            for (int i=0; i<m; i++) {
                cells[i] = board[i].toCharArray();
                for (int j=0; j<n; j++) {
                    if (removeMap[i][j])
                        cells[i][j] = ' ';
                }
            }
            for (int i=0; i<n; i++) {
                for (int j=m-2; j>=0; j--) {
                    if (cells[j][i] != ' ') {
                        int destination = -1;
                        for (int k=j+1; k<m; k++) {
                            if (cells[k][i] == ' ')
                                destination = k;
                        }
                        if (destination != -1) {
                            cells[destination][i] = cells[j][i];
                            cells[j][i] = ' ';
                        }
                    }
                }
            }
            for (int i=0; i<m; i++) {
                board[i] = new String(cells[i]);
            }
        }
    }
}
