package net.sloth.storage.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationTypeAdapter extends TypeAdapter<ResourceLocation> {
    public ResourceLocationTypeAdapter() {
    }

    public void write(JsonWriter writer, ResourceLocation value) throws IOException {
        writer.beginObject().name("resourceLocation").value(value.toString()).endObject();
    }

    public ResourceLocation read(JsonReader reader) throws IOException {
        ResourceLocation resourceLocation = new ResourceLocation("place:holder");

        try {
            reader.beginObject();
            String fieldName = null;

            while(reader.hasNext()) {
                JsonToken token = reader.peek();
                if (token.equals(JsonToken.NAME)) {
                    fieldName = reader.nextName();
                }

                if ("resourceLocation".equals(fieldName)) {
                    token = reader.peek();
                    resourceLocation = new ResourceLocation(reader.nextString());
                }
            }

            reader.endObject();
        } catch (IllegalStateException var5) {
            resourceLocation = new ResourceLocation(reader.nextString());
        }

        return resourceLocation;
    }
}
