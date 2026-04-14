package com.campusconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.EventCategory;
import com.campusconnect.repository.EventCategoryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event-categories")
@RequiredArgsConstructor
public class EventCategoryController {

    private final EventCategoryRepository eventCategoryRepository;

    @PostMapping
    public ResponseEntity<EventCategory> createCategory(@RequestBody EventCategory req) {

        EventCategory parentCategory = null;

        if (req.getParentCategory() != null && req.getParentCategory().getId() != null) {
            parentCategory = eventCategoryRepository.findById(req.getParentCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
        }

        EventCategory category = new EventCategory();
        category.setName(req.getName());
        category.setCategoryId(req.getCategoryId());
        category.setLevel(req.getLevel());
        category.setParentCategory(parentCategory);

        EventCategory savedCategory = eventCategoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<EventCategory>> getAllCategories() {
        return ResponseEntity.ok(eventCategoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventCategory> getCategoryById(@PathVariable Long id) {
        EventCategory category = eventCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        return ResponseEntity.ok(category);
    }

    @GetMapping("/category-id/{categoryId}")
    public ResponseEntity<EventCategory> getCategoryByCategoryId(@PathVariable String categoryId) {
        EventCategory category = eventCategoryRepository.findByCategoryId(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with categoryId " + categoryId));

        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventCategory> updateCategory(@PathVariable Long id,
                                                        @RequestBody EventCategory req) {
        EventCategory category = eventCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        EventCategory parentCategory = null;
        if (req.getParentCategory() != null && req.getParentCategory().getId() != null) {
            parentCategory = eventCategoryRepository.findById(req.getParentCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
        }

        category.setName(req.getName());
        category.setCategoryId(req.getCategoryId());
        category.setLevel(req.getLevel());
        category.setParentCategory(parentCategory);

        EventCategory updatedCategory = eventCategoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventCategory> patchCategory(@PathVariable Long id,
                                                       @RequestBody EventCategory req) {
        EventCategory category = eventCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        if (req.getName() != null) {
            category.setName(req.getName());
        }

        if (req.getCategoryId() != null) {
            category.setCategoryId(req.getCategoryId());
        }

        if (req.getLevel() != 0) {
            category.setLevel(req.getLevel());
        }

        if (req.getParentCategory() != null) {
            if (req.getParentCategory().getId() == null) {
                category.setParentCategory(null);
            } else {
                EventCategory parentCategory = eventCategoryRepository.findById(req.getParentCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
                category.setParentCategory(parentCategory);
            }
        }

        EventCategory updatedCategory = eventCategoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        EventCategory category = eventCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        eventCategoryRepository.delete(category);
        return ResponseEntity.ok("Category deleted successfully");
    }
}