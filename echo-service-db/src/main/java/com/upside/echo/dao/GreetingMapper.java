package com.upside.echo.dao;

import com.upside.echo.model.Greeting;
import static com.upside.echo.model.Greeting.GREETING_ID;
import static com.upside.echo.model.Greeting.UUID_ID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * <p>ResultSetMapper for a Greeting</p>
 */
public class GreetingMapper implements ResultSetMapper<Greeting> {

    @Override
    public Greeting map(int i, ResultSet r, StatementContext sc) throws SQLException {
        return Greeting.builder()
            .uuid(UUID.fromString(r.getString(UUID_ID)))
            .greeting(r.getString(GREETING_ID))
            .build();
    }

}
