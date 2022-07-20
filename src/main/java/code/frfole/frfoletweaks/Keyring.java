package code.frfole.frfoletweaks;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

public final class Keyring {
    private final UnixDomainSocketAddress socket;
    private final SocketChannel socketChannel;

    public Keyring(@NotNull Path socketPath) throws IllegalArgumentException, IOException {
        socket = UnixDomainSocketAddress.of(socketPath);
        socketChannel = SocketChannel.open(StandardProtocolFamily.UNIX);
    }

    public Optional<Response> request(Request request) throws IOException {
        socketChannel.connect(socket);
        System.out.println(socketChannel.write(request.asBuffer()));
        ByteBuffer buffer = ByteBuffer.allocate(256);
        socketChannel.read(buffer);
        socketChannel.close();
        buffer.flip();
        if (buffer.get() == 0) {
            return Optional.of(new Response(buffer.slice().array()));
        }
        return Optional.empty();
    }

    public boolean testConnection() throws IOException {
        Request request = new Keyring.Request(
                "secret".getBytes(StandardCharsets.UTF_8),
                "test".getBytes(StandardCharsets.UTF_8)
        );
        socketChannel.connect(socket);
        System.out.println(socketChannel.write(request.asBuffer()));
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.read(buffer);
        socketChannel.close();
        buffer.flip();
        return buffer.get() == 0x1;
    }

    public record Request(byte[] secret, byte[] data) {
        public ByteBuffer asBuffer() {
            return ByteBuffer.allocate(4 + secret.length + 4 + data.length)
                    .putInt(secret.length)
                    .put(secret)
                    .putInt(data.length)
                    .put(data)
                    .flip();
        }
    }

    public record Response(byte[] data) {

    }
}
