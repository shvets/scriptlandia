//import com.google.inject.ImplementedBy;

//@ImplementedBy(FooManagerImpl.class)
public interface FooManager {

  public Foo get(Long id);

  public void save(Foo foo);

}
