package com.rk.musify.model.dao;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String uid;
	String uname;
	String pass;
	String mobile;
	String otp;
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String apiKey;
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
