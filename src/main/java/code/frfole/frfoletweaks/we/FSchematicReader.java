package code.frfole.frfoletweaks.we;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extension.input.InputParseException;
import com.sk89q.worldedit.extension.input.ParserContext;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FSchematicReader implements ClipboardReader {

    private final DataInputStream inputStream;

    public FSchematicReader(InputStream inputStream) {
        this.inputStream = new DataInputStream(new BufferedInputStream(inputStream));
    }


    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public Clipboard read() throws IOException {
        int sizeX = inputStream.readInt();
        int sizeY = inputStream.readInt();
        int sizeZ = inputStream.readInt();
        int offsetX = inputStream.readInt();
        int offsetY = inputStream.readInt();
        int offsetZ = inputStream.readInt();
        int paletteSize = inputStream.readInt();
        BlockState[] palette = new BlockState[paletteSize];
        {
            ParserContext parserContext = new ParserContext();
            parserContext.setRestricted(false);
            parserContext.setTryLegacy(false);
            parserContext.setPreferringWildcard(false);
            BlockState state;
            for (int paletteIndex = 0; paletteIndex < paletteSize; paletteIndex++) {
                String blockString = inputStream.readUTF();
                try {
                    state = WorldEdit.getInstance().getBlockFactory().parseFromInput(blockString, parserContext).toImmutableState();
                } catch (InputParseException e) {
                    assert BlockTypes.AIR != null;
                    state = BlockTypes.AIR.getDefaultState();
                }
                palette[paletteIndex] = state;
            }
        }
        BlockArrayClipboard clipboard = new BlockArrayClipboard(new CuboidRegion(
                BlockVector3.at(offsetX, offsetY, offsetZ),
                BlockVector3.at(offsetX + sizeX, offsetY + sizeY, offsetZ + sizeZ)
        ));
        {
            inputStream.readInt();
            int currentBlockId = 0;
            int currentLen = 0;
            for (int x = 0; x < sizeX; x++) {
                for (int z = 0; z < sizeZ; z++) {
                    for (int y = 0; y < sizeY; y++) {
                        if (currentLen == 0) {
                            currentBlockId = inputStream.readInt();
                            currentLen = inputStream.readInt();
                        }
                        currentLen--;
                        BlockState state = palette[currentBlockId];
                        try {
                            clipboard.setBlock(BlockVector3.at(x + offsetX, y + offsetY, z + offsetZ), state);
                        } catch (WorldEditException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        return clipboard;
    }
}
