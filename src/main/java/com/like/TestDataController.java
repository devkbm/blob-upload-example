package com.like;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// https://bezkoder.com/spring-boot-upload-file-database/

@Controller
//@CrossOrigin("http://localhost:8081")
public class TestDataController {

	private TestDataRepository repository;
	
	public TestDataController(TestDataRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping("/file/")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {						
				
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileData fileData = new FileData(fileName, file.getContentType(), file.getBytes());
		
		repository.save(fileData);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(new ResponseMessage(fileName + "업로드 되었습니다."));
	}
	
	@GetMapping("/file/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable(value="id") Long id) throws Exception {						
				
		FileData file = repository.findById(id).orElse(null);		
								
		return ResponseEntity.ok()							 							
							 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(file.getName().getBytes("EUC-KR"), "8859_1") + "\"")
							 .contentType(MediaType.APPLICATION_OCTET_STREAM)							
							 .contentLength(file.getData().length)							 
							 .body(file.getData());
	}
	
	 @GetMapping("/files")
	  public ResponseEntity<List<ResponseFile>> getListFiles() {
	    List<ResponseFile> files = repository.findAll().stream().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/files/")
	          .path(dbFile.getId().toString())
	          .toUriString();

	      return new ResponseFile(
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getType(),
	          dbFile.getData().length);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
}
