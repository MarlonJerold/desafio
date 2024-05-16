package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.MeioTransporte;
import br.com.padrao.base.EMMySql;


@Name("meioTransporteRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class MeioTransporteRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1427524932044657019L;
	
	@In
	private EMMySql emMySql;
	
	public MeioTransporte getPorId(Long id) {
		return emMySql.find(MeioTransporte.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public MeioTransporte getPorCodigoErp(String codMeioErp,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MeioTransporte mt ");
		hql.append("where mt.codigoErp=:codMeioErp ");
		hql.append("  and mt.tipoLingua=:tipoLingua ");
		hql.append(" order by mt.id desc");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("codMeioErp", codMeioErp);
		query.setParameter("tipoLingua", tipoLingua);
		
		
		List<MeioTransporte> meioTransporte = query.getResultList();
		if(meioTransporte.size()>0){
			return (MeioTransporte) meioTransporte.get(0);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MeioTransporte> getPorTipoLingua(String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MeioTransporte mt ");
		hql.append("where mt.tipoLingua=:tipoLingua ");		
		Query query = emMySql.createQuery(hql.toString());		
		query.setParameter("tipoLingua", tipoLingua);
		
		List<MeioTransporte> meiosTransporte = query.getResultList();
		return meiosTransporte;
		
	}	
	
	@SuppressWarnings("unchecked")
	public MeioTransporte getPorCodigo(String cod,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from MeioTransporte mt ");
		hql.append("where mt.codigo=:codigo ");
		hql.append("  and mt.tipoLingua=:tipoLingua ");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("codigo", cod);
		query.setParameter("tipoLingua", tipoLingua);
		
		
		List<MeioTransporte> meioTransporte = query.getResultList();
		if(meioTransporte.size()>0){
			return (MeioTransporte) meioTransporte.get(0);
		}else{
			return null;
		}
		
	}
	

}
