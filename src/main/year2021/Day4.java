package main.year2021;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day4 extends Day {

    public static void main(String[] args) throws IOException {
        Day4 day = new Day4();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<String[][]> bingoBoards = new ArrayList<>();
        String[] numbersToDraw = convertInput(list, bingoBoards);

        System.out.println(playBingo(bingoBoards, numbersToDraw, true));
    }

    public void solve2(List<String> list) {
        List<String[][]> bingoBoards = new ArrayList<>();
        String[] numbersToDraw = convertInput(list, bingoBoards);

        System.out.println(playBingo(bingoBoards, numbersToDraw, false));
    }

    private int playBingo(List<String[][]> bingoBoards, String[] numbersToDraw, boolean checkFirstBoard) {
        int winBoard = 0;
        String endNumber = numbersToDraw[0];
        for (String s : numbersToDraw) {
            for (String[][] board : bingoBoards) {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j].equals(s)) board[i][j] = "-" + board[i][j];
                    }
                }
            }
            winBoard = checkWinConditions(bingoBoards, checkFirstBoard);
            if (checkFirstBoard) {
                if (winBoard >= 0) {
                    endNumber = s;
                    break;
                }
            } else if (winBoard == 0) {
                endNumber = s;
                break;
            }
        }
        System.out.println(winBoard);
        System.out.println(endNumber);
        return winBoard >= 0 ? calculateScore(bingoBoards.get(winBoard), endNumber) : 0;
    }

    private int checkWinConditions(List<String[][]> bingoBoards, boolean checkFirstBoard) {
        int size = bingoBoards.size();
        int k = 0;
        while (k < size) {
            String[][] board = bingoBoards.get(k);
            boolean boardWin = false;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j].charAt(0) == '-' && j == board.length-1 ) {
                        if (checkFirstBoard) {
                            return k;
                        } else boardWin = true;
                    }
                    if (board[i][j].charAt(0) != '-') break;
                }
            }
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[j][i].charAt(0) == '-' && j == board.length-1 ) {
                        if (checkFirstBoard) {
                            return k;
                        } else boardWin = true;
                    }
                    if (board[j][i].charAt(0) != '-') break;
                }
            }
            if (boardWin) {
                if (bingoBoards.size() == 1) return 0;
                bingoBoards.remove(k);
                --size;
            } else ++k;
        }
        return -1;
    }

    private int calculateScore(String[][] board, String endNumber) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) != '-') sum += Integer.parseInt(board[i][j]);
            }
        }
        return sum*Integer.parseInt(endNumber);
    }

    private String[] convertInput(List<String> list, List<String[][]> bingoBoards) {
        String[] numbersToDraw = list.get(0).split(",");
        List<String[]> list1 = new ArrayList<>();
        for (int i = 2; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                bingoBoards.add(list1.toArray(new String[0][]));
                list1.clear();
            } else list1.add(list.get(i).trim().split(" +"));
        }
        bingoBoards.add(list1.toArray(new String[0][]));
        return numbersToDraw;
    }
}
