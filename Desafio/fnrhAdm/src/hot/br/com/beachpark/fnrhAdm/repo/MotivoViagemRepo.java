package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.MotivoViagem;
import br.com.padrao.base.EMMySql;


@Name("motivoViagemRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class MotivoViagemRepo implements Serializable {

	
	private static final long serialVersionUID = 4779061752053849510L;
	
	@In
	private EMMySql emMySql;
	
	public MotivoViagem getPorId(Long id) {
		return emMySql.find(MotivoViagem.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public MotivoViagem getPorCodigoErp(String codMotivoErp,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MotivoViagem mv ");
		hql.append("where mv.codigoErp=:codMotivoErp ");
		hql.append("  and mv.tipoLingua=:tipoLingua ");
		hql.append(" order by mv.id desc");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("codMotivoErp", codMotivoErp);
		query.setParameter("tipoLingua", tipoLingua);
		
		
		List<MotivoViagem> motViagem = query.getResultList();
		if(motViagem.size()>0){
			return (MotivoViagem) motViagem.get(0);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoViagem> getPorTipoLingua(String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MotivoViagem mv ");
		hql.append("where mv.tipoLingua=:tipoLingua ");		
		Query query = emMySql.createQuery(hql.toString());		
		query.setParameter("tipoLingua", tipoLingua);		
		
		List<MotivoViagem> motivosViagem = query.getResultList();
		return motivosViagem;
	}
	
	@SuppressWarnings("unchecked")
	public MotivoViagem getPorCodigo(String codigo,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MotivoViagem mv ");
		hql.append("where mv.codigo=:codigo ");
		hql.append("  and mv.tipoLingua=:tipoLingua ");
		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("codigo", codigo);
		query.setParameter("tipoLingua", tipoLingua);
		
		
		List<MotivoViagem> motViagem = query.getResultList();
		if(motViagem.size()>0){
			return (MotivoViagem) motViagem.get(0);
		}else{
			return null;
		}
		
	}
	
	
	

}
