package com.weweb.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by wshen on 6/30/2017.
 */
public class EchoClient {
	public void connect(int port,String host) {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ByteBuf delimiter= Unpooled.copiedBuffer("$_".getBytes());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new EchoClientHandler());
            }
        });
        //发起异步连接操作
        try {
            ChannelFuture future=bootstrap.connect(host,port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
     class EchoClientHandler extends ChannelHandlerAdapter {
        private int counter;
        final static String ECHO_REQ="Hi,Jack,Welcome to Netty.$_";
        public EchoClientHandler(){}

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i=0;i<10;i++){
                ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("This is "+ ++counter+ " times receive server:["+msg+"]");
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {
        new EchoClient().connect(9000,"127.0.0.1");
    }
}
