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
  char** x1 = (char**)malloc(256 * sizeof(char*));;
  int* x2 = (int*)malloc(256 * sizeof(int));;
  int x3 = 0;
  int x4 = 0;
  int* x5 = (int*)malloc(256 * sizeof(int));;
  int x6 = 0;
  while (x6 != 256) {
    x5[x6] = -1;
    x6 = x6 + 1;
  }
  char** x7 = (char**)malloc(65536 * sizeof(char*));;
  int* x8 = (int*)malloc(65536 * sizeof(int));;
  int* x9 = (int*)malloc(65536 * sizeof(int));;
  int x10 = 0;
  int x11 = 0;
  int* x12 = (int*)malloc(65536 * sizeof(int));;
  int* x13 = (int*)malloc(256 * sizeof(int));;
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
  long x19 = 0L * 41L;
  while (x17 < x15) {
    int x20 = x17;
    while (x16[x17] != ',') x17 = x17 + 1;
    int x21 = x17 - x20;
    x17 = x17 + 1;
    char* x22 = x16 + x20;
    int x23 = 0;
    while (x16[x17] != '\n') {
      x23 = x23 * 10 + (int)(x16[x17] - '0');
      x17 = x17 + 1;
    }
    x17 = x17 + 1;
    int x24 = x11;
    x7[x24] = x22;
    x8[x24] = x21;
    x9[x24] = x23;
    x11 = x11 + 1;
    int x25 = ((int)(x19 + hash(x22, x21))) & 255;
    int x26 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x27 = x25;
      while (x5[x27] != -1 && ({
        int x28 = x5[x27];
        char* x29 = x1[x28];
        int x30 = x2[x28];
        !(x30 == x21 && ({
          int x31 = 0;
          while (x31 < x30 && x29[x31] == x22[x31]) x31 = x31 + 1;
          x31 == x30;
        }));
      })) x27 = (x27 + 1) & 255;
      (x5[x27] == -1) ? ({
        int x32 = x4;
        x1[x32] = x22;
        x2[x32] = x21;
        x4 = x4 + 1;
        x5[x27] = x32;
        x13[x32] = 0;
        x32;
      }) : x5[x27]
      ;//#hash_lookup;
    });
    int x33 = x13[x26];
    x12[x26 * 256 + x33] = x24;
    x13[x26] = x33 + 1;
  }
  close(x14);
  int x34 = open(x0,0);
  int x35 = fsize(x34);
  char* x36 = mmap(0, x35, PROT_READ, MAP_FILE | MAP_SHARED, x34, 0);
  int x37 = 0;
  while (x37 < x35) {
    int x38 = x37;
    while (x36[x37] != '\t') x37 = x37 + 1;
    int x39 = x37 - x38;
    x37 = x37 + 1;
    char* x40 = x36 + x38;
    int x41 = x37;
    while (x36[x37] != '\t') x37 = x37 + 1;
    x37 = x37 + 1;
    int x42 = x37;
    while (x36[x37] != '\t') x37 = x37 + 1;
    x37 = x37 + 1;
    int x43 = x37;
    while (x36[x37] != '\n') x37 = x37 + 1;
    x37 = x37 + 1;
    int x44 = ((int)(x19 + hash(x40, x39))) & 255;
    int x45 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x46 = x44;
      while (x5[x46] != -1 && ({
        int x47 = x5[x46];
        char* x48 = x1[x47];
        int x49 = x2[x47];
        !(x49 == x39 && ({
          int x50 = 0;
          while (x50 < x49 && x48[x50] == x40[x50]) x50 = x50 + 1;
          x50 == x49;
        }));
      })) x46 = (x46 + 1) & 255;
      x5[x46]
      ;//#hash_lookup;
    });
    if (x45 != -1) {
      char* x51 = x36 + x41;
      char* x52 = x36 + x42;
      char* x53 = x36 + x43;
      int x54 = x45 * 256;
      int x55 = x54 + x13[x45];
      int x56 = x54;
      while (x56 != x55) {
        int x57 = x12[x56];
        printll(x7[x57]);
        printf(",");
        printf("%d", x9[x57]);
        printf(",");
        printll(x40);
        printf(",");
        printll(x51);
        printf(",");
        printll(x52);
        printf(",");
        printll(x53);
        printf("%s\n", "");
        x56 = x56 + 1;
      }
    } else {
    }
  }
  close(x34);
}
/*****************************************
End of C Generated Code
*******************************************/
