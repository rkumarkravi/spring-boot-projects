package com.rk.musify.model.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicFile {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	String name;
	String contentType;
	@Lob
	byte[] blob;

	public MusicFile(String name,String contentType,byte[] blob) {
		super();
		this.name = name;
		this.contentType=contentType;
		this.blob = blob;
	}

}
