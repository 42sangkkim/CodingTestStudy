package FillPuzzles;

import java.util.HashMap;

class Solution {
    public int solution(int[][] game_board, int[][] table) {
        int[][] indexTable = indexParts(table);

//        System.out.println("=== Index Table ===");
//        for (int[] row : indexTable) {
//            for (int col : row) {
//                System.out.print(col+" ");
//            }
//            System.out.print("\n");
//        }
//        System.out.print("\n");

        HashMap<Integer, int[][]> parts = separateParts(indexTable);

        int num = parts.size();
//        for (int i=0; i<num; i++) {
//            System.out.println("====== [Part "+(i+1)+"] ======");
//            for (int[] row : parts.get(i)) {
//                for (int col : row) {
//                    System.out.print(col+" ");
//                }
//                System.out.print("\n");
//            }
//            System.out.print("\n");
//        }
//
//        System.out.println("=== Game Board ===");
//        for (int[] row : game_board) {
//            for (int col : row) {
//                System.out.print(col+" ");
//            }
//            System.out.print("\n");
//        }
//        System.out.print("\n");

        return insertParts(game_board.length, game_board, num, parts);
    }

    private int[][] indexParts(int[][] table) {
        int len = table.length, lastIndex = 0;
        int[][] indexTable = new int[len][len];

        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                if (table[i][j] == 1) {
                    if (i>0 && table[i-1][j] == 1) {
                        indexTable[i][j] = indexTable[i-1][j];
                        if (j>0 && table[i][j-1] == 1) {
                            int from = indexTable[i][j-1], to = indexTable[i-1][j];
                            for (int[] row : table) {
                                for (int col : row) {
                                    if (col == from)
                                        col = to;
                                }
                            }
                            simplify(indexTable, from);
                            lastIndex--;
                        }
                    } else if (j>0 && table[i][j-1] == 1){
                        indexTable[i][j] = indexTable[i][j-1];
                    } else {
                        indexTable[i][j] = ++lastIndex;
                    }
                }
            }
        }
        return indexTable;
    }

    private void simplify(int[][] table, int n) {
        int len = table.length;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                if (table[i][j] >= n) {
                    table[i][j] -= 1;
                }
            }
        }
    }

    private HashMap<Integer, int[][]> separateParts(int[][] indexTable) {
        int partsNum = 0;
        for (int[] row : indexTable) {
            for (int col : row) {
                if (col > partsNum) {
                    partsNum = col;
                }
            }
        }
        partsNum += 1;


        HashMap<Integer, int[][]> parts = new HashMap<>();

        int len = indexTable.length;
        for (int i=1; i<partsNum; i++) {
            int[] leftTop = {50, 50}, rightBottom = {0, 0};
            for (int x=0; x<len; x++) {
                for (int y=0; y<len; y++) {
                    if (indexTable[x][y] == i) {
                        leftTop[0] = Math.min(leftTop[0], x);
                        leftTop[1] = Math.min(leftTop[1], y);
                        rightBottom[0] = Math.max(rightBottom[0], x);
                        rightBottom[1] = Math.max(rightBottom[1], y);
                    }
                }
            }

            int width = rightBottom[0] - leftTop[0] + 1;
            int height = rightBottom[1] - leftTop[1] + 1;
            int[][] part = new int[width][height];

            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    if (x+leftTop[0] < len && y+leftTop[1] < len) {
                        part[x][y] = indexTable[x+leftTop[0]][y+leftTop[1]] == i ? 1 : 0;
                    }
                }
            }
            parts.put(i-1, part);
        }
        return parts;
    }

    private int insertParts(int len, int[][] board, int size, HashMap<Integer, int[][]> parts) {
        int result = 0, width, height;

        for (int i=0; i<size; i++) {
            if (!parts.containsKey(i))
                continue;
            int[][] part = parts.get(i);
            for (int dir=0; dir<4; dir++) {
                width = part.length;
                height = part[0].length;
                for (int x=0; x<len-width+1; x++) {
                    for (int y=0; y<len-height+1; y++) {
                        int[][] tmpBoard = match(board, part, x, y, i);
                        if (tmpBoard.length == 0) {
                            continue;
                        } else {
                            HashMap<Integer, int[][]> tmpParts = new HashMap<>(parts);
                            tmpParts.remove(i);
                            int tmp = insertParts(len, tmpBoard, size, tmpParts);
                            if (tmp > result) result = tmp;
                        }
                    }
                }
                part = turn90deg(part);
            }
        }
        if (result == 0) {
            result = countCovers(len, board);
        }
        return result;
    }

    private int[][] match(int[][] board, int[][] part, int x, int y, int index) {
        int width = part.length, height = part[0].length, len = board.length;
        int[][] tmpBoard = new int[len][len];
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                tmpBoard[i][j] = board[i][j];
            }
        }
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (part[i][j] == 1) {
                    if (tmpBoard[i+x][j+y] > 0) {
                        return new int[0][0];
                    } else {
                        tmpBoard[i+x][j+y] = index + 2;
                    }
                }
            }
        }
        return tmpBoard;
    }

    private int[][] turn90deg(int[][] part) {
        int width = part.length;
        int height = part[0].length;
        int[][] tmp = new int[height][width];
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                tmp[i][j] = part[width-j-1][i];
            }
        }
        return tmp;
    }

    private int countCovers(int len, int[][] board) {
        int covered = 0;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                if (board[i][j] > 2) {
                    if (i>0 && board[i-1][j] == 0) {
                        return 0;
                    } else if (i<len-1 && board[i+1][j] == 0) {
                        return 0;
                    } else if (j>0 && board[i][j-1] == 0) {
                        return 0;
                    } else if (j<len-1 && board[i][j+1] == 0) {
                        return 0;
                    }
                    covered += 1;
                }
            }
        }
        return covered;
    }
}
