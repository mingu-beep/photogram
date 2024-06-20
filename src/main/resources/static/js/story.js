/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

let page = 0;

// (1) 스토리 로드하기
function storyLoad() {
    $.ajax({
        type:"get",
        url:`/api/image?page=${page}`,
        datatype:"json"
    }).done(res => {
        let images = res.details ;
        console.log(images);
        res.details.forEach((image) => {
            let item = getStoryItem(image);
            $("#storyList").append(item);
        });
    }).fail(err => {
        console.log("fail", err);
    });
}

storyLoad();

function getStoryItem(image) {

    let item = `
        <div class="story-list__item">
            <div class="sl__item__header">
                <div>
                    <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                        onerror="this.src='/images/person.jpeg'" />
                </div>
                <div>${image.user.username}</div>
            </div>

            <div class="sl__item__img">
                <img src="/upload/${image.postImageUrl}" />
            </div>

            <div class="sl__item__contents">
                <div class="sl__item__contents__icon">
                    <button>`;

    if(image.likeState) {
        item +=  `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
    } else {
        item +=  `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
    }

    item += `
                    </button>
                </div>

                <span class="like"><b id="storyLikeCount-${image.id}}">${image.likeCount} </b>likes</span>

                <div class="sl__item__contents__content">
                    <p>${image.caption}</p>
                </div>

                <div id="storyCommentList-${image.id}">

                    <div class="sl__item__contents__comment" id="storyCommentItem-${image.id}"">
                        <p>
                            <b>Lovely :</b> 부럽습니다.
                        </p>

                        <button>
                            <i class="fas fa-times"></i>
                        </button>

                    </div>

                </div>

                <div class="sl__item__input">
                    <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
                    <button type="button" onClick="addComment()">게시</button>
                </div>

            </div>
        </div>
        `
    return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
// document.height = window.scrollTop + window.height;
//    $(window).scrollTop();
//    $(document).height();
//    $(window).height();

    let checkScroll = $(document).height() - $(window).height() - $(window).scrollTop();

    if(checkScroll < 1 && checkScroll > -1) {
        page++;
        storyLoad();
    }
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) { // 좋아요
	    $.ajax({
	        type:"post",
	        url:`/api/image/${imageId}/likes`,
	        datatype:"json"
	    }).done(res => {
            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");

	    }).fail(err => {
	    });
	} else { // 구독 취소

	    $.ajax({
            type:"delete",
            url:`/api/image/${imageId}/likes`,
            datatype:"json"
        }).done(res => {

            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");

        }).fail(err => {
        });

	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







