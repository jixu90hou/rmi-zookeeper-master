package com.weweb.netty;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wshen on 6/28/2017.
 */
public class TimeServer {
    public void bind(int port) {
        // 配置服务器端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,
                1024).childHandler(new ChildChannelHandler());
        //绑定端口，同步等待成功
        try {
            ChannelFuture future=bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            //退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
}
class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object message) throws UnsupportedEncodingException {
        ByteBuf buf= (ByteBuf) message;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("The time server receive order:"+body);
        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date().toString():"BAD ORDER";
        ByteBuf responseText= Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(responseText);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    public static void main(String[] args) {
        int port=9080;
        new TimeServer().bind(port);

    }
}
