package com.glassky.www;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;


public class ChatMessageInbound extends MessageInbound {
	private final static Map<Integer, WsOutbound> map = new HashMap<Integer, WsOutbound>(); //会话信息
	private final static Map<Integer,String> list=new HashMap<Integer,String>();//用户列表
	
	@Override  
    protected void onOpen(WsOutbound outbound) { 
		System.out.println("Hash code:"+outbound.hashCode());
        map.put(outbound.hashCode(), outbound);  
        super.onOpen(outbound);  
    }  

    @Override  
    protected void onClose(int status) {  
        map.remove(getWsOutbound().hashCode()); 
        list.remove(getWsOutbound().hashCode());
        super.onClose(status);  
    }  

    @Override  
    protected void onBinaryMessage(ByteBuffer buffer) throws IOException {  
        // TODO Auto-generated method stub  

    }  

    @Override  
    protected void onTextMessage(CharBuffer buffer) throws IOException {  
        String msg = buffer.toString();  
        Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
        
        //根据传递过来的信息的开头，判断操作
        if(msg.startsWith("add:")){
        	msg=addUser(msg.substring(4));        	
        }else if(msg.startsWith("delete:")){
        	msg=deleteUser();
        }else{
        	msg = list.get(getWsOutbound().hashCode())+" " + sdf.format(date) + "\n " + msg+"\n";  
        }
        broadcast(msg);  
    }  
    
    private String addUser(String user){
    	list.put(getWsOutbound().hashCode(), user);
    	StringBuffer sb=new StringBuffer("user:");
    	for(String s: list.values()){
    		sb.append(" "+s+"\n");
    	}
    	return sb.toString();
    }
    private String deleteUser(){
    	list.remove(getWsOutbound().hashCode());
    	StringBuffer sb=new StringBuffer("user:");
    	for(String s: list.values()){
    		sb.append(" "+s+"\n");
    	}
    	return sb.toString();
    }

    private void broadcast(String msg) {  
        Set<Integer> set = map.keySet();  
        for (Integer integer : set) {  
            WsOutbound outbound = map.get(integer);             
            CharBuffer buffer = CharBuffer.wrap(msg);  
            try {  
                outbound.writeTextMessage(buffer);  
                outbound.flush();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
