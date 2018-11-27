<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>xdb后台管理系统</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	var sessionInfo_loginName = '${sessionInfo.loginName}';
	if (sessionInfo_loginName == '' || sessionInfo_loginName == null) {
	window.location.href = "<%=request.getContextPath()%>/gologin";
	}
});
</script>
</head>
<body id="indexLayout" class="easyui-layout">
	<div region="north" style="height:40px;overflow: hidden;" href="<%=request.getContextPath()%>/views/layout/north.jsp"></div>
	<div region="center" title="xdb后台管理" style="overflow: hidden;" href="<%=request.getContextPath()%>/views/layout/center.jsp"></div>
	<div region="west" title="功能导航" split="false" style="width:200px;overflow: hidden;" href="<%=request.getContextPath()%>/views/layout/west.jsp"></div>
	<input type="hidden" id="top" name="top"/>
</body>
</html>