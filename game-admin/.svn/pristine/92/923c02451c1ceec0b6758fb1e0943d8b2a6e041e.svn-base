<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript">
	var url;
	function newRole() {
		$('#dlg').dialog('open').dialog('setTitle', '添加角色');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/role/add";
		
	}
	function editRole() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑角色');
			$('#fm').form('load', row);
			url = "<%=request.getContextPath()%>/role/edit?id=" + row.id;
		}
	}
	function saveRole() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var d = $.parseJSON(result);
				$.messager.show({
					title : '提示',
					msg : d.msg
				});
				$('#dlg').dialog('close'); // close the dialog
				$('#dg').datagrid('reload'); // reload the user data
			}
		});
	}
	function destroyRole() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确定',
				'确定删除?', function(r) {
					if (r) {
						$.post('<%=request.getContextPath()%>/role/deleteRole',{
							id : row.id
						},function(data) {
							var d = $.parseJSON(data);
							$.messager.show({
								title : '提示',
								msg : d.msg
							});
							$('#dg').datagrid('reload');
						});
					}
				});
		}
	}
	function showauthtree(id) {
		$('#dlgauth').dialog('open').dialog('setTitle', '权限设置');
		$('#id').val(id);
		$('#tree').tree({   
		      url:'<%=request.getContextPath()%>/menu/authtree?roleId='+id,
		      checkbox:true,
		      animate:true
		});  
	}
	function changeAuth() {
		var auths=getMenus();
		if(auths == '')
		{
			alert("权限不能设置为空。");
			return false;
		}
		$.post('<%=request.getContextPath()%>/role/updateAuth',{
			id : $('#id').val(),
			auths: auths
		},function(data) {
			var d = $.parseJSON(data);
			$.messager.show({
				title : '提示',
				msg : d.msg
			});
			$('#dg').datagrid('reload');
		});
	}
	function getMenus(){
		var nodes = $('#tree').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
				s += nodes[i].id;
		}
		return s;
	}
	function formateroperate(value,row,rowIndex)
	{
		return "<a href='#' onclick='showauthtree("+  row.id +");'>设置权限</a>";
	}

</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
       <table id="dg" title="角色" class="easyui-datagrid" title="Basic DataGrid" style="overflow: hidden;"
            data-options="singleSelect:true,collapsible:true,url:'<%=request.getContextPath()%>/role/datagrid',method:'get'" toolbar="#toolbar"  rownumbers="true" pagination="true">
			<thead>
				<tr>
					<th data-options="field:'roleName'" width="220">名称</th>
					<th data-options="field:'roleDesc'" width="300" align="right">描述</th>
					<th data-options="field:'opt',title:'Operation',formatter:formateroperate" width="100">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRole()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRole()">删除</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 460px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">角色</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>名称:</label> <input id="roleName" name="roleName" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>描述:</label> <textarea cols="30" rows="5" name="roleDesc"></textarea>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
	
	<div id="dlgauth" class="easyui-dialog" style="width: 400px; height: 500px; padding: 10px 20px" closed="true" buttons="#dlgdlgauth-buttons">
		<div class="ftitle">权限列表</div>
		<input type="hidden" name="id" id="id">
		<ul id="tree" class="easyui-tree"></ul>
	</div>
	<div id="dlgdlgauth-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="changeAuth()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgauth').dialog('close')">取消</a>
	</div>
</body>
</html>