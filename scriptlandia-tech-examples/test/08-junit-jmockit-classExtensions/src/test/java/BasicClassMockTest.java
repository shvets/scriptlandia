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
import org.junit.*;

public class BasicClassMockTest
{
   public static class Document
   {
      private final Printer printer;
      private String content;

      public Document(Printer printer)
      {
         this.printer = printer;
      }

      public String getContent()
      {
         return content;
      }

      public void setContent(String content)
      {
         this.content = content;
      }

      public void print()
      {
         printer.print(content);
      }
   }

   public abstract static class Printer
   {
      public abstract void print(String toPrint);
   }

   private Expectations expectations;
   private Printer printer;
   private Document document;

   @Before
   public void setUp()
   {
      expectations = new Expectations()
      {
         Printer mockPrinter;

         { printer = mockPrinter; }
      };

      document = new Document(printer);
   }

   @After
   public void tearDown()
   {
      printer = null;
      document = null;
   }

   @Test
   public void testPrintContent()
   {
      printer.print("Hello world");
      expectations.endRecording();

      document.setContent("Hello world");
      document.print();

      // expectations.endReplay() will be automatically called, as long as JMockit is run with
      // JUnit 4 integration: "-javaagent:jmockit.jar=junit4"
   }

   @Test
   public void testPrintEmptyContent()
   {
      printer.print("");
      expectations.endRecording();

      document.setContent("");
      document.print();

      // expectations.endReplay() will be automatically called, as long as JMockit is run with
      // JUnit 4 integration: "-javaagent:jmockit.jar=junit4"
   }
}