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
  char** x1 = (char**)malloc(256 * sizeof(char*));
  int* x2 = (int*)malloc(256 * sizeof(int));
  int x3 = 0;
  int* x4 = (int*)malloc(256 * sizeof(int));
  int x5 = 0;
  while (x5 != 256) {
    x4[x5] = -1;
    x5 = x5 + 1;
  }
  char** x6 = (char**)malloc(65536 * sizeof(char*));
  int* x7 = (int*)malloc(65536 * sizeof(int));
  char** x8 = (char**)malloc(65536 * sizeof(char*));
  int x9 = 0;
  int* x10 = (int*)malloc(65536 * sizeof(int));
  int* x11 = (int*)malloc(256 * sizeof(int));
  int x12 = open("src/data/t.csv",0);
  int x13 = fsize(x12);
  char* x14 = mmap(0, x13, PROT_READ, MAP_FILE | MAP_SHARED, x12, 0);
  int x15 = 0;
  while (x14[x15] != ',') x15 = x15 + 1;
  x15 = x15 + 1;
  while (x14[x15] != ',') x15 = x15 + 1;
  x15 = x15 + 1;
  while (x14[x15] != '\n') x15 = x15 + 1;
  x15 = x15 + 1;
  while (x15 < x13) {
    int x16 = x15;
    while (x14[x15] != ',') x15 = x15 + 1;
    int x17 = x15 - x16;
    x15 = x15 + 1;
    char* x18 = x14 + x16;
    int x19 = 0;
    while (x14[x15] != ',') {
      x19 = x19 * 10 + (x14[x15] - '0');
      x15 = x15 + 1;
    }
    x15 = x15 + 1;
    int x20 = x15;
    while (x14[x15] != '\n') x15 = x15 + 1;
    x15 = x15 + 1;
    int x21 = x9;
    x6[x21] = x18;
    x7[x21] = x19;
    x8[x21] = x14 + x20;
    x9 = x9 + 1;
    int x22 = (int)hash(x18, x17) & 255;
    //# hash_lookup
    // generated code for hash lookup
    int x23 = ({
      int x24 = x22;
      while (x4[x24] != -1 && ({
        int x25 = x4[x24];
        int x26 = x2[x25];
        !(x26 == x17 && ({
          char* x27 = x1[x25];
          int x28 = 0;
          while (x28 < x26 && x27[x28] == x18[x28]) x28 = x28 + 1;
          x28 == x26;
        }));
      })) x24 = x24 + 1 & 255;
      x4[x24] == -1 ? ({
        int x29 = x3;
        x1[x29] = x18;
        x2[x29] = x17;
        x3 = x3 + 1;
        x4[x24] = x29;
        x11[x29] = 0;
        x29;
      }) : x4[x24];
    });
    //# hash_lookup
    int x30 = x11[x23];
    x10[x23 * 256 + x30] = x21;
    x11[x23] = x30 + 1;
  }
  close(x12);
  int x31 = 0;
  while (x14[x31] != ',') x31 = x31 + 1;
  x31 = x31 + 1;
  while (x14[x31] != ',') x31 = x31 + 1;
  x31 = x31 + 1;
  while (x14[x31] != '\n') x31 = x31 + 1;
  x31 = x31 + 1;
  while (x31 < x13) {
    int x32 = x31;
    while (x14[x31] != ',') x31 = x31 + 1;
    int x33 = x31 - x32;
    x31 = x31 + 1;
    char* x34 = x14 + x32;
    while (x14[x31] != ',') x31 = x31 + 1;
    x31 = x31 + 1;
    while (x14[x31] != '\n') x31 = x31 + 1;
    x31 = x31 + 1;
    int x35 = (int)hash(x34, x33) & 255;
    //# hash_lookup
    // generated code for hash lookup
    int x36 = ({
      int x37 = x35;
      while (x4[x37] != -1 && ({
        int x38 = x4[x37];
        int x39 = x2[x38];
        !(x39 == x33 && ({
          char* x40 = x1[x38];
          int x41 = 0;
          while (x41 < x39 && x40[x41] == x34[x41]) x41 = x41 + 1;
          x41 == x39;
        }));
      })) x37 = x37 + 1 & 255;
      x4[x37];
    });
    //# hash_lookup
    if (x36 != -1) {
      int x42 = x36 * 256;
      int x43 = x42 + x11[x36];
      int x44 = x42;
      while (x44 != x43) {
        int x45 = x10[x44];
        printll(x6[x45]);
        printf(",");
        printf("%d", x7[x45]);
        printf(",");
        printll(x8[x45]);
        printf(",");
        printll(x34);
        printf("%s\n", "");
        x44 = x44 + 1;
      }
    }
  }
  close(x12);
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
