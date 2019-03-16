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
  int x1 = open("src/data/t.csv",0);
  int x2 = fsize(x1);
  char* x3 = mmap(0, x2, PROT_READ, MAP_FILE | MAP_SHARED, x1, 0);
  int x4 = 0;
  while (x3[x4] != ',') x4 = x4 + 1;
  x4 = x4 + 1;
  int x5 = 0;
  while (x3[x4] != ',') {
    x5 = x5 * 10 + (int)(x3[x4] - '0');
    x4 = x4 + 1;
  }
  x4 = x4 + 1;
  while (x3[x4] != '\n') x4 = x4 + 1;
  x4 = x4 + 1;
  while (x4 < x2) {
    int x6 = x4;
    while (x3[x4] != ',') x4 = x4 + 1;
    int x7 = x4 - x6;
    x4 = x4 + 1;
    char* x8 = x3 + x6;
    int x9 = 0;
    while (x3[x4] != ',') {
      x9 = x9 * 10 + (int)(x3[x4] - '0');
      x4 = x4 + 1;
    }
    x4 = x4 + 1;
    int x10 = x9;
    int x11 = x4;
    while (x3[x4] != '\n') x4 = x4 + 1;
    x4 = x4 + 1;
    char* x12 = x3 + x11;
    int x13 = 0;
    while (x3[x13] != ',') x13 = x13 + 1;
    x13 = x13 + 1;
    int x14 = 0;
    while (x3[x13] != ',') {
      x14 = x14 * 10 + (int)(x3[x13] - '0');
      x13 = x13 + 1;
    }
    x13 = x13 + 1;
    while (x3[x13] != '\n') x13 = x13 + 1;
    x13 = x13 + 1;
    while (x13 < x2) {
      int x15 = x13;
      while (x3[x13] != ',') x13 = x13 + 1;
      int x16 = x13;
      x13 = x13 + 1;
      int x17 = 0;
      while (x3[x13] != ',') {
        x17 = x17 * 10 + (int)(x3[x13] - '0');
        x13 = x13 + 1;
      }
      x13 = x13 + 1;
      while (x3[x13] != '\n') x13 = x13 + 1;
      x13 = x13 + 1;
      if (x7 == x16 - x15 && ({
        char* x18 = x3 + x15;
        int x19 = 0;
        while (x19 < x7 && x8[x19] == x18[x19]) x19 = x19 + 1;
        x19 == x7;
      })) {
        char* x18 = x3 + x15;
        printll(x8);
        printf(",");
        printf("%d", x10);
        printf(",");
        printll(x12);
        printf(",");
        printll(x18);
        printf("%s\n", "");
      } else {
      }
    }
    close(x1);
  }
  close(x1);
}
/*****************************************
End of C Generated Code
*******************************************/
