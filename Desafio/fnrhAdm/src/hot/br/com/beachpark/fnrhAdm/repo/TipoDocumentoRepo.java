package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.TipoDocumento;
import br.com.padrao.base.EMMySql;


/**
 * Entity para Tipo de Documento da base fnrh.(FNRH.TIPODOCUMENTO)
 * 
 * @author LesteTI
 * 
 */
@Name("tipoDocumentoRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class TipoDocumentoRepo implements Serializable {

	private static final long serialVersionUID = -3076233189166184699L;
	
	@In
	private EMMySql emMySql;
	
	public TipoDocumento getPorId(Long id) {
		return emMySql.find(TipoDocumento.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> getAllOrderDescricao() {
		return emMySql.createNamedQuery("tipoDocumento.getAllOrderDescricao").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> getPorTipoLingua(String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from TipoDocumento td ");
		hql.append("where td.tipoLingua=:tipoLingua ");		
		Query query = emMySql.createQuery(hql.toString());		
		query.setParameter("tipoLingua", tipoLingua);		
		
		List<TipoDocumento> tiposDocumento = query.getResultList();
		return tiposDocumento;
	}
	
	@SuppressWarnings("unchecked")
	public TipoDocumento getPorCodigo(String codigo,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from TipoDocumento td ");
		hql.append("where td.tipoLingua=:tipoLingua ");	
		hql.append("and td.codigo=:codigo ");	
		Query query = emMySql.createQuery(hql.toString());		
		query.setParameter("tipoLingua", tipoLingua);		
		query.setParameter("codigo", codigo);	
		
		List<TipoDocumento> tiposDocumento = query.getResultList();	
		if(tiposDocumento.size()>0){
			return (TipoDocumento) tiposDocumento.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public TipoDocumento getPorTipoDocumentoErp(Long id,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from TipoDocumento td ");
		hql.append("where td.tipoLingua=:tipoLingua ");	
		hql.append("and td.tipoDocumentoErp=:id ");	
		Query query = emMySql.createQuery(hql.toString());		
		query.setParameter("tipoLingua", tipoLingua);		
		query.setParameter("id", id);	
		
		List<TipoDocumento> tiposDocumento = query.getResultList();	
		if(tiposDocumento.size()>0){
			return (TipoDocumento) tiposDocumento.get(0);
		}else{
			return null;
		}
	}

}
