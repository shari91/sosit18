/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ticket;
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
public class TicketFacade extends AbstractFacade<Ticket> {

    @PersistenceContext(unitName = "Sosit18PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }
    
    public Ticket FindById(BigDecimal id){
        Query q = this.em.createNamedQuery("Ticket.findByTicketid");
        q.setParameter("ticketid", id);
        Ticket t = (Ticket)q.getSingleResult();
        return t;
    }
    
    public List<Ticket> GetAllTickets() {
        Query q = this.em.createNamedQuery("Ticket.findAll");
        List<Ticket> l = (List<Ticket>)q.getResultList();
        return l;
    }

    
}
