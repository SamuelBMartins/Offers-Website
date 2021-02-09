package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.Category;
import ch.supsi.webapp.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepsitory extends JpaRepository<SubCategory, String> {
}
