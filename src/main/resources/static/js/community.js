function postCommunity() {
    /**
     * 提交回复
     */
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2traget(questionId, 1, content);
    //console.log(questionId);
    //console.log(content);
}

function comment2traget(targetId, type, content) {
    if (!content) {
        Alert("不能回复空内容!!!");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://localhost:8887/loginHtml");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            //console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2traget(commentId, 2, content);
}

function collapseComments(e) {
    /**
     * 展开二级评论
     */
        // debugger;
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评级的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse != null) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论状态
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {

            //获取数据
            $.getJSON("/comment/" + id, function (data) {
                //console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var medialeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": "../image/"+comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": DateUtil.formatDateTime(DateUtil.date(comment.gmtCreate),date_formate.normDatePattern)
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(medialeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
            });
            //展开二级评论状态
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }
    }
    //console.log(id);
}

function selectTag(e) {
    var value =e.getAttribute("data-tag");
    var previous=$("#tag").val();
   if (previous){
       if (previous.indexOf(value)<0){
           $("#tag").val(previous+","+value);
       }
   }else
   {
       $("#tag").val(value);
   }
}

function showSelectTag() {
    $("#select-tag").attr("style","display:block;");
}

function hideSelectTag() {
    //$("#select-tag").attr("style","display:none;");
}