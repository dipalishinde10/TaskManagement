package com.TaskManagement.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.TaskManagement.Enum.BoardType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="boards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String boardName;
	private String projectKey;
	private BoardType boardType;
	private LocalDateTime createAt=LocalDateTime.now();
	
	@OneToMany(mappedBy="board",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("position")
	private List<BoardColumn>columns=new ArrayList<>();
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	public BoardType getBoardType() {
		return boardType;
	}
	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public List<BoardColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<BoardColumn> columns) {
		this.columns = columns;
	}
	
	
	
}
