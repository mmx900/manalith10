<%@ page contentType = "text/html; charset=utf-8" pageEncoding= "UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/tem9p" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/manalith" prefix="manalith" %>

<html>
<head>
	<meta name="ROBOTS" content="NOINDEX, NOFOLLOW">
	<t:fckeditor jsPath="${fckeditor_path}" container="contents" lang="ko" theme="default" replace="true" enableXHTML="true" imageBrowser="false" width="800" functionalize="fck" />
</head>
<body>
${id} 블로그에 새로운 엔트리를 생성합니다.

<html:form action="/blog/article?id=${id}&method=${targetAction}" enctype="multipart/form-data" focus="title">
	<html:hidden property="blogOwnerId" value="${param.id}" />
	<html:hidden property="articleId" value="${param.articleId}" />
	<html:hidden property="author" value="${userId}" />
	제목 : <html:text property="title" value="${article.title}" /><br />
	분류 : <input type="text" id="category" name="category" value="${article.category}" />

	<select
		onChange="document.getElementById('category').value = this.value">
		<option value="">새로 만들기</option>
		<c:if test="${! empty categories}"><option value="">--</option></c:if>
		<c:forEach var="category" items="${categories}">
		<option>${category}</option>
		</c:forEach>
	</select>
	<br />
	날짜 : <html:text property="date" value="${articleDate}" /><br />
	내용 : <html:textarea property="contents" value="${article.contents}"  cols="50" rows="20" /><br />
	포맷 : <html:select property="format" value="${article.format}">
		<html:option value="text" />
		<html:option value="html" />
	</html:select>
	<input type="button" onClick="fck();this.style.display='none';alert('에디터 사용시에는 포맷을 반드시 HTML로 설정해 주세요.')" value="HTML 에디터 사용" />
	
	<p>이 엔트리에 첨부되는 파일들 :
	<br /><c:forEach var="file" items="${article.files}">
			<html:hidden property="files" value="${file.id}" />
			<a href="${uploadPath}/${file.name}" target="_blank">${file.name}</a>
			<manalith:isImage test="${file.name}">
				<script type="text/javascript">
				function attach${file.id}(){
					var tag = "<img src='${uploadPath}/${file.name}' alt='file' />";
					if(oFCKeditor == ""){
						var c = document.getElementById('contents');
						if(! c) c = document.getElementsByName('contents')[0];
						c.value += tag ;
					}else{
						var f = FCKeditorAPI.GetInstance('contents'); 
						f.SetHTML(f.GetHTML() + tag); 
					}
				}
				</script>
				<a href="#" onclick="javascript:attach${file.id}()">[본문에 삽입]</a>
			</manalith:isImage>
			<a href="javascript:uploadCancel('${file.id}','${file.name}');">[삭제]</a>
	<br />
	</c:forEach>
	<script language="JavaScript">
	if(document.articleForm.files){
		for(i=0;i<document.articleForm.files.length;i++){
			document.articleForm.files[i].checked = true;
		}
	}
	
	function upload(){
		document.forms[0].action = "article.do?id=${id}&articleId=${param.articleId}&method=upload&mode=${targetAction}";
		//document.forms[0].enctype = "multipart/form-data";
		document.forms[0].submit();
	}
	
	function uploadCancel(id, name){
		document.forms[0].action = "article.do?id=${id}&articleId=${param.articleId}&method=uploadCancel&mode=${targetAction}&fileId=" + id + "&fileName=" + name;
		//document.forms[0].enctype = "multipart/form-data";
		document.forms[0].submit();
	}
	</script>
	(파일을 업로드하고 사용하지 않았을 경우 목록에 나타날 수 있습니다.)
	<br><html:file property="tmpFile" value="" />
	<input type="button" value="추가" onclick="upload()" />
	
	<p><html:submit value="확인" />
</html:form>
</body>
</html>