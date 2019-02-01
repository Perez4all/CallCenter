package com.almundo.callcenter.dispatch;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.function.Function;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.Status;
import com.almundo.callcenter.model.Role;

public class Dispatcher implements Runnable{
    
    private static final Logger logger = Logger.getLogger(Dispatcher.class.getName());
    private ThreadPoolExecutor executor;
    private ConcurrentLinkedDeque<Empleado> empleados;
    private ConcurrentLinkedDeque<Llamada> entrantes;
    private Random randomNumber = new Random(); 
    
    public Dispatcher(Collection<Empleado> empleados){
        this.empleados = new ConcurrentLinkedDeque<>(empleados);
        this.entrantes = new ConcurrentLinkedDeque<>();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }
    
    public void dispatchCall(Llamada llamada){
        this.entrantes.push(llamada);       
    }
    
    private Empleado findEmpleadoDisponible(){
        List<Empleado> disponibles = this.empleados.stream()
        .filter(e -> Status.DISPONIBLE.equals(e.getStatus()))
        .collect(Collectors.toList());
        
        Function<Role, Optional<Empleado>> findEmpleadoByRole = r -> disponibles.stream()
        .filter(d -> r.equals(d.getRol())).findAny();
        
        Optional<Empleado> empleado = findEmpleadoByRole.apply(Role.OPERADOR);
        if(!empleado.isPresent()){
            empleado = findEmpleadoByRole.apply(Role.SUPERVISOR);
            if(!empleado.isPresent()){
                empleado = findEmpleadoByRole.apply(Role.DIRECTOR);
                if(!empleado.isPresent()){
                    logger.warning("No se encontro un empleado disponible");
                    return null;
                }
            }
        }
        
        return empleado.get();
        
    }

	@Override
	public void run() {
		
		Empleado empleadoDisponible = findEmpleadoDisponible();
		
	}
    
}
