package spd.trello.service;

import spd.trello.domain.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BoardService extends AbstractService<Board>{
    static List<Board> storage = new ArrayList<>();
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
    public void update(UUID index, Board board) {
        throw new UnsupportedOperationException();
    }
}
