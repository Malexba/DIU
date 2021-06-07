package master;
import java.io.*;
import java.util.*;

/**
 * Clase que representa a un alumno que esté cursando el master.
 * @author Grupo 1L, equipo 3
 */
public abstract class Alumno implements Serializable {
    private final String DNI;
    private final String nombre;
    private final String apellidos;
    private final String dir;
    private final int tel;
    private final String email;
    private boolean sup;
    protected ArrayList<Asignatura> Materias;
    protected final boolean tiempo; // false = completo; true = parcial
    
    /**
     * Constructor de Alumno.
     * @param DNI Identificador del alumno
     * @param nombre Nombre del alumno
     * @param apellidos Apellidos del alumno
     * @param dir Dirección del alumno
     * @param tel Teléfono del alumno
     * @param email Correo eléctrónico del alumno
     * @param tiempo Indica si se está cursando el master a tiempo parcial (true)
     * o a tiempo completo (false)
     */
    public Alumno ( String DNI, String nombre, String apellidos, String dir, int tel, String email, boolean tiempo ){
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dir = dir;
        this.tel = tel;
        this.email = email;
        this.sup = false;
        this.tiempo = tiempo;
        ArrayList<Asignatura> nuevo = new ArrayList<Asignatura>();
        this.Materias = nuevo;
    }
    
    /**
     * Muestra el DNI del estudiante.
     * @return DNI del alumno
     */
    public String getDNI () {
        return DNI;
    }
    
    /**
     * Enseña el nombre del estudiante
     * @return Nombre del alumno
     */
    public String getNombre () {
        return nombre;
    }
    
    /**
     * Indica los apellidos del estudiante
     * @return Apellidos del alumno
     */
    public String getApellidos () {
        return apellidos;
    }
    
    /**
     * Muestra la dirección del alumno
     * @return Dirección del estudiante
     */
    public String getDir () {
        return dir;
    }
    
    /**
     * Enseña el teléfono del estudiante
     * @return Teléfono del alumno
     */
    public int getTel () {
        return tel;
    }
    
    /**
     * Muestra el correo electrónico del alumno
     * @return Email del estudiante
     */
    public String getMail () {
        return email;
    }
    
    /**
     * Indica si el alumno ha superado o no el máster
     * @return Verdadero si lo ha pasado; sino, falso
     */
    public boolean superado () {
        return sup;
    }
    
    /**
     * Identifica si el alumno está cursando el master a tiempo parcial o completo.
     * @return Verdadero si lo está realizando a tiempo parcial, falso si lo
     * cursa a tiempo completo
     */
    public boolean geTiempo () {
        return tiempo;
    }
    
    /**
     * Método abstracto para matricular a un alumno. Se implementa en sus subclases.
     * @param datos ArrayList con los datos de las asignaturas
     * @param ini Año de inicio del curso a matricularse
     */
    public abstract void addMatricula ( ArrayList<String> datos, int ini );
    
    /**
     * Método que muestra por pantalla las asignaturas en las que se ha matriculado
     * el alumno; indicando nombre, curso y nota. En caso de no haberse
     * matriculado en ninguna, se informa de ello al usuario.
     */
    public void mAsignaturas () {
        System.out.println( "LISTADO DE ASIGNATURAS" );
        System.out.println();
        if ( Materias.isEmpty() ) {
            System.out.println( "El alumno no esta matriculado en ninguna asignatura." );
        } else{
            for ( int i = 0; i < Materias.size(); i++ ) {
                System.out.println( Materias.get(i).mNombre() + "\t" + Materias.get(i).mCurso() + "\t" + Materias.get(i).mNota() );
            }
        }
    }
    
    /**
     * Método que calcula la nota media del alumno. Éste solo se emplea en caso
     * de que haya superado el máster e indica únicamente la nota media de las
     * asignaturas que haya aprobado.
     */
    public void media () {
        float media = 0;
        int j = 0;
        for ( int i = 0; i < Materias.size(); i++ ) {
            if ( Materias.get(i).aprob() ) {
                media = media + Materias.get(i).getNota();
                j++;
            }
        }
        media = media / j;
        System.out.println("El alumno ha superado el master con una nota media de " + media + "." );
    }
    
    /**
     * Método que indica si el alumno se ha matriculado en una asignatura
     * algún curso posterior al aportado por el usuario. Además, si el alumno
     * aún no se ha apuntado a ninguna asignatura se informa de ello.
     * @param ini Año de inicio de un curso
     * @return Verdadero si se ha matriculado alguna vez en años posteriores;
     * falso en cualquier otro caso
     */
    public boolean actCurso ( int ini ) {
        boolean curs = false;
        int i = 0;
        if ( Materias.isEmpty() ) {
            System.out.println( "El alumno no esta matriculado en ninguna asignatura." );
        } else {
            do {
                if ( Materias.get(i).mAno() > ini ) {
                    curs = true;
                }
                i++;
            } while ( ( i < Materias.size() ) && ( curs == false ) );
        }
        return curs;
    }
    
    /**
     * Método que permite evaluar todas las asignaturas en las que esté
     * matriculado el alumno, en un curso determinado.
     * Se informa cuando ya no resten más asignaturas por calificar ese curso.
     * @param ini Año de inicio del curso
     */
    public void calificar ( int ini ) {
        int i = 0;
        String NP;
        float nota = 12;
        boolean pres;
        do {
            if ( ( Materias.get(i).mAno() == ini ) && ( Materias.get(i).getNota() == -1 ) ) {
                do {
                    System.out.println( "Introduzca la calificacion de " + Materias.get(i).mNombre() + " (en caso de no presentado introduzca NP):" );
                    NP = MyInput.readString();
                    if ( NP.equals("NP") ) {
                        break;
                    } else {
                        nota = MyInput.readFloat(NP);
                    }
                } while ( ( 10 < nota ) || ( 0 > nota ) );
                if ( NP.equals("NP") ) {
                    pres = false;
                } else {
                    pres = true;
                    Materias.get(i).ponerNota(nota);
                }
                Materias.get(i).presentacion(pres);
            }
            i++;
        } while ( i < Materias.size() );
        System.out.println( "Ya no quedan asignaturas por evaluar del curso " + ini + "/" + ( ini + 1 ) +"." );
    }
    
    /**
     * Método que comprueba si el alumno ha superado el máster.
     * Comprueba si el alumno ha superado todas las asignaturas obligatorias
     * y dos parciales, certificando que sumen al menos 60 créditos. Se informa
     * al usuario en caso de que el alumno pase el máster.
     * @param datos ArrayList con los datos de las asignaturas
     */
    public void compSup( ArrayList<String> datos ) {
        int tam = datos.size();
        int tot = 0;
        int i = 0, j;
        int opAprob = 0;
        String cod = "0000";
        String tipo;
        int cred;
        StringTokenizer tokenizer = null;
        boolean sal;
        if ( Materias.isEmpty() == false ) {
            do { // Lee líneas del fichero
                tokenizer = new StringTokenizer( datos.get(i), "/" );
                cod = tokenizer.nextToken();
                i++;
                tokenizer.nextToken();
                tipo = tokenizer.nextToken();
                if ( tipo.equals("OB") ) {
                    // Buscamos si esta asignatura está aprobada
                    cred = Integer.parseInt(tokenizer.nextToken());
                    j = 0;
                    sal = true;
                    do {
                        if ( cod.equals(Materias.get(j).codigo()) && Materias.get(j).aprob() ) {
                            tot = tot + cred;
                            sal = false;
                        }
                        j++;
                    } while ( sal && ( j < Materias.size() ) );
                } else if ( tipo.equals("OP") && ( opAprob < 2 ) ) {
                    // Buscamos si esta asignatura está aprobada
                    cred = Integer.parseInt(tokenizer.nextToken());
                    j = 0;
                    sal = true;
                    do {
                        if ( cod.equals(Materias.get(j).codigo()) && Materias.get(j).aprob() ) {
                            tot = tot + cred;
                            opAprob++;
                            sal = false;
                        }
                        j++;
                    } while ( sal && ( opAprob < 2 ) && ( j < Materias.size() ) );
                }
            } while ( ( i < tam ) && ( tot < 60 ) );
            if ( tot >= 60 ) {
                this.sup = true;
                System.out.println( "¡Enhorabuena, ha superado el master!" );
            }
        }
    }
}
