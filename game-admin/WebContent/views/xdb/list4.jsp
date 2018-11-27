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
		                         columns.push({ "field":   item.toString(), "title": item.toString(), "width": 5 ,"editor":"text"});
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
				onHeaderContextMenu:function(){},
			pageSize:20,
			singleSelect:true,
			rownumbers:true,
			fitColumns:true,
			fit:true,
			 loadMsg: "数据加载中，请稍后...",
			pagination:true,
			columns: [colss],
			onBeforeLoad:function(param) {
				//hiddenfield();
			       },
			       
			       onClickCell: onClickCell, //单击单元格触发事件，方法在下面
			 
			 onAfterEdit:function(rowIndex, rowData, changes){  // 第三个参数是改变的值
		         
				 var rowdatas = JSON.stringify(rowData);
				 var changesdatas = JSON.stringify(changes);
				 console.log(changesdatas);
			 
			 	if ("{}" == changesdatas|| ""==changesdatas || undefined == changesdatas){
			 		return;
			 	}
		               $.post('<%=request.getContextPath()%>/xdbData/updateCell',
		            		   {
		            	   			tablename:'<%=request.getParameter("tablename")%>',
		            	   			changes:changesdatas,
		            	   			rowdatas:rowdatas
		            		   }
		                       ,function(result){
		                      if(result.status=="0"){
		                        //  $.messager.alert("操作提示",result.msg,"info"); 
		                          $('#dg').datagrid('reload');
		                      }else{
		                        //  $.messager.alert("操作提示",result.msg,"info"); 
		                          $('#dg').datagrid('refreshRow', rowIndex); 
		                      }
		                     
		                  },"json");  
			 },
			 
			});
	});    
   	
   	$.extend($.fn.datagrid.methods, {
   		//编辑单元格
   	    editCell: function(jq,param){
   	        return jq.each(function(){
   	            var opts = $(this).datagrid('options');
   	            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));//获取列
   	            for(var i=0; i<fields.length; i++){
   	                var col = $(this).datagrid('getColumnOption', fields[i]);
   	                col.editor1 = col.editor;
   	                if (fields[i] != param.field){//如果不是选中的单元格  editor置空
   	                    col.editor = null;
   	                }
   	            }
   	            $(this).datagrid('beginEdit', param.index);
   	            for(var i=0; i<fields.length; i++){
   	                var col = $(this).datagrid('getColumnOption', fields[i]);
   	                col.editor = col.editor1;
   	            }
   	        });
   	    }
   	});
   	 
   	var editIndex = undefined;
   	function endEditing(){
   	    if (editIndex == undefined){return true}//如果为undefined的话，为真，说明可以编辑
   	    if ($('#dg').datagrid('validateRow', editIndex)){
   	        $('#dg').datagrid('endEdit', editIndex);  //判断是否有开启编辑的行，如果有则把开启编辑的那行结束编辑
   	        editIndex = undefined;
   	        return true;
   	    } else {
   	        return false;
   	    }
   	}
   	function onClickCell(index, field){
   	    if (endEditing()){ //如果编辑列返回undefined 
   	        $('#dg').datagrid('selectRow', index)
   	                .datagrid('editCell', {index:index,field:field});
   	        editIndex = index;
   	    }
   	}
   	
   	function  hiddenfield(){
		$('#dg').datagrid('hideColumn','key');
	}
 
</script>
</head>
<body class="easyui-layout" fit="true" >
	
		<div region="center" border="false" style="overflow: auto;">
		<table id="dg"  title="table--><%=request.getParameter("tablename")%>" >
		</table>
	</div>
 
	
</body>
</html>