import com.google.inject.Inject;

public class FooController {

    private FooManager fooManager;

    //----- Controller Actions -------------------------------------------------
    public Foo create(String name) {
        Foo foo = new Foo();
        foo.setName(name);
        fooManager.save(foo);
        return foo;
    }

    public Foo retrieve(Long id) {
        return fooManager.get(id);
    }

    //----- Getters/Setters ----------------------------------------------------
    public FooManager getFooManager() {
        return fooManager;
    }

    @Inject
    public void setFooManager(FooManager fooManager) {
        this.fooManager = fooManager;
    }

}
