package com.example.pawsly.UserBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board,String> {
    List<Board> findAllByWriter(String writer);
    void deleteByBoardKey(String boardKey);
    Board findByBoardKey(String boardKey);

    List<Board> findByWriter(String writer);
}