/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.util;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
/**
 *
 * @author Diaz
 * No usar ya q actua como un singleton y siempre devolvera el 1er usuario logueado en el servidor de aplicaciones
 */
 @Documented
 @Target(value={METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
 @Retention(value=RUNTIME)
 @Qualifier
public @interface Logeado {

}
