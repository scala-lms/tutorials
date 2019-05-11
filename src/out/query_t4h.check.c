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
  printf("%s\n", "Name,Value,Flag,Name1");
  int x1 = 0;
  int* x2 = (int*)malloc(256 * sizeof(int));
  int x3 = 0;
  while (x3 != 256) {
    x2[x3] = -1;
    x3 = x3 + 1;
  }
  char** x4 = (char**)malloc(65536 * sizeof(char*));
  int* x5 = (int*)malloc(65536 * sizeof(int));
  char** x6 = (char**)malloc(65536 * sizeof(char*));
  int x7 = 0;
  int* x8 = (int*)malloc(65536 * sizeof(int));
  int* x9 = (int*)malloc(256 * sizeof(int));
  int x10 = open("src/data/t.csv",0);
  int x11 = fsize(x10);
  char* x12 = mmap(0, x11, PROT_READ, MAP_FILE | MAP_SHARED, x10, 0);
  int x13 = 0;
  while (x12[x13] != ',') x13 = x13 + 1;
  x13 = x13 + 1;
  while (x12[x13] != ',') x13 = x13 + 1;
  x13 = x13 + 1;
  while (x12[x13] != '\n') x13 = x13 + 1;
  x13 = x13 + 1;
  int x14 = 0 & 255;
  while (x13 < x11) {
    int x15 = x13;
    while (x12[x13] != ',') x13 = x13 + 1;
    x13 = x13 + 1;
    int x16 = 0;
    while (x12[x13] != ',') {
      x16 = x16 * 10 + (x12[x13] - '0');
      x13 = x13 + 1;
    }
    x13 = x13 + 1;
    int x17 = x13;
    while (x12[x13] != '\n') x13 = x13 + 1;
    x13 = x13 + 1;
    int x18 = x7;
    x4[x18] = x12 + x15;
    x5[x18] = x16;
    x6[x18] = x12 + x17;
    x7 = x7 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x19 = ({
      int x20 = x14;
      while (x2[x20] != -1 && false) x20 = x20 + 1 & 255;
      x2[x20] == -1 ? ({
        int x21 = x1;
        x1 = x1 + 1;
        x2[x20] = x21;
        x9[x21] = 0;
        x21;
      }) : x2[x20];
    });
    //# hash_lookup
    int x22 = x9[x19];
    x8[x19 * 256 + x22] = x18;
    x9[x19] = x22 + 1;
  }
  close(x10);
  int x23 = 0;
  while (x12[x23] != ',') x23 = x23 + 1;
  x23 = x23 + 1;
  while (x12[x23] != ',') x23 = x23 + 1;
  x23 = x23 + 1;
  while (x12[x23] != '\n') x23 = x23 + 1;
  x23 = x23 + 1;
  while (x23 < x11) {
    int x24 = x23;
    while (x12[x23] != ',') x23 = x23 + 1;
    x23 = x23 + 1;
    while (x12[x23] != ',') x23 = x23 + 1;
    x23 = x23 + 1;
    while (x12[x23] != '\n') x23 = x23 + 1;
    x23 = x23 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x25 = ({
      int x26 = x14;
      while (x2[x26] != -1 && false) x26 = x26 + 1 & 255;
      x2[x26];
    });
    //# hash_lookup
    if (x25 != -1) {
      char* x27 = x12 + x24;
      int x28 = x25 * 256;
      int x29 = x28 + x9[x25];
      int x30 = x28;
      while (x30 != x29) {
        int x31 = x8[x30];
        printll(x4[x31]);
        printf(",");
        printf("%d", x5[x31]);
        printf(",");
        printll(x6[x31]);
        printf(",");
        printll(x27);
        printf("%s\n", "");
        x30 = x30 + 1;
      }
    }
  }
  close(x10);
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
