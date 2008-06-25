/*
 * JMockit Expectations
 * Copyright (c) 2007 Rogério Liesenfeld
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
import orderMngr.service.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the OrderRepository class, which depends on the {@link Database} class.
 * The tests use expectations to simulate the interaction between OrderRepository and Database.
 *
 * @author Rogério Liesenfeld
 */
public final class OrderRepositoryTestUsingExpectations extends Expectations
{
   @MockField
   final Database db = null; // only contain static methods, so no instance is needed

   @MockField
   ResultSet result;

   @MockField
   ResultSet result2;

   @MockField(methods = "loadOrderItems")
   OrderRepository repository;

   // Helper fields:
   private Order order;
   private OrderItem orderItem;

   @After
   public void tearDown()
   {
      Expectations.restoreFieldTypeDefinitions();
   }

   @Test
   public void createOrder()
   {
      order = new Order(561, "customer");
      orderItem = new OrderItem(order, "Prod", "Some product", 3, new BigDecimal("5.20"));
      order.getItems().add(orderItem);

      // Expectations:
      Database.executeInsertUpdateOrDelete(
         withPrefix("insert into order "),
         withEqual(new Object[] {order.getNumber(), order.getCustomerId()}));
      Database.executeInsertUpdateOrDelete(
         withPrefix("insert into order_item "),
         withEqual(new Object[] {
            order.getNumber(), orderItem.getProductId(), orderItem.getProductDescription(),
            orderItem.getQuantity(), orderItem.getUnitPrice()}));
      endRecording();

      new OrderRepository().create(order);

      Expectations.assertSatisfied();
   }

   @Test
   public void updateOrder()
   {
      order = new Order(1, "test");

      // Expectations:
      Database.executeInsertUpdateOrDelete(
         withPrefix("update order "),
         withEqual(new Object[] {order.getCustomerId(), order.getNumber()}));
      endRecording();

      new OrderRepository().update(order);

      endReplay();
   }

   @Test
   public void removeOrder()
   {
      order = new Order(35, "remove");

      // Expectations:
      Database.executeInsertUpdateOrDelete(
         withPrefix("delete from order "), withEqual(new Object[] {order.getNumber()}));
      endRecording();

      new OrderRepository().remove(order);

      endReplay();
   }

   @Test
   public void findOrderByNumber() throws Exception
   {
      // Undo the mocks created for all tests in this class due to it extending Expectations,
      // because this test needs to call the real "OrderRepository#loadOrderItems(Order)".
      Expectations.restoreFieldTypeDefinitions();

      // Set up state.
      order = new Order(1, "test");
      orderItem = new OrderItem(order, "343443", "Some product", 3, new BigDecimal(5));
      order.getItems().add(orderItem);

      // Set expectations:
      Expectations expectations = new Expectations()
      {
         @MockField final Database db = null;
         @MockField ResultSet result;
         @MockField ResultSet result2;

         {
            Database.executeQuery(
               withEqual("select customer_id from order where number=?"),
               withEqual(new Object[]{order.getNumber()}));
            returns(result);

            result.next();
            returns(true);
            result.getString(1);
            returns(order.getCustomerId());

            Database.executeQuery(
               withMatch("select .+ from order_item where .+"),
               withEqual(new Object[]{order.getNumber()}));
            returns(result2);
            result2.next();
            returns(true);
            result2.getString(1);
            result2.getString(2);
            result2.getInt(3);
            result2.getBigDecimal(4);
            result2.next();
            returns(false);
            Database.closeStatement(result2);

            Database.closeStatement(result);
         }
      }.endRecording();

      // Exercise code under test:
      Order found = new OrderRepository().findByNumber(order.getNumber());

      // Verify results:
      assertEquals(order, found);
      expectations.endReplay();
   }

   @Test
   public void findOrderByCustomer() throws Exception
   {
      String customerId = "Cust";
      order = new Order(890, customerId);

      // Expectations:
      Database.executeQuery(
         withMatch("select.+from\\s+order.*where.+customer_id\\s*=\\s*\\?"),
         withEqual(new Object[] {customerId}));
      returns(result);

      result.next(); returns(true);
      result.getInt(1); returns(order.getNumber());
      invoke(repository, "loadOrderItems", order);
      result.next(); returns(false);

      Database.closeStatement(withSameInstance(result));
      endRecording();

      List<Order> found = repository.findByCustomer(customerId);

      assertTrue("Order not found by customer id", found.contains(order));
      endReplay();
   }
}