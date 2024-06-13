// (1) 회원정보 수정
function update(id, event) {

    // submit 기본 이벤트 disable
    event.preventDefault();

    // form 데이터 추출
    let data = $("#profileUpdate").serialize();
    console.log("form data : " + data);

    $.ajax({
        type: "put",
        url: `/api/user/${id}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => { // 성공 로직 (HttpStatusCode == 200)
        alert("회원 정보가 성공적으로 수정되었습니다.")
        location.href = `/user/${id}`;
    }).fail(error => { // 실패 로직 (HttpStatusCode != 200)
        alert("회원 정보가 수정되지 않았습니다. 원인 : " + error.responseJson.data)
    });

}