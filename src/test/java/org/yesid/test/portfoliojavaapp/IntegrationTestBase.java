package org.yesid.test.portfoliojavaapp;

import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/test_data.sql")
public class IntegrationTestBase {
}
