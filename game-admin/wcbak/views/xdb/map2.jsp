<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>猎手列表</title>
<jsp:include page="../../inc.jsp"></jsp:include>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>
<script type="text/javascript"
	src="<%=basePath %>/jslib/main/syUtil.js"></script>
	
<script type="text/javascript"
	src="<%=basePath %>/jslib/jquery-easyui/datagrid-groupview.js"></script>
	
<script type="text/javascript"
	src="<%=basePath %>/jslib/jquery-easyui/jquery.easyui.validatebox.js"></script>
	
<script type="text/javascript"
	src="<%=basePath %>/jslib/task/taskUtil.js"></script>
	
	<script type="text/javascript"
	src="<%=basePath %>/jslib/hero/heroUtil.js"></script>
	
	<script type="text/javascript" src="<%=basePath %>/jslib/datagrid-dnd.js"></script>


<script type="text/javascript">
	 function getCols() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbData/getmapcols?fieldname=<%=request.getParameter("fieldname")%>',
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
			$('#dg2').datagrid({
				url:'<%=request.getContextPath()%>/xdbData/mapdatagrid',
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
			       
			       onLoadSuccess:function(){
			            //数据加载成功之后,使其可以拖动
			            //$(this).datagrid('enableDnd');
			        },
			        onDrop : function(targetRow,sourceRow,point){
			          console.log(targetRow + "," + sourceRow + "," + point);
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
		                          $('#dg2').datagrid('reload');
		                      }else{
		                        //  $.messager.alert("操作提示",result.msg,"info"); 
		                          $('#dg2').datagrid('refreshRow', rowIndex); 
		                      }
		                     
		                  },"json");  
			 },
			 
			});
	});    
   	
   	 
   	var editIndex = undefined;
   	function endEditing(){
   	    if (editIndex == undefined){return true}//如果为undefined的话，为真，说明可以编辑
   	    if ($('#dg2').datagrid('validateRow', editIndex)){
   	        $('#dg2').datagrid('endEdit', editIndex);  //判断是否有开启编辑的行，如果有则把开启编辑的那行结束编辑
   	        editIndex = undefined;
   	        return true;
   	    } else {
   	        return false;
   	    }
   	}
   	function onClickCell(index, field,value){
   		//如果单元格是map类型 重新弹出一个新的对话框
   		if (value.charAt(0) == '{' && value.charAt(value.lenth-1) == '}'){
   			console.log(value);
   			return;
   		}
   	    if (endEditing()){ //如果编辑列返回undefined 
   	        $('#dg2').datagrid('selectRow', index)
   	                .datagrid('editCell', {index:index,field:field});
   	        editIndex = index;
   	    }
   	}
   	
   	function  hiddenfield(){
		$('#dg2').datagrid('hideColumn','key');
	}
   	
   	
   	$("#dialog1").dialog({
        title : '--------',
        width : 260,
        height: 300,
//      left : 300,
//      top : 200, center方法可以绝对居中
        closed : true,  //是否可以关闭窗口
        cache : false,  
        modal : true,   //模式化窗口
        buttons :[{
            text : '取消',
            handler : function(){
                $("#dialog1").window('close');
            } 
        },{
            text : '保存',
            handler : function(){
                //TODO submit data and close this window
                $("#dialog1").window('close');              
            } 
        }],
        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
            $("#dialog1").dialog("center");
        }
    });
</script>
</head>
<body class="easyui-layout" fit="true" >
	
	<div id="dialog1">
   	 <table id="dg2">
   	 </table>
	</div>
	
</body>
</html>