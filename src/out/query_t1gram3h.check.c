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
  int x13 = 0 & 255;
  while (x12 < x10) {
    int x14 = x12;
    while (x11[x12] != ',') x12 = x12 + 1;
    x12 = x12 + 1;
    int x15 = 0;
    while (x11[x12] != '\n') {
      x15 = x15 * 10 + (x11[x12] - '0');
      x12 = x12 + 1;
    }
    x12 = x12 + 1;
    int x16 = x6;
    x4[x16] = x11 + x14;
    x5[x16] = x15;
    x6 = x6 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x17 = ({
      int x18 = x13;
      while (x2[x18] != -1 && false) x18 = x18 + 1 & 255;
      x2[x18] == -1 ? ({
        int x19 = x1;
        x1 = x1 + 1;
        x2[x18] = x19;
        x8[x19] = 0;
        x19;
      }) : x2[x18];
    });
    //# hash_lookup
    int x20 = x8[x17];
    x7[x17 * 256 + x20] = x16;
    x8[x17] = x20 + 1;
  }
  close(x9);
  int x21 = open("src/data/t1gram.csv",0);
  int x22 = fsize(x21);
  char* x23 = mmap(0, x22, PROT_READ, MAP_FILE | MAP_SHARED, x21, 0);
  int x24 = 0;
  while (x24 < x22) {
    int x25 = x24;
    while (x23[x24] != '\t') x24 = x24 + 1;
    x24 = x24 + 1;
    int x26 = x24;
    while (x23[x24] != '\t') x24 = x24 + 1;
    x24 = x24 + 1;
    int x27 = x24;
    while (x23[x24] != '\t') x24 = x24 + 1;
    x24 = x24 + 1;
    int x28 = x24;
    while (x23[x24] != '\n') x24 = x24 + 1;
    x24 = x24 + 1;
    //# hash_lookup
    // generated code for hash lookup
    int x29 = ({
      int x30 = x13;
      while (x2[x30] != -1 && false) x30 = x30 + 1 & 255;
      x2[x30];
    });
    //# hash_lookup
    if (x29 != -1) {
      char* x31 = x23 + x25;
      char* x32 = x23 + x26;
      char* x33 = x23 + x27;
      char* x34 = x23 + x28;
      int x35 = x29 * 256;
      int x36 = x35 + x8[x29];
      int x37 = x35;
      while (x37 != x36) {
        int x38 = x7[x37];
        printll(x4[x38]);
        printf(",");
        printf("%d", x5[x38]);
        printf(",");
        printll(x31);
        printf(",");
        printll(x32);
        printf(",");
        printll(x33);
        printf(",");
        printll(x34);
        printf("%s\n", "");
        x37 = x37 + 1;
      }
    }
  }
  close(x21);
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
