package com.rk.musify.model.dao;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Album {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String albumId;
	String albumName;
	String creatorName;
	String description;
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate=new Date();
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "albumId")
	Set<MusicFile> musicFiles;
	String albumArt;
	String dominantColour;

	public Album(String albumName, String creatorName,String url) {
		super();
		this.albumName = albumName;
		this.creatorName = creatorName;
		this.albumArt=url;
	}

}
