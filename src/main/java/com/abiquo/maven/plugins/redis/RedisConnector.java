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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import redis.clients.jedis.Jedis;

/**
 * Redis Connector to perform operations against the Redis database.
 * 
 * @author Ignasi Barrera
 */
public class RedisConnector implements Redis, InvocationHandler
{
    /** The target Redis connector. */
    private Jedis jedis;

    /**
     * Get a new instance of the {@link RedisConnector}.
     * 
     * @param host The host where the Redis database is running.
     * @param port The port where the Redis database is listening.
     * @return A new instance of the <code>RedisConnector</code>.
     */
    public static Redis newInstance(String host, int port)
    {
        return (Redis) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class[] {Redis.class}, new RedisConnector(host, port));
    }

    /**
     * Creates a new {@link RedisConnector} to the given host and port.
     * 
     * @param host The host where the Redis database is running.
     * @param port The port where the Redis database is listening.
     */
    private RedisConnector(String host, int port)
    {
        super();
        jedis = new Jedis(host, port);
    }

    @Override
    public boolean ping()
    {
        return jedis.ping().equalsIgnoreCase("pong");
    }

    @Override
    public void flushDB() throws IOException
    {
        jedis.flushDB();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        try
        {
            jedis.connect();
            return method.invoke(this, args);
        }
        catch (InvocationTargetException ex)
        {
            // Rethrow the original exception
            throw ex.getTargetException();
        }
        finally
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

}
