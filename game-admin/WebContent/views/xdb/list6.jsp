<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>xdb</title>
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
	src="<%=request.getContextPath()%>/jslib/jquery-utils.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/hero/heroUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/datagrid-dnd.js"></script>
<script type="text/javascript">

	var fieldnames = '';
	var fieldkeys='';
	var url= '';
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
		            	   			changes:escape(changesdatas),
		            	   			rowdatas:escape(rowdatas)
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
   	function onClickCell(index, field,value){
   		//如果单元格是map类型 重新弹出一个新的对话框
   		var beginv = value.charAt(0);
   		var endv = value.charAt(value.length-1);
   		if (beginv == '{' && endv == '}'){
   			console.log(value);
   			showMapDailog(index,field);
   			return;
   		}
   		if (beginv == '[' &&endv == ']'){
   			console.log(value);
   			showMapDailog(index,field);
   			return;
   		}
   	    if (endEditing()){ //如果编辑列返回undefined 
   	        $('#dg').datagrid('selectRow', index)
   	                .datagrid('editCell', {index:index,field:field});
   	        editIndex = index;
   	    }
   	}
   	
   	function  hiddenfield(){
		$('#dg').datagrid('hideColumn','key');
	}
 
   	
   	function showMapDailog(index,field){
   		var row = $('#dg').datagrid('getData').rows[index];
   		//var row=   $('#dg').datagrid('selectRow', index);
   		var fieldkey = row.key;
  		$("#dd").dialog({
   	        title : '--------',
   	        width : 860,
   	        height: 400,
   	        closed : true,  //是否可以关闭窗口
   	        cache : false,  
  	        modal : true,   //模式化窗口
  	      buttons :[{
	            text : '取消',
	            handler : function(){
	                $("#dd").window('close');
	            } 
	        },{
	            text : '添加',
	            handler : function(){
	            	showAddMap();              
	            } 
	        }],
	        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
	            $("#dd").dialog("center");
	        },
	        onClose:function(){
	        	 $('#dg').datagrid('reload');
	        },
	        
	     	href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames=<%=request.getParameter("tablename")%>$'+field + '&fieldkeys='+fieldkey,
	     
   	      });
  		fieldnames='<%=request.getParameter("tablename")%>$'+field ;
  		fieldkeys = fieldkey;
   	  		$('#dd').dialog('open').dialog('setTitle', '编辑map数据');
   	}
  //添加map
  	
  	function getaddCols() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbMapData/getaddmapcols?fieldnames='+fieldnames,
		             type: 'GET',
		            async: false,
		             dataType: 'json',
		             success: function (data) {
		                 if (data != null && data.length > 0) {
		                     $.each(data, function (i, item) {
		                         columns.push(item.toString());
		                     });
		                 }
		             }
		         });
		         return columns;
	}
	function showAddMap() {
		 var html ='';
		 var columnFields = getaddCols();
		 alert(columnFields)
		//var columnFields = $('#dg').datagrid('getColumnFields');  
		for ( var i = 0; i <columnFields.length; i++){
			 var itemarr = columnFields[i].toString().split("$");
          	// "title":itemarr[itemarr.length-1] 
			var br = ((i+1)%3==0)?"<br/>":"";
		     	html +='<tr>'
		    	html +='<td>'+itemarr[itemarr.length-1]+':</td>'
		    	html +='<td>'
		    	html +='<input class="easyui-validatebox easyui-textbox" name="'+columnFields[i] +'" data-options="required:true" id="'+columnFields[i]+'"  >'; 
		    	html +='</td>' 
		    	html +='</tr>';
		    	html += br;
		}
		$('#fmadd').empty();
		$('#dlgadd').dialog({
	     	onLoad:function(){
	    		$('#fmadd').form('clear');
			    $('#addaccordion').accordion('select','基本信息');
			    
			    	$("#fmtable").after(html);
			    	//for ( var i = 0; i <columnFields.length; i++){
			    	//	$.parser.parse($('#'+columnFields[i]).parent());
			    	//}
			    	
	    },
	    href:'<%=request.getContextPath()%>/views/xdb/addmap.jsp',
        modal: true,
        closed: true});
		$('#dlgadd').dialog('open');
		$('#dlgadd').dialog('setTitle', '添加map');
	}
	
	function submitadd() {
		url = '<%=request.getContextPath()%>/xdbMapData/addmapobject';
			
		if($("#fmadd").form('validate')){
			$.post(url,{
				serverId:0,
				fieldnames:fieldnames,
				fieldkeys:escape(fieldkeys),
				//changes:escape(changesdatas),
	   			//rowdatas:escape(rowdatas)
				dataJson:escape($.toJSON($('#fmadd').serializeObject()))
			},function(data) {
				var d = $.parseJSON(data);
				$.messager.show({
					title : '提示',
					msg : d.msg
				});
				if(d.status==0){
					$('#dgmap').datagrid('reload');
					$('#dlgadd').window('close');
				}else{
					$('#addaccordion').accordion('select','基本信息');
					$('#dlgadd').window('close');
				}
			});			
		}else{
			$('#addaccordion').accordion('select','基本信息');
		}
	}
</script>
</head>
<body class="easyui-layout" fit="true" >
	
		<div region="center" border="false" style="overflow: auto;">
		<table id="dg"  title="table--><%=request.getParameter("tablename")%>" >
		</table>
	</div>
 
	<div id="dd" >
	</div>
	
	<div id="dlgadd"  style="width: 800px;overflow: auto;" closed="true" buttons="#dlgadd-buttons" align="center">
		</div>
	<div id="dlgadd-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitadd()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgadd').dialog('close');">取消</a>
	</div>
</body>
</html>