package ch.supsi.webapp.controller;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.model.Announce;
import ch.supsi.webapp.model.Item;
import ch.supsi.webapp.model.ItemGroup;
import ch.supsi.webapp.service.CategoryService;
import ch.supsi.webapp.service.GroupService;
import ch.supsi.webapp.service.ItemService;
import ch.supsi.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Controller
public class GroupController {
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public GroupController(ItemService itemService, CategoryService categoryService, UserService userService, GroupService groupService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String getGroups(Model model){
        model.addAttribute("myGroups", groupService.getMyGroups());
        model.addAttribute("allGroups", groupService.getAll());
        model.addAttribute("user", userService.getLoggedUser());
        model.addAttribute("page", "groups");
        return "groups";
    }

    @GetMapping("/groups/create")
    public String createGroup(Model model){
        model.addAttribute("group", new ItemGroup());
        model.addAttribute("respondTo", "/groups/create");
        return "createGroup";
    }

    @GetMapping("/groups/{id}")
    public String getGroup(@PathVariable int id, Model model) throws NotFoundException {
        model.addAttribute("group", groupService.findById(id));
        model.addAttribute("hasAccess", groupService.findById(id).getMembers().contains(userService.getLoggedUser()));
        return "groupDetail";
    }

    @GetMapping("/groups/{id}/create")
    public String createItem(@PathVariable int id, Model model) throws NotFoundException {
        model.addAttribute("item", new Item());
        model.addAttribute("typesAnnounce", Announce.values());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("respondTo", "/groups/"+id+"/create");
        return "createItemForm";
    }

    @PostMapping("/groups/{id}/create")
    public String createItem(@PathVariable int id, Item item, @RequestParam("file") MultipartFile file) throws IOException, NotFoundException {
        item.setDate(new Date());
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        item.setAuthor(userService.getLoggedUser());
        item.setGroup(groupService.findById(id));
        itemService.save(item);
        return "redirect:/groups/+"+id;
    }

    @GetMapping("/groups/{id}/enter")
    public String addToGroup(@PathVariable int id, Model model) throws NotFoundException {
        ItemGroup group = groupService.findById(id);
        group.getMembers().add(userService.getLoggedUser());
        groupService.save(group);
        return "redirect:/groups/"+id+"/";
    }


    @PostMapping("/groups/create")
    public String createItem(ItemGroup group) throws IOException {
        group.setCreator(userService.getLoggedUser());
        group.setDate(new Date());
        group.getMembers().add(userService.getLoggedUser());
        groupService.save(group);
        return "redirect:/groups";
    }
}
