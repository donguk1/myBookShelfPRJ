package kopo.poly.repository;

import kopo.poly.repository.entity.NlBookEntity;
import kopo.poly.repository.entity.PK.NlBookPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NlBookRepository extends JpaRepository<NlBookEntity, NlBookPK> {

    Optional<NlBookEntity> findByCallNoAndRegIdAndTitle(String callNo,
                                                String regId,
                                                String title);
}
