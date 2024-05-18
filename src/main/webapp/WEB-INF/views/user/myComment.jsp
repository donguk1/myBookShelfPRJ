<%@ page import="kopo.poly.dto.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>myComment</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style th:inline="css">
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

    <script src="https://kit.fontawesome.com/5a07bfcb19.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script th:inline="javascript">

        let currentPage

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getMyComment()

            $("#btnSend").on("click", function () {

                let f = document.getElementById("f");

                if (f.keyword.value === "") {
                    alert("검색어를 입력하세요");
                    f.keyword.focus();
                    return;
                }

            })

        })

        // 북마크 리스트 가져오기
        function getMyComment() {

            const urlParams = new URL(location.href).searchParams;
            currentPage = urlParams.get('page');

            // page 값이 null이거나 빈 문자열인 경우 0으로 초기화
            if (currentPage === null || currentPage.trim() === "") {
                currentPage = 1;
            } else {
                currentPage = parseInt(currentPage, 10);

                // page 값이 NaN이거나 음수인 경우 0으로 초기화
                if (isNaN(currentPage) || currentPage < 0) {
                    currentPage = 1;
                }
            }

            currentPage++

            $.ajax({
                url: "/comment/getMyComment",
                type: "POST",
                dataType: "JSON",
                data: {"page" : currentPage},
                success: function (json) {
                    console.log(json);

                    insertData(json.content)
                    pagination(json.totalPages)

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            });
        }

        function doDetail(seq) {
            location.href = "/board/boardInfo?bSeq=" + seq
        }

        // 리스트 보여주기
        function insertData(bList) {

            let list = $("#boardList")
            list.empty();

            bList.forEach(function (data) {

                console.log(data);

                let listOne = $("<tr>")
                    .addClass("listOne table-light")
                    .click(function () {
                        doDetail(data.boardSeq)
                    })

                let contents = $("<th>")
                    .text(data.contents)
                listOne.append(contents)

                let regDt = $("<th>")
                    .text(data.regDt)
                listOne.append(regDt)

                list.append(listOne)
            })

        }

        // 페이징
        function pagination(totalPages) {
            let list = $("#pagination");
            list.empty();

            let pagesPerGroup = 5;
            let startPage = ((currentPage -2) / pagesPerGroup) * pagesPerGroup + 1;
            let endPage = Math.min(startPage + pagesPerGroup - 1, totalPages);
            let page = currentPage - 1

            console.log("page : ", page);
            console.log("totalPages : ", totalPages);
            console.log("startPage : ", startPage);
            console.log("endPage : ", endPage);

            if (startPage > 1) {
                let prevFirstPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === 1);

                let prevFirstPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === 1 ? "#" : "myBoard?page=1")
                    .html("&laquo;");

                prevFirstPageItem.append(prevFirstPageLink);
                list.append(prevFirstPageItem);

                let prevPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === 1);

                let prevPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === 1 ? "#" : "myBoard?page=" + (startPage - 1))
                    .html("&lt;");

                prevPageItem.append(prevPageLink);
                list.append(prevPageItem);
            }

            for (let i = page - 3; i <= page + 3 && i <= endPage; i++) {

                if (i < 1) continue

                if (page > endPage) break

                let pageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("active", i === page);

                let pageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", i === page ? "#" : "myBoard?page=" + i)
                    .text(i);

                pageItem.append(pageLink);
                list.append(pageItem);
            }

            if (endPage < totalPages) {
                let nextPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === totalPages);

                let nextPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === totalPages ? "#" : "myBoard?page=" + (endPage + 1))
                    .html("&gt;");

                nextPageItem.append(nextPageLink);
                list.append(nextPageItem);

                let nextLastPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === totalPages);

                let nextLastPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === totalPages ? "#" : "myBoard?page=" + totalPages)
                    .html("&raquo;");

                nextLastPageItem.append(nextLastPageLink);
                list.append(nextLastPageItem);
            }
        }

    </script>
</head>
<body>
<!-- 메모 목록 페이지 -->

<!-- 상단바 부분 -->
<%@include file="../header.jsp" %>
<br>
<br>

<!-- 내용 부분 -->
<button id="btnBoardReg" class="btn btn-secondary my-2 my-sm-0" style="margin-left: 89%">글 쓰기</button>
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif; margin-top: 1%">
    <div class="card-body">
        <table class="table table-hover" style="text-align: center">
            <thead>
            <tr>
                <th scope="col" style="width: 80%">내용</th>
                <th scope="col" style="width: 20%">작성일</th>
            </tr>
            </thead>
            <tbody id="boardList">

            </tbody>
        </table>
    </div>

    <!-- 페이지네이션 영역-->
    <div class="center-pagination mx-auto">
        <ul class="pagination" id="pagination">
        </ul>
    </div>
    <br>
</div>


</body>
</html>