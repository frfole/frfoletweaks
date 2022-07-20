import code.frfole.frfoletweaks.Keyring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeyringTest {

    @Test
    public void connectTest() throws IOException {
        Path tmp = Paths.get("/tmp/keyring.sock");
        Keyring keyring = new Keyring(tmp);
//        keyring.request(new Keyring.Request(
//                "secret".getBytes(StandardCharsets.UTF_8),
//                "test".getBytes(StandardCharsets.UTF_8)
//        ));
        Assertions.assertTrue(keyring.testConnection());
    }
}
