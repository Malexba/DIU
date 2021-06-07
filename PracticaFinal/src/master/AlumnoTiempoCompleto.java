package master;
import java.util.*;

/**
 * Clase que representa a un alumno que esté cursando el master a tiempo
 * completo.
 * @author Grupo 1L, equipo 3
 */
public class AlumnoTiempoCompleto extends Alumno {
    
    /**
     * Constructor de AlumnoTiempoCompleto.
     * @param DNI Identificador del alumno
     * @param nombre Nombre del alumno
     * @param apellidos Apellidos del alumno
     * @param dir Dirección del alumno
     * @param tel Teléfono del alumno
     * @param email Correo eléctrónico del alumno
     */
    public AlumnoTiempoCompleto ( String DNI, String nombre, String apellidos, String dir, int tel, String email ){
        super(DNI, nombre, apellidos, dir, tel, email, false);
    }
    
    /**
     * Método que permite a un alumno matricularse en una asignatura.
     * Pide al usuario el código de la asignatura; comprobando primero si no se
     * ha matriculado antes en ella o si ya la ha aprobado, mostrando entonces
     * un mensaje de error y finalizando. En caso de que esto no ocurra,
     * comprueba si el código aportado se corresponde con alguna de las
     * asignaturas existentes (mostrando error y cancelando en caso negativo),
     * obtiene todos los datos de ésta y el alumno queda matriculado en la asignatura.
     * @param datos ArrayList con los datos de las asignaturas
     * @param ini Año de inicio del curso a matricularse
     */
    public void addMatricula ( ArrayList<String> datos, int ini ) { // 0 - Bien, 1 - Código mal, 2 - Asignatura aprobada ya, 3 - Asignatura ya matriculada, 4 - Asignatura ya matriculada en cursos anteriores sin evaluar
        System.out.println( "Aporte el codigo de la asignatura: " );
        String cod = MyInput.readString();
        int j = 0;
        int vuelta = 0;
        int tamA;
        boolean sal = false;
        if ( Materias.isEmpty() != true ) {
            tamA = Materias.size();
            do {
                if ( cod.equals( Materias.get(j).codigo() ) ) {
                    if ( Materias.get(j).mAno() >= ini ) { 
                        vuelta = 3;
                        System.out.println( "Error, ya se ha matriculado este curso de esta asignatura." );
                    } else if ( Materias.get(j).aprob() ) {
                        vuelta = 2;
                        System.out.println( "Error, asignatura ya aprobada." );
                    } else if ( Materias.get(j).getNota() == -1 ) {
                        vuelta = 4;
                        System.out.println( "Error, asignatura ya matriculada en cursos anteriores sin evaluar." );
                    }
                }
                j++;
            } while ( ( j < tamA ) && ( vuelta >= 2 )  );
        }
        if ( vuelta < 2 ) {
            int i = 0;
            int tam = datos.size();
            String codTemp;
            StringTokenizer tokenizer = new StringTokenizer( "" );            
            while ( i < tam ) { // Lee líneas del fichero
                tokenizer = new StringTokenizer( datos.get(i), "/" );
                codTemp = tokenizer.nextToken();
                if ( cod.equals(codTemp) ) {
                    break;
                }
                i++;
            }
            if ( i == tam ) {
                System.out.println( "Codigo erroneo." );
            } else {
                String nombre = tokenizer.nextToken();
                String tipo = tokenizer.nextToken();
                int cred = Integer.parseInt(tokenizer.nextToken());
                Asignatura asig = new Asignatura( cod, nombre, tipo, cred, ini );
                Materias.add( asig );
                System.out.println( "Acaba usted de matricularse en " + nombre + "." );
            }
        }
    }
}
