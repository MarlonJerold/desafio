<%@ page import="org.eclipse.birt.report.resource.BirtResources" %>

<%-- Map Java resource messages to Javascript constants --%>
<script type="text/javascript">
// <![CDATA[	
	// Error msgs
	Constants.error.invalidPageRange = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.dialog.page.error.invalidpagerange" )%>';
	Constants.error.parameterRequired = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.error.parameterrequired" )%>';
	Constants.error.parameterNotAllowBlank = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.error.parameternotallowblank" )%>';
	Constants.error.parameterNotSelected = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.error.parameternotselected" )%>';
	Constants.error.invalidPageNumber = 'Número da página inválido';
	Constants.error.unknownError = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.error.unknownerror" )%>';
	Constants.error.generateReportFirst = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.error.generatereportfirst" )%>';
	Constants.error.printPreviewAlreadyOpen = '<%= BirtResources.getJavaScriptMessage( "birt.viewer.dialog.print.printpreviewalreadyopen" )%>';
	Constants.error.confirmCancelTask = 'Você deseja cancelar a operação?';
// ]]>
</script>
