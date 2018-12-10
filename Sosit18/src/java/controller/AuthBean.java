package controller;

import entity.Useraccount;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This managed bean holds the currently logged in user.
 * It can also be asked if a user has a certain Role or permission.
 * @author woutbr@student.hik.be
 */
@Named(value = "auth")
@SessionScoped
public class AuthBean implements Serializable {

    private Useraccount user;
    private boolean debugMode;

    public AuthBean() {
        this.debugMode = true;//For testing. Ignores the logincheck in LoginFilter
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Useraccount getUser() {
        return user;
    }

    public void setUser(Useraccount user) {
        this.user = user;
    }
    
    public void clearUser() {
        this.user = null;
    }
    
    /**
     * Returns true when auth.user is not null.
     */
    public boolean isLoggedIn(){
        return this.user != null;
    }
    
    public boolean canEdit(){
        return this.user.getCanedit();
    }
    
    public boolean canRead(){
        return this.user.getCanread();
    }
    
    public boolean canInsert(){
        return this.user.getCaninsert();
    }
    
    public boolean canDelete(){
        return this.user.getCandelete();
    }

}
