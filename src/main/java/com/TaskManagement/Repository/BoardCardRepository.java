package com.TaskManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.TaskManagement.Entity.BoardCard;




@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard,Long> {
	
	List<BoardCard>findByBoardIdAndColumnIdOrderPosition(Long boardId,Long columnId);
	Optional<BoardCard>findByIssueId(Long issueId);
	long countByBoardIdColumnId(Long boardId,Long columnId);


}
