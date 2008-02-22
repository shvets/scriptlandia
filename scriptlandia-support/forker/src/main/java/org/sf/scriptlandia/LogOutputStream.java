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

import org.apache.tools.ant.util.LineOrientedOutputStream;

import java.io.IOException;

/**
 * Logs each line written to this stream to the log system of ant.
 *
 * Tries to be smart about line separators.<br>
 *
 * @since Ant 1.2
 */
public class LogOutputStream extends LineOrientedOutputStream {

   // private ProjectComponent pc;
    private int level = /*Project.MSG_INFO*/2;


    /**
     * Creates a new instance of this class.
     *
     * @param level loglevel used to log data written to this stream.
     * @since Ant 1.6.3
     */
    public LogOutputStream(/*ProjectComponent pc, */int level) {
        //this.pc = pc;
        this.level = level;
    }

   public LogOutputStream() {
    }  
    /**
     * Converts the buffer to a string and sends it to <code>processLine</code>
     */
    protected void processBuffer() {
        try {
            super.processBuffer();
        } catch (IOException e) {
            // impossible since *our* processLine doesn't throw an IOException
            throw new RuntimeException("Impossible IOException caught: " + e);
        }
    }

    /**
     * Logs a line to the log system of ant.
     *
     * @param line the line to log.
     */
    protected void processLine(String line) {
        processLine(line, level);
    }

    /**
     * Logs a line to the log system of ant.
     *
     * @param line the line to log.
     * @param level the logging level to use.
     */
    protected void processLine(String line, int level) {
        //pc.log(line, level);
      System.out.println(line);
    }

    /**
     * Get the level.
     * @return the log level.
     */
    public int getMessageLevel() {
        return level;
    }

}