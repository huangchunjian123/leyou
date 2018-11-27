<%@ page contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path ;
%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<style type="text/css">
.div3{border-style:solid; border-width:5px;height:0px; border-color:#F0FFF0 }
</style>
<script type="text/javascript">
var treeTitle = '选择列表';
var treeUrl = '<%=request.getContextPath()%>/menu/ctrlTree';
var nodeExp = false;
var nodekeep = "";
var rows;
var noinf = 0;
$(function () {
    $('#treewindow').window({
        title: treeTitle,
        width: 400,
        height: 400,
        modal: true,
        shadow: false,
        closed: true,
        resizable: false,
        maximizable: false,
        minimizable: false,
        collapsible: false
    });
});

function treeWindowOpen(name, rowIndx) {
    $('#treewindow').window('open');
    nodekeep = "";
    nodeExp = false;
    rows = rowIndx.toString();
    $('#basetree').tree({
        checkbox: true,
        animate: true,
        url: '<%=request.getContextPath()%>/menu/ctrlTree' ,
        cascadeCheck: true,
        onlyLeafCheck: false,
        onBeforeExpand: function (node, param) {
            //------------第一种方法：异步加载子节点值-------------
            // $('#basetree').tree('options').url = "../DataAshx/getTreeNode.ashx?pid=" + node.id+"&coln="+escape(name.toString());

            //------------第二种方法：Ajax方法返回子节点Json值，使用append方法加载子节点
            $.ajax({
                type: "POST",
                url: treeUrl+ "?pid=" + node.id ,
                cache: false,
                async: false,
                dataType: "json",
                success: function (data) {
                    if (nodekeep.indexOf(node.id) == -1) {
                        append(data, node);
                        nodeExp = true;
                    }
                }
            });
            $("#radCollapse").removeAttr("checked");
        },
        onLoadError: function (Error) {
            $.messager.alert('提示', '查询语句出错', 'error');
            if (nodeExp == false) {
                $("#basetree").children().remove();
            }
        },
        onLoadSuccess: function (success) {
            var child = $("#basetree").children().length;
            noinf++;
            if (child == 0 && noinf > 1) {
                $.messager.alert('提示', '数据不存在', 'Info');
            }
        }
    });

}

function treeWindowClose() {
    $('#treewindow').window('close');
    nodekeep = "";
    nodekeep = false;
}

function treeWindowSubmit() {
    var nodes = $('#basetree').tree('getChecked');
    var info = '';
    if (nodes.length > 0) {
        for (var i = 0; i < nodes.length; i++) {
            if (info != '') {
                info += ',';
            }
            info += nodes[i].text;
        }
//alert(JSON.stringify(nodes));
    }
    else {
        var node = $('#basetree').tree('getSelected');
        if (node != null) {
            info = node.text;
        }
    }
    $("#" + rows).val(info);
    $('#treewindow').window('close');
    nodekeep = "";
    nodeExp = false;
}

//全部展开
function collapseAll() {
    $("#radCollapse").attr("checked", "checked");
    var node = $('#basetree').tree('getSelected');
    if (node) {
        $('#basetree').tree('collapseAll', node.target);
    } else {
        $('#basetree').tree('collapseAll');
    }
}

//全部收缩
function expandAll() {
    var node = $('#basetree').tree('getSelected');
    if (node) {
        $('#basetree').tree('expandAll', node.target);
    } else {
        $('#basetree').tree('expandAll');
    }
}

//增加子节点
function append(datas, cnode) {
    var node = cnode;
    $('#basetree').tree('append', {
        parent: node.target,
        data: datas
    });
    nodekeep += "," + node.id;
}

//重新加载
function reload() {
    var node = $('#basetree').tree('getSelected');
    if (node) {
        $('#basetree').tree('reload', node.target);
    } else {
        $('#basetree').tree('reload');
    }
}

//删除子节点
function remove() {
    var node = $('#basetree').tree('getSelected');
    $('#basetree').tree('remove', node.target);
}
	/* #FFFAFA */
</script>
<div class="easyui-accordion" fit="true"  class="div1">
	<div title="菜单"  class="div2" >
		<div id="menuPanel" fit="true"  title="功能菜单"   class="div3" style="width: 186px;">
			<ul id="basetree" style="background:#FFFAFA; color:#242424 ;"  ></ul>
		</div>
	</div>
</div>