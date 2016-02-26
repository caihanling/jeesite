<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>未打卡管理</title>
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
	
	<form:form id="searchForm" modelAttribute="oaPunch" action="${ctx}/oa/oaPunch/detail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<label>姓名：</label>
		<sys:treeselect id="user" name="user.id" value="${oaPunch.user.id}" labelName="user.name" labelValue="${oaPunch.user.name}" 
			title="用户" url="/sys/office/treeData?type=3" cssStyle="width:150px" allowClear="true" notAllowSelectParent="true"/>&nbsp;&nbsp;
				
			
		<label>加班时间：</label>
		<input name="backup2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaPunch.backup2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>&nbsp;
					
		<input name="backup3" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
		value="<fmt:formatDate value="${oaPunch.backup3}" pattern="yyyy-MM-dd HH:mm:ss"/>"
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
		
		
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
				<th>未打卡原因</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaPunch">
<%-- 			<c:if test="${oaPunch.count!=null }"> --%>
				<tr>
					<td><a href="${ctx}/oa/oaPunch/form?id=${oaPunch.id}">${oaPunch.user.name}</a></td>
					<td>${oaPunch.office.name}</td>
					<td>${fns:getDictLabel(oaPunch.post, 'user_post', '')}</td>
					<td><fmt:formatDate value="${oaPunch.punchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${oaPunch.reason}</td>
				</tr>
<%-- 			</c:if> --%>
		</c:forEach>
		</tbody>
		
		
		<thead>
			<tr>
				<th colspan="4"></th>
				<th>未打卡总次数(次)</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th colspan="4">总计</th>
				<td>${detail.total}</td>
			</tr>
		</tbody>
		
	</table>
	
	
	<div class="pagination">${page}</div>
</body>
</html>