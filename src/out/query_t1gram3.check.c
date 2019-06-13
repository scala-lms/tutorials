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
  while (x3[x4] != '\n') x4 = x4 + 1;
  x4 = x4 + 1;
  int x5 = open("src/data/t1gram.csv",0);
  int x6 = fsize(x5);
  char* x7 = mmap(0, x6, PROT_READ, MAP_FILE | MAP_SHARED, x5, 0);
  while (x4 < x2) {
    int x8 = x4;
    while (x3[x4] != ',') x4 = x4 + 1;
    x4 = x4 + 1;
    char* x9 = x3 + x8;
    int x10 = 0;
    while (x3[x4] != '\n') {
      x10 = x10 * 10 + (int)(x3[x4] - '0');
      x4 = x4 + 1;
    }
    x4 = x4 + 1;
    int x11 = x10;
    int x12 = 0;
    while (x12 < x6) {
      int x13 = x12;
      while (x7[x12] != '\t') x12 = x12 + 1;
      int x14 = x12 + 1;
      x12 = x14;
      while (x7[x12] != '\t') x12 = x12 + 1;
      int x15 = x12 + 1;
      x12 = x15;
      while (x7[x12] != '\t') x12 = x12 + 1;
      int x16 = x12 + 1;
      x12 = x16;
      while (x7[x12] != '\n') x12 = x12 + 1;
      x12 = x12 + 1;
      printll(x9);
      printf(",");
      printf("%d", x11);
      printf(",");
      printll(x7 + x13);
      printf(",");
      printll(x7 + x14);
      printf(",");
      printll(x7 + x15);
      printf(",");
      printll(x7 + x16);
      printf("%s\n", "");
    }
    close(x5);
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
