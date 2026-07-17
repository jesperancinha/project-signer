---
name: Maven standards
description: Conventions to use in Maven files, mostly the `pom.xml` files
---

## 1. Make sure dependencies that are supposed to be of scope `test`, remain test dependencies

In most cases, test dependencies should be of scope `test`. If you find any dependencies that are supposed to be of scope `test` but are not, you should add the `<scope>test</scope>` element to them. This ensures that the dependencies are only included in the test classpath and not in the production classpath.
For these cases, the following are the dependencies that supposed to be of scope `test`:

```xml
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
        </dependency>
        <dependency>
            <groupId>io.mockk</groupId>
            <artifactId>mockk-jvm</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.ninja-squad</groupId>
            <artifactId>springmockk</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.kotest</groupId>
            <artifactId>kotest-assertions-core-jvm</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```

In some cases, the test dependencies may be used for production code in test modules. Those cases are not always easy to determine.
However, if you see that the module, where these dependencies are found to be used, is being used as a test dependency of other modules in the same project, then leave the dependency as is.
