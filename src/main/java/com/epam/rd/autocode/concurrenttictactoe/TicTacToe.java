package com.epam.rd.autocode.concurrenttictactoe;

import java.util.Arrays;

public class TicTacToe {
    private final char[][] board = new char[3][3];
    private char lastMark = ' ';
    private final Object lock = new Object();

    public TicTacToe() {
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }


    public void setMark(int x, int y, char mark) {
        synchronized (lock) {
            if (board[x][y] != ' ') {
                throw new IllegalArgumentException("Cell is already occupied");
            }else {
                if (board[x][y] == ' ') {
                    board[x][y] = mark;
                    lastMark = mark;
                }
            }
        }
    }


    public char[][] table() {
        synchronized (lock) {
            char[][] copy = new char[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(board[i], 0, copy[i], 0, 3);
            }
            return copy;
        }
    }


    public char lastMark() {
        synchronized (lock) {
            return lastMark;
        }
    }

    public static TicTacToe buildGame() {
        return new TicTacToe();
    }

    public boolean isGameFinished() {
        // Перевірка виграшу по рядках, стовпцях та діагоналях
        for (int i = 0; i < 3; i++) {
            // Перевірка рядків
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true; // Виграш по рядку
            }
            // Перевірка стовпців
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true; // Виграш по стовпцю
            }
        }
        // Перевірка діагоналей
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true; // Виграш по головній діагоналі
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true; // Виграш по побічній діагоналі
        }

        // Перевірка на нічию
        boolean isDraw = true; // Прапорець для перевірки нічиї
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    isDraw = false; // Знайшли вільну клітинку, гра триває
                    break;
                }
            }
        }

        return isDraw; // Повертаємо true, якщо немає вільних клітинок (нічия)
    }


}
