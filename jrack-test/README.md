# jRack-Test

Great stuffs to help your jRack projects with unit-tests.

## Maven configuration (POM.xml)

Remove the `junit` dependency and add the following lines to your project's POM file:

```xml
  <dependencies>
    <dependency>
      <groupId>jrack</groupId>
      <artifactId>jrack-test</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```
