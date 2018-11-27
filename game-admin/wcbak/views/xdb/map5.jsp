<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
//var editIndex = undefined;
//var fieldnames = '';
//var fieldkeys='';
//var url= '';
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
                	var itemarr = item.toString().split("$");
                	// columns.push({ "field":   item.toString(), "title":itemarr[itemarr.length-1] ,"width":"50" ,"align":"center" ,"editor":"text"});
                    columns.push({ "field":   item.toString(), "title":item.toString() ,"width":"150" ,"align":"center" ,"editor":"text"});
                });
            }
        }
    });
    fieldnames = '<%=request.getParameter("fieldnames")%>';
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
			            	   			changes:escape(changesdatas),
			            	   			rowdatas:escape(rowdatas)
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
  		$("#ddmap").dialog({
   	        title : '--------',
   	        width : 860,
   	        height: 400,
   	        closed : true,  //是否可以关闭窗口
   	        cache : false,  
  	        modal : true,   //模式化窗口
  	      buttons :[{
	            text : '取消',
	            handler : function(){
	                $("#ddmap").window('close');
	                $('#dlgmapadd').window('close');
	            } 
	        },{
	            text : '添加',
	            handler : function(){
	                //TODO submit data and close this window
	            	showmapAddMap();         
	            } 
	        }],
	        onBeforeOpen : function(){  //dialog展示前,使其绝对居中
	            $("#ddmap").dialog("center");
	        },
	        
	        onLoadSuccess:function(data){    
	        	   $('#dgmap').datagrid('fixRowHeight'); //格式化后行号对齐
	        	}
	      ,
	        	
	     	href:'<%=request.getContextPath()%>/views/xdb/map.jsp?fieldnames='+field + '&fieldkeys='+fieldkey,
	     
   	      });
  		fieldnames = field ;

  		fieldkeys = fieldkey;
  		console.log(fieldkeys);
  		//alert(fieldkeys);
   	  		$('#ddmap').dialog('open').dialog('setTitle', '编辑map数据2');
   	}
   	
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
   	
   	function showmapAddMap() {
		 var html ='';
		 var columnFields = getaddCols();
		 //alert(columnFields)
		//var columnFields = $('#dg').datagrid('getColumnFields');  
		for ( var i = 0; i <columnFields.length; i++){
			 var itemarr = columnFields[i].toString().split("$");
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
		$('#dlgmapadd').dialog({
	     	onLoad:function(){
	    		$('#fmadd').form('clear');
			    $('#addaccordion').accordion('select','基本信息');
			    
			    $("#fmtable").html(html);
			    //	$("#fmtable").after(html);
			    	for ( var i = 0; i <columnFields.length; i++){
			    		 var itemarr = columnFields[i].toString().split("$");
			    		$.parser.parse($('#'+itemarr[itemarr.length-1]));
			    		//console.log($('#'+columnFields[i]));
			    	}
			    	
	    },
	    href:'<%=request.getContextPath()%>/views/xdb/addmap.jsp',
       modal: true,
       closed: true});
		$('#dlgmapadd').dialog('open');
		$('#dlgmapadd').dialog('setTitle', '添加map');
	}
});   	

function submitmapadd() {
	url = '<%=request.getContextPath()%>/xdbMapData/addmapobject';
	//	alert(fieldkeys)
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
				//$('#dlgmapadd').window('close');
			}else{
				$('#addaccordion').accordion('select','基本信息');
				//$('#dlgmapadd').window('close');
			}
		});			
	}else{
		$('#addaccordion').accordion('select','基本信息');
	}
}
</script>

<div id="ddmap" >
	</div>
            <table id="dgmap">
   	 		</table>
   	 		
   	<div id="dlgmapadd"  style="width: 800px;overflow: auto;" closed="true" buttons="#dlgmapadd-buttons" align="center">
		</div>
	<div id="dlgmapadd-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitmapadd()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgmapadd').dialog('close');">取消</a>
	</div>
   	 