<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" buffer="none" %>
<%@ page import="org.eclipse.birt.report.presentation.aggregation.IFragment,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.resource.BirtResources" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%-----------------------------------------------------------------------------
	Navigation bar fragment
-----------------------------------------------------------------------------%>
<TR 
	<%	
		String imagesPath = "birt/images/";
	
		if( attributeBean.isShowNavigationbar( ) )
		{
	%>
		HEIGHT="25px"
	<%
		}
		else
		{
	%>
		style="display:none"
	<%
		}
	%>	
>
	<TD>
		<DIV id="navigationBar">
			<TABLE CELLSPACING="0" CELLPADDING="0" WIDTH="100%" HEIGHT="25px" CLASS="birtviewer_navbar">
				<TR><TD></TD></TR>
				<TR>
					<TD WIDTH="6px"></TD>
					<TD>
						<B>
						<%
							if ( attributeBean.getBookmark( ) != null )
							{
						%>
							Página&nbsp;
							<SPAN ID='pageNumber'></SPAN>&nbsp;
							de&nbsp;
							<SPAN ID='totalPage'></SPAN>
						<%
							}
							else
							{
						%>
							Página&nbsp;
							<SPAN ID='pageNumber'><%= ""+attributeBean.getReportPage( ) %></SPAN>&nbsp;
							de&nbsp;
							<SPAN ID='totalPage'></SPAN>
						<%
							}
						%>
						</B>
					</TD>
					
					<TD WIDTH="15px">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"LastPage":"FirstPage") + "_disabled.gif" %>" NAME='first'
							ALT="Primeira Página" 
							TITLE="Primeira Página" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="2px"></TD>
					<TD WIDTH="15px">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"NextPage":"PreviousPage") + "_disabled.gif" %>" NAME='previous' 
							ALT="Página anterior" 
							TITLE="Página anterior" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="2px"></TD>
					<TD WIDTH="15px">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"PreviousPage":"NextPage") + "_disabled.gif" %>" NAME='next'
						    ALT="Próxima página" 
							TITLE="Próxima página" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="2px"></TD>
					<TD WIDTH="15px">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"FirstPage":"LastPage") + "_disabled.gif" %>" NAME='last'
						    ALT="Última página"
							TITLE="Última página" CLASS="birtviewer_clickable">
					</TD>
					
					<TD WIDTH="8px"></TD>
					
					<TD ALIGN="right" WIDTH="84px"><LABEL for="gotoPage"><b>Ir para página</b></LABEL></TD>
					<TD WIDTH="2px"></TD>
					<TD ALIGN="right" WIDTH="50px">
						<INPUT ID='gotoPage' TYPE='text' VALUE='' MAXLENGTH="8" SIZE='5' CLASS="birtviewer_navbar_input">
					</TD>
					<TD WIDTH="4px"></TD>
					<TD ALIGN="right" WIDTH="10px">
						<INPUT TYPE="image" SRC="<%= imagesPath + (attributeBean.isRtl()?"Go_rtl.gif":"Go.gif") %>" NAME='goto'
						    ALT="Ir para página" 
							TITLE="Ir para página" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="6px"></TD>
				</TR>
			</TABLE>
		</DIV>
	</TD>
</TR>
