package ru.vsu.cs.zombie.server.net;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame stringJson, List<Object> objects) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(stringJson.text());
            objects.add(mapper.readValue(stringJson.text(), Command.getClassByName(rootNode.path("name").asText())));
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
             e.printStackTrace();
         }
    }
}
