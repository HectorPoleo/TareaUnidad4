
package es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import es.ies.puerto.model.Empleado;
import es.ies.puerto.model.OperacionesInterface;

/**
 *  @author: hectorpoleo
 *  @version: 1.0.0
 */
public class OperacionesFichero implements OperacionesInterface {

    File fichero;
    String path = "C:\\Users\\Héctor\\Desktop\\git\\TareaUnidad4\\demo\\src\\main\\resources\\archivo.txt";
    public OperacionesFichero(){
        fichero = new File(path);
        if (!fichero.exists() || !fichero.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero "+path);
        }
    }

    @Override
    public boolean create(Empleado persona) {
        if (persona == null || persona.getIdentificador().isEmpty() || persona.getIdentificador() == null) {
            return false;
        }
        TreeMap<String, Empleado> empleados = read(fichero);
        if (empleados.containsValue(persona)) {
            return false;
        }
        return create(persona.toString(), fichero);
        }
    private boolean create(String data,File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine(); // Añadir una nueva línea después del registro
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private TreeMap<String, Empleado> read(File file) {
        if (file == null){
            return new TreeMap<>();
        }
        TreeMap <String, Empleado>empleados = new TreeMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayLine = line.split(",");
                Empleado empleado = new Empleado(arrayLine[0], arrayLine[1], Double.parseDouble(arrayLine [2]), arrayLine[3], arrayLine[4]);
                empleados.put(empleado.getIdentificador(), empleado);
            }
        } catch (IOException e) {
            return new TreeMap<>();
        }
        return empleados;
    }
    
    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador().isEmpty() || empleado.getIdentificador() == null) {
            return false;
        }
        TreeMap<String, Empleado> empleados = read(fichero);
        if (!empleados.containsValue(empleado)) {
            return false;
        }
        for (Empleado empleadoBuscado : empleados.values()) {
            if (empleadoBuscado.equals(empleado)) {
                empleados.remove(empleadoBuscado.getIdentificador());
                empleados.put(empleado.getIdentificador(),empleado);
                return updateFile(empleados, fichero);
            }
        }
        System.out.println(empleados);
        return true;
}

    private boolean updateFile(TreeMap<String, Empleado> empleados, File file){
        path = file.getAbsolutePath();
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        for(Empleado empleado : empleados.values()) {
            create(empleado);
        }
        return true;
    }

    @Override
    public boolean delete(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador().isEmpty() || empleado.getIdentificador() == null) {
            return false;
        }
        TreeMap<String, Empleado> empleados = read(fichero);
        if (!empleados.containsValue(empleado)) {
            return false;
        }
        for (Empleado empleadoBuscar : empleados.values()) {
            if(empleadoBuscar.equals(empleado)) {
                empleados.remove(empleadoBuscar.getIdentificador());
                return updateFile(empleados, fichero);
            }
        }
        return false;
    }

    @Override
    public Empleado search(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador().isEmpty() || empleado.getIdentificador() == null) {
            return empleado;
        }
        TreeMap<String, Empleado> empleados = read(fichero);
        for (Empleado empleadoBuscar : empleados.values()) {
            if(empleadoBuscar.equals(empleado)) {
                return empleadoBuscar;
            }
        }
        return empleado;
    }

    @Override
    public Empleado search(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return null;
        }
        Empleado empleado = new Empleado(identificador);
        return search(empleado);
    }

    @Override
    public TreeMap<String, Empleado> empleadosPorPuesto(String puesto) {
        TreeMap<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[3].equalsIgnoreCase(puesto)) {
                    Empleado empleado = new Empleado(data[0], data[1], Double.parseDouble(data [2]), data[3], data[4]);
                    empleados.put(empleado.getIdentificador(),empleado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public TreeMap<String, Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        TreeMap<String, Empleado> empleados = new TreeMap<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date inicio = sdf.parse(fechaInicio);
            Date fin = sdf.parse(fechaFin);
            
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(", ");
                    Date fechaNacimiento = sdf.parse(data[4]);
                    if (!fechaNacimiento.before(inicio) && !fechaNacimiento.after(fin)) {
                    Empleado empleado = new Empleado(data[0], data[1], Double.parseDouble(data [2]), data[3], data[4]);
                    empleados.put(empleado.getIdentificador(), empleado);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return empleados;
    }
}