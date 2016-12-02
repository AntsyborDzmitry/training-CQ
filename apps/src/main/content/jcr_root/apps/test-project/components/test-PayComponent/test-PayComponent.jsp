<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false" %>

<c:set var="paths" value="<%= properties.get("path", String[].class) %>" />
<c:set var="titles" value="<%= properties.get("text", String[].class) %>" />

<c:set var="titlesLength" value="${fn:length(titles)}" />
<c:set var="pathsLength" value="${fn:length(paths)}" />
<c:set var="dataLength" value="${pathsLength}" />

<%--if collections has not equals length set the shortest --%>
 <c:choose>
     <c:when test="${titlesLength > pathsLength} >">
            <c:set var="dataLength" value="${titlesLength}" />
     </c:when>
 </c:choose>

<div class="payable-content">
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <c:forEach var="i" begin="0" end="${dataLength - 1}">
                 <c:choose>
                     <c:when test="${i == 0 }">
                           <li data-target="#myCarousel" data-slide-to="${i}" class="active"></li>
                     </c:when>
                     <c:otherwise>
                            <li data-target="#myCarousel" data-slide-to="${i}"></li>
                     </c:otherwise>
                 </c:choose>
            </c:forEach>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <c:forEach var="i" begin="0" end="${dataLength - 1}">
                <c:choose>
                     <c:when test="${i == 0}">
                           <div class="item active">
                               <img src="${paths[i]}" alt="altText">
                               <div class="carousel-caption">
                                   <h3>${titles[i]}</h3>
                               </div>
                           </div>
                     </c:when>
                     <c:otherwise>
                          <div class="item">
                             <img src="${paths[i]}" alt="altText">
                             <div class="carousel-caption">
                                 <h3>${titles[i]}</h3>
                             </div>
                          </div>
                     </c:otherwise>
                 </c:choose>
            </c:forEach>
        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>