package kopo.poly.service;

public interface INoticeService {

    /**
     * Notice 추가
     */
    Long insertNotice(final String userId,
                      final String title,
                      final String contents) throws Exception;
}
