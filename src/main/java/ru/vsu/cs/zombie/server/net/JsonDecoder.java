package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame stringJson, List<Object> objects) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(stringJson.text());
        objects.add(mapper.readValue(stringJson.text(), Command.getClassByName(rootNode.path("name").getTextValue())));
    }
}
