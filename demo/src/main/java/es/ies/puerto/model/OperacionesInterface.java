package es.ies.puerto.model;

import java.util.Set;

/**
 *  @author: hectorpoleo
 *  @version: 1.0.0
 */
public interface OperacionesInterface {
    public boolean create(Empleado empleado);
    public boolean update(Empleado persona);
    public boolean delete(Empleado empleado);
    public Empleado search(Empleado empleado);
    public Empleado search(String identificador);
    Set<Empleado> empleadosPorPuesto(String puesto);
    Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}