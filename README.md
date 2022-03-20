# Sneaky Java

Java utilities to throw checked exceptions in a "sneaky" way.

If you're tired of checked exceptions in lambdas, then this library is made for you!
For example, take a look at this code snippet.

```java
class MyClass {

  void doAction() {
    List<String> messageContents = getStringLines()
        .stream()
        .map(str -> {
          try {
            return objectMapper.readValue(str, MessageDTO.class);
          } catch (JsonProccesingException e) {
            throw new RuntimeException(e);
          }
        })
        .map(msg -> msg.getContent())
        .toList();
  }
}
```

It can be simplified with `Sneaky.function`.

```java
class MyClass {

  void doAction() {
    List<String> messageContents = getStringLines()
        .stream()
        .map(Sneaky.function(
            str -> objectMapper.readValue(str, MessageDTO.class)
        ))
        .map(msg -> msg.getContent())
        .toList();
  }
}
```

This library has no dependencies.

The `master` branch provides the latest `DEV-SNAPSHOT` version. You can find the specific release
version info by git tags.

## Status

[![Build Status](https://app.travis-ci.com/SimonHarmonicMinor/sneaky-java.svg?branch=master)](https://app.travis-ci.com/SimonHarmonicMinor/sneaky-java)
[![Javadoc](https://javadoc.io/badge2/com.kirekov/sneaky-java/javadoc.svg)](https://javadoc.io/doc/com.kirekov/sneaky-java)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SimonHarmonicMinor_sneaky-java&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SimonHarmonicMinor_sneaky-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=SimonHarmonicMinor_sneaky-java&metric=coverage)](https://sonarcloud.io/summary/new_code?id=SimonHarmonicMinor_sneaky-java)
[![Hits-of-Code](https://hitsofcode.com/github/simonharmonicminor/sneaky-java?branch=master)](https://hitsofcode.com/github/simonharmonicminor/sneaky-java/view?branch=master)
[![checkstyle](https://img.shields.io/badge/checkstyle-intergrated-informational)](https://checkstyle.sourceforge.io/)
[![PMD](https://img.shields.io/badge/PMD-intergrated-informational)](https://pmd.github.io/pmd-6.35.0/pmd_rules_java.html)
[![MIT License](https://img.shields.io/apm/l/atomic-design-ui.svg?)](https://github.com/SimonHarmonicMinor/Java-Useful-Utils/blob/master/LICENSE)

## Quick Start

You need Java 8+ to use the library.

Please, use the latest release
version [![Maven Central](https://img.shields.io/maven-central/v/com.kirekov/sneaky-java)](https://mvnrepository.com/artifact/com.kirekov/sneaky-java)
.

Maven:

```xml

<dependency>
  <groupId>com.kirekov</groupId>
  <artifactId>sneaky-java</artifactId>
  <version>x.y.z</version>
</dependency>
```

Gradle:

```groovy
implementation 'com.kirekov:sneaky-java:x.y.z' 
```

## Usage

The library provides wrappers for native Java functional interfaces.

Suppose we want to declare a `Predicate` to use it within Stream API. Here we got `isValueAllowed`
method.

```java
class Predicates {

  boolean isValueAllowed(String value) throws IOException {
    // filtering logic  
  }
}
```

Sadly, the underlined statement won't compile.

```java
class MyService {

  void doJob() {
    // compile error happens here
    Predicate<String> predicate = (value) -> isValueAllowed(value);
    ...
  }
}
```

Because `isValueAllowed` may throw `IOException` which is
a [checked exception](https://www.baeldung.com/java-checked-unchecked-exceptions).
Whilst `Predicate` declaration does not allow it. We can write a custom wrapper for this case.

```java
class MyService {

  void doJob() {
    Predicate<String> predicate = (value) -> {
      try {
        return isValueAllowed(value);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    ...
  }
}
```

Though the solution it does work, it looks rather ugly. Fortunately, `sneaky-java` provides
convenient factory methods for such cases.

```java
import com.kirekov.sneaky.Sneaky;

class MyService {

  void doJob() {
    Predicate<String> predicate = Sneaky.predicate(
        value -> isValueAllowed(value)
    );
    ...
  }
}
```

Besides, sneaky predicates does not wrap checked exceptions with `RuntimeException` instance.
Instead, it uses Java generic type erasure mechanism to rethrow checked exception ignoring compile
errors. Here is the core idea.

```java
class Sneaky {

  @SuppressWarnings("unchecked")
  public static <T extends Exception> void throwUnchecked(Exception exception) throws T {
    throw (T) exception;
  }
}
```

The `sneaky-java` provides wrappers for the common Java functional interfaces.

1. [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)
   — `Sneaky.predicate`
2. [BiPredicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiPredicate.html)
   — `Sneaky.biPredicate`
3. [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)
   — `Sneaky.function`
4. [BiFunction](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html)
   — `Sneaky.biFunction`
5. [Consumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)
   — `Sneaky.consumer`
6. [BiConsumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/BiConsumer.html)
   — `Sneaky.biConsumer`
7. [Supplier](https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html)
   — `Sneaky.supplier`