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
  printf("%s\n", "Name,Value,Flag,Name1");
  int x1 = 0;
  int x2 = 0;
  int* x3 = (int*)malloc(256 * sizeof(int));;
  int x4 = 0;
  while (x4 != 256) ({
    x3[x4] = -1;;
    x4 = x4 + 1;;
  });
  char** x5 = (char**)malloc(65536 * sizeof(char*));;
  int* x6 = (int*)malloc(65536 * sizeof(int));;
  int* x7 = (int*)malloc(65536 * sizeof(int));;
  char** x8 = (char**)malloc(65536 * sizeof(char*));;
  int* x9 = (int*)malloc(65536 * sizeof(int));;
  int x10 = 0;
  int x11 = 0;
  int* x12 = (int*)malloc(65536 * sizeof(int));;
  int* x13 = (int*)malloc(256 * sizeof(int));;
  int x14 = open("src/data/t.csv",0);
  int x15 = fsize(x14);
  char* x16 = mmap(0, x15, PROT_READ, MAP_FILE | MAP_SHARED, x14, 0);
  int x17 = 0;
  while (x16[x17] != ',') x17 = x17 + 1;;
  x17 = x17 + 1;
  int x18 = 0;
  while (x16[x17] != ',') ({
    x18 = x18 * 10 + (int)(x16[x17] - '0');;
    x17 = x17 + 1;;
  });
  x17 = x17 + 1;
  while (x16[x17] != '\n') x17 = x17 + 1;;
  x17 = x17 + 1;
  int x19 = ((int)0L) & 255;
  bool x20 = !true;
  while (x17 < x15) ({
    int x21 = x17;;
    while (x16[x17] != ',') x17 = x17 + 1;;;
    int x22 = x17;;
    x17 = x17 + 1;;
    int x23 = 0;;
    while (x16[x17] != ',') ({
      x23 = x23 * 10 + (int)(x16[x17] - '0');;
      x17 = x17 + 1;;
    });;
    x17 = x17 + 1;;
    int x24 = x17;;
    while (x16[x17] != '\n') x17 = x17 + 1;;;
    int x25 = x17;;
    x17 = x17 + 1;;
    int x26 = x11;;
    x5[x26] = x16 + x21;;
    x6[x26] = x22 - x21;;
    x7[x26] = x23;;
    x8[x26] = x16 + x24;;
    x9[x26] = x25 - x24;;
    x11 = x11 + 1;;
    int x27 = ({
      //#hash_lookup;
      // generated code for hash lookup;
      int x28 = x19;;
      while (x3[x28] != -1 && x20) x28 = (x28 + 1) & 255;;;
      ((x3[x28] == -1) ? ({
        int x29 = x2;;
        x2 = x2 + 1;;
        x3[x28] = x29;;
        x13[x29] = 0;;
        x29;
      }) : x3[x28]);
      //#hash_lookup;
    });;
    int x30 = x13[x27];;
    x12[x27 * 256 + x30] = x26;;
    x13[x27] = x30 + 1;;
  });
  close(x14);
  int x31 = 0;
  while (x16[x31] != ',') x31 = x31 + 1;;
  x31 = x31 + 1;
  int x32 = 0;
  while (x16[x31] != ',') ({
    x32 = x32 * 10 + (int)(x16[x31] - '0');;
    x31 = x31 + 1;;
  });
  x31 = x31 + 1;
  while (x16[x31] != '\n') x31 = x31 + 1;;
  x31 = x31 + 1;
  while (x31 < x15) ({
    int x33 = x31;;
    while (x16[x31] != ',') x31 = x31 + 1;;;
    x31 = x31 + 1;;
    int x34 = 0;;
    while (x16[x31] != ',') ({
      x34 = x34 * 10 + (int)(x16[x31] - '0');;
      x31 = x31 + 1;;
    });;
    x31 = x31 + 1;;
    while (x16[x31] != '\n') x31 = x31 + 1;;;
    x31 = x31 + 1;;
    int x35 = ({
      //#hash_lookup;
      // generated code for hash lookup;
      int x36 = x19;;
      while (x3[x36] != -1 && x20) x36 = (x36 + 1) & 255;;;
      x3[x36];
      //#hash_lookup;
    });;
    ((x35 != -1) ? ({
      char* x37 = x16 + x33;;
      int x38 = x35 * 256;;
      int x39 = x38 + x13[x35];;
      int x40 = x38;;
      while (x40 != x39) ({
        int x41 = x12[x40];;
        printll(x5[x41]);;
        printf(",");;
        printf("%d", x7[x41]);;
        printf(",");;
        printll(x8[x41]);;
        printf(",");;
        printll(x37);;
        printf("%s\n", "");;
        x40 = x40 + 1;;
      });;
    }) : ({
      ;
    }));;
  });
  close(x14);
}
/*****************************************
End of C Generated Code
*******************************************/
