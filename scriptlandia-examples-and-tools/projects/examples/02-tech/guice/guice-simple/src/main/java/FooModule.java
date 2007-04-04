import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class FooModule extends AbstractModule {

    protected void configure() {
        bind(FooManager.class)
            .to(FooManagerImpl.class)
            .in(Scopes.SINGLETON);
    }

}
