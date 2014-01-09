package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.codehaus.jackson.map.ObjectMapper;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String stringJson, List<Object> objects) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        objects.add(mapper.readValue(stringJson, Command.class));
    }
}
