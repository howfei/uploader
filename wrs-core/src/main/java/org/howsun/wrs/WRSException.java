/**
 * 
 */
package org.howsun.wrs;

/**
 * 说明:
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 下午3:10:27
 */
public class WRSException extends RuntimeException {

	private static final long serialVersionUID = 2691145304486279863L;

	

	public WRSException(String message) {
		super(message);
	}
	
	public WRSException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		return buildMessage(super.getMessage(), getCause());
	}

	/**
	 * 获取异常根原因
	 * @return 如果没有向上追溯到原因，则返回自己。
	 */
	public Throwable getRootCause(){
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause == null ? this : rootCause;
	}
	
	/**
	 * 判断是否异常包含一个给定类型的异常
	 * @param exceptionClass
	 * @return
	 */
	public boolean contains(Class<?> exceptionClass) {
		if (exceptionClass == null) {
			return false;
		}
		if (exceptionClass.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if (cause == this) {
			return false;
		}
		if (cause instanceof WRSException) {
			return ((WRSException) cause).contains(exceptionClass);
		}
		else {
			while (cause != null) {
				if (exceptionClass.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}

	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (message != null) {
				sb.append(message).append("; ");
			}
			sb.append("base exception is ").append(cause);
			return sb.toString();
		}
		else {
			return message;
		}
	}

	/**
	 * 判断是否为强制查检类型异常
	 * @param ex
	 * @return
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}
}
