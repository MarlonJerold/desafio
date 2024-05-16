package br.com.beachpark.fnrhAdm.service;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import br.com.beachpark.fnrhAdm.model.Log;
import br.com.beachpark.fnrhAdm.model.TipoLog;
import br.com.beachpark.fnrhAdm.repo.LogRepo;
import br.com.padrao.repo.TimeServiceMySql;
import br.com.padrao.util.GeradorUtil;

@Name("logService")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class LogService implements Serializable {


	private static final long serialVersionUID = -4337511210504903297L;

	@In
	LogRepo logRepo;
	
	@In
	TimeServiceMySql timeServiceMySql;
	
	@In(required=false)
	FacesContext facesContext;

	public String getIp() {
		String ip = "";
		if(facesContext!=null){
			ip =((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRemoteAddr();
		}
		return ip;
	}
	
	public String getNovoUUID(){
		return GeradorUtil.geraCodidoUUID();
	}
	
	@Transactional
	public void adicionaLog(TipoLog tipo,String uuid,String descricao,String erro,String reserva){
		Log novoLog = new Log();		
		if(StringHelper.isEmpty(uuid)){
			novoLog.setUuid(getNovoUUID());
		}else{
			novoLog.setUuid(uuid);
		}
		novoLog.setTipo(tipo);		
		novoLog.setDataHora(timeServiceMySql.getDatabaseTimestamp());
		novoLog.setDescricao(descricao);
		novoLog.setIp(getIp());		
		novoLog.setReserva(null);
		if(StringHelper.isNotEmpty(reserva)){
			novoLog.setReserva(reserva);
		}		
		novoLog.setDetalhe(erro);
		logRepo.persist(novoLog);		
	}

}
