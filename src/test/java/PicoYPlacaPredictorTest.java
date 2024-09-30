import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PicoYPlacaPredictorTest {

    @Test
    public void testPuedeCircular() {
        PicoYPlacaPredictor predictor = new PicoYPlacaPredictor();
        // Ejemplo de prueba para una placa que puede circular
        assertTrue(predictor.puedeCircular("ABC1234", "2024-09-30", "08:00"),
                "El vehículo debería poder circular a esta hora.");
    }

    @Test
    public void testNoPuedeCircular() {
        PicoYPlacaPredictor predictor = new PicoYPlacaPredictor();
        // Cambiar a un último dígito restringido para el lunes
        assertFalse(predictor.puedeCircular("ABC1232", "2024-09-30", "08:30"),
                "El vehículo NO debería poder circular a esta hora.");
    }
}