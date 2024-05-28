function pagination(totalPages, currentPage) {
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
                    .attr("href", page === 1 ? "#" : "/board/boardList?page=1")
                    .html("&laquo;");

                prevFirstPageItem.append(prevFirstPageLink);
                list.append(prevFirstPageItem);

                let prevPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === 1);

                let prevPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === 1 ? "#" : "/board/boardList?page=" + (startPage - 1))
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
                    .attr("href", i === page ? "#" : "/board/boardList?page=" + i)
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
                    .attr("href", page === totalPages ? "#" : "/board/boardList?page=" + (endPage + 1))
                    .html("&gt;");

                nextPageItem.append(nextPageLink);
                list.append(nextPageItem);

                let nextLastPageItem = $("<li>")
                    .addClass("page-item")
                    .toggleClass("disabled", page === totalPages);

                let nextLastPageLink = $("<a>")
                    .addClass("page-link")
                    .attr("href", page === totalPages ? "#" : "/board/boardList?page=" + totalPages)
                    .html("&raquo;");

                nextLastPageItem.append(nextLastPageLink);
                list.append(nextLastPageItem);
            }
        }