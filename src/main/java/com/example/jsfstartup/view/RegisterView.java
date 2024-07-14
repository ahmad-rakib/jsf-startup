package com.example.jsfstartup.view;

import com.example.jsfstartup.enitty.User;
import com.example.jsfstartup.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RegisterView implements Serializable{

    @Inject
    private UserService userService;

    private User registerUser = new User();

    private List<User> allUserList;

    @PostConstruct
    public void postConstruct() {
        allUserList = userService.getAllUsers();
        String userIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
        if (userIdParam != null) {
            registerUser = userService.getUser(Long.parseLong(userIdParam));
        }
    }

    public User getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(User registerUser) {
        this.registerUser = registerUser;
    }

    public void save() {
        userService.addUser(this.registerUser);
    }

    public List<User> getAllUserList() {
        return allUserList;
    }

    public void update() {
        userService.update(registerUser);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        userService.delete(registerUser);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
