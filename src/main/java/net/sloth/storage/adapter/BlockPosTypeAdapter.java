package net.sloth.storage.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.minecraft.util.math.BlockPos;

public class BlockPosTypeAdapter extends TypeAdapter<BlockPos> {
    public BlockPosTypeAdapter() {
    }

    public void write(JsonWriter writer, BlockPos value) throws IOException {
        writer.beginObject().name("x").value((long)value.getX()).name("y").value((long)value.getY()).name("z").value((long)value.getZ()).endObject();
    }

    public BlockPos read(JsonReader reader) throws IOException {
        BlockPos blockPos = new BlockPos(0, 0, 0);
        reader.beginObject();
        String fieldName = null;

        while(reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldName = reader.nextName();
            }

            if ("x".equals(fieldName)) {
                token = reader.peek();
                blockPos = new BlockPos(reader.nextInt(), blockPos.getY(), blockPos.getZ());
            }

            if ("y".equals(fieldName)) {
                token = reader.peek();
                blockPos = new BlockPos(blockPos.getX(), reader.nextInt(), blockPos.getZ());
            }

            if ("z".equals(fieldName)) {
                token = reader.peek();
                blockPos = new BlockPos(blockPos.getX(), blockPos.getY(), reader.nextInt());
            }
        }

        reader.endObject();
        return blockPos;
    }
}
