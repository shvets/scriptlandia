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
package orderMngr.web.submitOrder;

import java.math.*;

import mockit.*;
import orderMngr.domain.order.*;
import org.junit.*;
import static org.junit.Assert.*;

public final class OrderEntryPageTestUsingExpectations
{
   @After
   public void tearDown()
   {
      Expectations.restoreFieldTypeDefinitions();
   }

   @Test
   public void submitOrder() throws Exception
   {
      final OrderEntryPage page = new OrderEntryPage();
      page.load();

      final String customerId = "889000";
      page.setCustomerId(customerId);
      page.getOrderItems().add(new OrderItem("3934", "test item", 2, new BigDecimal(20)));
      assertEquals(0, page.getOrderNo());

      final int orderNo = 464;

      new Expectations()
      {
         final OrderFactory orderFactory = null;

         {
            new OrderFactory().createOrder(customerId, page.getOrderItems());
            returns(new Order(orderNo, customerId));
         }
      }.endRecording();

      page.submitOrder();

      Expectations.assertSatisfied();
      assertEquals(orderNo, page.getOrderNo());
   }
}