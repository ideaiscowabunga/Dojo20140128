package br.com.cowabunga;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class AbstractTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		executeSqlScript( getFileName(), false );
	}

	protected abstract String getFileName();

}
