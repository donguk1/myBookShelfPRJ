package kopo.poly.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import kopo.poly.repository.entity.BookShelfEntity;
import kopo.poly.repository.entity.BookmarkEntity;
import kopo.poly.repository.entity.PK.BookShelfId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelfEntity, BookShelfId> {

    List<BookShelfEntity> findByRegDtAndRegId(Date regDt, String regId) throws Exception;
}