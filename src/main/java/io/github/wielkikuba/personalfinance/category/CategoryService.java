package io.github.wielkikuba.personalfinance.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(String name){
        Category category = Category.builder().name(name).build();
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Category "+id+" does not exists"));
        try{
            categoryRepository.delete(category);
            categoryRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("Category cannot be deleted due to existing constraints.");
        }
    }
    @Transactional(readOnly = true)
    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Category "+id+" does not exists"));
        return category;
    }

    @Transactional(readOnly = true)
    public Category getCategoryByName(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Category "+name+" does not exists"));
        return category;
    }
}
