package com.TaskManagement.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Entity.Board;
import com.TaskManagement.Entity.BoardCard;
import com.TaskManagement.Entity.BoardColumn;
import com.TaskManagement.Service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@PostMapping("/create_board")
	public ResponseEntity<Board>create(@RequestBody Board board){
		return ResponseEntity.ok(boardService.createBoard(board));
	}
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Board>> getBoardById(@PathVariable Long id){ 
		return ResponseEntity.ok(boardService.findById(id));
		
	}
	@GetMapping("/{id}/columns")
	public ResponseEntity<List<BoardColumn>> getBoardByColumn(@PathVariable Long id){
		return ResponseEntity.ok(boardService.getByColumns(id));
	}
	@PostMapping("/{id}/columns")
	public ResponseEntity<Board> addColumn(@PathVariable Long id,@RequestBody BoardColumn boardColumn){
		boardColumn.setBoardId(boardService.findById(id).orElseThrow(()->new RuntimeException("Board not found")));
		
		return ResponseEntity.ok(boardService.createBoard(boardColumn.getBoardId()));
	
	}
	@PostMapping("/{id}/cards")
	public ResponseEntity<BoardCard>addCards(@PathVariable Long id,@RequestBody Map<String,Object>body){
		
		Long columnId=Long.valueOf(String.valueOf(body.get("columnId")));
		Long issueId=Long.valueOf(String.valueOf(body.get("issueId")));

		return ResponseEntity.ok(boardService.addIssueToBoard(id, columnId, issueId));
		
	}
	@PostMapping("/{id}/{cards}/{cardId}/{move}")
	public ResponseEntity<String>moveCards(@PathVariable Long id
											,@PathVariable Long cardId
											,@RequestBody  Map<String,Object>body
											,@RequestHeader(value="Ex_user_Email",required=false)String user){
		
		Long toColumnId=Long.valueOf(String.valueOf(body.get("toColumnId")));
		int toPosition=Integer.parseInt(String.valueOf(body.get("toPosition")));
		
		
		boardService.moveCard(cardId,cardId,toColumnId,toPosition,user);
		return ResponseEntity.ok("Moved");
		
		
	}
	@PostMapping("/{id}/{columns}/{columnId}/{records}")
	public ResponseEntity<String>recordColumn( @PathVariable Long id,@PathVariable Long columnId,@RequestBody List<Long> orderCardIds){
		boardService.recordColumn(id, columnId, orderCardIds);
		return ResponseEntity.ok("recorded");

	}
	@PostMapping("/sprints/sprintId/start")
	public ResponseEntity<String>startSprint(@PathVariable Long sprintId){
		boardService.startSprint(sprintId);
		return ResponseEntity.ok("sprint for started");
		
 
	}
	@PostMapping("/sprints/sprintId/complete")
	public ResponseEntity<String>completeSprint(@PathVariable Long sprintId){
		boardService.completeSprint(sprintId);
		return ResponseEntity.ok("sprint for completed");

	}



	

}
