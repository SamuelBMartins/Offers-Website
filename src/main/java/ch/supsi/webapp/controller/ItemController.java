package ch.supsi.webapp.controller;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.model.Item;
import ch.supsi.webapp.model.SubCategory;
import ch.supsi.webapp.repository.CategoryRepository;
import ch.supsi.webapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ItemController {
    final ItemService itemService;
    final CategoryRepository categoryRepository;

    @Autowired
    public ItemController(ItemService itemService, CategoryRepository categoryRepository) {
        this.itemService = itemService;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value="/items", method = RequestMethod.GET)
    public Iterable<Item> get() {
        return itemService.readAll();
    }

    @RequestMapping(value="/items/{id}", method = RequestMethod.GET)
    public Item get(@PathVariable int id) throws NotFoundException {
        return itemService.read(id);
    }

    @RequestMapping(value="/items", method = RequestMethod.POST)
    public ResponseEntity<Item> post(@RequestBody Item item) {
        Item createdItem = itemService.save(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @RequestMapping(value="/items/{id}", method = RequestMethod.PUT)
    public Item put(@PathVariable int id, @RequestBody Item item) throws NotFoundException {
        return itemService.update(id, item);
    }

    @RequestMapping(value="/items/{id}", method = RequestMethod.DELETE)
    public Map<String, Boolean> delete(@PathVariable int id) throws NotFoundException {
        itemService.delete(id);
        return Collections.singletonMap("success", true);
    }

    @RequestMapping(value="/item/search", method = RequestMethod.GET)
    public List<Item> search(@RequestParam String q) {
        return itemService.search(q);
    }

    @RequestMapping(value="/category/search", method = RequestMethod.GET)
    public Set<String> searchCategory(@RequestParam String q) {
        System.out.println(q+categoryRepository.findById(q).get());
        Set<String> subcategory = new HashSet<>();
        categoryRepository.findById(q).get().getSubCategory().forEach(e -> {
            subcategory.add(e.getName());
        });
        return  subcategory;
    }
}
