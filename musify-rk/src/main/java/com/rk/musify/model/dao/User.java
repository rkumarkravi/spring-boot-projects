package com.rk.musify.model.dao;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "email",columnNames = "email"))
@JsonIgnoreProperties(value = {"pass","otp","albums"},allowSetters = true)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer uid;
	String uname;
	String pass;
	String email;
	String mobile;
	String otp;
	UUID apiKey=UUID.randomUUID();
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate=new Date();
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "uid")
	Set<Playlist> playlists;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "uid")
	Set<Album> albums;

	public User(String uname, String pass, String mobile) {
		this.uname = uname;
		this.pass = pass;
		this.mobile = mobile;
		this.otp = null;
	}
}
