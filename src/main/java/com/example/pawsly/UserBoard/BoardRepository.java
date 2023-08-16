package com.example.pawsly.UserBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByWriter(String writer);
    void deleteByBoardKey(Long boardKey);
}