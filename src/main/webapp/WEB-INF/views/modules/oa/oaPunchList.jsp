<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>未打卡申请管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaPunch/">未打卡申请列表</a></li>
		<shiro:hasPermission name="oa:oaPunch:edit"><li><a href="${ctx}/oa/oaPunch/form">未打卡申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaPunch" action="${ctx}/oa/oaPunch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<label>未打卡时间：</label>
		<input name="backup2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaPunch.backup2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>&nbsp;
					
		<input name="backup3" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
		value="<fmt:formatDate value="${oaPunch.backup3}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>未打卡时间</th>
				<th>审批状态</th>
<!-- 				<th>更新时间</th> -->
				<shiro:hasPermission name="oa:oaPunch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaPunch">
			<tr>
				<td><a href="${ctx}/oa/oaPunch/form?id=${oaPunch.id}">${oaPunch.user.name}</a></td>
				<td>${oaPunch.office.name}</td>
				<td>${fns:getDictLabel(oaPunch.post, 'user_post', '')}</td>
				<td><fmt:formatDate value="${oaPunch.punchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<c:if test="${oaPunch.status==0}"><td style="color:#FF9900">${fns:getDictLabel(oaPunch.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaPunch.status==1}"><td style="color:#3EC01D">${fns:getDictLabel(oaPunch.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaPunch.status==2}"><td style="color:red">${fns:getDictLabel(oaPunch.status, 'activiti_status', '')}</td></c:if>
				<c:if test="${oaPunch.status==null}"><td></td></c:if>
<%-- 				<td><fmt:formatDate value="${oaPunch.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
				<shiro:hasPermission name="oa:oaPunch:edit"><td>
    				<a href="${ctx}/oa/oaPunch/form?id=${oaPunch.id}&act.procInsId=${oaPunch.procInsId}">详情</a>
<%-- 					<a href="${ctx}/oa/oaPunch/delete?id=${oaPunch.id}" onclick="return confirmx('确认要删除该未打卡申请吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>