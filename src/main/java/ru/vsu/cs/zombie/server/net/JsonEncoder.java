package ru.vsu.cs.zombie.server.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.List;

public class JsonEncoder extends MessageToMessageEncoder<Command> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Command command, List<Object> objects) throws Exception {
       // ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = new ObjectMapper().writer();
        objects.add(new TextWebSocketFrame(ow.writeValueAsString(command)));
    }
}
