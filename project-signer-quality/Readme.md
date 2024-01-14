# project-signer-quality

A Collection of quality code files. More to come soon...

## Quick search and replaces

### Mardown (*.md)

#### Bullet lists (3 spaces)

```text
\n- ([a-zA-Z])

\n-   $1
```

#### Bullet lists (3 spaces)

```text
\n- (\[)

\n-   $1
```

#### Number lists (1 Ciphers, 2 Spaces)

```text
\n([0-9])\.([\  ]+)(.)

\n$1\.  $3
```

```text
([\ ]+)([0-9])\.([\  ]+)(.)

$1$2\.  $4
```

#### Number lists (2 Ciphers, 1 Space)

```text
\n([0-9][0-9]+)\.([\  ]+)(.)

\n$1\. $3
```

```text
([\ ]+)([0-9][0-9]+)\.([\  ]+)(.)

$1$2\. $4
```

### Java Test Files (*Test.java)

#### JUnit 5 When (No underscores)

```text
test([a-zA-Z]*)_w([a-zA-Z]*)

test$1W$2
```

#### JUnit 5 Then (No underscores)

```text
([a-zA-Z]*)_then([a-zA-Z]*)

$1Then$2
```

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
