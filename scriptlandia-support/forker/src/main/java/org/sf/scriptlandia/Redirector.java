package org.sf.scriptlandia;

import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
import org.apache.tools.ant.taskdefs.PumpStreamHandler;
import org.apache.tools.ant.util.TeeOutputStream;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * The Redirector class manages the setup and connection of
 * input and output redirection for an Ant project component.
 *
 * @since Ant 1.6
 */
public class Redirector {
    /** Flag which indicates that output should be always sent to the log */
    private boolean alwaysLog = false;

    /** The stream for output data */
    private OutputStream outputStream = null;

    /** The stream for error output */
    private OutputStream errorStream = null;

    /** The stream for input */
    private InputStream inputStream = null;

    /** The thread group used for starting <code>StreamPumper</code> threads */
    private ThreadGroup threadGroup = new ThreadGroup("redirector");


    /**
     * Set a stream to use as input.
     *
     * @param inputStream the stream from which input will be read
     * @since Ant 1.6.3
     */
    /*public*/ void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    /**
     * Create the input, error and output streams based on the
     * configuration options.
     */
    public synchronized void createStreams() {
        if (alwaysLog || outputStream == null) {
            OutputStream outputLog
                = new LogOutputStream();
            outputStream = (outputStream == null)
                ? outputLog : new TeeOutputStream(outputLog, outputStream);
        }
        if (alwaysLog || errorStream == null) {
            OutputStream errorLog
                = new LogOutputStream();
            errorStream = (errorStream == null)
                ? errorLog : new TeeOutputStream(errorLog, errorStream);
        }
     }

    /**
     * Create the StreamHandler to use with our Execute instance.
     *
     * @return the execute stream handler to manage the input, output and
     * error streams.
     *
     */
    public synchronized ExecuteStreamHandler createHandler()
        {
        createStreams();
        return new PumpStreamHandler(outputStream, errorStream, inputStream);
    }



    /**
     * Complete redirection.
     *
     * This operation will close any streams and create any specified
     * property values.
     *
     * @throws IOException if the output properties cannot be read from their
     * output streams.
     */
    public synchronized void complete() throws IOException {
        System.out.flush();
        System.err.flush();

        if (inputStream != null) {
            inputStream.close();
        }

        outputStream.flush();
        outputStream.close();

        errorStream.flush();
        errorStream.close();

        //wait for the StreamPumpers to finish
        while (threadGroup.activeCount() > 0) {
            try {

                wait(1000);
            } catch (InterruptedException eyeEx) {
                // Ignore exception
            }
        }

       // setProperties();

        inputStream = null;
        outputStream = null;
        errorStream = null;
   }

}
