package kopo.poly.service.impl;

import kopo.poly.dto.NoticeDTO;
import kopo.poly.repository.NoticeRepository;
import kopo.poly.repository.entity.NoticeEntity;
import kopo.poly.service.INoticeService;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService implements INoticeService {

    private final NoticeRepository noticeRepository;

    private final MongoOperations mongoOperations;

    private final MongoTemplate mongoTemplate;

    public long generateSequence(String seqName) {
        NoticeEntity counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("noticeSeq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                NoticeEntity.class);
        return counter != null && counter.getNoticeSeq() != null ? counter.getNoticeSeq() : 0L;
    }

    @Override
    public Long insertNotice(String userId, String title, String contents) throws Exception {

        log.info("service insertNotice");

        String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

        noticeRepository.save(NoticeEntity.builder()
                        .noticeSeq(generateSequence("noticeSeq"))
                        .regId(userId)
                        .title(title)
                        .contents(contents)
                        .regDt(dt)
                        .chgDt(dt)
                        .readCnt(0L)
                        .build());

        return noticeRepository.findByRegDtAndChgDt(dt, dt).getNoticeSeq();
    }

    @Override
    public Page<NoticeDTO> getNoticeList(Pageable pageable, String keyword) throws Exception {

        log.info("service getNoticeList");

        return noticeRepository.findByRegIdOrderByNoticeSeq("admin", pageable, keyword).map(NoticeDTO::from);
    }

    @Override
    @Transactional
    public NoticeDTO getNoticeInfo(Long noticeSeq, Boolean type) throws Exception {


        log.info("service 상세정보 가져오기");

        if (type) {
            log.info("조회수 업데이트");

            Query query = new Query(Criteria.where("noticeSeq").is(noticeSeq)
                    .andOperator(Criteria.where("regId").is("admin")));
            Update update = new Update().inc("readCnt", 1);
            mongoTemplate.updateFirst(query, update, NoticeEntity.class);

        }

        return NoticeDTO.from(noticeRepository.findByRegIdAndNoticeSeq("admin", noticeSeq));

    }

    @Override
    @Transactional
    public int updateNotice(Long noticeSeq, String title, String contents) throws Exception {

        log.info("service updateNotice");

        String dt = DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss");

        Query query = new Query(
                Criteria.where("noticeSeq").is(noticeSeq)
                        .andOperator(Criteria.where("regId").is("admin")));

        Update update = new Update().set("title", title)
                .set("content", contents)
                .set("chgDt", dt);

        mongoTemplate.updateFirst(query, update, NoticeEntity.class);

        return 1;
    }

    @Override
    public void deleteNotice(Long noticeSeq) throws Exception {

        log.info("service deleteNotice");

        Query query = new Query(
                Criteria.where("noticeSeq").is(noticeSeq)
                        .andOperator(Criteria.where("regId").is("admin")));

        mongoOperations.remove(query, NoticeEntity.class);
    }
}
