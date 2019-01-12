/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ActionFacade;
import dao.TicketFacade;
import entity.Action;
import entity.Ticket;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;
import javax.ejb.EJB;
import java.util.List;
import javax.inject.Inject;


/**
 *
 * @author Shari
 */
@Named(value = "actionController")
@SessionScoped
public class ActionController implements Serializable {

    @EJB
    private ActionFacade actionFacade;
    @EJB
    private TicketFacade ticketFacade;
    
    @Inject
    private TicketController ticketController;
    
    private Action action = new Action();
    private Ticket ticket = new Ticket();

    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public ActionController() {
    }
    
    public void FindById(BigDecimal id){
        action = this.actionFacade.FindById(id);
    }
       
    public String create(){
        if (this.action.getActionid()==null) {
            this.actionFacade.create(action);
            resetAction();
        }else{
            this.actionFacade.edit(action);
        }
   
        return "ticketList?faces-redirect=true";
    }
    
    public void onload(BigDecimal ticketid){

        LocalDate date = LocalDate.now();
        Date today = Date.valueOf(date);
        action.setCreationdate(today);
        action.setTicketid(this.ticketFacade.FindById(ticketid));
        
    }
    
        public void onload2(BigDecimal actionId){
            if (actionId!=null) {
                this.action=this.actionFacade.FindById(actionId);
                int a = 1 ; 
            }else{
                resetAction();
                LocalDate date = LocalDate.now();
                Date today = Date.valueOf(date);
                action.setCreationdate(today);
                BigDecimal ticketid= ticketController.getTicket().getTicketid();
                action.setTicketid(this.ticketFacade.FindById(ticketid));
            }
   
    }
    
    public List<Action> GetAllActions(){
        return this.actionFacade.GetAllActions();

    }
    
    public List<Action> listAllActionsByTicket(BigDecimal id){
        
        List<Action> l = this.actionFacade.listActionsByTicketId(id);
        return l;        
    }
    
    public String cancel(){
        return "actionList";
    }
    
    public void resetAction(){
        this.action = new Action();
    } 
}
