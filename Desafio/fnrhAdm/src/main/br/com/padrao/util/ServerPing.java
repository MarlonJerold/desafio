package br.com.padrao.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;

public class ServerPing {
	
	private String host;
	
	private int port;
	
	private long time;
	
	private String status;
	
	private boolean active;
	
	private String description;
	
	private String directions;
	
	public ServerPing(String host, int port, String description) {
		this.host = host;
		this.port = port;
		this.description = description;
		
		refresh();
	}
	
	private long now() {
		return new GregorianCalendar().getTimeInMillis();
	}
	
	public void refresh() {
		long timeStart = now();
		
	    Socket s = null;
	    try {
	        s = new Socket();
	        s.setReuseAddress(false);
	        SocketAddress sa = new InetSocketAddress(this.host, this.port);
	        s.connect(sa, 3000);
	        this.active = true;
	        this.status = "Ok";
	        this.directions = "Ok";
	    } catch (ConnectException e) {
	    	this.active = false;
	    	this.status = e.getClass().getName();
	    	this.directions = "Verifique se o serviço está disponível.";
	    } catch (UnknownHostException e) {
	    	this.active = false;
	    	this.status = e.getClass().getName();
	    	this.directions = "O servidor não foi encontrado. Veja se está ligado.";
	    } catch (SocketTimeoutException e) {
	    	//TODO
	    } catch (NoRouteToHostException e) {
	    	//TODO
	    } catch (IOException e) {
	    	this.active = false;
	    	this.status = e.getClass().getName();
	    	this.directions = "Erro inesperado.";
	    	//e.printStackTrace();java.net.SocketTimeoutException
	    } finally {
	        this.time = now() - timeStart;

	        if (s != null) {
	            try {
	                s.close();
	            } catch (IOException e) {
	            }
	        }
	    }
	}

	public long getTime() {
		return time;
	}

	public String getStatus() {
		return status;
	}

	public boolean isActive() {
		return active;
	}

	public String getDirections() {
		return directions;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {
		ServerPing s = new ServerPing("bparkto-cluster", 1521, "Oracle Database");
		System.out.println("Host: " + s.getHost());
		System.out.println("Active: " + s.isActive());
		System.out.println("Time: " + s.getTime() + " millis");
		System.out.println("Status: " + s.getStatus());
		System.out.println("Directions: " + s.getDirections());
	}
}
