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
			pageSize:20,
			singleSelect:true,
			rownumbers:true,
			fitColumns:true,
			fit:true,
			 loadMsg: "数据加载中，请稍后...",
			pagination:true,
			columns: [colss],
			onBeforeLoad:function(param) {
			       },
			       
			   //   onDblClickCell: onClickRow
			});
	});    
   	
  //点击列表变成文本框,进入可编辑状态

    $(function () {
        var doc = $(document),
            table = $("#dg");
        doc.on("mousedown", ".btnEdit", function () {

            var th = $(this),
                ind = th.attr("index"),
                keyid = th.attr("keyid");

            type = th.attr("type");
            if (th.hasClass("btnEdit")) {
                if (type == "edit") {

                    table.datagrid("beginEdit", ind);
                    setTimeout(function () {
                        th.html("保存")
                          .attr("type", "sava");
                        var _ele = table.datagrid('getEditors', ind);


                    })
                } else if (type == "sava") {
                    var ele = table.datagrid('getEditors', ind);

                    table.datagrid("endEdit", ind);//结束编辑



                    //执行保存的操作
                    $.ajax({
                        url: '@Url.Action("ActionName", "ControllerName")',
                        data: { "ID": keyid, "Score": Score },
                        type: "POST",
                        async: true,
                        dataType: "json",
                        success: function (result) {
                            if (result.Success == true) {
                                RefreshData()
                            } else {
                                //alert('失败');
                            }
                        }
                    });
                }
            }
        })


        doc.on("mousedown", ".datagrid-editable-input", function () {
            //alert('点击了文本框');
            console.log(this);
            //PersonSelect('11', '11txt');

            var user = $(this).val();
          // $(this).val(user.UserName);

        });


    })

   	
</script>
</head>
<body class="easyui-layout" fit="true">
	
		<div region="center" border="false" style="overflow: auto;">
		<table id="dg"  title="table--><%=request.getParameter("tablename")%>" >
		</table>
	</div>
 
</table>
	</div>
	<div id="dlgedit-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitforEditRole()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgedit').dialog('close');">取消</a>
	</div>
</body>
</html>