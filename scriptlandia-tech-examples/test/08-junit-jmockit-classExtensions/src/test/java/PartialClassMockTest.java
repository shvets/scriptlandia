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

import mockit.*;
import static org.junit.Assert.*;
import org.junit.*;

public class PartialClassMockTest
{
   public static class Rect
   {
      private int x;
      private int y;

      public int getX()
      {
         return x;
      }

      public void setX(int x)
      {
         this.x = x;
      }

      public int getY()
      {
         return y;
      }

      public void setY(int y)
      {
         this.y = y;
      }

      public int getArea()
      {
         return getX() * getY();
      }
   }

   @Test
   public void testGetArea()
   {
      new Expectations()
      {
         @MockField(methods = {"getX()", "getY()"})
         Rect rect;

         {
            invokeReturning(rect.getX(), 4);
            invokeReturning(rect.getY(), 5);
            endRecording();

            assertEquals(20, rect.getArea());
         }
      };
   }
}