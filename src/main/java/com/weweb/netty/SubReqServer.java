package com.weweb.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wshen on 6/30/2017.
 */
public class SubReqServer {
}
@ChannelHandler.Sharable
class SubReqServerHandler extends ChannelHandlerAdapter{
    public void channelRead(ChannelHandlerContext ctx,Object msg){

    }
}
