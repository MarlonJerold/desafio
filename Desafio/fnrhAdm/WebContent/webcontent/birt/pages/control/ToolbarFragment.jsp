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
				 org.eclipse.birt.report.resource.BirtResources,
				 org.eclipse.birt.report.utility.ParameterAccessor,
				 org.eclipse.birt.report.servlet.ViewerServlet" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%-----------------------------------------------------------------------------
	Toolbar fragment
-----------------------------------------------------------------------------%>
<TR 
	<%
		if( attributeBean.isShowToolbar( ) )
		{
	%>
		HEIGHT="20px"
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
	<TD COLSPAN='2'>
		<DIV ID="toolbar">
			<TABLE CELLSPACING="1px" CELLPADDING="1px" WIDTH="100%" CLASS="birtviewer_toolbar">
				<TR><TD></TD></TR>
				<TR>
					<TD WIDTH="6px"></TD>
					<TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='toc' SRC="birt/images/Toc.gif"
					   		TITLE="Exibir/Esconder tabela de conteúdo"
					   		ALT="Exibir/Esconder tabela de conteúdo" CLASS="birtviewer_clickable">
					</TD>
					<!--<TD WIDTH="6px"/>
					 <TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='parameter' SRC="birt/images/Report_parameters.gif"
					   		TITLE="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.parameter" )%>"	
					   		ALT="<%= BirtResources.getHtmlMessage( "birt.viewer.toolbar.parameter" )%>" CLASS="birtviewer_clickable">
					</TD> -->
					<TD WIDTH="6px"></TD>
					<TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='export' SRC="birt/images/Export.gif"
					   		TITLE="Exportar dados"
					   		ALT="Exportar dados" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="6px"></TD>
					<TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='exportReport' SRC="birt/images/ExportReport.gif"
					   		TITLE="Exportar relatório"
					   		ALT="Exportar relatório" CLASS="birtviewer_clickable">
					</TD>
					<TD WIDTH="6px"></TD>
					<TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='print' SRC="birt/images/Print.gif"
					   		TITLE="Imprimir"
					   		ALT="Imprimir" CLASS="birtviewer_clickable">
					</TD>
					<%
					if( ParameterAccessor.isSupportedPrintOnServer )
					{
					%>					
					<TD WIDTH="6px"></TD>
					<TD WIDTH="15px">
					   <INPUT TYPE="image" NAME='printServer' SRC="birt/images/PrintServer.gif"
					   		TITLE="Imprimir no servidor"
					   		ALT="Imprimir no servidor" CLASS="birtviewer_clickable">
					</TD>
					<%
					}
					%>										
					<TD ALIGN='right'>
					</TD>
					<TD WIDTH="6px"></TD>
				</TR>
			</TABLE>
		</DIV>
	</TD>
</TR>
