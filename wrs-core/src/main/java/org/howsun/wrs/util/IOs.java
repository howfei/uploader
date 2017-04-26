/**
 * 
 */
package org.howsun.wrs.util;


/**
 * @author howsun
 *
 */
public abstract class IOs {

	
	/**
	 * 
	 * @param closeable
	 */
	public static void close(AutoCloseable... closeable){
		for (AutoCloseable c : closeable) {
			if(c != null){
				try {c.close();} catch (Exception e) {e.printStackTrace();}
			}
		}
	}
	
	
	/**
	 * @param args{
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
