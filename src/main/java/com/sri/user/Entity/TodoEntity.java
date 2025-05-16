package com.sri.user.Entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="Todo")
public class TodoEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="description",nullable=false)
	private String description;
	
	@CreationTimestamp
	@Column(name="startTime",updatable = false)
	private Date startTime;
	
	@UpdateTimestamp
	@Column(name="endTime",insertable = false)
	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
//	@Override
//	public int hashCode() {
//		return Objects.hash(course, id, name);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TodoEntity other = (TodoEntity) obj;
//		return Objects.equals(course, other.course) && id == other.id && Objects.equals(name, other.name);
//	}
	
	
}
