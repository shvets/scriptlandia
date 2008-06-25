package orderMngr.service.domain;

import java.io.*;

import orderMngr.service.persistence.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DomainTest
{
   @Before
   public final void setUp()
   {
      Persistence.beginTransaction();
   }

   @After
   public final void tearDown()
   {
      Persistence.persistPendingChanges();
      Persistence.rollbackTransaction();
      Persistence.closeUnitOfWork();
   }

   protected final void assertPersisted(Serializable entityId, Object entity)
   {
      Persistence.unload(entity);
      Object loadedEntity = Persistence.load(entity.getClass(), entityId);
      assertEquals(entity, loadedEntity);
   }

   protected final void assertUpdated(Object entity)
   {
      Persistence.persistPendingChanges();
      assertPersisted(Persistence.entityId(entity), entity);
   }

   protected final void assertDeleted(Object entity)
   {
      assertFalse(
         "Entity " + entity + " not actually deleted from persistent store", 
         Persistence.exists(entity.getClass(), Persistence.entityId(entity)));
   }
}
