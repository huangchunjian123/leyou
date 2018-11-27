<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

$(document).ready(function () {
	var columns = [];
    $.ajax({
    url: '<%=request.getContextPath()%>/xdbMapData/getmapcols?fieldnames=<%=request.getParameter("fieldnames")%>',
        type: 'GET',
       async: false,
        dataType: 'json',
        success: function (data) {
            if (data != null && data.length > 0) {
                $.each(data, function (i, item) {
                    columns.push({ "field":   item.toString(), "title": item.toString(),"width":"150" ,"align":"center" ,"editor":"text"});
                });
            }
        }
    });
	$('#dgmap').datagrid({
				url:'<%=request.getContextPath()%>/xdbMapData/mapdatagrid',
				queryParams: {
				fieldnames: '<%=request.getParameter("fieldnames")%>',
				fieldkeys: '<%=request.getParameter("fieldkeys")%>'
				},
				onHeaderContextMenu:function(){},
			pageSize:10,
			singleSelect:true,
			rownumbers:true,
			fitColumns:false,
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
			            	   			changes:changesdatas,
			            	   			rowdatas:rowdatas
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
	var editIndex = undefined;
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
   		if (value.charAt(0) == '{' && value.charAt(value.length-1) == '}'){
   			console.log(value);
   			showMapDailog(index,field);
   			return;
   		}
   	    if (endEditing()){ //如果编辑列返回undefined 
   	        $('#dgmap').datagrid('selectRow', index)
   	                .datagrid('editCell', {index:index,field:field});
   	        editIndex = index;
   	    }
   	}
   	
   	function showMapDailog(index,field){
   		var row = $('#dgmap').datagrid('getData').rows[index];
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
	                $("#dialog").window('close');
	            } 
	        },{
	            text : '保存',
	            handler : function(){
	                //TODO submit data and close this window
	                $("#dialog").window('close');              
	            } 
	        }],
	        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
	            $("#dialog").dialog("center");
	        },
	     	href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames='+field + '&fieldkeys='+fieldkey,
	     
   	      });
   	  		$('#dd').dialog('open').dialog('setTitle', '编辑map数据');
   	}
});   	
</script>

<div id="dd" >
	</div>
            <table id="dgmap">
   	 		</table>
   	 