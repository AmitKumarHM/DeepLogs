package edu.nitdelhi.deeplogs.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nitdelhi.deeplogs.entity.FileDetails;
import edu.nitdelhi.deeplogs.repository.FileDetailsRepository;

@RestController
public class BatchImportController {

	@Autowired
	private FileDetailsRepository studentRepository;

	@GetMapping("/student")
	public List<FileDetails> all() {
		List<FileDetails> studentList=new ArrayList<FileDetails>();
		Iterator<FileDetails> itr= studentRepository.findAll().iterator();
		while(itr.hasNext())
			studentList.add(itr.next());
		
		return studentList;
	}
	
}
