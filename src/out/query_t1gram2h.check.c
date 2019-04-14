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
  printf("%s\n", "Word,Value,Word,Year,MatchCount,VolumeCount");
  char** x1 = (char**)malloc(256 * sizeof(char*));
  int* x2 = (int*)malloc(256 * sizeof(int));
  int x3 = 0;
  int x4 = 0;
  int* x5 = (int*)malloc(256 * sizeof(int));
  int x6 = 0;
  while (x6 != 256) {
    x5[x6] = -1;
    x6 = x6 + 1;
  }
  char** x7 = (char**)malloc(65536 * sizeof(char*));
  int* x8 = (int*)malloc(65536 * sizeof(int));
  int* x9 = (int*)malloc(65536 * sizeof(int));
  int x10 = 0;
  int x11 = 0;
  int* x12 = (int*)malloc(65536 * sizeof(int));
  int* x13 = (int*)malloc(256 * sizeof(int));
  int x14 = open("src/data/words.csv",0);
  int x15 = fsize(x14);
  char* x16 = mmap(0, x15, PROT_READ, MAP_FILE | MAP_SHARED, x14, 0);
  int x17 = 0;
  while (x16[x17] != ',') x17 = x17 + 1;
  x17 = x17 + 1;
  int x18 = 0;
  while (x16[x17] != '\n') {
    x18 = x18 * 10 + (int)(x16[x17] - '0');
    x17 = x17 + 1;
  }
  x17 = x17 + 1;
  while (x17 < x15) {
    int x19 = x17;
    while (x16[x17] != ',') x17 = x17 + 1;
    int x20 = x17 - x19;
    x17 = x17 + 1;
    char* x21 = x16 + x19;
    int x22 = 0;
    while (x16[x17] != '\n') {
      x22 = x22 * 10 + (int)(x16[x17] - '0');
      x17 = x17 + 1;
    }
    x17 = x17 + 1;
    int x23 = x11;
    x7[x23] = x21;
    x8[x23] = x20;
    x9[x23] = x22;
    x11 = x11 + 1;
    int x24 = ((int)(hash(x21, x20))) & 255;
    int x25 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x26 = x24;
      while (x5[x26] != -1 && ({
        int x27 = x5[x26];
        char* x28 = x1[x27];
        int x29 = x2[x27];
        !(x29 == x20 && ({
          int x30 = 0;
          while (x30 < x29 && x28[x30] == x21[x30]) x30 = x30 + 1;
          x30 == x29;
        }));
      })) x26 = (x26 + 1) & 255;
      (x5[x26] == -1) ? ({
        int x31 = x4;
        x1[x31] = x21;
        x2[x31] = x20;
        x4 = x4 + 1;
        x5[x26] = x31;
        x13[x31] = 0;
        x31;
      }) : x5[x26]
      ;//#hash_lookup;
    });
    int x32 = x13[x25];
    x12[x25 * 256 + x32] = x23;
    x13[x25] = x32 + 1;
  }
  close(x14);
  int x33 = open("src/data/t1gram.csv",0);
  int x34 = fsize(x33);
  char* x35 = mmap(0, x34, PROT_READ, MAP_FILE | MAP_SHARED, x33, 0);
  int x36 = 0;
  while (x36 < x34) {
    int x37 = x36;
    while (x35[x36] != '\t') x36 = x36 + 1;
    int x38 = x36 - x37;
    x36 = x36 + 1;
    char* x39 = x35 + x37;
    int x40 = x36;
    while (x35[x36] != '\t') x36 = x36 + 1;
    x36 = x36 + 1;
    int x41 = x36;
    while (x35[x36] != '\t') x36 = x36 + 1;
    x36 = x36 + 1;
    int x42 = x36;
    while (x35[x36] != '\n') x36 = x36 + 1;
    x36 = x36 + 1;
    int x43 = ((int)(hash(x39, x38))) & 255;
    int x44 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x45 = x43;
      while (x5[x45] != -1 && ({
        int x46 = x5[x45];
        char* x47 = x1[x46];
        int x48 = x2[x46];
        !(x48 == x38 && ({
          int x49 = 0;
          while (x49 < x48 && x47[x49] == x39[x49]) x49 = x49 + 1;
          x49 == x48;
        }));
      })) x45 = (x45 + 1) & 255;
      x5[x45]
      ;//#hash_lookup;
    });
    if (x44 != -1) {
      char* x50 = x35 + x40;
      char* x51 = x35 + x41;
      char* x52 = x35 + x42;
      int x53 = x44 * 256;
      int x54 = x53 + x13[x44];
      int x55 = x53;
      while (x55 != x54) {
        int x56 = x12[x55];
        printll(x7[x56]);
        printf(",");
        printf("%d", x9[x56]);
        printf(",");
        printll(x39);
        printf(",");
        printll(x50);
        printf(",");
        printll(x51);
        printf(",");
        printll(x52);
        printf("%s\n", "");
        x55 = x55 + 1;
      }
    } else {
    }
  }
  close(x33);
}
/*****************************************
End of C Generated Code
*******************************************/
