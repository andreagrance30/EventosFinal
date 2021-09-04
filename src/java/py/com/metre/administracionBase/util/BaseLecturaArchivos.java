/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author  * clase que maneja las propiedades configurables
 * con el sgte codigo d ejemplo
 * BaseLecturaArchivos archivoConfig = new BaseLecturaArchivos();
 * archivoConfig.setNombreArchivo("utilitarios.properties");
 * se pasa el nombre del archivo del cual se quieren traer las propiedades
 * cada modulo debe tener su propio archivo de configuracion (fuerzaventas.properties, etc)
 * el archivo se debe guardar en el servidor d  aplicaciones en
 * C:\glassfishv3\glassfish\domains\domain1
 */
public class BaseLecturaArchivos implements Serializable {

    public static final String SeparadorArchivo = File.separator;
    private Properties labels= new Properties();

    public void setNombreArchivo(String archivo) throws FileNotFoundException, IOException {
        FileInputStream io;
        if (SeparadorArchivo.equals(ApplicationConstant.SEPARADOR_WINDOWS))
            io = new FileInputStream(ApplicationConstant.CARPETA_WINDOWS + SeparadorArchivo + archivo);
        else
            io = new FileInputStream(ApplicationConstant.CARPETA_LINUX + SeparadorArchivo + archivo);

        labels.load(io);
    }

    /*
     * metodo para traer la propiedad deseada
     * @param key
     */
    public String getPropiedad(String key) {
        if (!labels.containsKey(key)) {
            System.out.println("No existe la propiedad " + key);
            return null;
        }
        return labels.getProperty(key);

    }
}
