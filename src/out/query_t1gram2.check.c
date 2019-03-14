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
  printf("%s\n", "Phrase,Year,MatchCount,VolumeCount");
  int x2 = open(x0,0);
  int x3 = fsize(x2);
  char* x4 = mmap(0, x3, PROT_READ, MAP_FILE | MAP_SHARED, x2, 0);
  int x5 = 0;
  while (x5 < x3) ({
    int x6 = x5;
    while (x4[x5] != '\t') ({
      x5 = x5 + 1;
    });
    int x8 = x5 - x6;
    x5 = x5 + 1;
    int x9 = x5;
    while (x4[x5] != '\t') ({
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    int x12 = x5;
    while (x4[x5] != '\t') ({
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    int x15 = x5;
    while (x4[x5] != '\n') ({
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    ((x8 == 12 && ({
      char* x18 = x4 + x6;
      int x19 = 0;
      while (x19 < x8 && x18[x19] == "Auswanderung"[x19]) ({
        x19 = x19 + 1;
      });
      x19 == x8;
    })) ? ({
      char* x18 = x4 + x6;
      printll(x18);
      printf(",");
      printll(x4 + x9);
      printf(",");
      printll(x4 + x12);
      printf(",");
      printll(x4 + x15);
      printf("%s\n", "");
    }) : ({}));
  });
  close(x2);
}
/*****************************************
End of C Generated Code
*******************************************/
