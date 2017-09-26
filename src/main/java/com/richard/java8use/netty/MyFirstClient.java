package com.richard.java8use.netty;

import org.apache.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月26日 上午11:01:15
*/
public class MyFirstClient {

	private static Logger logger = Logger.getLogger(MyFirstClient.class);
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
				.handler(new MyFirstClientInitializer());
			ChannelFuture channelFuture = bootstrap.connect("10.12.22.201", 8899).sync();
			logger.info("Client start successfully");
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}

class MyFirstClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new MyFirstClientHandler());
	}
}

class MyFirstClientHandler extends ChannelInboundHandlerAdapter {
	
	private Logger logger = Logger.getLogger(MyFirstClientHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf result = (ByteBuf) msg;
		byte[] byteResult = new byte[result.readableBytes()];
		result.readBytes(byteResult);
		String strResult = new String(byteResult);
		logger.info("Server response: " + strResult);
		result.release();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String text = "Hello, Server!";
		logger.info("Client -> Server: " + text);
		ctx.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
	}
}
