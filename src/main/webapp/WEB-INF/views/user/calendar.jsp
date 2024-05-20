<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>BookShelf</title>

    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/5a07bfcb19.js" crossorigin="anonymous"></script>

<%--    <link href="/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">--%>
<%--    <link href="/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">--%>
<%--    <link href="/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">--%>
<%--    <link href="/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">--%>
<%--    <link href="/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">--%>


<%--    <link href="/css/style.css" rel="stylesheet">--%>
<%--    <link href="/css/bluStyle.css" rel="stylesheet">--%>
    <link rel="stylesheet" type="text/css" href="/css/introRecord.css">
    <link rel="stylesheet" type="text/css" href="/css/table.css">

    <style>
        .s {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .s.horizontal {
            flex-direction: row;
            justify-content: space-between;
            width: 100%;
        }

        .s.horizontal .ss {
            flex: 0 0 24%;
        }
    </style>

    <!-- Template Main JS File -->
    <script src="https://kit.fontawesome.com/5a07bfcb19.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
    <script type="text/javascript" src="/js/js-introRecord.js"></script>

</head>
<body>
<%@include file="../header.jsp" %>

<main class="container">
    <%--<h2 style="text-align: center">기록</h2>--%>

    <div id="registerBtns" style="margin: 10px 0"> <%-- 위치 원하는 위치 몰라서 ... --%>
        <button id="registerBookShelf" type="button" class="btn btn-secondary my-2 my-sm-0">도서 추가</button>
    </div>

    <%--<hr/>--%>
    <%-- 달력 --%>
    <table class="scriptCalendar">
        <thead style="font-weight: bolder;">
        <tr>
            <td onclick="prevCalendar()" style="cursor:pointer;"><i class="fa-solid fa-chevron-left"
                                                                    style="color: #27ADFB;"></i></td>
            <td colspan="5">
                <span id="calYear">YYYY</span>년
                <span id="calMonth">MM</span>월
            </td>
            <td onclick="nextCalendar()" style="cursor:pointer;"><i class="fa-solid fa-chevron-right"
                                                                    style="color: #27ADFB;"></i></td>
        </tr>
        <tr>
            <td>일</td>
            <td>월</td>
            <td>화</td>
            <td>수</td>
            <td>목</td>
            <td>금</td>
            <td>토</td>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
    <br>

    <%-------------팝업창들--------------%>

    <!-- 도서 추가 팝업 -->
    <div id="bookShelfModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">도서 추가</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form name="bookShelf" id="fo">
                    <br/><br/>
                    <div class="form-group mx-auto" style="width: 90%;">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" name="bookShelfTitle" id="bookShelfTitle"
                                   placeholder="책 제목">
                            <label for="bookShelfTitle">책 제목</label>
                        </div>
                    </div>
                </form>
                <br/><br/>
                <div class="modal-footer">
                    <button type="submit" id="modalBtnSc" class="btn btn-primary">저장</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 도서 수정 팝업 -->
    <div id="bookShelfEditModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">도서 수정</h5>
                    <button type="button" class="btn btn-secondary my-2 my-sm-0" ></button>
                </div>
                <form name="bookShelfEdit" id="bookShelfEditf"> <!-- onsubmit="editbookShelf();" -->
                    <br/><br/>
                    <input type="hidden" name="bookShelfSeq" id="bookShelfSeq">
                    <div class="form-group mx-auto" style="width: 90%;">
                        <div class="form-floating mb-3">
                            <input type="hidden" id="oldTitle">
                            <input type="text" class="form-control" name="bookShelf" id="newTitle">
                            <label for="newTitle">제목</label>
                        </div>
                    </div>
                </form>
                <br/><br/>
                <div class="modal-footer">
                    <button type="submit" id="editBtnSc" class="btn btn-primary">수정</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>


    <div id="bookShelfList">
        <div id="bookShelf"></div>
    </div>


</main>
<%@include file="../footer.jsp" %>
</body>
</html>