package by.aws.projectsecond.repository;

import by.aws.projectsecond.entity.MetadataEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Polina Bril
 */
@Repository
public interface MetadataRepository extends JpaRepository<MetadataEntity, Long> {

	List<MetadataEntity> findAll();

	void removeByName(String name);

	Optional<MetadataEntity> findByName(String name);
}
