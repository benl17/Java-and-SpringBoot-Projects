package todolistbackend.todolistbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolistbackend.todolistbackend.repository.ItemRepository;
import todolistbackend.todolistbackend.model.Item;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(value = "/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    //api to create item
    @PostMapping("/items")
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }
}
