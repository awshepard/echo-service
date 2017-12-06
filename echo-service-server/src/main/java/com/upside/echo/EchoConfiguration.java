package com.upside.echo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.washingtonpost.dw.auth.AllowedPeerConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>Dropwizard Configuration for our EchoService</p>
 */
public class EchoConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("allowedPeers")
    private AllowedPeerConfiguration allowedPeers = new AllowedPeerConfiguration();

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    public AllowedPeerConfiguration getAllowedPeers() {
        return allowedPeers;
    }

    public void setAllowedPeers(AllowedPeerConfiguration allowedPeers) {
        this.allowedPeers = allowedPeers;
    }

}
