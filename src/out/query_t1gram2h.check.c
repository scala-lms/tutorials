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
  int x8 = 0;
  int* x9 = (int*)malloc(65536 * sizeof(int));
  int* x10 = (int*)malloc(256 * sizeof(int));
  int x11 = open("src/data/words.csv",0);
  int x12 = fsize(x11);
  char* x13 = mmap(0, x12, PROT_READ, MAP_FILE | MAP_SHARED, x11, 0);
  int x14 = 0;
  while (x13[x14] != ',') x14 = x14 + 1;
  x14 = x14 + 1;
  while (x13[x14] != '\n') x14 = x14 + 1;
  x14 = x14 + 1;
  while (x14 < x12) {
    int x15 = x14;
    while (x13[x14] != ',') x14 = x14 + 1;
    int x16 = x14 - x15;
    x14 = x14 + 1;
    char* x17 = x13 + x15;
    int x18 = 0;
    while (x13[x14] != '\n') {
      x18 = x18 * 10 + (x13[x14] - '0');
      x14 = x14 + 1;
    }
    x14 = x14 + 1;
    int x19 = x8;
    x6[x19] = x17;
    x7[x19] = x18;
    x8 = x8 + 1;
    int x20 = (int)hash(x17, x16) & 255;
    //# hash_lookup
    // generated code for hash lookup
    int x21 = ({
      int x22 = x20;
      while (x4[x22] != -1 && ({
        int x23 = x4[x22];
        int x24 = x2[x23];
        !(x24 == x16 && ({
          char* x25 = x1[x23];
          int x26 = 0;
          while (x26 < x24 && x25[x26] == x17[x26]) x26 = x26 + 1;
          x26 == x24;
        }));
      })) x22 = x22 + 1 & 255;
      x4[x22] == -1 ? ({
        int x27 = x3;
        x1[x27] = x17;
        x2[x27] = x16;
        x3 = x3 + 1;
        x4[x22] = x27;
        x10[x27] = 0;
        x27;
      }) : x4[x22];
    });
    //# hash_lookup
    int x28 = x10[x21];
    x9[x21 * 256 + x28] = x19;
    x10[x21] = x28 + 1;
  }
  close(x11);
  int x29 = open("src/data/t1gram.csv",0);
  int x30 = fsize(x29);
  char* x31 = mmap(0, x30, PROT_READ, MAP_FILE | MAP_SHARED, x29, 0);
  int x32 = 0;
  while (x32 < x30) {
    int x33 = x32;
    while (x31[x32] != '\t') x32 = x32 + 1;
    int x34 = x32 - x33;
    x32 = x32 + 1;
    char* x35 = x31 + x33;
    int x36 = x32;
    while (x31[x32] != '\t') x32 = x32 + 1;
    x32 = x32 + 1;
    int x37 = x32;
    while (x31[x32] != '\t') x32 = x32 + 1;
    x32 = x32 + 1;
    int x38 = x32;
    while (x31[x32] != '\n') x32 = x32 + 1;
    x32 = x32 + 1;
    int x39 = (int)hash(x35, x34) & 255;
    //# hash_lookup
    // generated code for hash lookup
    int x40 = ({
      int x41 = x39;
      while (x4[x41] != -1 && ({
        int x42 = x4[x41];
        int x43 = x2[x42];
        !(x43 == x34 && ({
          char* x44 = x1[x42];
          int x45 = 0;
          while (x45 < x43 && x44[x45] == x35[x45]) x45 = x45 + 1;
          x45 == x43;
        }));
      })) x41 = x41 + 1 & 255;
      x4[x41];
    });
    //# hash_lookup
    if (x40 != -1) {
      char* x46 = x31 + x36;
      char* x47 = x31 + x37;
      char* x48 = x31 + x38;
      int x49 = x40 * 256;
      int x50 = x49 + x10[x40];
      int x51 = x49;
      while (x51 != x50) {
        int x52 = x9[x51];
        printll(x6[x52]);
        printf(",");
        printf("%d", x7[x52]);
        printf(",");
        printll(x35);
        printf(",");
        printll(x46);
        printf(",");
        printll(x47);
        printf(",");
        printll(x48);
        printf("%s\n", "");
        x51 = x51 + 1;
      }
    }
  }
  close(x29);
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
