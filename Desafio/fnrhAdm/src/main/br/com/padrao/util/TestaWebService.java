package br.com.padrao.util;


public class TestaWebService {

	public static final String URL_EMPRESA_FILIAL = "/WS/01/010101/";
	public static final String COMPLEMENTO_WSDL = ".apw?WSDL";

	

	public TestConnection tc;

	public TestConnection getTc() {
		return tc;
	}

	public void setTc(TestConnection tc) {
		this.tc = tc;
	}

	public TestaWebService() {
		tc = new TestConnection();
	}

	public boolean isServidorAtivo() {
		boolean retorno = tc.isUrlActive(TomcatUtil
				.obterValorPorChave("wsAddress"), TomcatUtil
				.obterValorPorChave("wsPort"), null);
	/*	boolean retorno = tc.isUrlActive("172.16.1.123", TomcatUtil
				.obterValorPorChave("wsPort"), null);*/
	//	tc = new TestConnection("172.16.1.124",8082,"");
	/*	boolean retorno = tc.isActive(); 
		if(!retorno){
			enviaEmailErro();
		}	*/	
		
		return retorno;
	}

	public boolean isWebServiceAtivo() {
		return tc.isUrlActive(TomcatUtil.obterValorPorChave("wsAddress"),
				TomcatUtil.obterValorPorChave("wsPort"), URL_EMPRESA_FILIAL);
	}

	public boolean isMetodoWebServiceAtivo(String metodo) {
		metodo += COMPLEMENTO_WSDL;
		String link ="http://"+TomcatUtil.obterValorPorChave("wsAddress")+":"+TomcatUtil.obterValorPorChave("wsPort")+URL_EMPRESA_FILIAL+metodo;
		/*String link = "http://" + TomcatUtil.obterValorPorChave("wsAddress")
				+ ":" + TomcatUtil.obterValorPorChave("wsPort")
				+ URL_EMPRESA_FILIAL + metodo;*/
		return tc.testMethodWebService(link);
	}

	public void enviaEmailErro() {
		String erro = "Endereço: " + "http://"
				+ TomcatUtil.obterValorPorChave("wsAddress") + ":"
				+ TomcatUtil.obterValorPorChave("wsPort");
		erro += "<br>";
		erro += "Erro:" + tc.getStatus() + "(" + tc.getDescription() + ")";
		EmailUtil emailUtil = new EmailUtil(EmailUtil.EMAIL_PADRAO,EmailUtil.EMAIL_PADRAO,"Problemas no webservice do protheus",erro);		
		emailUtil.send();

	}

}
