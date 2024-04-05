package kopo.poly.service;

public interface IMailService {

    /**
     *  메일 발송
     */
    int doSendMail(final String toMail,
                   final String title,
                   final String contents) throws Exception;

    /* 임시 비번 생성 */
    String getTmpPassword() throws Exception;
}
