package br.com.padrao.util;

/**
 * Utilitário para código de barras.
 * 
 * @author Leonardo
 * 
 */
// CHECKSTYLE:OFF
public class CodigoBarrasUtil {

	private static final String[] vCodPreta = { "00110", "10001", "01001",
			"11000", "00101", "10100", "01100", "00011", "10010", "01010" };  

	private static final String[] vCodBranca = { "22332", "32223", "23223",
			"33222", "22323", "32322", "23322", "22233", "32232", "23232" };

	/**
	 * Codifica o código de barras 2/5 interleaved para impressão.
	 * 
	 * @param szCodIn
	 * @return String
	 */
	public static String decodCodBar25(String szCodIn) {

		int iComp = szCodIn.length();

		StringBuilder szCodOut = new StringBuilder("0202");
		for (int iCont = -1; iCont <= iComp - 2; iCont++) {
			iCont++;
			int iAux1 = Integer.valueOf(szCodIn.substring(iCont, iCont + 1));
			int iAux2 = Integer
					.valueOf(szCodIn.substring(iCont + 1, iCont + 2));
			for (int iCont2 = 0; iCont2 <= 4; iCont2++) {
				szCodOut.append(vCodPreta[iAux1].charAt(iCont2));
				szCodOut.append(vCodBranca[iAux2].charAt(iCont2));
			}
		}
		szCodOut.append("120");
		return szCodOut.toString();
	}

}
// CHECKSTYLE:ON