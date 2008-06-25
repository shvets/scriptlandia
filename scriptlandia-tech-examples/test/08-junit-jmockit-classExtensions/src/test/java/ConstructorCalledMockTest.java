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
//package jmockitVersionOfEasymockClassExtensionSample;

import java.math.*;

import mockit.*;
import static org.junit.Assert.*;
import org.junit.*;

public class ConstructorCalledMockTest
{
   /**
    * Class to test and partially mock.
    */
   public abstract static class TaxCalculator
   {
      private final BigDecimal[] values;

      protected TaxCalculator(BigDecimal... values)
      {
         this.values = values;
      }

      protected abstract BigDecimal rate();

      public BigDecimal tax()
      {
         BigDecimal result = BigDecimal.ZERO;

         for (BigDecimal d : values) {
            result = result.add(d);
         }

         return result.multiply(rate());
      }
   }

   private TaxCalculator tc = new MockTaxCalculator(new BigDecimal(5), new BigDecimal(15));

   // TODO: change ExpectationsModifier so that it supports a generated subclass for an abstract
   // class which lacks a default constructor; then this class won't be required
   static class MockTaxCalculator extends TaxCalculator
   {
      MockTaxCalculator(BigDecimal... values) { super(values); }

      @Override
      protected BigDecimal rate() { return null; }
   }

   @Test
   public void testTax()
   {
      new Expectations(true)
      {
         // Asks to mock only the rate() method, which isn't really necessary because only methods
         // and constructors declared in the field type (MockTaxCalculator here) are mocked, NOT
         // inherited methods such as TaxCalculator#tax().
         @MockField(methods = "rate()")
         MockTaxCalculator mockTc;

         {
            mockTc.rate(); returns(new BigDecimal("0.20"));
         }
      };

      assertEquals(new BigDecimal("4.00"), tc.tax());
   }

   @Test
   public void testTax_ZeroRate()
   {
      new Expectations(true)
      {
         MockTaxCalculator mockTc;

         {
            mockTc.rate(); returns(BigDecimal.ZERO);
         }
      };

      assertEquals(BigDecimal.ZERO, tc.tax());
   }
}