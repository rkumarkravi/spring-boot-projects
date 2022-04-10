package com.rk.musify.model.dao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String albumId;
	String albumName;
	String creatorName;
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "albumId")
	Set<MusicFile> musicFiles;

	public Album(String albumName, String creatorName) {
		super();
		this.albumName = albumName;
		this.creatorName = creatorName;
	}

}