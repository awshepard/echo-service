package com.upside.echo.dao;
import com.upside.echo.model.Greeting;
import static com.upside.echo.model.Greeting.GREETING_ID;
import static com.upside.echo.model.Greeting.UUID_ID;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

/**
 * <p>Binder for the Greeting POJO</p>
 */
@BindingAnnotation(GreetingBinder.ExecutionBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface GreetingBinder {

    class ExecutionBinderFactory implements BinderFactory {
        @Override
        public Binder build(Annotation annotation) {
            return new Binder<GreetingBinder, Greeting>() {
                @Override
                public void bind(SQLStatement q, GreetingBinder bind, Greeting greeting) {

                    q.bind(UUID_ID, greeting.getUuid());
                    q.bind(GREETING_ID, greeting.getGreeting());
                }
            };
        }
    }
}
