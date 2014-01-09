package ru.vsu.cs.zombie.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FrameEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byteBuf.capacity(s.length() + 4);
        byteBuf.writeInt(s.length());
        byteBuf.writeBytes(s.getBytes());
    }
}
