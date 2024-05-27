package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INoticeService {

    /**
     * Notice 추가
     */
    Long insertNotice(final String userId,
                      final String title,
                      final String contents) throws Exception;

    Page<NoticeDTO> getNoticeList(Pageable pageable,
                                  final String keyword) throws Exception;

    NoticeDTO getNoticeInfo(final Long noticeSeq,
                          final Boolean type) throws Exception;

    int updateNotice(final Long noticeSeq,
                      final String title,
                      final String contents) throws Exception;

    void deleteNotice(final Long noticeSeq) throws Exception;
}
