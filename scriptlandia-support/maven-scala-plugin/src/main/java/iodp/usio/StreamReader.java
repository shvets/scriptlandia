/*
 * Copyright 2006 Integrated Ocean Drilling Program US Implementing Organization.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package iodp.usio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;

public class StreamReader extends Thread{

	private InputStream in;
	private Log log;

	public StreamReader(InputStream in, AbstractMojo mojo)
	{
		this.in = in;
		this.log = mojo.getLog();
	}

	public void run()
	{
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = r.readLine()) != null)
			{
				log.info(line);
			}
			r.close();
			in.close();
		} catch (Exception e) {
		}
	}
}
