package com.TaskManagement.Entity;

import java.util.Set;

import jakarta.persistence.*;

import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;

import lombok.*;

@Entity
@Table(name="workFlow_Transactions")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WorkFlowTransaction {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	private IssueStatus fromStatus;
	private IssueStatus toStatus;
	private String actionName;
	private Set<Role> role;
	
	
	@ManyToOne(fetch = FetchType.LAZY)

	@JoinColumn(name = "workflow_id", nullable = false)
	private WorkFlow workFlow;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public IssueStatus getFromStatus() {
		return fromStatus;
	}


	public void setFromStatus(IssueStatus fromStatus) {
		this.fromStatus = fromStatus;
	}


	public IssueStatus getToStatus() {
		return toStatus;
	}


	public void setToStatus(IssueStatus toStatus) {
		this.toStatus = toStatus;
	}


	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	

	public Set<Role> getRole() {
		return role;
	}


	public void setRole(Set<Role> role) {
		this.role = role;
	}


	public WorkFlow getWorkFlow() {
		return workFlow;
	}


	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}


	
	
	
	
	

}


