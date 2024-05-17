<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Board</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style type="text/css">
        .dropdown-menu {
            right: 0;
            top: 60px;
        }
        .dropdown-menu a {
            display: block;
        }
        .dropdown-menu a:hover {
            background-color: #ddd;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
        }

        /* 드롭다운 메뉴가 나타날 때 네비게이션 바가 밀리는 현상을 방지 */
        .navbar-nav {
            position: relative;
        }

        .navbar-nav .dropdown-menu {
            position: absolute;
            right: 0;
            z-index: 1000;
        }

        .readTextarea {
            width: 100%;
            resize: none;
            overflow-y: hidden; /* prevents scroll bar flash */
            border: none;
            outline: none;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/5a07bfcb19.js" crossorigin="anonymous"></script>

    <script type="text/javascript">

        const urlParams = new URL(location.href).searchParams;

        let ssUserId;
        let bookmaker;

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getSsUserId()
            getBoardInfo()
            getCommentList()

            $("div[id^='updateComment']").hide();
            $("div[id^='updateRecomment']").hide();
            $("div[id^='recommentArea']").hide();

            $("#btnEdit").on("click", function () {
                doEdit(); // 메모 수정하기 실행
            })

            $("#btnDelete").on("click", function () {
                doDelete(); // 메모 삭제하기 실행
            })

            $("#btnList").on("click", function () {
                location.href = "boardList"; // 메모 리스트 이동
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#bookmarkIcon").on("click", function () {
                bookmark()
            })
        })

        // 답글쓰기 버튼 클릭 시 답글 폼 보여주기
        function showReRegArea(commentSeq) {
            $("#reCommentArea_" + commentSeq).show();
        }

        // 댓글 수정 버튼 클릭 시 수정 폼 보여주기
        function showEditArea(commentSeq) {
            $("#baseComment_" + commentSeq).hide();
            $("#updateComment_" + commentSeq).show();
            console.log("commentSeq : " + commentSeq);
        }

        // 댓글 수정 취소 버튼 클릭 시 수정 폼 숨기기
        function showHideArea(commentSeq) {
            $("#baseComment_" + commentSeq).show();
            $("#updateComment_" + commentSeq).hide();
            console.log("commentSeq : " + commentSeq);
        }

        // 답글 수정 버튼 클릭 시 수정 폼 보여주기
        function showReEditArea(commentSeq) {
            $("#reBaseComment_" + commentSeq).hide();
            $("#updateRecomment_" + commentSeq).show();
            console.log("commentSeq : " + commentSeq);
        }

        // 댓글 수정 취소 버튼 클릭 시 수정 폼 숨기기
        function showReHideArea(commentSeq) {
            $("#reBaseComment_" + commentSeq).show();
            $("#updateRecomment_" + commentSeq).hide();
            console.log("commentSeq : " + commentSeq);
        }



        // 댓글 수정
        function doCoEdit(commentSeq) {

            const coUser = document.getElementById("commentUser_" + commentSeq).value;
            const upComment = document.getElementById("upComment_" + commentSeq).value;
            const boardSeq = document.getElementById("boardSeq").value;

            console.log("commentSeq : " + commentSeq);
            console.log("ssUserId : " + ssUserId);
            console.log("coUser : " + coUser);
            console.log("upComment : " + upComment);
            console.log("boardSeq : " + boardSeq);

            if (ssUserId === "") {
                if (confirm("로그인 정보가 없습니다. \n로그인 하시겠습니까?")) {
                    location.href = "/user/login";
                }
                return;

            } else if (ssUserId === coUser) {

                if (upComment === "") {
                    alert("내용을 작성하세요.");
                    document.getElementById("upComment_" + commentSeq).focus();
                    return;

                }

                // 요청 데이터를 JavaScript 객체로 구성
                const requestData = {
                    upComment: upComment,
                    boardSeq: boardSeq,
                    commentSeq: commentSeq,
                };


                $.ajax({
                    url: "/comment/updateComment",
                    type: "post",
                    dataType: "JSON",
                    data: requestData,
                    success: function (json) {
                        if (json.result === 1) {
                            alert(json.msg);
                            getCommentList()
                        } else {
                            alert(json.msg);
                        }

                    }
                })
            } else {
                alert("본인이 쓴 글만 수정 가능합니다.")
            }
        }

        // 댓글 삭제
        function doCoDelete(commentSeq) {

            const coUser = document.getElementById("commentUser_" + commentSeq).value;
            const upComment = document.getElementById("upComment_" + commentSeq).value;
            const nSeq = document.getElementById("nSeq").value;

            console.log("commentSeq : " + commentSeq);
            console.log("ssId : " + session_user_id);
            console.log("coUser : " + coUser);
            console.log("upComment : " + upComment);
            console.log("nSeq : " + nSeq);

            if (session_user_id === "") {
                if (confirm("로그인 정보가 없습니다. \n로그인 하시겠습니까?")) {
                    location.href = "/user/login";
                }
                return;

            } else if (session_user_id === coUser) {

                if (!confirm("삭제 하시겠습니까?")) {
                    return;
                }

                // 요청 데이터를 JavaScript 객체로 구성
                const requestData = {
                    upComment: upComment,
                    nSeq: nSeq,
                    commentSeq: commentSeq,
                };


                $.ajax({
                    url: "/comment/deleteComment",
                    type: "post",
                    dataType: "JSON",
                    data: requestData,
                    success: function (json) {

                        alert(json.msg);

                        if (json.result === 1) {
                            location.reload();

                        }

                    }
                })
            } else {
                alert("본인이 쓴 글만 수정 가능합니다.")
            }
        }

        // 북마크 모양 수정
        function bookmarkCheck() {
            const element = document.getElementById('bookmarkIcon');

            if (element.classList.contains('fa-regular')) {
                element.classList.remove('fa-regular');
                element.classList.add('fa-solid');

                bookmaker = true;

            } else {
                element.classList.remove('fa-solid');
                element.classList.add('fa-regular');

                bookmaker = false;
            }
        }

        // 세션 아이디 가져오기
        function getSsUserId() {

            $.ajax({
                url: "/user/getSsUserId",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    ssUserId = json.userId
                }

            });
        }

        //수정하기
        function doEdit() {
            console.log(ssUserId);
            console.log(document.getElementById("regId").value);

            if (ssUserId === document.getElementById("regId").value) {
                location.href = "boardEditInfo?boardSeq=" + document.getElementById("boardSeq").value;

            } else if (ssUserId === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 메모만 수정 가능합니다.");

            }
        }

        // 본문 내용 가져오기
        function getBoardInfo() {
            $.ajax({
                url: "/board/getBoardInfo",
                type: "post",
                dataType: "JSON",
                data: {
                    "bSeq" : urlParams.get('bSeq'),
                    "type" : true
                },
                success: function (json) {

                    insertData(json)
                    getBookmark()
                }
            });
        }

        // 본문 내용 입력
        function insertData(json) {
            document.getElementById("boardSeq").value = json.boardSeq;
            document.getElementById("regId").value = json.regId;
            document.getElementById("category").textContent = json.category;
            document.getElementById("title").textContent = json.title;
            document.getElementById("nickname").innerText = "작성자 : " + json.nickname;
            document.getElementById("regDt").innerText = "등록일 : " + json.regDt;
            document.getElementById("readCnt").innerText = "조회수 : " + json.readCnt;
            document.getElementById("contents").value = json.contents;
        }

        // 북마크
        function bookmark() {

            if (ssUserId === "") {
                if (confirm("로그인한 회원만 이용 가능한 기능입니다.\n" +
                    "로그인 하시겠습니까?")) {
                    location.href = "/user/login"
                }
                return

            }

            const data = {
                "boardSeq" : document.getElementById("boardSeq").value,
                "type" : bookmaker
            }

            $.ajax({
                url: "/bookmark/updateBookmark",
                type: "post",
                dataType: "JSON",
                data: data,
                success: function (json) {

                    if (json.result === 0) {
                        alert("오류로 인해 실패 했습니다.\n" +
                            "다시 실행해주세요")
                    } else {
                        bookmarkCheck()
                    }

                }

            });
        }

        // 북마크 여부 가져오기
        function getBookmark() {

            if (ssUserId.length > 0) {

                $.ajax({
                    url: "/bookmark/getBookmark",
                    type: "post",
                    dataType: "JSON",
                    data: {"boardSeq" : document.getElementById("boardSeq").value},
                    success: function (json) {

                        const element = document.getElementById('bookmarkIcon');

                        if (json.result === 1) {
                            element.classList.add('fa-solid');
                            bookmaker = true;

                        } else {
                            element.classList.add('fa-regular');

                            bookmaker = false;
                        }


                    }

                });
            } else {
                document.getElementById('bookmarkIcon').classList.add('fa-regular')
            }
        }

        // 댓글 달기
        function insertComment(dept, targetSeq) {

            if (document.getElementById("commentContents").value === "") {
                alert("내용 입력 후 등록 가능합니다.")
                document.getElementById("commentContents").focus()
                return
            }

            $.ajax({
                url: "/comment/insertComment",
                type: "post",
                dataType: "JSON",
                data: {
                    'boardSeq': document.getElementById("boardSeq").value,
                    'contents': document.getElementById("commentContents").value,
                    'dept': dept,
                    'targetSeq': targetSeq
                },
                success: function (json) {

                    alert(json.msg)
                    if (json.result === 1) {
                        getCommentList()

                        document.getElementById("commentContents").value = ""
                    }

                }

            });
        }

        // 댓글 리스트 가져오기
        function getCommentList() {
            $.ajax({
                url: "/comment/getCommentList",
                type: "post",
                dataType: "JSON",
                data: {
                    "boardSeq" : urlParams.get('bSeq'),
                },
                success: function (json) {
                    insertCommentList(json)
                }
            });
        }

        // 댓글 내용 입력
        function insertCommentList(json) {

            let commentList = $("#commentList")
            commentList.empty()

            json.forEach(function (data) {

                let tr = $("<tr>")
                let td = $("<td>")

                // hidden userId
                let userId = $("<input>")
                    .attr({
                        "type" : "hidden",
                        "name" : "commentUser",
                        "id" : "commentUser_" + data.commentSeq,
                        "value" : data.regId
                    })
                td.append(userId)

                // hidden commentSeq
                let commentSeq = $("<input>")
                    .attr({
                        "type" : "hidden",
                        "name" : "commentSeq",
                        "id" : "commentSeq_" + data.commentSeq,
                        "value" : data.commentSeq
                    })
                td.append(commentSeq)

                // nickname
                let strong = $("<strong>")
                    .text("작성자 : " + data.nickname);

                // regDt
                let span = $("<span>")
                    .css("float", "right")
                    .text("작성일 : " + data.regDt);
                td.append($("<p>")
                    .append(strong, span)
                )

                // 기본 상태
                let baseComment = $("<div>")
                    .attr("id", "baseComment_" + data.commentSeq)

                // 댓글 내용
                let readComment = $("<textarea>")
                    .prop("readonly", true) // 또는 false
                    .addClass("readTextarea")
                    .text(data.contents);
                baseComment.append(readComment)

                // 답글 쓰기
                let btnShowReRegArea = $("<button>")
                    .addClass("btn btn-link")
                    .attr("type", "button")
                    .text("답글 쓰기")
                    .click(function () {
                        showReRegArea(data.commentSeq)
                    })
                baseComment.append($("<div>")
                    .css("float", "left")
                    .append(btnShowReRegArea)
                )

                // 수정
                let btnShowEditArea = $("<button>")
                    .addClass("btn btn-light btn-sm")
                    .attr("type", "button")
                    .text("수정")
                    .click(function () {
                        showEditArea(data.commentSeq)
                    })

                // 삭제
                let btnDoCoDelete = $("<button>")
                    .addClass("btn btn-light btn-sm")
                    .attr("type", "button")
                    .text("삭제")
                    .click(function () {
                        doCoDelete(data.commentSeq)
                    })
                baseComment.append($("<div>")
                    .css("float", "right")
                    .append(btnShowEditArea, btnDoCoDelete)
                )

                td.append(baseComment)

                // 업데이트 상태
                let updateComment = $("<div>")
                    .attr("id", "updateComment_" + data.commentSeq)

                // 수정 가능한 댓글 내용
                let updateTextarea = $("<textarea>")
                    .attr({
                        "name" : "upComment",
                        "id" : "upComment_" + data.commentSeq,
                        "row": "3",
                    })
                    .css("width" , "95%")
                    .text(data.contents)

                updateComment.append($("<div>")
                        .addClass("commentArea mx-auto")
                        .append(updateTextarea),
                    $("<br>")
                )

                // 수정
                let btnDoCoEdit = $("<button>")
                    .addClass("btn btn-light btn-sm")
                    .attr("type", "button")
                    .text("수정")
                    .click(function () {
                        doCoEdit(data.commentSeq)
                    })

                // 취소
                let btnShowHideArea = $("<button>")
                    .addClass("btn btn-light btn-sm")
                    .attr("type", "button")
                    .text("취소")
                    .click(function () {
                        showHideArea(data.commentSeq)
                    })
                updateComment.append($("<div>")
                    .css("float", "right")
                    .append(btnDoCoEdit, btnShowHideArea)
                )

                td.append(updateComment)

                // 답글부
                let reCommentArea = $("<div>")
                    .addClass("commentArea mx-auto")
                    .css("text-align", "center")
                    .attr("id", "reCommentArea_" + data.commentSeq)

                // 답글 작성
                let reContent = $("<textarea>")
                    .attr({
                        "name" : "commentReContents",
                        "id" : "commentReContents_" + data.commentSeq
                    })
                    .css({
                        "width" : "95%",
                        "height" : "80px"
                    })
                reCommentArea.append(reContent)

                // 답글 등록 버튼
                let btnReComment = $("<button>")
                    .addClass("btn btn-primary")
                    .attr("type", "button")
                    .css("width", "95%")
                    .click(function (){
                        doReComment(data.commentSeq)
                    })
                    .text("답글 등록")
                reCommentArea.append(btnReComment)

                td.append(reCommentArea)
                tr.append(td)
                commentList.append(tr)
            })

            $("div[id^='updateComment']").hide();
            $("div[id^='updateRecomment']").hide();
            $("div[id^='reCommentArea']").hide();

            // textarea 요소 찾기
            let textarea = $('.readTextarea');

            // 각 textarea에 대해 실행
            textarea.each(function () {
                this.style.height = 'auto'; // 초기 높이 설정

                // 스크롤 높이 설정
                this.style.height = (this.scrollHeight) + 'px';
            });
        }


    </script>
</head>
<body>
<!-- 메모 상세보기 페이지 -->
<!-- 상단바 부분 -->
<%@include file="../header.jsp" %>
<br/>
<br/>

<!-- 내용 부분 -->
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">
    <div class="card-body">
        <div class="card mb-3">
            <!-- PK -->
            <input type="hidden" id="boardSeq">
            <input type="hidden" id="regId">

            <!-- 타이틀 출력 -->
            <div class="card-header">
                <div style="justify-content: space-between; display: flex;">
                    <!-- 카테고리 -->
                    <span class="badge rounded-pill bg-secondary" id="category" style="margin-bottom: 0.5%"></span>

                    <p></p>

                    <!-- 북마크 -->
                    <i class="fa-bookmark fa-2xl" id="bookmarkIcon" style="padding-top: 0.8%"></i>
                </div>

                <!-- 타이틀 -->
                <h3 style="font-family: 'Do Hyeon', sans-serif;">
                    <span type="text" id="title" disabled="">
                    </span>
                </h3>
            </div>

            <br/>

            <h6> &nbsp;&nbsp;
                <span  id="nickname"></span >  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                <span  id="regDt"></span >  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                <span  id="readCnt"></span >
            </h6>

            <!-- 이미지 출력 -->
            <div class="imageArea" style="width: 100%; padding-bottom: 10px;" id="img">
                <img src=""  width="100%"><br><br>
            </div>

            <!-- 내용 입력 영역 -->
            <div>
                <textarea class="readTextarea" id="contents" rows="16" readonly name="contents" style="width: 98%; border: None; padding: 10px"></textarea>
                <br>
            </div>
        </div>
    </div>

    <!-- 버튼 영역 -->
    <div class="ms-auto" style="padding: 0 20px;">
        <button id="btnEdit" type="button" class="btn btn-outline-dark">수정</button>
        <button id="btnList" type="button" class="btn btn-outline-dark">목록</button>
    </div>
    <br>
</div>

<%-- 댓글 영역 --%>
<div class="card mx-auto" style="width: 95%">
    <div class="card-body" >
        <h5 class="card-title" style="text-align: left"></h5>
        <div class="commentArea mx-auto" style="text-align: center">
            <textarea name="commentContents" id="commentContents" style="width: 95%; height: 80px"></textarea>
            <button class="btn btn-primary" type="button" id="btnComment" style="width: 95%" onclick="insertComment(0, 0)">등록</button>
        </div>
        <hr/>
        <table class="table table-hover">
            <tbody id="commentList">

            </tbody>
        </table>
    </div>
</div>

</body>
</html>