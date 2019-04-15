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
  printf("%s\n", "Name,Value,Flag,Name");
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
  char** x10 = (char**)malloc(65536 * sizeof(char*));
  int* x11 = (int*)malloc(65536 * sizeof(int));
  int x12 = 0;
  int x13 = 0;
  int* x14 = (int*)malloc(65536 * sizeof(int));
  int* x15 = (int*)malloc(256 * sizeof(int));
  int x16 = open("src/data/t.csv",0);
  int x17 = fsize(x16);
  char* x18 = mmap(0, x17, PROT_READ, MAP_FILE | MAP_SHARED, x16, 0);
  int x19 = 0;
  while (x18[x19] != ',') x19 = x19 + 1;
  x19 = x19 + 1;
  int x20 = 0;
  while (x18[x19] != ',') {
    x20 = x20 * 10 + (int)(x18[x19] - '0');
    x19 = x19 + 1;
  }
  x19 = x19 + 1;
  while (x18[x19] != '\n') x19 = x19 + 1;
  x19 = x19 + 1;
  while (x19 < x17) {
    int x21 = x19;
    while (x18[x19] != ',') x19 = x19 + 1;
    int x22 = x19 - x21;
    x19 = x19 + 1;
    char* x23 = x18 + x21;
    int x24 = 0;
    while (x18[x19] != ',') {
      x24 = x24 * 10 + (int)(x18[x19] - '0');
      x19 = x19 + 1;
    }
    x19 = x19 + 1;
    int x25 = x19;
    while (x18[x19] != '\n') x19 = x19 + 1;
    int x26 = x19;
    x19 = x19 + 1;
    int x27 = x13;
    x7[x27] = x23;
    x8[x27] = x22;
    x9[x27] = x24;
    x10[x27] = x18 + x25;
    x11[x27] = x26 - x25;
    x13 = x13 + 1;
    int x28 = (int)hash(x23, x22) & 255;
    int x29 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x30 = x28;
      while (x5[x30] != -1 && ({
        int x31 = x5[x30];
        char* x32 = x1[x31];
        int x33 = x2[x31];
        !(x33 == x22 && ({
          int x34 = 0;
          while (x34 < x33 && x32[x34] == x23[x34]) x34 = x34 + 1;
          x34 == x33;
        }));
      })) x30 = x30 + 1 & 255;
      (x5[x30] == -1) ? ({
        int x35 = x4;
        x1[x35] = x23;
        x2[x35] = x22;
        x4 = x4 + 1;
        x5[x30] = x35;
        x15[x35] = 0;
        x35;
      }) : x5[x30]
      ;//#hash_lookup;
    });
    int x36 = x15[x29];
    x14[x29 * 256 + x36] = x27;
    x15[x29] = x36 + 1;
  }
  close(x16);
  int x37 = 0;
  while (x18[x37] != ',') x37 = x37 + 1;
  x37 = x37 + 1;
  int x38 = 0;
  while (x18[x37] != ',') {
    x38 = x38 * 10 + (int)(x18[x37] - '0');
    x37 = x37 + 1;
  }
  x37 = x37 + 1;
  while (x18[x37] != '\n') x37 = x37 + 1;
  x37 = x37 + 1;
  while (x37 < x17) {
    int x39 = x37;
    while (x18[x37] != ',') x37 = x37 + 1;
    int x40 = x37 - x39;
    x37 = x37 + 1;
    char* x41 = x18 + x39;
    int x42 = 0;
    while (x18[x37] != ',') {
      x42 = x42 * 10 + (int)(x18[x37] - '0');
      x37 = x37 + 1;
    }
    x37 = x37 + 1;
    while (x18[x37] != '\n') x37 = x37 + 1;
    x37 = x37 + 1;
    int x43 = (int)hash(x41, x40) & 255;
    int x44 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x45 = x43;
      while (x5[x45] != -1 && ({
        int x46 = x5[x45];
        char* x47 = x1[x46];
        int x48 = x2[x46];
        !(x48 == x40 && ({
          int x49 = 0;
          while (x49 < x48 && x47[x49] == x41[x49]) x49 = x49 + 1;
          x49 == x48;
        }));
      })) x45 = x45 + 1 & 255;
      x5[x45]
      ;//#hash_lookup;
    });
    if (x44 != -1) {
      int x50 = x44 * 256;
      int x51 = x50 + x15[x44];
      int x52 = x50;
      while (x52 != x51) {
        int x53 = x14[x52];
        printll(x7[x53]);
        printf(",");
        printf("%d", x9[x53]);
        printf(",");
        printll(x10[x53]);
        printf(",");
        printll(x41);
        printf("%s\n", "");
        x52 = x52 + 1;
      }
    } else {
    }
  }
  close(x16);
}
/*****************************************
End of C Generated Code
*******************************************/
