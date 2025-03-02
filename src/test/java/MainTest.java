import com.ecwid.task.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

    @Test
    void testEmptyArguments() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[0]),
                "Incorrect number of arguments provided: 0");
    }

    @Test
    void testTooManyArguments() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"1", "2"}),
                "Incorrect number of arguments provided: 2");
    }
}
