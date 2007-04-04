import java.util.Map;
import java.util.HashMap;

//import com.google.inject.Singleton;

//@Singleton
public class FooManagerImpl implements FooManager {

  private static Long fooSequence = 1l;
  private static final Map<Long, Foo> foos = new HashMap<Long, Foo>();

  public Foo get(Long id) {
    return foos.get(id);
  }

  public void save(Foo foo) {
    if(foo == null) return;

    if(foo.getId() == null) {
      foo.setId(fooSequence);
      fooSequence++;
    }

    foos.put(foo.getId(), foo);
  }
}