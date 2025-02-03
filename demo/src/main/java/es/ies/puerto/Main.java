package es.ies.puerto;
import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.fichero.OperacionesFichero;

public class Main {
    public static void main(String[] args) {
        OperacionesFichero operaciones = new OperacionesFichero();
        Empleado empleado1 = new Empleado("1, Juan Perez, Desarrollador, 3000.50, 15\10\1995");
        Empleado empleado2 = new Empleado("2, Ana Gomez, Diseñadora, 2800.75, 10\01\1990");
        //System.out.println(empleado);
        boolean insertar = operaciones.create(empleado1);
        boolean insertar2 = operaciones.create(empleado2);
        if (insertar) {
            System.out.println("Se ha insertado correctamente");
        }  else{
            System.out.println("No se ha insertado el elemento");
        }
        if (insertar2) {
            System.out.println("Se ha insertado correctamente");
        }  else{
            System.out.println("No se ha insertado el elemento");
        }
        Empleado empleadoBuscar = new Empleado("1");
        Empleado empleadoBuscar2 = new Empleado("2");
        empleadoBuscar = operaciones.search(empleadoBuscar);
        empleadoBuscar2 = operaciones.search(empleadoBuscar2);
        System.out.println("Persona encontrada: " + empleadoBuscar);
        System.out.println("Persona encontrada: " + empleadoBuscar2);
        operaciones.update(empleadoBuscar2);
        
        

    }
}