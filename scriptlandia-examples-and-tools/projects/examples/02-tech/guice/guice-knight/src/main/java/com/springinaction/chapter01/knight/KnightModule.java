package com.springinaction.chapter01.knight;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;

public class KnightModule extends AbstractModule {
  public void configure() {
    
    bindConstant().annotatedWith(Name.class)
        .to("Bedivere");
    
    bind(Knight.class).to(KnightOfTheRoundTable.class)
        .in(Scopes.SINGLETON);

    bind(Quest.class).to(HolyGrailQuest.class)
        .in(Scopes.SINGLETON);
    
    bindInterceptor(
      any(), 
      annotatedWith(MinstrelIntercepted.class), 
      new MinstrelInterceptor()
    );
  }
  
  public static void main(String[] args) throws Exception {
    Injector injector = Guice.createInjector(new KnightModule() );
    Knight knight = injector.getInstance(Knight.class);
    knight.embarkOnQuest();
  }
}
