<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
  <div class="right">
      <div class="location">
          <strong>你现在所在的位置是:</strong>
          <span>供应商管理页面 >> 供应商修改页</span>
      </div>
      <div class="providerAdd">
          <fm:form id="providerForm" name="providerForm" method="post" action="${pageContext.request.contextPath }/provider/modifySave.html">
              <input type="hidden" name="id" value="${provider.id }"/>
              <!--div的class 为error是验证错误，ok是验证成功-->
              <div class="">
                  <label for="proCode">供应商编码：</label>
                  <input type="text" name="proCode" id="proCode" value="${provider.proCode }" readonly="readonly"> 
              </div>
              <div>
                  <label for="proName">供应商名称：</label>
                 <input type="text" name="proName" id="proName" value="${provider.proName }"> 
			<font color="red"><fm:errors path="proName"/></font>
              </div>
              
              <div>
                  <label for="proContact">联系人：</label>
                  <input type="text" name="proContact" id="proContact" value="${provider.proContact }"> 
			<font color="red"><fm:errors path="proContact"/></font>
              </div>
              
              <div>
                  <label for="proPhone">联系电话：</label>
                  <input type="text" name="proPhone" id="proPhone" value="${provider.proPhone }"> 
			<font color="red"><fm:errors path="proPhone"/></font>
              </div>
              
              <div>
                  <label for="proAddress">联系地址：</label>
                  <input type="text" name="proAddress" id="proAddress" value="${provider.proAddress }"> 
              </div>
              
              <div>
                  <label for="proFax">传真：</label>
                  <input type="text" name="proFax" id="proFax" value="${provider.proFax }">
              </div>
              
              <div>
                  <label for="proDesc">描述：</label>
                  <input type="text" name="proDesc" id="proDesc" value="${provider.proDesc }"> 
              </div>
              <div class="providerAddBtn">
                  <input type="button" name="save" id="save" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
              </div>
          </fm:form>
      </div>
  </div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/providermodify.js"></script>