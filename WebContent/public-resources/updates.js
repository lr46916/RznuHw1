function updatePost(postId, text) {
	var path = "/rznu/posts";
	if(postId != -1){
		path = path + "/" + postId;
	}
	$.ajax({
		beforeSend: function(xhrObj) {
            xhrObj.setRequestHeader("Content-Type", "text/plain");
            xhrObj.setRequestHeader("Accept", "text/plain");
        },
        type: 'PUT',
        url: path,
        data: text,
        dataType: "text",
        success: function(result) {
            window.location = "/rznu/posts";
        },
        failure: function(fail) {
        	alert('FAIL');
        }
    })
}