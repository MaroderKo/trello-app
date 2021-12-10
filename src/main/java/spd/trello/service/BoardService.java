package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardService extends AbstractService<Board>{
    static List<Board> storage = new ArrayList();
    @Override
    public Board create() {
        Board board = new Board();
        Scanner sc = new Scanner(System.in);
        System.out.print("Input name: ");
        String name = sc.nextLine();
        board.setName(name);
        storage.add(board);
        return board;
    }

    @Override
    public void print(Board board) {
        System.out.println(board);
    }

    @Override
    public void update(int index, Board board) {
        Board board1 = storage.get(index);
        board1.setName(board.getName());
        board1.setUpdatedDate(LocalDateTime.now());
    }
}
