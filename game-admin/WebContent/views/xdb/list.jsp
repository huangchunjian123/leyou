<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>xdb</title>
<jsp:include page="../../inc.jsp"></jsp:include>
	
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/jquery-utils.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/datagrid-dnd.js"></script>
<script type="text/javascript">

	var fieldnames = '';
	var fieldkeys='';
	var url= '';
	var surl='';
	 function getCols() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbData/getcols?tablename=<%=request.getParameter("tablename")%>'+'&surl='+surl,
		             type: 'GET',
		            async: false,
		             dataType: 'json',
		             success: function (data) {
		            	
		            	 
		                 if (data != null && data.length > 0) {
		                	// alert(100/data.length);
		                	 var wid = 100/data.length;
		                	 if (wid<3){
		                		 wid=3;
		                	 }
		                	 if (wid>30){
		                		 wid=30;
		                	 }
		                     $.each(data, function (i, item) {
		                    	 
		                    	 //columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":"5%" ,"align":"right","resizable":"false"  ,"editor":"text"});
		                    		 columns.push({ "field":   item.toString(), "title": item.toString(), "width":wid+"%" ,"align":"right","resizable":"false" ,"editor":"text"});
		                     });
		                 }
		             }
		         });
		         return columns;
		     }
   	$(function() {
   		
   		surl = '<%=request.getParameter("surl")%>';
   		//alert($('#qConditions').val())
   		var colss = getCols();
			$('#dg').datagrid({
				url:'<%=request.getContextPath()%>/xdbData/datagrid',
				queryParams: {
				tablename: '<%=request.getParameter("tablename")%>',
				surl:surl,
				qConditions:$('#qConditions').val()
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
		            	   			rowdatas:escape(rowdatas),
		            	   			surl:surl
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
   		if (field == 'key')
   			return;
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
   	        width : 1660,
   	        height: 500,
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
	        },{
	            text : '删除',
	            handler : function(){
	            	showdelMap();              
	            } 
	        }],
	        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
	            $("#dd").dialog("center");
	        },
	        onClose:function(){
	        	 $('#dg').datagrid('reload');
	        },
	        
	     	href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames=<%=request.getParameter("tablename")%>_'+field + '&fieldkeys='+fieldkey+ '&surl='+surl,
	     
   	      });
  		fieldnames='<%=request.getParameter("tablename")%>_'+field ;
  		fieldkeys = fieldkey;
   	  		$('#dd').dialog('open').dialog('setTitle', '编辑map数据');
   	}
  //添加map
  	
  	function getaddCols() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbMapData/getaddmapcols?fieldnames='+fieldnames + '&surl='+surl,
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
  
  
  	function showdelMap() {
  		var row = $('#dgmap').datagrid('getSelections');
  		if (row){
  		}else {
  			if (row.length ==0) {
  				$.messager.alert('消息','请至少选择一条要删除的行');
  	  			return;
  			}
  			$.messager.alert('消息','请选择要删除的行');
  			return;
  		}
  		url = '<%=request.getContextPath()%>/xdbMapData/delmap'+'?surl='+surl;
  		$.messager.confirm('Confirm','Are you sure you want to delete record?',function(r){
  		    if (r){
  		    	
  		    	var temID=""; 
  		    	for (i = 0; i < row.length;i++) { 
                    if (temID =="") { 
                         temID = row[i].key; 
                     } else { 
                         temID = row[i].key + "," + temID; 
                     }   
             	}
  		    	$.post(url,{
  					serverId:0,
  					fieldnames:fieldnames,
  					fieldkeys:escape(fieldkeys),
  					delkey:escape(temID),
  					surl:surl
  				},function(data) {
  					var d = $.parseJSON(data);
  					$.messager.show({
  						title : '提示',
  						msg : d.msg
  					});
  					if(d.status==0){
  						$('#dgmap').datagrid('reload');
  					}else{
  						$('#addaccordion').accordion('select','基本信息');
  					}
  				});	
  		    }
  		});
	}
	
	function showAddMap() {
		 var html ='';
		 var columnFields = getaddCols();
		 //alert(columnFields)
		//var columnFields = $('#dg').datagrid('getColumnFields');  
		for ( var i = 0; i <columnFields.length; i++){
			 var itemarr = columnFields[i].toString().split("_");
        	// "title":itemarr[itemarr.length-1] 
			var br = ((i+1)%3==0)?"<br/>":"";
		     	html +='<tr>'
		    	html +='<td>'+itemarr[itemarr.length-1]+':</td>'
		    	html +='<td>'
		    	html +='<input class="easyui-validatebox easyui-textbox" name="'+columnFields[i].toString() +'" data-options="required:true" id="'+itemarr[itemarr.length-1]+'"  >'; 
		    	html +='</td>' 
		    	html +='</tr>';
		    	html += br;
		    	
		}
		$('#fmadd').empty();
		$('#dlgadd').dialog({
	     	onLoad:function(){
	    		$('#fmadd').form('clear');
			    $('#addaccordion').accordion('select','基本信息');
			    
			    $("#fmtable").html(html);
			    //	$("#fmtable").after(html);
			    	for ( var i = 0; i <columnFields.length; i++){
			    		 var itemarr = columnFields[i].toString().split("_");
			    		$.parser.parse($('#'+itemarr[itemarr.length-1]));
			    		//console.log($('#'+columnFields[i]));
			    	}
			    	
	    },
	    href:'<%=request.getContextPath()%>/views/xdb/addmap.jsp?surl='+surl,
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
				surl:surl,
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
	
	
	//addtable
	
	function getadds() {
		         var columns = [];
		         $.ajax({
	             url: '<%=request.getContextPath()%>/xdbData/getaddcols?tablename='+'<%=request.getParameter("tablename")%>'+'&surl='+surl,
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
	
	function _add() {
		 var html ='';
		 var columnFields = getadds();
		 //alert(columnFields)
		//var columnFields = $('#dg').datagrid('getColumnFields');  
		for ( var i = 0; i <columnFields.length; i++){
			 var itemarr = columnFields[i].toString().split("_");
        	// "title":itemarr[itemarr.length-1] 
			var tr = ((i+1)%2==1);
			var endtr = ((i+1)%2==0);
		     	html +=tr?'<tr>':''
		    	html +='<td>'+itemarr[itemarr.length-1]+':</td>'
		    	html +='<td>'
		    	html +='<input class="easyui-validatebox easyui-textbox" name="'+columnFields[i].toString() +'" data-options="required:true" id="'+itemarr[itemarr.length-1]+'"  >'; 
		    	html +='</td>' 
		    	html +=endtr?'</tr>':'';
		}
		$('#fmtbladd').empty();
		$('#tbladd').dialog({
			top:50,
	     	onLoad:function(){
	    		$('#fmtbladd').form('clear');
			    $('#addtblaccordion').accordion('select','基本信息');
			    
			    $("#fmtbltable").html(html);
			    //	$("#fmtable").after(html);
			    	for ( var i = 0; i <columnFields.length; i++){
			    		 var itemarr = columnFields[i].toString().split("_");
			    		$.parser.parse($('#'+itemarr[itemarr.length-1]));
			    		//console.log($('#'+columnFields[i]));
			    	}
			    	
	    },
	    href:'<%=request.getContextPath()%>/views/xdb/add.jsp?surl='+surl,
      modal: true,
      closed: true});
		$('#tbladd').dialog('open');
		$('#tbladd').dialog('setTitle', '添加map');
	}
	
	function submittbladd() {
		url = '<%=request.getContextPath()%>/xdbData/addobject';
			
		if($("#fmtbladd").form('validate')){
			$.post(url,{
				serverId:0,
				tablename:'<%=request.getParameter("tablename")%>',
				surl:surl,
				//changes:escape(changesdatas),
	   			//rowdatas:escape(rowdatas)
				dataJson:escape($.toJSON($('#fmtbladd').serializeObject()))
			},function(data) {
				var d = $.parseJSON(data);
				$.messager.show({
					title : '提示',
					msg : d.msg
				});
				if(d.status==0){
					$('#dg').datagrid('reload');
					$('#tbladd').window('close');
				}else{
					$('#addaccordion').accordion('select','基本信息');
					$('#tbladd').window('close');
				}
			});			
		}else{
			$('#addtblaccordion').accordion('select','基本信息');
		}
	}
	
	function _search(){	
		//$('#dg').datagrid('reload'); 
		$('#dg').datagrid({
			url:'<%=request.getContextPath()%>/xdbData/datagrid',
			queryParams: {
			tablename: '<%=request.getParameter("tablename")%>',
			surl:surl,
			qConditions:escape($('#qConditions').val())
			},
		});
	}
	
	function _del() {
  		var row = $('#dg').datagrid('getSelected');
  		if (row){
  		}else {
  			$.messager.alert('消息','请选择要删除的行');
  			return;
  		}
  		url = '<%=request.getContextPath()%>/xdbData/del';
  		$.messager.confirm('Confirm','Are you sure you want to delete record?',function(r){
  		    if (r){
  		    	$.post(url,{
  					serverId:0,
  					tablename:'<%=request.getParameter("tablename")%>',
  					tablekey:escape(row.key),
  					surl:surl
  				},function(data) {
  					var d = $.parseJSON(data);
  					$.messager.show({
  						title : '提示',
  						msg : d.msg
  					});
  					if(d.status==0){
  						$('#dg').datagrid('reload');
  					}else{
  						$('#addaccordion').accordion('select','基本信息');
  					}
  				});	
  		    }
  		});
	}
</script>
</head>
<body class="easyui-layout" fit="true" >
	<div id="dd" >
	</div>
		<div region="center" border="false" style="overflow: auto;">
		<table id="butts" >
		<tr>
					<td>
						搜索条件:<input id="qConditions" name="qConditions" style="width: 200px;" />&nbsp;
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">搜索</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_del();">删除</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_add();">添加</a>
						<font color="red" size="3" ><strong>提醒:工具为了方便修改服务器的xdb数据库数据，不做业务逻辑判定处理(ps:添加表会有延时- -)</strong></font>
					</td>
			</tr>
		</table>
		
		<table id="dg"  title="table--><%=request.getParameter("tablename")%>" >
			
		</table>
				
	</div>
 	 
	
	
	<div id="dlgadd"  style="width: 800px;overflow: auto;" closed="true" buttons="#dlgadd-buttons" align="center">
		</div>
	<div id="dlgadd-buttons" style="display: none;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitadd()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgadd').dialog('close');">取消</a>
	</div>
	
	<div id="tbladd"  style="width: 800px;overflow: auto;" closed="true" buttons="#tbladd-buttons" align="center">
		</div>
	<div id="tbladd-buttons" style="display: none;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submittbladd()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#tbladd').dialog('close');">取消</a>
	</div>
</body>
</html>