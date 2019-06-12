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
  printf("%s\n", "Phrase,Year,MatchCount,VolumeCount");
  int x1 = open("src/data/t1gram.csv",0);
  int x2 = fsize(x1);
  char* x3 = mmap(0, x2, PROT_READ, MAP_FILE | MAP_SHARED, x1, 0);
  int x4 = 0;
  while (x4 < x2) {
    int x5 = x4;
    while (x3[x4] != '\t') x4 = x4 + 1;
    int x6 = x4 - x5;
    int x7 = x4 + 1;
    x4 = x7;
    char* x8 = x3 + x5;
    while (x3[x4] != '\t') x4 = x4 + 1;
    int x9 = x4 + 1;
    x4 = x9;
    char* x10 = x3 + x7;
    while (x3[x4] != '\t') x4 = x4 + 1;
    int x11 = x4 + 1;
    x4 = x11;
    char* x12 = x3 + x9;
    while (x3[x4] != '\n') x4 = x4 + 1;
    x4 = x4 + 1;
    char* x13 = x3 + x11;
    if (x6 == 12 && ({
      int x14 = 0;
      while (x14 < x6 && x8[x14] == "Auswanderung"[x14]) x14 = x14 + 1;
      x14 == x6;
    })) {
      printll(x8);
      printf(",");
      printll(x10);
      printf(",");
      printll(x12);
      printf(",");
      printll(x13);
      printf("%s\n", "");
    }
  }
  close(x1);
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
