<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
//var editIndex = undefined;
//var fieldnames = '';
//var fieldkeys='';
//var url= '';
$(document).ready(function () {
	var columns = [];
    $.ajax({
    url: '<%=request.getContextPath()%>/xdbMapData/getmapcols?fieldnames=<%=request.getParameter("fieldnames")%>'+'&surl='+'<%=request.getParameter("surl")%>',
        type: 'GET',
       async: false,
        dataType: 'json',
        success: function (data) {
            if (data != null && data.length > 0) {
                $.each(data, function (i, item) {
                	var itemarr = item.toString().split("$");
                //	if (item.toString()== 'key'){
                //		 columns.push({ "field":   item.toString(), "title":item.toString() ,"width":"15%" ,"align":"right","resizable":"false" ,"editor":"text","hidden":"true"});
                //	} else {
                //		if (item.toString().substring(item.toString().length-3,item.toString().length)== 'key'){
               // 			columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":"15%" ,"align":"right","resizable":"false" ,"editor":"numberbox"});
               // 		}else {
                //			columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":"15%" ,"align":"right","resizable":"false"  ,"editor":"text"});
                //		}
                //	}
                	 columns.push({ "field":   item.toString(), "title":item.toString() ,"width":"15%" ,"align":"right","resizable":"false","editor":"text"});
                   
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
			/***
			onLoadSuccess: function (data) {
			    var fields=$("#dgmap").datagrid('getColumnFields',false);
			    //datagrid头部 table 的第一个tr 的td们，即columns的集合
			    var panel = $("#dgmap").datagrid("getPanel");  
			    var headerTds =panel.find(".datagrid-view2 .datagrid-header .datagrid-header-inner table tr:first-child").children();

			    //重新设置列表头的对齐方式
			    headerTds.each(function (i, obj) {
			        var col = $("#dgmap").datagrid('getColumnOption',fields[i]);
			        if (!col.hidden && !col.checkbox)
			        {
			            var headalign=col.headalign||col.align||'left';
			            $("div:first-child", obj).css("text-align", headalign);
			        }
			    })
			}
				*/
				
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
   		var fieldarr = field.toString().split("$");
   		if (fieldarr[fieldarr.length-1].indexOf("org_") == -1){
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
   	
   	function showMapMapDailog(index,field){
   		var row = $('#dgmap').datagrid('getData').rows[index];
   		//var row=   $('#dg').datagrid('selectRow', index);
   		var fieldkey = row.key;
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
	        	
	     	href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames='+field + '&fieldkeys='+fieldkey+'&surl='+'<%=request.getParameter("surl")%>',
	     
   	      });
  		fieldnames = field ;

  		fieldkeys = fieldkey;
  		console.log(fieldkeys);
  		//alert(fieldkeys);
   	  		$('#dd').dialog('open').dialog('setTitle', '编辑map数据2');
   	}
});   	

</script>

            <table id="dgmap">
   	 		</table>
   	 