package com.almundo.callcenter.model;

public class Empleado implements Runnable{
    
    private Role rol;
    
    private Status status;
    
    public void setStatus(Status status){
        this.status = status;
    }
    
    public Status getStatus(){
        return this.status;
    }
    
    public void setRol(Role rol){
        this.rol = rol;
    }
    
    public Role getRol(){
        return this.rol;
    }
    
    @Override
    public void run(){
        
        
    }
    
}
