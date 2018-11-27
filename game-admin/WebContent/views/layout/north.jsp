<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function logout(b) {
		$.post('<%=request.getContextPath()%>/logout', function() {
			if (b) {
				var browserName=navigator.appName;
				if (browserName=="Netscape") {
					window.open('','_parent','');
					window.close();
				} 
				else if (browserName=="Microsoft Internet Explorer") {
					window.opener = "whocares"; window.close(); 
				} 
			} else {
				window.location.href="<%=request.getContextPath()%>/gologin";
			}
		});
	}
</script>
 <div class="cs-north-bg"><div class="cs-north-logo">xdb后台</div></div>
<div style="position: absolute; right: 0px; bottom: 0px; ">
	当前用户：${sessionInfo.loginName}
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_exitMenu" iconCls="icon-back">退出</a>
</div>
<!-- 
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');">default</div>
	<div onclick="sy.changeTheme('gray');">gray</div>
	<div onclick="sy.changeTheme('cupertino');">cupertino</div>
	<div onclick="sy.changeTheme('dark-hive');">dark-hive</div>
	<div onclick="sy.changeTheme('pepper-grinder');">pepper-grinder</div>
	<div onclick="sy.changeTheme('sunny');">sunny</div>
</div>
 -->
<div id="layout_north_exitMenu" style="width: 100px; display: none;">
	<div onclick="logout();">切换用户</div>
	<div onclick="logout();">退出系统</div>
</div>
