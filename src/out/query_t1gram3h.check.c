#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
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
long hash(char *str0, int len)
{
  unsigned char* str = (unsigned char*)str0;
  unsigned long hash = 5381;
  int c;
  while ((c = *str++) && len--)
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char* x0) {
  printf("%s\n", "Word,Value,Phrase,Year,MatchCount,VolumeCount");
  int x1 = 0;
  int x2 = 0;
  int* x3 = (int*)malloc(256 * sizeof(int));;
  int x4 = 0;
  while (x4 != 256) {
    x3[x4] = -1;
    x4 = x4 + 1;
  }
  char** x5 = (char**)malloc(65536 * sizeof(char*));;
  int* x6 = (int*)malloc(65536 * sizeof(int));;
  int* x7 = (int*)malloc(65536 * sizeof(int));;
  int x8 = 0;
  int x9 = 0;
  int* x10 = (int*)malloc(65536 * sizeof(int));;
  int* x11 = (int*)malloc(256 * sizeof(int));;
  int x12 = open("src/data/words.csv",0);
  int x13 = fsize(x12);
  char* x14 = mmap(0, x13, PROT_READ, MAP_FILE | MAP_SHARED, x12, 0);
  int x15 = 0;
  while (x14[x15] != ',') x15 = x15 + 1;
  x15 = x15 + 1;
  int x16 = 0;
  while (x14[x15] != '\n') {
    x16 = x16 * 10 + (int)(x14[x15] - '0');
    x15 = x15 + 1;
  }
  x15 = x15 + 1;
  int x17 = ((int)0L) & 255;
  bool x18 = !true;
  while (x15 < x13) {
    int x19 = x15;
    while (x14[x15] != ',') x15 = x15 + 1;
    int x20 = x15;
    x15 = x15 + 1;
    int x21 = 0;
    while (x14[x15] != '\n') {
      x21 = x21 * 10 + (int)(x14[x15] - '0');
      x15 = x15 + 1;
    }
    x15 = x15 + 1;
    int x22 = x9;
    x5[x22] = x14 + x19;
    x6[x22] = x20 - x19;
    x7[x22] = x21;
    x9 = x9 + 1;
    int x23 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x24 = x17;
      while (x3[x24] != -1 && x18) x24 = (x24 + 1) & 255;
      (x3[x24] == -1) ? ({
        int x25 = x2;
        x2 = x2 + 1;
        x3[x24] = x25;
        x11[x25] = 0;
        x25;
      }) : x3[x24]
      ;//#hash_lookup;
    });
    int x26 = x11[x23];
    x10[x23 * 256 + x26] = x22;
    x11[x23] = x26 + 1;
  }
  close(x12);
  int x27 = open(x0,0);
  int x28 = fsize(x27);
  char* x29 = mmap(0, x28, PROT_READ, MAP_FILE | MAP_SHARED, x27, 0);
  int x30 = 0;
  while (x30 < x28) {
    int x31 = x30;
    while (x29[x30] != '\t') x30 = x30 + 1;
    x30 = x30 + 1;
    int x32 = x30;
    while (x29[x30] != '\t') x30 = x30 + 1;
    x30 = x30 + 1;
    int x33 = x30;
    while (x29[x30] != '\t') x30 = x30 + 1;
    x30 = x30 + 1;
    int x34 = x30;
    while (x29[x30] != '\n') x30 = x30 + 1;
    x30 = x30 + 1;
    int x35 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x36 = x17;
      while (x3[x36] != -1 && x18) x36 = (x36 + 1) & 255;
      x3[x36]
      ;//#hash_lookup;
    });
    if (x35 != -1) {
      char* x37 = x29 + x31;
      char* x38 = x29 + x32;
      char* x39 = x29 + x33;
      char* x40 = x29 + x34;
      int x41 = x35 * 256;
      int x42 = x41 + x11[x35];
      int x43 = x41;
      while (x43 != x42) {
        int x44 = x10[x43];
        printll(x5[x44]);
        printf(",");
        printf("%d", x7[x44]);
        printf(",");
        printll(x37);
        printf(",");
        printll(x38);
        printf(",");
        printll(x39);
        printf(",");
        printll(x40);
        printf("%s\n", "");
        x43 = x43 + 1;
      }
    } else {
    }
  }
  close(x27);
}
/*****************************************
End of C Generated Code
*******************************************/
