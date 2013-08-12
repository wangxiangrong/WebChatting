package com.glassky.www;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;  
import org.apache.catalina.websocket.WsOutbound;
/**
 * Servlet implementation class ChatSocketServlet
 */
@WebServlet("/chat")
public class ChatSocketServlet extends WebSocketServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override  
    protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest request) {  
        return new ChatMessageInbound();  
    }  	
}
