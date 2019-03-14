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
  int x2 = open("src/data/words.csv",0);
  int x3 = fsize(x2);
  char* x4 = mmap(0, x3, PROT_READ, MAP_FILE | MAP_SHARED, x2, 0);
  int x5 = 0;
  while (x4[x5] != ',') ({
    x5 = x5 + 1;
  });
  x5 = x5 + 1;
  int x9 = 0;
  while (x4[x5] != '\n') ({
    x9 = x9 * 10 + (int)(x4[x5] - '0');
    x5 = x5 + 1;
  });
  x5 = x5 + 1;
  int x12 = open(x0,0);
  int x13 = fsize(x12);
  char* x14 = mmap(0, x13, PROT_READ, MAP_FILE | MAP_SHARED, x12, 0);
  while (x5 < x3) ({
    int x15 = x5;
    while (x4[x5] != ',') ({
      x5 = x5 + 1;
    });
    int x17 = x5 - x15;
    x5 = x5 + 1;
    char* x18 = x4 + x15;
    int x19 = 0;
    while (x4[x5] != '\n') ({
      x19 = x19 * 10 + (int)(x4[x5] - '0');
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    int x21 = x19;
    int x22 = 0;
    while (x22 < x13) ({
      int x23 = x22;
      while (x14[x22] != '\t') ({
        x22 = x22 + 1;
      });
      int x25 = x22;
      x22 = x22 + 1;
      int x26 = x22;
      while (x14[x22] != '\t') ({
        x22 = x22 + 1;
      });
      x22 = x22 + 1;
      int x29 = x22;
      while (x14[x22] != '\t') ({
        x22 = x22 + 1;
      });
      x22 = x22 + 1;
      int x32 = x22;
      while (x14[x22] != '\n') ({
        x22 = x22 + 1;
      });
      x22 = x22 + 1;
      ((x17 == x25 - x23 && ({
        char* x35 = x14 + x23;
        int x36 = 0;
        while (x36 < x17 && x18[x36] == x35[x36]) ({
          x36 = x36 + 1;
        });
        x36 == x17;
      })) ? ({
        char* x35 = x14 + x23;
        printll(x18);
        printf(",");
        printf("%d", x21);
        printf(",");
        printll(x35);
        printf(",");
        printll(x14 + x26);
        printf(",");
        printll(x14 + x29);
        printf(",");
        printll(x14 + x32);
        printf("%s\n", "");
      }) : ({}));
    });
    close(x12);
  });
  close(x2);
}
/*****************************************
End of C Generated Code
*******************************************/
