package todolistbackend.todolistbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolistbackend.todolistbackend.repository.ItemRepository;
import todolistbackend.todolistbackend.exception.ResourceNotFoundException;
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

    //api to get employee from id
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with that ID does not exist." + id));
        
        return ResponseEntity.ok(item);
    }

    //api to update employee
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with that ID does not exist." + id));

        item.setItemName(itemDetails.getItemName());
        item.setDueDate(itemDetails.getDueDate());
        item.setItemImportance(itemDetails.getItemImportance());

        Item itemUpdated = itemRepository.save(item);
        return ResponseEntity.ok(itemUpdated);
    }

    //api to finish task
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Boolean>> finishTask(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with that ID does not exist." + id));

        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}