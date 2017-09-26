package com.richard.java8use.netty;


import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月26日 下午2:45:37
* ws://localhost:8899/
*/
public class WebsocketServer {

	private Logger logger = Logger.getLogger(WebsocketServer.class);

	private int port;
	
	public WebsocketServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		int port = 8899;
		try {
			new WebsocketServer(port).run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.childHandler(new WebsocketServerInitializer()).option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = bootstrap.bind(getPort()).sync();
			logger.info("Websocket server start successfully, bind port: " + getPort());
			// wait for server socket close
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
			logger.info("Websocket server close successfully");
		}
	}
	
	public int getPort() {
		return port;
	}
}

class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new WebSocketServerProtocolHandler("/")); // This is websocket url path, please ensure it is right, or connection cannot establish
		pipeline.addLast(new TextWebSocketFrameHandler());
	}
}

class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private Logger logger = Logger.getLogger(TextWebSocketFrameHandler.class);
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		Channel incoming = ctx.channel();
		String str = null;
		for(Channel temp : channels) {
			if(temp != incoming) {
				str = "[" + incoming.remoteAddress() + "]" + msg.text();
			} else {
				str = "[you]" + msg.text();
			}
			temp.writeAndFlush(new TextWebSocketFrame(str));
		}
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		String welcomeMsg = "[SERVER] - " + incoming.remoteAddress() + " join in";
		channels.writeAndFlush(new TextWebSocketFrame(welcomeMsg));
		channels.add(incoming);
		logger.info("Client: " + incoming.remoteAddress() + " join in");
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		String broadcastMsg = "[SERVER] - " + incoming.remoteAddress() + " leave";
		channels.writeAndFlush(new TextWebSocketFrame(broadcastMsg));
		logger.info("Client:" + incoming.remoteAddress() + " leave");
		
		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + " online");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + " offline");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + " disconnect");
		//cause.printStackTrace();
		ctx.close();
	}
}