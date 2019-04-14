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
  int* x3 = (int*)malloc(256 * sizeof(int));
  int x4 = 0;
  while (x4 != 256) {
    x3[x4] = -1;
    x4 = x4 + 1;
  }
  char** x5 = (char**)malloc(65536 * sizeof(char*));
  int* x6 = (int*)malloc(65536 * sizeof(int));
  int* x7 = (int*)malloc(65536 * sizeof(int));
  int x8 = 0;
  int x9 = 0;
  int* x10 = (int*)malloc(65536 * sizeof(int));
  int* x11 = (int*)malloc(256 * sizeof(int));
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
  while (x15 < x13) {
    int x18 = x15;
    while (x14[x15] != ',') x15 = x15 + 1;
    int x19 = x15;
    x15 = x15 + 1;
    int x20 = 0;
    while (x14[x15] != '\n') {
      x20 = x20 * 10 + (int)(x14[x15] - '0');
      x15 = x15 + 1;
    }
    x15 = x15 + 1;
    int x21 = x9;
    x5[x21] = x14 + x18;
    x6[x21] = x19 - x18;
    x7[x21] = x20;
    x9 = x9 + 1;
    int x22 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x23 = x17;
      while (x3[x23] != -1 && false) x23 = (x23 + 1) & 255;
      (x3[x23] == -1) ? ({
        int x24 = x2;
        x2 = x2 + 1;
        x3[x23] = x24;
        x11[x24] = 0;
        x24;
      }) : x3[x23]
      ;//#hash_lookup;
    });
    int x25 = x11[x22];
    x10[x22 * 256 + x25] = x21;
    x11[x22] = x25 + 1;
  }
  close(x12);
  int x26 = open("src/data/t1gram.csv",0);
  int x27 = fsize(x26);
  char* x28 = mmap(0, x27, PROT_READ, MAP_FILE | MAP_SHARED, x26, 0);
  int x29 = 0;
  while (x29 < x27) {
    int x30 = x29;
    while (x28[x29] != '\t') x29 = x29 + 1;
    x29 = x29 + 1;
    int x31 = x29;
    while (x28[x29] != '\t') x29 = x29 + 1;
    x29 = x29 + 1;
    int x32 = x29;
    while (x28[x29] != '\t') x29 = x29 + 1;
    x29 = x29 + 1;
    int x33 = x29;
    while (x28[x29] != '\n') x29 = x29 + 1;
    x29 = x29 + 1;
    int x34 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x35 = x17;
      while (x3[x35] != -1 && false) x35 = (x35 + 1) & 255;
      x3[x35]
      ;//#hash_lookup;
    });
    if (x34 != -1) {
      char* x36 = x28 + x30;
      char* x37 = x28 + x31;
      char* x38 = x28 + x32;
      char* x39 = x28 + x33;
      int x40 = x34 * 256;
      int x41 = x40 + x11[x34];
      int x42 = x40;
      while (x42 != x41) {
        int x43 = x10[x42];
        printll(x5[x43]);
        printf(",");
        printf("%d", x7[x43]);
        printf(",");
        printll(x36);
        printf(",");
        printll(x37);
        printf(",");
        printll(x38);
        printf(",");
        printll(x39);
        printf("%s\n", "");
        x42 = x42 + 1;
      }
    } else {
    }
  }
  close(x26);
}
/*****************************************
End of C Generated Code
*******************************************/
