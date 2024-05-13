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
        .dropdown-menu a:hover {background-color: #ddd;}

        .dropdown:hover .dropdown-menu {display: block;}

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

        let ssUserId;
        let bookmaker;

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getSsUserId()
            getBoardInfo()

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnEdit").on("click", function () {
                doEdit(); // 메모 수정하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnDelete").on("click", function () {
                doDelete(); // 메모 삭제하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnList").on("click", function () {
                location.href = "boardList"; // 메모 리스트 이동
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#bookmarkIcon").on("click", function () {
                bookmarkCheck()
                bookmark()
            })
        })

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
                location.href = "boardEditInfo?bSeq=" + document.getElementById("bSeq").value;

            } else if (ssUserId === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 메모만 수정 가능합니다.");

            }
        }

        // 본문 내용 가져오기
        function getBoardInfo() {

            const urlParams = new URL(location.href).searchParams;
            const bSeq = urlParams.get('bSeq');

            const data = {
                "bSeq" : bSeq,
                "type" : true
            }

            $.ajax({
                url: "/board/getBoardInfo",
                type: "post",
                dataType: "JSON",
                data: data,
                success: function (json) {

                    document.getElementById("bSeq").value = bSeq;
                    document.getElementById("regId").value = json.regId;
                    document.getElementById("category").textContent = json.category;
                    document.getElementById("title").textContent = json.title;
                    document.getElementById("nickname").innerText = "작성자 : " + json.nickname;
                    document.getElementById("regDt").innerText = "등록일 : " + json.regDt;
                    document.getElementById("readCnt").innerText = "조회수 : " + json.readCnt;
                    document.getElementById("contents").value = json.contents;

                    getBookmark()
                }

            });
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
                "boardSeq" : document.getElementById("bSeq").value,
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
                    }

                }

            });
        }

        // 북마크 여부 가져오기
        function getBookmark() {

            const data = {
                "boardSeq" : document.getElementById("bSeq").value
            }

            $.ajax({
                url: "/bookmark/getBookmark",
                type: "post",
                dataType: "JSON",
                data: data,
                success: function (json) {

                    console.log(json);

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
            <input type="hidden" id="bSeq">
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

</body>
</html>