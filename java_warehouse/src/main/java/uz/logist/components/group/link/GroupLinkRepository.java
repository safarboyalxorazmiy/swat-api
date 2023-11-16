package uz.logist.components.group.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupLinkRepository extends JpaRepository<GroupLinkEntity, Long> {
}