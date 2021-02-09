package ch.supsi.webapp.controller;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Controller
public class ImageController {
	private final ItemService itemService;

	@Autowired
	public ImageController(ItemService itemService) {
		this.itemService = itemService;
	}

	@ResponseBody
	@GetMapping("/item/{id}/image")
	public ResponseEntity<byte[]> index(@PathVariable int id, WebRequest webRequest) throws NotFoundException, IOException {
		if (webRequest.checkNotModified(itemService.read(id).getDate().toInstant().toEpochMilli())) {
			return null;
		}

		byte[] img = itemService.read(id).getImage();
		if (img == null) {
			String path = Objects.requireNonNull(getClass().getClassLoader().getResource("static/img/noimage.jpeg")).getFile();
			Path path2 = new File(path).toPath();
			img = Files.readAllBytes(path2);
		}


		return ResponseEntity.ok()
				.lastModified(itemService.read(id).getDate().toInstant())
				.body(img);
	}

}
