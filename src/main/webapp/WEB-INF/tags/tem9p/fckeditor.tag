<%@ tag body-content="scriptless" pageEncoding="UTF-8" %>
<%@ attribute name="jsPath" required="true" %>
<%@ attribute name="container" required="false" %>
<%@ attribute name="lang" required="false" %>
<%@ attribute name="theme" required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="replace" required="false" %>
<%@ attribute name="enableXHTML" required="false" %>
<%@ attribute name="imageBrowser" required="false" %>
<%@ attribute name="functionalize" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:doBody />
<script type="text/javascript" src="${jsPath}/fckeditor.js"></script>
<script type="text/javascript">
<c:if test="${! empty functionalize}">
var swc = true;
function ${functionalize}(){
	if(swc){
		replaceTextarea();
		swc = false;
	}else{
		restoreTextarea();
		swc = true;
	}
}

var oFCKeditor = "";

function replaceTextarea(){
</c:if>

	oFCKeditor = new FCKeditor( '${container}' ) ;
	oFCKeditor.BasePath	= '${jsPath}/' ;
	
	<c:if test="${! empty theme}">
	//oFCKeditor.Config["SkinPath"] = '${jsPath}/editor/skins/${theme}';
	</c:if>
	
	<c:if test="${! empty width}">
	oFCKeditor.Width = ${width};
	</c:if>
	
	<c:if test="${! empty lang}">
	oFCKeditor.Config["AutoDetectLanguage"] = false;
	oFCKeditor.Config["DefaultLanguage"]    = "${lang}";
	</c:if>
	
	<c:if test="${! empty imageBrowser}">
	oFCKeditor.Config["ImageBrowser"] = ${imageBrowser};
	</c:if>
	
	<c:if test="${! empty enableXHTML}">
	oFCKeditor.Config["EnableXHTML"]    = ${enableXHTML};
	</c:if>
	
	//oFCKeditor.Value	= 'This is some <strong>sample text</strong>. You are using <a href="http://www.fckeditor.net/">FCKeditor</a>.' ;
	
	<c:if test="${empty replace}">
	oFCKeditor.Create() ;
	</c:if>
	<c:if test="${replace}">
	oFCKeditor.ReplaceTextarea() ;
	</c:if>
	
<c:if test="${! empty functionalize}">}</c:if>

function restoreTextarea(){
	var oTextarea = document.getElementById( "${container}" ) ;
		
	if ( !oTextarea )
		oTextarea = document.getElementsByName( "${container}" )[0] ;
	
	if ( !oTextarea || oTextarea.tagName != 'TEXTAREA' )
	{
		alert( 'Error: The TEXTAREA id "' + this.InstanceName + '" was not found' ) ;
		return ;
	}

	oTextarea.style.display = 'block' ;
	
	
	oTextarea = document.getElementsByTagName( "IFRAME" )[0] ;
		
	if ( !oTextarea )
		oTextarea = document.getElementsByName( "${container}" )[1] ;
	
	if ( !oTextarea || oTextarea.tagName != 'IFRAME' )
	{
		alert( 'Error: The IFRAME id "' + this.InstanceName + '" was not found' ) ;
		return ;
	}
	
	oTextarea.style.display = 'none' ;
}

</script>