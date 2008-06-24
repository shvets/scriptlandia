/*
 * Copyright (c) 2001-2007 OFFIS, Tammo Freese.
 * This program is made available under the terms of the MIT License.
 */
package org.easymock.samples;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleTest {

    private ClassUnderTest classUnderTest;

    private Collaborator mock;

    @Before
    public void setup() {
        mock = createMock(Collaborator.class);
        classUnderTest = new ClassUnderTest();
        classUnderTest.addListener(mock);
    }

    @Test
    public void removeNonExistingDocument() {
        replay(mock);
        classUnderTest.removeDocument("Does not exist");
    }

    @Test
    public void addDocument() {
        mock.documentAdded("New Document");
        replay(mock);
        classUnderTest.addDocument("New Document", new byte[0]);
        verify(mock);
    }

    @Test
    public void addAndChangeDocument() {
        mock.documentAdded("Document");
        mock.documentChanged("Document");
        expectLastCall().times(3);
        replay(mock);
        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);
        classUnderTest.addDocument("Document", new byte[0]);
        verify(mock);
    }

    @Test
    public void voteForRemoval() {
        // expect document addition
        mock.documentAdded("Document");
        // expect to be asked to vote, and vote for it
        expect(mock.voteForRemoval("Document")).andReturn((byte) 42);
        // expect document removal
        mock.documentRemoved("Document");

        replay(mock);
        classUnderTest.addDocument("Document", new byte[0]);
        assertTrue(classUnderTest.removeDocument("Document"));
        verify(mock);
    }

    @Test
    public void voteAgainstRemoval() {
        // expect document addition
        mock.documentAdded("Document");
        // expect to be asked to vote, and vote against it
        expect(mock.voteForRemoval("Document")).andReturn((byte) -42); // 
        // document removal is *not* expected

        replay(mock);
        classUnderTest.addDocument("Document", new byte[0]);
        assertFalse(classUnderTest.removeDocument("Document"));
        verify(mock);
    }

    @Test
    public void voteForRemovals() {
        mock.documentAdded("Document 1");
        mock.documentAdded("Document 2");
        String[] documents = new String[] { "Document 1", "Document 2" };
        expect(mock.voteForRemovals(aryEq(documents))).andReturn((byte) 42);
        mock.documentRemoved("Document 1");
        mock.documentRemoved("Document 2");
        replay(mock);
        classUnderTest.addDocument("Document 1", new byte[0]);
        classUnderTest.addDocument("Document 2", new byte[0]);
        assertTrue(classUnderTest.removeDocuments(new String[] { "Document 1",
                "Document 2" }));
        verify(mock);
    }

    @Test
    public void voteAgainstRemovals() {
        mock.documentAdded("Document 1");
        mock.documentAdded("Document 2");
        String[] documents = new String[] { "Document 1", "Document 2" };
        expect(mock.voteForRemovals(aryEq(documents))).andReturn((byte) -42);
        replay(mock);
        classUnderTest.addDocument("Document 1", new byte[0]);
        classUnderTest.addDocument("Document 2", new byte[0]);
        assertFalse(classUnderTest.removeDocuments(new String[] { "Document 1",
                "Document 2" }));
        verify(mock);
    }
}
