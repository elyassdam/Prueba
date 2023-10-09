import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.util.Pair;

public class LeerFichAleatorio {

    public static void main(String[] args) throws IOException {
        File fichRandom = new File("fic_accesso_aleat.dat"); // Cambia el nombre del archivo si es diferente
        RandomAccessFile file = new RandomAccessFile(fichRandom, "r");

        // Define los campos y sus longitudes de acuerdo a cómo fueron escritos
        Pair<String, Integer> dniCampo = new Pair<>("DNI", 9);
        Pair<String, Integer> nombreCampo = new Pair<>("NOMBRE", 32);
        Pair<String, Integer> cpCampo = new Pair<>("CP", 5);

        // Calcula la longitud total del registro
        int longReg = dniCampo.getValue() + nombreCampo.getValue() + cpCampo.getValue();

        // Calcula la cantidad de registros en el archivo
        long numReg = file.length() / longReg;

        // Procesa cada registro
        for (long i = 0; i < numReg; i++) {
            file.seek(i * longReg);

            // Lee los campos del registro
            String dni = readString(file, dniCampo.getValue());
            String nombre = readString(file, nombreCampo.getValue());
            String cp = readString(file, cpCampo.getValue());

            // Imprime la información del registro
            System.out.printf("DNI: %s, NOMBRE: %s, CP: %s%n", dni, nombre, cp);
        }

        file.close();
    }

    // Método para leer una cadena de caracteres de longitud fija desde el archivo
    private static String readString(RandomAccessFile file, int length) throws IOException {
        byte[] buffer = new byte[length];
        file.readFully(buffer);
        return new String(buffer, "UTF-8").trim();
    }
}
