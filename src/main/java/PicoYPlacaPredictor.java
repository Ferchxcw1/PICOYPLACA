import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class PicoYPlacaPredictor {

    // Horarios de restricción (mañana y tarde)
    private static final LocalTime MORNING_START = LocalTime.of(7, 0);
    private static final LocalTime MORNING_END = LocalTime.of(9, 30);
    private static final LocalTime AFTERNOON_START = LocalTime.of(16, 0);
    private static final LocalTime AFTERNOON_END = LocalTime.of(21, 0);

    // Mapa que asocia días de la semana con los dígitos restringidos
    private static final Map<Integer, int[]> RESTRICTION_MAP = new HashMap<>();

    static {
        RESTRICTION_MAP.put(1, new int[]{1, 2}); // Lunes
        RESTRICTION_MAP.put(2, new int[]{3, 4}); // Martes
        RESTRICTION_MAP.put(3, new int[]{5, 6}); // Miércoles
        RESTRICTION_MAP.put(4, new int[]{7, 8}); // Jueves
        RESTRICTION_MAP.put(5, new int[]{9, 0}); // Viernes
    }

    // Metodo principal que verifica si el vehículo puede circular
    public boolean puedeCircular(String placa, String fechaStr, String horaStr) {
        try {
            // Obtener el último dígito de la placa
            int ultimoDigito = obtenerUltimoDigito(placa);
            // Convertir la fecha de String a LocalDate
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // Convertir la hora de String a LocalTime
            LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

            // Obtener el día de la semana (1=Lunes, 7=Domingo)
            int diaSemana = fecha.getDayOfWeek().getValue();

            // Si es sábado o domingo, no hay restricción
            if (diaSemana == 6 || diaSemana == 7) {
                return true; // Puede circular
            }

            // Verificar si el último dígito de la placa está restringido ese día
            int[] digitosRestringidos = RESTRICTION_MAP.get(diaSemana);
            boolean estaRestringido = false;
            for (int digito : digitosRestringidos) {
                if (ultimoDigito == digito) {
                    estaRestringido = true;
                    break;
                }
            }

            // Si no está restringido, puede circular
            if (!estaRestringido) {
                return true; // Puede circular
            }

            // Verificar si la hora está en el rango de restricción
            if ((hora.equals(MORNING_START) || (hora.isAfter(MORNING_START) && hora.isBefore(MORNING_END))) ||
                    (hora.equals(AFTERNOON_START) || (hora.isAfter(AFTERNOON_START) && hora.isBefore(AFTERNOON_END)))) {
                return false; // No puede circular
            }

            // Si no está en el rango de restricción, puede circular
            return true;

        } catch (DateTimeParseException e) {
            System.out.println("Error en el formato de fecha u hora. Utilice los formatos 'yyyy-MM-dd' para fecha y 'HH:mm' para hora.");
            return false;
        }
    }

    // Metodo auxiliar para obtener el último dígito de la placa
    private int obtenerUltimoDigito(String placa) {
        char ultimoCaracter = placa.charAt(placa.length() - 1);
        return Character.getNumericValue(ultimoCaracter);
    }

    public static void main(String[] args) {
        PicoYPlacaPredictor predictor = new PicoYPlacaPredictor();
        String placa = "ABC1234";
        String fecha = "2024-09-30";
        String hora = "08:00";

        boolean puedeCircular = predictor.puedeCircular(placa, fecha, hora);
        if (puedeCircular) {
            System.out.println("El vehículo puede circular.");
        } else {
            System.out.println("El vehículo NO puede circular.");
        }
    }
}