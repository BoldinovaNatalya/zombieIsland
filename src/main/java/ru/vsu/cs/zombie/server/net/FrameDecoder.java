package ru.vsu.cs.zombie.server.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

class FrameDecoder extends ReplayingDecoder<FrameDecoder.DecoderState> {

    enum DecoderState {
        READ_LENGTH,
        READ_CONTENT
    }

    private int length;

    public FrameDecoder() {
        super(DecoderState.READ_LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> objects) throws Exception {

        switch (state()) {
            case READ_LENGTH:
                length = buf.readInt();
                System.out.println(String.format("length = %d", length));
                checkpoint(DecoderState.READ_CONTENT);

            case READ_CONTENT:
                ByteBuf tmp = buf.readBytes(length);
                checkpoint(DecoderState.READ_LENGTH);
                objects.add(tmp.toString(CharsetUtil.UTF_8));
                return;

            default:
                throw new Error("Shouldn't reach here.");
        }

    }
}