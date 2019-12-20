package com.sijia3.code;

import com.sijia3.base.Request;
import com.sijia3.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sijia3
 * @date 2019/12/19 16:31
 */
//public class RpcEncoder extends MessageToByteEncoder {
//    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
//        Request request = (Request)o;
//        MessagePack messagePack = new MessagePack();
//        byte[] bytes = messagePack.write(request);
//        byteBuf.writeInt(bytes.length);
//        byteBuf.writeBytes(bytes);
//        System.out.println("编码成功");
//    }
//}
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
