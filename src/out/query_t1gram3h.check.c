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
  int x1 = 0;
  int* x2 = (int*)malloc(256 * sizeof(int));
  int x3 = 0;
  while (x3 != 256) {
    x2[x3] = -1;
    x3 = x3 + 1;
  }
  char** x4 = (char**)malloc(65536 * sizeof(char*));
  int* x5 = (int*)malloc(65536 * sizeof(int));
  int x6 = 0;
  int* x7 = (int*)malloc(65536 * sizeof(int));
  int* x8 = (int*)malloc(256 * sizeof(int));
  int x9 = open("src/data/words.csv",0);
  int x10 = fsize(x9);
  char* x11 = mmap(0, x10, PROT_READ, MAP_FILE | MAP_SHARED, x9, 0);
  int x12 = 0;
  while (x11[x12] != ',') x12 = x12 + 1;
  x12 = x12 + 1;
  while (x11[x12] != '\n') x12 = x12 + 1;
  x12 = x12 + 1;
  while (x12 < x10) {
    int x13 = x12;
    while (x11[x12] != ',') x12 = x12 + 1;
    x12 = x12 + 1;
    int x14 = 0;
    while (x11[x12] != '\n') {
      x14 = x14 * 10 + (int)(x11[x12] - '0');
      x12 = x12 + 1;
    }
    x12 = x12 + 1;
    int x15 = x6;
    x4[x15] = x11 + x13;
    x5[x15] = x14;
    x6 = x6 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x16 = ({
      int x17 = 0;
      while (false) x17 = x17 + 1 & 255;
      x2[x17] == -1 ? ({
        int x18 = x1;
        x1 = x1 + 1;
        x2[x17] = x18;
        x8[x18] = 0;
        x18;
      }) : x2[x17];
    });
    //# hash_lookup
    int x19 = x8[x16];
    x7[x16 * 256 + x19] = x15;
    x8[x16] = x19 + 1;
  }
  close(x9);
  int x20 = open("src/data/t1gram.csv",0);
  int x21 = fsize(x20);
  char* x22 = mmap(0, x21, PROT_READ, MAP_FILE | MAP_SHARED, x20, 0);
  int x23 = 0;
  while (x23 < x21) {
    int x24 = x23;
    while (x22[x23] != '\t') x23 = x23 + 1;
    int x25 = x23 + 1;
    x23 = x25;
    char* x26 = x22 + x24;
    while (x22[x23] != '\t') x23 = x23 + 1;
    int x27 = x23 + 1;
    x23 = x27;
    char* x28 = x22 + x25;
    while (x22[x23] != '\t') x23 = x23 + 1;
    int x29 = x23 + 1;
    x23 = x29;
    char* x30 = x22 + x27;
    while (x22[x23] != '\n') x23 = x23 + 1;
    x23 = x23 + 1;
    char* x31 = x22 + x29;
    //# hash_lookup
    // generated code for hash lookup
    int x32 = ({
      int x33 = 0;
      while (false) x33 = x33 + 1 & 255;
      x2[x33];
    });
    //# hash_lookup
    if (x32 != -1) {
      int x34 = x32 * 256;
      int x35 = x34 + x8[x32];
      int x36 = x34;
      while (x36 != x35) {
        int x37 = x7[x36];
        printll(x4[x37]);
        printf(",");
        printf("%d", x5[x37]);
        printf(",");
        printll(x26);
        printf(",");
        printll(x28);
        printf(",");
        printll(x30);
        printf(",");
        printll(x31);
        printf("%s\n", "");
        x36 = x36 + 1;
      }
    }
  }
  close(x20);
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
