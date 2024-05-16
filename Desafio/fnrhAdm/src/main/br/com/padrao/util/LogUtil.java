package br.com.padrao.util;

import org.apache.log4j.Logger;
public class LogUtil {

		private Logger logger;
		private String ip;
		private String uuid;

		public void setLogger(Logger logger) {
			this.logger = logger;
			if (logger != null) {
				this.logger.setAdditivity(false);
			}
		}

		public Logger getLogger() {
			return logger;
		}

		public void novaInstanciaLog(String ip) {
			this.ip = ip;
		}

		public void montarLog(String uuid, String garcom, String mesa,
				String cartaoConsumo, String apto, String msg) {
			if (uuid == null) {
				if (this.uuid != null) {
					uuid = this.uuid;
				} else {
					uuid = "";
				}
			} else {
				this.uuid = uuid;
			}
			if (garcom == null) {
				garcom = "";
			}
			if (mesa == null) {
				mesa = "";
			}
			if (cartaoConsumo == null) {
				cartaoConsumo = "";
			}
			if (apto == null) {
				apto = "";
			}
			logger.warn(uuid + " - " + ip + " - " + garcom + " - " + mesa + " - "
					+ cartaoConsumo + " - " + apto + " - " + msg);
		}

		

	}


