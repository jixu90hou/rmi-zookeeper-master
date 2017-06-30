package com.weweb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by wshen on 6/30/2017.
 */
public class EchoServer {
	public void bind(int port) {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
        option(ChannelOption.SO_BACKLOG,100).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch){
                ByteBuf delimiter= Unpooled.copiedBuffer("$_".getBytes());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new EchoServerHandler());
            }
        });
        try {
        //绑定端口，同步等待成功
        ChannelFuture future=bootstrap.bind(port).sync();
        //等待服务器监听端口关闭

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    @ChannelHandler.Sharable
     class EchoServerHandler extends ChannelHandlerAdapter {
        int counter=0;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body= (String) msg;
            System.out.println("This is "+ ++counter +"times receive client:["+body+"]");
            body+="$_";
            ByteBuf echo= Unpooled.copiedBuffer(body.getBytes());
            ctx.writeAndFlush(echo);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {
        new EchoServer().bind(9000);
    }
}
