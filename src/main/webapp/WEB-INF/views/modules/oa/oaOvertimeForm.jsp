<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加班申请管理</title>
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
		<li><a href="${ctx}/oa/oaOvertime/">加班申请列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaOvertime/form?id=${oaOvertime.id}">加班申请<shiro:hasPermission name="oa:oaOvertime:edit">${not empty oaOvertime.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaOvertime:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaOvertime" action="${ctx}/oa/oaOvertime/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		

<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">申请用户：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<sys:treeselect id="user" name="user.id" value="${oaOvertime.user.id}" labelName="user.name" labelValue="${oaOvertime.user.name}" --%>
<%-- 					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">归属部门：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<sys:treeselect id="office" name="office.id" value="${oaOvertime.office.id}" labelName="office.name" labelValue="${oaOvertime.office.name}" --%>
<%-- 					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->

		<div class="control-group">
			<label class="control-label">申请用户：</label>
			<div class="controls">
				<form:hidden path="user.id" value="${oaOvertime.user.id}" />${oaOvertime.user.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门：</label>
			<div class="controls">
				<form:hidden path="office.id" value="${oaOvertime.office.id}" />${oaOvertime.office.name}
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
			<label class="control-label">加班事由：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班开始时间：</label>
			<div class="controls">
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaOvertime.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oaOvertime.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见1：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit1Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见2：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit2Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见3：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit3Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见4：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit4Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见5：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit5Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">审批意见6：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="audit6Text" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">加班时长：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="count" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">加班总时长：</label> -->
<!-- 			<div style="display:none"> -->
<%-- 				<form:input path="totalCount" htmlEscape="false" maxlength="32" id="getTotal"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">备用字段1：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="backup1" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">备用字段2：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="backup2" htmlEscape="false" maxlength="64" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">备用字段3：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="backup3" htmlEscape="false" maxlength="64" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaOvertime:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty oaOvertime.id}">
			<act:histoicFlow procInsId="${oaOvertime.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>