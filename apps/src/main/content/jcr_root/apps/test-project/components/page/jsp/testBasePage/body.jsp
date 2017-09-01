<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<%
    StringBuffer cls = new StringBuffer();
    for (String c: componentContext.getCssClassNames()) {
        cls.append(c).append(" ");
    }
%>
<body class="<%= cls %>">
    <div class="bg" id="main_bg">
        <cq:include script="header.jsp"/>
        <cq:include script="content.jsp"/>
        <cq:include script="footer.jsp"/>
    </div>
</body>
