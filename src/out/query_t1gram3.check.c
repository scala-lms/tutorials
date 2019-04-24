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
  printf("%s\n", "Word,Value,Phrase,Year,MatchCount,VolumeCount");
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
    x4 = x4 + 1;
    char* x10 = x3 + x9;
    int x11 = 0;
    while (x3[x4] != '\n') {
      x11 = x11 * 10 + (x3[x4] - '0');
      x4 = x4 + 1;
    }
    x4 = x4 + 1;
    int x12 = x11;
    int x13 = 0;
    while (x13 < x7) {
      int x14 = x13;
      while (x8[x13] != '\t') x13 = x13 + 1;
      x13 = x13 + 1;
      int x15 = x13;
      while (x8[x13] != '\t') x13 = x13 + 1;
      x13 = x13 + 1;
      int x16 = x13;
      while (x8[x13] != '\t') x13 = x13 + 1;
      x13 = x13 + 1;
      int x17 = x13;
      while (x8[x13] != '\n') x13 = x13 + 1;
      x13 = x13 + 1;
      printll(x10);
      printf(",");
      printf("%d", x12);
      printf(",");
      printll(x8 + x14);
      printf(",");
      printll(x8 + x15);
      printf(",");
      printll(x8 + x16);
      printf(",");
      printll(x8 + x17);
      printf("%s\n", "");
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
