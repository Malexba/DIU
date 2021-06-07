package master;
import java.util.*;
import java.io.*;

/**
 * Esta es la clase principal, que representa al máster.
 * @author Grupo 1L, equipo 3
 */
public class Master {

    /**
     * Este es el método main.
     * Primero lee los datos de los ficheros correspondientes, indicando si hay
     * alumnos registrados (diferenciando entre tiempo parcial y completo).
     * A continuación muestra el menú principal, inquiriendo al usuario que
     * escoja una opción.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean vacio = true;
        int k = 0;
        ArrayList<AlumnoTiempoParcial> alumnosP = new ArrayList<AlumnoTiempoParcial>();
        ArrayList<AlumnoTiempoCompleto> alumnosC = new ArrayList<AlumnoTiempoCompleto>();
        ArrayList<String> datos = MyInput.leeFichero( "planEstudios.txt" );
        File f = new File( "datosAcademicos.dat" );
        try {
            if ( f.createNewFile() == false ) { // Comprobamos si el archivo existe, y sino lo creamos
                FileInputStream archivo = new FileInputStream( f );
                ObjectInputStream cosa = new ObjectInputStream( archivo );
                alumnosP = (ArrayList<AlumnoTiempoParcial>)cosa.readObject();
                if ( alumnosP.get(0).getDNI().equals("0") ) {
                    alumnosP = new ArrayList<AlumnoTiempoParcial>();
                    k++;
                } else {
                    k = 3;
                }
                alumnosC = (ArrayList<AlumnoTiempoCompleto>)cosa.readObject();
                k++;
                cosa.close();
                archivo.close();
            } else {
                System.out.println( "No hay alumnos registrados." );
            }
        } catch ( FileNotFoundException FNFE ) {
            System.out.println( "No hay alumnos registrados." );
        } catch ( EOFException EOFE ) {
            if ( ( k == 1 ) || ( k == 0 ) ) {
                System.out.println( "No hay alumnos registrados." );
            } else if ( k == 3 ) {
                vacio = false;
                System.out.println( "No hay alumnos a tiempo completo registrados." );
            }
        } catch ( IOException IOE ) {
            System.out.println( "Error de lectura." );
        } catch ( ClassNotFoundException CNFE ) {
            System.out.println( "La clase no existe" );
        } finally {            
            // Menú principal
            int res = 0;
            if ( k == 2 ) {
                vacio = false;
                System.out.println( "No hay alumnos a tiempo parcial registrados." );
            }
            do {
                System.out.println( "MENU PRINCIPAL" );
                System.out.println( "\t1.Dar de alta nuevo alumno." );
                System.out.println( "\t2.Consultar asignatura." );
                System.out.println( "\t3.Consultar expediente de alumno." );
                System.out.println( "\t4.Gestion de matricula." );
                System.out.println( "\t5.Calificacion de asignatura." );
                System.out.println( "\t6.Guardar datos." );
                System.out.println( "\t7.Salir." );
                System.out.println( "Escoja una opcion: " );
                res = MyInput.readInt();
                switch (res) {
                    case 1:
                        nAlumno ( alumnosP, alumnosC );
                        break;
                    case 2:
                        cAsignatura( datos );
                        break;
                    case 3:
                        mExpediente( alumnosP, alumnosC );
                        break;
                    case 4:
                        gMatricula( alumnosP, alumnosC, datos );
                        break;
                    case 5:
                        calAsignatura( alumnosP, alumnosC, datos );
                        break;
                    case 6:
                        Guardar( alumnosP, alumnosC, f, vacio );
                        break;
                    case 7:
                        Guardar( alumnosP, alumnosC, f, vacio );
                        System.out.println( "Que tenga un buen dia." );
                        break;
                    default:
                        System.out.println( "Elija una opcion valida." );
                }
                Wait();
            } while ( res != 7 );
        }
    }
    
    /**
     * Método que permite dar de alta a un nuevo alumno.
     * Se valida el formato del DNI a la par que se comprueba si el alumno ha
     * sido ya registrado o no. Después, se pide el resto de información del
     * estudiante y diferenciamos si cursará el master a tiempo parcial o a
     * tiempo completo.
     * @param alumnosP ArrayList con los alumnos a tiempo parcial registrados
     * @param alumnosC ArrayList con los alumnos a tiempo completo registrados
     */
    public static void nAlumno ( ArrayList<AlumnoTiempoParcial> alumnosP, ArrayList<AlumnoTiempoCompleto> alumnosC ) { // ESTÁ BIEN
        String DNI;
        char comp[];
        boolean sal;
        String nombre;
        String apellidos;
        String dir;
        int tel;
        String email;
        System.out.println( "MATRICULACION DE ALUMNOS" );
        System.out.println();
        System.out.println( "Bienvenido al sistema de matriculacion de alumnos. A continuacion se le pediran los datos del nuevo alumno." );    
        do {
            sal = false;
            int i = 0;
            System.out.println( "DNI (incluya los ceros anteriores en caso de haberlos y la letra en mayuscula): " );
            DNI = MyInput.readString();
            comp = DNI.toCharArray();
            if ( DNI.length() == 9 ) { // Comprobamos validez del formato
                while ( ( sal == false ) && ( i < 8 ) ) {
                    if ( ( 48 > (int)comp[i] ) || ( (int)comp[i] > 57 ) ) {
                        sal = true;
                    }
                    i++;
                }
                if ( ( 65 > (int)comp[8] ) || ( (int)comp[8] > 90 ) ) {
                    sal = true;
                }
                i = 0;
                if ( ( sal == false ) && ( alumnosP.isEmpty() == false ) ) {
                    do { // Comprobemos que no exista ya el DNI en parcial
                        if ( DNI.equals(alumnosP.get(i).getDNI()) ) {
                            sal = true;
                            System.out.println( "Error, alumno ya registrado." );
                        }
                        i++;
                    } while ( ( sal == false ) && ( alumnosP.size() > i ) );
                }
                if ( ( sal == false ) && ( alumnosC.isEmpty() == false ) ) {
                    i = 0;
                    do { // Comprobemos que no exista ya el DNI en completo
                        if ( DNI.equals(alumnosC.get(i).getDNI()) ) {
                            sal = true;
                            System.out.println( "Error, alumno ya registrado." );
                        }
                        i++;
                    } while ( ( sal == false ) && ( alumnosC.size() > i ) );
                }
            } else {
                sal = true;
            }
        } while ( sal );
        System.out.println( "Nombre: " );
        nombre = MyInput.readString();
        System.out.println( "Primer apellido: " );
        apellidos = MyInput.readString();
        System.out.println( "Segundo apellido: " );
        apellidos = apellidos + " " + MyInput.readString();
        System.out.println( "Direccion: " );
        dir = MyInput.readString();
        System.out.println( "Telefono: " );
        tel = MyInput.readInt();
        System.out.println( "Correo electronico: " );
        email = MyInput.readString();
        String res;
        do {
            System.out.println( "Elija entre tiempo parcial (P) o completo (C): " );
            res = MyInput.readString();
        } while ( ( res.equals("P") == false ) && ( res.equals("C") == false ) );
        if ( res.equals("P") ) {
            alumnosP.add( new AlumnoTiempoParcial( DNI, nombre, apellidos, dir , tel, email ) );
        } else {
            alumnosC.add( new AlumnoTiempoCompleto( DNI, nombre, apellidos, dir , tel, email ) );
        }
        System.out.println( "Alumno agregado satisfactoriamente." );
    }
    
    /**
     * Método que permite consultar los datos de una asignatura.
     * Se pide al usuario el código de la materia y se comprueba si éste es
     * válido. En caso afirmativo, se muestra la información por pantalla.
     * Por último, el usuario decide si repetir o no la operación.
     * @param datos ArrayList con los datos de las asignaturas
     */
    public static void cAsignatura ( ArrayList<String> datos ) {
        int tam = datos.size();
        String codTemp = "0000";
        boolean existe = false;
        StringTokenizer tokenizer = null;
        String res = "S";
        System.out.println( "CONSULTAR ASIGNATURA" );
        System.out.println();
        System.out.println( "Bienvenido al sistema de consulta de asignaturas." );
        do {
            System.out.println( "Aporte el codigo de la asignatura: " );
            String cod = MyInput.readString();
            int i = 0;
            while ( i < tam ) { // Lee líneas del fichero
                tokenizer = new StringTokenizer( datos.get(i), "/" );
                codTemp = tokenizer.nextToken();
                if ( cod.equals(codTemp) ) {
                    existe = true;
                    break;
                }
                i++;
            }
            if ( existe ) {
                System.out.print( "\n" + codTemp + " " );
                for ( int j = 0; j < 3; j++ ) {
                    codTemp = tokenizer.nextToken();
                    System.out.print( codTemp + " " );
                }
            } else {
                System.out.println( "Aporte un codigo de asignatura valido." );
            }
            System.out.println();
            System.out.println( "¿Desea ver los datos de otra asignatura? ( Marque S para si; N para no )" );
            res = MyInput.readString();
            while ( ( res.equals("S") == false ) && ( res.equals("N") == false ) ) {
                System.out.println( "Introduzca una respuesta apropiada." );
                System.out.println( "¿Desea ver los datos de otra asignatura?" );
                res = MyInput.readString();
            }
        } while ( res.equals("S") );
    }
    
    /**
     * Método para consultar el expediente académico de un alumno.
     * Se pide al usuario el identificador de un estudiante (en caso de que
     * éste no exista, se retorna al menú principal). A continuación se muestran
     * los datos del alumno y un listado con las asignaturas en las que se haya
     * matriculado a lo largo del máster. Por último, el usuario decide si
     * desea consultar otro expediente.
     * @param alumnosP ArrayList con los alumnos a tiempo parcial registrados
     * @param alumnosC ArrayList con los alumnos a tiempo completo registrados
     */
    public static void mExpediente ( ArrayList<AlumnoTiempoParcial> alumnosP, ArrayList<AlumnoTiempoCompleto> alumnosC ) {
        String DNI;
        boolean existe;
        boolean tiempo;
        int i;
        System.out.println( "CONSULTAR EXPEDIENTE" );
        System.out.println();
        System.out.println( "Bienvenido al sistema de consulta de expedientes." );
        String res = "S";
        do {
            existe = false;
            tiempo = false;
            System.out.println( "DNI del alumno (incluya los ceros anteriores en caso de haberlos): " );
            DNI = MyInput.readString();
            i = 0;
            if ( alumnosP.isEmpty() == false ) {
                do { // Buscamos el DNI en tiempo parcial
                    if ( DNI.equalsIgnoreCase(alumnosP.get(i).getDNI()) ) {
                        existe = true;
                        tiempo = true;
                    }
                    i++;
                } while ( ( existe == false ) && ( alumnosP.size() > i ) );
            }
            if ( ( existe == false ) && ( alumnosC.isEmpty() == false ) ){
                i = 0;
                do { // Buscamos el DNI en tiempo completo
                    if ( DNI.equalsIgnoreCase(alumnosC.get(i).getDNI()) ) {
                        existe = true;
                        tiempo = false;
                    }
                    i++;
                } while ( ( existe == false ) && ( alumnosC.size() > i ) );
            }
            i--;
            if ( tiempo && existe ){
                AlumnoTiempoParcial actual = alumnosP.get(i);
                System.out.println( "DATOS DEL ALUMNO" );
                System.out.println();
                System.out.println( actual.getDNI() + "\t" + actual.getNombre() + " " + actual.getApellidos() + "\t" + actual.getDir() + "\t" + actual.getTel() + "\t" + actual.getMail() );
                System.out.println( "El alumno realiza el master a tiempo parcial." );
                actual.mAsignaturas();
                if ( actual.superado() ) {
                    actual.media();
                }
                System.out.println( "¿Desea consultar otro expediente? ( Marque S para si; N para no )" );
                res = MyInput.readString();
                while ( ( res.equals("S") == false ) && ( res.equals("N") == false ) ) {
                    System.out.println( "Introduzca una respuesta apropiada." );
                    System.out.println( "¿Desea consultar otro expediente?" );
                    res = MyInput.readString();
                }
            } else if ( existe ){
                AlumnoTiempoCompleto actual = alumnosC.get(i);
                System.out.println( "DATOS DEL ALUMNO" );
                System.out.println();
                System.out.println( actual.getDNI() + "\t" + actual.getNombre() + " " + actual.getApellidos() + "\t" + actual.getDir() + "\t" + actual.getTel() + "\t" + actual.getMail() );
                System.out.println( "El alumno realiza el master a tiempo completo." );
                actual.mAsignaturas();
                if ( actual.superado() ) {
                    actual.media();
                }
                System.out.println( "¿Desea consultar otro expediente? ( Marque S para si; N para no )" );
                res = MyInput.readString();
                while ( ( res.equals("S") == false ) && ( res.equals("N") == false ) ) {
                    System.out.println( "Introduzca una respuesta apropiada." );
                    System.out.println( "¿Desea consultar otro expediente?" );
                    res = MyInput.readString();
                }
            } else {
                System.out.println( "Error, el DNI aportado no existe." );
                res = "N";
            }
        } while ( res.equals("S") );
    }
    
    /**
     * Método para matricular a un alumno en asignaturas.
     * Se pide al usuario el DNI de un estudiante; en caso de que éste no
     * exista, se retorna al menú principal (ocurriendo lo mismo en caso
     * de que el alumno haya superado ya el máster). Después, se pide el
     * año de inicio del curso a matricularse (comprobando que éste no sea
     * anterior al de alguna asignatura ya matriculada) y llamamos al método
     * addMatrícula de Alumno.
     * @param alumnosP ArrayList con los alumnos a tiempo parcial registrados
     * @param alumnosC ArrayList con los alumnos a tiempo completo registrados
     * @param datos ArrayList con los datos de las asignaturas
     */
    public static void gMatricula ( ArrayList<AlumnoTiempoParcial> alumnosP, ArrayList<AlumnoTiempoCompleto> alumnosC, ArrayList<String> datos ) {
        String DNI;
        boolean existe = false;
        int i = 0;
        boolean tiempo = false; // false = completo; true = parcial
        System.out.println( "GESTION DE MATRICULA" );
        System.out.println();
        System.out.println( "Bienvenido al sistema de gestion de matricula." );
        String res = "B";
        do {
            if ( res == "B" ) {
                System.out.println( "DNI del alumno (incluya los ceros anteriores en caso de haberlos): " );
                DNI = MyInput.readString();
                i = 0;
                if ( alumnosP.isEmpty() == false ) {
                    do { // Buscamos el DNI en tiempo parcial
                        if ( DNI.equalsIgnoreCase(alumnosP.get(i).getDNI()) ) {
                            existe = true;
                            tiempo = true;
                        }
                        i++;
                    } while ( ( existe == false ) && ( alumnosP.size() > i ) );
                }
                if ( ( existe == false ) && ( alumnosC.isEmpty() == false ) ){
                    i = 0;
                    do { // Buscamos el DNI en tiempo completo
                        if ( DNI.equalsIgnoreCase(alumnosC.get(i).getDNI()) ) {
                            existe = true;
                            tiempo = false;
                        }
                        i++;
                    } while ( ( existe == false ) && ( alumnosC.size() > i ) );
                }
                i--;
            }
            if ( tiempo && existe ){
                AlumnoTiempoParcial actual = alumnosP.get(i);
                if ( actual.superado() ){
                    System.out.println( "Error, el alumno ya ha superado el master." );
                    res = "C";
                } else {
                    System.out.println( "Ano de inicio del curso a matricularse: " );
                    int ini = MyInput.readInt();
                    if ( actual.actCurso(ini) ) {
                        System.out.println( "Error, ha seleccionado un ano anterior al actualmente cursado." );
                    } else {
                        actual.addMatricula( datos, ini );
                        alumnosP.set(i, actual);
                    }
                    System.out.println( "¿Desea matricularse de otra asignatura (A) , cambiar de alumno (B) o finalizar (C)? " );
                    res = MyInput.readString();
                    while ( ( res.equals("A") == false ) && ( res.equals("B") == false ) && ( res.equals("C") == false ) ) {
                        System.out.println( "Introduzca una respuesta apropiada." );
                        System.out.println( "¿Que desea hacer? " );
                        res = MyInput.readString();
                    }
                }                   
            } else if ( existe ){
                AlumnoTiempoCompleto actual = alumnosC.get(i);                
                if ( actual.superado() ){
                    System.out.println( "Error, el alumno ya ha superado el master." );
                    res = "C";
                } else {
                    System.out.println( "Ano de inicio del curso a matricularse: " );
                    int ini = MyInput.readInt();
                    if ( actual.actCurso(ini) ) {
                        System.out.println( "Error, ha seleccionado un ano anterior al actualmente cursado." );
                    } else {
                        actual.addMatricula( datos, ini );
                        alumnosC.set(i, actual);
                    }
                    System.out.println( "¿Desea matricularse de otra asignatura (A) , cambiar de alumno (B) o finalizar (C)? " );
                    res = MyInput.readString();
                    while ( ( res.equals("A") == false ) && ( res.equals("B") == false ) && ( res.equals("C") == false ) ) {
                        System.out.println( "Introduzca una respuesta apropiada." );
                        System.out.println( "¿Que desea hacer? " );
                        res = MyInput.readString();
                    }
                }        
            } else {
                System.out.println( "Error, el DNI aportado no existe." );
                res = "C";
            }
        } while ( res.equals("C") == false );
    }
    
    /**
     * Método que permite calificar todas las asignaturas de un alumno en un
     * curso determinado.
     * Pedimos al usuario el DNI de un alumno y comprobamos que está registrado
     * o si a superado ya el máster. En caso de que todo esté en orden;
     * se le inquirirá al usuario un curso, realizando una llamada a los
     * métodos calificar y compSup de la clase Alumno.
     * @param alumnosP ArrayList con los alumnos a tiempo parcial registrados
     * @param alumnosC ArrayList con los alumnos a tiempo completo registrados
     * @param datos ArrayList con los datos de las asignaturas
     */
    public static void calAsignatura ( ArrayList<AlumnoTiempoParcial> alumnosP, ArrayList<AlumnoTiempoCompleto> alumnosC, ArrayList<String> datos ) { // Aquí comprobamos si está o no aprobado
        String DNI;
        boolean existe = false;
        boolean tiempo = false;
        int i = 0;
        System.out.println( "CALIFICACION DE ASIGNATURAS" );
        System.out.println();
        System.out.println( "Bienvenido al sistema de calificacion de asignaturas." );
        String res = "B";
        System.out.println( "DNI del alumno (incluya los ceros anteriores en caso de haberlos): " );
        DNI = MyInput.readString();
        i = 0;
        if ( alumnosP.isEmpty() == false ) {
            do { // Buscamos el DNI en tiempo parcial
                if ( DNI.equalsIgnoreCase(alumnosP.get(i).getDNI()) ) {
                    existe = true;
                    tiempo = true;
                }
                i++;
            } while ( ( existe == false ) && ( alumnosP.size() > i ) );
        }
        i--;
        if ( existe ) {
            AlumnoTiempoParcial actual = alumnosP.get(i);
            if ( actual.superado() ){
                    System.out.println( "Error, el alumno ya ha superado el master." );
            } else {
                System.out.println( "Ano de inicio del curso a calificar: " );
                int ini = MyInput.readInt();
                actual.calificar(ini);
                actual.compSup( datos );
            }
        } else {
            i = 0;
            if ( alumnosC.isEmpty() == false ){
                i = 0;
                do { // Buscamos el DNI en tiempo completo
                    if ( DNI.equalsIgnoreCase(alumnosC.get(i).getDNI()) ) {
                        existe = true;
                    }
                    i++;
                } while ( ( existe == false ) && ( alumnosC.size() > i ) );
            }
            i--;
            if ( existe ) {
                AlumnoTiempoCompleto actual = alumnosC.get(i);
                if ( actual.superado() ){
                    System.out.println( "Error, el alumno ya ha superado el master." );
                } else {
                    System.out.println( "Ano de inicio del curso a calificar: " );
                    int ini = MyInput.readInt();
                    actual.calificar(ini);
                    actual.compSup( datos );
                }
            } else {
                System.out.println( "Error, el DNI aportado no existe." );
            }
        }
        
        
    }
    
    /**
     * Método para sobrescribir los datos de los alumnos en el fichero binario.
     * Comprobamos si el fichero está vacío (en caso contrario, lo borramos).
     * A continuación volcamos los datos de los alumnos en el fichero
     * (en el caso de que no haya alumnos a tiempo parcial creamos y
     * escribimos uno con datos significativos, buscando facilitar la
     * posterior lectura) e informamos al usuario de ello.
     * @param alumnosP ArrayList con los alumnos a tiempo parcial registrados
     * @param alumnosC ArrayList con los alumnos a tiempo completo registrados
     * @param f Fichero binario del que tomamos los datos de los alumnos
     * registrados en ejecuciones anteriores del programa
     * @param vacio Verdadero si el fichero f está vacío; falso en su defecto
     */
    public static void Guardar ( ArrayList<AlumnoTiempoParcial> alumnosP, ArrayList<AlumnoTiempoCompleto> alumnosC, File f, boolean vacio ) {
        try {
            FileOutputStream out = new FileOutputStream( f );
            ObjectOutputStream oout = new ObjectOutputStream( out );
            if ( vacio == false ) {
                f.delete();
            }
            int i = 0;
            if ( alumnosP.isEmpty() ) {
                alumnosP.add(new AlumnoTiempoParcial( "0", "", "", "", 0, "" ));
            }
            oout.writeObject(alumnosP);
            if ( alumnosC.isEmpty() == false ) {
                oout.writeObject(alumnosC);
            }
            System.out.println( "Guardado realizado con exito." );
            out.close();
            oout.close();
        } catch ( FileNotFoundException FNFE ) {
            System.out.println( "No hay alumnos registrados." );
        } catch ( IOException IOE ) {
            System.out.println( "Error de escritura." );
        }
    }
    
    /**
     * Método que permite al usuario decidir cuándo continuar con la ejecución
     * del programa.
     */
    public static void Wait () {
        System.out.println( "Pulse enter para continuar." );
        System.out.flush();
        MyInput.readString();
    }
}
