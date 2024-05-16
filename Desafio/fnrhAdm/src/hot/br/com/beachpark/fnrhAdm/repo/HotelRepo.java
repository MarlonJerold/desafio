package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Hotel;
import br.com.padrao.base.EMMySql;

@Name("hotelRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class HotelRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4026551486386617397L;
	
	@In
	private EMMySql emMySql;	           
	
	
	public Hotel getPorId(Long id) {
		return emMySql.find(Hotel.class, id);
	}
	
	public void persist(Hotel hotel) {
		emMySql.persist(hotel);
	}
	
	@SuppressWarnings("unchecked")
	public List<Hotel> getAllOrderDescricao() {
		return emMySql.createNamedQuery("hotel.getAllOrderDescricao").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Hotel> getAllAtivosOrderDescricao() {
		return emMySql.createNamedQuery("hotel.getAllAtivosOrderDescricao").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Hotel getPorCodigo(String codErp) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Hotel h ");
		hql.append("where h.codigoErp=:codErp ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("codErp", codErp);
		
		List<Hotel> hoteis = query.getResultList();
		if(hoteis.size()>0){
			return (Hotel) hoteis.get(0);
		}else{
			return null;
		}
		
	}


}
