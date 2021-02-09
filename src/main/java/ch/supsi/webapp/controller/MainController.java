package ch.supsi.webapp.controller;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.model.Announce;
import ch.supsi.webapp.model.Item;
import ch.supsi.webapp.service.CategoryService;
import ch.supsi.webapp.service.ItemService;
import ch.supsi.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Controller
public class MainController {
	private final ItemService itemService;
	private final CategoryService categoryService;
	private final UserService userService;

	@Autowired
	public MainController(ItemService itemService, CategoryService categoryService, UserService userService) {
		this.itemService = itemService;
		this.categoryService = categoryService;
		this.userService = userService;
	}

	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("offers", itemService.readOffers());
		model.addAttribute("requests", itemService.readRequests());
		model.addAttribute("page", "home");
		return "index";
	}

	@GetMapping("/item/{id}")
	public String getItem(@PathVariable int id, Model model) throws NotFoundException {
		model.addAttribute("item", itemService.read(id));
		return "itemDetails";
	}

	@GetMapping("/item/new")
	public String getForm(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("typesAnnounce", Announce.values());
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("respondTo", "/item/new");
		model.addAttribute("page", "form");
		return "createItemForm";
	}

	@PostMapping("/item/new")
	public String createItem(Item item, @RequestParam("file") MultipartFile file) throws IOException {
		item.setDate(new Date());
		if(!file.isEmpty())
			item.setImage(file.getBytes());
		item.setAuthor(userService.getLoggedUser());
		itemService.save(item);
		return "redirect:/";
	}

	@GetMapping("/item/{id}/edit")
	public String getFormEdit(@PathVariable int id, Model model) throws NotFoundException {
		Item item = itemService.read(id);
		model.addAttribute("item", item);
		model.addAttribute("typesAnnounce", Announce.values());
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("respondTo", "/item/" + id + "/edit");
		return "createItemForm";
	}

	@PostMapping("/item/{id}/edit")
	public String updateItem(@PathVariable int id, Item item, @RequestParam("file") MultipartFile file) throws NotFoundException, IOException {
		item.setDate(new Date());
		if(!file.isEmpty())
			item.setImage(file.getBytes());
		else
			item.setImage(itemService.read(id).getImage());
		item.setAuthor(userService.getLoggedUser());
		itemService.save(item);
		return "redirect:/";
	}

	@GetMapping("/item/{id}/delete")
	public String deleteItem(@PathVariable int id) throws NotFoundException {
		itemService.delete(id);
		return "redirect:/";
	}

}
