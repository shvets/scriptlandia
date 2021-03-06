/*
 * JMockit: a Java class library for developer testing with "mock methods"
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
import java.util.ArrayList;
import static java.util.Arrays.*;
import java.util.*;

import mockit.*;
import org.junit.*;
import static org.junit.Assert.*;

public final class OrderFactoryTestUsingExpectations
{
   @After
   public void tearDown()
   {
      Expectations.restoreFieldTypeDefinitions();
   }

   @Test
   public void createOrder() throws Exception
   {
      final String customerId = "123";
      List<OrderItem> expectedItems = asList(
         new OrderItem("393439493", "Core Java 5 6ed", 2, new BigDecimal("45.00")),
         new OrderItem("04940458", "JUnit Recipes", 1, new BigDecimal("49.95")));
      final List<OrderItem> actualItems = new ArrayList<OrderItem>();

      new Expectations()
      {
         @MockField(methods = {"equals", "getNumber"}, inverse = true)
         final Order order = new Order(withAny(0), withEqual(customerId));
         {
            order.getItems(); returns(actualItems);
         }

         final OrderRepository orderRepository = new OrderRepository();
         {
            orderRepository.create(withEqual(order));
         }
      }.endRecording();

      Order order = new OrderFactory().createOrder(customerId, expectedItems);

      assertNotNull(order);
      assertEquals(expectedItems, actualItems);
      Expectations.assertSatisfied();
   }
}