package kopo.poly.service;

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
}
