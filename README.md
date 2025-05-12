# sleep-maven-plugin
[![Maven Central](https://img.shields.io/maven-central/v/de.fbrandes.maven/sleep-maven-plugin.svg?label=Maven%20Central&logo=apachemaven)](https://central.sonatype.com/artifact/de.fbrandes.maven/sleep-maven-plugin/)
[![Build plugin](https://github.com/fbrandes/sleep-maven-plugin/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/fbrandes/sleep-maven-plugin/actions/workflows/build.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fbrandes%3Asleep-maven-plugin&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fbrandes%3Asleep-maven-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fbrandes%3Asleep-maven-plugin&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=fbrandes%3Asleep-maven-plugin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fbrandes%3Asleep-maven-plugin&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fbrandes%3Asleep-maven-plugin)

Maven plugin that pauses the build for a specified amount of time

# Getting started
Maven: 
```xml
<dependency>
    <groupId>de.fbrandes.maven</groupId>
    <artifactId>sleep-maven-plugin</artifactId>
    <version>1.2.1</version>
</dependency>
```
Gradle
```groovy
implementation 'de.fbrandes.maven:sleep-maven-plugin:1.2.1'
```

# Usage
You can either call the sleep goal directly and pass an amount to sleep, like this: 
```bash 
mvn sleep:sleep -Dseconds=30
```
Or you can create an execution if you want to define a sleep during your build, for example:   

```xml
<plugin>
    <groupId>de.fbrandes.maven</groupId>
    <artifactId>sleep-maven-plugin</artifactId>
    <version>1.2.1</version>
    <executions>
        <execution>
            <id>wait after compile</id>
            <phase>compile</phase>
            <goals>
                <goal>sleep</goal>
            </goals>
            <configuration>
                <seconds>2</seconds>
            </configuration>
        </execution>
    </executions>
</plugin>
```

# License
Distributed under the Apache License. See [LICENSE](LICENSE) file for more information.
