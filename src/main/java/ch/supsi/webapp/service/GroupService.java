package ch.supsi.webapp.service;

import ch.supsi.webapp.exceptions.NotFoundException;
import ch.supsi.webapp.model.ItemGroup;
import ch.supsi.webapp.repository.CategoryRepository;
import ch.supsi.webapp.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;

    @Autowired
    public GroupService(CategoryRepository categoryRepository, GroupRepository groupRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public Iterable<ItemGroup> getAll() {
        return groupRepository.findAll();
    }

    public List<ItemGroup> getMyGroups() {
        List<ItemGroup> myGroups = new ArrayList<>();
        groupRepository.findAll().forEach(g -> {
            if (g.getMembers().contains(userService.getLoggedUser())) {
                myGroups.add(g);
            }
        });
        return myGroups;
    }

    public ItemGroup save(ItemGroup group) {
        return groupRepository.save(group);
    }

    public ItemGroup findById(int id) throws NotFoundException {
        return groupRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteAll() {
        groupRepository.findAll().forEach(e -> {
            e.getMembers().clear();
            e.getItems().clear();
            groupRepository.save(e);
        });
        groupRepository.deleteAll();
    }

}
