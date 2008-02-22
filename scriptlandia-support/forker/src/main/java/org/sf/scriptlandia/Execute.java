/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.sf.scriptlandia;

import java.io.*;
import java.util.Vector;

import org.sf.scriptlandia.commandlauncher.*;
import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
import org.apache.tools.ant.taskdefs.ProcessDestroyer;
/**
 * Runs an external program.
 *
 * @since Ant 1.2
 *
 */
public class Execute {

    /** Invalid exit code.
     * set to {@link Integer#MAX_VALUE}
     */
    public static final int INVALID = Integer.MAX_VALUE;
public static final String LINE_SEP = System.getProperty("line.separator");

    private String[] cmdl = null;
    private String[] env = null;
    private int exitValue = INVALID;
    private ExecuteStreamHandler streamHandler;
    private ExecuteWatchdog watchdog;
    private File workingDirectory = null;

    /** Controls whether the VM is used to launch commands, where possible. */
    private boolean useVMLauncher = true;

    private static String antWorkingDirectory = System.getProperty("user.dir");
    private static CommandLauncher vmLauncher = null;
    private static CommandLauncher shellLauncher = null;
    private static Vector procEnvironment = null;

    /** Used to destroy processes when the VM exits. */
    private static ProcessDestroyer processDestroyer = new ProcessDestroyer();

    /*
     * Builds a command launcher for the OS and JVM we are running under.
     */
    static {
        // Try using a JDK 1.3 launcher
        try {
            if (!OsUtils.isFamily("os/2")) {
                vmLauncher = new Java13CommandLauncher();
            }
        } catch (NoSuchMethodException exc) {
            // Ignore and keep trying
        }
        if (OsUtils.isFamily("mac") && !OsUtils.isFamily("unix")) {
            // Mac
            shellLauncher = new MacCommandLauncher(new CommandLauncher(), antWorkingDirectory);
        } else if (OsUtils.isFamily("os/2")) {
            // OS/2
            shellLauncher = new OS2CommandLauncher(new CommandLauncher());
        } else if (OsUtils.isFamily("windows")) {
           // environmentCaseInSensitive = true;
            CommandLauncher baseLauncher = new CommandLauncher();

            if (!OsUtils.isFamily("win9x")) {
                // Windows XP/2000/NT
                shellLauncher = new WinNTCommandLauncher(baseLauncher);
            } else {
                // Windows 98/95 - need to use an auxiliary script
                shellLauncher
                    = new ScriptCommandLauncher("bin/antRun.bat", baseLauncher);
            }
        } else if (OsUtils.isFamily("netware")) {

            CommandLauncher baseLauncher = new CommandLauncher();

            shellLauncher
                = new PerlScriptCommandLauncher("bin/antRun.pl", baseLauncher);
        } else if (OsUtils.isFamily("openvms")) {
            // OpenVMS
            try {
                shellLauncher = new VmsCommandLauncher();
            } catch (NoSuchMethodException exc) {
            // Ignore and keep trying
            }
        } else {
            // Generic
            shellLauncher = new ScriptCommandLauncher("bin/antRun",
                new CommandLauncher());
        }
    }

    /**
     * Creates a new execute object.
     *
     * @param streamHandler the stream handler used to handle the input and
     *        output streams of the subprocess.
     */
    public Execute(ExecuteStreamHandler streamHandler) {
        this(streamHandler, null);
    }

    /**
     * Creates a new execute object.
     *
     * @param streamHandler the stream handler used to handle the input and
     *        output streams of the subprocess.
     * @param watchdog a watchdog for the subprocess or <code>null</code> to
     *        to disable a timeout for the subprocess.
     */
    public Execute(ExecuteStreamHandler streamHandler,
                   ExecuteWatchdog watchdog) {
        setStreamHandler(streamHandler);
        this.watchdog = watchdog;
        //By default, use the shell launcher for VMS
        //
        if (OsUtils.isFamily("openvms")) {
            useVMLauncher = false;
        }
    }

    /**
     * Set the stream handler to use.
     * @param streamHandler ExecuteStreamHandler.
     * @since Ant 1.6
     */
    public void setStreamHandler(ExecuteStreamHandler streamHandler) {
        this.streamHandler = streamHandler;
    }

    /**
     * Returns the commandline used to create a subprocess.
     *
     * @return the commandline used to create a subprocess.
     */
    public String[] getCommandline() {
        return cmdl;
    }

    /**
     * Sets the commandline of the subprocess to launch.
     *
     * @param commandline the commandline of the subprocess to launch.
     */
    public void setCommandline(String[] commandline) {
        cmdl = commandline;
    }


    /**
     * Returns the environment used to create a subprocess.
     *
     * @return the environment used to create a subprocess.
     */
    public String[] getEnvironment() {
      return env;

    }


    /**
     * Sets the working directory of the process to execute.
     *
     * <p>This is emulated using the antRun scripts unless the OS is
     * Windows NT in which case a cmd.exe is spawned,
     * or MRJ and setting user.dir works, or JDK 1.3 and there is
     * official support in java.lang.Runtime.
     *
     * @param wd the working directory of the process.
     */
    public void setWorkingDirectory(File wd) {
        workingDirectory =
            (wd == null || wd.getAbsolutePath().equals(antWorkingDirectory))
            ? null : wd;
    }


    /**
     * Creates a process that runs a command.
     *
     * @param command the command to run.
     * @param env the environment for the command.
     * @param dir the working directory for the command.
     * @param useVM use the built-in exec command for JDK 1.3 if available.
     * @return the process started.
     * @throws IOException forwarded from the particular launcher used.
     *
     * @since Ant 1.5
     */
    public static Process launch(String[] command,
                                 String[] env, File dir, boolean useVM)
        throws IOException {
        if (dir != null && !dir.exists()) {
            throw new ForkerException(dir + " doesn't exist.");
        }
        CommandLauncher launcher
            = ((useVM && vmLauncher != null) ? vmLauncher : shellLauncher);
        return launcher.exec(command, env, dir);
    }

    /**
     * Runs a process defined by the command line and returns its exit status.
     *
     * @return the exit status of the subprocess or <code>INVALID</code>.
     * @exception java.io.IOException The exception is thrown, if launching
     *            of the subprocess failed.
     */
    public int execute() throws IOException {
        if (workingDirectory != null && !workingDirectory.exists()) {
            throw new ForkerException(workingDirectory + " doesn't exist.");
        }
        final Process process = launch(getCommandline(),
                                       getEnvironment(), workingDirectory,
                                       useVMLauncher);
        try {
            streamHandler.setProcessInputStream(process.getOutputStream());
            streamHandler.setProcessOutputStream(process.getInputStream());
            streamHandler.setProcessErrorStream(process.getErrorStream());
        } catch (IOException e) {
            process.destroy();
            throw e;
        }
        streamHandler.start();

        try {
            // add the process to the list of those to destroy if the VM exits
            //
            processDestroyer.add(process);

          if (watchdog != null) {
                watchdog.start(process);
            }
            waitFor(process);

            if (watchdog != null) {
                watchdog.stop();
            }
          
            streamHandler.stop();
            closeStreams(process);

            if (watchdog != null) {
                watchdog.checkException();
            }
            return getExitValue();
        } catch (ThreadDeath t) {
            // #31928: forcibly kill it before continuing.
            process.destroy();
            throw t;
        } finally {
            // remove the process to the list of those to destroy if
            // the VM exits
            //
            processDestroyer.remove(process);
        }
    }


    /**
     * Wait for a given process.
     *
     * @param process the process one wants to wait for.
     */
    protected void waitFor(Process process) {
        try {
            process.waitFor();
            setExitValue(process.exitValue());
        } catch (InterruptedException e) {
            process.destroy();
        }
    }

    /**
     * Set the exit value.
     *
     * @param value exit value of the process.
     */
    protected void setExitValue(int value) {
        exitValue = value;
    }

    /**
     * Query the exit value of the process.
     * @return the exit value or Execute.INVALID if no exit value has
     * been received.
     */
    public int getExitValue() {
        return exitValue;
    }

    /**
     * Close the streams belonging to the given Process.
     * @param process   the <code>Process</code>.
     */
    public static void closeStreams(Process process) {
        FileUtils.close(process.getInputStream());
        FileUtils.close(process.getOutputStream());
        FileUtils.close(process.getErrorStream());
    }

}
