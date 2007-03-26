package org.codehaus.plexus.compiler.scalac;

/*                                                                                                                                                                                                              
*  ============================================================================
*  The Apache Software License, Version 1.1
*  ============================================================================
*  Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.
*  Redistribution and use in source and binary forms, with or without modifica-
*  tion, are permitted provided that the following conditions are met:
*  1. Redistributions of  source code must  retain the above copyright  notice,
*  this list of conditions and the following disclaimer.
*  2. Redistributions in binary form must reproduce the above copyright notice,
*  this list of conditions and the following disclaimer in the documentation
*  and/or other materials provided with the distribution.
*  3. The end-user documentation included with the redistribution, if any, must
*  include  the following  acknowledgment:  "This product includes  software
*  developed  by the  Apache Software Foundation  (http://www.apache.org/)."
*  Alternately, this  acknowledgment may  appear in the software itself,  if
*  and wherever such third-party acknowledgments normally appear.
*  4. The names "Apache Cocoon" and  "Apache Software Foundation" must  not  be
*  used to  endorse or promote  products derived from  this software without
*  prior written permission. For written permission, please contact
*  apache@apache.org.
*  5. Products  derived from this software may not  be called "Apache", nor may
*  "Apache" appear  in their name,  without prior written permission  of the
*  Apache Software Foundation.
*  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
*  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
*  FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
*  APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
*  INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
*  DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
*  OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
*  ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
*  (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
*  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*  This software  consists of voluntary contributions made  by many individuals
*  on  behalf of the Apache Software  Foundation and was  originally created by
*  Stefano Mazzocchi  <stefano@apache.org>. For more  information on the Apache                                                                                                                                
*  Software Foundation, please see <http://www.apache.org/>.
*/

import org.codehaus.plexus.compiler.*;
import scala.Function1;
import scala.runtime.BoxedAnyArray;
import scala.runtime.BoxedArray;
import scala.tools.nsc.CompilerCommand;
import scala.tools.nsc.*;
import scala.tools.nsc.reporters.Reporter;
import scala.tools.nsc.util.Position;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author lars
 * @created 29. August 2006
 * @plexus.component role="org.codehaus.plexus.compiler.Compiler"
 * role-hint="scalac"
 */
public class ScalacCompiler extends AbstractCompiler {
  private final static String pathSeperator = System.getProperty("path.separator");

  private final static String fileSeperator = System.getProperty("file.separator");

  /**
   * Constructor for the ScalacCompiler
   */
  public ScalacCompiler() {
    super(CompilerOutputStyle.ONE_OUTPUT_FILE_PER_INPUT_FILE, ".scala", ".class", null);
  }

  // -----------------------------------------------------------------------
  // Compiler Implementation
  // -----------------------------------------------------------------------

  /**
   * Compiles a collection of sources
   *
   * @param config the compiler configuration
   * @return a list of compiler messages
   * @throws CompilerException compilation failed
   */
  public List compile(CompilerConfiguration config) throws CompilerException {
    // Ensures that the directory exist.
    getDestinationDir(config);

    ErrorHandlerFunction errors = new ErrorHandlerFunction();
    CompilerReporter reporter = new CompilerReporter();

    String[] args = createCommandLine(config);

    BoxedArray argsarray = new BoxedAnyArray(args.length);
    argsarray.copyFrom(args, 0, 0, args.length);

    //CompilerCommand command = new CompilerCommand(scala.List$.MODULE$.fromArray(argsarray), errors, false);
    //CompilerCommand command = new CompilerCommand(argsarray.toList(), errors, false);
    Settings settings = new Settings();
    CompilerCommand command = new CompilerCommand(argsarray.toList(), settings, errors, false);
//
    Global compiler = new Global(command.settings(), reporter);
    //Global.Run compilerRun = new Global.Run(compiler);
    Global.Run compilerRun = compiler.new Run();

    String[] sources = getSourceFiles(config);
    BoxedArray filearray = new BoxedAnyArray(sources.length);
    filearray.copyFrom(sources, 0, 0, sources.length);


    ArrayList messages = new ArrayList();
    try {
      //compilerRun.compile(scala.List$.MODULE$.fromArray(filearray));
      compilerRun.compile(filearray.toList());
    } catch (Exception e) {
      //e.printStackTrace();
      messages.add(new CompilerError(e.getLocalizedMessage(), true));
    }

    messages.addAll(errors.getMessages());
    messages.addAll(reporter.getMessages());

    return messages;
  }

  /**
   * Builds the command line for a specific compiler configuration
   *
   * @param config the compiler configuration
   * @return the command line to be executed
   * @throws CompilerException inconsistent configuration
   */
  public String[] createCommandLine(CompilerConfiguration config)
    throws CompilerException {
    String javaHome = System.getProperty("java.home");

    List args = new ArrayList();

    // args.add("scalac");

    args.add("-bootclasspath");

    String bootclasspath = convertListToPath(new File(javaHome + "/lib")
      .listFiles());
    bootclasspath = bootclasspath
      + convertListToPath(new File(javaHome + "/lib/ext").listFiles());

    args.add(bootclasspath);
    getLogger().debug("Bootclasspath: " + bootclasspath);

    args.add("-classpath");

    String classpathEntries = getPathString(config.getClasspathEntries());

    args.add(classpathEntries);
    getLogger().debug("Classpath: " + classpathEntries);

    if (config.getCustomCompilerArguments().size() > 0) {
      LinkedHashMap customArgs = config.getCustomCompilerArguments();
      Object[] entries = customArgs.entrySet().toArray();
      for (int i = 0; i < customArgs.size(); i++) {
        args.add(entries[i]);
      }
    }
    if (!config.isShowWarnings()) {
      args.add("-nowarn");
    }

    if (config.isVerbose()) {
      args.add("-verbose");
    }

    if (config.isDebug()) {
      args.add("-g");
    }

    args.add("-d");

    args.add(getDestinationDir(config).getAbsolutePath());

    Object[] sources = config.getSourceLocations().toArray();

    String sourceDir = "";
    for (int i = 0; i < sources.length; i++) {
      sourceDir = sourceDir + sources[i] + pathSeperator;
    }
    if (sources.length > 0) {
      args.add("-sourcepath");
      args.add(sourceDir.substring(0, sourceDir.length() - 1)
        + fileSeperator);
      getLogger().debug("Source path:" + sourceDir);
    }

    String[] _sources = getSourceFiles(config);

    for (int i = 0; i < _sources.length; i++) {
      args.add(_sources[i]);
    }

    return (String[]) args.toArray(new String[args.size()]);
  }

  // -----------------------------------------------------------------------
  // Private
  // -----------------------------------------------------------------------

  /**
   * Gets the destinationDir attribute of the ScalacCompiler object
   *
   * @param config Description of the Parameter
   * @return The destinationDir value
   */
  private File getDestinationDir(CompilerConfiguration config) {
    File destinationDir = new File(config.getOutputLocation());

    if (!destinationDir.exists()) {
      destinationDir.mkdirs();
    }

    return destinationDir;
  }

  /**
   * Description of the Method
   *
   * @param files Description of the Parameter
   * @return Description of the Return Value
   */
  private String convertListToPath(File[] files) {
    String filePath = "";
    for (int i = 0; i < files.length; i++) {
      if (files[i].getPath().endsWith(".jar")) {
        filePath = filePath + files[i].getPath() + pathSeperator;
      }
    }
    return filePath;
  }

  private class ErrorHandlerFunction implements Function1 {
    private List messages = new ArrayList();

    public Object apply(Object arg0) {
      messages.add(new CompilerError(arg0.toString(), true));
      return null;
    }

    public Function1 andThen(Function1 function1) {
      return null;
    }

    public Function1 compose(Function1 arg0) {
      return null;
    }

    public int $tag() throws RemoteException {
      return 0;
    }

    public List getMessages() {
      return messages;
    }
  }

  private class CompilerReporter extends Reporter {
    private List messages = new ArrayList();

    public void info0(Position pos, String message, Reporter.Severity severity, boolean force) {
      messages.add(new CompilerError(pos.source().path(), severity.code() > 1, pos.line(), pos.column(), pos.line(), pos.column(), message));
    }

    public Collection getMessages() {
      return messages;
    }

  }
}
