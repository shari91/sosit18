package controller;

import dao.UseraccountFacade;
import entity.Useraccount;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;

/**
 * Source: How to handle authentication/authorization with users in a database?
 * https://stackoverflow.com/a/9969415 Performing user authentication in Java EE
 * / JSF using j_security_check : answer by BalusC
 * https://stackoverflow.com/a/2207147
 *
 * @author woutbr@student.hik.be
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UseraccountFacade useraccountFacade;
    @ManagedProperty("#{user}")
    private Useraccount useraccount;
    private String username;
    private String password;

    public LoginController() {
        this.clearUsernamePassword();
        this.useraccount = null;
    }

    public Useraccount getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(Useraccount useraccount) {
        this.useraccount = useraccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void clearUsernamePassword() {
        this.username = this.password = "";
    }

    /**
     * If no user is logged in, validate the username and password. On succes,
     * redirect. Else add an error message.
     *
     * @throws IOException If the redirect can be executed.
     */
    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (useraccount != null) {
            FacesMessage loginErrorMessage = new FacesMessage("A user is still logged in.");
            context.addMessage(null, loginErrorMessage);
        } else {
            ExternalContext externalContext = context.getExternalContext();
//          HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
//          try {
//               request.login(this.username, this.password);
            Useraccount foundUser = this.useraccountFacade.findByUsernamePassword(this.username, this.password);
            if (foundUser != null) {
                externalContext.getSessionMap().put("user", foundUser);
                //User is accessible in JSF EL by #{user}
                externalContext.redirect(this.originalURL(externalContext));
            } else {
                FacesMessage loginErrorMessage = new FacesMessage("Username or password are incorrect.");
                context.addMessage(null, loginErrorMessage);
            }
//          } catch (ServletException e) {
                // Handle unknown username/password in request.login().
//              context.addMessage(null, new FacesMessage("Username or password are incorrect."));
//          }
        }
    }

    public String originalURL(ExternalContext externalContext) {
        String originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
        return originalURL;
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        this.useraccount = null;
        externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
    }
}
