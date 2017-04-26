/**
 * 
 */
package org.howsun.wrs.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * 
 * 2017年2月16日 下午1:41:10
 */
public abstract class Ips {

	private static Set<String> IP_HEADERS = new HashSet<String>(Arrays.asList("x-forwarded-for", "x-real-ip", "proxy-client-ip", "wl-proxy-client-ip"));

	/**
	 * 
	 * @param request
	 * @return
	 * String
	 */
	public static String getIp2String(HttpServletRequest request) {
		
		//适用于内部调试
		String ip = request.getParameter("__ip__");
		
		if(!Strings.hasLength(ip)){
			ip = request.getRemoteAddr();
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equals(ip)) {//Nginx代理后IP为本机地址
			Enumeration<?> headerNames = request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName = String.valueOf(headerNames.nextElement());
				if(IP_HEADERS.contains(headerName.toLowerCase())){
					ip = request.getHeader(headerName);
					if(Strings.hasLengthByTrim(ip) && !"unknown".equalsIgnoreCase(ip)) {
						break;
					}
				}
			}
			if(ip != null && ip.indexOf(",") > 0){
				ip = ip.split(",")[0];
			}
		}
		
		return ip;
	}
	
	 /**
     * ip地址转换成整数
     * @param ip
     * @return
     */
	public static long ip2long(String ip){
		long result = 0;
		if(Strings.hasLength(ip)){
			String section[] = ip.split("\\.");
			if(section.length > 2){
				for (int i = 0; i < section.length; i++) {
					result += Long.parseLong(section[i]) << ((section.length - i - 1) * 8);
				}
			}
		}
		return result;
	}
	
	/**
     * 整数转换成IP地址
     * @param value
     * @return
     */
    public static String long2ip(long value){
		StringBuffer ip = new StringBuffer();
		ip.append(String.valueOf((value >>> 24)))             .append("."); //直接右移24位
		ip.append(String.valueOf((value & 0x00FFFFFF) >>> 16)).append("."); //将高8位置0，然后右移16位
		ip.append(String.valueOf((value & 0x0000FFFF) >>> 8)) .append("."); //将高16位置0，然后右移8位
		ip.append(String.valueOf((value & 0x000000FF)));                    //将高24位置0
		return ip.toString();
	}
}
