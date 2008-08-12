/* Copyright 2008 Hycel Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simple;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.ApplicationLauncher;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public class SimpleApp {
  private static final Log logger = LogFactory.getLog(SimpleApp.class);

      /**
       * Main routine for the simple sample application.
       *
       * @param args
       */
      public static void main( String[] args ) {
          logger.info("SimpleApp starting up");

          // In order to launch the platform, we have to construct an
          // application context that defines the beans (services) and
          // wiring. This is pretty much straight Spring.
          //
          // Part of this configuration will indicate the initial page to be
          // displayed.

          String rootContextDirectoryClassPath = "/ctx";

          // The startup context defines elements that should be available
          // quickly such as a splash screen image.

          String startupContextPath = rootContextDirectoryClassPath + "/richclient-startup-context.xml";

          String richclientApplicationContextPath = rootContextDirectoryClassPath
                  + "/richclient-application-context.xml";

          // The ApplicationLauncher is responsible for loading the contexts,
          // presenting the splash screen, initializing the Application
          // singleton instance, creating the application window to display
          // the initial page.
          try {
              new ApplicationLauncher(startupContextPath, 
                      new String[] { richclientApplicationContextPath });
          } catch (RuntimeException e) {
              logger.error("RuntimeException during startup", e);
          }
      }

}
