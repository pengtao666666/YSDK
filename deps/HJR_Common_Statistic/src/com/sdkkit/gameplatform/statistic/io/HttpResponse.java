/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sdkkit.gameplatform.statistic.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sdkkit.gameplatform.statistic.io.core.IOResponse;
import com.sdkkit.gameplatform.statistic.util.L;

/*******************************************************************************
 * HTTP 响应
 * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public class HttpResponse extends IOResponse {

	private static final String TAG = "HttpResponse";
	/** 网络连接管理�?*/
	private static NetConnection mNetConn;

	/** 错误代码 */
	public static final int UNKNOWN_CODE = 20001;
	public static final int UNKNOWN_EXCEPTION = 20002;
	public static final int TOO_MANY_REDIRECTS = 20003;
	public static final int IO_EXCEPTION = 20004;
	public static final int OPEN_EXCEPTION = 20005;
	public static final int USELESS_RESPONSE_FROM_OPERATOR = 20006;
	public static final int CACHE_ERROR = 20007;
	public static final int DECODE_ERROR = 20008;
	public static final int ENCODE_ERROR = 20009;
	public static final int COMPRESS_ERROR = 20010;
	public static final int DECOMPRESS_ERROR = 20011;
	public static final int CANCELED_BY_USER = 20012;
	public static final int SOCKET_TIME_OUT_EXCEPTION = 20013;

	/** 成功代码 */
	public static final int CACHE_HIT = 21001;

	/** 与服务器的网络链�?*/
	private Object mConnection;

	/** 网络链接中的输入输出数据�?*/
	private InputStream mInputStream;
	private OutputStream mOutputStream;

	/** 服务器响应的内容类型 */
	private String mContentType;
	/** 服务器响应的内容长度 */
	private int mContentLength;

	public HttpResponse() {
		mNetConn = NetConnection.getInstance();
	}

	public void setConnection(Object conn) {
		mConnection = conn;
	}

	public Object getConnection() {
		return mConnection;
	}

	public void setInputStream(InputStream input) {
		mInputStream = input;
	}

	public InputStream getInputStream() throws IOException {
		if (mInputStream == null && mConnection != null) {
			mInputStream = mNetConn.openInputStream(mConnection);
		} else if (mInputStream != null) {
			return mInputStream;
		}

		return mInputStream;
	}

	public void setOutputStream(OutputStream output) {
		mOutputStream = output;
	}

	public OutputStream getOutputStream() throws IOException {
		if (mOutputStream == null && mConnection != null) {
			mOutputStream = mNetConn.openOutputStream(mConnection);
		}

		return mOutputStream;
	}

	
	/**
	 * 	对头信息进行解析 得到 Content-Type Content-Length
	 */
	public void parseHeaderFields() throws IOException {
		if (mConnection == null)
			return;

		// 获取服务器响应的类型
		mContentType = mNetConn.getHeaderField(mConnection, "Content-Type");
		if (mContentType != null) {
			int index = mContentType.indexOf(';');
			if (index != -1) {
				mContentType = mContentType.substring(0, index);
			}
		} else {
			mContentType = "unknown";
		}

		// 获取服务器响应的长度
		String value = mNetConn.getHeaderField(mConnection, "Content-Length");
		if (value != null) {
			try {
				mContentLength = Integer.parseInt(value.trim());
			} catch (NumberFormatException e) {
				mContentLength = -1;
			}
		} else {
			mContentLength = -1;
		}
	}

	public String getContentType() {
		return mContentType;
	}

	public int getContentLength() {
		return mContentLength;
	}

	public void setContentLength(int length) {
		mContentLength = length;
	}

	public void close() {
		if (mInputStream != null) {
			try {
				mInputStream.close();
			} catch (Exception e) {
				if (L.WARN)
					L.w(TAG, "Failed to close inputstream.", e);
			}
		}
		mInputStream = null;

		if (mOutputStream != null) {
			try {
				mOutputStream.close();
			} catch (Exception e) {
				if (L.WARN)
					L.w(TAG, "Failed to close outputstream.", e);
			}
		}
		mOutputStream = null;

		if (mConnection != null) {
			try {
				mNetConn.close(mConnection);
			} catch (Exception e) {
				if (L.WARN)
					L.w(TAG, "Failed to close connection.", e);
			}
		}
		mConnection = null;
	}
}
