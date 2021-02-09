package ch.supsi.webapp.service;

import ch.supsi.webapp.model.Category;
import ch.supsi.webapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Iterable<Category> getAll() {
		return categoryRepository.findAll();
	}
}
