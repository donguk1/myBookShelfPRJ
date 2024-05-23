package kopo.poly.repository;

import kopo.poly.repository.entity.BookShelfEntity;
import kopo.poly.repository.entity.PK.BookShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelfEntity, BookShelfId> {

    Page<BookShelfEntity> findByRegIdAndTitleContainingOrderByRegDtDesc(String regId, Pageable pageable, String title) throws Exception;

}
