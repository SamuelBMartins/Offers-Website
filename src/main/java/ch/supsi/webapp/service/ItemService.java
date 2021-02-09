package ch.supsi.webapp.service;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.model.Announce;
import ch.supsi.webapp.model.Item;
import ch.supsi.webapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    final ItemRepository itemRepository;

    @Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public Iterable<Item> readAll() {
        return itemRepository.findAll();
    }

    public Item read(int id) throws NotFoundException {
        return itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Item> readOffers() {
        return itemRepository.findAllByTypeAndAndGroupIsNull(Announce.OFFER);
    }

    public List<Item> readRequests() {
        return itemRepository.findAllByTypeAndAndGroupIsNull(Announce.REQUEST);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item update(int id, Item item) throws NotFoundException {
        Item oldItem = itemRepository.findById(id).orElseThrow(NotFoundException::new);
        changeItem(oldItem, item);
        return itemRepository.save(oldItem);
    }

    public List<Item> search(String q) {
        return itemRepository.search(q);
    }

    @SuppressWarnings("UnusedReturnValue")
    public Item delete(int id) throws NotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(NotFoundException::new);
        itemRepository.deleteById(id);
        return item;
    }

    private void changeItem(Item to, Item from) {
        if (from.getTitle() != null)
            to.setTitle(from.getTitle());
        if (from.getDescription() != null)
            to.setDescription(from.getDescription());
        if(from.getAuthor() != null)
            to.setAuthor(from.getAuthor());
    }
}
