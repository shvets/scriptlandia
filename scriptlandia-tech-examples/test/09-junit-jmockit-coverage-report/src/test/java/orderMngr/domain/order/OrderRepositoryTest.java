/*
 * JMockit: a class library for developer testing with "mock methods"
 * Copyright (c) 2006, 2007 Rogério Liesenfeld
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
package orderMngr.domain.order;

import java.math.*;
import java.sql.*;
import java.util.*;

import mockit.*;
import static mockit.Mockit.*;
import orderMngr.service.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the OrderRepository class, which depends on the {@link Database} class.
 * The tests use mocks to simulate the interaction between OrderRepository and Database.
 *
 * @author Rogério Liesenfeld
 */
public final class OrderRepositoryTest
{
   private static PreparedStatement proxyStmt;
   private static ResultSet proxyRS;
   private Order order;
   private OrderItem orderItem;

   @After
   public void tearDown()
   {
      tearDownMocks();
   }

   @Test
   public void createOrder()
   {
      order = new Order(561, "customer");
      orderItem = new OrderItem(order, "Prod", "Some product", 3, new BigDecimal("5.20"));
      order.getItems().add(orderItem);

      setUpMocks(new MockDatabaseForCreate());

      new OrderRepository().create(order);

      assertExpectations();
   }

   @MockClass(realClass = Database.class)
   public class MockDatabaseForCreate
   {
      private boolean orderInserted;

      @Mock(invocations = 2)
      public void executeInsertUpdateOrDelete(String sql, Object... args)
      {
         if (!orderInserted) {
            assertTrue(sql.trim().toLowerCase().startsWith("insert into order "));
            assertEquals(order.getNumber(), args[0]);
            assertEquals(order.getCustomerId(), args[1]);
            orderInserted = true;
         }
         else {
            assertTrue(sql.trim().toLowerCase().startsWith("insert into order_item "));
            assertEquals(5, args.length);
            assertEquals(order.getNumber(), args[0]);
            assertEquals(orderItem.getProductId(), args[1]);
            assertEquals(orderItem.getProductDescription(), args[2]);
            assertEquals(orderItem.getQuantity(), args[3]);
            assertEquals(orderItem.getUnitPrice(), args[4]);
         }
      }
   }

   @Test
   public void updateOrder()
   {
      order = new Order(1, "test");

      setUpMocks(new MockDatabaseForUpdate());

      new OrderRepository().update(order);

      assertExpectations();
   }

   @MockClass(realClass = Database.class)
   public class MockDatabaseForUpdate
   {
      @Mock(invocations = 1)
      public void executeInsertUpdateOrDelete(String sql, Object... args)
      {
         assertTrue(sql.trim().toLowerCase().startsWith("update order "));
         String customerId = (String) args[0];
         assertEquals("test", customerId);
         Integer orderNo = (Integer) args[1];
         assertEquals(1, orderNo.intValue());
      }
   }

   @Test
   public void removeOrder()
   {
      order = new Order(35, "remove");

      setUpMocks(new MockDatabaseForRemove());

      new OrderRepository().remove(order);

      assertExpectations();
   }

   @MockClass(realClass = Database.class)
   public class MockDatabaseForRemove
   {
      @Mock(minInvocations = 1, maxInvocations = 1) // equivalent to "invocations = 1"
      public void executeInsertUpdateOrDelete(String sql, Object... args)
      {
         assertTrue(sql.trim().toLowerCase().startsWith("delete from order "));
         assertEquals(order.getNumber(), args[0]);
      }
   }

   /**
    * Demonstrates use of {@link Mockit#newEmptyProxy(ClassLoader, Class)}.
    */
   @Test
   public void findOrderByNumber()
   {
      order = new Order(1, "test");
      orderItem = new OrderItem(order, "343443", "Some product", 3, new BigDecimal(5));
      order.getItems().add(orderItem);

      setUpMocks(MockDatabase.class);
      redefineMethods(MockDatabase.connection().getClass(), MockConnection.class);
      ClassLoader classLoader = OrderRepository.class.getClassLoader();
      proxyStmt = newEmptyProxy(classLoader, PreparedStatement.class);
      redefineMethods(proxyStmt.getClass(), MockPreparedStatement.class);
      proxyRS = newEmptyProxy(classLoader, ResultSet.class);
      redefineMethods(proxyRS.getClass(), new MockResultSet());

      Order found = new OrderRepository().findByNumber(order.getNumber());

      assertEquals(order, found);
   }

   public static class MockConnection
   {
      public PreparedStatement prepareStatement(String sql)
      {
         assertNotNull(sql);
         return proxyStmt;
      }
   }

   public static class MockPreparedStatement
   {
      public int executeUpdate() { return 1; }

      public ResultSet executeQuery()
      {
         return proxyRS;
      }
   }

   public class MockResultSet
   {
      int callNo;

      MockResultSet() {}

      public boolean next()
      {
         callNo++;
         assertTrue("attempted to read more DB rows than expected", callNo <= 3);
         return callNo < 3;
      }

      public String getString(int columnIndex)
      {
         if (callNo == 1) return order.getCustomerId();
         return columnIndex == 1 ? orderItem.getProductId() : orderItem.getProductDescription();
      }

      public int getInt(int i)
      {
         assertEquals(3, i);
         return orderItem.getQuantity();
      }

      public BigDecimal getBigDecimal(int i)
      {
         assertEquals(4, i);
         return orderItem.getUnitPrice();
      }

      public Statement getStatement() { return proxyStmt; }
   }

   @Test
   public void findOrderByCustomer()
   {
      order = new Order(890, "Cust");

      setUpMocks(new MockDatabaseForFindByCustomer(), new MockOrderRepository());

      List<Order> found = new OrderRepository().findByCustomer(order.getCustomerId());

      assertTrue("Order not found by customer id", found.contains(order));
      assertExpectations();
   }

   // Allows ignoring the call to loadOrderItems, a private method not meant to be tested by the
   // test using this mock.
   @MockClass(realClass = OrderRepository.class)
   public class MockOrderRepository
   {
      @Mock(invocations = 1)
      public void loadOrderItems(Order loadedOrder)
      {
         assertEquals(order, loadedOrder);
      }
   }

   @MockClass(realClass = Database.class)
   public class MockDatabaseForFindByCustomer
   {
      private ResultSet mockRS;

      @Mock(invocations = 1)
      public ResultSet executeQuery(String sql, Object... args)
      {
         assertTrue(
            "Invalid Order query: " + sql,
            sql.matches("select.+from\\s+order.*where.+customer_id\\s*=\\s*\\?"));
         assertEquals(1, args.length);
         assertEquals("Cust", args[0]);

         mockRS = setUpMock(new MockResultSetForFindByCustomer());
         return mockRS;
      }

      @Mock(invocations = 1)
      public void closeStatement(ResultSet result)
      {
         assertSame(mockRS, result);
      }

      @MockClass(realClass = ResultSet.class)
      public class MockResultSetForFindByCustomer
      {
         private int rowIndex;

         MockResultSetForFindByCustomer() {}

         @Mock(invocations = 2)
         public boolean next()
         {
            rowIndex++;
            return rowIndex == 1;
         }

         @Mock(invocations = 1)
         public int getInt(int i)
         {
            assertEquals(1, i);
            return order.getNumber();
         }
      }
   }
}