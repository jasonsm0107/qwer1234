package net.sloth.command.argument.serializer;


import com.google.gson.JsonObject;
import net.sloth.command.argument.FormArgument;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class FormArgumentSerializer implements IArgumentSerializer<FormArgument> {
    public void serializeToNetwork(FormArgument pArgument, PacketBuffer pBuffer) {
        pBuffer.writeUtf(pArgument.getPokemonArgumentName());
    }

    public FormArgument deserializeFromNetwork(PacketBuffer pBuffer) {
        return FormArgument.pixelmonForm(pBuffer.readUtf());
    }

    public void serializeToJson(FormArgument pArgument, JsonObject pJson) {
        pJson.addProperty("pokemon", pArgument.getPokemonArgumentName());
    }

    public FormArgumentSerializer() {
    }
}
