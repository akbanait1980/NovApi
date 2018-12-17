package com.learn.restapi.nov.NovApi.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;

	@Size(min=2, message="!!!name should be longer!!!")
	private String name;
	
	@Past(message="!!!BirthDate cant be in future!!!")
	private Date birthDate;
	
	//from POST Class
	@OneToMany(mappedBy="user") 
	private List<Post> post;
	
	public User(){
		
	}
	
	public User(int id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	

}
