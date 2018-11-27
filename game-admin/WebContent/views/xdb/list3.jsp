<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>猎手列表</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/main/syUtil.js"></script>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/jquery-easyui/datagrid-groupview.js"></script>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/jquery-easyui/jquery.easyui.validatebox.js"></script>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/task/taskUtil.js"></script>
	
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/hero/heroUtil.js"></script>
	
<script type="text/javascript">
	
	 function getCols() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbData/getcols?tablename=<%=request.getParameter("tablename")%>',
		             type: 'GET',
		            async: false,
		             dataType: 'json',
		             success: function (data) {
		                 if (data != null && data.length > 0) {
		                     $.each(data, function (i, item) {
		                         columns.push({ "field":   item.toString(), "title": item.toString(), "width": 5 });
		                     });
		                 }
		             }
		         });
		         return columns;
		     }
   	$(function() {
   		var colss = getCols();
   			
			$('#dg').datagrid({
				url:'<%=request.getContextPath()%>/xdbData/datagrid',
				queryParams: {
				tablename: '<%=request.getParameter("tablename")%>'
				},
			pageSize:20,
			singleSelect:true,
			rownumbers:true,
			fitColumns:true,
			fit:true,
			pagination:true,
			columns: [colss],
			       onBeforeLoad:function(param) {
			       },
			       
			    //   onDblClickCell: function(index,field,value){
			   	//	$(this).datagrid('beginEdit', index);
			   //	var ed = $(this).datagrid('getEditor', {index:index,field:field});
			   	//	alert(ed);
			   //		alert(ed.taget);
			   //		$(ed.target).focus();
			   //	}
			});
	});    
	
	
   	
   	
</script>
</head>
<body class="easyui-layout" fit="true">
	
		<div region="center" border="false" style="overflow: auto;">
		<table id="dg"  title="table--><%=request.getParameter("tablename")%>" >
		</table>
	</div>
 
</table>
	</div>
	<div id="dlgedit-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitforEditRole()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgedit').dialog('close');">取消</a>
	</div>
</body>
</html>