<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假申请管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaLeave/">请假申请列表</a></li>
		<shiro:hasPermission name="oa:oaLeave:edit"><li><a href="${ctx}/oa/oaLeave/form">请假申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaLeave" action="${ctx}/oa/oaLeave/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<label>请假时间：</label>
		<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaLeave.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>&nbsp;
					
		<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
		value="<fmt:formatDate value="${oaLeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>&nbsp;
		
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>部门</th>
				<th>岗位</th>
				<th>请假类型</th>
				<th>请假时间</th>
				<th>请假时长(天)</th>
				<th>审批状态</th>
				<shiro:hasPermission name="oa:oaLeave:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaLeave">
			<tr>
<%-- 				<td><a href="${ctx}/oa/oaLeave/form?id=${oaLeave.id}">${oaLeave.user.name}</a></td> --%>
				<td>${oaLeave.user.name}</td>
				<td>${oaLeave.office.name}</td>
				<td>${fns:getDictLabel(oaLeave.post, 'user_post', '')}</td>
				<td>${fns:getDictLabel(oaLeave.type, 'oa_leave_type', '')}</td>
				<td><fmt:formatDate value="${oaLeave.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;~&nbsp;&nbsp;
					<fmt:formatDate value="${oaLeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${oaLeave.leaveTime}</td>
				<c:if test="${oaLeave.status==0}"><td style="color:#FF9900">${fns:getDictLabel(oaLeave.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaLeave.status==1}"><td style="color:#3EC01D">${fns:getDictLabel(oaLeave.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaLeave.status==2}"><td style="color:red">${fns:getDictLabel(oaLeave.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaLeave.status==null}"><td></td></c:if>
				<shiro:hasPermission name="oa:oaLeave:edit"><td>
    				<a href="${ctx}/oa/oaLeave/form?id=${oaLeave.id}&act.procInsId=${oaLeave.procInsId}">详情</a>
<%-- 					<a href="${ctx}/oa/oaLeave/delete?id=${oaLeave.id}" onclick="return confirmx('确认要删除该请假申请吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>