package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
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
