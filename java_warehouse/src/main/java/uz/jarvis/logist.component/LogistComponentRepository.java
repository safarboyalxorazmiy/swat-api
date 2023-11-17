package uz.jarvis.logist.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogistComponentRepository extends JpaRepository<LogistComponentEntity, Long> {
  Optional<LogistComponentEntity> findByComponentIdAndLogistId(Long componentId, Long logistId);

  List<LogistComponentEntity> findByLogistIdOrderByUpdatedDateDesc(Long logistId);

  @Query("from LogistComponentEntity where (logistId = ?2 and component.code like ?1) or (component.name like ?1 and logistId = ?2) order by updatedDate desc ")
  List<LogistComponentEntity> search(String searchQuery, Long logistId);
}