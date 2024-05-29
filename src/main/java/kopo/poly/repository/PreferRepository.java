package kopo.poly.repository;

import kopo.poly.repository.custom.PreferRepositoryCustom;
import kopo.poly.repository.entity.PK.PreferPK;
import kopo.poly.repository.entity.PreferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferRepository extends JpaRepository<PreferEntity, PreferPK>, PreferRepositoryCustom {
}
