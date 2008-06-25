//package jmockitVersionOfEasymockSample;

import mockit.*;
import static org.junit.Assert.*;
import org.junit.*;

// This test class assumes the use of JUnit 4 integration: "-javaagent:jmockit.jar=junit4".
public class ExampleTest extends Expectations
{
   private ClassUnderTest classUnderTest;
   Collaborator mock; // A mock field (since not private) which will be automatically set.

   @Before
   public void setup()
   {
      classUnderTest = new ClassUnderTest();
      classUnderTest.addListener(mock);
   }

   @Test
   public void removeNonExistingDocument()
   {
      // Expects no uses of the collaborator.
      endRecording();

      assertTrue(classUnderTest.removeDocument("Does not exist"));
   }

   @Test
   public void addDocument()
   {
      mock.documentAdded("New Document");
      endRecording();

      classUnderTest.addDocument("New Document", new byte[0]);
   }

   @Test
   public void addAndChangeDocument()
   {
      mock.documentAdded("Document");
      mock.documentChanged("Document");
      mock.documentChanged("Document");
      mock.documentChanged("Document");
      endRecording();

      classUnderTest.addDocument("Document", new byte[0]);
      classUnderTest.addDocument("Document", new byte[0]);
      classUnderTest.addDocument("Document", new byte[0]);
      classUnderTest.addDocument("Document", new byte[0]);
   }

   @Test
   public void voteForRemoval()
   {
      // Expect document addition.
      mock.documentAdded("Document");
      // Expect to be asked to vote, and vote for it.
      invokeReturning(mock.voteForRemoval("Document"), (byte) 42);
      // Expect document removal.
      mock.documentRemoved("Document");
      endRecording();

      classUnderTest.addDocument("Document", new byte[0]);
      assertTrue(classUnderTest.removeDocument("Document"));
   }

   @Test
   public void voteAgainstRemoval()
   {
      // Expect document addition.
      mock.documentAdded("Document");
      // Expect to be asked to vote, and vote against it.
      invokeReturning(mock.voteForRemoval("Document"), (byte) -42);
      // Document removal is *not* expected.
      endRecording();

      classUnderTest.addDocument("Document", new byte[0]);
      assertFalse(classUnderTest.removeDocument("Document"));
   }

   @Test
   public void voteForRemovals()
   {
      mock.documentAdded("Document 1");
      mock.documentAdded("Document 2");
      String[] documents = {"Document 1", "Document 2"};
      invokeReturning(mock.voteForRemovals(withEqual(documents)), (byte) 42);
      mock.documentRemoved("Document 1");
      mock.documentRemoved("Document 2");
      endRecording();

      classUnderTest.addDocument("Document 1", new byte[0]);
      classUnderTest.addDocument("Document 2", new byte[0]);
      assertTrue(classUnderTest.removeDocuments(new String[] {"Document 1", "Document 2"}));
   }

   @Test
   public void voteAgainstRemovals()
   {
      mock.documentAdded("Document 1");
      mock.documentAdded("Document 2");
      String[] documents = {"Document 1", "Document 2"};
      invokeReturning(mock.voteForRemovals(withEqual(documents)), (byte) -42);
      endRecording();

      classUnderTest.addDocument("Document 1", new byte[0]);
      classUnderTest.addDocument("Document 2", new byte[0]);
      assertFalse(classUnderTest.removeDocuments(new String[] {"Document 1", "Document 2"}));
   }
}