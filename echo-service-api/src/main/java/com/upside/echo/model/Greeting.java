package com.upside.echo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import java.util.UUID;

/**
 * <p>POJO for a greeting</p>
 */
@AutoValue
public abstract class Greeting {

    public static final String UUID_ID = "uuid";
    public static final String GREETING_ID = "greeting";

    @JsonProperty(UUID_ID)
    public abstract UUID getUuid();

    @JsonProperty(GREETING_ID)
    public abstract String getGreeting();

    @JsonCreator
    public static Greeting create(@JsonProperty(UUID_ID) UUID uuid,
                                  @JsonProperty(GREETING_ID) String greeting) {
        return builder()
            .uuid(uuid)
            .greeting(greeting)
            .build();
    }

    public static Builder builder() {
        return new AutoValue_Greeting.Builder();
    }

    public abstract Builder toBuilder();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder uuid(UUID uuid);
        public abstract Builder greeting(String greeting);

        public abstract Greeting build();
    }
}
