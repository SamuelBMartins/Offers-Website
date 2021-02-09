package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.ItemGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<ItemGroup, Integer> {
}
