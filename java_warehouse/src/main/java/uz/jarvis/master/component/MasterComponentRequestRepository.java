package uz.jarvis.master.component;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterComponentRequestRepository extends JpaRepository<MasterComponentRequestEntity, Long> {
  List<MasterComponentRequestEntity> findByVerifiedFalseAndMasterIdOrderByCreatedDateDesc(Long masterId);
}