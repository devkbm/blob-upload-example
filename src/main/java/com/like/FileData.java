package com.like;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "TEST")
public class FileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="FILE_NAME")
	private String name;
	
	@Column(name="FILE_TYPE")
	private String type;
	
	@Lob
	@Column(name="BLOB_DATA")	
	private byte[] data;
	
	public FileData(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}
}
