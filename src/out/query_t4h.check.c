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
  int x2 = 0;
  int* x3 = (int*)malloc(256 * sizeof(int));
  int x4 = 0;
  while (x4 != 256) {
    x3[x4] = -1;
    x4 = x4 + 1;
  }
  char** x5 = (char**)malloc(65536 * sizeof(char*));
  int* x6 = (int*)malloc(65536 * sizeof(int));
  int* x7 = (int*)malloc(65536 * sizeof(int));
  char** x8 = (char**)malloc(65536 * sizeof(char*));
  int* x9 = (int*)malloc(65536 * sizeof(int));
  int x10 = 0;
  int x11 = 0;
  int* x12 = (int*)malloc(65536 * sizeof(int));
  int* x13 = (int*)malloc(256 * sizeof(int));
  int x14 = open("src/data/t.csv",0);
  int x15 = fsize(x14);
  char* x16 = mmap(0, x15, PROT_READ, MAP_FILE | MAP_SHARED, x14, 0);
  int x17 = 0;
  while (x16[x17] != ',') x17 = x17 + 1;
  x17 = x17 + 1;
  int x18 = 0;
  while (x16[x17] != ',') {
    x18 = x18 * 10 + (int)(x16[x17] - '0');
    x17 = x17 + 1;
  }
  x17 = x17 + 1;
  while (x16[x17] != '\n') x17 = x17 + 1;
  x17 = x17 + 1;
  int x19 = (int)0L & 255;
  while (x17 < x15) {
    int x20 = x17;
    while (x16[x17] != ',') x17 = x17 + 1;
    int x21 = x17;
    x17 = x17 + 1;
    int x22 = 0;
    while (x16[x17] != ',') {
      x22 = x22 * 10 + (int)(x16[x17] - '0');
      x17 = x17 + 1;
    }
    x17 = x17 + 1;
    int x23 = x17;
    while (x16[x17] != '\n') x17 = x17 + 1;
    int x24 = x17;
    x17 = x17 + 1;
    int x25 = x11;
    x5[x25] = x16 + x20;
    x6[x25] = x21 - x20;
    x7[x25] = x22;
    x8[x25] = x16 + x23;
    x9[x25] = x24 - x23;
    x11 = x11 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x26 = ({
      int x27 = x19;
      while (x3[x27] != -1 && ({
        false;
      })) x27 = x27 + 1 & 255;
      (x3[x27] == -1) ? ({
        int x28 = x2;
        x2 = x2 + 1;
        x3[x27] = x28;
        x13[x28] = 0;
        x28;
      }) : x3[x27];
    });
    //# hash_lookup
    int x29 = x13[x26];
    x12[x26 * 256 + x29] = x25;
    x13[x26] = x29 + 1;
  }
  close(x14);
  int x30 = 0;
  while (x16[x30] != ',') x30 = x30 + 1;
  x30 = x30 + 1;
  int x31 = 0;
  while (x16[x30] != ',') {
    x31 = x31 * 10 + (int)(x16[x30] - '0');
    x30 = x30 + 1;
  }
  x30 = x30 + 1;
  while (x16[x30] != '\n') x30 = x30 + 1;
  x30 = x30 + 1;
  while (x30 < x15) {
    int x32 = x30;
    while (x16[x30] != ',') x30 = x30 + 1;
    x30 = x30 + 1;
    int x33 = 0;
    while (x16[x30] != ',') {
      x33 = x33 * 10 + (int)(x16[x30] - '0');
      x30 = x30 + 1;
    }
    x30 = x30 + 1;
    while (x16[x30] != '\n') x30 = x30 + 1;
    x30 = x30 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x34 = ({
      int x35 = x19;
      while (x3[x35] != -1 && ({
        false;
      })) x35 = x35 + 1 & 255;
      x3[x35];
    });
    //# hash_lookup
    if (x34 != -1) {
      char* x36 = x16 + x32;
      int x37 = x34 * 256;
      int x38 = x37 + x13[x34];
      int x39 = x37;
      while (x39 != x38) {
        int x40 = x12[x39];
        printll(x5[x40]);
        printf(",");
        printf("%d", x7[x40]);
        printf(",");
        printll(x8[x40]);
        printf(",");
        printll(x36);
        printf("%s\n", "");
        x39 = x39 + 1;
      }
    } else {
    }
  }
  close(x14);
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
