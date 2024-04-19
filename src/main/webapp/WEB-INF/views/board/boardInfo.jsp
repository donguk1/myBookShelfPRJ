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
    </style>

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">

        let ssUserId;

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getSsUserId();
            getBoardInfo();

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
        })

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

        //삭제하기
        function doDelete() {

            if (ssUserId === $("#regId")) {

                if (confirm("작성한 메모를 삭제하시겠습니까?")) {
                    location.href = "/memo/memoDelete?num=" + num;
                }

            } else if (ssUserId === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 메모만 삭제 가능합니다.");
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

            console.log(data);

            $.ajax({
                url: "/board/getBoardInfo",
                type: "post",
                dataType: "JSON",
                data: data,
                success: function (json) {

                    console.log(json);
                    document.getElementById("bSeq").value = bSeq;
                    document.getElementById("regId").value = json.regId;
                    document.getElementById("category").value = json.category;
                    document.getElementById("title").value = json.title;
                    document.getElementById("nickname").innerText = "작성자 : " + json.nickname;
                    document.getElementById("regDt").innerText = "등록일 : " + json.regDt;
                    document.getElementById("readCnt").innerText = "조회수 : " + json.readCnt;
                    document.getElementById("contents").value = json.contents;
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
                <!-- 카테고리 -->
                <span class="badge rounded-pill bg-secondary" id="category"></span>

                <!-- 타이틀 -->
                <h3 style="font-family: 'Do Hyeon', sans-serif;">
                    <input class="form-control me-sm-2" type="text" id="title" disabled="">
                </h3>
            </div>

            <br/>

            <h6> &nbsp;&nbsp;
                <span  id="nickname"></span >  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                <i class="fa-solid fa-pencil"></i>
                <span  id="regDt"></span >  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                <i class="fa-regular fa-eye"></i>
                <span  id="readCnt"></span >
            </h6>

            <!-- 이미지 출력 -->
            <div class="imageArea" style="width: 100%; padding-bottom: 10px;" id="img">
                <img src=""  width="100%"><br><br>
            </div>

            <!-- 내용 입력 영역 -->
            <div class="form-group">
                <textarea class="form-control mx-auto" id="contents" rows="14" readonly name="contents" style="width: 98%"></textarea>
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