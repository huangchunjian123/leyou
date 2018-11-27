<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/jquery-easyui/jquery.easyui.validatebox.js"></script>
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
	function newMenu() {
		$('#dlg').dialog('open').dialog('setTitle', '添加菜单');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/menu/add";
	}
	function editMenu() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑菜单');
			$('#fm').form('load', row);
			url = "<%=request.getContextPath()%>/menu/edit?id=" + row.id;
		}
	}
	function saveMenu() {
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
				$('#dlg').dialog('close');
				$('#dg').treegrid('reload');
				$('#cbtree').combotree('reload');
			}
		});
	}
	function destroyMenu() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确定',
				'确定删除?', function(r) {
					if (r) {
						$.post('<%=request.getContextPath()%>/menu/deleteMenu',{
							id : row.id
						},function(data) {
							var d = $.parseJSON(data);
							$.messager.show({
								title : '提示',
								msg : d.msg
							});
							$('#dg').treegrid('reload');
							$('#cbtree').combotree('reload');
						});
					}
				});
		}
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="dg" title="菜单" class="easyui-treegrid"
			data-options="
                url: '<%=request.getContextPath()%>/menu/treegrid',
                method: 'get',
                rownumbers: true,
                idField: 'id',
                fit:true,
                treeField: 'name'"
			toolbar="#toolbar" pagination="false">
			<thead>
				<tr>
					<th data-options="field:'name'" width="220">名称</th>
					<th data-options="field:'url'" width="300" align="right">url</th>
					<th data-options="field:'typeName'" width="80">类型</th>
					<th data-options="field:'sort'" width="80">排序</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newMenu()">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editMenu()">编辑</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyMenu()">删除</a>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 320px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">菜单</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>名称:</label> <input name="name"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>url:</label> <input name="url" required="true">
			</div>
			<div class="fitem">
				<label>排序:</label> <input name="sort" class="easyui-validatebox" required="true" data-options="validType:'integer'">
			</div>
			<div class="fitem">
				<label>类型:</label>
				<select class="easyui-combobox" name="type">
			        <option value="01">菜单</option>
			        <option value="02">按钮</option>
		        </select>
			</div>
			<div class="fitem">
				<label>上级菜单:</label>
				 <input id="cbtree" class="easyui-combotree" name="pid" data-options="url:'<%=request.getContextPath()%>/menu/ctrlTree',
				 		method:'get',
				 		required:true" style="width:200px;">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveMenu()">Save</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>