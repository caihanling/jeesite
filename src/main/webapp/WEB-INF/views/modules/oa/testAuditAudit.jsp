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
		<li><a href="${ctx}/oa/testAudit/">审批列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="oa:testAudit:edit">${testAudit.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="oa:testAudit:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="testAudit" action="${ctx}/oa/testAudit/saveAudit" method="post" class="form-horizontal">
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
					<td class="tit">姓名</td><td>${oaOvertime.createBy.id}</td>
					<td class="tit">加班开始时间</td><td>${oaOvertime.startTime}</td>
					<td class="tit">加班结束时间职级</td><td>${oaOvertime.endTime}</td>
				</tr>
				<tr>
					<td class="tit">调整原因</td>
					<td colspan="5">${oaOvertime.reason}</td>
				</tr>
				<tr>
					<td class="tit" rowspan="3">备注信息</td>
					<td class="tit">备注信息档级</td>
					<td>${oaOvertime.remarks}</td>
				</tr>
			
			
				<tr>
					<td class="tit">借用remarks字段代表意见</td>
					<td colspan="5">
						${testAudit.remarks}
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
			<shiro:hasPermission name="oa:testAudit:edit">
				<c:if test="${testAudit.act.taskDefKey eq 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="兑 现" onclick="$('#flag').val('yes')"/>&nbsp;
				</c:if>
				<c:if test="${testAudit.act.taskDefKey ne 'apply_end'}">
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
