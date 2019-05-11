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
  printf("%s\n", "Name,Value");
  char** x1 = (char**)malloc(256 * sizeof(char*));
  int* x2 = (int*)malloc(256 * sizeof(int));
  int x3 = 0;
  int* x4 = (int*)malloc(256 * sizeof(int));
  int x5 = 0;
  while (x5 != 256) {
    x4[x5] = -1;
    x5 = x5 + 1;
  }
  int* x6 = (int*)malloc(256 * sizeof(int));
  int x7 = open("src/data/t.csv",0);
  int x8 = fsize(x7);
  char* x9 = mmap(0, x8, PROT_READ, MAP_FILE | MAP_SHARED, x7, 0);
  int x10 = 0;
  while (x9[x10] != ',') x10 = x10 + 1;
  x10 = x10 + 1;
  while (x9[x10] != ',') x10 = x10 + 1;
  x10 = x10 + 1;
  while (x9[x10] != '\n') x10 = x10 + 1;
  x10 = x10 + 1;
  while (x10 < x8) {
    int x11 = x10;
    while (x9[x10] != ',') x10 = x10 + 1;
    int x12 = x10 - x11;
    x10 = x10 + 1;
    char* x13 = x9 + x11;
    int x14 = 0;
    while (x9[x10] != ',') {
      x14 = x14 * 10 + (x9[x10] - '0');
      x10 = x10 + 1;
    }
    x10 = x10 + 1;
    while (x9[x10] != '\n') x10 = x10 + 1;
    x10 = x10 + 1;
    int x15 = (int)hash(x13, x12) & 255;
    //# hash_lookup
    // generated code for hash lookup
    int x16 = ({
      int x17 = x15;
      while (x4[x17] != -1 && ({
        int x18 = x4[x17];
        int x19 = x2[x18];
        !(x19 == x12 && ({
          char* x20 = x1[x18];
          int x21 = 0;
          while (x21 < x19 && x20[x21] == x13[x21]) x21 = x21 + 1;
          x21 == x19;
        }));
      })) x17 = x17 + 1 & 255;
      x4[x17] == -1 ? ({
        int x22 = x3;
        x1[x22] = x13;
        x2[x22] = x12;
        x3 = x3 + 1;
        x4[x17] = x22;
        x6[x22] = 0;
        x22;
      }) : x4[x17];
    });
    //# hash_lookup
    x6[x16] = x6[x16] + x14;
  }
  close(x7);
  int x23 = x3;
  int x24 = 0;
  while (x24 != x23) {
    int x25 = x24;
    printll(x1[x25]);
    printf(",");
    printf("%d", x6[x25]);
    printf("%s\n", "");
    x24 = x24 + 1;
  }
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
