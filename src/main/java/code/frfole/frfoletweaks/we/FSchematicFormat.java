package code.frfole.frfoletweaks.we;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class FSchematicFormat implements ClipboardFormat {
    @Override
    public String getName() {
        return "F_SCHEMATIC";
    }

    @Override
    public Set<String> getAliases() {
        return Set.of("fs", "fschem", "fschematic");
    }

    @Override
    public ClipboardReader getReader(InputStream inputStream) {
        return new FSchematicReader(inputStream);
    }

    @Override
    public ClipboardWriter getWriter(OutputStream outputStream) {
        return new FSchematicWriter(outputStream);
    }

    @Override
    public boolean isFormat(File file) {
        return true;
    }

    @Override
    public String getPrimaryFileExtension() {
        return "fs";
    }

    @Override
    public Set<String> getFileExtensions() {
        return getAliases();
    }
}
