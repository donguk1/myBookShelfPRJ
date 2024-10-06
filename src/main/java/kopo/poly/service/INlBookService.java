package kopo.poly.service;

import kopo.poly.dto.NlBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INlBookService {

    void insertNlBook(String callNo,
                      String regId,
                      String title,
                      String manageName,
                      String placeInfo,
                      String id) throws Exception;

    void deleteNlBook(String callNo,
                      String regId,
                      String id) throws Exception;

    int getNlBook(String callNo,
                  String regId,
                  String title,
                  String id) throws Exception;

    Page<NlBookDTO> getNlBookList(String regId,
                                  Pageable pageable,
                                  String keyword) throws Exception;
}
