<%@ page import="kopo.poly.dto.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<BoardDTO> bList = (List<BoardDTO>) request.getAttribute("bList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Board</title>
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

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getBoardList()

            $("#btnSend").on("click", function () {

                let f = document.getElementById("f");

                if (f.keyword.value === "") {
                    alert("검색어를 입력하세요");
                    f.keyword.focus();
                    return;
                }

            })

            $("#btnBoardReg").on("click", function () {
                location.href = "boardReg";
            })



        })

        function getBoardList() {

            const urlParams = new URL(location.href).searchParams;
            const page = urlParams.get('page');

            $.ajax({
                url: "/board/getBoardListPage",
                type: "POST",
                dataType: "JSON",
                data: {page: page},
                success: function (json) {
                    console.log(json);

                    insertData(json.content)
                    // pageNation(json.currentPage, json.totalPages)

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            });
        }

        function doDetail(seq) {
            location.href = "boardInfo?bSeq=" + seq
        }

        // 리스트 보여주기
        function insertData(bList) {

            let list = $("#boardList")
            list.empty();

            bList.forEach(function (data) {

                // console.log(data);

                let listOne = $("<tr>")
                    .addClass("listOne table-light")
                    .click(function () {
                        doDetail(data.boardSeq)
                    })

                let category = $("<th>")
                    // .addClass("badge rounded-pill bg-secondary")
                    .text(data.category)
                listOne.append(category)

                let title = $("<th>")
                    .text(data.title)

                if (data.fileYn === "Y") {
                    let fileYn = $("<i>")
                        .addClass("fa-solid fa-image")
                        // .css({"color": "#27ADFB"})
                    title.append(fileYn)
                }
                listOne.append(title)

                let commentCnt = $("<th>")
                    .text(data.commentCnt)
                listOne.append(commentCnt)

                let nickname = $("<th>")
                    .text(data.nickname)
                listOne.append(nickname)

                let regDt = $("<th>")
                    .text(data.regDt)
                listOne.append(regDt)

                let readCnt = $("<th>")
                    .text(data.readCnt)
                listOne.append(readCnt)

                list.append(listOne)
            })

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
                <th scope="col" style="width: 10%">카테고리</th>
                <th scope="col" style="width: 40%">제목</th>
                <th scope="col" style="width: 10%">댓글수</th>
                <th scope="col" style="width: 10%">작성자</th>
                <th scope="col" style="width: 20%">작성일</th>
                <th scope="col" style="width: 10%">조회수</th>
            </tr>
            </thead>
<%--            <%for(BoardDTO dto : bList) {%>--%>
<%--                <tbody id="boardList" >--%>
<%--                    <tr onclick="doDetail(<%=dto.boardSeq()%>)">--%>
<%--                        <th><%=dto.category()%></th>--%>
<%--                        <th><%=dto.title()%>--%>
<%--                            <%if (Objects.equals(dto.fileYn(), "Y")) { %>--%>
<%--                            <i class="fa-solid fa-image"></i>--%>
<%--                            <% } %>--%>
<%--                        </th>--%>
<%--                        <th><%=dto.commentCnt()%></th>--%>
<%--                        <th><%=dto.nickname()%></th>--%>
<%--                        <th><%=dto.regDt()%></th>--%>
<%--                        <th><%=dto.readCnt()%></th>--%>
<%--                    </tr>--%>
<%--                </tbody>--%>
<%--            <%}%>--%>

            <tbody id="boardList">
            </tbody>
        </table>
    </div>

    <!-- 페이지네이션 영역-->
    <div class="center-pagination mx-auto">
        <ul class="pagination">
<%--            <%--%>
<%--                int currentPage = (int) request.getAttribute("currentPage");--%>
<%--                int totalPages = (int) request.getAttribute("totalPages");--%>
<%--                // 각 그룹당 표시할 페이지 수 정의--%>
<%--                int pagesPerGroup = 5;--%>
<%--                int startPage = ((currentPage - 1) / pagesPerGroup) * pagesPerGroup + 1;--%>
<%--                int endPage = Math.min(startPage + pagesPerGroup - 1, totalPages);--%>
<%--            %>--%>
<%--            <% if (startPage > 1) { %>--%>
<%--            <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">--%>
<%--                <a class="page-link"--%>
<%--                   href="<%= currentPage == 1 ? "#" : "/board/boardList?page=1" %>">&laquo;</a>--%>
<%--            </li>--%>
<%--            <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">--%>
<%--                <a class="page-link"--%>
<%--                   href="<%= currentPage == 1 ? "#" : "/board/boardList?page=" + (startPage - 1) %>">&lt;</a>--%>
<%--            </li>--%>
<%--            <% } %>--%>
<%--            <% for (int i = startPage; i <= endPage; i++) { %>--%>
<%--            <li class="page-item <%= i == currentPage ? "active" : "" %>">--%>
<%--                <a class="page-link"--%>
<%--                   href="<%= i == currentPage ? "#" : "/board/boardList?page=" + i %>"><%= i %>--%>
<%--                </a>--%>
<%--            </li>--%>
<%--            <% } %>--%>
<%--            <% if (endPage < totalPages) { %>--%>
<%--            <li class="page-item <%= currentPage == totalPages ? "disabled" : "" %>">--%>
<%--                <a class="page-link"--%>
<%--                   href="<%= currentPage == totalPages ? "#" : "/board/boardList?page=" + (endPage + 1)  %>">&gt;</a>--%>
<%--            </li>--%>
<%--            <li class="page-item <%= currentPage == totalPages ? "disabled" : "" %>">--%>
<%--                <a class="page-link"--%>
<%--                   href="<%= currentPage == totalPages ? "#" : "/board/boardList?page=" + (totalPages) %>">&raquo;</a>--%>
<%--            </li>--%>
<%--            <% } %>--%>
        </ul>
    </div>

    <!-- 검색 버튼 영역 -->
    <form class="form-inline my-2 my-lg-0 mx-auto" id="f">
        <div class="input-group mb-3">
            <select name="type" id="type" style="margin-right: 3%">
                <option selected value="all">전체</option>
                <option value="기타">기타</option>
                <option value="철학">철학</option>
                <option value="종교">종교</option>
                <option value="과학">과학</option>
                <option value="예술">예술</option>
                <option value="언어">언어</option>
                <option value="문학">문학</option>
                <option value="역사">역사</option>
            </select>
            <input class="form-control mr-sm-2" type="search" placeholder="Search" name="keyword">
            <button type="submit" class="btn btn-secondary my-2 my-sm-0" id="btnSend" onclick="getSelectMemoList">검색</button>
        </div>
    </form>
    <br>
</div>


</body>
</html>