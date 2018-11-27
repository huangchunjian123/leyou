<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/btn-tags" prefix="btn"%>
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
	function newUser() {
		$('#dlgadd').dialog('open').dialog('setTitle', '添加用户');
		$('#fmadd').form('clear');
	}
	function editUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#fmedit').form('clear');
			$('#dlgedit').dialog('open').dialog('setTitle', '编辑用户');
			$('#fmedit').form('load', row);
		}
	}
	function submitAdd() {
		$('#fmadd').form('submit', {
			url : '<%=request.getContextPath()%>/user/add',
			onSubmit : function() {
				return $('#fmadd').form('validate');
			},
			success : function(result) {
				var d = $.parseJSON(result);
				$.messager.show({
					title : '提示',
					msg : d.msg
				});
				$('#dlgadd').dialog('close');
				$('#dg').datagrid('reload');
			}
		});
	}
	
	
	
	function submitEdit() {
		$('#fmedit').form('submit', {
			url : '<%=request.getContextPath()%>/user/edit',
			onSubmit : function() {
				return $('#fmedit').form('validate');
			},
			success : function(result) {
				var d = $.parseJSON(result);
				$.messager.show({
					title : '提示',
					msg : d.msg
				});
				$('#dlgedit').dialog('close');
				$('#dg').datagrid('reload');
			}
		});
		
	}
	
	
	
	<%-- function submitEdit() {
		var g = $('#roleId').combogrid('grid');
		var rows = g.datagrid('getSelections');
		var ids = '';
		for(var i=0;i<rows.length;i++){
			ids += rows[i].id+",";
		}
		$.post('<%=request.getContextPath()%>/user/edit',{
			id : $('#id').val(),
			realName : $('#realName').val(),
			mail:$('#mail').val(),
			mobile:$('#mobile').val(),
			roleId:ids
		},function(data) {
			var d = $.parseJSON(data);
			$.messager.show({
				title : '提示',
				msg : d.msg
			});
			$('#dlgedit').dialog('close');
			$('#dg').datagrid('reload');
		});
	} --%>
	function destroyUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确定',
				'确定删除?', function(r) {
					if (r) {
						$.post('<%=request.getContextPath()%>/user/deleteUser',{
							id : row.id
						},function(data) {
							var d = $.parseJSON(data);
							$.messager.show({
								title : '提示',
								msg : d.msg
							});
							$('#dg').datagrid('reload'); // reload the user data	
						});
					}
				});
		}
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
       <table id="dg" title="用户" class="easyui-datagrid" title="Basic DataGrid" style="overflow: hidden;"
            data-options="singleSelect:true,collapsible:true,url:'<%=request.getContextPath()%>/user/datagrid',method:'get'" toolbar="#toolbar"  rownumbers="true" pagination="true">
			<thead>
				<tr>
					<th data-options="field:'name'" width="220">账号</th>
					<th data-options="field:'realName'" width="220">真实名字</th>
					<th data-options="field:'mail'" width="220">邮箱</th>
					<th data-options="field:'mobile'" width="220">手机</th>
					<th data-options="field:'roleNames'" width="220">权限</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<btn:access privilege="user/add">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newUser()">添加</a> 
		</btn:access>
			<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
	</div>

	<div id="dlgadd" class="easyui-dialog"
		style="width: 450px; height: 350px; padding: 10px 30px" closed="true"
		buttons="#dlgadd-buttons">
		<div class="ftitle">用户</div>
		<form id="fmadd" method="post" novalidate>
			<div class="fitem">
				<label>账号:</label> <input name="name"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>密码:</label> <input name="pwd" type="password" required="true">
			</div>
			<div class="fitem">
				<label>真实名字:</label> <input name="realName"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Mail:</label> <input name="mail" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>手机:</label> <input name="mobile" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>角色:</label>
				<select id="roleId" name="roleId"  class="easyui-combogrid"
						style="width: 200px"
						data-options="
			            panelWidth: 200,
			            multiple: true,
			            idField: 'id',
			            textField: 'roleName',
			            fitColumns: true,
						method:'post',
			            url: '<%=request.getContextPath()%>/user/rolecombobox',
			            columns: [[
			            	{field:'ck',checkbox:true},
			                {field:'roleName',title:'名称',width:60}
			            ]]
			           " required="true" class="easyui-validatebox"></select>
			</div>
			
		</form>
	</div>
	<div id="dlgadd-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submitAdd()">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlgadd').dialog('close')">取消</a>
	</div>
	
	<div id="dlgedit" class="easyui-dialog"
		style="width: 450px; height: 300px; padding: 10px 30px" closed="true"
		buttons="#dlgedit-buttons">
		<div class="ftitle">用户</div>
		<form id="fmedit" method="post" novalidate>
			<div class="fitem">
				<label>账号:</label> <input name="name"
					class="easyui-validatebox" required="true" readonly="readonly">
			</div>
			<div class="fitem">
				<label>真实名字:</label> <input id="realName" name="realName"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Mail:</label> <input id="mail" name="mail" required="true" class="easyui-validatebox">
			</div>
			<div class="fitem">
				<label>手机:</label> <input id="mobile" name="mobile" required="true" class="easyui-validatebox">
			</div>
			<div class="fitem">
				<label>角色:</label>
				<select id="roleId" name="roleId"  class="easyui-combogrid"
						style="width: 200px"
						data-options="
			            panelWidth: 200,
			            multiple: true,
			            idField: 'id',
			            textField: 'roleName',
			            fitColumns: true,
						method:'post',
			            url: '<%=request.getContextPath()%>/user/rolecombobox',
			            columns: [[
			            	{field:'ck',checkbox:true},
			                {field:'roleName',title:'名称',width:60}
			            ]]
			           " required="true" class="easyui-validatebox"></select>
			</div>
			<div class="fitem">
				<label></label>
				<input name="id" id="id" type="hidden">
			</div>
		</form>
	</div>
	<div id="dlgedit-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submitEdit()">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlgedit').dialog('close')">取消</a>
	</div>
	
</body>
</html>