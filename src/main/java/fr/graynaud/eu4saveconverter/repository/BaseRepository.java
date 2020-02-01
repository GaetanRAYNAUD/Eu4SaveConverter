package fr.graynaud.eu4saveconverter.repository;

import fr.graynaud.eu4saveconverter.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Integer> {
}
