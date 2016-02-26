<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/oaLeave/">审批列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaLeave/form/?procInsId=${oaLeave.procInsId}">审批详情</a></li>
	</ul>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>
		<fieldset>
			<legend>审批详情</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td><td>${oaLeave.user.name}</td>
					<td class="tit">部门</td><td>${oaLeave.office.name}</td>
					<td class="tit">岗位职级</td><td>${fns:getDictLabel(oaLeave.post, 'user_post', '')}</td>
				</tr>
				<tr>
					<td class="tit">请假类型</td><td>${oaLeave.type}</td>
					<td class="tit">请假时间</td><td><fmt:formatDate value="${oaLeave.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>～<fmt:formatDate value="${oaLeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="tit">请假时长</td><td>${oaLeave.leaveTime}</td>
				</tr>
				
				<tr>
					<td class="tit">请假原因</td>
					<td colspan="5">${oaLeave.reason}</td>
				</tr>
			
			
			
				<tr>
					<td class="tit">组长意见意见</td>
					<td colspan="5">
						${oaLeave.audit1Text}
					</td>
				</tr>
				<tr>
					<td class="tit">部门经理意见</td>
					<td colspan="5">
						${oaLeave.audit2Text}
					</td>
				</tr>
			</table>
		</fieldset>
		<act:histoicFlow procInsId="${oaLeave.act.procInsId}" />
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
