<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!-- /* -->
<!--  * Hackerspace Bremen Backend - An Open-Space-Notifier -->
<!--  *  -->
<!--  * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com> -->
<!--  *  -->
<!--  * This program is free software; you can redistribute it and/or modify it under the terms of the  -->
<!--  * GNU General Public License as published by the Free Software Foundation; either version 3 of  -->
<!--  * the License, or (at your option) any later version. -->
<!--  *  -->
<!--  * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;  -->
<!--  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See  -->
<!--  * the GNU General Public License for more details. -->
<!--  *  -->
<!--  * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html. -->
<!--  *  -->
<!--  * Contributors: -->
<!--  *     Steve Liedtke <sliedtke57@gmail.com> -->
<!--  */ -->
<%@ page import="com.google.inject.Injector"%>
<%@ page import="com.google.inject.Guice"%>
<%@ page import="de.hackerspacebremen.domain.api.PropertyService" %>
<%@ page import="de.hackerspacebremen.valueobjects.PushProperties" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final PushProperties properties = inj.getInstance(PropertyService.class).fetchPushProperties();
%>

<html>
  <jsp:include page="basicJSPs/head.jsp">
  	<jsp:param name="child" value="true" />
  </jsp:include>

  <body>
   	<jsp:include page="basicJSPs/nav.jsp" />
   	<div class="row">
   		<div class="large-9 push-3 columns">
	      
	      <h2>Push-Benachrichtigungen</h2>
		  
		  <form action="/admin/push" method="POST">
		  	<div class="row">
			  	<fieldset id="gcm_field">
			  		<div class="row">
			  			<div class="large-11 columns">
						  	<h3>Google Cloud Messaging</h3>
						</div>
						<div class="large-1 columns">
						  	<%
						  	if(properties.isGcmEnabled()){
						  	%>
						  	<input type="checkbox" id="gcm_enable" name="gcm_enable" value="true" checked="checked">
						  	<%
						  	}else{ 
						  	%>
						  	<input type="checkbox" id="gcm_enable" name="gcm_enable" value="false">
						  	<%
						  	} 
						  	%>
					  	</div>
					</div>
					<br/>
					<div class="row">
						  	<%
						  	if(request.getParameter("validError") == null){
						  	%>
						  	<div class="large-8 columns">
							  	<label>Google API Key:</label>
							  	<input type="text" id="gcm_key" name="gcm_key" value="<%=properties.getGcmApiKey() %>">
							</div>
						  	<%
						  	} else{
						  	%>
						  	<div class="large-8 columns error">
							  	<label>Google API Key:</label>
							  	<input type="text" id="gcm_key" name="gcm_key" value="<%=properties.getGcmApiKey() %>">
							  	<small class="error">Der Key muss mindestens ein Zeichen besitzen!</small>
							</div>
						  	<%
						  	} 
						  	%>
					</div>
				  </fieldset>
			  </div>
			  <div class="row">
				  <fieldset id="apns_field">
			  			<div class="large-11 columns">
						  	<h3>Apple Push Notification Service</h3>
						</div>
						<div class="large-1 columns">
							<%
						  	if(properties.isApnsEnabled()){
						  	%>
						  	<input type="checkbox" id="apns_enable" name="apns_enable" value="true" checked="checked">
						  	<%
						  	}else{
						  	%>
						  	<input type="checkbox" id="apns_enable" name="apns_enable" value="false">
						  	<%
						  	} 
						  	%>
					  	</div>
					<br/>
					<!-- TODO -->
				  </fieldset>
			  </div>
			  <div class="row">
			  	<fieldset id="mpns_field">
		  			<div class="large-11 columns">
					  	<h3>Microsoft Push Notification Service</h3>
					</div>
					<div class="large-1 columns">
					  	<%
					  	if(properties.isApnsEnabled()){
					  	%>
					  	<input type="checkbox" id="mpns_enable" name="mpns_enable" value="true" checked="checked">
					  	<%
					  	}else{
					  	%>
					  	<input type="checkbox" id="mpns_enable" name="mpns_enable" value="false">
					  	<%
					  	}
					  	%>
				  	</div>
				<br/>
				<!-- TODO -->
			  	</fieldset>
			  </div>
			  
			  <div class="row">
			  	<div class="large-4 large-offset-8 columns">
			  		<input type="submit" value="Speichern" class="large button expand success">
			  	</div>
			  </div>
		  </form>
		        
	    </div>
	    
	    <jsp:include page="basicJSPs/adminMenue.jsp" />
   	</div>
   	
   	<jsp:include page="basicJSPs/footer.jsp" />
  </body>
</html>