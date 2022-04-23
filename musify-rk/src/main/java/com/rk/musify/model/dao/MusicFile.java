package com.rk.musify.model.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicFile{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	String name;
	String contentType;
	String blobId;
	Boolean published;
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate=new Date();

	public MusicFile(String name,String contentType,String blobId,Boolean published) {
		super();
		this.name = name;
		this.contentType=contentType;
		this.blobId=blobId;
		this.published=published;
	}

}
