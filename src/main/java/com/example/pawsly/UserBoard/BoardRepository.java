package com.example.pawsly.UserBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByBoardKey(Long boardKey);
    void deleteByBoardKey(Long boardKey);
}