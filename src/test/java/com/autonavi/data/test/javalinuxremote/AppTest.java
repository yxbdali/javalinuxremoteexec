/**
 * 
 */
package com.autonavi.data.test.javalinuxremote;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jcraft.jsch.JSchException;

/**
 * @author xiangbin.yang
 *
 */
public class AppTest {
	static org.apache.logging.log4j.Logger log = LogManager.getLogger(AppTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		try (Remoter remoter = new Remoter("10.17.135.17", "root", "test&gjpt1!")) {
			String shellResult = remoter.exec("ls /home");
			String[] resultArray = shellResult.split("\\s");
			log.info(String.format("Count: %d", resultArray.length));
			for (String result : resultArray) {
				log.info(result.trim());
			}
		}
	}

}
