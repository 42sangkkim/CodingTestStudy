package FillPuzzles;

import java.util.ArrayList;

class Solution {
    public int solution(int[][] game_board, int[][] table) {
        int[][] indexTable = indexParts(table);

        System.out.println("=== Index Table ===");
        for (int[] row : indexTable) {
            for (int col : row) {
                System.out.print(col+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        ArrayList<int[][]> parts = separateParts(indexTable);

        int num = parts.size();
        for (int i=0; i<num; i++) {
            System.out.println("====== [Part "+(i+1)+"] ======");
            for (int[] row : parts.get(i)) {
                for (int col : row) {
                    System.out.print(col+" ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }

        return insertParts(game_board.length, game_board, parts);
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

    private ArrayList<int[][]> separateParts(int[][] indexTable) {
        int partsNum = 0;
        for (int[] row : indexTable) {
            for (int col : row) {
                if (col > partsNum) {
                    partsNum = col;
                }
            }
        }
        partsNum += 1;

        ArrayList<int[][]> parts = new ArrayList<>();

        int len = indexTable.length;
        for (int i=1; i<partsNum; i++) {
            int x1 = 50, x2 = 0, y1 = 50, y2 = 0;
            for (int x=0; x<len; x++) {
                for (int y=0; y<len; y++) {
                    if (indexTable[x][y] == i) {
                        if (x > x2) {
                            x2 = x;
                        }
                        if (x < x1) {
                            x1 = x;
                        }
                        if (y > y2) {
                            y2 = y;
                        }
                        if (y < y1) {
                            y1 = y;
                        }
                    }
                }
            }

            int width = x2 - x1 + 1;
            int height = y2 - y1 + 1;
            int[][] part = new int[width][height];

            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    if (x+x1 < len && y+y1 < len) {
                        part[x][y] = indexTable[x+x1][y+y1] == i ? 1 : 0;
                    }
                }
            }
            parts.add(part);
        }
        return parts;
    }

    private int insertParts(int len, int[][] board, ArrayList<int[][]> parts) {
        int size = parts.size(), result = 0, tmp;
        int[][] tmpBoard;
        boolean nothingMatched = true;
        ArrayList<int[][]> tmpParts;
        for (int i=0; i<size; i++) {
            int[][] part = parts.get(i);
            int width = part.length, height = part[0].length;
            for (int dir=0; dir<4; dir++) {
                for (int x=0; x<len-width; x++) {
                    for (int y=0; y<len-height; y++) {
                        tmpBoard = match(board, part, x, y, i);
                        if (tmpBoard.length == 0) {
                            continue;
                        } else {
                            nothingMatched = false;
                            tmpParts = new ArrayList<>(parts);
                            tmpParts.remove(i);
                            int[][] block = new int[len][len];
                            for (int a=0; a<len; a++) {
                                for (int b=0; b<len; b++) {
                                    block[a][b] = 1;
                                }
                            }
                            tmpParts.add(i, block);

                            tmp = insertParts(len, tmpBoard, tmpParts);
                            if (tmp == 0) {
                                nothingMatched = true;
                                continue;
                            }
                            if (tmp > result) result = tmp;
                        }
                    }
                }
                part = turn90deg(part);
            }
        }
        if (nothingMatched) {
            System.out.println("============================================");
            for (int[] row : board) {
                for (int col : row) {
                    System.out.print(col + " ");
                }
                System.out.print("\n");
            }
            result = countCovers(len, board);
        }
        return result;
    }

    private int[][] match(int[][] board, int[][] part, int x, int y, int index) {
        int width = part.length;
        int height = part[0].length;
        int[][] tmpBoard;
        tmpBoard = board.clone();
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
