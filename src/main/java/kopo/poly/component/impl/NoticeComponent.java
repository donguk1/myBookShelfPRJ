//package kopo.poly.component.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import kopo.poly.component.AbstractMongoDBComon;
//import kopo.poly.component.INoticeComponent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class NoticeComponent extends AbstractMongoDBComon implements INoticeComponent {
//
//    private final MongoTemplate mongoDB;
//
//    @Override
//    public void insertNotice(String userId, String title, String contents, String colName) throws Exception {
//
//        log.info("component insertNotice");
//
//        super.createCollection(mongoDB, colName);
//
//        MongoCollection<Document> collection = mongoDB.getCollection(colName);
//
//    }
//
//    @Override
//    public Long getNoticeSeq(String colName) throws Exception {
//
//        log.info("component getNoticeSeq");
//
//        MongoCollection<Document> col = mongoDB.getCollection(colName);
//
//        Document doc = new Document();
//
//        FindIterable<Document> iterable = col.find(new Document()).projection(doc);
//
//
//
//        return null;
//    }
//}
