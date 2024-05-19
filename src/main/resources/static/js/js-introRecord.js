let choiceDay = ""; // 선택일
let containerSeq = 1;
let editContainerSeq = 1;
let today = new Date();
$(document).ready(function () {


    // + 버튼 클릭했을때
    $("#btnPlus").on("click", function () {
        toggleRegisterBtns(); // 등록 버튼 토글 함수 실행
    })

    // + 버튼 클릭 시 등록버튼 보이기/숨기기 토글
    function toggleRegisterBtns() {
        $("#registerBtns").toggle();
    }

    // 일정 버튼 눌렀을때 일정 추가 모달 열기
    $("#registerBookShelf").on("click", function () {
        $("#bookShelfModal").modal("show");
    });

    // 일정 추가 모달에서 저장 버튼 눌렀을때
    $("#modalBtnSc").on("click", function () {
        insertBookShelf(); // 일정 추가 함수 실행
    })

    // 일정 수정
    $("#editBtnSc").on("click", function () {
        editBookShelf()
    })



    // 오늘 날짜를 자동으로 선택하는 블록 추가
    let todayCell = getTodayCell(); // 오늘 날짜에 해당하는 셀을 가져오는 함수
    if (todayCell) {
        calendarChoiceDay(todayCell);
    }


})

/* 도서 추가 */
function insertBookShelf() {

    let bookShelfTitle = document.getElementById("bookShelfTitle").value;


    // 요청 데이터를 JavaScript 객체로 구성
    const requestData = {
        choiceDay: choiceDay,
        title: bookShelfTitle,

    };

    console.log(requestData);


    $.ajax({
        url: "/bookShelf/insertBook",
        type: "post",
        dataType: "JSON",
        data: requestData,
        success: function (json) {
            if (json.result === 1) {
                alert(json.msg);
                location.reload();
            } else {
                alert(json.msg);
            }

        }
    })
}

/* 도서 조회 */
function getBookShelfList(choiceDay) {

    $.ajax({
        url: "/bookShelf/getMyBookList",
        type: "post",
        data: {date: choiceDay},
        dataType: "JSON",
        success: function (json) {
            let bookShelfDiv = $("#bookShelf");
            bookShelfDiv.empty();

            console.log("choiceDay : " + choiceDay);
            console.log(json);

            json.forEach(function (bookShelf) {

                let bookShelfItem = $("<div class=\"listOne\" id='listOneSc'>")
                    .addClass("bookShelfItem");

                let etcBtn = $("<div class='btn-group' role='group'>")
                    .addClass("editButtons");
                etcBtn.addClass("float-end");

                let editBtn = $("<i type='button' class=\"fa-solid fa-ellipsis-vertical fa-lg\" data-bs-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></i>")
                let dropdownMenu = $("<div class='dropdown-menu' aria-labelledby='btnGroupDrop3'>");
                let editLink = $("<div class='dropdown-item'>").text("수정");
                let deleteLink = $("<div class='dropdown-item'>").text("삭제");
                let cancelLink = $("<div class='dropdown-item'>").text("취소");

                // Edit 링크 클릭 이벤트
                editLink.on("click", function (event) {
                    event.preventDefault();
                    $("#bookShelfEditModal").modal("show");
                    setBookShelfInfoInModal(bookShelf);
                });

                // Delete 링크 클릭 이벤트
                deleteLink.on("click", function (event) {
                    event.preventDefault();
                    deleteBookShelf(bookShelf);
                });

                // Cancel 링크 클릭 이벤트
                cancelLink.on("click", function (event) {
                    event.preventDefault();
                    cancelEditButtons();
                });

                function cancelEditButtons() {
                    // 수정, 삭제, 취소 버튼을 숨김.
                    $(".editButtons .btn-group").toggle(); // editButtons 클래스 안의 btn-group 요소들을 토글
                }

                dropdownMenu.append(editLink, deleteLink, cancelLink);
                etcBtn.append(editBtn, dropdownMenu);

                let bookShelfTime = $("<strong>")
                    .addClass("bookShelfTime")
                    .text(bookShelf.time);

                let bookShelfTitle = $("<div>")
                    .addClass("bookShelfTitle")
                    .text(bookShelf.title);

                let bookShelfCon = $("<div>")
                    .addClass("bookShelfCon")
                    .text(bookShelf.contents);

                bookShelfItem.append(etcBtn, bookShelfTime, bookShelfTitle, bookShelfCon);
                bookShelfDiv.append(bookShelfItem);
            });
        },
        error: function (xhr, status, error) {
            alert("일정 정보를 불러오는 데 실패했습니다.");
            console.error("에러: " + error);
        }
    });
}


/* 도서 여부 확인 */
function checkBookShelf(doMonth , callback) {

    const Month = doYYMonth(doMonth);
    const nextMonth = nextYYMonth(doMonth)

    $.ajax({
        url: "/bookShelf/checkMyBook",
        type: "post",
        dataType: "JSON",
        data:  {doMonth : Month,
            nextMonth: nextMonth},
        success: function (json) {

            console.log(json)


            callback(json);
        }
    })
}

/* 일정 수정창 팝업시 값 입력 */
function setBookShelfInfoInModal(bookShelf) {
    console.log("Setting bookShelf info in modal: ", bookShelf);
    $("#bookShelfEditTitle").val(bookShelf.title);
    $("#bookShelfEditTime").val(bookShelf.time);
    $("#bookShelfEditContents").val(bookShelf.contents);
    $("#bookShelfSeq").val(bookShelf.bookShelfSeq);
}

/* 일정 수정 */
function editBookShelf() {
    // 선택한 일정을 수정하는 동작을 추가합니다.
    // 예: 수정할 폼을 띄우거나, Ajax 요청을 보내서 서버에 수정 요청을 보냅니다.

    let requestData = {
        bookShelfSeq : document.getElementById("bookShelfSeq").value,
        title : document.getElementById("bookShelfEditTitle").value,
        contents : document.getElementById("bookShelfEditContents").value,
        time : document.getElementById("bookShelfEditTime").value
    };

    $.ajax({
        url: "/bookShelf/bookShelfUpdate",
        type: "post",
        dataType: "JSON",
        data: requestData,
        success: function (json) {
            alert(json.msg);
            location.reload();

        }
    })
}

/* 일정 삭제 */
function deleteBookShelf(bookShelf) {
    // 선택한 일정을 삭제하는 동작을 추가합니다.
    // 예: Ajax 요청을 보내서 서버에 삭제 요청을 보냅니다.

    console.log(bookShelf)

    if (confirm("삭제 하시겠습니까?")) {
        $.ajax({
            url: "/bookShelf/bookShelfDelete",
            type: "post",
            dataType: "JSON",
            data: bookShelf,
            success: function (json) {
                alert(json.msg);
                location.reload();

            }
        })
    }
}




// 개수
$(document).ready(function(){
    $('.btn-number').click(function(e){
        e.preventDefault();

        let fieldName = $(this).attr('data-field');
        let type      = $(this).attr('data-type'); /* 버튼 타입 */
        let input = $("input[name='"+fieldName+"']"); /* 입력 값 */
        let currentVal = parseInt(input.val()); /* 최신 값 정수 변환 */

        if (!isNaN(currentVal)) {
            if(type == 'minus') { /* 마이너스 버튼 */
                if(currentVal<1){ /* 값이 음수면 */
                    input.val(currentVal); /* 더 이상 빼지 않고 0 출력 */
                } else {
                    input.val(currentVal - 1); /* 음수가 아니라면 1씩 줄임 */
                }
            } else if(type == 'plus') { /* 플러스 버튼 */
                input.val(currentVal + 1); /* 현재 값에서 1씩 늘림 */
            }
        } else {
            input.val(0);
        }
    });
});


/* --------------------달력--------------------- */

// 오늘 날짜에 해당하는 셀을 가져오는 함수
function getTodayCell() {

    let day = today.getDate();

    // 셀을 순회하면서 오늘 날짜에 해당하는 셀을 찾음
    let cells = document.querySelectorAll(".scriptCalendar > tbody td");
    for (let i = 0; i < cells.length; i++) {
        if (cells[i].innerText == day && cells[i].style.color !== "#A9A9A9") {
            return cells[i];
        }
    }

    return null;
}


/* 현재 년월 가져오기 */
function doYYMonth(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 포맷팅
    const day = 1

    return `${year}-${month}-${day}`;
}

/* 다음 년월 가져오기 */
function nextYYMonth(date) {
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 포맷팅

    if (month === '12') {
        month = '01';
        year += 1;
    } else {
        month = String(date.getMonth() + 2).padStart(2, '0');
    }

    const day = 1

    return `${year}-${month}-${day}`;
}

document.addEventListener("DOMContentLoaded", function () {
    buildCalendar();
});

let date = new Date();  // @param 전역 변수, today의 Date를 세어주는 역할

/**
 * @brief   이전달 버튼 클릭
 */
function prevCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    buildCalendar();    // @param 전월 캘린더 출력 요청
    console.log("prev")
}

/**
 * @brief   다음달 버튼 클릭
 */
function nextCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    buildCalendar();    // @param 명월 캘린더 출력 요청
    console.log("next")
}

/**
 * @brief   캘린더 오픈
 * @details 날짜 값을 받아 캘린더 폼을 생성하고, 날짜값을 채워넣는다.
 */
function buildCalendar() {

    let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);

    let tbCalendar = document.querySelector(".scriptCalendar > tbody");

    document.getElementById("calYear").innerText = today.getFullYear(); // @param YYYY월
    document.getElementById("calMonth").innerText = autoLeftPad((today.getMonth() + 1), 2); // @param MM월

    // @details 이전 캘린더의 출력결과가 남아있다면, 이전 캘린더를 삭제한다.
    while (tbCalendar.rows.length > 0) {
        tbCalendar.deleteRow(tbCalendar.rows.length - 1);
    }

    // @param 첫번째 개행
    let row = tbCalendar.insertRow();

    // @param 날짜가 표기될 열의 증가값
    let dom = 1;

    // @details 시작일의 요일값( doMonth.getDay() ) + 해당월의 전체일( lastDate.getDate())을  더해준 값에서
    // 7로 나눈값을 올림( Math.ceil() )하고 다시 시작일의 요일값( doMonth.getDay() )을 빼준다.
    let daysLength = (Math.ceil((doMonth.getDay() + lastDate.getDate()) / 7) * 7) - doMonth.getDay();

    checkBookShelf(doMonth, function (sList) {
        sList.forEach(function (bookShelf) {

            const bookShelfDate = new Date(bookShelf.regDt);
            const bookShelfDay = bookShelfDate.getDate();
            console.log("bookShelfDate : " + bookShelfDate)
            console.log("bookShelfDay : " + bookShelfDay)

            // 캘린더에 해당 일정을 표시하는 로직 추가
            const cellIndex = bookShelfDay + doMonth.getDay() - 1;
            const rowNumber = Math.floor(cellIndex / 7);
            const columnIndex = cellIndex % 7;

            if (rowNumber < tbCalendar.rows.length) {
                const cell = tbCalendar.rows[rowNumber].cells[columnIndex];
                const circleSc = document.createElement('div');
                circleSc.classList.add('circleSc');
                cell.appendChild(circleSc);
            }
        });

    });

    // @param 달력 출력
    // @details 시작값은 1일을 직접 지정하고 요일값( doMonth.getDay() )를 빼서 마이너스( - )로 for문을 시작한다.
    for (let day = 1 - doMonth.getDay(); daysLength >= day; day++) {

        let column = row.insertCell();

        // @param 평일( 전월일과 익월일의 데이터 제외 )
        if (Math.sign(day) == 1 && lastDate.getDate() >= day) {

            // @param 평일 날짜 데이터 삽입
            column.innerText = autoLeftPad(day, 2);

            // @param 일요일인 경우
            if (dom % 7 == 1) {
                column.style.color = "#FF4D4D";
            }

            // @param 토요일인 경우
            if (dom % 7 == 0) {
                column.style.color = "#4D4DFF";
                row = tbCalendar.insertRow();   // @param 토요일이 지나면 다시 가로 행을 한줄 추가한다.
            }
        }

        // @param 평일 전월일과 익월일의 데이터 날짜변경
        else {
            let exceptDay = new Date(doMonth.getFullYear(), doMonth.getMonth(), day);
            column.innerText = autoLeftPad(exceptDay.getDate(), 2);
            column.style.color = "#A9A9A9";
        }

        // @brief   전월, 명월 음영처리
        // @details 현재년과 선택 년도가 같은경우
        if (today.getFullYear() == date.getFullYear()) {

            // @details 현재월과 선택월이 같은경우
            if (today.getMonth() == date.getMonth()) {

                // @details 현재일보다 이전인 경우이면서 현재월에 포함되는 일인경우
                if (date.getDate() > day && Math.sign(day) == 1) {
                    column.style.backgroundColor = "#FFFFFF";
                    column.style.cursor = "pointer";
                    column.onclick = function () {
                        calendarChoiceDay(this);
                    }
                }

                // @details 현재일보다 이후이면서 현재월에 포함되는 일인경우
                else if (date.getDate() < day && lastDate.getDate() >= day) {
                    column.style.backgroundColor = "#FFFFFF";
                    column.style.cursor = "pointer";
                    column.onclick = function () {
                        calendarChoiceDay(this);
                    }
                }

                // @details 현재일(오늘)인 경우
                else if (date.getDate() == day) {
                    // column.style.backgroundColor = "#dfe8f1";
                    column.style.fontWeight = "bold";
                    column.style.borderRadius = "50%";
                    column.style.cursor = "pointer";
                    column.onclick = function () {
                        calendarChoiceDay(this);
                    }
                }

                // @details 현재월보다 이전인경우
            } else if (today.getMonth() < date.getMonth()) {
                if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                    column.style.backgroundColor = "#FFFFFF";
                    column.style.cursor = "pointer";
                    column.onclick = function () {
                        calendarChoiceDay(this);
                    }
                }
            }

            // @details 현재월보다 이후인경우
            else {
                if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                    column.style.backgroundColor = "#FFFFFF";
                    column.style.cursor = "pointer";
                    column.onclick = function () {
                        calendarChoiceDay(this);
                    }
                }
            }
        }

        // @details 선택한년도가 현재년도보다 작은경우
        else if (today.getFullYear() < date.getFullYear()) {
            if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                column.style.backgroundColor = "#FFFFFF";
                column.style.cursor = "pointer";
                column.onclick = function () {
                    calendarChoiceDay(this);
                }
            }
        }

        // @details 선택한년도가 현재년도보다 큰경우
        else {
            if (Math.sign(day) == 1 && day <= lastDate.getDate()) {
                column.style.backgroundColor = "#FFFFFF";
                column.style.cursor = "pointer";
                column.onclick = function () {
                    calendarChoiceDay(this);
                }
            }
        }
        dom++;
    }
}

/**
 * @brief   날짜 선택
 * @details 사용자가 선택한 날짜에 표시를 남긴다.
 */
function calendarChoiceDay(column) {

    // @param 기존 선택일이 존재하는 경우 기존 선택일의 표시형식을 초기화 한다.
    if (document.getElementsByClassName("choiceDay")[0]) {
        // document.getElementsByClassName("choiceDay")[0].style.backgroundColor = "#FFFFFF";
        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");
    }

    // @param 선택일 클래스명 변경
    column.classList.add("choiceDay");
    choiceDay = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + column.innerText;

    // $("#btnradio4").click(); // 날짜 클릭했을때 일정이 기본으로 뜨게
    getBookShelfList(choiceDay);


}

/**
 * @brief   숫자 두자릿수( 00 ) 변경
 * @details 자릿수가 한자리인 ( 1, 2, 3등 )의 값을 10, 11, 12등과 같은 두자리수 형식으로 맞추기위해 0을 붙인다.
 * @param   num     앞에 0을 붙일 숫자 값
 * @param   digit   글자의 자릿수를 지정 ( 2자릿수인 경우 00, 3자릿수인 경우 000 … )
 */
function autoLeftPad(num, digit) {
    if (String(num).length < digit) {
        num = new Array(digit - String(num).length + 1).join("0") + num;
    }
    return num;
}