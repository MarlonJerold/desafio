package br.com.padrao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;

public class TestConnection {

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	private String host;

	private int port;

	private long time;

	private String status;

	private boolean active;

	private String description;

	private String directions;

	public TestConnection() {

	}
	
	public TestConnection(String host, int port, String description) {
		this.host = host;
		this.port = port;
		this.description = description;

		refresh();
	}


	public boolean isUrlActive(String link) {
		HttpURLConnection con;
		try {
			URL url = new URL(link);
			con = (HttpURLConnection) url.openConnection();
			con.connect();
			//con.getResponseCode();
			return true;
		} catch (MalformedURLException mx) {
			this.active = false;
			this.status = mx.getClass().getName();
			this.directions = "Verifique se o serviço está disponível.";
			return false;

		} catch (ConnectException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Verifique se o serviço está disponível.";
			return false;
		} catch (UnknownHostException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "O servidor não foi encontrado. Veja se está ligado.";
			return false;
		} catch (SocketTimeoutException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "O servidor extrapolou o tempo padrão de resposta.";
			return false;
		} catch (NoRouteToHostException e) {
			// TODO
			return false;
		} catch (IOException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Erro inesperado.";
			// e.printStackTrace();java.net.SocketTimeoutException
			return false;
		}

	}
	
	public  boolean isUrlActive(String host, String port,String complemento) {
		try {
			String endereco = "http://";
			endereco+=host;
			endereco+=":"+port;
			if(complemento!=null){
				endereco+=complemento;
			}			
			
			URL url = new URL(endereco);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			return true;
		} catch (MalformedURLException mx) {
			this.active = false;
			this.status = mx.getClass().getName();
			this.directions = "Verifique se o serviço está disponível.";
			return false;

		} catch (ConnectException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Verifique se o serviço está disponível.";
			return false;
		} catch (UnknownHostException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "O servidor não foi encontrado. Veja se está ligado.";
			return false;
		} catch (SocketTimeoutException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "O servidor extrapolou o tempo padrão de resposta.";
			return false;
		} catch (NoRouteToHostException e) {
			// TODO
			return false;
		} catch (IOException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Erro inesperado.";
			// e.printStackTrace();java.net.SocketTimeoutException
			return false;
		} catch (Exception e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Erro inesperado.";
			// e.printStackTrace();java.net.SocketTimeoutException
			return false;
		}

	}



	private long now() {
		return new GregorianCalendar().getTimeInMillis();
	}
	
	public boolean testMethodWebService(String link) {
		if(isUrlActive(link)){
			try {
				URL url = new URL(link);
				url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				StringBuilder sb = new StringBuilder();

				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
				in.close();
				String total = sb.toString();
				if(total.contains("<faultstring>")){			

					int beginFaultstring = total.indexOf("<faultstring>");
					int endFaultstring = total.indexOf("</faultstring>");
					String faultstring = total.substring(beginFaultstring,
							endFaultstring).replace("<faultstring>", "faultstring=");
					
					this.description = faultstring;
					
					return false;
				}else {					
					return true;
				}
			} catch (IOException e) {
				this.description = "Erro inesperado.";
				this.status = e.getClass().getName();				
				return false;
			}	
		}else {
			return false;
		}
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
			// TODO
		} catch (NoRouteToHostException e) {
			// TODO
		} catch (IOException e) {
			this.active = false;
			this.status = e.getClass().getName();
			this.directions = "Erro inesperado.";
			// e.printStackTrace();java.net.SocketTimeoutException
		} finally {
			this.time = now() - timeStart;
			this.active = false;

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
		TestConnection s = new TestConnection("bparkto-cluster", 1521,
				"Oracle Database");
		System.out.println("Host: " + s.getHost());
		System.out.println("Active: " + s.isActive());
		System.out.println("Time: " + s.getTime() + " millis");
		System.out.println("Status: " + s.getStatus());
		System.out.println("Directions: " + s.getDirections());
	}
}
