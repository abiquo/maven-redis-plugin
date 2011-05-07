/**
 * Copyright (c) 2010 Ignasi Barrera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.abiquo.maven.plugins.redis;

import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Clean the Redis database.
 * 
 * @author Ignasi Barrera
 * @goal clean
 * @phase initialize
 * @requiresProject
 */
public class CleanMojo extends AbstractRedisMojo
{
    /**
     * Cleans the Redis database.
     * 
     * @throws MojoExecutionException If there is an error during plugin execution,
     * @throws MojoFailureException If there is an error during plugin execution,
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        if (!skip)
        {
            initialize();

            if (redis.ping())
            {
                try
                {
                    getLog().info("Cleaning the selected Redis database");
                    redis.flushDB();
                }
                catch (IOException ex)
                {
                    throw new MojoExecutionException("Could not clean the selected database: "
                        + ex.getMessage(), ex);
                }
            }
            else
            {
                getLog().warn(
                    "Redis database seems to be down. Database clean will not be performed");
            }
        }
    }

}
