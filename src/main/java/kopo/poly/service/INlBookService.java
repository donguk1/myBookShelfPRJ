package kopo.poly.service;

public interface INlBookService {

    void insertNlBook(String callNo,
                      String regId,
                      String title,
                      String manageName,
                      String placeInfo) throws Exception;

    void deleteNlBook(String callNo,
                      String regId) throws Exception;

    int getNlBook(String callNo,
                  String regId,
                  String title) throws Exception;
}
