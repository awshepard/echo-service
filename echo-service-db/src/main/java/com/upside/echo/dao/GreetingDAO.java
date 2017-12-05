package com.upside.echo.dao;

import com.upside.echo.model.Greeting;
import com.upside.model.jdbi.argument.UUIDArgumentFactory;
import java.util.Collection;
import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterArgumentFactory;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * <p>DAO for our Greeting POJO</p>
 */
@RegisterMapper(GreetingMapper.class)
@RegisterArgumentFactory(UUIDArgumentFactory.class)
public interface GreetingDAO {

    @SqlQuery("select * from greeting")
    Collection<Greeting> findGreetings();

    @SqlQuery("select * from greeting where uuid = :uuid")
    Greeting findByUuid(@Bind("uuid") UUID uuid);

    @SqlUpdate("insert into greeting (uuid, "
        + "                           greeting) "
        + "                  values  (:uuid,"
        + "                           :greeting) "
        + "on duplicate key update greeting = :greeting")
    void upsert(@GreetingBinder Greeting greeting);

}
