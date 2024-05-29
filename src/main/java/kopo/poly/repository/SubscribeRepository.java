package kopo.poly.repository;

import kopo.poly.repository.custom.SubscribeRepositoryCustom;
import kopo.poly.repository.entity.PK.SubscribePK;
import kopo.poly.repository.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, SubscribePK>, SubscribeRepositoryCustom {


}
