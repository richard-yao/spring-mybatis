package com.richard.java8use.netty;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月26日 上午10:19:14
* Use netty to create a nio server
*/
public class MyFirstServer {

	private static Logger logger = Logger.getLogger(MyFirstServer.class);
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.childHandler(new MyFirstServerInitializer());
			int bindPort = 8899;
			ChannelFuture channelFuture = bootstrap.bind(bindPort).sync();
			logger.info("Server start successfully and bind port: " + bindPort);
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}

class MyFirstServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		MyFirstServerHandler handler = new MyFirstServerHandler();
		pipeline.addLast(handler);
	}
	
}

class MyFirstServerHandler extends ChannelInboundHandlerAdapter {
	
	private Logger logger = Logger.getLogger(MyFirstServerHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf result = (ByteBuf) msg;
		byte[] byteResult = new byte[result.readableBytes()];
		result.readBytes(byteResult);
		String strResult = new String(byteResult);
		logger.info("Client request: " + strResult);
		String response = "Got message, over![from Server]";
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes())); // return message with byte buffer
	}
	
	/**
	 * Catch exception when client is offline and disconnect
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause, cause);
		ctx.close();
	}
}