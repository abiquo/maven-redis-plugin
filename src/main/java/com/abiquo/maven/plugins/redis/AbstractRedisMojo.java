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

import org.apache.maven.plugin.AbstractMojo;

/**
 * Base class for all Redis Mojos.
 * 
 * @author Ignasi Barrera
 */
public abstract class AbstractRedisMojo extends AbstractMojo
{
    /**
     * Boolean indicating if this mojo execution must be excluded.
     * 
     * @parameter default-value="false"
     */
    protected boolean skip;

    /**
     * The Redis database host.
     * 
     * @parameter default-value="localhost"
     */
    protected String host;

    /**
     * The Redis database post.
     * 
     * @parameter default-value="6379"
     */
    protected Integer port;

    /**
     * The Redis connector.
     */
    protected Redis redis;

    /**
     * Initializes the mojo.
     */
    protected void initialize()
    {
        getLog().info("Initializing Redis connector to " + host + ":" + port);
        redis = new Redis(host, port.intValue());
    }
}
