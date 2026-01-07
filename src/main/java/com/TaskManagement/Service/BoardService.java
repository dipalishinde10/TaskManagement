package com.TaskManagement.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement.Entity.Board;
import com.TaskManagement.Entity.BoardCard;
import com.TaskManagement.Entity.BoardColumn;
import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Repository.BoardCardRepository;
import com.TaskManagement.Repository.BoardColumnRepository;
import com.TaskManagement.Repository.BoardRepository;
import com.TaskManagement.Repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private BoardColumnRepository boardColumnRepo;
	
	@Autowired
	private BoardCardRepository boardCardRepo;
	
	@Autowired
	private IssueRepository issueRepo;
	
	public Board createBoard(Board bd) {
		return boardRepo.save(bd);
	}
	public Optional<Board>findById(Long id){
		return boardRepo.findById(id);
	}
	public List<BoardColumn>getByColumns(Long id){
		return boardColumnRepo.findByOrderedByPosition(id);
				
		
	}
	public List<BoardCard>getCardsForColumn(Long boardId,Long columnId){
		return boardCardRepo.findByBoardIdAndColumnIdOrderPosition(boardId, columnId);
		
	}
	@Transactional
	public BoardCard addIssueToBoard(Long boardId,Long columnId,Long issueId) {
		Issue issue= issueRepo.finById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));
		
		boardCardRepo.findByIssueId(issueId).ifPresent(boardCardRepo::delete);
		BoardColumn column=boardColumnRepo.findById(columnId).orElseThrow(()->new RuntimeException("Column not found"));
		
		
		if(column.getWipeLimit()!=null && column.getWipeLimit()>0) {
			long count= boardCardRepo.countByBoardIdColumnId(boardId, columnId);
			if(count >= column.getWipeLimit()) {
				throw new RuntimeException("Wipe limit reached for column:"+column.getBoardName());
			}
		}
		List<BoardCard> existing=boardCardRepo.findByBoardIdAndColumnIdOrderPosition(boardId, columnId);
		int pos=existing.size();
		BoardCard card = new BoardCard();
		card.setBoardId(boardId);
		card.setColumn(column);
		card.setIssueId(issueId);
		card.setPosition(null);
		
		card = boardCardRepo.save(card);
		
		
		if(column.getStatusKey()!=null) {
			issue.setIssueStatus(Enum.valueOf(com.TaskManagement.Enum.IssueStatus.class,column.getStatusKey()));
			
			issueRepo.save(issue);
			
		}
		
		
		return card;
	}
	
	
	@Transactional
	public void moveCard(Long boardId,Long cardId,Long columnId,int toPosition,String performedBy) {
		
		BoardCard card= boardCardRepo.findById(cardId).orElseThrow(()->new RuntimeException("Card not found"));
		
		BoardColumn from =card.getColumn();
		BoardColumn to=boardColumnRepo.findById(columnId).orElseThrow(()->new RuntimeException("Card not found"));
		
		if(to.getWipeLimit()!=null && to.getWipeLimit()>0) {
			long count= boardCardRepo.countByBoardIdColumnId(boardId, columnId);
			if(!Objects.equals(from.getId(), to.getId())&& count>=to.getWipeLimit()) {
				throw new RuntimeException("Wipe limit exceeded"+to.getBoardName());

			}
			
			
		}
		List<BoardCard> fromList=boardCardRepo.findByBoardIdAndColumnIdOrderPosition(boardId, from.getId());
		
		for(BoardCard bc : fromList) {
			if(bc.getPosition()>card.getPosition()) {
				bc.setPosition(bc.getPosition()-1);
				boardCardRepo.save(bc);
			}
		}
		List<BoardCard> toList=boardCardRepo.findByBoardIdAndColumnIdOrderPosition(boardId, to.getId());
		for(BoardCard bc : toList) {
			if(bc.getPosition()>= toPosition()) {
				bc.setPosition(bc.getPosition()+1);
				boardCardRepo.save(bc);
			}

	}
	card.setColumn(to);
	card.setPosition(toPosition);
	boardCardRepo.save(card);
	
	Issue issue= issueRepo .finById(card.getIssueId()).orElseThrow(()->new RuntimeException("Issue not found"));
	if(to.getStatusKey()!=null) {
		issue.setIssueStatus(Enum.valueOf(com.TaskManagement.Enum.IssueStatus.class,to.getStatusKey()));
		
		issueRepo.save(issue);
	}
		
	
	}
	
	
	private Integer toPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	public void recordColumn(Long boardId,Long columnId,List<Long> orderCardIds) {
		int pos=0;
		for(Long cId:orderCardIds) {
			BoardCard bd=boardCardRepo.findById(cId).orElseThrow(()->new RuntimeException("Issue not found"));
			bd.setPosition(pos++);
			boardCardRepo.save(bd);
		}
	}
		@Transactional
		public void startSprint(Long sprintId) {
			
	}
		@Transactional
		public void completeSprint(Long sprintId) {
			
	}

}
	
