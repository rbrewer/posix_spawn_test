To build the example:

./gradlew fatJar

To run the program:

java -jar build/libs/posix_spawn-jnr-all.jar

This usually results in a file in the current directory with unprintable characters, instead of the
file "/tmp/bar-log". "ls -lb" will show filenames with unprintable characters as backslash escapes,
and they can be matched with bash by wrapping in $''
