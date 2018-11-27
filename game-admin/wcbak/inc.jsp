<%@ page language="java" pageEncoding="UTF-8"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=9" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>
<link id="easyuiTheme" rel="stylesheet" href="<%=basePath %>/jslib/jquery-easyui/themes/gray/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath %>/jslib/jquery-easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath %>/jslib/main/syCss.css" type="text/css"></link>
<script type="text/javascript" src="<%=basePath %>/jslib/jquery-easyui/jquery-1.8.0.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath %>/jslib/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath %>/jslib/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath %>/jslib/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath %>/jslib/jquery.json.js"></script>