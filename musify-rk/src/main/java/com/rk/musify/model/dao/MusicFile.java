package com.rk.musify.model.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class MusicFile{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	String musicName;
	String contentType;
	String blobId;
	Boolean published;
	String duration;
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate=new Date();

	public MusicFile(String musicName,String contentType,String blobId,Boolean published) {
		super();
		this.musicName = musicName;
		this.contentType=contentType;
		this.blobId=blobId;
		this.published=published;
	}

	public MusicFile(String musicName, String contentType, String blobId, Boolean published, String duration) {
		super();
		this.musicName = musicName;
		this.contentType = contentType;
		this.blobId = blobId;
		this.published = published;
		this.duration = duration;
	}

}
