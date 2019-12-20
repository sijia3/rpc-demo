package com.sijia3.code;

import com.sijia3.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author sijia3
 * @date 2019/12/19 16:31
 */
//public class RpcDecoder extends ByteToMessageDecoder {
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println("开始解码");
//        if (in.readableBytes() < 4){      // 可读字节不够长度
//            return;
//        }
//        in.markReaderIndex();      // 设置读开始的标志位，当数据无法完成业务时，可复位
//        int dataLength = in.readInt();
//        if (in.readableBytes() < dataLength){
//            in.resetReaderIndex();       // 复位
//            return;
//        }
//        byte[] data = new byte[dataLength];
//        in.readBytes(data);
//        MessagePack messagePack = new MessagePack();
//        out.add(messagePack.read(data));
//    }
//}
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, genericClass));
    }
}
