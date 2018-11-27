<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(document).ready(function () {
	var surl= '';
	var columns = [];
	alert ("aa"+'<%=request.getParameter("fieldnames")%>')
    $.ajax({
    url: '<%=request.getContextPath()%>/xdbMapData/getmapcols?fieldnames=<%=request.getParameter("fieldnames")%>'+'&surl='+'<%=request.getParameter("surl")%>',
        type: 'GET',
       async: false,
        dataType: 'json',
        success: function (data) {
            if (data != null && data.length > 0) {
            	 var wid = 100/data.length;
            	 if (wid<2.5){
            		 wid=2.5;
            	 }
            	 if (wid>30){
            		 wid=30;
            	 }
                $.each(data, function (i, item) {
                	var itemarr = item.toString().split("_");
                	if (item.toString()== 'key'){
                		 columns.push({ "field":   item.toString(), "title":item.toString() ,"width":wid+"%" ,"align":"right","resizable":"false" ,"editor":"text","hidden":"true"});
                	} else {
                		if (item.toString().substring(item.toString().length-3,item.toString().length)== 'key'){
                			columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":wid+"%" ,"align":"right","resizable":"false" ,"editor":"numberbox"});
                		}else {
                			columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":wid+"%" ,"align":"right","resizable":"false"  ,"editor":"text"});
                		}
                	}
                	// columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":"50" ,"align":"center" ,"editor":"text"});
                   
                });
            }
        }
    });
    fieldnames = '<%=request.getParameter("fieldnames")%>';
	$('#dgmap').datagrid({
				url:'<%=request.getContextPath()%>/xdbMapData/mapdatagrid',
				queryParams: {
				fieldnames: '<%=request.getParameter("fieldnames")%>',
				fieldkeys: '<%=request.getParameter("fieldkeys")%>',
				surl:'<%=request.getParameter("surl")%>'
				},
				onHeaderContextMenu:function(){},
			pageSize:10,
			singleSelect:false,
			rownumbers:true,
			fitColumns:true,
			fit:true,
			 loadMsg: "数据加载中，请稍后...", 
			pagination:true,
			columns: [columns],
			
			  onClickCell: onClickCell, //单击单元格触发事件，方法在下面
				 
				 onAfterEdit:function(rowIndex, rowData, changes){  // 第三个参数是改变的值
			         
					 var rowdatas = JSON.stringify(rowData);
					 var changesdatas = JSON.stringify(changes);
					 console.log(changesdatas);
					 var login_name = $('#dgmap').datagrid('getEditor', {index:editIndex,field:'login_name'});
				 	if ("{}" == changesdatas|| ""==changesdatas || undefined == changesdatas){
				 		return;
				 	}
			               $.post('<%=request.getContextPath()%>/xdbMapData/updateMapCell',
			            		   {
			            	   			changes:escape(changesdatas),
			            	   			rowdatas:escape(rowdatas),
			            	   			surl:'<%=request.getParameter("surl")%>'
			            		   }
			                       ,function(result){
			                      if(result.status=="0"){
			                        //  $.messager.alert("操作提示",result.msg,"info"); 
			                          $('#dgmap').datagrid('reload');
			                      }else{
			                        //  $.messager.alert("操作提示",result.msg,"info"); 
			                          $('#dgmap').datagrid('refreshRow', rowIndex); 
			                      }
			                     
			                  },"json");  
				 },
			
	});    

   	function endEditing(){
   	    if (editIndex == undefined){return true}//如果为undefined的话，为真，说明可以编辑
   	    if ($('#dgmap').datagrid('validateRow', editIndex)){
   	        $('#dgmap').datagrid('endEdit', editIndex);  //判断是否有开启编辑的行，如果有则把开启编辑的那行结束编辑
   	        editIndex = undefined;
   	        return true;
   	    } else {
   	        return false;
   	    }
   	}
   	
   	function onClickCell(index, field,value){
   		//如果单元格是map类型 重新弹出一个新的对话框
   		var fieldarr = field.toString().split("_");
   		if (fieldarr[fieldarr.length-1].indexOf("ORG") == -1){
   			//if (value.charAt(0) == '{' && value.charAt(value.length-1) == '}'){
   	   		//	console.log(value);
   	   		//	showMapDailog(index,field);
   	   		//	return;
   	   		//}
   			var beginv = value.charAt(0);
   	   		var endv = value.charAt(value.length-1);
   	   		if (beginv == '{' && endv == '}'){
   	   			console.log(value);
   	   		showMapMapDailog(index,field);
   	   			return;
   	   		}
   	   		if (beginv == '[' &&endv == ']'){
   	   			console.log(value);
   	   		showMapMapDailog(index,field);
   	   			return;
   	   		}
   		}
   		
   		
   		
   	    if (endEditing()){ //如果编辑列返回undefined 
   	        $('#dgmap').datagrid('selectRow', index)
   	                .datagrid('editCell', {index:index,field:field});
   	        editIndex = index;
   	    }
   	}
   	surl = '<%=request.getParameter("surl")%>';
   	function showMapMapDailog(index,field){
   		var row = $('#dgmap').datagrid('getData').rows[index];
   		//var row=   $('#dg').datagrid('selectRow', index);
   		var fieldkey = row.key;
   		var 
  		$("#dd").dialog({
   	        title : '--------',
   	        width : 1660,
   	        height: 400,
   	        closed : true,  //是否可以关闭窗口
   	        cache : false,  
  	        modal : true,   //模式化窗口
  	      buttons :[{
	            text : '取消',
	            handler : function(){
	                $("#dd").window('close');
	                $('#dlgadd').window('close');
	            } 
	        },{
	            text : '添加',
	            handler : function(){
	                //TODO submit data and close this window
	            	showAddMap();         
	            } 
	        },{
	            text : '删除',
	            handler : function(){
	                //TODO submit data and close this window
	            	showdelMap();         
	            } 
	        }],
	        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
	            $("#dd").dialog("center");
	        },
	        
	        onLoadSuccess:function(data){    
	        	   $('#dgmap').datagrid('fixRowHeight'); //格式化后行号对齐
	        	}
	      	,
		href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames='+field + '&fieldkeys='+fieldkey+'&surl='+surl,
	     
   	      });
  		fieldnames = field ;

  		fieldkeys = fieldkey;
  		console.log(fieldkeys);
  		//alert(fieldkeys);
   	  		$('#dd').dialog('open').dialog('setTitle', '编辑map数据2');
   	}
});   	

</script>

            <table id="dgmap" title="field--><%=request.getParameter("fieldnames")%>">
   	 		</table>      
   	 