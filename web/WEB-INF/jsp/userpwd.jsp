<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户修改密码页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post"
              action="${pageContext.request.contextPath }/user/modifyPwd" >
            <div>
                <label for="oldUserPwd">旧密码：</label>
                <input type="text" name="oldUserPwd" id="oldUserPwd" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="newUserPwd">新密码：</label>
                <input type="password" name="newUserPwd" id="newUserPwd" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="ruserPassword">确认密码：</label>
                <input type="password" name="ruserPassword" id="ruserPassword" value="">
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="button" name="add" id="add" value="保存" >
                <input type="button" id="back" name="back" value="返回" >
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>

