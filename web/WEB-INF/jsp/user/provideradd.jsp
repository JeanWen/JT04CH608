<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <fm:form method="post" action="${pageContext.request.contextPath}/provider/provideraddsave.html" modelAttribute="provider" >
        供应商编码：<fm:input path="proCode"/><fm:errors path="proCode"/> <br>
        供应商名称：<fm:input path="proName"/><fm:errors path="proName"/> <br>
        联系人：<fm:input path="proContact"/><fm:errors path="proContact"/> <br>
        联系电话：<fm:input path="proPhone"/><fm:errors path="proPhone"/> <br>
        联系地址：<fm:input path="proAddress"/><fm:errors path="proAddress"/> <br>
        传真：<fm:input path="proFax"/><fm:errors path="proFax"/> <br>
        描述：<fm:input path="proDesc"/><fm:errors path="proDesc"/> <br>
        <input type="submit" value="保存">
    </fm:form>
</body>
</html>
