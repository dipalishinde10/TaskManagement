package com.TaskManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.TaskManagement.Entity.BoardCard;




@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard,Long> {
	
	long countByBoardIdAndColumn_Id(Long boardId, Long columnId);

    List<BoardCard> findByBoardIdAndColumn_IdOrderByPosition(Long boardId, Long columnId);

    Optional<BoardCard> findByIssueId(Long issueId);


}
