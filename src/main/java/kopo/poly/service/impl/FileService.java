package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.FileDTO;
import kopo.poly.repository.FileRepository;
import kopo.poly.repository.entity.FileEntity;
import kopo.poly.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService implements IFileService {

    private final FileRepository fileRepository;

    /**
     * 파일 저장
     */
    @Override
    public void saveFiles(FileDTO pDTO) throws Exception {

        log.info("service 파일 저장");

        fileRepository.save(FileEntity.builder()
                .boardSeq(pDTO.boardSeq())
                .noticeSeq(pDTO.noticeSeq())
                .orgFileName(pDTO.orgFileName())
                .saveFilePath(pDTO.saveFilePath())
                .saveFileName(pDTO.saveFileName())
                .saveFileUrl(pDTO.saveFileUrl())
                .build());

    }

    /**
     * 이미지 삭제
     */
    @Override
    public void deleteFiles(Long boardSeq) throws Exception {
        fileRepository.deleteAll(fileRepository.findByBoardSeq(boardSeq));
    }

    /**
     * 이미지 삭제
     */
    @Override
    public void deleteImage(Long fileSeq) throws Exception {
        fileRepository.delete(fileRepository.findByFileSeq(fileSeq));
    }

    /**
     * 경로 가져오기
     */
    @Override
    public List<FileDTO> getFilePath(Long boardSeq) throws Exception {

        log.info("service 경로 가져오기");

        List<FileEntity> pEntity = fileRepository.findByBoardSeq(boardSeq);

        return new ObjectMapper().convertValue(pEntity,
                new TypeReference<List<FileDTO>>() {
                });
    }
}
