package kopo.poly.repository;

import kopo.poly.repository.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    /**
     * 경로 가져오기
     */
    List<FileEntity> findByBoardSeq(Long boardSeq);
}
