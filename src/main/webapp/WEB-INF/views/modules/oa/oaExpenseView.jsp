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
		<li><a href="${ctx}/oa/oaExpense/">审批列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaExpense/form/?procInsId=${oaExpense.procInsId}">审批详情</a></li>
	</ul>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>
		<fieldset>
			<legend>审批详情</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td><td>${oaExpense.user.name}</td>
					<td class="tit">部门</td><td>${oaExpense.office.name}</td>
					<td class="tit">岗位职级</td><td>${fns:getDictLabel(oaExpense.post, 'user_post', '')}</td>
				</tr>
				
				<tr>
					<td class="tit">报销类型</td><td>${fns:getDictLabel(oaExpense.type, 'expense_type', '')}</td>
					<td class="tit">报销原因</td><td>${oaExpense.reason}</td>
					<td class="tit">报销总额</td><td>${oaExpense.account}</td>
				</tr>
				
				<tr>
					<td class="tit">申请时间</td>
					<td colspan="5"><fmt:formatDate value="${oaExpense.applyTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td class="tit">备注</td>
					<td colspan="5">${oaExpense.remarks}</td>
				</tr>

			
				<tr>
					<td class="tit">组长意见意见</td>
					<td colspan="5">
						${oaExpense.audit1Text}
					</td>
				</tr>
				<tr>
					<td class="tit">部门经理意见</td>
					<td colspan="5">
						${oaExpense.audit2Text}
					</td>
				</tr>
				<tr>
					<td class="tit">行政意见</td>
					<td colspan="5">
						${oaExpense.audit3Text}
					</td>
				</tr>
			</table>
		</fieldset>
		<act:histoicFlow procInsId="${oaExpense.act.procInsId}" />
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
