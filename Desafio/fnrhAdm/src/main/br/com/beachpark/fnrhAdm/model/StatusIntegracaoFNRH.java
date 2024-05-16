package br.com.beachpark.fnrhAdm.model;

/**
 * Enum que representa as opções de status de integração da fnrh.
 * <ul> Opções <ul>
 * <li> IMP("Importado") - Quando a reserva confirmada é importada da base CM para a FNRH</li>
 * <li> MAI("Email enviado") - Quando é enviado email ao hóspede responsável pela reserva com o link de preenchimento da FNRH<br>
 * 								A reserva para do status IMP para MAI
 *  </li>
 * <li> ERM("Erro no envio do e-mail") - Quando ocorre alguma falha ao enviar o email para o hóspede responsável pela reserva</li>
 * <li> EIV("Email inválido") - Quando o email do responsável da reserva é dectado como inválido. A reserva passa do status IMP para EIV</li>
 * <li> HSE("Hóspede sem email") - Quando é detectado que o hóspede da reserva não tem email cadastrado. A reserva passa do status IMP para HSE</li>
 * <li> PRE("Em preenchimento") - Quando o hóspede clica no link ou acessa o site pra preencher a reserva ela muda do status IMP para PRE</li>
 * <li> DIG("Digitado pelo hóspede") - Quando o hóspede preenche todas as fnrhs da sua reserva o status muda PRE para DIG</li>
 * <li> EXP("Exportado") - Quando são exportadas para o CM todas as informações pertinentes às fnrhs de uma determina reserva</li>
 * <li> ERE("Erro na exportação") - Quando por ventura ocorrer um erro ao exportar os dados da reserva para o CM</li>
 * <li> CAN("Cancelado") - </li>
 * <li> IGN("Ignorada") - Quando por alguma falha na reserva o recepcionista a ignora do processo de exportação para o CM </li>
 * <li> CAT("Checkin atualizado") - Quando após os dados exportados ocorre o checkin e são atualizados os dados finais referentes ao histórico de estada </li>
 * <li> EAC("Erro ao atualizar checkin") - Quando ocorrer alguma falha na atualização das tabelas do cm após o processo de checkin </li>
 * 
 * @author LesteTI
 *
 */
public enum StatusIntegracaoFNRH {
	
	IMP("Importado"),
	MAI("Email enviado"),
	ERM("Erro no envio do e-mail"),
	EIV("Email inválido"),
	PRE("Em preenchimento"),
	DIG("Digitado pelo hóspede"),
	EXP("Exportado"),
	ERE("Erro na exportação"),
	CAN("Cancelada"),
	HSE("Hóspede sem email"),
	IGN("Ignorada"),
	CAT("Checkin atualizado"),
	EAC("Erro ao atualizar checkin");
	//Reserva não pertence ao cliente
	
	private String descricao;
		
	private StatusIntegracaoFNRH (String descricao){
		this.descricao=descricao;
	}
	
	@Override
	public String toString(){
		return getDescricao();
	}	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}








