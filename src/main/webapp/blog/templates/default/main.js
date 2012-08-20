/* ?? ??? ?? ????, ?? ???, ?? ??, ???? ??*/
function show_recent_comments(){
}
function show_recent_trackbacks(){
}
function show_categories(){
}

/* ? Article ?? comment, trackback, file ???? ?? */
function show_comments(index){
	var c = document.getElementById("comments" + index);
	var t = document.getElementById("trackbacks" + index);
	var f = document.getElementById("files" + index);
	if(c.style.display == "block"){
		c.style.display="none";
	}else{
		c.style.display="block";
		if(t.style.display == "block"){
			t.style.display="none";
		}
		if(f.style.display == "block"){
			f.style.display="none";
		}
	}
}
function show_trackbacks(index){
	var c = document.getElementById("comments" + index);
	var t = document.getElementById("trackbacks" + index);
	var f = document.getElementById("files" + index);
	if(t.style.display == "block"){
		t.style.display="none";
	}else{
		t.style.display="block";
		if(c.style.display == "block"){
			c.style.display="none";
		}
		if(f.style.display == "block"){
			f.style.display="none";
		}
	}
}

function show_files(index){
	var c = document.getElementById("comments" + index);
	var t = document.getElementById("trackbacks" + index);
	var f = document.getElementById("files" + index);
	if(f.style.display == "block"){
		f.style.display="none";
	}else{
		f.style.display="block";
		if(c.style.display == "block"){
			c.style.display="none";
		}
		if(t.style.display == "block"){
			t.style.display="none";
		}
	}
}