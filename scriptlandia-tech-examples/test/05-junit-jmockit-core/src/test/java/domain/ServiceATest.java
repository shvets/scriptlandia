/*
 * JMockit Core
 * Copyright (c) 2006-2008 Rogério Liesenfeld
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package domain;

import java.math.*;
import java.util.*;

import infrastructure.*;
import mockit.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceATest 
{
   private boolean serviceMethodCalled;

   public static class MockDatabase
   {
      static int findMethodCallCount;
      static int saveMethodCallCount;

      public static void save(Object o)
      {
         assertNotNull(o);
         saveMethodCallCount++;
      }

      public static List find(String ql, Object arg1)
      {
         assertNotNull(ql);
         assertNotNull(arg1);
         findMethodCallCount++;
         return Collections.EMPTY_LIST;
      }
   }

   @Before
   public void setUp() throws Exception
   {
      //super.setUp();
      MockDatabase.findMethodCallCount = 0;
      MockDatabase.saveMethodCallCount = 0;
      Mockit.redefineMethods(Database.class, MockDatabase.class);
   }

   @Test
   public void testDoBusinessOperationXyz() throws Exception
   {
      final BigDecimal total = new BigDecimal("125.40");

      Mockit.redefineMethods(ServiceB.class, new Object() {
         public BigDecimal computeTotal(List items)
         {
            assertNotNull(items);
            serviceMethodCalled = true;
            return total;
         }
      });

      EntityX data = new EntityX(5, "abc", "5453-1");
      new ServiceA().doBusinessOperationXyz(data);

      assertEquals(total, data.getTotal());
      assertTrue(serviceMethodCalled);
      assertEquals(1, MockDatabase.findMethodCallCount);
      assertEquals(1, MockDatabase.saveMethodCallCount);
   }

   @Test
   public void testDoBusinessOperationXyzWithInvalidItemStatus()
   {
      Mockit.redefineMethods(ServiceB.class, new Object() {
         public BigDecimal computeTotal(List items) throws InvalidItemStatus
         {
            throw new InvalidItemStatus();
         }
      });

      EntityX data = new EntityX(5, "abc", "5453-1");

      try {
         new ServiceA().doBusinessOperationXyz(data);
         fail(InvalidItemStatus.class + " was expected");
      }
      catch (InvalidItemStatus ignore) {
         // ok, test passed
         assertNull(data.getTotal());
         assertEquals(1, MockDatabase.findMethodCallCount);
         assertEquals(0, MockDatabase.saveMethodCallCount);
      }
   }
}