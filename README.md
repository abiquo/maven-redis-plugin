Maven Redis Plugin
==================
       
This plugin enables Redis access during the Maven build lifecycle. 

Usage
-----

To enable the plugin, include it in the *build* section of your
`pom.xml` and configure it to execute the desired goal, as follows:

    <plugin>
        <groupId>com.abiquo.maven</groupId>
        <artifactId>maven-redis-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>clean</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    
Configuration
-------------

You can configure where the Redis database is running. By default the plugin
will assume that it is at `localhost:6379`. If you need to change it, you can
configure the plugin as follows:

    <configuration>
        <skip>true|false</skip> (Optional)
        <host>The Redis host</host> (Optional)
        <port>The Redis listening port</port> (Optional)
    </configuration>

License
-------

See LICENSE file.
