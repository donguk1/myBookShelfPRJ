//package kopo.poly.component;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Slf4j
//@RequiredArgsConstructor
//public abstract class AbstractMongoDBComon {
//
//
//    protected boolean createCollection(MongoTemplate mongoDB, String colName) {
//
//        log.info("component abstract createCollection");
//
//        boolean res;
//
//        if (mongoDB.collectionExists(colName)) {
//            res = false;
//
//        } else {
//            mongoDB.collectionExists(colName);
//
//            res = true;
//        }
//
//         return res;
//    }
//}
