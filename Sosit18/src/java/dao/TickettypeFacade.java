/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Tickettype;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author c1041184
 */
@Stateless
public class TickettypeFacade extends AbstractFacade<Tickettype> {

    @PersistenceContext(unitName = "Sosit18PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TickettypeFacade() {
        super(Tickettype.class);
    }
    
    public List<Tickettype> GetAllTickettypes(){
        Query q = this.em.createNamedQuery("Tickettype.findAll");
        List<Tickettype> ltt = (List<Tickettype>)q.getResultList() ;
        return ltt;
    }
    
    public Tickettype GetTicketTypeById(BigDecimal id){
        Query q = this.em.createNamedQuery("Tickettype.findByTickettypeid");
        q.setParameter("tickettypeid", id);
        Tickettype tickettype = (Tickettype)q.getSingleResult();
        return tickettype;
    }
        
        
    
}
