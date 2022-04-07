package spd.trello.web;

import java.util.List;
import java.util.UUID;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import spd.trello.domain.Attachment;
import spd.trello.service.AttachmentService;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

	@Autowired 
	private AttachmentService attachmentStorageService;
	
	@GetMapping()
	public String get(Model model) {
		List<Attachment> attachments = attachmentStorageService.getAll();
		model.addAttribute("attachments", attachments);
		return "attachment";
	}
	
	@PostMapping("/uploadFiles")
	@SneakyThrows
	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		for (MultipartFile file: files) {
			String Attachmentname = file.getOriginalFilename();
			Attachment attachment = new Attachment();
			attachment.setParentId(UUID.fromString("caa4359b-70bb-4cba-9951-f860f6ead52d"));
			attachment.setName(Attachmentname);
			attachment.getData().setFile(file.getBytes());
			attachmentStorageService.create(attachment);
			
		}
		return "redirect:/attachments";
	}
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID fileId){
		Attachment attachment = attachmentStorageService.read(fileId);
		return ResponseEntity.ok()
				.contentType(MediaType.MULTIPART_MIXED)
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ attachment.getName()+"\"")
				.body(new ByteArrayResource(attachment.getData().getFile()));
	}
	
}