package com.huy.cryptoblog.service;

import com.huy.cryptoblog.dto.CategoryDTO;
import com.huy.cryptoblog.exception.ResourceNotFoundException;
import com.huy.cryptoblog.model.Category;
import com.huy.cryptoblog.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(this::mapToCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepo.save(category);
        return mapToCategoryDTO(category);
    }

    // delete category
    public void deleteCategory(String name) {
        Category category = categoryRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepo.delete(category);
    }

    public CategoryDTO mapToCategoryDTO (Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}
