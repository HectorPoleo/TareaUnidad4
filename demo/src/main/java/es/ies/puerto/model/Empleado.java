package es.ies.puerto.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Empleado {
    String identificador;
    String nombre;
    long sueldo;
    String puesto;
    String fechaNacimiento;


    public Empleado(){
        
    }

    public Empleado(String identificador){
        this.identificador = identificador;
    }
    public Empleado(String identificador, String nombre, long sueldo,String puesto,String fechanacimiento ){
        this.identificador = identificador;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.fechaNacimiento = fechanacimiento;
        this.puesto = puesto;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public long getSueldo() {
        return this.sueldo;
    }

    public int getEdad(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date birthDate = sdf.parse(fechaNacimiento);
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return getIdentificador() +"," + getNombre() + "," + getSueldo()+ ","+ getFechaNacimiento();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Empleado)) {
            return false;
        }
        Empleado empleado = (Empleado) o;
        return Objects.equals(identificador, empleado.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}
