#  Basics of programming course: the utility diff

Realization of the utility diff (https://ru.wikipedia.org/wiki/Diff)

##Usage
Input is possible both from the keyboard and from the console

For examples of output you can print `help`

Specify paths (global or local for files located in the root folder) \
By default, the program will output in the unified format

To select the output format after path use: 
`u` for unified,
`r` for original

Examples of interaction:
```
$ text1.txt text2.txt 
$ text1.txt text2.txt r

$ src/main/kotlin/text1.txt src/main/kotlin/text2.txt u
 ```
##Formats

###Unified format

```
0a1,6
> This is an important
> notice! It should
> therefore be located at
> the beginning of this
> document!
> 
10,14d15
< 
< This paragraph contains
< text that is outdated.
< It will be deleted in the
< near future.
17c18
< check this dokument. On
---
> check this document. On
24a26,29
> 
> This paragraph contains
> important new additions
> to this document.
```
###Original format

```
@@ -1,3 +1,9 @@
+ This is an important
+ notice! It should
+ therefore be located at
+ the beginning of this
+ document!
+ 
  This part of the
  document has stayed the
  same from version to
@@ -7,14 +13,9 @@
  would not be helping to
  compress the size of the
  changes.
- 
- This paragraph contains
- text that is outdated.
- It will be deleted in the
- near future.
  
  It is important to spell
- check this dokument. On
+ check this document. On
  the other hand, a
  misspelled word isn't
  the end of the world.
@@ -22,3 +23,7 @@
  this paragraph needs to
  be changed. Things can
  be added after it.
+ 
+ This paragraph contains
+ important new additions
+ to this document.
```

