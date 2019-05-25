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
  printf("%s\n", "Name,Value,Flag,Name");
  int x1 = open("src/data/t.csv",0);
  int x2 = fsize(x1);
  char* x3 = mmap(0, x2, PROT_READ, MAP_FILE | MAP_SHARED, x1, 0);
  int x4 = 0;
  while (x3[x4] != ',') x4 = x4 + 1;
  x4 = x4 + 1;
  while (x3[x4] != ',') x4 = x4 + 1;
  x4 = x4 + 1;
  while (x3[x4] != '\n') x4 = x4 + 1;
  x4 = x4 + 1;
  while (x4 < x2) {
    int x5 = x4;
    while (x3[x4] != ',') x4 = x4 + 1;
    int x6 = x4 - x5;
    x4 = x4 + 1;
    char* x7 = x3 + x5;
    int x8 = 0;
    while (x3[x4] != ',') {
      x8 = x8 * 10 + (x3[x4] - '0');
      x4 = x4 + 1;
    }
    x4 = x4 + 1;
    int x9 = x8;
    int x10 = x4;
    while (x3[x4] != '\n') x4 = x4 + 1;
    x4 = x4 + 1;
    char* x11 = x3 + x10;
    int x12 = 0;
    while (x3[x12] != ',') x12 = x12 + 1;
    x12 = x12 + 1;
    while (x3[x12] != ',') x12 = x12 + 1;
    x12 = x12 + 1;
    while (x3[x12] != '\n') x12 = x12 + 1;
    x12 = x12 + 1;
    while (x12 < x2) {
      int x13 = x12;
      while (x3[x12] != ',') x12 = x12 + 1;
      int x14 = x12;
      x12 = x12 + 1;
      char* x15 = x3 + x13;
      while (x3[x12] != ',') x12 = x12 + 1;
      x12 = x12 + 1;
      while (x3[x12] != '\n') x12 = x12 + 1;
      x12 = x12 + 1;
      if (x6 == x14 - x13 && ({
        int x16 = 0;
        while (x16 < x6 && x7[x16] == x15[x16]) x16 = x16 + 1;
        x16 == x6;
      })) {
        printll(x7);
        printf(",");
        printf("%d", x9);
        printf(",");
        printll(x11);
        printf(",");
        printll(x15);
        printf("%s\n", "");
      }
    }
    close(x1);
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
