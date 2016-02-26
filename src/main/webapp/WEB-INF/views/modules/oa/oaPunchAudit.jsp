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
		<li><a href="${ctx}/oa/oaPunch/">审批列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="oa:oaPunch:edit">${oaPunch.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="oa:oaPunch:edit">查看</shiro:lacksPermission></a></li>
	</ul>
		<form:form id="inputForm" modelAttribute="oaPunch" action="${ctx}/oa/oaPunch/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>${oaPunch.act.taskName}</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td><td>${oaPunch.user.name}</td>
					<td class="tit">部门</td><td>${oaPunch.office.name}</td>
					<td class="tit">岗位职级</td><td>${fns:getDictLabel(oaPunch.post, 'user_post', '')}</td>
				</tr>
				
				<tr>
					<td class="tit">未打卡时间</td><td><fmt:formatDate value="${oaPunch.punchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="tit">未打卡原因</td><td colspan="3">${oaPunch.reason}</td>
				</tr>
				
				<tr>
					<td class="tit">备注</td>
					<td colspan="5">${oaPunch.remarks}</td>
				</tr>

			
				<tr>
					<td class="tit">组长意见意见</td>
					<td colspan="5">
						${oaPunch.audit1Text}
					</td>
				</tr>
				<tr>
					<td class="tit">部门经理意见</td>
					<td colspan="5">
						${oaPunch.audit2Text}
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
			<shiro:hasPermission name="oa:oaPunch:edit">
				<c:if test="${oaPunch.act.taskDefKey eq 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="兑 现" onclick="$('#flag').val('yes')"/>&nbsp;
				</c:if>
				<c:if test="${oaPunch.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${oaPunch.act.procInsId}"/>
	</form:form>
</body>
</html>
