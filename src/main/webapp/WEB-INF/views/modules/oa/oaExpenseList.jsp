<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报销申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/oaExpense/">报销申请列表</a></li>
		<shiro:hasPermission name="oa:oaExpense:edit"><li><a href="${ctx}/oa/oaExpense/form">报销申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaExpense" action="${ctx}/oa/oaExpense/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<label>姓名：</label>
		<sys:treeselect id="user" name="user.id" value="${oaExpense.user.id}" labelName="user.name" labelValue="${oaExpense.user.name}" 
			title="用户" url="/sys/office/treeData?type=3" cssStyle="width:150px" allowClear="true" notAllowSelectParent="true"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>部门</th>
				<th>岗位</th>
				<th>报销类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="oa:oaExpense:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaExpense">
			<tr>
				<td><a href="${ctx}/oa/oaExpense/form?id=${oaExpense.id}">${oaExpense.user.name}</a></td>
				<td>${oaExpense.office.name}</td>
				<td>${fns:getDictLabel(oaExpense.post, 'user_post', '')}</td>
				<td>${oaExpense.type}</td>
				<td><fmt:formatDate value="${oaExpense.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="oa:oaExpense:edit"><td>
    				<a href="${ctx}/oa/oaExpense/form?id=${oaExpense.id}&act.procInsId=${oaExpense.procInsId}">详情</a>
					<a href="${ctx}/oa/oaExpense/delete?id=${oaExpense.id}" onclick="return confirmx('确认要删除该报销申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>