<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>当月工作详情管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysUserMonthDetail/">当月工作详情列表</a></li>
		<shiro:hasPermission name="sys:sysUserMonthDetail:edit"><li><a href="${ctx}/sys/sysUserMonthDetail/form">当月工作详情添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysUserMonthDetail" action="${ctx}/sys/sysUserMonthDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>加班时间</th>
				<th>请假时间</th>
				<th>未打卡次数</th>
				<th>报销总额</th>
				<shiro:hasPermission name="sys:sysUserMonthDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUserMonthDetail">
			<tr>
				<td><a href="${ctx}/oa/testAudit/form?id=${sysUserMonthDetail.id}">${sysUserMonthDetail.user.name}</a></td>
				<td>${sysUserMonthDetail.countOvertime}</td>
				<td>${sysUserMonthDetail.countLeave}</td>
				<td>${sysUserMonthDetail.countPunch}</td>
				<td>${sysUserMonthDetail.countExpense}</td>
				<shiro:hasPermission name="sys:sysUserMonthDetail:edit"><td>
    				<a href="${ctx}/sys/sysUserMonthDetail/form?id=${sysUserMonthDetail.id}">修改</a>
					<a href="${ctx}/sys/sysUserMonthDetail/delete?id=${sysUserMonthDetail.id}" onclick="return confirmx('确认要删除该当月工作详情吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>