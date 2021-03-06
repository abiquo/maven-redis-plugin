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

import redis.clients.jedis.Jedis;

/**
 * Redis Connector to perform operations against the Redis database.
 * 
 * @author Ignasi Barrera
 */
public class Redis
{
    /** The target Redis connector. */
    private Jedis jedis;

    /**
     * Creates a new {@link Redis} to the given host and port.
     * 
     * @param host The host where the Redis database is running.
     * @param port The port where the Redis database is listening.
     */
    public Redis(String host, int port)
    {
        super();
        jedis = new Jedis(host, port);
    }

    /**
     * Pings the Redis database.
     * 
     * @return Boolean indicating if the Redis database is up.
     */
    public boolean ping()
    {
        try
        {
            connect();
            return jedis.ping().equalsIgnoreCase("pong");
        }
        catch (IOException ex)
        {
            return false;
        }
        finally
        {
            disconnect();
        }
    }

    /**
     * Cleans the selected Redis database.
     * 
     * @throws IOException If the clean operation fails.
     */
    public void flushDB() throws IOException
    {
        try
        {
            connect();
            jedis.flushDB();
        }
        finally
        {
            disconnect();
        }
    }

    /**
     * Connects to the Redis database.
     * 
     * @throws IOException
     */
    private void connect() throws IOException
    {
        jedis.connect();
    }

    /**
     * Disconnects from the Redis datbase.
     */
    private void disconnect()
    {
        if (jedis.isConnected())
        {
            try
            {
                jedis.disconnect();
            }
            catch (IOException ex)
            {
                // Ignore disconnection errors
            }
        }
    }

}
