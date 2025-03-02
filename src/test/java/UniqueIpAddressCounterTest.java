import com.ecwid.task.UniqueIpAddressCounter;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueIpAddressCounterTest {

    @Test
    void testEmptyFile() throws URISyntaxException {
        //given
        Path resourcePath = Path.of(getClass().getResource("empty.txt").toURI());
        System.out.println(resourcePath);

        //when
        UniqueIpAddressCounter counter = new UniqueIpAddressCounter();
        long actualUniqueIps = counter.countUniqueIps(resourcePath.toAbsolutePath());

        //then
        assertEquals(0, actualUniqueIps);
    }

    @Test
    void testCorrectCountOfUniqueIps() throws URISyntaxException {
        //given
        Path resourcePath = Path.of(getClass().getResource("ip_addresses.txt").toURI());
        System.out.println(resourcePath);

        //when
        UniqueIpAddressCounter counter = new UniqueIpAddressCounter();
        long actualUniqueIps = counter.countUniqueIps(resourcePath.toAbsolutePath());

        //then
        assertEquals(8, actualUniqueIps);
    }
}
