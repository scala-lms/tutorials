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
  printf("%s\n", "Name,Value");
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
  int* x7 = (int*)malloc(256 * sizeof(int));;
  int x8 = 0;
  int x9 = open("src/data/t.csv",0);
  int x10 = fsize(x9);
  char* x11 = mmap(0, x10, PROT_READ, MAP_FILE | MAP_SHARED, x9, 0);
  int x12 = 0;
  while (x11[x12] != ',') x12 = x12 + 1;
  x12 = x12 + 1;
  int x13 = 0;
  while (x11[x12] != ',') {
    x13 = x13 * 10 + (int)(x11[x12] - '0');
    x12 = x12 + 1;
  }
  x12 = x12 + 1;
  while (x11[x12] != '\n') x12 = x12 + 1;
  x12 = x12 + 1;
  long x14 = 0L * 41L;
  while (x12 < x10) {
    int x15 = x12;
    while (x11[x12] != ',') x12 = x12 + 1;
    int x16 = x12 - x15;
    x12 = x12 + 1;
    char* x17 = x11 + x15;
    int x18 = 0;
    while (x11[x12] != ',') {
      x18 = x18 * 10 + (int)(x11[x12] - '0');
      x12 = x12 + 1;
    }
    x12 = x12 + 1;
    while (x11[x12] != '\n') x12 = x12 + 1;
    x12 = x12 + 1;
    int x19 = ((int)(x14 + hash(x17, x16))) & 255;
    int x20 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x21 = x19;
      while (x5[x21] != -1 && ({
        int x22 = x5[x21];
        char* x23 = x1[x22];
        int x24 = x2[x22];
        !(x24 == x16 && ({
          int x25 = 0;
          while (x25 < x24 && x23[x25] == x17[x25]) x25 = x25 + 1;
          x25 == x24;
        }));
      })) x21 = (x21 + 1) & 255;
      (x5[x21] == -1) ? ({
        int x26 = x4;
        x1[x26] = x17;
        x2[x26] = x16;
        x4 = x4 + 1;
        x5[x21] = x26;
        x7[x26] = 0;
        x26;
      }) : x5[x21]
      ;//#hash_lookup;
    });
    x7[x20] = x7[x20] + x18;
  }
  close(x9);
  int x27 = x4;
  int x28 = 0;
  while (x28 != x27) {
    int x29 = x28;
    printll(x1[x29]);
    printf(",");
    printf("%d", x7[x29]);
    printf("%s\n", "");
    x28 = x28 + 1;
  }
}
/*****************************************
End of C Generated Code
*******************************************/
