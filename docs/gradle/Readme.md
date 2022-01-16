# Project Signer - Gradle Troubleshooting

Gradle can be difficult to understand, especially when you are so used to working with `Maven`. Although I'm not an advocate for any particular package manager I do understand the need to learn about all of them as much as possible.

To avoid repeating same errors, this document is here to highlight problems I've faced using gradle and how I've solved them:

### 1. Migrating to JDK 17 - Compiling Java Version error

#### Symptom - project [international-airports-service-root](https://bitbucket.org/jesperancinha/international-airports-service-root)

```java
* Where:
Build file '/Users/jofisaes/dev/src/jofisaes/international-airports-service-root/international-airports-rest/international-airports-model/build.gradle'

* What went wrong:
Could not compile build file '/Users/jofisaes/dev/src/jofisaes/international-airports-service-root/international-airports-rest/international-airports-model/build.gradle'.
> startup failed:
  General error during conversion: Unsupported class file major version 61
  
  java.lang.IllegalArgumentException: Unsupported class file major version 61
        at groovyjarjarasm.asm.ClassReader.<init>(ClassReader.java:189)
        at groovyjarjarasm.asm.ClassReader.<init>(ClassReader.java:170)
        at groovyjarjarasm.asm.ClassReader.<init>(ClassReader.java:156)
        at groovyjarjarasm.asm.ClassReader.<init>(ClassReader.java:277)
```

#### Solution

I updated to Gradle 7.3.3 using SDK-MAN. Since I'm using SDK-MAN, the native MAC-OS installation has no precedence and this is why this issue kept coming back.

### 2. Block Hound issues with flag [-XX:+AllowRedefinitionToAddDeleteMethod](https://github.com/reactor/BlockHound/issues/33)

#### Symptom - - project [international-airports-service-root](https://bitbucket.org/jesperancinha/international-airports-service-root)


```java
Caused by: java.lang.ExceptionInInitializerError
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480)
	at java.base/java.util.ServiceLoader$ProviderImpl.newInstance(ServiceLoader.java:789)
	... 36 more
Caused by: java.lang.IllegalStateException: The instrumentation have failed.
It looks like you're running on JDK 13+.
You need to add '-XX:+AllowRedefinitionToAddDeleteMethods' JVM flag.
See https://github.com/reactor/BlockHound/issues/33 for more info.
	at reactor.blockhound.BlockHound$Builder.testInstrumentation(BlockHound.java:482)
	at reactor.blockhound.BlockHound$Builder.install(BlockHound.java:445)
	at reactor.blockhound.BlockHound.install(BlockHound.java:95)
	at reactor.blockhound.junit.platform.BlockHoundTestExecutionListener.<clinit>(BlockHoundTestExecutionListener.java:19)
	... 42 more
```

#### Solution

In build.json:

```kotlin
test {
    jvmArgs '-XX:+AllowRedefinitionToAddDeleteMethods'
}
```