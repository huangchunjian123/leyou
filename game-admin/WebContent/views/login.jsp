<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>
<!DOCTYPE html>
<html>
<head>
<title>讯果科技后台登录系统</title>
<jsp:include page="../inc.jsp"></jsp:include>
<style type="text/css">
body {
	margin-left: 0.1px;
	margin-top: 190px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: repeat-x;
}

</style>
</head>
<script type="text/javascript">
		function submitok(){
			$('#fm').form('submit', {
				url : '<%=request.getContextPath()%>/login',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.status==0) {
						window.location.href='<%=request.getContextPath()%>/gomain';
					} else {
						$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}	
				}
			});
		}
		
		
		function keyLogin(){
		  if(event.keyCode==13){//按下Enter键提交登录请求
			  submitok();
		  }     
		}  
	
</script>
<body onkeydown="keyLogin();">
<form id="fm" method="post" novalidate>
<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="190"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <table width="320" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="40" height="50"><img src="<%=basePath %>/views/zpagecss/login/images/user.gif" width="30" height="30"></td>
                    <td width="38" height="50">用户</td>
                    <td width="242" height="50"><input type="text" name="cname" id="textfield" style="width:164px; height:32px; line-height:34px; background:url(<%=basePath %>/views/zpagecss/login/images/inputbg.gif) repeat-x; border:solid 1px #d1d1d1; font-size:9pt; font-family:Verdana, Geneva, sans-serif;" class="easyui-validatebox" value="" required="true" missingMessage="请填写登录名称"></td>
                  </tr>
                  <tr>
                    <td height="50"><img src="<%=basePath %>/views/zpagecss/login/images/password.gif" width="28" height="32"></td>
                    <td height="50">密码</td>
                    <td height="50"><input type="password" name="cpwd" id="textfield2" style="width:164px; height:32px; line-height:34px; background:url(<%=basePath %>/views/zpagecss/login/images/inputbg.gif) repeat-x; border:solid 1px #d1d1d1; font-size:9pt; " class="easyui-validatebox" value="" required="true" missingMessage="请填写登录密码" ></td>
                  </tr>
                  <tr>
                    <td height="40">&nbsp;</td>
                    <td height="40">&nbsp;</td>
                    <td height="60"><img src="<%=basePath %>/views/zpagecss/login/images/login.gif" width="95" height="34" onclick="submitok()"></td>
                  </tr>
                </table>
              </tr>
            </td>
          </tr>
        </table>
      </tr>
    </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
