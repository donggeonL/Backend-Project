package com.example.demo.model;






import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String userid;
	private String userpw;
	
	@Builder
	public User(String userid, String userpw) {
		this.userid = userid;
		this.userpw = userpw;
	}
	
}
