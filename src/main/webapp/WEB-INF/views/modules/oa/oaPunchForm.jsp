<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>未打卡申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		<li><a href="${ctx}/oa/oaPunch/">未打卡申请列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaPunch/form?id=${oaPunch.id}">未打卡申请<shiro:hasPermission name="oa:oaPunch:edit">${not empty oaPunch.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaPunch:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaPunch" action="${ctx}/oa/oaPunch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">流程实例ID：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="procInsId" htmlEscape="false" maxlength="64" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">申请用户：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<sys:treeselect id="user" name="user.id" value="${oaPunch.user.id}" labelName="user.name" labelValue="${oaPunch.user.name}" --%>
<%-- 					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">归属部门：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<sys:treeselect id="office" name="office.id" value="${oaPunch.office.id}" labelName="office.name" labelValue="${oaPunch.office.name}" --%>
<%-- 					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">申请用户：</label>
			<div class="controls">
				<form:hidden path="user.id" value="${oaPunch.user.id}" />${oaPunch.user.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门：</label>
			<div class="controls">
				<form:hidden path="office.id" value="${oaPunch.office.id}" />${oaPunch.office.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位：</label>
			<div class="controls">
				<form:select path="post" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_post')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未打卡原因：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">未打卡时间：</label>
			<div class="controls">
				<input name="punchTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaPunch.punchTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaPunch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>