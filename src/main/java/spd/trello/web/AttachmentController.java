package spd.trello.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import spd.trello.domain.Attachment;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

	@Autowired 
	private AttachmentStorageService attachmentStorageService;
	
	@GetMapping()
	public String get(Model model) {
		List<Attachment> attachments = attachmentStorageService.getFiles();
		model.addAttribute("attachments", attachments);
		return "attachment";
	}
	
	@PostMapping("/uploadFiles")
	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		for (MultipartFile file: files) {
			attachmentStorageService.saveFile(file);
			
		}
		return "redirect:/attachments";
	}
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID fileId){
		Attachment attachment = attachmentStorageService.getFile(fileId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.MULTIPART_MIXED)
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ attachment.getName()+"\"")
				.body(new ByteArrayResource(attachment.getData().getFile()));
	}
	
}

@Service
class AttachmentStorageService {
	@Autowired
	private spd.trello.repository.AttachmentRepository AttachmentRepository;

	public Attachment saveFile(MultipartFile file) {
		String Attachmentname = file.getOriginalFilename();
		try {
			Attachment attachment = new Attachment();
			attachment.setParentId(UUID.fromString("caa4359b-70bb-4cba-9951-f860f6ead52d"));
			attachment.setName(Attachmentname);
			attachment.getData().setFile(file.getBytes());
			return AttachmentRepository.save(attachment);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Optional<Attachment> getFile(UUID fileId) {
		return AttachmentRepository.findById(fileId);
	}
	public List<Attachment> getFiles(){
		return AttachmentRepository.findAll();
	}
}