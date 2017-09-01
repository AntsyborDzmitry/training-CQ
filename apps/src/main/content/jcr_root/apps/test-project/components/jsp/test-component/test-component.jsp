<%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %>

<form class="form-inline"  action="#" onsubmit="send(event);">
  <div class="form-group">
    <label for="exampleInputName2">Name</label>
    <input type="text" class="form-control" id="inputName" placeholder="Anc D">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail2">Email</label>
    <input type="email" class="form-control" id="inputEmail" placeholder="anc.d@example.com">
  </div>
  <button type="submit" class="btn btn-primary">Send invitation</button>
</form>
<div class="answer">
    <div class="firstName">first name: <span class="text"></span></div>
    <div class="lastName">last name: <span class="text"></span></div>
    <div class="address">address: <span class="text"></span></div>
</div>