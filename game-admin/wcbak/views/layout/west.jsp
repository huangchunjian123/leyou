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
	var tree;
	var menuPanel;
	$(function() {
		menuPanel = $('#menuPanel').panel({
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					tree.tree('reload');
				}
			}, {
				iconCls : 'icon-redo',
				handler : function() {
					var node = tree.tree('getSelected');
					if (node) {
						tree.tree('expandAll', node.target);
					} else {
						tree.tree('expandAll');
					}
				}
			}, {
				iconCls : 'icon-undo',
				handler : function() {
					var node = tree.tree('getSelected');
					if (node) {
						tree.tree('collapseAll', node.target);
					} else {
						tree.tree('collapseAll');
					}
				}
			} ]
		});

		var _node;
		tree = $('#menu').tree({
			url : '<%=request.getContextPath()%>/menu/ctrlTree',
			lines : true,
			onClick : function(node) {
				//$('#menu').collapseAll()
				//alert(node.text.toString().substring(1,2))
				
				var p_nodes=$('#menu').tree('getChildren',node.target);
			//	alert(p_nodes.length)
				//if(node.text.toString().substring(2,4)!='t_'){
				  if (p_nodes.length == 0) {	
					  if(node.text.toString().substring(2,4)!='t_'){
					$.post('<%=request.getContextPath()%>/menu/ctrlTreeChilds', { 
			              "id" : node.id,
			              "url" : node.attributes.url
			            }, function(json) {
			            	//alert(json.status)
			            	//var d = $.parseJSON(json);
			            	//alert(d.msg)
			    			//$.messager.show({
			    			//	title : '提示',
			    			//	msg : d.msg
			    			//});
			    			if(json.status==1){
			    				$.messager.show({
					    				title : '提示',
					    				msg : json.msg
					    			});
			    				return;
			    			}

								$('#menu').tree('append', { 
						            	parent : node.target, 
						                data : json 
						              }); 
			    				
			             
			            }, "json"); 
					  } else {
						  parent.addTab(node);
					  }
				}else {
					for(i=0;i<p_nodes.length;i++){
					   $('#menu').tree('remove',p_nodes[i].target);
					}
				}
				
			},
			
			onDblClick : function(node) {
				if (node.state == 'closed') {
					$(this).tree('expand', node.target);
				} else {
					$(this).tree('collapse', node.target);
				}
			},
			onLoadSuccess : function(node,data){
				var roots=$('#menu').tree('getRoots');
				$(this).tree('expand', roots[0].target);
			}
		});		
		
		//增加子节点
		function append(datas, cnode) {
		    var node = cnode;
		    $('#menu').tree('append', {
		        parent: node.target,
		        data: datas
		    });
		}
		
		function Travel(){
			var roots=$('#menu').tree('getRoots');
		   	for(var i=0;i<roots.length;i++){
		     	//alert(roots[i].text);
		     	children=$('#menu').tree('getChildren',roots[i].target);		     
	  		}
		}
	});
	
	function addTab(node) {
		if (centerTabs.tabs('exists', node.text)) {
			refreshTab(node.text);
			centerTabs.tabs('select', node.text);
		} else {
			if (node.attributes.url && node.attributes.url.length > 0) {
					$.messager.progress({
						text : '正在加载...',
						interval : 100
					});
					window.setTimeout(function() {
						try {
							$.messager.progress('close');
						} catch (e) {
						}
					}, 0);
					centerTabs.tabs('add', {
					title : node.text,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="' + node.attributes.url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(node.text);
						}
					} ]
				});
			} else {
				centerTabs.tabs('add', {
					title : node.text,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="error/err.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(node.text);
						}
					} ]
				});
			}
		}
	}
	/* #FFFAFA */
</script>
<div class="easyui-accordion" fit="true"  class="div1">
	<div title="菜单"  class="div2" >
		<div id="menuPanel" fit="true"  title="功能菜单"   class="div3" style="width: 186px;">
			<ul id="menu" style="background:#FFFAFA; color:#242424 ;"  ></ul>
		</div>
	</div>
</div>