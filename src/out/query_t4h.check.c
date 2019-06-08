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
  while (x13 < x11) {
    int x14 = x13;
    while (x12[x13] != ',') x13 = x13 + 1;
    x13 = x13 + 1;
    int x15 = 0;
    while (x12[x13] != ',') {
      x15 = x15 * 10 + (x12[x13] - '0');
      x13 = x13 + 1;
    }
    x13 = x13 + 1;
    int x16 = x13;
    while (x12[x13] != '\n') x13 = x13 + 1;
    x13 = x13 + 1;
    int x17 = x7;
    x4[x17] = x12 + x14;
    x5[x17] = x15;
    x6[x17] = x12 + x16;
    x7 = x7 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x18 = ({
      int x19 = 0;
      while (false) x19 = x19 + 1 & 255;
      x2[x19] == -1 ? ({
        int x20 = x1;
        x1 = x1 + 1;
        x2[x19] = x20;
        x9[x20] = 0;
        x20;
      }) : x2[x19];
    });
    //# hash_lookup
    int x21 = x9[x18];
    x8[x18 * 256 + x21] = x17;
    x9[x18] = x21 + 1;
  }
  close(x10);
  int x22 = 0;
  while (x12[x22] != ',') x22 = x22 + 1;
  x22 = x22 + 1;
  while (x12[x22] != ',') x22 = x22 + 1;
  x22 = x22 + 1;
  while (x12[x22] != '\n') x22 = x22 + 1;
  x22 = x22 + 1;
  while (x22 < x11) {
    int x23 = x22;
    while (x12[x22] != ',') x22 = x22 + 1;
    x22 = x22 + 1;
    char* x24 = x12 + x23;
    while (x12[x22] != ',') x22 = x22 + 1;
    x22 = x22 + 1;
    while (x12[x22] != '\n') x22 = x22 + 1;
    x22 = x22 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x25 = ({
      int x26 = 0;
      while (false) x26 = x26 + 1 & 255;
      x2[x26];
    });
    //# hash_lookup
    if (x25 != -1) {
      int x27 = x25 * 256;
      int x28 = x27 + x9[x25];
      int x29 = x27;
      while (x29 != x28) {
        int x30 = x8[x29];
        printll(x4[x30]);
        printf(",");
        printf("%d", x5[x30]);
        printf(",");
        printll(x6[x30]);
        printf(",");
        printll(x24);
        printf("%s\n", "");
        x29 = x29 + 1;
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
