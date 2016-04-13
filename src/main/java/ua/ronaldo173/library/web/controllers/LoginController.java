package ua.ronaldo173.library.web.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;

@ManagedBean
@RequestScoped
public class LoginController {

    public LoginController() {
    }

    public String login() {
        return "books";
    }

    public String exit(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "exit";
    }


}
