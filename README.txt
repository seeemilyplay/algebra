This is ancient and old forgotten and crusty code I found at the back of a cupboard
and decided might be nice to try and do something with (there's one particular little bit I like - it might
be nice to reimplement that bit as a tool or library).

To compile you will first need to download the following jars and manually put them in your maven repos:

1) cream 1.06 from http://bach.istc.kobe-u.ac.jp/cream/
2) napkin look and feel 1.0_001 from http://sourceforge.net/projects/napkinlaf/
3) beankeeper 2.6.0 from http://beankeeper.netmind.hu/download.php

You'll also need to turn off the tests, because a few fail:

mvn install -DskipTests

Unfortunately it doesn't run, something to do with a number format exception in the look and feel.
I'm working on it.
