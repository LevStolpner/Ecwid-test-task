# Ecwid home assignment #

You have a simple text file with IPv4 addresses. One line is one address, line by line:

```
145.67.23.4
8.34.5.23
89.54.3.124
89.54.3.124
3.45.71.5
...
```

The file is unlimited in size and can occupy tens and hundreds of gigabytes.

You should calculate the number of __unique addresses__ in this file using as little memory and time as possible.
There is a "naive" algorithm for solving this problem (read line by line, put lines into HashSet).
It's better if your implementation is more complicated and faster than this naive algorithm.

Some details:
- For any questions, feel free to write to join-ecom@lightspeedhq.com
- You can only use the features of the standard Java/Kotlin library.
- You should write in Java (version 21 and above) or Kotlin.
- The assignment must have a working main() method, to demonstrate how it works
- The completed assignment should be posted on GitHub

---
Before submitting an assignment, it will be nice to check how it handles this [file](https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip). Attention - the file weighs about 20Gb, and unzips to about 120Gb.

# Solution #

## To build and run project: ##
```
./gradlew build

./gradlew run --args=<path to file with ip addresses>
```

## Thought process ##

There were a few things in this assignment where I had to make some sort of decision, 
here I outline my thought process which led to final solution.

### Reading file ###

As I started working with 120GB file from task description, I realized it's very important to work with it efficiently.
As it obviously can't fit into memory, solution should avoid doing that and read the file some other way.  <br/>
My initial thought was to somehow use multithreading to read file in parallel, but after some research and testing
it turned out that for such files most optimal solution would be to simply use single-threaded streaming reading.

### Choice of data structure ###

I went through several data structures until I ended up on solution with 2 BitSets.
Naive solution using `HashSet<String>` fails with OoME very quickly, then I thought about converting String to Integer,
as IPv4 has the same range of values as Integer in Java (4 bytes). Still, `HashSet<Integer>` failed with OoME,
so I had to come up with something even smaller.  <br/>
Then I realized, we don't need to store actual representations of IPs we already met,
and we can use some sort of array with boolean values, and then I remembered there is a BitSet,
which can do exactly what I need.  <br/>
But, one caveat, BitSet maximum length is only half the range of values,
so we need to use 2 of them for solution to work.

### Algorithm for conversion of IPv4 into Integer ###

In my initial tries I used an algorithm with split() function for each String, 
but it seemed to slow down the process quite a bit (compared to simple reading through a file).  <br/>
Then I read about ways to convert IPv4 to Integer, and thought that I can do it with bit operations, 
and that it should be quite fast compared to using split() (turned out, around 2 times faster).  <br/>
I thought about doing some validation for correctness of IPv4, 
but decided with current solution it might slow down the solution and make it harder to understand.