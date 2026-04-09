package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.EventCategory;
import com.campusconnect.repository.EventCategoryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class EventCategoryController {

    private final EventCategoryRepository repository;

    @PostMapping
    public ResponseEntity<EventCategory> create(@RequestBody EventCategory category) {
        return ResponseEntity.ok(repository.save(category));
    }

    @GetMapping
    public ResponseEntity<List<EventCategory>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventCategory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventCategory> update(@PathVariable Long id,
                                                @RequestBody EventCategory details) {

        EventCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        category.setName(details.getName());
        category.setCategoryId(details.getCategoryId());
        category.setLevel(details.getLevel());
        category.setParentCategory(details.getParentCategory());

        return ResponseEntity.ok(repository.save(category));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventCategory> patch(@PathVariable Long id,
                                              @RequestBody EventCategory details) {

        EventCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (details.getName() != null)
            category.setName(details.getName());

        if (details.getCategoryId() != null)
            category.setCategoryId(details.getCategoryId());

        return ResponseEntity.ok(repository.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}