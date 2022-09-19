package code.frfole.frfoletweaks.we;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.math.BlockVector3;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;

public class FSchematicWriter implements ClipboardWriter {

    private final DataOutputStream stream;

    public FSchematicWriter(OutputStream outputStream) {
        this.stream = new DataOutputStream(new BufferedOutputStream(outputStream));
    }

    @Override
    public void write(Clipboard clipboard) throws IOException {
        int offsetX = clipboard.getMinimumPoint().getBlockX();
        int offsetY = clipboard.getMinimumPoint().getBlockY();
        int offsetZ = clipboard.getMinimumPoint().getBlockZ();
        int sizeX = clipboard.getDimensions().getBlockX();
        int sizeY = clipboard.getDimensions().getBlockY();
        int sizeZ = clipboard.getDimensions().getBlockZ();
        Object2IntArrayMap<String> palette = new Object2IntArrayMap<>();
        palette.defaultReturnValue(-1);
        ArrayDeque<BlockEntry> queue = new ArrayDeque<>();
        int paletteSize = 0;
        int currentPaletteIndex = -1;
        int currentLen = 0;
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                for (int y = 0; y < sizeY; y++) {
                    currentLen++;
                    String blockString = clipboard.getBlock(BlockVector3.at(x + offsetX, y + offsetY, z + offsetZ)).getAsString();
                    int blockState = palette.getInt(blockString);
                    if (blockState == -1) {
                        blockState = paletteSize;
                        palette.put(blockString, blockState);
                        paletteSize++;
                    }
                    if (currentPaletteIndex == -1) {
                        currentPaletteIndex = blockState;
                    }
                    if (blockState != currentPaletteIndex || currentLen == Integer.MAX_VALUE) {
                        queue.add(new BlockEntry(currentPaletteIndex, currentLen - 1));
                        currentPaletteIndex = blockState;
                        currentLen = 1;
                    }
                }
            }
        }
        if (currentLen > 0) {
            queue.add(new BlockEntry(currentPaletteIndex, currentLen));
        }
        stream.writeInt(sizeX);
        stream.writeInt(sizeY);
        stream.writeInt(sizeZ);
        stream.writeInt(offsetX - clipboard.getOrigin().getX());
        stream.writeInt(offsetY - clipboard.getOrigin().getY());
        stream.writeInt(offsetZ - clipboard.getOrigin().getZ());
        stream.writeInt(paletteSize);
        for (String blockString : palette.keySet()) {
            stream.writeUTF(blockString);
        }
        stream.writeInt(queue.size());
        while (!queue.isEmpty()) {
            BlockEntry entry = queue.pollFirst();
            writeVarInt(entry.blockId(), stream);
            writeVarInt(entry.len(), stream);
        }
    }

    private void writeVarInt(int n, OutputStream stream) throws IOException {
        while ((n & -128) != 0) {
            stream.write(n & 127 | 128);
            n >>>= 7;
        }
        stream.write(n);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    public record BlockEntry(int blockId, int len) { }
}
