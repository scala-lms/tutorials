/*****************************************
Emitting C Generated Code
*******************************************/
#include <unistd.h>
#include <errno.h>
#include <err.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdint.h>
#include <sys/mman.h>
#include <stdbool.h>
/************* Functions **************/
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
long hash(char *str0, int len) {
  unsigned char* str = (unsigned char*)str0;
  unsigned long hash = 5381;
  int c;
  while ((c = *str++) && len--)
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
/**************** Snippet ****************/
void Snippet(char* x0) {
  printf("%s\n", "Word,Value,Word,Year,MatchCount,VolumeCount");
  int x1 = open("src/data/words.csv",0);
  int x2 = fsize(x1);
  char* x3 = mmap(0, x2, PROT_READ, MAP_FILE | MAP_SHARED, x1, 0);
  int x4 = 0;
  while (x3[x4] != ',') x4 = x4 + 1;
  x4 = x4 + 1;
  int x5 = 0;
  while (x3[x4] != '\n') {
    x5 = x5 * 10 + (x3[x4] - '0');
    x4 = x4 + 1;
  }
  x4 = x4 + 1;
  int x6 = open("src/data/t1gram.csv",0);
  int x7 = fsize(x6);
  char* x8 = mmap(0, x7, PROT_READ, MAP_FILE | MAP_SHARED, x6, 0);
  while (x4 < x2) {
    int x9 = x4;
    while (x3[x4] != ',') x4 = x4 + 1;
    int x10 = x4 - x9;
    x4 = x4 + 1;
    char* x11 = x3 + x9;
    int x12 = 0;
    while (x3[x4] != '\n') {
      x12 = x12 * 10 + (x3[x4] - '0');
      x4 = x4 + 1;
    }
    x4 = x4 + 1;
    int x13 = x12;
    int x14 = 0;
    while (x14 < x7) {
      int x15 = x14;
      while (x8[x14] != '\t') x14 = x14 + 1;
      int x16 = x14;
      x14 = x14 + 1;
      int x17 = x14;
      while (x8[x14] != '\t') x14 = x14 + 1;
      x14 = x14 + 1;
      int x18 = x14;
      while (x8[x14] != '\t') x14 = x14 + 1;
      x14 = x14 + 1;
      int x19 = x14;
      while (x8[x14] != '\n') x14 = x14 + 1;
      x14 = x14 + 1;
      if (x10 == x16 - x15 && ({
        char* x20 = x8 + x15;
        int x21 = 0;
        while (x21 < x10 && x11[x21] == x20[x21]) x21 = x21 + 1;
        x21 == x10;
      })) {
        printll(x11);
        printf(",");
        printf("%d", x13);
        printf(",");
        printll(x8 + x15);
        printf(",");
        printll(x8 + x17);
        printf(",");
        printll(x8 + x18);
        printf(",");
        printll(x8 + x19);
        printf("%s\n", "");
      } else {
      }
    }
    close(x6);
  }
  close(x1);
}
/*****************************************
End of C Generated Code
*******************************************/
int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("usage: %s <arg>\n", argv[0]);
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
