/**
 * 
 */
package com.autonavi.data.test.javalinuxremote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author xiangbin.yang
 *
 */
public class Remoter implements AutoCloseable {
	private Session session;
	private ChannelExec channelExec;
	private JSch jSch;

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @throws JSchException
	 */
	public Remoter(String host, int port, String user, String password) throws JSchException {
		// TODO Auto-generated constructor stub
		jSch = new JSch();
		session = jSch.getSession(user, host);
		if (port > 0) {
			session.setPort(port);
		}
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);
		session.connect();
	}

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @throws JSchException
	 */
	public Remoter(String host, String user, String password) throws JSchException {
		// TODO Auto-generated constructor stub
		this(host, 0, user, password);
	}

	/**
	 * Remote execute a command shell and return the shell execution output
	 * 
	 * @param command
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String exec(String command) throws JSchException, IOException, InterruptedException {
		String output = "Hello Xiangbin, no result!";

		channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand(command);

		try (ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
				PrintStream psStream = new PrintStream(byteArrayOutput)) {
			channelExec.setOutputStream(psStream);
			channelExec.connect();
			// wait for shell execute compelety
			while (channelExec.isClosed() == false) {
				Thread.sleep(500);
			}
			channelExec.disconnect();
			session.disconnect();
			output = new String(byteArrayOutput.toByteArray());
		}

		return output;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		if (channelExec != null && channelExec.isConnected() == true) {
			channelExec.disconnect();
		}

		if (session != null && session.isConnected() == true) {
			session.disconnect();
		}
	}
}
