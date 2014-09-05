#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Phrase,Year,MatchCount,VolumeCount,VolumeCount1");
  int32_t x5 = 0;
  int32_t x2 = open(x0,0);
  int32_t x3 = fsize(x2);
  char* x4 = mmap(0, x3, PROT_READ, MAP_FILE | MAP_SHARED, x2, 0);
  int32_t x57 = open("src/data/words.csv",0);
  int32_t x58 = fsize(x57);
  char* x59 = mmap(0, x58, PROT_READ, MAP_FILE | MAP_SHARED, x57, 0);
  for (;;) {
    int32_t x6 = x5;
    bool x7 = x6 < x3;
    if (!x7) break;
    int32_t x9 = x5;
    for (;;) {
      int32_t x10 = x5;
      char x11 = x4[x10];
      bool x12 = x11 != '\t';
      if (!x12) break;
      x5 += 1;
    }
    int32_t x17 = x5;
    x5 += 1;
    int32_t x21 = x5;
    for (;;) {
      int32_t x22 = x5;
      char x23 = x4[x22];
      bool x24 = x23 != '\t';
      if (!x24) break;
      x5 += 1;
    }
    int32_t x29 = x5;
    x5 += 1;
    int32_t x33 = x5;
    for (;;) {
      int32_t x34 = x5;
      char x35 = x4[x34];
      bool x36 = x35 != '\t';
      if (!x36) break;
      x5 += 1;
    }
    int32_t x41 = x5;
    x5 += 1;
    int32_t x45 = x5;
    for (;;) {
      int32_t x46 = x5;
      char x47 = x4[x46];
      bool x48 = x47 != '\n';
      if (!x48) break;
      x5 += 1;
    }
    int32_t x53 = x5;
    x5 += 1;
    int32_t x60 = 0;
    for (;;) {
      int32_t x61 = x60;
      char x62 = x59[x61];
      bool x63 = x62 != ',';
      if (!x63) break;
      x60 += 1;
    }
    int32_t x68 = x60;
    x60 += 1;
    int32_t x71 = x60;
    for (;;) {
      int32_t x72 = x60;
      char x73 = x59[x72];
      bool x74 = x73 != '\n';
      if (!x74) break;
      x60 += 1;
    }
    int32_t x79 = x60;
    x60 += 1;
    char* x20 = x4+x9;
    char* x32 = x4+x21;
    char* x44 = x4+x33;
    char* x56 = x4+x45;
    for (;;) {
      int32_t x83 = x60;
      bool x84 = x83 < x58;
      if (!x84) break;
      int32_t x86 = x60;
      for (;;) {
        int32_t x87 = x60;
        char x88 = x59[x87];
        bool x89 = x88 != ',';
        if (!x89) break;
        x60 += 1;
      }
      int32_t x94 = x60;
      x60 += 1;
      int32_t x98 = x60;
      for (;;) {
        int32_t x99 = x60;
        char x100 = x59[x99];
        bool x101 = x100 != '\n';
        if (!x101) break;
        x60 += 1;
      }
      int32_t x106 = x60;
      x60 += 1;
      int32_t x110 = printll(x20);
      printf(",");
      int32_t x112 = printll(x32);
      printf(",");
      int32_t x114 = printll(x44);
      printf(",");
      int32_t x116 = printll(x56);
      printf(",");
      char* x109 = x59+x98;
      int32_t x118 = printll(x109);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
