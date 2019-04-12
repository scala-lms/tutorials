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
  while (x12 < x10) {
    int x14 = x12;
    while (x11[x12] != ',') x12 = x12 + 1;
    int x15 = x12 - x14;
    x12 = x12 + 1;
    char* x16 = x11+x14;
    int x17 = 0;
    while (x11[x12] != ',') {
      x17 = x17 * 10 + (int)(x11[x12] - '0');
      x12 = x12 + 1;
    }
    x12 = x12 + 1;
    while (x11[x12] != '\n') x12 = x12 + 1;
    x12 = x12 + 1;
    int x18 = ((int)(hash(x16, x15))) & 255;
    int x19 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x20 = x18;
      while (x5[x20] != -1 && ({
        int x21 = x5[x20];
        char* x22 = x1[x21];
        int x23 = x2[x21];
        !(x23 == x15 && ({
          int x24 = 0;
          while (x24 < x23 && x22[x24] == x16[x24]) x24 = x24 + 1;
          x24 == x23;
        }));
      })) x20 = (x20 + 1) & 255;
      (x5[x20] == -1) ? ({
        int x25 = x4;
        x1[x25] = x16;
        x2[x25] = x15;
        x4 = x4 + 1;
        x5[x20] = x25;
        x7[x25] = 0;
        x25;
      }) : x5[x20]
      ;//#hash_lookup;
    });
    x7[x19] = x7[x19] + x17;
  }
  close(x9);
  int x26 = x4;
  int x27 = 0;
  while (x27 != x26) {
    int x28 = x27;
    printll(x1[x28]);
    printf(",");
    printf("%d", x7[x28]);
    printf("%s\n", "");
    x27 = x27 + 1;
  }
}
/*****************************************
End of C Generated Code
*******************************************/
