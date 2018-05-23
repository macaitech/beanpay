package com.macaitech.beanpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.methods.GetMethod;

public class CustomGetMethod extends GetMethod {
	public CustomGetMethod(String uri) {
		super(uri);
	}

	public String getResponseBodyAsString() throws IOException {
		if ((getResponseBody() != null) || (getResponseStream() != null)) {
			if ((getResponseHeader("Content-Encoding") != null) && (getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip") > -1)) {
				InputStream is = getResponseBodyAsStream();
				GZIPInputStream gzin = new GZIPInputStream(is);

				InputStreamReader isr = new InputStreamReader(gzin, getResponseCharSet());
				BufferedReader br = new BufferedReader(isr);
				StringBuffer sb = new StringBuffer();
				String tempbf;
				while ((tempbf = br.readLine()) != null) {
					sb.append(tempbf);
					sb.append("\r\n");
				}
				isr.close();
				gzin.close();
				return sb.toString();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseBodyAsStream(), getResponseCharSet()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			return stringBuffer.toString();
		}
		return null;
	}
}
