package com.epam.rd.autocode.concurrenttictactoe;


public class Player implements Runnable {
    private final TicTacToe ticTacToe;
    private final char mark;
    private final PlayerStrategy strategy;

    private Player(TicTacToe ticTacToe, char mark, PlayerStrategy strategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.strategy = strategy;
    }

    public static Player createPlayer(TicTacToe ticTacToe, char mark, PlayerStrategy strategy) {
        return new Player(ticTacToe, mark, strategy);
    }

//    @Override
//    public void run() {
//        while (true) {
//            synchronized (ticTacToe) {
//                // Пропустити хід, якщо це не черга гравця
//                if (ticTacToe.lastMark() == mark) {
//                    try {
//                        ticTacToe.wait();
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        return; // Завершення потоку при перериванні
//                    }
//                } else {
//                    try {
//                        Move move = strategy.computeMove(mark, ticTacToe);
//                        if (move == null) {
//                            ticTacToe.notifyAll();
//                            return; // Завершення гри, якщо немає доступних ходів
//                        }
//                        ticTacToe.setMark(move.row, move.column, mark);
//                        ticTacToe.notifyAll();
//
//                        // Перевірка на стан гри після ходу
//                        if (ticTacToe.isGameFinished()) {
//                            return; // Завершення гри, якщо вона закінчена
//                        }
//                    } catch (IllegalArgumentException e) {
//                        // Обробка ситуації, якщо немає доступних ходів
//                        ticTacToe.notifyAll();
//                        return; // Завершення гри через відсутність доступних ходів
//                    }
//                }
//            }
//        }
//    }
@Override
public void run() {
    while (true) {
        synchronized (ticTacToe) {
            // Пропустити хід, якщо це не черга гравця
            if (ticTacToe.lastMark() == mark) {
                try {
                    ticTacToe.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Завершення потоку при перериванні
                }
            } else {
                try {
                    Move move = strategy.computeMove(mark, ticTacToe);
                    if (move == null) {
                        ticTacToe.notifyAll();
                        return; // Завершення гри, якщо немає доступних ходів
                    }

                    // Додати перевірку перед встановленням ходу
                    if (!ticTacToe.isGameFinished()) {
                        ticTacToe.setMark(move.row, move.column, mark);
                    }

                    // Перевірка на стан гри після ходу
                    if (ticTacToe.isGameFinished()) {
                        ticTacToe.notifyAll();
                        return; // Завершення гри, якщо вона закінчена
                    }

                    ticTacToe.notifyAll();
                } catch (IllegalArgumentException e) {
                    // Обробка ситуації, якщо немає доступних ходів
                    ticTacToe.notifyAll();
                    return; // Завершення гри через відсутність доступних ходів
                }
            }
        }
    }
}


}
