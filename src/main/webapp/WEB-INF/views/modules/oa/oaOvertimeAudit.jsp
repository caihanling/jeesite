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
		<li><a href="${ctx}/oa/oaOvertime/">审批列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="oa:oaOvertime:edit">${oaOvertime.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="oa:oaOvertime:edit">查看</shiro:lacksPermission></a></li>
	</ul>
		<form:form id="inputForm" modelAttribute="oaOvertime" action="${ctx}/oa/oaOvertime/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>${oaOvertime.act.taskName}</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td><td>${oaOvertime.user.name}</td>
					<td class="tit">部门</td><td>${oaOvertime.office.name}</td>
					<td class="tit">岗位职级</td><td>${fns:getDictLabel(oaOvertime.post, 'user_post', '')}</td>
				</tr>
				
				
				<tr>
					<td class="tit">加班原因</td><td>${oaOvertime.reason}</td>
					<td class="tit">加班时间</td><td><fmt:formatDate value="${oaOvertime.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;--&nbsp;&nbsp;
						<fmt:formatDate value="${oaOvertime.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="tit">加班时长</td><td>${oaOvertime.count}</td>
				</tr>

			
				<tr>
					<td class="tit">组长意见意见</td>
					<td colspan="5">
						${oaOvertime.audit1Text}
					</td>
				</tr>
				<tr>
					<td class="tit">部门经理意见</td>
					<td colspan="5">
						${oaOvertime.audit2Text}
					</td>
				</tr>
				<tr>
					<td class="tit">您的意见</td>
					<td colspan="5">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaOvertime:edit">
				<c:if test="${oaOvertime.act.taskDefKey eq 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="兑 现" onclick="$('#flag').val('yes')"/>&nbsp;
				</c:if>
				<c:if test="${oaOvertime.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${oaOvertime.act.procInsId}"/>
	</form:form>
</body>
</html>
