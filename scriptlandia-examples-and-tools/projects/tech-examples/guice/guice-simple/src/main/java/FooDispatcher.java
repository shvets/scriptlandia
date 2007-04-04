import com.google.inject.Guice;
import com.google.inject.Injector;

public class FooDispatcher {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new FooModule());
//        FooController controller = new FooController();

        Injector injector = Guice.createInjector();
        injector.injectMembers(controller);

        //Our action is now injected with a FooManager, so let's use it
        Foo foo = controller.create("Bar");
        Foo bar = controller.retrieve(foo.getId());
        System.out.println("bar.name => "+bar.getName());

    }

}

