package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.Announce;
import ch.supsi.webapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findAllByTypeAndAndGroupIsNull(Announce type);

	@Query("SELECT i FROM Item i where i.title like %:value% or i.description LIKE %:value% or i.category.name like %:value%")
	List<Item> search(@Param(value= "value") String q);
}
